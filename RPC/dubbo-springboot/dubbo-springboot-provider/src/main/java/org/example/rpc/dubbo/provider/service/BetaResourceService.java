package org.example.rpc.dubbo.provider.service;

import com.google.common.collect.Lists;
import org.apache.dubbo.config.annotation.DubboService;
import org.example.rpc.service.ResourceService;

import java.util.List;

@DubboService(
        group = "beta"
)
public class BetaResourceService implements ResourceService {

    @Override
    public String getName() {
        return "beta";
    }

    @Override
    public List<String> getHostNameList() {
        return Lists.newArrayList("demo.beta.com");
    }
}
