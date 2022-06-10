package org.example.demo.sharding.config;

import org.jooq.ConnectionProvider;
import org.jooq.ExecuteListenerProvider;
import org.jooq.SQLDialect;
import org.jooq.conf.Settings;
import org.jooq.impl.DefaultConfiguration;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.jooq.DefaultConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
public class JooqConfig {

    @Resource(name = "shardingSphereDataSource")
    private DataSource shardingSphereDataSource;

    @Bean
    public DefaultConfiguration jooqConfiguration(ConnectionProvider connectionProvider,
                                                  ObjectProvider<ExecuteListenerProvider> executeListenerProviders,
                                                  ObjectProvider<DefaultConfigurationCustomizer> configurationCustomizers) {
        DefaultConfiguration configuration = new DefaultConfiguration();
        configuration.set(SQLDialect.MYSQL);
        configuration.set(connectionProvider);
        configuration.setDataSource(shardingSphereDataSource);
        configuration.set(new Settings().withRenderSchema(false));
        configuration.set(executeListenerProviders.orderedStream().toArray(ExecuteListenerProvider[]::new));
        configurationCustomizers.orderedStream().forEach((customizer) -> customizer.customize(configuration));
        return configuration;
    }
}
