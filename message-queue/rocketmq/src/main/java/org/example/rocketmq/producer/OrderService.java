package org.example.rocketmq.producer;

import lombok.extern.slf4j.Slf4j;
import org.example.rocketmq.common.JsonUtils;
import org.example.rocketmq.common.Order;

@Slf4j
public class OrderService {

    /**
     * 模拟执行本地下单, 需要支持事务.
     */
    public void placeOrder(Order order) {
        log.info("place order, orderNum: {}", JsonUtils.toJson(order));
    }

    /**
     * 模拟查单
     */
    public Order queryOrder(long orderNum) {
        log.info("query order, orderNum: {}", orderNum);
        return new Order(orderNum, System.currentTimeMillis());
    }
}
