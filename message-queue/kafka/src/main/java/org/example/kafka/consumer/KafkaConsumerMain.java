package org.example.kafka.consumer;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.example.kafka.common.Constants;

import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

@Slf4j
public class KafkaConsumerMain {

    public static void main(final String[] args) {
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(buildProperties());
        consumer.subscribe(Lists.newArrayList(Constants.SIMPLE_TOPIC));

        // 需要循环地进行拉取, poll 是一个阻塞操作, 需要设置最大的超时时间
        // noinspection InfiniteLoopStatement
        for (; ; ) {
            // 拉取消息
            ConsumerRecords<String, String> record = consumer.poll(Duration.ofMillis(1000));
            log.info("pull records, count: {}", record.count());

            record.forEach(message -> log.info("onMessage, topic: {}, content: {}",
                    message.topic(), message.value()));

            // 提交偏移量
            consumer.commitSync();
        }
    }

    private static Properties buildProperties() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, Constants.BROKER_SERVER_ADDRESS);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "group1");

        // 开启消费者自动提交偏移量配置, 这样每过五秒中就会自动将 poll 接收到最大偏移量提交上去
        // 但是使用自动提交之后, 会出现重复消息的问题(提交的偏移量 < 处理的偏移量)
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, TimeUnit.SECONDS.toMillis(3));
        return props;
    }
}
