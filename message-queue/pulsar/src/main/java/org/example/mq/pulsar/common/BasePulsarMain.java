package org.example.mq.pulsar.common;

import org.apache.pulsar.client.api.PulsarClient;

public class BasePulsarMain {

    public static PulsarClient createSimpleClient() throws Exception {
        return PulsarClient.builder()
                .serviceUrl(Constants.BROKER_ADDRESS)
                .build();
    }
}
