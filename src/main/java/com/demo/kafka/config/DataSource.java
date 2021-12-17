package com.demo.kafka.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

@ConfigurationProperties
public class DataSource {

    @Value("${spring.datasource.fetchsize}")
    private int fetchsize;

    @Bean
    public DataSourceProperties dataSource() {
        return new DataSourceProperties();
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(
                dataSource().initializeDataSourceBuilder().build());
        jdbcTemplate.setFetchSize(fetchsize);
        return jdbcTemplate;
    }
}
