package org.example.demo.sharding.dao.sharding;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * t_order 表分表的<b>精准分表算法</b>
 */
@Slf4j
public class OrderTablePreciseShardingAlgorithm implements PreciseShardingAlgorithm<Long> {

    /**
     * 取模算法，分片健 % 表数量
     */
    @Override
    public String doSharding(Collection<String> tableNames, PreciseShardingValue<Long> shardingValue) {
        for (String tableName : tableNames) {
            long shardId = shardingValue.getValue() % tableNames.size();
            if (tableName.endsWith(String.valueOf(shardId))) {
                log.info("shardingValue({}) route to '{}' table", shardId, tableName);
                return tableName;
            }
        }
        throw new IllegalArgumentException();
    }
}
