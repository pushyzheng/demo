package org.example.rpc.dubbo.consumer.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class APIResponse {

    private int status;

    private String message;

    private Object data;

    public static APIResponse success(Object data) {
        return new APIResponse(0, "", data);
    }

    public static APIResponse error(String message) {
        return new APIResponse(1, message, null);
    }
}
