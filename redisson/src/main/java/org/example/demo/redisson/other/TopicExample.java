package org.example.demo.redisson.other;

import lombok.extern.slf4j.Slf4j;
import org.example.demo.redisson.Redissons;
import org.example.demo.redisson.Threads;
import org.example.demo.redisson.model.Message;
import org.redisson.api.RTopic;

/**
 * 通过 {@link RTopic} 使用 Redis 来实现一个简单的队列功能
 */
@Slf4j
public class TopicExample {

    public static final String QUEUE_NAME = "queue";

    public static void main(String[] args) {
        Redissons.execute(c -> {
            RTopic queue = c.getTopic(QUEUE_NAME);

            queue.addListener(Message.class, TopicExample::consumer);

            startProducer(queue);
        });
    }

    private static void consumer(CharSequence chan, Message msg) {
        log.info("receive message from '{}': {}", chan, msg);
    }

    private static void startProducer(RTopic queue) {
        Threads.run(() -> {
            queue.publish(new Message("Hello World"));
            log.info("publish message");
        }, 100);
    }
}
