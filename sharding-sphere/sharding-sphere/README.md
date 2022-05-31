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

## 参考资料

[ShardingSphere 4.x SPRING BOOT配置](https://shardingsphere.apache.org/document/legacy/4.x/document/cn/manual/sharding-jdbc/configuration/config-spring-boot/)

[sharding-jdbc-test](https://github.com/qimok/sharding-jdbc-test)

[sharding-jdbc 分库分表的 4种分片策略，还蛮简单的](https://segmentfault.com/a/1190000037706070)
