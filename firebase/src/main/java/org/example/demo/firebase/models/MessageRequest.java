package org.example.demo.firebase.models;

import com.google.firebase.messaging.Notification;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * @author zuqin.zheng
 */
@Data
@Builder
public class MessageRequest {

    private String token;

    private Notification notification;

    private Map<String, String> data;
}
