package org.example.reactor.mono;

import lombok.extern.slf4j.Slf4j;
import org.example.reactor.common.AsyncHttpUtils;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class ThreadSwitch {

    private static final Scheduler CUSTOM_SCHEDULER = Schedulers.newSingle("handle-response");

    public static void main(String[] args) {
        AsyncHttpUtils.get("https://baidu.com")
                .doOnSuccess(resp -> log.info("request success, response: \n{}", resp))
                // 此时的线程为 AsyncHttpClient 线程
                // 切换线程到 handle-response 处理业务逻辑
                .publishOn(CUSTOM_SCHEDULER)
                .map(s -> {
                    log.info("handle response");
                    return s.length();
                })
                .doOnSuccess(s -> log.info("success: {}", s))
                .subscribe();
    }
}
