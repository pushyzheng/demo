package org.example.demo.jooq.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class UserDTO {

    private long id;

    private String username;

    private String password;

    private String gender;

    private LocalDateTime timestamp;
}
