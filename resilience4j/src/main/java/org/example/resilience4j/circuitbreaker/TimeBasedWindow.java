package org.example.resilience4j.circuitbreaker;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.vavr.control.Try;
import org.example.resilience4j.utils.Resilience4jUtils;
import org.example.resilience4j.utils.TimeUtils;

import java.time.Duration;

public class TimeBasedWindow {
    public static void main(String[] args) {
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                // 基于时间的滑动窗口类型
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.TIME_BASED)
                // 窗口大小
                .slidingWindowSize(5000)

                .waitDurationInOpenState(Duration.ofMillis(1000))
                .build();

        CircuitBreaker circuitBreaker =
                Resilience4jUtils.createCircuitBreaker(circuitBreakerConfig);

        for (int i = 0; i < 50; i++) {
            Try.runRunnable(circuitBreaker.decorateRunnable(() -> {
                throw new RuntimeException();
            })).onFailure(Resilience4jUtils::handleError);

            TimeUtils.sleep(500);
        }
    }
}
