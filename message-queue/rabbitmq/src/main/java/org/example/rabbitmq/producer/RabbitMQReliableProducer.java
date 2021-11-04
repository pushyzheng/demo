package org.example.rabbitmq.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * 可靠的 Producer, 通过 confirm 模式来实现
 * 在消息发送之前先持久化到本地的数据库, 然后再 confirm ACK 之后再删除
 * 保证消息在 producer 端不丢失
 */
@Slf4j
public class RabbitMQReliableProducer extends RabbitMQSimpleProducer {

    private static CountDownLatch countDownLatch;

    public static void main(String[] args) {
        new RabbitMQReliableProducer().run();
    }

    @Override
    protected void doPublish(Channel channel, String message) throws Exception {
        addConfirmListener(channel);

        int count = 10;
        countDownLatch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            String actualMessage = message + "-" + i;
            saveMessage(actualMessage, channel.getNextPublishSeqNo());
            super.doPublish(channel, actualMessage);
        }
        countDownLatch.await();
    }

    private static void addConfirmListener(Channel channel) throws Exception {
        channel.confirmSelect();
        channel.addConfirmListener(confirmListener);
    }

    private static void saveMessage(String message, long deliverTag) {
        log.info("save message to local, deliverTag: {}, message: {}",
                deliverTag, message);
    }

    private static void deleteMessage(long deliverTag) {
        log.info("delete message, deliverTag: {}", deliverTag);
    }

    /**
     * 监听消息 ACK 或 NACK 的回调
     */
    private static final ConfirmListener confirmListener = new ConfirmListener() {

        @Override
        public void handleAck(long deliveryTag, boolean multiple) throws IOException {
            log.info("The message ACK, tag: {}", deliveryTag);
            deleteMessage(deliveryTag);
            countDownLatch.countDown();
        }

        @Override
        public void handleNack(long deliveryTag, boolean multiple) throws IOException {
            log.info("The message NACK, tag: {}", deliveryTag);
        }
    };
}
