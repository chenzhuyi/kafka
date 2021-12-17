package com.imooc.kafka.producer;

import com.imooc.kafka.common.MessageEntity;

import lombok.extern.slf4j.Slf4j;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SimpleProducer {

    @Autowired
    @Qualifier("producerObj")
    private Producer<String, String> producer;

    public void send(String topic, int maxLoop) {
        for (int i=0; i<maxLoop; ++i) {
            producer.send(new ProducerRecord<String, String>(topic, Integer.toString(i), "value="+Integer.toString(i)),
//                    new Callback() {
//
//                        @Override
//                        public void onCompletion(RecordMetadata metadata, Exception exception) {
//                            if (exception == null) {
//                                log.info("Send message successfully! " + metadata.offset());
//                            } else {
//                                log.error(exception.getMessage());
//                            }
//                        }
//                
//            }
            (meta, exp) -> {
                if (exp == null) {
                    log.info("Send message successfully! " + meta.offset());
                } else {
                    log.error(exp.getMessage());
                }
            });
        }
        producer.close();
    }

}