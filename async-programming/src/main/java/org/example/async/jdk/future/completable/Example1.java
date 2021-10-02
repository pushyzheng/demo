package org.example.async.jdk.future.completable;

import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class Example1 {

    private static final AtomicInteger cupCount = new AtomicInteger(0);

    public static void main(String[] args) {
        // future1
        CompletableFuture<Void> f1 = CompletableFuture.runAsync(() -> {
            log.info("洗水壶");
            log.info("烧开水");
        });

        // future2
        CompletableFuture<String> f2 = CompletableFuture
                .supplyAsync(() -> {
                    log.info("洗茶壶");
                    log.info("洗茶杯");
                    if (cupCount.getAndDecrement() <= 0) {
                        throw new RuntimeException("茶杯没有了!");
                    }
                    log.info("拿茶叶");
                    return "龙井";
                })
                .handle((result, throwable) -> {
                    if (throwable != null) {
                        log.info("发生了异常", throwable);
                        return "无";
                    }
                    return result;
                });

        // future3 需要等待 future1 以及 future2 的执行完成
        // 并获取到 future2 的结果
        CompletableFuture<String> f3 = f1.thenCombine(f2, (unused, tea) -> {
            log.info("拿到茶叶: {}", tea);
            log.info("泡茶...");
            return "上茶:" + tea;
        });

        String result = f3.join();
        log.info(result);
    }
}
