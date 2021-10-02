package org.example.kafka.consumer;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.example.kafka.common.Constants;

import java.time.Duration;
import java.util.Properties;

@Slf4j
public class KafkaConsumerMain {

    private static final int GAP_MILLIS = 1000;

    public static void main(final String[] args) throws InterruptedException {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, Constants.BROKER_SERVER_ADDRESS);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "group1");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Lists.newArrayList(Constants.SIMPLE_TOPIC));

        // 需要循环地进行拉取
        // poll 是一个阻塞操作, 需要设置最大的超时时间
        Thread listenerThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                consumer.poll(Duration.ofMillis(GAP_MILLIS))
                        .forEach(KafkaConsumerMain::onMessage);
                System.out.println("????");
            }
        });

        listenerThread.start();
        listenerThread.join();
    }

    private static void onMessage(ConsumerRecord<String, String> message) {
        String value = message.value();
        log.info("onMessage, value: {}", value);
    }
}
