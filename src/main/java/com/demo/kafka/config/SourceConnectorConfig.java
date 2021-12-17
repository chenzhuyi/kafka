package com.demo.kafka.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SourceConnectorConfig {

    @Value("${kafka.connector.name}")
    public String connectorname;
    @Value("${kafka.connector.tasks.max}")
    public int maxTasks;
    @Value("${kafka.connector.topic.prefix}")
    public String topicPrefix;
    @Value("${kafka.connector.url}")
    public String url;
    @Value("${kafka.connector.user}")
    public String user;
    @Value("${kafka.connector.password}")
    public String password;

}
