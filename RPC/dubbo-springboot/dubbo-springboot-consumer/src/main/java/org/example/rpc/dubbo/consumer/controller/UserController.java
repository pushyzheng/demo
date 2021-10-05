package org.example.rpc.dubbo.consumer.controller;

import org.apache.dubbo.common.constants.ClusterRules;
import org.apache.dubbo.config.annotation.DubboReference;
import org.example.rpc.service.LogService;
import org.example.rpc.service.User;
import org.example.rpc.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

//    @DubboReference(
//            version = "v0.0.1",
//            url = "dubbo://127.0.0.1:12345",
//            timeout = 100
//    )
//    private UserService userService;

    @DubboReference(
            version = "v0.0.1"
    )
    private UserService userService;

    @DubboReference(
            cluster = ClusterRules.FAIL_SAFE
    )
    private LogService logService;

    @PostMapping("")
    public User saveUser(@RequestParam String name, @RequestParam int age) {
        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .name(name)
                .age(age)
                .build();
        userService.saveUser(user);
        return null;
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable String id) {
        return userService.getUserById(id);
    }
}
