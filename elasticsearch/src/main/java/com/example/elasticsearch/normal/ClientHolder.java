package com.example.elasticsearch.normal;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;

@Slf4j
public class ClientHolder {

    public static final RestHighLevelClient REST_HIGH_LEVEL_CLIENT;

    static {
        REST_HIGH_LEVEL_CLIENT = RestClients
                .create(ClientConfiguration.localhost())
                .rest();
    }
}
