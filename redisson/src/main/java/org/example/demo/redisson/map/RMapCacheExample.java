package org.example.demo.redisson.map;

import org.example.demo.redisson.Redissons;
import org.redisson.api.RMapCache;

import java.util.concurrent.TimeUnit;

public class RMapCacheExample {

    public static void main(String[] args) {
        Redissons.executeThenDestroy(client -> {
            RMapCache<Object, Object> cache = client.getMapCache("map-cache");
            cache.put("a", "1", 10, TimeUnit.SECONDS);

            System.out.println(cache.get("a"));
        });
    }
}
