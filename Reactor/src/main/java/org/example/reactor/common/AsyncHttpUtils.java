package org.example.reactor.common;

import lombok.extern.slf4j.Slf4j;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClientConfig;
import org.asynchttpclient.Response;
import reactor.core.publisher.Mono;

@Slf4j
public class AsyncHttpUtils {

    private static final AsyncHttpClient ASYNC_HTTP_CLIENT;

    static {
        DefaultAsyncHttpClientConfig config = new DefaultAsyncHttpClientConfig.Builder()
                .setIoThreadsCount(2)
                .build();
        ASYNC_HTTP_CLIENT = new DefaultAsyncHttpClient(config);
    }

    public static Mono<String> get(String url) {
        log.info("request, url: {}", url);
        return Mono.fromFuture(() -> ASYNC_HTTP_CLIENT.prepareGet(url).execute().toCompletableFuture())
                .map(Response::getResponseBody);
    }
}
