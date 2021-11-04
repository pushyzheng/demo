package org.example.rabbitmq.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RabbitMQReliableConsumer extends BaseRabbitMQConsumer {

    public static void main(String[] args) throws Exception {
        RabbitMQReliableConsumer consumer = new RabbitMQReliableConsumer();
        consumer.run();
    }

    @Override
    protected void doConsume(Channel channel) throws Exception {
        channel.basicConsume(
                QUEUE_NAME,
                // 取消自动 ACK
                false,
                // 处理业务
                (tag, message) -> {
                    doSomething(new String(message.getBody()));
                    // 手动 ACK
                    long deliveryTag = message.getEnvelope().getDeliveryTag();
                    channel.basicAck(deliveryTag, false);
                },
                // 消息被取消的回调
                (tag) -> {
                    log.info("message is canceled, tag: {}", tag);
                }
        );
    }

    private void doSomething(String message) {
        log.info("do something, message: {}", message);
    }
}
