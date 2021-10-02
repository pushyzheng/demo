package com.example.mq.pulsar.common;

public class MyMsg {

    private final String data;

    public MyMsg(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    @Override
    public String toString() {
        return "MyMsg{" +
                "data='" + data + '\'' +
                '}';
    }
}
