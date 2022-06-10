package org.example.demo.sharding.dao;

import org.example.demo.sharding.dao.tables.TUser;
import org.example.demo.sharding.dao.tables.records.TUserRecord;
import org.example.demo.sharding.model.dto.UserDTO;
import org.jooq.Result;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao extends BaseDao {

    private static final TUser TABLE = Tables.T_USER;

    public boolean saveUser(String name, String pwd) {
        return getShardingDslCtx()
                .insertInto(TABLE)
                .set(TABLE.USERNAME, name)
                .set(TABLE.PASSWORD, pwd)
                .execute() > 0;
    }

    public UserDTO getUserById(long id) {
        Result<TUserRecord> fetch = getShardingDslCtx()
                .selectFrom(TUser.T_USER)
                .where(Tables.T_USER.ID.equal(id))
                .fetch();
        return fetch.stream()
                .map(record -> new UserDTO().setId(record.getId())
                        .setUsername(record.getUsername())
                        .setPassword(record.getPassword()))
                .findFirst()
                .orElse(null);
    }
}
