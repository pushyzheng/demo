package com.example.scandemo.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author Pushy
 * @since 2018/12/1 21:08
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Component
public @interface Service {

    Class<?> value();

}
