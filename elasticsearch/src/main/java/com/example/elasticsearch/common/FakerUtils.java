package com.example.elasticsearch.common;

import com.github.javafaker.Faker;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FakerUtils {

    private static final Faker FAKER = new Faker();

    public String getFakerName() {
        return FAKER.funnyName().name();
    }
}
