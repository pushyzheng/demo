package com.example.micrometer;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Tags;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zuqin.zheng
 */
@RestController
public class IndexController {

    @Resource
    private MeterRegistry meterRegistry;

    @GetMapping("/")
    public String index(@RequestParam String type) {
        long start = System.currentTimeMillis();

        // 使用 Metrics 静态方法来记录
        Metrics.counter("controller.index.all").increment();
        Metrics.counter("controller.index", Tags.of("type", type))
                .increment();

        // 使用 MeterRegistry 来记录
        meterRegistry
                .summary("controller.index.cost", Tags.of("type", type))
                .record(System.currentTimeMillis() - start);

        for (int i = 0; i < 30; i++) {
            ExecutorServiceHolder.getCommon().execute(() -> System.out.println("running"));
        }
        return "Hello world";
    }
}
