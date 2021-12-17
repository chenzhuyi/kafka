package com.demo.kafka;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.demo.kafka.consumer.SimpleConsumer;
import com.demo.kafka.producer.SimpleProducer;

@EnableAutoConfiguration
@SpringBootApplication
public class ExamplesApplication implements ApplicationRunner {

    @Autowired
    private SimpleProducer procedure;

    @Autowired
    private SimpleConsumer consumer;

	public static void main(String[] args) {
		SpringApplication.run(ExamplesApplication.class, args);
	}

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // TODO Auto-generated method stub
//        procedure.send("testKafka1", 100);
        consumer.subscribeTopic(Arrays.asList("testKafka1"));
    }
}
