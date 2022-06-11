package org.example.demo.cloudnative;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
public class CloudNativeApplication {

    @RequestMapping("/")
    public String index() {
        return "Hello World";
    }

    public static void main(String[] args) {
        SpringApplication.run(CloudNativeApplication.class, args);
    }
}
