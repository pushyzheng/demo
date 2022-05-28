package org.example.rocketmq.producer;

import io.vavr.control.Try;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.example.common.bean.Order;
import org.example.common.utils.JsonUtils;

import java.util.Optional;

/**
 * 事务监听
 */
public class MyTransactionListener implements TransactionListener {

    private final OrderService orderService = new OrderService();

    /**
     * 执行本地事务
     */
    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object o) {
        Optional<Order> order = JsonUtils.parse(new String(message.getBody()), Order.class);
        if (order.isEmpty()) {
            return LocalTransactionState.UNKNOW;
        }

        return Try.run(() -> orderService.placeOrder(order.get()))
                // 提交事务
                .map(unused -> LocalTransactionState.COMMIT_MESSAGE)
                // 事务执行失败, 返回回滚的状态, 在 broker 则会删除这条半消息
                .getOrElse(LocalTransactionState.ROLLBACK_MESSAGE);
    }

    /**
     * 回查事务的执行结果
     */
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
        Optional<Order> order = JsonUtils.parse(new String(messageExt.getBody()), Order.class);
        if (order.isEmpty()) {
            return LocalTransactionState.UNKNOW;
        }
        Order localOrder = orderService.queryOrder(order.get().getId());

        // 回查阶段, 若本地订单已经存在, 则任务该订单的分布式事务在该系统是执行成功的
        // 返回 COMMIT_MESSAGE 状态, 让 broker 将半消息转换为正常的消息, 发送到 MQ 中给 consumer 消费
        // 否则认为本地事务执行失败, 返回 ROLLBACK_MESSAGE 消息让 broker 删除这条半消息.
        if (localOrder == null) {
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }
        return LocalTransactionState.COMMIT_MESSAGE;
    }
}
