package org.example.demo.redisson.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Message implements Serializable {
    private static final long serialVersionUID = -519544943887943770L;

    private String content;
}
