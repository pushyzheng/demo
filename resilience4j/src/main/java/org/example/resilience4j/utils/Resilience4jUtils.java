package org.example.resilience4j.utils;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
public class Resilience4jUtils {

    public static CircuitBreaker createCircuitBreaker(CircuitBreakerConfig config) {
        return createCircuitBreaker(config, "service");
    }

    public static CircuitBreaker createCircuitBreaker(CircuitBreakerConfig config, String name) {
        CircuitBreakerRegistry circuitBreakerRegistry =
                CircuitBreakerRegistry.of(config);
        return circuitBreakerRegistry.circuitBreaker(name, config);
    }

    public static RateLimiter createRateLimiter(int limitRefreshPeriod, int limitForPeriod) {
        RateLimiterConfig config = RateLimiterConfig.custom()
                .limitRefreshPeriod(Duration.ofMillis(limitRefreshPeriod))
                .limitForPeriod(limitForPeriod)
                .build();

        RateLimiterRegistry rateLimiterRegistry = RateLimiterRegistry.of(config);
        return rateLimiterRegistry.rateLimiter("service", config);
    }

    public static void handleError(Throwable throwable) {
        if (throwable instanceof CallNotPermittedException) {
            log.error("请求被熔断");
        } else if (throwable instanceof RequestNotPermitted) {
            log.error("请求被限流");
        } else {
            log.error("发生了异常");
        }
    }
}
