package org.example.async.jdk.future;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class FutureDemo {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        FutureTask<String> futureTask = new FutureTask<>(() -> "value");
        executorService.submit(futureTask);

        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(2000);
                System.out.println("result: " + futureTask.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
        thread.join();
        executorService.shutdown();
    }
}
