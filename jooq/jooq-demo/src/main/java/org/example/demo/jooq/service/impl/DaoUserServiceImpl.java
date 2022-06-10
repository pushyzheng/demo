package org.example.demo.jooq.service.impl;

import org.example.demo.jooq.dao.tables.daos.TUserDao;
import org.example.demo.jooq.dao.tables.pojos.TUser;
import org.example.demo.jooq.model.dto.UserDTO;
import org.example.demo.jooq.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 使用生成的 DAO 类 {@link TUserDao} 来实现
 * <p>
 * 比较方便
 *
 * @author zuqin.zheng
 */
@Service("dao")
public class DaoUserServiceImpl implements UserService {

    @Resource
    private TUserDao userDao;

    @Override
    public boolean upsert(UserDTO userDTO) {
        throw new UnsupportedOperationException();
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        TUser tUser = new TUser();
        tUser.setUsername(userDTO.getUsername());
        tUser.setPassword(userDTO.getPassword());
        tUser.setGender(userDTO.getGender());
        userDao.insert(tUser);
        return userDTO;
    }

    @Override
    public UserDTO getUserById(long id) {
        TUser tUser = userDao.fetchOneById(id);
        UserDTO result = new UserDTO();
        result.setUsername(tUser.getUsername());
        result.setPassword(tUser.getPassword());
        result.setGender(tUser.getGender());
        return result;
    }

    @Override
    public List<UserDTO> listById(List<Long> idList) {
        throw new UnsupportedOperationException();
    }
}
