package org.example.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class JsonUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static String toJson(Object obj) {
        return Try.of(() -> mapper.writeValueAsString(obj))
                .onFailure(throwable -> log.error("toJson error", throwable))
                .getOrElse("");
    }

    public static <T> Optional<T> parse(String s, Class<T> orderClass) {
        return Try.of(() -> mapper.readValue(s, orderClass))
                .map(Optional::ofNullable)
                .recover(throwable -> Optional.empty())
                .get();
    }
}
