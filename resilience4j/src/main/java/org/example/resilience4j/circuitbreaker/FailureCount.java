package org.example.resilience4j.circuitbreaker;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.vavr.control.Try;
import org.example.resilience4j.utils.Resilience4jUtils;
import org.example.resilience4j.utils.TimeUtils;

import java.time.Duration;
import java.util.Random;


public class FailureCount {
    public static void main(String[] args) {
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                // 窗口大小
                .slidingWindowSize(5)
                // 失败
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofMillis(1000))
                .build();

        CircuitBreaker circuitBreaker =
                Resilience4jUtils.createCircuitBreaker(circuitBreakerConfig);

        for (int i = 0; i < 50; i++) {
            Try.runRunnable(circuitBreaker.decorateRunnable(() -> {
                if (new Random().nextInt(5) != 0) {  // 80%的几率会失败
                    throw new RuntimeException();
                }
                System.out.println("SUCCESS");
                TimeUtils.sleep(100);
            })).onFailure(Resilience4jUtils::handleError);
        }
    }
}
