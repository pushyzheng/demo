package org.example.resilience4j.ratelimiter;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.vavr.CheckedRunnable;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.example.resilience4j.utils.Resilience4jUtils;

import java.time.Duration;
import java.util.function.IntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Slf4j
public class RateLimiterDemo {
    public static void main(String[] args) {
        RateLimiterConfig config = RateLimiterConfig.custom()
                // 周期时间
                .limitRefreshPeriod(Duration.ofMillis(1000))
                // 在周期内的令牌个数
                .limitForPeriod(3)
                // 线程获取令牌等待时间
                .timeoutDuration(Duration.ofMillis(3))
                .build();

        RateLimiterRegistry rateLimiterRegistry = RateLimiterRegistry.of(config);
        RateLimiter rateLimiter = rateLimiterRegistry.rateLimiter("service", config);

        registerEvent(rateLimiter);

        CheckedRunnable runnable = RateLimiter.decorateCheckedRunnable(rateLimiter, () -> {
            log.info("SUCCESS");
        });

        for (int i = 0; i < 5; i++) {
            new Thread(() -> Try.run(runnable)
                    .onFailure(Resilience4jUtils::handleError)).start();
        }
    }

    private static void registerEvent(RateLimiter rateLimiter) {
        rateLimiter.getEventPublisher()
                .onSuccess(event -> {
                    log.info("[EventPublisher] success: {}", event.getRateLimiterName());
                })
                .onFailure(event -> {
                    log.info("[EventPublisher] error:  {}", event.getRateLimiterName());
                });
    }
}
