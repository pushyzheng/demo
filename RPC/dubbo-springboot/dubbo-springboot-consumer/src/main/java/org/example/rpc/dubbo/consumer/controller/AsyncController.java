package org.example.rpc.dubbo.consumer.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.Method;
import org.apache.dubbo.rpc.RpcContext;
import org.example.rpc.dubbo.consumer.DubboServiceHolder;
import org.example.rpc.service.AsyncService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/async")
@Slf4j
public class AsyncController {

    @Resource
    private DubboServiceHolder dubboServiceHolder;

    /**
     * 服务端异步实现
     */
    @RequestMapping("server")
    public CompletableFuture<String> async(@RequestParam(defaultValue = " World") String name) {
        CompletableFuture<String> future = dubboServiceHolder.getAsyncService()
                .asyncHello(name);
        future.whenComplete((result, t) -> {
            if (t != null) {
                t.printStackTrace();
            } else {
                log.info("response: {}", result);
            }
        });
        log.info("controller return");
        return future;
    }

    /**
     * 客户端异步实现
     * 即调用的服务端接口本身是一个同步的方法
     */
    @RequestMapping("client")
    public CompletableFuture<String> asyncByClient(@RequestParam(defaultValue = " World") String name) {
        String nullResult = dubboServiceHolder.getAsyncService().syncHello(name);
        log.info("此时立马调用的,返回的结果为 null:,即 result = {}", nullResult);
        CompletableFuture<String> future = RpcContext.getServiceContext().getCompletableFuture();

        future.whenComplete((result, t) -> {
            if (t != null) {
                t.printStackTrace();
            } else {
                log.info("[sync method] response: {}", result);
            }
        });
        log.info("[sync method] controller return");
        return future;
    }
}
