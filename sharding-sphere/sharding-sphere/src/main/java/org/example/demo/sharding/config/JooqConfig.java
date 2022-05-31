package org.example.demo.sharding.config;

import com.zaxxer.hikari.HikariDataSource;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class JooqConfig {

    /* no sharding datasource config  */

    @Primary
    @Bean(name = "getNoShardingDataSource")
    @Qualifier("getNoShardingDataSource")
    public DataSource noShardingDataSource(@Autowired NoShardingDataSourceProperties noShardingDataSourceProperties) {
        return new TransactionAwareDataSourceProxy(
                new HikariDataSource(noShardingDataSourceProperties));
    }

    @Bean(name = "noShardingTransactionManager")
    @Primary
    public PlatformTransactionManager noShardingTransactionManager(@Qualifier("getNoShardingDataSource")
                                                                   DataSource noShardingDataSource) {
        return new DataSourceTransactionManager(noShardingDataSource);
    }

    @Primary
    @Bean(name = "noShardingDsl")
    @Qualifier("noShardingDsl")
    public DSLContext noShardDsl(@Qualifier("getNoShardingDataSource") DataSource noShardingDataSource) {
        return DSL.using(new DefaultConfiguration()
                .set(noShardingDataSource)
                .set(SQLDialect.MYSQL));
    }

    /* sharding datasource config  */

    @Bean(name = "getShardingDataSource")
    @Qualifier("getShardingDataSource")
    public DataSource shardingDataSource(@Qualifier("shardingDataSource") DataSource shardingDataSource) {
        return new TransactionAwareDataSourceProxy(shardingDataSource);
    }

    @Bean(name = "shardingTransactionManager")
    public PlatformTransactionManager shardingTransactionManager(@Qualifier("getShardingDataSource") DataSource shardingDataSource) {
        return new DataSourceTransactionManager(shardingDataSource);
    }

    @Bean(name = "shardingDsl")
    @Qualifier("shardingDsl")
    public DSLContext shardingDsl(@Qualifier("shardingDataSource") DataSource shardingDataSource) {
        return DSL.using(new DefaultConfiguration()
                .set(shardingDataSource)
                .set(new Settings().withRenderSchema(false))
                .set(SQLDialect.MYSQL));
    }
}
