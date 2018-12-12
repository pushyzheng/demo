package com.example.redislock;

/**
 * @author Pushy
 * @since 2018/12/9 21:56
 */
public class Test {
    public static void main(String[] args) {
        for (int i = 0; i < 2; i++) {
            new Thread(new Runnable() {
                public void run() {
                    String threadName = Thread.currentThread().getName();

                    RedisDistributedLockVersion2 lock = new RedisDistributedLockVersion2();
                    System.out.println(threadName + " acquire lock...");
                    String identifier = lock.acquireLockWithTimeout("market:", 3);

                    if (identifier == null) {
                        System.out.println(threadName + " acquire lock timeout...");
                        return;
                    }

                    try {
                        System.out.println(threadName + " is doing something...");
                        Thread.sleep(1000);
                        System.out.println(threadName + " have done.");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        // 释放锁
                        lock.releaseLock("market:", identifier);
                    }
                }
            }).start();
        }

    }
}
