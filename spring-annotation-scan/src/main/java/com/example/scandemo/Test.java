package com.example.scandemo;

import com.example.scandemo.annotation.Service;
import org.springframework.context.support.GenericApplicationContext;

/**
 * @author Pushy
 * @since 2018/12/3 9:56
 */
public class Test {

    public static void main(String[] args) {
        /*ApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        Map<String, Object> map = context.getBeansWithAnnotation(Service.class);
        System.out.println(map);*/


        GenericApplicationContext context = new GenericApplicationContext();
        ServiceScanner serviceScanner = new ServiceScanner(context);
        serviceScanner.registerTypeFilter();

        serviceScanner.scan("com.example.scandemo");
        context.refresh();

        System.out.println(context.getBeansWithAnnotation(Service.class));
    }

}
