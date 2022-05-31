package org.example.demo.redisson.other;

import org.example.demo.redisson.Redissons;
import org.example.demo.redisson.model.Message;
import org.redisson.api.RStream;

public class RStreamExample {
    public static void main(String[] args) {
        Redissons.execute(c -> {
            RStream<String, Message> stream = c.getStream("stream");
        });
    }
}
