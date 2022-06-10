package org.example.demo.jooq.service;

import lombok.extern.slf4j.Slf4j;
import org.example.demo.jooq.utils.Jsons;
import org.example.demo.jooq.model.dto.PostDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;

import java.util.List;

/**
 * @author zuqin.zheng
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
@Slf4j
class PostServiceImplTest {

    @Resource
    private PostService postService;

    @Test
    void savePost() {
        boolean result = postService.savePost(new PostDTO()
                .setCategory(new PostDTO.Category().setName("tech").setDesc("技术"))
                .setTags(List.of("java", "frame")));
        Assertions.assertTrue(result);

        result = postService.savePost(new PostDTO()
                .setCategory(new PostDTO.Category().setName("share").setDesc("分享"))
                .setTags(List.of("app")));
        Assertions.assertTrue(result);
    }

    @Test
    public void queryPost() {
        List<PostDTO> result = postService.getPostsByCategory("tech");
        log.info("queryPost, result: {}", Jsons.toJson(result));
        Assertions.assertTrue(result.size() != 0);
    }

    @Test
    public void updateCategory() {
        Assertions.assertTrue(postService.updateCategory(1, "share"));
    }

    @Test
    public void addTag() {
        Assertions.assertTrue(postService.addTag(2, "python"));
    }
}
