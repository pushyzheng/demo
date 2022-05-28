package org.example.demo.redisson.reactor;

import lombok.extern.slf4j.Slf4j;
import org.example.demo.redisson.Redissons;
import org.redisson.api.RMapReactive;

@Slf4j
public class ReactorExample {

    public static void main(String[] args) {
        RMapReactive<String, Integer> map = Redissons.getReactive().getMap("map");

        map.get("a")
                .map(s -> s * s)
                .doOnSuccess(result -> log.info("result is {}", result))
                .subscribe();

        Redissons.destroy();
    }
}
