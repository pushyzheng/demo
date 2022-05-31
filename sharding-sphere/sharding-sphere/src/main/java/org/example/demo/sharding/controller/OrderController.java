package org.example.demo.sharding.controller;

import org.example.demo.sharding.dao.OrderDao;
import org.example.demo.sharding.dao.UserDao;
import org.example.demo.sharding.model.dto.OrderDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class OrderController {

    @Resource
    private OrderDao orderDao;

    @GetMapping("/orders")
    public OrderDTO getOrder(@RequestParam long orderNum) {
        return orderDao.getByOrderNum(orderNum);
    }

    @PostMapping("/orders")
    public OrderDTO createOrder(@RequestBody OrderDTO orderDTO) {
        orderDao.saveOrder(orderDTO);
        return orderDTO;
    }
}
