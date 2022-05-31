package org.example.demo.sharding.config;

import com.zaxxer.hikari.HikariConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.datasource.no-sharding")
public class NoShardingDataSourceProperties extends HikariConfig {
}
