CREATE TABLE `t_post`
(
    `id`       int NOT NULL AUTO_INCREMENT,
    `category` json DEFAULT NULL,
    `tags`     json DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
