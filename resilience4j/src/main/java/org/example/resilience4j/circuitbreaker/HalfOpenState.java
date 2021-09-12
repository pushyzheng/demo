package org.example.resilience4j.circuitbreaker;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.vavr.control.Try;
import org.example.resilience4j.utils.Resilience4jUtils;
import org.example.resilience4j.utils.TimeUtils;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicBoolean;


public class HalfOpenState {

    public static void main(String[] args) {
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                // 窗口大小
                .slidingWindowSize(5)
                // 失败阈值
                .failureRateThreshold(50)
                // 等待时间
                .waitDurationInOpenState(Duration.ofMillis(2000))

                // 半开模式下的尝试次数, 默认为 10 次
                .permittedNumberOfCallsInHalfOpenState(3)
                // 半开模式下最大的等待时间
                .maxWaitDurationInHalfOpenState(Duration.ofMillis(2000))
                .build();

        CircuitBreaker circuitBreaker =
                Resilience4jUtils.createCircuitBreaker(circuitBreakerConfig);

        AtomicBoolean needsThrow = new AtomicBoolean(true);

        // 这里模拟的情况是: 直到状态从 CLOSED -> OPEN -> HALF_OPEN 之前
        // 都是抛出异常, 直到变成 HALF_OPEN 后所有的情况都正常
        // 熔断器最终恢复成 CLOSED 状态
        for (int i = 0; i < 50; i++) {
            Try.runRunnable(circuitBreaker.decorateRunnable(() -> {
                if (needsThrow.get()) {
                    throw new RuntimeException();
                } else {
                    System.out.println("SUCCESS");
                    // 半开状态所有请求是否成功
//                    needsThrow.set(false);
                }
            })).onFailure(Resilience4jUtils::handleError);

            TimeUtils.sleep(500);
            if (circuitBreaker.getState() == CircuitBreaker.State.HALF_OPEN) {
                needsThrow.set(false);
            }
            System.out.println("state: " + circuitBreaker.getState());
        }
    }
}
