package com.demo.kafka.connector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.demo.kafka.config.SourceConnectorConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JdbcSourceTask extends SourceTask {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private String tableName;
    private String topic;
    private int fetchsize;
    private Connection conn;
    private Statement stmt;
    private ResultSet rs;

    @Autowired
    private SourceConnectorConfig sourceConnectorConfig;

    @Override
    public String version() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void start(Map<String, String> props) {
        tableName = props.get("table_name");
        topic = props.get("topic");
        fetchsize = jdbcTemplate.getFetchSize();
        try {
            conn = jdbcTemplate.getDataSource().getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT HADP_TRANS_NUMBER, HADP_TRANS_TYPE FROM " + tableName);
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public List<SourceRecord> poll() throws InterruptedException {
        List<SourceRecord> sourceRecords = new ArrayList<>();
        jdbcTemplate.queryForList(
                "SELECT HADP_TRANS_NUMBER, HADP_TRANS_TYPE FROM " + tableName,
                new RowMapper<SourceRecord>() {
                    @Override
                    public SourceRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
                        if (rs.getInt("HADP_TRANS_TYPE") != 2) {
                            Map sourcePartition = Collections.singletonMap("TableName", tableName);
                            Map sourceOffset = Collections.singletonMap("position", rs.getLong("HADP_TRANS_NUMBER"));
                            return new SourceRecord(
                                    sourcePartition,
                                    sourceOffset,
                                    topic,
                                    Schema.STRING_SCHEMA,
                                    rs.getInt("HADP_TRANS_TYPE"));
                        }
                        return null;
                    }
                });
        return sourceRecords;
    }

    @Override
    public synchronized void stop() {
        try {
            rs.close();
        } catch (SQLException e) {
            log.warn(e.getMessage());
            rs = null;
        }
        try {
            stmt.close();
        } catch (SQLException e) {
            log.warn(e.getMessage());
            stmt = null;
        }
        try {
            conn.close();
        } catch (SQLException e) {
            log.warn(e.getMessage());
            conn = null;
        }
    }
}
