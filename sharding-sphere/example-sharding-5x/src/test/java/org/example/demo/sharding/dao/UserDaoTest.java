package org.example.demo.sharding.dao;

import com.github.javafaker.Faker;
import org.example.demo.sharding.model.dto.UserDTO;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
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
class UserDaoTest {

    @Resource
    private UserDao userDao;

    private final Faker faker = new Faker();

    @RepeatedTest(value = 3)
    void saveUser() {
        userDao.saveUser(faker.name().name(),
                faker.phoneNumber().phoneNumber());
    }

    @Test
    void getUserById() {
        UserDTO user = userDao.getUserById(742065081616957441L);
        System.out.println(user);
    }
}
