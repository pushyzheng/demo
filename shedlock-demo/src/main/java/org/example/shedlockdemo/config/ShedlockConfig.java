package org.example.shedlockdemo.config;

import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.redis.jedis4.JedisLockProvider;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import redis.clients.jedis.JedisPool;

@Configuration
@EnableScheduling // enable spring
@EnableSchedulerLock(defaultLockAtMostFor = "10m") // enable shedlock
public class ShedlockConfig {

    @Bean
    public JedisPool jedisPool() {
        return new JedisPool();
    }

    @Bean
    public LockProvider lockProvider(JedisPool jedisPool) {
        return new JedisLockProvider(jedisPool);
    }
}
