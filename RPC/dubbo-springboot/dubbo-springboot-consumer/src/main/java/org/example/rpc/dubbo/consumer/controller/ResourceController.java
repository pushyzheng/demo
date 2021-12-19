package org.example.rpc.dubbo.consumer.controller;

import com.google.common.collect.Maps;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.Method;
import org.example.rpc.dubbo.consumer.JoinerMerger;
import org.example.rpc.service.ResourceService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/resources")
public class ResourceController {

    @DubboReference(
            group = "beta"
    )
    private ResourceService betaResourceService;

    @DubboReference(
            group = "prod"
    )
    private ResourceService prodResourceService;

    /**
     * 任意组
     */
    @DubboReference(
            group = "*"
    )
    private ResourceService anyResourceService;

    /**
     * 搜索并调用所有的分组, 然后将结果聚合返回
     * <p>
     * 这里只针对方法进行配置, 其他方法不聚合
     */
    @DubboReference(
            group = "*",
            methods = {
                    // merger 用于指定合并策略，缺省根据返回值类型自动匹配，如这里是返回值是 List，相当于两个列表合并。
                    // 如果同一类型有两个合并器时，需指定合并器的名称。
                    @Method(name = "getHostNameList", merger = "true"),

                    // 自定义的 Merger, 通过 SPI 进行加载
                    // 见: src/main/resources/META-INF/dubbo/org.apache.dubbo.rpc.cluster.Merger
                    @Method(name = "getName", merger = "joinerMerger")
            }
    )
    private ResourceService mergeResourceService;

    @GetMapping("")
    public String getResourceName(String name) {
        ResourceService resourceService;
        if (!StringUtils.hasLength(name)) {
            resourceService = anyResourceService;
        } else if (name.equals("prod")) {
            resourceService = prodResourceService;
        } else {
            resourceService = betaResourceService;
        }
        return resourceService.getName();
    }

    @GetMapping("/merger")
    public Map<String, Object> getMergeResult() {
        Map<String, Object> result = Maps.newHashMap();
        result.put("hostNameList", mergeResourceService.getHostNameList());
        result.put("names", JoinerMerger.INSTANCE.split(mergeResourceService.getName()));
        return result;
    }
}
