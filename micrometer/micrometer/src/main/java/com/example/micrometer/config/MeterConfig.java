package com.example.micrometer.config;

import com.example.micrometer.ExecutorServiceHolder;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.binder.jvm.ExecutorServiceMetrics;
import io.micrometer.core.instrument.config.MeterFilter;
import io.micrometer.core.instrument.config.MeterFilterReply;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author zuqin.zheng
 */
@Configuration
public class MeterConfig {

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Resource
    private MeterRegistry meterRegistry;

    /**
     * 可以在这里设置一些公共的 Tag
     */
    @PostConstruct
    public void init() throws Exception {
        setCommonTags();
        setFilter();
    }

    /**
     * 监控线程池
     */
    @Bean
    public ExecutorServiceMetrics executorServiceMetrics() {
        return new ExecutorServiceMetrics(
                ExecutorServiceHolder.getCommon(), "common",
                Tags.of("name", "common"));
    }

    private void setCommonTags() throws UnknownHostException {
        meterRegistry.config().commonTags("hostname", InetAddress.getLocalHost().getHostName());
    }

    private void setFilter() {
        meterRegistry.config().meterFilter(testMetersFilter);
    }

    /**
     * 过滤掉测试的指标
     */
    private final MeterFilter testMetersFilter = new MeterFilter() {
        @Override
        public MeterFilterReply accept(Meter.Id id) {
            if (!activeProfile.equals("test") && id.getName().startsWith(("test"))) {
                return MeterFilterReply.DENY;
            }
            return MeterFilterReply.ACCEPT;
        }
    };
}
