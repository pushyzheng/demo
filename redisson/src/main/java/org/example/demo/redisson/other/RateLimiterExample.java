package org.example.demo.redisson.other;

import lombok.extern.slf4j.Slf4j;
import org.example.demo.redisson.Redissons;
import org.example.demo.redisson.Threads;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;

@Slf4j
public class RateLimiterExample {

    /**
     * 令牌桶的限流器
     * <p>
     * 和 Guava 实现的类似
     */
    public static void main(String[] args) {
        Redissons.execute(c -> {
            RRateLimiter limiter = c.getRateLimiter("123");

            // 尝试去设置
            // 返回值代表是不是初始化
            limiter.trySetRate(RateType.OVERALL, 3, 1, RateIntervalUnit.SECONDS);

            Threads.runRepeatAlways(3, 500, () -> {
                if (limiter.tryAcquire()) {
                    log.info("acquire success");
                } else {
                    log.warn("rate limit");
                }
            });
        });
    }
}
