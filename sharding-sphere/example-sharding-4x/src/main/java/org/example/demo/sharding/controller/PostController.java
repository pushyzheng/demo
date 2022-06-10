package org.example.demo.sharding.controller;

import org.apache.shardingsphere.api.hint.HintManager;
import org.example.demo.sharding.dao.PostDao;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class PostController {

    @Resource
    private PostDao postDao;

    @PostMapping("/posts")
    public boolean savePost(@RequestParam long userId,
                            @RequestParam String title,
                            @RequestParam String content) {
        saveHint(userId);
        return postDao.savePost(title, content);
    }

    public void saveHint(long userId) {
        HintManager hintManager = HintManager.getInstance();
        hintManager.addDatabaseShardingValue("t_post", userId);
    }
}
