package org.example.demo.sharding.dao.sharding;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;

import java.util.Collection;

/**
 * 取模算法: 分片键 % 表数量
 * <p>
 * 5.x 之后, 其实内部自己已经有是实现了:
 *
 * @see org.apache.shardingsphere.sharding.algorithm.sharding.mod.HashModShardingAlgorithm
 */
@Slf4j
public abstract class BaseModulusPreciseShardingAlgorithm implements StandardShardingAlgorithm<Long> {

    @Override
    public String doSharding(Collection<String> availableTargetNames,
                             PreciseShardingValue<Long> shardingValue) {
        for (String dbName : availableTargetNames) {
            long shardId = shardingValue.getValue() % availableTargetNames.size();
            if (dbName.endsWith(String.valueOf(shardId))) {
                log.info("{} shardingValue({}) route to '{}'", getType(), shardId, dbName);
                return dbName;
            }
        }
        throw new IllegalArgumentException();
    }

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames,
                                         RangeShardingValue<Long> rangeShardingValue) {
        return availableTargetNames;
    }

    @Override
    public void init() {

    }

    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
}
