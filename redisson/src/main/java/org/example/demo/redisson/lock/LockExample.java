package org.example.demo.redisson.lock;

import org.example.demo.redisson.Redissons;
import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

public class LockExample {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(LockExample::run).start();
        }
    }

    private static void run() {
        Redissons.execute(client -> {
            RLock lock = client.getLock("lock");
            try {
                // 最多会等待 5 秒中获取锁
                // 另外, 当获取锁成功之后, 只会持有锁 10s 钟
                if (!lock.tryLock(5, 10, TimeUnit.SECONDS)) {
                    System.out.println("try lock fail");
                    return;
                }
                System.out.println("lock succeed");
                Thread.sleep(10000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
