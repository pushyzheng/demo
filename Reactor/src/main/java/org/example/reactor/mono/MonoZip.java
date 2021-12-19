package org.example.reactor.mono;

import lombok.extern.slf4j.Slf4j;
import org.example.reactor.common.AsyncHttpUtils;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class MonoZip {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Mono<String> baiduResult = AsyncHttpUtils.get("https://baidu.com")
                .doOnSuccess(r -> log.info("request baidu successfully, cost:{}ms", System.currentTimeMillis() - start));

        Mono<Integer> weiboResult = AsyncHttpUtils.get("https://weibo.com")
                .doOnSuccess(r -> log.info("request weibo successfully, cost:{}ms", System.currentTimeMillis() - start))
                .map(String::length);

        Mono.zip(baiduResult, weiboResult)
                .doOnSuccess(s -> log.info("get two result, cost: {}ms", System.currentTimeMillis() - start))
                .publishOn(Schedulers.newSingle("handle"))
                .map(tuple -> {
                    // 合并两个结果
                    int result = tuple.getT1().length() + tuple.getT2();
                    log.info("merge result: {}", result);
                    return result;
                })
                .subscribe();
    }
}
