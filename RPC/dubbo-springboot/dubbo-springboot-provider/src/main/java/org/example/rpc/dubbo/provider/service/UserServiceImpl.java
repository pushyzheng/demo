package org.example.rpc.dubbo.provider.service;

import com.google.common.collect.Maps;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.RpcException;
import org.example.rpc.bean.User;
import org.example.rpc.service.UserService;

import java.util.Map;

@DubboService(version = "v0.0.1")
public class UserServiceImpl implements UserService {

    private static final Map<String, User> MOCK_DATABASE = Maps.newConcurrentMap();

    static {
        MOCK_DATABASE.put("1", User.builder().id("1").name("Phillip Weaver").age(20).build());
        MOCK_DATABASE.put("2", User.builder().id("2").name("Lisa Gardner").age(28).build());
    }

    @Override
    public boolean saveUser(User user) {
        MOCK_DATABASE.put(user.getId(), user);
        return true;
    }

    @Override
    public User getUserById(String id) {
        if (!MOCK_DATABASE.containsKey(id)) {
            throw new RpcException("cannot found user, id=" + id);
        }
        return MOCK_DATABASE.get(id);
    }
}
