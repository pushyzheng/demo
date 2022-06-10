package org.example.demo.firebase.service;

import com.google.common.collect.Maps;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FcmOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MulticastMessage;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.example.demo.firebase.FirebaseAppRegistry;
import org.example.demo.firebase.models.MessageRequest;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
@Slf4j
public class MessageSender {

    /**
     * 批量发送消息
     */
    public void sendDataBatch(List<MessageRequest> requests) throws FirebaseMessagingException {
        List<Message> messages = requests.stream().map(r -> MessageSender.buildMessage(r).build())
                .collect(Collectors.toList());
        BatchResponse batchResponse = FirebaseMessaging.getInstance(FirebaseAppRegistry.getDefault())
                .sendAll(messages);
    }

    /**
     * 发送单条消息
     */
    public void sendSingle(MessageRequest request) throws FirebaseMessagingException {
        if (request.getNotification() == null && request.getData() == null) {
            throw new IllegalArgumentException();
        }
        Message message = buildMessage(request).build();
        String messageId = FirebaseMessaging.getInstance(FirebaseAppRegistry.getDefault())
                .send(message);
        log.info("send '{}' to {}, messageId: {}", request.getData(), request.getToken(), messageId);
    }

    /**
     * 多播
     */
    public void sendMulticast(List<String> token, MessageRequest request) throws FirebaseMessagingException {
        MulticastMessage message = MulticastMessage.builder()
                .setNotification(request.getNotification())
                .putAllData(request.getData())
                .addAllTokens(token)
                .build();

        BatchResponse batchResponse = FirebaseMessaging.getInstance(FirebaseAppRegistry.getDefault())
                .sendMulticast(message);
    }

    /**
     * 发送消息给某个主题
     */
    public void sendToTopic(String topic, MessageRequest request) throws FirebaseMessagingException {
        Message message = buildMessage(request)
                .setTopic(topic)
                .build();
        String messageId = FirebaseMessaging.getInstance(FirebaseAppRegistry.getDefault())
                .send(message);
        log.info("send '{}' to {}, messageId: {}", request, topic, messageId);
    }

    /**
     * 发送携带 label
     */
    public void sendWithLabel(MessageRequest request, String label) throws FirebaseMessagingException {
        FcmOptions options = FcmOptions.builder()
                .setAnalyticsLabel(label) // 最多 50 个字符
                .build();
        Message message = buildMessage(request)
                .setFcmOptions(options)
                .build();
        String messageId = FirebaseMessaging.getInstance(FirebaseAppRegistry.getDefault())
                .send(message);

        log.info("send '{}' with {} label, messageId: {}", request, label, messageId);
    }

    private Message.Builder buildMessage(MessageRequest request) {
        return Message.builder()
                .setToken(request.getToken())
                .setNotification(request.getNotification())
                .putAllData(request.getData() == null ? Maps.newHashMap() : request.getData());
    }
}
