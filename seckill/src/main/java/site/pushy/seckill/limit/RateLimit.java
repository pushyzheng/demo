package site.pushy.seckill.limit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Pushy
 * @since 2019/3/3 20:42
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {

    /**
     * 限流唯一标识
     */
    String key() default "";

    /**
     * 限流时间
     */
    int time();

    /**
     * 限流次数
     */
    int count();

}
