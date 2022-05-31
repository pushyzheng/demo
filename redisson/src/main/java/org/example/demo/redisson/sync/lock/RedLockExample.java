package org.example.demo.redisson.sync.lock;

import lombok.extern.slf4j.Slf4j;
import org.example.demo.redisson.Redissons;
import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;

@Slf4j
public class RedLockExample {

    /**
     * 使用 {@link RedissonRedLock} 用来将多个 RLock 对象关联为一个红锁
     */
    public static void main(String[] args) {
        Redissons.executeThenDestroy(c -> {
            RLock rl = c.getLock("redLock");

            var redLock = new RedissonRedLock(rl);
            redLock.lock();

            log.info("lock success");
        });
    }
}
