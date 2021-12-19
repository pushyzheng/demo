package org.example.rpc.dubbo.consumer.controller;

import org.example.rpc.dubbo.consumer.DubboServiceHolder;
import org.example.rpc.dubbo.consumer.pojo.APIResponse;
import org.example.rpc.bean.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Resource
    private DubboServiceHolder dubboServiceHolder;

    @PostMapping("")
    public APIResponse saveUser(@RequestParam String name, @RequestParam int age) {
        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .name(name)
                .age(age)
                .build();
        dubboServiceHolder.getUserService().saveUser(user);
        return APIResponse.success(user);
    }

    @GetMapping("/{id}")
    public APIResponse getUser(@PathVariable String id) {
        User user = dubboServiceHolder.getUserService().getUserById(id);
        if (user == null) {
            return APIResponse.error("cannot found");
        }
        return APIResponse.success(user);
    }
}
