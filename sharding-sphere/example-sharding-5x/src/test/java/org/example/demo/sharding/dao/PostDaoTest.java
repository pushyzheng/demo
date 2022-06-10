package org.example.demo.sharding.dao;

import com.github.javafaker.Faker;
import org.apache.shardingsphere.infra.hint.HintManager;
import org.example.demo.sharding.ShardingSphereApplication;
import org.example.demo.sharding.dao.tables.TPost;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author zuqin.zheng
 */
@SpringBootTest(classes = ShardingSphereApplication.class)
@ExtendWith(SpringExtension.class)
class PostDaoTest {

    @Resource
    private PostDao postDao;

    private static final Faker FAKER = new Faker();

    @Test
    void savePost() {
        saveHint(12898913L);

        assertTrue(postDao.savePost(FAKER.name().title(), FAKER.address().fullAddress()));
    }

    public void saveHint(long userId) {
        HintManager hintManager = HintManager.getInstance();
        hintManager.addDatabaseShardingValue(TPost.T_POST.getName(),
                userId);
    }
}
