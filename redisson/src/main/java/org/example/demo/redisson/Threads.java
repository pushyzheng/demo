package org.example.demo.redisson;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Threads {

    public void run(Runnable runnable) {
        new Thread(runnable).start();
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public void runAlways(Runnable runnable, int interval) {
        run(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                runnable.run();
                Times.sleep(interval);
            }
        });
    }

    public void run(Runnable runnable, int delayMs) {
        Times.sleep(delayMs);
        new Thread(runnable).start();
    }

    public void runRepeat(int n, Runnable runnable) {
        for (int i = 0; i < n; i++) {
            new Thread(runnable).start();
        }
    }

    public void runRepeatAlways(int n, int interval, Runnable runnable) {
        for (int i = 0; i < n; i++) {
            runAlways(runnable, interval);
        }
    }
}
