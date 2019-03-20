package site.pushy.seckill.limit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author Pushy
 * @since 2019/3/4 11:12
 */
@Aspect
@Configuration
public class SemaphoreAspect {

    private static final Logger logger = LoggerFactory.getLogger(RateLimitAspect.class);

    @Resource(name = "semaphoreScript")
    private DefaultRedisScript<Number> semaphoreScript;

    @Resource(name = "semaphoreReleaseScript")
    private DefaultRedisScript<Number> semaphoreReleaseScript;

    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;

    @Around("execution(* site.pushy.seckill.controller ..*(..) )")
    public Object semaphoreInterceptor(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Class<?> targetClass = method.getDeclaringClass();
        SemaphoreLimit semaphoreLimit = method.getAnnotation(SemaphoreLimit.class);

        if (semaphoreLimit != null) {
            String identifier = UUID.randomUUID().toString();
            List<String> keys = Arrays.asList(identifier, semaphoreLimit.key());
            long now = System.currentTimeMillis();

            Number result = redisTemplate
                    .execute(semaphoreScript, keys, now, semaphoreLimit.timeout(), semaphoreLimit.limit());
            if (result.intValue() == 1) {
                logger.info("获取信号量成功 => " + identifier);
                try {
                    return joinPoint.proceed();
                } finally {
                    releaseSemaphore(identifier, semaphoreLimit.key());
                }
            }
        }
        else {
            return joinPoint.proceed();
        }
        logger.error("获取信号量失败，拒绝访问");
        throw new RuntimeException("获取信号量失败，拒绝访问");
    }

    /**
     * 执行Lua脚本，释放信号量
     */
    private boolean releaseSemaphore(String identifier, String key) {
        List<String> keys = Arrays.asList(identifier, key);
        Number result = redisTemplate.execute(semaphoreReleaseScript, keys);
        return result.intValue() == 1;
    }

}
