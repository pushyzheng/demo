package org.example.demo.sharding.dao.sharding;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.hint.HintShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.hint.HintShardingValue;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 假设 `t_post` 表没有一些业务字段作为分片键(如这里的 userId), 用来分库
 * <p>
 * 那么我就可以使用这种 {@link HintShardingAlgorithm} 来实现
 * <p>
 * 即在 {@link org.example.demo.sharding.controller.PostController#saveHint(long)} 中设置 hint 值, 然后在这里读出即可
 */
@Slf4j
public class PostDatabaseHintShardingAlgorithm implements HintShardingAlgorithm<Long> {

    @Override
    public Collection<String> doSharding(Collection<String> dbNames,
                                         HintShardingValue<Long> hintValue) {
        if (CollectionUtils.isEmpty(hintValue.getValues())) {
            throw new IllegalArgumentException();
        }
        long hint = hintValue.getValues().iterator().next();
        long shardId = hint % dbNames.size();
        List<String> result = dbNames.stream()
                .filter(s -> s.endsWith(String.valueOf(shardId)))
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(result)) {
            throw new RuntimeException("The routing database is null");
        }
        log.info("HintValue({}) route to '{}' database", hint, result);
        return result;
    }
}
