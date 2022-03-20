package com.feather.demo.producer;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: kafka-demo
 * @description:
 * @author: 杜雪松(feather)
 * @since: 2022-03-20 08:59
 **/
public class MyProducer {
    public static void main(String[] args) {
        Map<String, Object> congigs=new HashMap<>();
        congigs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"node:9002");
        congigs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
        congigs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        //如果有多个拦截器，则设置为多个拦截器类的全限定类名，中间用逗号隔开
        congigs.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG,"com.feather.demo.interctptor.InterceptorOne,"+
        "com.feather.demo.interctptor.InterceptorTwo,"+
        "com.feather.demo.interctptor.InterceptorThree");

        congigs.put("classContent","this is kafka class");

        KafkaProducer<Integer,String> producer=new KafkaProducer<Integer, String>(congigs);

        ProducerRecord<Integer,String> record=new ProducerRecord<Integer,String>(
                "tp_inter_01",
                0,
                1001,
                "this is messge"
        );
        producer.send(record, new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                if (e!=null){
                    System.out.println("消息异常");
                }else {
                    System.out.println(recordMetadata.offset());
                    System.out.println(recordMetadata.partition());
                    System.out.println(recordMetadata.topic());
                }
            }
        });
        producer.close();

    }
}
