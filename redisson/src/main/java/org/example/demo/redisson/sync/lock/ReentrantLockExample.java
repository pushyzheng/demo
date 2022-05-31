package org.example.demo.redisson.sync.lock;

import lombok.extern.slf4j.Slf4j;
import org.example.demo.redisson.Redissons;
import org.example.demo.redisson.Threads;
import org.example.demo.redisson.Times;
import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

@Slf4j
public class ReentrantLockExample {

    public static void main(String[] args) {
        Threads.runRepeat(10, ReentrantLockExample::run);
    }

    private static void run() {
        Redissons.executeThenDestroy(client -> {
            RLock lock = client.getLock("lock");
            try {
                // 最多会等待 1 秒中获取锁
                // 另外, 当获取锁成功之后, 只会持有锁 10s 钟
                if (!lock.tryLock(1, 10, TimeUnit.SECONDS)) {
                    log.warn("try lock fail");
                    return;
                }
                log.info("lock succeed");
                Times.sleepSeconds(3);
                lock.unlock();
                log.info("unlock succeed");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
