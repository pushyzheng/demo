package org.example.example.rws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration;

/**
 * 因为 ShardingSphere 会自动转配 {@link javax.sql.DataSource}
 * <p>
 * 所以不需要 {@link DataSourceAutoConfiguration} 和 {@link R2dbcAutoConfiguration} 来自动装配了
 */
@SpringBootApplication(
        exclude = {DataSourceAutoConfiguration.class,
                R2dbcAutoConfiguration.class}
)
public class ExampleReadWriteSplittingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExampleReadWriteSplittingApplication.class, args);
    }
}
