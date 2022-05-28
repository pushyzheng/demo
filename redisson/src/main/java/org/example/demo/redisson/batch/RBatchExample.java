package org.example.demo.redisson.batch;

import org.example.demo.redisson.Redissons;
import org.redisson.api.RBatch;
import org.redisson.api.RMapAsync;
import org.redisson.api.RSetAsync;
import org.redisson.client.codec.IntegerCodec;

public class RBatchExample {
    public static void main(String[] args) {
        Redissons.executeThenDestroy(client -> {
            RBatch batch = client.createBatch();

            // map
            RMapAsync<Object, Object> map = batch.getMap("map-batch");
            map.fastPutAsync("1", "a");
            map.fastPutAsync("2", "b");
            map.fastPutAsync("3", "c");

            // set
            RSetAsync<Object> set = batch.getSet("set-batch", IntegerCodec.INSTANCE);
            for (int i = 0; i < 10; i++) {
                set.addAsync(i);
            }

            // execute by pipeline
            batch.execute();
            System.out.println("execute finished");
        });
    }
}
