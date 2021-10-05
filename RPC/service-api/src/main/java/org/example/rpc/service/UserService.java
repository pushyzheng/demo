package org.example.rpc.service;

public interface UserService {

    boolean saveUser(User user);

    User getUserById(String id);
}
