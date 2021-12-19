package org.example.rpc.dubbo.provider.service;

import com.google.common.collect.Lists;
import org.apache.dubbo.config.annotation.DubboService;
import org.example.rpc.service.ResourceService;

import java.util.List;

/**
 * 通过 group 来区分接口的不同实现
 * <p>
 * 但往往也用来作为测试环境/生产环境的区分方式
 */
@DubboService(
        group = "prod"
)
public class ProdResourceService implements ResourceService {

    @Override
    public String getName() {
        return "prod";
    }

    @Override
    public List<String> getHostNameList() {
        return Lists.newArrayList("pushyzheng.com");
    }
}
