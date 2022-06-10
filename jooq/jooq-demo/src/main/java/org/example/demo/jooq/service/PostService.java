package org.example.demo.jooq.service;

import org.example.demo.jooq.model.dto.PostDTO;

import java.util.List;

/**
 * @author zuqin.zheng
 */
public interface PostService {

    boolean savePost(PostDTO postDTO);

    /**
     * 查询 JSON 的某个字段
     */
    List<PostDTO> getPostsByCategory(String category);

    boolean updateCategory(int id, String category);

    boolean addTag(int id, String tag);
}
