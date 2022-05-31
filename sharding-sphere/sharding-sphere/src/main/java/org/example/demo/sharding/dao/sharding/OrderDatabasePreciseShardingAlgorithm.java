package org.example.demo.sharding.dao.sharding;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * t_order 表分库的<b>精准分表算法</b>
 */
@Slf4j
public class OrderDatabasePreciseShardingAlgorithm extends BaseModulusPreciseShardingAlgorithm {
}
