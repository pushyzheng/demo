package org.example.reactor.flux;

import com.google.common.collect.Lists;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Parallel {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        Flux.fromIterable(Lists.newArrayList(1, 2, 3))
                // 并行数量
                .parallel(3)
                // 每个并行轨道执行的线程
                .runOn(Schedulers.parallel())
                // 并行的逻辑
                .flatMap(s -> {
                    Try.run(() -> TimeUnit.SECONDS.sleep(s + 1));
                    log.info("finished, param: {}", s);
                    return Mono.just(s + 1);
                })
                // 有序地收集最终的并行结果
                .sequential()
                .collectList()
                .doOnSuccess(collect -> log.info("last result: {}", collect))
                .doFinally(st -> log.info("cost: {}", System.currentTimeMillis() - start))
                .subscribeOn(Schedulers.parallel())
                .subscribe();

        new Scanner(System.in).nextLine();
    }
}
