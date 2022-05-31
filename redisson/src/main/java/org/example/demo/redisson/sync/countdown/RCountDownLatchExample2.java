package org.example.demo.redisson.sync.countdown;

import lombok.extern.slf4j.Slf4j;
import org.example.demo.redisson.Redissons;
import org.example.demo.redisson.Threads;
import org.example.demo.redisson.Times;
import org.redisson.api.RCountDownLatch;

@Slf4j
public class RCountDownLatchExample2 {

    public static void main(String[] args) {
        Redissons.executeThenDestroy(c -> {
            RCountDownLatch cdl = c.getCountDownLatch("countdown-latch");

            // 开启两个线程, 对 cdl 进行 countdown
            // 这样 RCountDownLatchExample1 就会 await 就会被唤醒了
            Threads.runRepeat(2, () -> {
                cdl.countDown();
                Times.sleepSeconds(1);
                log.info("countdown");
            });
        });
    }
}
