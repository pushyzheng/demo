package com.example.elasticsearch;

import com.example.elasticsearch.common.JsonUtils;
import com.example.elasticsearch.spring.ElasticsearchApplication;
import com.example.elasticsearch.spring.bean.Article;
import com.example.elasticsearch.spring.bean.User;
import com.example.elasticsearch.spring.repository.ArticleRepository;
import com.example.elasticsearch.spring.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest(classes = ElasticsearchApplication.class)
@Slf4j
public class QueryTests {

    @Resource
    private UserRepository userRepository;
    @Resource
    private ArticleRepository articleRepository;

    /**
     * 分页查询
     */
    @Test
    public void listUsersByPage() {
        Pageable pagination = Pageable.ofSize(20);

        Page<User> users = userRepository.findAll(pagination);
        log.info("result size: {}, total pages: {}", users.getSize(), users.getTotalPages());
        log.info("result: {}", JsonUtils.toJson(users.get().collect(Collectors.toList())));
    }

    /**
     * 匹配查询
     */
    @Test
    public void queryMatch() {
        User user = userRepository.findByUsername("Theresa Green");
        log.info("find user: {}", JsonUtils.toJson(user));
    }

    @Test
    public void queryMatchBool() {
        List<Article> article = articleRepository
                .findAllByTitleAndContent("Suite", "qui_sed");
        log.info("queryMatchBool: {}", JsonUtils.toJson(article));
    }

    /**
     * 根据 date 字段进行范围查询
     */
    @Test
    public void queryByRange() {
        long start = 1640348636389L;
        long end = 1640348639389L;
        log.info("queryByRange: {}", JsonUtils.toJson(articleRepository.findAllByDateBetween(start, end)));
    }
}
