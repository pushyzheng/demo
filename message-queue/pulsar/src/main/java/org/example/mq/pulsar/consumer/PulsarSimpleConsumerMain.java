package org.example.mq.pulsar.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.Messages;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.SubscriptionType;
import org.example.mq.pulsar.common.BasePulsarMain;
import org.example.mq.pulsar.common.Constants;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
public class PulsarSimpleConsumerMain extends BasePulsarMain {
    public static void main(String[] args) throws Exception {
        PulsarClient client = createSimpleClient();

        Consumer<byte[]> consumer = client.newConsumer()
                .topic(Constants.SIMPLE_TOPIC)
                .subscriptionName("my-subscription")
                .ackTimeout(10, TimeUnit.SECONDS)
                .subscriptionType(SubscriptionType.Exclusive)
                .subscribe();

        syncReceived(consumer);
//        asyncReceived(consumer);
//        batchReceived(consumer);
    }

    private static void syncReceived(Consumer<byte[]> consumer) throws Exception {
        Message<byte[]> message = consumer.receive();
        onMessage(message);
    }

    private static void asyncReceived(Consumer<byte[]> consumer) {
        CompletableFuture<Message<byte[]>> future = consumer.receiveAsync();

        // just for test
        Message<byte[]> message = future.join();
        onMessage(message);
    }

    private static void batchReceived(Consumer<byte[]> consumer) throws Exception {
        Messages<byte[]> messages = consumer.batchReceive();
        messages.forEach(PulsarSimpleConsumerMain::onMessage);
    }

    private static void batchReceivedAsync(Consumer<byte[]> consumer) throws Exception {
        CompletableFuture<Messages<byte[]>> future = consumer.batchReceiveAsync();
        future.join().forEach(PulsarSimpleConsumerMain::onMessage);
    }

    private static void onMessage(Message<byte[]> message) {
        log.info("received, message: {}", new String(message.getData()));
    }
}
