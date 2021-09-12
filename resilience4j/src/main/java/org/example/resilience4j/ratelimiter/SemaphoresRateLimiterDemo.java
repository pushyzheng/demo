package org.example.resilience4j.ratelimiter;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.internal.SemaphoreBasedRateLimiter;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.example.resilience4j.utils.Resilience4jUtils;

import java.time.Duration;

@Slf4j
public class SemaphoresRateLimiterDemo {
    public static void main(String[] args) {
        RateLimiterConfig config = new RateLimiterConfig.Builder()
                .limitRefreshPeriod(Duration.ofSeconds(20))
                .limitForPeriod(3)
                .timeoutDuration(Duration.ofSeconds(3))
                .build();

        SemaphoreBasedRateLimiter rateLimiter =
                new SemaphoreBasedRateLimiter("service", config);

        Runnable runnable = RateLimiter.decorateRunnable(rateLimiter, () -> {
            System.out.println("SUCCESS");
        });
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                Try.runRunnable(runnable)
                        .onFailure(Resilience4jUtils::handleError);
            }).start();
        }
    }
}
