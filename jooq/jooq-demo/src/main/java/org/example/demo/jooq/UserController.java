package org.example.demo.jooq;

import org.example.demo.jooq.dao.tables.daos.TUserDao;
import org.example.demo.jooq.model.dto.UserDTO;
import org.example.demo.jooq.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UserController {

    @Resource
    private ApplicationContext ctx;

    @GetMapping("/users/{id}")
    public UserDTO getUser(@PathVariable Long id,
                           @RequestParam(defaultValue = "underlying") String impl) {
        UserService userService = (UserService) ctx.getBean(impl);
        return userService.getUserById(id);
    }

    @PostMapping("/users")
    public UserDTO saveUser(@RequestBody UserDTO userDTO,
                            @RequestParam(defaultValue = "underlying") String impl) {
        UserService userService = (UserService) ctx.getBean(impl);
        return userService.save(userDTO);
    }
}
