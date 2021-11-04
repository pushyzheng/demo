package org.example.rabbitmq.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BaseRabbitMQProducer {

    protected static final String QUEUE_NAME = "hello";

    /**
     * 默认的交换机为空字符串
     */
    protected static final String DEFAULT_EXCHANGE = "";

    protected void run() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            doPublish(channel, "Hello World");
        } catch (Exception e) {
            log.error("error: ", e);
        }
    }

    protected abstract void doPublish(Channel channel, String message) throws Exception;
}
