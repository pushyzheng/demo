package org.example.demo.jooq.service;

import org.example.demo.jooq.dao.Tables;
import org.example.demo.jooq.dao.tables.TUser;
import org.example.demo.jooq.dao.tables.records.TUserRecord;
import org.example.demo.jooq.model.dto.UserDTO;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {

    @Resource
    private DSLContext dslContext;

    public UserDTO save(UserDTO userDTO) {
        dslContext.insertInto(TUser.T_USER)
                .set(TUser.T_USER.USERNAME, userDTO.getUsername())
                .set(TUser.T_USER.PASSWORD, userDTO.getPassword())
                .set(TUser.T_USER.GENDER, userDTO.getGender())
                .execute();
        return userDTO;
    }

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
