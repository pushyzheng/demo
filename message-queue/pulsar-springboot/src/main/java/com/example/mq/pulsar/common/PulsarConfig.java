package com.example.mq.pulsar.common;

import io.github.majusko.pulsar.producer.ProducerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PulsarConfig {

    @Bean
    public ProducerFactory producerFactory() {
        return new ProducerFactory()
                .addProducer("my-topic", MyMsg.class);
    }

}
