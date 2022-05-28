package com.example.elasticsearch.normal;

import com.example.elasticsearch.common.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Slf4j
public class QueryDemo {
    public static void main(String[] args) throws Exception {
        // match query
        doQueryArticles(sourceBuilder -> sourceBuilder
                .query(QueryBuilders.termQuery("title", "30107 Domingo Rapid, North Kevaville, MS 22897")));

        // range query
        doQueryArticles(sourceBuilder -> {
            RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("date")
                    .from(1640348636389L)
                    .to(1640348639389L);
            sourceBuilder.query(rangeQuery);
        });
    }

    private static void doQueryUsers(Consumer<SearchSourceBuilder> sourceBuilderConsumer) throws IOException {
        SearchRequest searchRequest = new SearchRequest("users");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilderConsumer.accept(sourceBuilder);

        searchRequest.source(sourceBuilder);
        SearchResponse result = ClientHolder.REST_HIGH_LEVEL_CLIENT
                .search(searchRequest, RequestOptions.DEFAULT);
        log.info("users result: \n{}", convertResponse(result));
    }

    private static void doQueryArticles(Consumer<SearchSourceBuilder> sourceBuilderConsumer) throws IOException {
        SearchRequest searchRequest = new SearchRequest("articles");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilderConsumer.accept(sourceBuilder);

        searchRequest.source(sourceBuilder);
        SearchResponse result = ClientHolder.REST_HIGH_LEVEL_CLIENT
                .search(searchRequest, RequestOptions.DEFAULT);
        log.info("articles result: \n{}", convertResponse(result));
    }

    private static String convertResponse(SearchResponse result) {
        return JsonUtils.pretty(Arrays
                .stream(result.getHits().getHits())
                .map(SearchHit::getSourceAsMap)
                .collect(Collectors.toList()));
    }
}
