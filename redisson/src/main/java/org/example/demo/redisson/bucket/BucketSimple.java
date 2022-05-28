package org.example.demo.redisson.bucket;

import org.example.demo.redisson.Redissons;
import org.example.demo.redisson.model.Person;
import org.redisson.api.RBucket;

public class BucketSimple {

    public static final String BUCKET_PERSON = "bucket-person";

    public static void main(String[] args) {
        // set
        Redissons.execute(client -> {
            RBucket<Person> bucket = client.getBucket(BUCKET_PERSON);
            bucket.set(new Person("Lindsey", 21));
        });

        // get
        Redissons.executeThenDestroy(client -> {
            RBucket<Person> bucket = client.getBucket(BUCKET_PERSON);

            System.out.println("size: " + bucket.size());
            System.out.println("person: " + bucket.get());
        });
    }
}
