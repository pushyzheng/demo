package org.example.rpc.dubbo.provider.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.constants.ClusterRules;
import org.apache.dubbo.config.annotation.DubboService;
import org.example.rpc.service.LogService;
import org.springframework.util.StringUtils;

@DubboService(
        cluster = ClusterRules.FAIL_SAFE
)
@Slf4j
public class LogServiceImpl implements LogService {

    @Override
    public void recordOne(String content) {
        log.info("recordOne, content is : {}", content);
        if (!StringUtils.hasText(content)) {
            throw new RuntimeException("The content cannot be emtpy");
        }
        doRecord(content);
    }

    private void doRecord(String content) {
        System.out.println(content);
    }
}
