package org.example.demo.jooq.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;

/**
 * @author zuqin.zheng
 */
@UtilityClass
public class Jsons {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public String toJson(Object obj) {
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("toJson error", e);
        }
    }

    public <T> T parse(String s, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(s, clazz);
        } catch (Exception e) {
            throw new RuntimeException("parse error, s: " + s, e);
        }
    }
}
