package org.example.demo.mockito;

import org.example.demo.mockito.models.User;
import org.example.demo.mockito.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zuqin.zheng
 */
class MainTest {

    @Test
    void test1() {
        List mockedList = Mockito.mock(List.class);
        Assertions.assertTrue(mockedList instanceof List);

        // mock 方法不仅可以 Mock 接口类, 还可以 Mock 具体的类型.
        ArrayList mockedArrayList = Mockito.mock(ArrayList.class);
        Assertions.assertTrue(mockedArrayList instanceof List);
        Assertions.assertTrue(mockedArrayList instanceof ArrayList);
    }

    @Test
    void test2() {
        UserService userService = Mockito.mock(UserService.class);

        Mockito.when(userService.getUserById(1)).thenReturn(new User(1, "Port Peter"));
        Mockito.when(userService.getUserById(2)).thenReturn(new User(2, "Mark Mouth"));

        User user = userService.getUserById(1);
        Assertions.assertNotNull(user);
        Assertions.assertEquals(1, user.getId());
        Assertions.assertEquals("Port Peter", user.getName());

        user = userService.getUserById(2);
        Assertions.assertNotNull(user);
        Assertions.assertEquals(2, user.getId());
        Assertions.assertEquals("Mark Mouth", user.getName());
    }

    @Test
    void test3() {
        Iterator iterator = Mockito.mock(Iterator.class);

        Mockito.when(iterator.next())
                .thenReturn(3)  // 第一次调用, 返回的值
                .thenReturn(4)  // 第二次调用, 返回的值
                .thenReturn(5); // 第三次调用, 返回的值

        Assertions.assertEquals(3, iterator.next());
        Assertions.assertEquals(4, iterator.next());
        Assertions.assertEquals(5, iterator.next());

        Assertions.assertEquals(5, iterator.next());
    }
}
