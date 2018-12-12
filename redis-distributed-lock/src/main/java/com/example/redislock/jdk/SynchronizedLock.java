package com.example.redislock.jdk;

/**
 * @author Pushy
 * @since 2018/12/12 14:23
 */
public class SynchronizedLock {

    private static SynchronizedLock INSTANCE;

    public static SynchronizedLock getINSTANCE() {
        if (INSTANCE == null) {
            synchronized (SynchronizedLock.class) {
                if (INSTANCE == null) {
                    return new SynchronizedLock();
                }
            }
        }
        return INSTANCE;
    }

}
