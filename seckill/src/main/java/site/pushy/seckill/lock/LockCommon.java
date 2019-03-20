package site.pushy.seckill.lock;

import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;

/**
 * @author Pushy
 * @since 2019/3/5 9:41
 */
@Component
public class LockCommon {

    @Bean(name = "lockReleaseScript")
    public DefaultRedisScript<Number> lockReleaseScript() {
        DefaultRedisScript<Number> script = new DefaultRedisScript<>();
        script.setScriptSource(new ResourceScriptSource(new ClassPathResource("lockReleaseScript.lua")));
        script.setResultType(Number.class);
        return script;
    }

    @Bean(name = "lockAcquireScript")
    public DefaultRedisScript<Number> lockScript() {
        DefaultRedisScript<Number> script = new DefaultRedisScript<>();
        script.setScriptSource(new ResourceScriptSource(new ClassPathResource("lockAcquireScript.lua")));
        script.setResultType(Number.class);
        return script;
    }

}
