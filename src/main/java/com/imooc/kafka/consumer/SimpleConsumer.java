package com.imooc.kafka.consumer;


import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SimpleConsumer {

    @Autowired
    @Qualifier("consumerObj")
    private KafkaConsumer consumer;

    @Autowired
    @Qualifier("consumerConfigs")
    private Map<String, Object> consumerConfigs;

    public void subscribeTopic(List<String> topics) {
        consumer.subscribe(topics);
        while(!Thread.currentThread().isInterrupted()) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000L));
            for (ConsumerRecord<String, String> record : records) {
               log.info(">>>> Topic = " + record.topic()
                   + " ; partition = "+ record.partition()
                   + " ; offset = "+ record.offset()
                   + " ; value = " + record.value());
            }
            consumer.commitAsync();
        }
    }
}