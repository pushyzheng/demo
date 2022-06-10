CREATE TABLE `t_order`
(
    `id`        bigint NOT NULL AUTO_INCREMENT,
    `order_num` bigint NOT NULL,
    `user_id`   bigint NOT NULL,
    `channel`   varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
    `timestamp` datetime                                                     DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

CREATE TABLE `t_order_0`
(
    `id`        bigint NOT NULL AUTO_INCREMENT,
    `order_num` bigint NOT NULL,
    `user_id`   bigint NOT NULL,
    `channel`   varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
    `timestamp` datetime                                                     DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

CREATE TABLE `t_order_1`
(
    `id`        bigint NOT NULL AUTO_INCREMENT,
    `order_num` bigint NOT NULL,
    `user_id`   bigint NOT NULL,
    `channel`   varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
    `timestamp` datetime                                                     DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

CREATE TABLE `t_order_2`
(
    `id`        bigint NOT NULL AUTO_INCREMENT,
    `order_num` bigint NOT NULL,
    `user_id`   bigint NOT NULL,
    `channel`   varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
    `timestamp` datetime                                                     DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;
