package org.example.demo.sharding.dao;

import org.jooq.DSLContext;

import javax.annotation.Resource;

/**
 * @author zuqin.zheng
 */
public class BaseDao {

    @Resource(name = "shardingDsl")
    private DSLContext dslCtx;

    @Resource(name = "noShardingDsl")
    private DSLContext noShardingDslCtx;

    /**
     * 会自动分片的 DSLContext
     */
    protected DSLContext getShardingDslCtx() {
        return dslCtx;
    }

    /**
     * 不分片的 DSLContext
     */
    protected DSLContext getNoShardingDslCtx() {
        return noShardingDslCtx;
    }
}
