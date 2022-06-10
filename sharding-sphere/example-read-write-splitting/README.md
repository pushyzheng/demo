# 工程简介

使用 ShardingSphere 来实现读写分离, 在 [application.yml](src/main/resources/application.yml) 中配置三个 DataSource, 同时将划分主库和从库分别对应的数据源:

```yaml
masterslave:
  name: ms
  # 主库
  master-data-source-name: master
  # 从库
  slave-data-source-names: slave0,slave1
```

然后[配置相应的 DataSource](src/main/java/org/example/example/rws/config/JooqConfig.java) 即可

使用见: [ExampleReadWriteSplittingApplicationTests](src/test/java/org/example/example/rws/ExampleReadWriteSplittingApplicationTests.java)

# 延伸阅读

[shardingsphere - 读写分离](https://shardingsphere.apache.org/document/legacy/4.x/document/cn/manual/sharding-jdbc/usage/read-write-splitting/)
