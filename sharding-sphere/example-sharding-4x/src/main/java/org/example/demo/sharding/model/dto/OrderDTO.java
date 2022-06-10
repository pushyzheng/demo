package org.example.demo.sharding.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class OrderDTO {

    private long id;

    private long orderNum;

    private long userId;

    private String channel;
}
