package org.example.demo.jooq.service;

import org.example.demo.jooq.model.dto.UserDTO;

/**
 * @author zuqin.zheng
 */
public interface UserService {

    /**
     * 处理主键冲突的情况
     */
    boolean upsert(UserDTO userDTO);

    UserDTO save(UserDTO userDTO);

    UserDTO getUserById(long id);
}
