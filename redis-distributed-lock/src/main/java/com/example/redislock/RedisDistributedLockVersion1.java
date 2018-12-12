package com.example.redislock;

import redis.clients.jedis.Jedis;

import java.util.UUID;

/**
 * @author Pushy
 * @since 2018/12/12 15:37
 */
public class RedisDistributedLockVersion1 {

    private Jedis conn = new Jedis("localhost", 6379);

    /**
     * 加锁
     * @param name 加锁的名称
     * @param timeout 请求锁的超时时间
     */
    public boolean acquireLock(String name, int timeout) {
        String lockKey = "lock:" + name;  // 加锁的键

        long end = System.currentTimeMillis() + timeout;
        while (System.currentTimeMillis() < end) {  // 重试循环到超时时间
            if (conn.setnx(lockKey, "1") == 1) { // 获取锁成功
                return true;
            }
            // 无法成功执行setnx方法，等待1秒后重试
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        // 请求超时，获取锁失败
        return false;
    }

    /**
     * 释放锁
     * @param name 加锁的名称
     */
    public boolean releaseLock(String name) {
        String lockKey = "lock:" + name;
        return conn.del(lockKey) == 1L;
    }


}
