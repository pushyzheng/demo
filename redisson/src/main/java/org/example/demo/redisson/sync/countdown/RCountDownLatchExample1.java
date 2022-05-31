package org.example.demo.redisson.sync.countdown;

import lombok.extern.slf4j.Slf4j;
import org.example.demo.redisson.Redissons;
import org.redisson.api.RCountDownLatch;

/**
 * 和 JDK  {@link java.util.concurrent.CountDownLatch} 基本类似
 * <p>
 * 但是, 可以进行分布式的同步调度
 */
@Slf4j
public class RCountDownLatchExample1 {

    public static void main(String[] args) {
        Redissons.executeThenDestroy(c -> {
            RCountDownLatch cdl = c.getCountDownLatch("countdown-latch");
            cdl.trySetCount(2);

            try {
                // 等待其他线程或者主机的成功需
                // 两次调用 cdl.countDown() 后, 才会继续往下运行
                cdl.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        log.info("finished");
    }
}
