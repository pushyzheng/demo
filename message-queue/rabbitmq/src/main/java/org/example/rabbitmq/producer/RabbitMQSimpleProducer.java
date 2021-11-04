package org.example.rabbitmq.producer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RabbitMQSimpleProducer extends BaseRabbitMQProducer {

    public static void main(String[] args) {
        new RabbitMQSimpleProducer().run();
    }

    @Override
    protected void doPublish(Channel channel, String message) throws Exception {
        channel.queueDeclare(
                QUEUE_NAME,          // 队列名
                false,       // 是否持久化
                false,      // 是否独占
                false,     // 自动删除
                null       // 其他参数
        );
        channel.basicPublish(
                DEFAULT_EXCHANGE,    // 交换机类型
                QUEUE_NAME,          // 队列名
                null,
                message.getBytes()   // 消息实体
        );
        log.info("send message: {}", message);
    }
}
