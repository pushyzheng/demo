package org.example.demo.redisson.map;

import lombok.extern.slf4j.Slf4j;
import org.example.demo.redisson.Redissons;
import org.redisson.api.RMap;

@Slf4j
public class RMapExample {

    public static void main(String[] args) {
        Redissons.executeThenDestroy(client -> {
            RMap<String, Integer> map = client.getMap("map");

            log.info("a = {}", map.get("a"));
            map.put("a", 1);
            log.info("a = {}", map.get("a"));

            if (map.containsKey("a")) {
                System.out.println("contains a");
            }

            // 异步的方式
            map.getAsync("a").thenAccept(result -> log.info("async a={}", result));
        });
    }
}
