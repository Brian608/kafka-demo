package com.feather.demo.interctptor;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.header.Headers;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.logging.Logger;

/**
 * @program: kafka-demo
 * @description:
 * @author: 杜雪松(feather)
 * @since: 2022-03-20 11:12
 **/
public class InterceptorOne implements ProducerInterceptor<Integer,String> {

    @Override
    public ProducerRecord<Integer, String> onSend(ProducerRecord<Integer, String> producerRecord) {
        //消息发送的时候，经过拦截器，调用该方法
        System.out.println("拦截器1 go");
        String topic = producerRecord.topic();
        Integer partition = producerRecord.partition();
        String value = producerRecord.value();
        Headers headers = producerRecord.headers();
        Integer key = producerRecord.key();
        Long timestamp = producerRecord.timestamp();

        //拦截器拦下来之后根据原消息创建新的消息
        //此处没有对原消息做任何改动
        ProducerRecord<Integer,String> newRecord=new ProducerRecord<Integer,String>(
                topic,
                partition,
                timestamp,
                key,
                value,
                headers);
        return newRecord;
    }

    @Override
    public void onAcknowledgement(RecordMetadata recordMetadata, Exception e) {
        //消息确认或者调用异常的时候调用该方法，该方法中不应该实现较重的任务  会影响卡夫卡的性能
        System.out.println( "拦截器1 back");

    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {
        Object classContent = map.get("classContent");
        System.out.println(classContent);

    }
}
