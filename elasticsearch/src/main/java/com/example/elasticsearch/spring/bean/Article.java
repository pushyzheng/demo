package com.example.elasticsearch.spring.bean;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.core.Range;

@Data
@Document(indexName = "articles")
public class Article {

    @Id
    private Long id;

    @Field(type = FieldType.Keyword)
    public String title;

    @Field(type = FieldType.Keyword)
    public String content;

    @Field(type = FieldType.Date)
    public Long date;
}
