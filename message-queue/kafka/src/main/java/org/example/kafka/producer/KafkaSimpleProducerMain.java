package org.example.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.errors.TopicExistsException;
import org.example.kafka.common.Constants;

import java.util.Collections;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.Future;

@Slf4j
public class KafkaSimpleProducerMain {

    private static KafkaProducer<String, String> producer;

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, Constants.BROKER_SERVER_ADDRESS);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, Constants.STRING_SERIALIZER);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, Constants.STRING_SERIALIZER);
        // 表示需要多少个 broker 结点收到消息, 才视为成功发送
        props.put(ProducerConfig.ACKS_CONFIG, 1);
        // 发送失败的重试次数
        props.put(ProducerConfig.RETRIES_CONFIG, 3);

        createTopic(props);

        producer = new KafkaProducer<>(props);
        ProducerRecord<String, String> record =
                new ProducerRecord<>(Constants.SIMPLE_TOPIC, "Hello World");

        sendSync(record);
        sendAsync(record);

        producer.flush();
        producer.close();
    }

    /**
     * 同步发送会返回一个 Future, 直接调用 get 即可
     */
    private static void sendSync(ProducerRecord<String, String> record) {
        Future<RecordMetadata> future = producer.send(record);
        try {
            RecordMetadata recordMetadata = future.get();
            log.info("send message successfully, topic: {}, partition: {}",
                    recordMetadata.topic(), recordMetadata.partition());
        } catch (Exception e) {
            log.error("cannot send message, record: {}", record, e);
            e.printStackTrace();
        }
    }

    /**
     * 异步发送则是传入处理完成的回调函数.
     * <p>
     * 成功: e 为空
     * 失败: e 不为空
     */
    private static void sendAsync(ProducerRecord<String, String> record) {
        producer.send(record, (recordMetadata, e) -> {
            if (e != null) {
                log.error("cannot send message, record: {}", record, e);
            } else {
                log.info("send message successfully, topic: {}, partition: {}",
                        recordMetadata.topic(), recordMetadata.partition());
            }
        });
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
