package site.pushy.seckill.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;
import site.pushy.seckill.limit.RateLimitAspect;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author Pushy
 * @since 2019/3/5 9:40
 */
@Component
public class DistributedLock {

    private static final Logger logger = LoggerFactory.getLogger(RateLimitAspect.class);
    private static final int SUCCESS = 1;

    @Resource(name = "lockReleaseScript")
    private DefaultRedisScript<Number> lockReleaseScript;

    @Resource(name = "lockAcquireScript")
    private DefaultRedisScript<Number> lockAcquireScript;

    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;

    private String getLockKey(String name) {
        return "locked:" + name;
    }

    public String acquire(String name) {
        return acquire(name, 2000, 60);
    }
    public String acquire(String name, int lockExpire) {
        return acquire(name, 2000, lockExpire);
    }

    /**
     * 获取锁
     * @param name 锁的唯一标识
     * @param acquireTimeout 获取锁的超时时间
     * @param lockExpire 锁的过期时间，单位 Second
     */
    public String acquire(String name, long acquireTimeout, int lockExpire) {
        String identifier = UUID.randomUUID().toString();
        String lockKey = getLockKey(name);

        long end = System.currentTimeMillis() + acquireTimeout;
        while (System.currentTimeMillis() < end) {
            List<String> keys = Arrays.asList(lockKey, identifier);
            Number result = redisTemplate.execute(lockAcquireScript, keys, lockExpire);
            if (result.intValue() == SUCCESS) { // 获取锁成功
                logger.info(Thread.currentThread().getName() + " 获取锁成功");
                return identifier;
            }
            // 休眠一秒，重新获取锁
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        logger.error(Thread.currentThread().getName() + " 获取锁超时");
        return null;
    }

    public boolean release(String name, String identifier) {
        List<String> keys = Arrays.asList(getLockKey(name), identifier);
        Number result = redisTemplate.execute(lockReleaseScript, keys);
        if (result.intValue() == SUCCESS) {  // 锁释放成功
            logger.info(Thread.currentThread().getName() + " 锁释放成功");
            return true;
        }
        logger.error(Thread.currentThread().getName() + " 锁释放失败");
        // 锁释放失败
        return false;
    }

}
