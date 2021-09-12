package org.example.resilience4j.circuitbreaker;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.vavr.control.Try;
import org.example.resilience4j.utils.Resilience4jUtils;

import java.time.Duration;

/**
 * 熔断降级的写法
 */
public class Demotion {
    public static void main(String[] args) {
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .slidingWindowSize(5)
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofMillis(1000))
                .build();

        final BackendService backendService = new BackendService();

        CircuitBreaker circuitBreaker =
                Resilience4jUtils.createCircuitBreaker(circuitBreakerConfig);

        for (int i = 0; i < 50; i++) {
            Try<String> result = Try
                    // 调用
                    .of(CircuitBreaker.decorateCheckedSupplier(circuitBreaker, backendService::getData))
                    // 对熔断之后的异常进行降级
                    .recover(CallNotPermittedException.class, Demotion::getDefaultData)

                    // 捕获正常业务抛出的异常
                    .recover(Throwable.class, throwable -> "ERROR");
            System.out.println("Result: " + result.get());
        }
    }

    /**
     * 降级的方法
     */
    private static String getDefaultData(Throwable throwable) {
        return "Hello World";
    }

    private static class BackendService {
        String getData() {
            throw new RuntimeException();
        }
    }
}
