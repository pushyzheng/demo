package org.example.rpc.service;

import java.util.concurrent.CompletableFuture;

public interface AsyncService {

    /**
     * 服务方就提供的异步接口
     */
    CompletableFuture<String> asyncHello(String name);

    /**
     * 服务方提供的同步接口, 但在客户端做异步
     */
    String syncHello(String name);
}
