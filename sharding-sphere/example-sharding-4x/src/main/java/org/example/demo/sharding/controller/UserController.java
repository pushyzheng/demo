package org.example.demo.sharding.controller;

import org.example.demo.sharding.dao.UserDao;
import org.example.demo.sharding.model.dto.UserDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UserController {

    @Resource
    private UserDao userDao;

    @GetMapping("/users/{id}")
    public UserDTO getUser(@PathVariable long id) {
        return userDao.getUserById(id);
    }

    @PostMapping("/users")
    public Boolean saveUser(@RequestParam String username,
                            @RequestParam String password) {
        return userDao.saveUser(username, password);
    }
}
