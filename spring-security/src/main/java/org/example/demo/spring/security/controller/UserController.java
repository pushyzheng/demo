package org.example.demo.spring.security.controller;

import org.example.demo.spring.security.model.UserDO;
import org.example.demo.spring.security.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 受保护的资源
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("{id}")
    public UserDO getUserById(@PathVariable long id) {
        return userService.getUserByID(id);
    }
}
