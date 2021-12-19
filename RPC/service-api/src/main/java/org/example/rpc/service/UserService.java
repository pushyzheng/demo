package org.example.rpc.service;

import org.example.rpc.bean.User;

public interface UserService {

    boolean saveUser(User user);

    User getUserById(String id);
}
