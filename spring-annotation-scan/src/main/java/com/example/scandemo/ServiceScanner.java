package com.example.scandemo;

import com.example.scandemo.annotation.Service;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

/**
 * @author Pushy
 * @since 2018/12/3 10:01
 */
public class ServiceScanner extends ClassPathBeanDefinitionScanner {

    public ServiceScanner(BeanDefinitionRegistry registry) {
        super(registry, false);
    }

    public void registerTypeFilter(){
        addIncludeFilter(new AnnotationTypeFilter(Service.class));
    }

}
