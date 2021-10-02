package org.example.async.jdk.future.completable;

import lombok.extern.slf4j.Slf4j;
import org.example.async.BaseExample;

import java.util.concurrent.CompletableFuture;

@Slf4j
public class AllOfExample {

    public static void main(String[] args) throws Exception {
        CompletableFuture<Integer> f1 = CompletableFuture
                .supplyAsync(() -> BaseExample.sleepRandom(3))
                .thenApplyAsync(AllOfExample::handleResult);

        CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync(() -> BaseExample.sleepRandom(3));

        CompletableFuture<Integer> f3 = CompletableFuture.supplyAsync(() -> BaseExample.sleepRandom(3));

        // allOf 可以等待所有的 future 都完成
        CompletableFuture.allOf(f1, f2, f3).join();
        log.info("f1: {}, f2: {}, f3: {}", f1.get(), f2.get(), f3.get());
    }

    private static int handleResult(int s) {
        // handle in another thread
        int result = s * 100;
        log.info("f1 apply to {}", result);
        return result;
    }
}
