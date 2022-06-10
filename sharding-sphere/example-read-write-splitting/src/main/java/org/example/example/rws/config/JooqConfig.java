package org.example.example.rws.config;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @author zuqin.zheng
 */
@Configuration
public class JooqConfig {

    @Resource
    private DataSource dataSource;

    @Bean
    public DSLContext shardingDsl() {
        return DSL.using(new DefaultConfiguration()
                .set(dataSource)
                .set(new Settings().withRenderSchema(false))
                .set(SQLDialect.MYSQL));
    }
}
