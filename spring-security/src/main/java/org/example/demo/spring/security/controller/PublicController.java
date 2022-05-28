package org.example.demo.spring.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 公开资源, 不需要认证
 */
@RestController
@RequestMapping("/public")
public class PublicController {

    @GetMapping("")
    public String publicMessage() {
        return "Hello World";
    }
}
