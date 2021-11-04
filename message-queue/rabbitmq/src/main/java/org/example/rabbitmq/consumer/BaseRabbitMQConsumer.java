package org.example.rabbitmq.consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BaseRabbitMQConsumer {

    protected static final String QUEUE_NAME = "hello";

    protected void run() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            doConsume(channel);
        } catch (Exception e) {
            log.error("consume error: ", e);
        }
    }

    protected abstract void doConsume(Channel channel) throws Exception;
}
