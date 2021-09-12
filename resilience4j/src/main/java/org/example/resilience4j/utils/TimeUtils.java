package org.example.resilience4j.utils;

import io.vavr.control.Try;

public class TimeUtils {

    public static void sleep(int ms) {
        Try.run(() -> Thread.sleep(ms));
    }
}
