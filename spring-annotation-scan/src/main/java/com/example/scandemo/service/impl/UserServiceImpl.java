package com.example.scandemo.service.impl;

import com.example.scandemo.annotation.Service;
import com.example.scandemo.pojo.User;
import com.example.scandemo.service.UserService;

/**
 * @author Pushy
 * @since 2018/12/3 9:47
 */
@Service(UserService.class)
public class UserServiceImpl implements UserService {

    @Override
    public User getUserById(String id) {
        return null;
    }
}
