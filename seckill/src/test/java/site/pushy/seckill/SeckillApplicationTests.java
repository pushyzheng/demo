package site.pushy.seckill;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SeckillApplicationTests {

	@Autowired
	private RedisTemplate<String, Serializable> redisTemplate;

	@Resource(name = "semaphoreScript")
	private DefaultRedisScript<Number> semaphoreScript;

	@Test
	public void contextLoads() {
		String identifier = UUID.randomUUID().toString();
		String key = "good";
		long now = System.currentTimeMillis();

		List<String> keys = Arrays.asList(identifier, key);
		Number result = redisTemplate.execute(semaphoreScript, keys, now, 1000, 10);
		System.out.println(result);
	}

}
