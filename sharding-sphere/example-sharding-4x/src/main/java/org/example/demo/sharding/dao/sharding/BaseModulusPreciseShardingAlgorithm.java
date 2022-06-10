package org.example.demo.sharding.dao.sharding;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * 取模算法: 分片键 % 表数量
 */
@Slf4j
public class BaseModulusPreciseShardingAlgorithm implements PreciseShardingAlgorithm<Long> {

    @Override
    public String doSharding(Collection<String> dbNames, PreciseShardingValue<Long> shardingValue) {
        for (String dbName : dbNames) {
            long shardId = shardingValue.getValue() % dbNames.size();
            if (dbName.endsWith(String.valueOf(shardId))) {
                log.info("shardingValue({}) route to '{}' database", shardId, dbName);
                return dbName;
            }
        }
        throw new IllegalArgumentException();
    }
}
