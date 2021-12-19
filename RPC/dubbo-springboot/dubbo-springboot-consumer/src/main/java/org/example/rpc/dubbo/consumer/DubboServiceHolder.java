package org.example.rpc.dubbo.consumer;

import lombok.Getter;
import org.apache.dubbo.common.constants.ClusterRules;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.Method;
import org.example.rpc.service.AsyncService;
import org.example.rpc.service.LogService;
import org.example.rpc.service.ResourceService;
import org.example.rpc.service.UserService;
import org.springframework.stereotype.Component;

@Component
@Getter
public class DubboServiceHolder {

    //    @DubboReference(
//            version = "v0.0.1",
//            url = "dubbo://127.0.0.1:12345",
//            timeout = 100
//    )
//    private UserService userService;

    @DubboReference(
            version = "v0.0.1",
            stub = "org.example.rpc.dubbo.consumer.stub.UserServiceStub",
            mock = "org.example.rpc.dubbo.consumer.mock.UserServiceMock"
    )
    private UserService userService;

    @DubboReference(
            cluster = ClusterRules.FAIL_SAFE
    )
    private LogService logService;

    @DubboReference(
            timeout = 5000,
            methods = {
                    // 配置异步
                    @Method(name = "syncHello", async = true)
            }
    )
    private AsyncService asyncService;
}
