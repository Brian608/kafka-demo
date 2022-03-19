package com.feather.demo02.springboot.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @program: kafka-demo
 * @description:
 * @author: 杜雪松(feather)
 * @since: 2022-02-20 17:24
 **/
@Component
public class MyConsumer {

    @KafkaListener(topics = "topic-spring-01")
    public  void  onMessage(ConsumerRecord<Integer,String> record){
        System.out.println("消费者收到的消息"+record.offset()+"\t"+record.partition()+"\t"+record.topic()+"\t"+record.key()+"\t"+record.value());
    }
}
