package org.example.demo.spring.security.service;

import org.example.demo.spring.security.model.UserDO;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public UserDO getUserByID(long id) {
        return new UserDO()
                .setId(id);
    }
}
