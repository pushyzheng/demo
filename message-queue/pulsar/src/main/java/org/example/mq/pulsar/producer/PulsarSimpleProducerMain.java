package org.example.mq.pulsar.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.MessageId;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.example.mq.pulsar.common.BasePulsarMain;
import org.example.mq.pulsar.common.Constants;

import java.nio.charset.StandardCharsets;

@Slf4j
public class PulsarSimpleProducerMain extends BasePulsarMain {
    public static void main(String[] args) throws Exception {
        PulsarClient client = createSimpleClient();

        Producer<byte[]> producer = client.newProducer()
                .topic(Constants.SIMPLE_TOPIC)
                .create();
        String message = "Hello World";

        sendSync(producer, message);
        sendAsync(producer, message);
    }

    private static void sendSync(Producer<byte[]> producer, String message) {
        try {
            MessageId messageId = producer.send(message.getBytes(StandardCharsets.UTF_8));
            log.info("succeed, messageId: {}", messageId);
        } catch (PulsarClientException e) {
            log.error("send error", e);
        }
    }

    private static void sendAsync(Producer<byte[]> producer, String message) {
        producer.sendAsync(message.getBytes(StandardCharsets.UTF_8))
                .thenAccept(messageId -> {
                    log.info("send successfully, meessageId: {}", messageId);
                });
    }
}
