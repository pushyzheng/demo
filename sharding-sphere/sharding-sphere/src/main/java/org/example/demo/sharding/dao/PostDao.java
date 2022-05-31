package org.example.demo.sharding.dao;

import org.example.demo.sharding.dao.tables.TPost;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class PostDao {

    @Resource(name = "shardingDsl")
    private DSLContext dslContext;

    private static final TPost TABLE = Tables.T_POST;

    public boolean savePost(String title, String body) {
        return dslContext.insertInto(TABLE)
                .set(TABLE.TITLE, title)
                .set(TABLE.BODY, body)
                .execute() > 0;
    }
}
