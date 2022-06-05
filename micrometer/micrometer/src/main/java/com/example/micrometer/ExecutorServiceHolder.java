package com.example.micrometer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author zuqin.zheng
 */
public class ExecutorServiceHolder {

    private static final ExecutorService common;

    static {
        common = new ThreadPoolExecutor(
                5,
                10,
                30,
                TimeUnit.MICROSECONDS,
                new LinkedBlockingDeque<>(100)
        );
    }

    public static ExecutorService getCommon() {
        return common;
    }
}
