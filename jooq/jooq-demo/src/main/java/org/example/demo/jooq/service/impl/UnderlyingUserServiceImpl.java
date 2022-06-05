package org.example.demo.jooq.service.impl;

import org.example.demo.jooq.dao.Tables;
import org.example.demo.jooq.dao.tables.TUser;
import org.example.demo.jooq.dao.tables.records.TUserRecord;
import org.example.demo.jooq.model.dto.UserDTO;
import org.example.demo.jooq.service.UserService;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 使用底层的 {@link DSLContext} 来实现
 * <p>
 * 较为繁琐, 但是比较灵活
 */
@SuppressWarnings("resource")
@Service("underlying")
@Primary
public class UnderlyingUserServiceImpl implements UserService {

    @Resource
    private DSLContext dslContext;

    private static final TUser TABLE = TUser.T_USER;

    @Override
    public boolean upsert(UserDTO userDTO) {
        return dslContext.insertInto(TABLE, TABLE.USERNAME, TABLE.PASSWORD, TABLE.GENDER)
                .values(userDTO.getUsername(), userDTO.getPassword(), userDTO.getGender())
                .onDuplicateKeyUpdate()
                // 当唯一约束重复时, 更新 password 和 gender
                .set(TABLE.PASSWORD, userDTO.getPassword())
                .set(TABLE.GENDER, userDTO.getGender())
                .execute() > 0;
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        dslContext.insertInto(TUser.T_USER)
                .set(TABLE.USERNAME, userDTO.getUsername())
                .set(TABLE.PASSWORD, userDTO.getPassword())
                .set(TABLE.GENDER, userDTO.getGender())
                .execute();
        return userDTO;
    }

    @Override
    public UserDTO getUserById(long id) {
        Result<TUserRecord> fetch = dslContext.selectFrom(TUser.T_USER)
                .where(Tables.T_USER.ID.equal(id))
                .fetch();
        return fetch.stream()
                .map(record -> new UserDTO().setId(record.getId())
                        .setUsername(record.getUsername())
                        .setPassword(record.getPassword())
                        .setGender(record.getGender())
                        .setTimestamp(record.getTimestamp()))
                .findFirst()
                .orElse(null);
    }
}
