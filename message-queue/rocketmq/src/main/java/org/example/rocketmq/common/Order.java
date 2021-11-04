package org.example.rocketmq.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Order {

    private long id;

    private long datetime;
}
