package com.example.elasticsearch.spring.controller;

import com.example.elasticsearch.spring.bean.User;
import com.example.elasticsearch.spring.repository.UserRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UserController {

    @Resource
    private UserRepository userRepository;

    @RequestMapping("/users/{name}")
    public User getUserByName(@PathVariable String name) {
        return userRepository.findByUsername(name);
    }
}
