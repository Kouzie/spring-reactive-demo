package com.example.sharding.sphere.demo.config;

import org.apache.shardingsphere.driver.api.yaml.YamlShardingSphereDataSourceFactory;
import org.apache.shardingsphere.sharding.algorithm.keygen.SnowflakeKeyGenerateAlgorithm;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

@Configuration
public class DbYamlConfig {
    @Bean
    public DataSource dataSource() throws SQLException, IOException {
        // Create ShardingSphereDataSource
        ClassPathResource resource = new ClassPathResource("sharding-datasource.yaml"); // Indicate YAML file
        DataSource dataSource = YamlShardingSphereDataSourceFactory.createDataSource(resource.getFile());
        return dataSource;
    }

    @Bean
    public SnowflakeKeyGenerateAlgorithm snowflakeKeyGenerateAlgorithm() {
        return new SnowflakeKeyGenerateAlgorithm();
    }


}
