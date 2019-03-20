package site.pushy.seckill.limit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Pushy
 * @since 2019/3/4 11:08
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SemaphoreLimit {

    /**
     * 限流唯一标识
     */
    String key() default "";

    /**
     * 信号量个数，即某一时间内并发的线程总数
     */
    int limit();

    /**
     * 信号量过期时间
     */
    int timeout();

}
