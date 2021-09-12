package org.example.resilience4j.retry;

import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.example.resilience4j.RetryNeededException;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class RetrySync {

    private static final int MAX_ATTEMPT = 3;

    public static void main(String[] args) {
        RetryConfig retryConfig = RetryConfig.custom()
                .maxAttempts(MAX_ATTEMPT)
                .retryExceptions(RetryNeededException.class)
                .build();
        Retry retry = Retry.of("service", retryConfig);
        registerEvent(retry);

        AtomicInteger counter = new AtomicInteger(0);
        Try<Void> result = Try.run(Retry.decorateCheckedRunnable(retry, () -> {
            if (counter.incrementAndGet() < MAX_ATTEMPT) {  // 模拟前两次都是异常
                System.out.println(counter.get() + " ERROR");
                throw new RetryNeededException();
            }
            System.out.println(counter.get() + " SUCCESS");
        }));

        if (result.isSuccess()) {
            System.out.println("执行成功");
        } else {
            System.out.println("执行失败");
        }
    }

    private static void registerEvent(Retry retry) {
        retry.getEventPublisher()
                .onRetry(event -> {
                    log.info("[Event publisher] name: {}, eventType: {}, attempts: {}",
                            event.getName(), event.getEventType(), event.getNumberOfRetryAttempts());
                }).onSuccess(event -> {
                    log.info("[Event publisher] name: {}, retry success",
                            event.getName());
                });
    }
}
