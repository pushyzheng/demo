package org.example.demo.redisson.sync;

import lombok.extern.slf4j.Slf4j;
import org.example.demo.redisson.Redissons;
import org.example.demo.redisson.Threads;

@Slf4j
public class SemaphoreExample {

    public static void main(String[] args) {
        Redissons.executeThenDestroy(c -> {
            var sem = c.getSemaphore("semaphore");
            sem.trySetPermits(2);  // 信号量资源个数

            Threads.runRepeat(5, () -> {
                if (sem.tryAcquire(1)) {
                    log.info("acquire success");
                } else {
                    log.warn("acquire fail");
                }
            });
        });
    }
}
