package org.example.async.jdk.forkJoin;

import lombok.extern.slf4j.Slf4j;
import org.example.async.BaseExample;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

@Slf4j
public class RecursiveTaskExample extends BaseExample {

    public static void main(String[] args) {
        // 1. fork-join 线程池
        ForkJoinPool fjp = new ForkJoinPool(4);
        // 2. 创建 ForkJoinTask
        Fibonacci fib = new Fibonacci(3);

        // 3. invoke 调用执行
        int result = fjp.invoke(fib);

        log.info("计算完成, result: {}", result);
    }

    private static class Fibonacci extends RecursiveTask<Integer> {

        private final int n;

        private Fibonacci(int n) {
            this.n = n;
        }

        @Override
        protected Integer compute() {
            if (n <= 1) {
                return n;
            }
            syncLog(() -> log.info("创建子任务 f({}) 异步执行", n - 1));
            // 创建子任务, 异步执行
            Fibonacci f1 = new Fibonacci(n - 1);
            f1.fork();

            syncLog(() -> log.info("递归处理任务 f({})", n - 2));
            // 在当前任务中递归处理
            Fibonacci f2 = new Fibonacci(n - 2);

            int r2 = f2.compute();
            syncLog(() -> log.info("获取到 f({}) 返回值: {}", n - 2, r2));
            int r1 = f1.join();
            syncLog(() -> log.info("获取到 f({}) 返回值: {}", n - 1, r1));
            return r2 + r1;
        }
    }
}