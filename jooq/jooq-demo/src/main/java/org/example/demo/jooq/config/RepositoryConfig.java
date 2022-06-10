package org.example.demo.jooq.config;

import org.example.demo.jooq.dao.tables.daos.TPostDao;
import org.example.demo.jooq.dao.tables.daos.TUserDao;
import org.jooq.DSLContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author zuqin.zheng
 */
@Configuration
public class RepositoryConfig {

    @Resource
    private DSLContext dslContext;

    @Bean
    public TUserDao tUserDao() {
        return new TUserDao(dslContext.configuration());
    }

    @Bean
    public TPostDao tPostDao() {
        return new TPostDao(dslContext.configuration());
    }
}
