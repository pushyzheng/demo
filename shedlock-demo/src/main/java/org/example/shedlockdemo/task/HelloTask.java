package org.example.shedlockdemo.task;

import net.javacrumbs.shedlock.core.LockAssert;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class HelloTask {

    @Scheduled(fixedRate = 3, timeUnit = TimeUnit.SECONDS)
    @SchedulerLock(
            name = "scheduledTaskName", // 名称
            lockAtMostFor = "14m",      // 最多锁定时间
            lockAtLeastFor = "14m"      // 最少锁定时间
    )
    public void schedule() {
        LockAssert.assertLocked();

        System.out.println("do-something");
    }
}
