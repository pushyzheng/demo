package com.example.elasticsearch.spring.repository;

import com.example.elasticsearch.spring.bean.Article;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends ElasticsearchRepository<Article, Long> {

    List<Article> findAllByDateBetween(long from, long to);

    List<Article> findAllByTitleAndContent(String title, String content);
}
