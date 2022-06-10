package org.example.demo.sharding.dao;

import org.example.demo.sharding.dao.tables.TCommonConfig;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author zuqin.zheng
 */
@Repository
public class CommonConfigDao {

    @Resource
    private DSLContext dslContext;

    public boolean save(String content) {
        return dslContext.insertInto(TCommonConfig.T_COMMON_CONFIG)
                .set(TCommonConfig.T_COMMON_CONFIG.CONTENT, content)
                .execute() > 0;
    }
}
