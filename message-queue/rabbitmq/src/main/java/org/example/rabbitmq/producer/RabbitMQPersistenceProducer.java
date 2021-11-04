package org.example.rabbitmq.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * 将消息持久化到 Broker, 防止消息在 Broker 端丢失.
 */
@Slf4j
public class RabbitMQPersistenceProducer extends BaseRabbitMQProducer {

    public static void main(String[] args) {
        RabbitMQPersistenceProducer producer = new RabbitMQPersistenceProducer();
        producer.run();
    }

    @Override
    protected void doPublish(Channel channel, String message) throws Exception {
        channel.basicPublish(
                DEFAULT_EXCHANGE,
                QUEUE_NAME,
                // 持久化
                MessageProperties.PERSISTENT_TEXT_PLAIN,
                message.getBytes(StandardCharsets.UTF_8)
        );
    }
}
