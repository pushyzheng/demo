package org.example.demo.sharding.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserDTO {

    private long id;

    private String username;

    private String password;
}
