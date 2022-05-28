package org.example.demo.redisson.bucket;

import org.example.demo.redisson.Redissons;
import org.redisson.api.RBucket;

public class BucketCAS {

    public static void main(String[] args) {
        Redissons.executeThenDestroy(client -> {
            RBucket<Integer> bucket = client.getBucket("bucket-cas");
            bucket.set(1);

            boolean result = bucket.compareAndSet(1, 2); // true
            System.out.println("result: " + result);

            new Thread(() -> bucket.set(3)).start();
            sleep();

            result = bucket.compareAndSet(2, 3); // false
            System.out.println("result: " + result);
        });
    }

    private static void sleep() {
        try {
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
