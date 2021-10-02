package org.example.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.TopicExistsException;
import org.example.kafka.common.Constants;

import java.util.Collections;
import java.util.Optional;
import java.util.Properties;

@Slf4j
public class KafkaSimpleProducerMain {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, Constants.BROKER_SERVER_ADDRESS);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        createTopic(props);

        KafkaProducer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 10; i++) {
            ProducerRecord<String, String> record =
                    new ProducerRecord<>(Constants.SIMPLE_TOPIC, String.format("[%d] Hello World", i));
            producer.send(record, (recordMetadata, e) -> {
                if (e != null) {
                    log.error("cannot send message, record: {}", record, e);
                } else {
                    log.info("succeed, record: {}", recordMetadata);
                }
            });
        }

        producer.flush();
        producer.close();
    }

    private static void createTopic(Properties props) {
        final NewTopic newTopic = new NewTopic(Constants.SIMPLE_TOPIC, Optional.empty(), Optional.empty());
        try (final AdminClient adminClient = AdminClient.create(props)) {
            adminClient.createTopics(Collections.singletonList(newTopic)).all().get();
        } catch (Exception e) {
            // Ignore if TopicExistsException, which may be valid if topic exists
            if (!(e.getCause() instanceof TopicExistsException)) {
                throw new RuntimeException(e);
            }
        }
    }
}
