package org.example.async;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
public class BaseExample {

    private static final Random random = new Random();

    public static void sleep(int s) {
        try {
            Thread.sleep(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static int sleepRandom() {
        return sleepRandom(3);
    }

    public static int sleepRandom(int bound) {
        int s = (random.nextInt(bound) + 1) * 1000;
        sleep(s);
        log.info("sleepRandom done, cost:{}ms", s);
        return s;
    }

    // 同步写日志, 并发场景下, 日志可能不是有序的
    public synchronized static void syncLog(Runnable runnable) {
        runnable.run();
    }
}
