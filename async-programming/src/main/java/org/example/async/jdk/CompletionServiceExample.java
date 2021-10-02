package org.example.async.jdk;

import lombok.extern.slf4j.Slf4j;
import org.example.async.BaseExample;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

@Slf4j
public class CompletionServiceExample extends BaseExample {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        CompletionService<Integer> service = new ExecutorCompletionService<>(
                // 1. thread pool
                Executors.newFixedThreadPool(10),
                // 2. blocking queue
                new LinkedBlockingDeque<>(100)
        );

        // 调用两个服务获取结果
        service.submit(BaseExample::sleepRandom);
        service.submit(BaseExample::sleepRandom);

        // 按照返回的顺序取出返回结果
        for (int i = 0; i < 2; i++) {
            Integer result = service.take().get();
            log.info("save to database, result: {}", result);
        }
    }
}
