package com.example.elasticsearch.spring.repository;

import com.example.elasticsearch.spring.bean.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends ElasticsearchRepository<User, Long> {

    User findByUsername(String username);
}
