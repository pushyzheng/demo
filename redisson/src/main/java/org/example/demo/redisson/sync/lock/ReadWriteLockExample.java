package org.example.demo.redisson.sync.lock;

import lombok.extern.slf4j.Slf4j;
import org.example.demo.redisson.Redissons;
import org.redisson.api.RReadWriteLock;

/**
 * @author zuqin.zheng
 */
@Slf4j
public class ReadWriteLockExample {
    public static void main(String[] args) {

        Redissons.executeThenDestroy(c -> {
            RReadWriteLock readWriteLock = c.getReadWriteLock("read-write-lock");

            readWriteLock.readLock().lock();
            log.info("加读锁成功");

            if (!readWriteLock.writeLock().tryLock()) {
                log.info("加写锁失败");
            }

            readWriteLock.readLock().unlock();
            log.info("释放读锁成功");

            if (readWriteLock.writeLock().tryLock()) {
                log.info("加写锁成功");
            }

            readWriteLock.writeLock().unlock();
            log.info("释放写锁成功");
        });
    }
}
