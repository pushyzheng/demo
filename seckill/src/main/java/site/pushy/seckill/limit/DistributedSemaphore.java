package site.pushy.seckill.limit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * @author Pushy
 * @since 2019/3/4 11:13
 */
@Component
public class DistributedSemaphore {

    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;

    private String getSemKey(String semName) {
        return "semaphore:" + semName;
    }

    /**
     * 获取公平信号量
     * @param name
     * @param limit
     * @param timeout
     */
    public String acquire(String name, int limit, long timeout) {
        String identifier = UUID.randomUUID().toString();
        String semKey = getSemKey(name);
        String czset = name + ":owner";
        String ctr = name + ":counter";

        long now = System.currentTimeMillis();
        redisTemplate.multi();
        redisTemplate.opsForZSet().removeRangeByScore(semKey, 0, now - timeout);
        redisTemplate.opsForZSet().intersectAndStore(czset, semKey, czset);
        redisTemplate.opsForValue().increment(ctr);
        List<Object> results = redisTemplate.exec();
        int counter = ((Long) results.get(results.size() - 1)).intValue();

        redisTemplate.multi();
        redisTemplate.opsForZSet().add(semKey, identifier, now);
        redisTemplate.opsForZSet().add(czset, identifier, counter);

        redisTemplate.opsForZSet().rank(czset, identifier);
        results = redisTemplate.exec();
        int rank = ((Long) results.get(results.size() - 1)).intValue();
        if (rank < limit){  // 检查是否成功获取到信号量
            return identifier;
        }

        // 获取信号量失败，删除之前添加的标识符
        redisTemplate.multi();
        redisTemplate.opsForZSet().remove(semKey, identifier);
        redisTemplate.opsForZSet().remove(czset, identifier);
        redisTemplate.exec();

        return null;
    }

    /**
     * 释放信号量
     */
    public boolean release(String name, String identifier) {
        String semKey = getSemKey(name);
        redisTemplate.multi();
        redisTemplate.opsForZSet().remove(semKey, identifier);
        redisTemplate.opsForZSet().remove(semKey + ":owner", identifier);
        List<Object> results = redisTemplate.exec();
        return (long) results.get(results.size() - 1) == 1L;
    }

}
