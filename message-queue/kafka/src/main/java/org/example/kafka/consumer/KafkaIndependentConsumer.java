package org.example.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.example.kafka.common.Constants;

import java.time.Duration;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * 独立的消费者
 * <p>
 * 适用于: 一个消费者从一个主题的所有分区或者某个特定的分区读取数据, 这个时候就不需要消费者群组和再均衡
 */
@Slf4j
public class KafkaIndependentConsumer {

    private static KafkaConsumer<String, String> consumer;

    public static void main(String[] args) {
        consumer = new KafkaConsumer<>(buildProperties());
        assignPartitions();

        //noinspection InfiniteLoopStatement
        for (; ; ) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
            records.forEach(message -> {
                log.info("topic = {}, partition = {}, offset = {}, customer = {}, country = {}",
                        message.topic(), message.partition(), message.offset(), message.key(), message.value());
            });
            consumer.commitSync();
        }
    }

    /**
     * 不需要订阅主题, 而是消费者自己分配分区
     * <p>
     * 1. 通过 partitionsFor 获取到主题的所有分区信息
     * 2. 过滤出期望分配的分区
     * 3. 通过 assign 进行分配
     * <p>
     * !!!WARNING!!!: 但是如果新增分区, 此时消费者是感知不到的, 所以需要周期性地执行这个方法或在增加分区后手动调用
     */
    private static void assignPartitions() {
        List<TopicPartition> expectedPartitions = consumer.partitionsFor("kafka.top.independent")
                .stream()
                .filter(KafkaIndependentConsumer::filterPartition)
                .map(partitionInfo -> new TopicPartition(partitionInfo.topic(), partitionInfo.partition()))
                .collect(Collectors.toList());

        consumer.assign(expectedPartitions);
    }

    private static boolean filterPartition(PartitionInfo partitionInfo) {
        return true;
    }

    private static Properties buildProperties() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, Constants.BROKER_SERVER_ADDRESS);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringDeserializer");
        return props;
    }
}
