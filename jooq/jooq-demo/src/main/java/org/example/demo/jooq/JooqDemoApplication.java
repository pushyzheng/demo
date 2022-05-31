package org.example.demo.jooq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;

@SpringBootApplication(
        exclude = FlywayAutoConfiguration.class
)
public class JooqDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(JooqDemoApplication.class, args);
    }
}
