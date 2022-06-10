package org.example.demo.jooq.service;

import org.example.demo.jooq.model.dto.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author zuqin.zheng
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
class UserServiceTest {

    @Resource
    private ApplicationContext ctx;

    private static final String IMPL = "underlying";

    @Test
    void upsert() {
        boolean upsert = getService(IMPL).upsert(new UserDTO()
                .setUsername("Michelle")
                .setPassword("123")
                .setGender("male")
                .setTimestamp(LocalDateTime.now()));

        Assertions.assertTrue(upsert);
    }

    @Test
    void save() {
        // test duplicate key
        Assertions.assertThrows(DuplicateKeyException.class, () -> {
            getService(IMPL).save(new UserDTO()
                    .setId(1)
                    .setUsername("Michelle")
                    .setPassword("123")
                    .setGender("male")
                    .setTimestamp(LocalDateTime.now()));
        });
    }

    @Test
    void getUserById() {
        UserDTO user = getService(IMPL).getUserById(1);
        Assertions.assertNotNull(user);
    }

    @Test
    void testListUser() {
        List<UserDTO> result = getService(IMPL).listById(List.of(1L, 2L, 3L));
        System.out.println(result);
    }

    private UserService getService(String impl) {
        return (UserService) ctx.getBean(impl);
    }
}
