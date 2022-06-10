package org.example.demo.sharding.dao;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author zuqin.zheng
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
class CommonConfigDaoTest {

    @Resource
    private CommonConfigDao commonConfigDao;

    private final Faker faker = new Faker();

    @RepeatedTest(5)
    void save() {
        commonConfigDao.save(faker.job().title());
    }
}
