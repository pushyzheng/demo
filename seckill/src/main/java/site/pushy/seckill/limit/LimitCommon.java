package site.pushy.seckill.limit;

import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author Pushy
 * @since 2019/3/3 20:44
 */
@Component
public class LimitCommon {

    /**
     * 读取限流的Lua脚本，并创建bean
     */
    @Bean("redisScript")
    public DefaultRedisScript<Number> redisScript() {
        DefaultRedisScript<Number> script = new DefaultRedisScript<>();
        script.setScriptSource(new ResourceScriptSource(new ClassPathResource("rateLimit.lua")));
        script.setResultType(Number.class);
        return script;
    }

    /**
     * 读取信号量Lua脚本
     */
    @Bean("semaphoreScript")
    public DefaultRedisScript<Number> semaphoreScript() {
        DefaultRedisScript<Number> script = new DefaultRedisScript<>();
        script.setScriptSource(new ResourceScriptSource(new ClassPathResource("semaphoreFairScript.lua")));
        script.setResultType(Number.class);
        return script;
    }

    @Bean("semaphoreReleaseScript")
    public DefaultRedisScript<Number> semaphoreReleaseScript() {
        DefaultRedisScript<Number> script = new DefaultRedisScript<>();
        script.setScriptSource(new ResourceScriptSource(new ClassPathResource("semaphoreFairReleaseScript.lua")));
        script.setResultType(Number.class);
        return script;
    }

    /**
     * 配置redisTemplate
     */
    @Bean
    public RedisTemplate<String, Serializable> limitRedisTemplate(LettuceConnectionFactory factory) {
        RedisTemplate<String, Serializable> template = new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setConnectionFactory(factory);
        return template;
    }

}
