package org.example.demo.sharding.controller;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.shardingsphere.shardingjdbc.jdbc.core.datasource.ShardingDataSource;
import org.example.demo.sharding.model.dto.DatasourceDTO;
import org.example.demo.sharding.utils.Jsons;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/sharding-sphere")
public class ShadingSphereController {

    @Resource
    private ShardingDataSource shardingDataSource;

    @GetMapping("/datasource")
    public Map<String, DatasourceDTO> getDatasourceMap() {
        Map<String, DatasourceDTO> result = new HashMap<>();
        shardingDataSource.getDataSourceMap().forEach((name, ds) -> {
            if (!(ds instanceof HikariDataSource)) {
                throw new UnsupportedOperationException();
            }
            HikariDataSource hds = (HikariDataSource) ds;
            result.put(name, new DatasourceDTO().setJdbcUrl(hds.getJdbcUrl())
                    .setUsername(hds.getUsername())
                    .setPassword(hds.getPassword())
                    .setDriver(hds.getDriverClassName()));
        });
        return result;
    }

    @GetMapping(value = "/rule", produces = "application/json")
    public String getConfig() {
        return Jsons.toJson(shardingDataSource.getRuntimeContext().getRule());
    }
}
