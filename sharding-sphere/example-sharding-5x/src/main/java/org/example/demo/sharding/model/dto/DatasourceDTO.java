package org.example.demo.sharding.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DatasourceDTO {

    private String jdbcUrl;

    private String username;

    private String password;

    private String driver;
}
