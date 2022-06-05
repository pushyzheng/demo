package org.example.demo.sharding.dao;

import org.example.demo.sharding.dao.tables.TPost;
import org.springframework.stereotype.Repository;

@Repository
public class PostDao extends BaseDao {

    private static final TPost TABLE = Tables.T_POST;

    public boolean savePost(String title, String body) {
        return getShardingDslCtx().insertInto(TABLE)
                .set(TABLE.TITLE, title)
                .set(TABLE.BODY, body)
                .execute() > 0;
    }
}
