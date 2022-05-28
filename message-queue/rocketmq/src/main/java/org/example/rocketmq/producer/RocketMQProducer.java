package org.example.rocketmq.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.example.common.bean.Order;
import org.example.common.utils.JsonUtils;

import java.nio.charset.StandardCharsets;
import java.util.Random;

@Slf4j
public class RocketMQProducer {
    private static final String BROKER_ADDRESS = "127.0.0.1:9876";
    private static final String ORDER_TOPIC = "topic.rocketmq.order";

    public static void main(String[] args) throws Exception {
        TransactionMQProducer producer = new TransactionMQProducer("group1");
        // 设置事务监听器
        producer.setTransactionListener(new MyTransactionListener());
        producer.setNamesrvAddr(BROKER_ADDRESS);
        producer.start();

        // 构建消息
        Order order = new Order(getOrderNumber(), System.currentTimeMillis());
        Message message = new Message(
                ORDER_TOPIC,
                JsonUtils.toJson(order).getBytes(StandardCharsets.UTF_8)
        );

        // 发送(半)消息
        SendResult sendResult = producer.sendMessageInTransaction(message, null);
        log.info("send message, result: {}", sendResult.getSendStatus());
        producer.shutdown();
    }

    private static long getOrderNumber() {
        return new Random().nextLong();
    }
}
