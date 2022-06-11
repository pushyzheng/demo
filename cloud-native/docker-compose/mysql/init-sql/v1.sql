CREATE DATABASE demo CHARACTER SET utf8mb4 COLLATE  utf8mb4_unicode_ci;

use demo;

CREATE TABLE `user`
(
    `id`       bigint NOT NULL AUTO_INCREMENT,
    `username` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
    `password` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
    PRIMARY KEY (`id`)
);