package org.example.rpc.dubbo.provider.service;

import org.apache.dubbo.config.annotation.DubboService;
import org.example.rpc.service.AsyncService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@DubboService
public class AsyncServiceImpl implements AsyncService {

    @Override
    public CompletableFuture<String> asyncHello(String name) {
        return CompletableFuture.supplyAsync(() -> {
            doSomething();
            return "Hello " + name + "!";
        });
    }

    @Override
    public String syncHello(String name) {
        doSomething();
        return "Hello " + name + "!";
    }

    private void doSomething() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
