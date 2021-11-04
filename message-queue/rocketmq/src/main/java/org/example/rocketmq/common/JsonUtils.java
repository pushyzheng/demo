package org.example.rocketmq.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;

import java.util.Optional;

public class JsonUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static String toJson(Object obj) {
        return Try.of(() -> mapper.writeValueAsString(obj))
                .getOrElse("");
    }

    public static <T> Optional<T> parse(String s, Class<T> orderClass) {
        return Try.of(() -> mapper.readValue(s, orderClass))
                .map(Optional::ofNullable)
                .recover(throwable -> Optional.empty())
                .get();
    }
}
