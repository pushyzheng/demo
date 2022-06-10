package org.example.demo.sharding.dao;

import org.example.demo.sharding.model.dto.OrderDTO;
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
class OrderDaoTest {

    public static final long ORDER_NUM = 101982912912L;

    @Resource
    private OrderDao orderDao;

    @Test
    void saveOrder() {
        OrderDTO orderDTO = new OrderDTO()
                .setUserId(742065081616957441L)
                .setOrderNum(ORDER_NUM)
                .setChannel("direct");
        orderDao.saveOrder(orderDTO);
    }

    @Test
    void getByOrderNum() {
        OrderDTO orderDTO = orderDao.getByOrderNum(ORDER_NUM);
        System.out.println(orderDTO);
    }
}
