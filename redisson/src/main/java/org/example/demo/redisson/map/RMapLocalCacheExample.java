package org.example.demo.redisson.map;

import lombok.extern.slf4j.Slf4j;
import org.example.demo.redisson.Redissons;
import org.redisson.api.LocalCachedMapOptions;
import org.redisson.api.RLocalCachedMap;

/**
 * 带本地缓存的 Map
 * <p>
 * 适用于 QPS 较高的场景
 */
@Slf4j
public class RMapLocalCacheExample {
    public static void main(String[] args) {
        Redissons.execute(client -> {
            LocalCachedMapOptions<String, Integer> defaults = LocalCachedMapOptions.defaults();
            defaults.syncStrategy(LocalCachedMapOptions.SyncStrategy.NONE);

            RLocalCachedMap<String, Integer> localCache =
                    client.getLocalCachedMap("local-cache-map", defaults);

            localCache.get("a");

            log.info("cacheMap: {}", localCache.getCachedMap());

            localCache.put("a", 2);

            log.info("cacheMap: {}", localCache.getCachedMap());

            localCache.remove("a");

            log.info("cacheMap: {}", localCache.getCachedMap());
        });
    }
}
