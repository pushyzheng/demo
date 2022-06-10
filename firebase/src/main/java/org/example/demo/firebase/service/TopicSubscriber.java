package org.example.demo.firebase.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.TopicManagementResponse;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.example.demo.firebase.FirebaseAppRegistry;

import java.util.List;

@UtilityClass
@Slf4j
public class TopicSubscriber {

    /**
     * 订阅主题
     */
    public void subscribe(String topic, List<String> tokens) throws FirebaseMessagingException {
        TopicManagementResponse response = FirebaseMessaging.getInstance(FirebaseAppRegistry.getDefault())
                .subscribeToTopic(tokens, topic);
        log.info("subscribe to {}, tokens: {}, failCount: {}", topic, tokens, response.getFailureCount());
    }

    /**
     * 取消订阅主题
     */
    public void unsubscribe(String topic, List<String> tokens) throws FirebaseMessagingException {
        TopicManagementResponse response = FirebaseMessaging.getInstance(FirebaseAppRegistry.getDefault())
                .unsubscribeFromTopic(tokens, topic);
        log.info("unsubscribe to {}, tokens: {}, failCount: {}", topic, tokens, response.getFailureCount());
    }
}
