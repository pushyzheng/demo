package org.example.rpc.service;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class User implements Serializable {

    private String id;

    private String name;

    private int age;
}
