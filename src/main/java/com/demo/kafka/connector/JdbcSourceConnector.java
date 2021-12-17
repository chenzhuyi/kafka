package com.demo.kafka.connector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.source.SourceConnector;
import org.springframework.beans.factory.annotation.Autowired;

import com.demo.kafka.config.SourceConnectorConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JdbcSourceConnector extends SourceConnector {

    @Autowired
    private SourceConnectorConfig sourceConfig;

    @Override
    public String version() {
        return "version 0";
    }

    @Override
    public void start(Map<String, String> props) {
        log.info("!!!! Start JdbcSourceConnector !!!!");
    }

    @Override
    public Class<? extends Task> taskClass() {
        return JdbcSourceTask.class;
    }

    @Override
    public List<Map<String, String>> taskConfigs(int maxTasks) {
        ArrayList<Map<String, String>> configs = new ArrayList<>();
        Map<String, String> config = new HashMap<>();
        config.put("table_name", sourceConfig.topicPrefix);
        config.put("topic", sourceConfig.topicPrefix);
        configs.add(config);
        return configs;
    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public ConfigDef config() {
        // TODO Auto-generated method stub
        return null;
    }

}
