package com.example.mq.pulsar.producer;

import com.example.mq.pulsar.common.MyMsg;
import io.github.majusko.pulsar.producer.PulsarTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MyProducer {

    @Resource
    private PulsarTemplate<MyMsg> producer;

    public void send() throws Exception {
        producer.send("my-topic", new MyMsg("Hello world!"));
    }
}
