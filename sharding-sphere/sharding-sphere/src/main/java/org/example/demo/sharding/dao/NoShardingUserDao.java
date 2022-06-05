package org.example.demo.sharding.dao;

import org.example.demo.sharding.dao.tables.TUser;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * 不执行分片
 */
@Repository
public class NoShardingUserDao extends BaseDao {

    private static final TUser TABLE = Tables.T_USER;

    public boolean saveUser(String name, String pwd) {
        return getNoShardingDslCtx()
                .insertInto(TABLE)
                .set(TABLE.USERNAME, name)
                .set(TABLE.PASSWORD, pwd)
                .execute() > 0;
    }
}
