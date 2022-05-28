package com.example.elasticsearch.normal;

import com.example.elasticsearch.common.FakerUtils;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Slf4j
public class CreateDocument {
    public static void main(String[] args) throws Exception {
        long id = new Random().nextLong();

        Map<String, Object> userSource = new HashMap<>();
        userSource.put("id", id);
        userSource.put("username", FakerUtils.getFakerName());
        userSource.put("age", new Random().nextInt(100));

        log.info("source: {}", userSource);

        IndexRequest request = new IndexRequest("users")
                .id(String.valueOf(id))
                .source(userSource);

        IndexResponse response = ClientHolder.REST_HIGH_LEVEL_CLIENT.index(request, RequestOptions.DEFAULT);
        log.info("result: {}", response);
    }
}
