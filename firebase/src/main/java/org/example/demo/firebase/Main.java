package org.example.demo.firebase;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Notification;
import lombok.extern.slf4j.Slf4j;
import org.example.demo.firebase.models.MessageRequest;
import org.example.demo.firebase.service.MessageSender;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zuqin.zheng
 */
@Slf4j
public class Main {

    private static final String TOPIC = "example-topic";

    public static void main(String[] args) throws IOException {
        List<String> tokens = getTokens();

        tokens.forEach(t -> {
            try {
                sendMessage(t);
            } catch (FirebaseMessagingException e) {
                log.error("sendMessage error, token: {}", t, e);
            }
        });
//        TopicSubscriber.subscribe(TOPIC, tokens);
    }

    private static void sendMessage(String token) throws FirebaseMessagingException {
        MessageSender.sendSingle(MessageRequest.builder()
                .token(token)
                .notification(Notification.builder()
                        .setTitle("这是标题")
                        .setBody("这是内容")
                        .build())
                .build());

        MessageSender.sendSingle(MessageRequest.builder()
                .token(token)
                .data(Map.of("content", "Hello World"))
                .build());

        MessageSender.sendToTopic(TOPIC, MessageRequest.builder()
                .notification(Notification.builder().setTitle("Hello topic").build())
                .build());
    }

    private static List<String> getTokens() throws IOException {
        return Files.readAllLines(Path.of("/Users/pushyzheng/Documents/registration-token.txt"))
                .stream()
                .filter(s -> !s.isBlank())
                .collect(Collectors.toList());
    }
}
