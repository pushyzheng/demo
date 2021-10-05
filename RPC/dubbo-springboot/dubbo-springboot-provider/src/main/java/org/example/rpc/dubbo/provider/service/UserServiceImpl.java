package org.example.rpc.dubbo.provider.service;

import com.google.common.collect.Maps;
import org.apache.dubbo.config.annotation.DubboService;
import org.example.rpc.service.User;
import org.example.rpc.service.UserService;

import java.util.Map;

@DubboService(
        version = "v0.0.1"
)
public class UserServiceImpl implements UserService {

    private final Map<String, User> database = Maps.newConcurrentMap();

    @Override
    public boolean saveUser(User user) {
        database.put(user.getId(), user);
        return true;
    }

    @Override
    public User getUserById(String id) {
        return database.get(id);
    }
}
