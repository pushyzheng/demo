package com.example.mq.pulsar.consumer;

import com.example.mq.pulsar.common.MyMsg;
import io.github.majusko.pulsar.annotation.PulsarConsumer;
import org.springframework.stereotype.Service;

@Service
public class MyConsumer {

    @PulsarConsumer(
            topic = "my-topic",
            clazz = MyMsg.class
    )
    public void onMessage(MyMsg myMsg) {
        System.out.println(myMsg);
    }
}
