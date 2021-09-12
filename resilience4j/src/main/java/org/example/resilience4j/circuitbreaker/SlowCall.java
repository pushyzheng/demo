package org.example.resilience4j.circuitbreaker;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.vavr.control.Try;
import org.example.resilience4j.utils.TimeUtils;

import java.time.Duration;
import java.util.Random;

public class SlowCall {

    public static void main(String[] args) {
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                // 窗口大小
                .slidingWindowSize(5)

                // 慢调用规则
                .slowCallDurationThreshold(Duration.ofMillis(1500))
                .slowCallRateThreshold(50)
                .build();

        CircuitBreakerRegistry circuitBreakerRegistry =
                CircuitBreakerRegistry.of(circuitBreakerConfig);
        CircuitBreaker circuitBreaker =
                circuitBreakerRegistry.circuitBreaker("service", circuitBreakerConfig);

        for (int i = 0; i < 50; i++) {
            Try.runRunnable(circuitBreaker.decorateRunnable(() -> {
                int s = new Random().nextInt(5) * 1000;
                System.out.println(s);
                TimeUtils.sleep(s);
                System.out.println("SUCCESS");
            })).onFailure(throwable -> {
                if (throwable instanceof CallNotPermittedException) {
                    System.out.println("接口被熔断");
                } else {
                    System.out.println("发生了异常");
                }
            });
        }
    }
}