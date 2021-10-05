package org.example.rpc.dubbo.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan(basePackages = "org.example.rpc.dubbo")
public class DubboProviderSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboProviderSpringApplication.class);
    }
}
