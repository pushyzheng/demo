package org.example.demo.redisson;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.api.RedissonReactiveClient;

import java.util.function.Consumer;

public class Redissons {

    private static final RedissonClient redissonClient;

    private static final RedissonReactiveClient reactiveRedissonClient;

    static {
        redissonClient = Redisson.create();
        reactiveRedissonClient = redissonClient.reactive();
    }

    public static RedissonClient get() {
        return redissonClient;
    }

    public static RedissonReactiveClient getReactive() {
        return reactiveRedissonClient;
    }

    public static void execute(Consumer<RedissonClient> func) {
        func.accept(get());
    }

    public static void executeThenDestroy(Consumer<RedissonClient> func) {
        func.accept(get());
        destroy();
    }

    public static void destroy() {
        redissonClient.shutdown();
    }
}
