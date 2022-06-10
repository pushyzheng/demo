package org.example.demo.jooq.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author zuqin.zheng
 */
@Data
@Accessors(chain = true)
public class PostDTO {

    private int id;

    private Category category;

    private List<String> tags;

    @Data
    @Accessors(chain = true)
    public static class Category {

        private String name;

        private String desc;
    }
}
