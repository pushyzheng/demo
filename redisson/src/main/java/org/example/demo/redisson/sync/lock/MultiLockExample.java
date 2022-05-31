package org.example.demo.redisson.sync.lock;

import lombok.extern.slf4j.Slf4j;
import org.example.demo.redisson.Redissons;
import org.example.demo.redisson.Threads;
import org.example.demo.redisson.Times;
import org.redisson.RedissonMultiLock;
import org.redisson.api.RLock;

@Slf4j
public class MultiLockExample {

    public static void main(String[] args) {
        Redissons.executeThenDestroy(c -> {
            RLock l1 = c.getLock("lock1");
            RLock l2 = c.getLock("lock2");
            RLock l3 = c.getLock("lock3");

            Threads.run(l1::lock);
            Times.sleep(100);

            // 使用联锁, 必须要保证三个锁都能成功上锁
            // 才能成功加锁
            var ml = new RedissonMultiLock(l1, l2, l3);
            if (ml.tryLock()) {
                log.info("lock success");
                ml.unlock();
            } else {
                log.warn("lock fail");
            }
        });
    }
}
