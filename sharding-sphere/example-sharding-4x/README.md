## 环境准备

创建多库:

```sql
CREATE DATABASE db0 CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
CREATE DATABASE db1 CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
```

创建多表, 执行 flyway:

```shell
$ mvn flyway:migrate
```

生成 Jooq 代码:

```shell
$ mvn jooq-codegen:generate
```

## 配置说明

### 区分数据源

在 [JooqConfig](src/main/java/org/example/demo/sharding/config/JooqConfig.java) 中配置了两个 DataSource/DSLContext 以及对应的事务管理器.

用于区分 "自动分片" 和 "不分片" 的数据源, 因为可能实际的业务场景下, 会使用到公共的一些库和表, 所以需要使用 "不分片" 的数据源.

### ShardingSphere

在 [application.yml](src/main/resources/application.yml) 的 shardingsphere 键中完成所有的分库分表策略的配置.

- t_user: 根据 id 来分库
- t_order: 根据 user_id 来分库, 再根据 order_num 来分表
- t_post: 分库, 使用 Hint 分片算法

## 参考资料

[ShardingSphere 4.x SPRING BOOT配置](https://shardingsphere.apache.org/document/legacy/4.x/document/cn/manual/sharding-jdbc/configuration/config-spring-boot/)

[sharding-jdbc-test](https://github.com/qimok/sharding-jdbc-test)

[sharding-jdbc 分库分表的 4种分片策略，还蛮简单的](https://segmentfault.com/a/1190000037706070)
