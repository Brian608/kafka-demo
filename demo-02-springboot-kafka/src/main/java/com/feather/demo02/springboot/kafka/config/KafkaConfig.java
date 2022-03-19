package com.feather.demo02.springboot.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.internals.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: kafka-demo
 * @description:
 * @author: 杜雪松(feather)
 * @since: 2022-02-20 18:03
 **/
@Configuration
public class KafkaConfig {
    @Bean
    public  NewTopic topic1(){
        return  new NewTopic("nptc-01",3,(short)1);
    }
    @Bean
    public NewTopic topic2(){
        return new  NewTopic("ntpic-02",5,(short) 1);
    }
    @Bean
    public KafkaAdmin kafkaAdmin(){
        Map<String, Object> configs=new HashMap<>();
        configs.put("bootstrap.servers","node1:9092");
        KafkaAdmin admin=new KafkaAdmin(configs);
        return admin;
    }
    @Bean
    @Autowired
    public  KafkaTemplate <Integer,String> kafkaTemplate(ProducerFactory<Integer,String> producerFactory){
    //覆盖原有producerFactory设置
        Map<String, Object> configs=new HashMap<>();
        configs.put(ProducerConfig.BATCH_SIZE_CONFIG,200);
        KafkaTemplate<Integer,String> template=new KafkaTemplate<Integer,String>(producerFactory,configs);
        return template;
    }
}
