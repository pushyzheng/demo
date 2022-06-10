package org.example.demo.sharding.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import lombok.experimental.UtilityClass;
import org.apache.shardingsphere.sharding.spi.ShardingAlgorithm;

import java.io.IOException;

@UtilityClass
public class Jsons {

    private static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(ShardingAlgorithm.class, new ShardingAlgorithmSerializer());
        OBJECT_MAPPER.registerModule(module);
    }

    public String toJson(Object obj) {
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * The serializer of {@link ShardingAlgorithm}
     */
    private static class ShardingAlgorithmSerializer extends StdSerializer<ShardingAlgorithm> {
        private static final long serialVersionUID = 174185609190749290L;

        protected ShardingAlgorithmSerializer() {
            this(null);
        }

        public ShardingAlgorithmSerializer(Class<ShardingAlgorithm> t) {
            super(t);
        }

        @Override
        public void serialize(ShardingAlgorithm preciseShardingAlgorithm,
                              JsonGenerator jsonGenerator,
                              SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("clazz", preciseShardingAlgorithm.getClass().getName());
            jsonGenerator.writeEndObject();
        }
    }
}
