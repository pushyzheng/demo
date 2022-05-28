package org.example.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.example.kafka.common.Constants;

import java.util.Properties;

public class KafkaCustomPartitionerExample {

    private static KafkaProducer<String, String> producer;

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, Constants.BROKER_SERVER_ADDRESS);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, Constants.STRING_SERIALIZER);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, Constants.STRING_SERIALIZER);

        // 使用自定义的分区
        props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, MyPartitioner.class.getName());
        producer = new KafkaProducer<>(props);

        send();
    }

    private static void send() {
        // 这样在发送指定 key 时, 就能固定发送到指定的分区了
        ProducerRecord<String, String> record =
                new ProducerRecord<>(Constants.SIMPLE_TOPIC, "test", "Hello World");
        producer.send(record);
    }
}
