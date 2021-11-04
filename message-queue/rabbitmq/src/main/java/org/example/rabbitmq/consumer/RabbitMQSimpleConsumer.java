package org.example.rabbitmq.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class RabbitMQSimpleConsumer extends BaseRabbitMQConsumer {

    public static void main(String[] args) {
        RabbitMQSimpleConsumer consumer = new RabbitMQSimpleConsumer();
        consumer.run();
    }

    @Override
    protected void doConsume(Channel channel) throws Exception {
        // 开始消费
        channel.basicConsume(
                // 队列名
                QUEUE_NAME,
                // 自动 ACK
                true,
                // 消息成功投递的回调
                (tag, message) -> {
                    log.info("message is delivered, tag: {}, message: {}", tag, new String(message.getBody()));
                },
                // 消息被取消的回调
                (tag) -> {
                    log.info("message is canceled, tag: {}", tag);
                }
        );
    }
}
