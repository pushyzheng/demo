package com.example.redislock.jdk;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Pushy
 * @since 2018/12/12 14:40
 */
public class ReentrantLockSingleton {

    private ReentrantLock reentrantLock = new ReentrantLock();
    private ReentrantLockSingleton INSTANCE;

    public ReentrantLockSingleton getINSTANCE() {
        if (INSTANCE == null) {
            reentrantLock.lock();
            if (INSTANCE == null) {
                return new ReentrantLockSingleton();
            }
            reentrantLock.unlock();
        }
        return INSTANCE;
    }
}
