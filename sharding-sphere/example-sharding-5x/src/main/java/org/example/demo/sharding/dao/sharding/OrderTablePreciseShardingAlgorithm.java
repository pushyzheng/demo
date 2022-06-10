package org.example.demo.sharding.dao.sharding;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * t_order 表分表的<b>精准分表算法</b>
 */
@Slf4j
@Component
public class OrderTablePreciseShardingAlgorithm extends BaseModulusPreciseShardingAlgorithm {
}
