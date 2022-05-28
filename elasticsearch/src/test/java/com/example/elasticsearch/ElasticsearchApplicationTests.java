package com.example.elasticsearch;

import com.example.elasticsearch.common.FakerUtils;
import com.example.elasticsearch.spring.ElasticsearchApplication;
import com.example.elasticsearch.spring.bean.Article;
import com.example.elasticsearch.spring.bean.User;
import com.example.elasticsearch.spring.repository.ArticleRepository;
import com.example.elasticsearch.spring.repository.UserRepository;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Random;

@SpringBootTest(classes = ElasticsearchApplication.class)
@Slf4j
class ElasticsearchApplicationTests {

    @Resource
    private UserRepository userRepository;
    @Resource
    private ArticleRepository articleRepository;

    @Test
    void testAddUser() {
        User user = new User();
        user.setId(new Random().nextLong());
        user.setUsername(FakerUtils.getFakerName());
        user.setAge(20);
        userRepository.save(user);
        log.info("save successfully");
    }

    @Test
    void testAddArticles() {
        Faker faker = new Faker();

        Article article = new Article();
        article.setId(new Random().nextLong());
        article.setTitle(faker.address().fullAddress());
        article.setContent(faker.file().fileName());
        article.setDate(System.currentTimeMillis());

        articleRepository.save(article);
    }

    @Test
    public void batchAdd() {
        for (int i = 0; i < 10; i++) {
            testAddArticles();
        }
    }
}
