package org.example.demo.jooq.service.impl;

import org.example.demo.jooq.service.PostService;
import org.example.demo.jooq.utils.Jsons;
import org.example.demo.jooq.dao.tables.TPost;
import org.example.demo.jooq.model.dto.PostDTO;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.JSON;
import org.jooq.Record;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zuqin.zheng
 */
@SuppressWarnings("resource")
@Service
public class PostServiceImpl implements PostService {

    @Resource
    private DSLContext dslContext;

    @Override
    public boolean savePost(PostDTO postDTO) {
        return dslContext.insertInto(TPost.T_POST)
                .set(TPost.T_POST.CATEGORY, JSON.json(Jsons.toJson(postDTO.getCategory())))
                .set(TPost.T_POST.TAGS, JSON.json(Jsons.toJson(postDTO.getTags())))
                .execute() > 0;
    }

    @Override
    public List<PostDTO> getPostsByCategory(String category) {
        Condition jsonCondition = DSL.jsonValue(TPost.T_POST.CATEGORY, "$.name")
                .eq(JSON.json(category));

        return dslContext.select()
                .from(TPost.T_POST)
                .where(jsonCondition)
                .fetch()
                .stream()
                .map(this::convertDTO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean updateCategory(int id, String category) {
        return dslContext.execute("update t_post " +
                "set category = JSON_REPLACE(category, '$.name', '" + category + "') " +
                "where id = " + id + ";")
                > 0;
    }

    @Override
    public boolean addTag(int id, String tag) {
        return dslContext.execute("update t_post " +
                "set tags = JSON_ARRAY_APPEND(tags, '$', '" + tag + "') " +
                "where id = " + id + ";")
                > 0;
    }

    private PostDTO convertDTO(Record r) {
        return new PostDTO()
                .setId(r.get(TPost.T_POST.ID))
                .setCategory(Jsons.parse(r.get(TPost.T_POST.CATEGORY).data(), PostDTO.Category.class));
    }
}
