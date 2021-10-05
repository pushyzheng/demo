package org.example.rpc.dubbo.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan(basePackages = "org.example.rpc.dubbo")
public class DubboConsumerSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboConsumerSpringApplication.class);
    }
}
