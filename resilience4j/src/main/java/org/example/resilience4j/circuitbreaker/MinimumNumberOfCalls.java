package org.example.resilience4j.circuitbreaker;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.example.resilience4j.utils.Resilience4jUtils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 通过 minimumNumberOfCalls 属性配置来控制计算失败率的最小调用次数
 * 而无需等到滑动窗口结束
 * <br>
 * 适用的场景即当前面过多的请求已经失败, 远远大于了失败的阈值(过低的失败率阈值)
 * 为了防止后边的调用仍然继续,可以使用这个来控制
 */
@Slf4j
public class MinimumNumberOfCalls {
    public static void main(String[] args) {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .slidingWindowSize(10)

                // 最小调用次数
                .minimumNumberOfCalls(8)
                .build();

        CircuitBreaker circuitBreaker = Resilience4jUtils.createCircuitBreaker(config);
        AtomicInteger counter = new AtomicInteger(0);

        // 不需要等待到达滑动窗口 10 次调用才开始计算失败率
        // 也就是说当第 8 次调用时,计算出来的失败率如果已经大于失败阈值 50%
        // 那么后两次的调用也会被熔断(即使没有到达一个滑动窗口的周期)
        while (counter.getAndIncrement() < 10) {
            Try.run(CircuitBreaker.decorateCheckedRunnable(circuitBreaker, () -> {
                if (counter.get() < 7) {
                    throw new RuntimeException();
                }
                log.info("SUCCESS, counter: {}", counter.get());
            })).onFailure(Resilience4jUtils::handleError);
        }
    }
}
