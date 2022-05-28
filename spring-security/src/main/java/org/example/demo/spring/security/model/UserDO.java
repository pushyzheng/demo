package org.example.demo.spring.security.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserDO {

    private Long id;

    private String name;

    private String password;
}
