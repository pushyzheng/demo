package com.example.redislock;

import redis.clients.jedis.Jedis;

import java.util.Collections;
import java.util.UUID;

/**
 * @author Pushy
 * @since 2018/12/12 15:37
 */
public class RedisDistributedLockVersion2 {

    private static final String ACQUIRE_SUCCESS = "OK";
    private static final String NX = "NX";
    private static final String EX = "EX";
    private static final Long RELEASE_SUCCESS = 1L;

    private Jedis conn = new Jedis("localhost", 6379);

    public String acquireLockWithTimeout(String lockName, int lockExpire) {
        return acquireLockWithTimeout(lockName, 5000, lockExpire);
    }

    /**
     * 加锁，并且给锁设置一个过期时间，如果持有该对象的线程没有在过期时间内完成任务，该锁将会被删除并释放
     * @param lockExpire seconds unit
     */
    public String acquireLockWithTimeout(String lockName, long acquireTimeout, int lockExpire) {
        String identifier = UUID.randomUUID().toString();
        String lockKey = "lock:" + lockName;

        long end = System.currentTimeMillis() + acquireTimeout;
        while (System.currentTimeMillis() < end) {
            /*
            这种逻辑存在一个问题，如果在执行完 setnx 后突然宕机，那么使得 expire 方法将不被执行
            得到的结果是锁将没有过期的时间，成为了死锁
            if (conn.setnx(lockKey, identifier) == 1) {
                conn.expire(lockKey, lockExpire);
                return identifier;
            }
            */

            /* 可以通过 set 的选项同时执行 setnx 和 setex 的操作 */
            String result = conn.set(lockKey, identifier, NX, EX, lockExpire);
            if (result != null && result.equals(ACQUIRE_SUCCESS)) {
                System.out.println(Thread.currentThread().getName() + " acquire lock succeed.");
                return identifier; // 加锁成功，返回加锁的标识符
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return null;
    }

    /**
     * 释放锁，由于要鉴定删除的键是否和标识符 identifier 相同，然后再删除该键
     * 但是这个操作不是原子性的，所以通过Lua脚本来执行，保证了这个两个操作的原子性
     * @param identifier 加锁生成的标识符，防止错删了锁
     */
    public boolean releaseLock(String lockKey, String identifier) {
        /*
        这样删除键释放锁，存在一个问题： 没有保证操作的原子性
        if (identifier.equals(conn.get(lockKey))) {
            如果在此时，这把锁突然不是该客户端的（此时锁可能刚好过期，属于该线程的键已经被删除，此时将会是其他线程加的锁）
            则会导致误解锁
            conn.del(lockKey);
        }
        */

        /*
            通过Lua脚本来执行删除键的命令，保证操作的原子性
            这是因为：在eval命令执行Lua代码的时候，Lua代码将被当成一个命令去执行，并且直到eval命令执行完成，Redis才会执行其他命令
         */
        String RELEASE_LOCK_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = conn.eval(RELEASE_LOCK_SCRIPT,
                Collections.singletonList(lockKey),
                Collections.singletonList(identifier));

        if (RELEASE_SUCCESS.equals(result)) {  // 删除锁成功
            System.out.println(Thread.currentThread().getName() + " release lock succeed.");
            return true;
        } else {
            System.out.println(Thread.currentThread().getName() + " fail to release lock.");
        }
        return false;
    }


}
