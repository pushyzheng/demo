package org.example.rpc.dubbo.consumer.mock;

import lombok.extern.slf4j.Slf4j;
import org.example.rpc.bean.User;
import org.example.rpc.service.UserService;
import org.springframework.stereotype.Service;

/**
 * 利用本地伪装实现完整的服务降级
 * <p>
 * 主要的应用场景如服务端宕机不可用时, 客户端对整个服务接口进行降级的操作
 * <p>
 * 需要注意的是正常的业务异常: 如 RuntimeException 是不会走降级策略的.
 * (即 mock 的约定就是只有出现 RpcException 时才执行)
 */
@Service
@Slf4j
public class UserServiceMock implements UserService {

    /**
     * 降级
     */
    @Override
    public boolean saveUser(User user) {
        record();
        return false;
    }

    @Override
    public User getUserById(String id) {
        record();
        return null;
    }

    private void record() {
        log.info("UserService 服务方不可用, 走降级策略");
    }
}
