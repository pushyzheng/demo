package org.example.rpc.dubbo.consumer.stub;

import com.alibaba.fastjson.JSON;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.example.rpc.bean.User;
import org.example.rpc.service.UserService;

/**
 * UserService 的客户端存根, 类似于代理商对象
 * <p>
 * 可以在调用的前后做一些 AOP 的操作
 */
@Slf4j
public class UserServiceStub implements UserService {

    private final UserService userService;

    // 构造函数传入真正的远程代理对象
    public UserServiceStub(UserService barService) {
        this.userService = barService;
    }

    /**
     * 例如: 对异常做降级, 直接返回 false 的状态
     */
    @Override
    public boolean saveUser(User user) {
        long start = System.currentTimeMillis();
        return Try.of(() -> userService.saveUser(user))
                .onSuccess(r -> log.info("调用远程接口保存用户信息成功, cost: {}ms",
                        System.currentTimeMillis() - start))
                .onFailure(throwable -> log.error("调用远程接口保存用户信息异常, user: {}",
                        JSON.toJSONString(user), throwable))
                .recover(throwable -> false)
                .get();
    }

    @Override
    public User getUserById(String id) {
        log.info("getUserById");
        return userService.getUserById(id);
    }
}
