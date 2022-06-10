## 环境准备

创建多库:

```sql
CREATE DATABASE db0 CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
CREATE DATABASE db1 CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
CREATE DATABASE db2 CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
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

在 4.x 中通过两个数据源 Bean 来区分分片和不分片的 DSLContext.

其实并不需要, 只需要一个即可, 在 ShardingSphere 的规则中配置是否需要分片即可.

### ShardingSphere

在 [application.yml](src/main/resources/application.yml) 的 shardingsphere 键中完成所有的分库分表策略的配置.

- t_common_config: 不分库, 也不分表
- t_user: 根据 id 来分库
- t_order: 根据 user_id 来分库, 再根据 order_num 来分表
- t_post: 分库, 使用 Hint 分片算法, 根据 user_id 来分库

## 参考资料

[shardingsphere](https://shardingsphere.apache.org/document/current/cn/overview/)

[sharding-jdbc-test](https://github.com/qimok/sharding-jdbc-test)

[sharding-jdbc 分库分表的 4种分片策略，还蛮简单的](https://segmentfault.com/a/1190000037706070)
