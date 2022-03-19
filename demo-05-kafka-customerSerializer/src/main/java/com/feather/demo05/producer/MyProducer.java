package com.feather.demo05.producer;

import com.feather.demo05.entity.User;
import com.feather.demo05.serialization.UserSerialization;
import org.apache.kafka.clients.producer.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: kafka-demo
 * @description:
 * @author: 杜雪松(feather)
 * @since: 2022-03-18 07:52
 **/
public class MyProducer {
    public static void main(String[] args) {
        Map<String, Object> configs=new HashMap<>();
        configs.put(ProducerConfig.BATCH_SIZE_CONFIG,"node:9002");
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, UserSerialization.class);
        KafkaProducer<String, User> producer=new KafkaProducer<String, User>(configs);
        User user=new User();
        user.setUserId(1001);
        user.setUserName("张三");
        ProducerRecord<String, User> record=new ProducerRecord<String, User>(
                "tp_user_01",//topic
                user.getUserName(),//key
                user//value
        );
        producer.send(record, new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                if (e!=null){
                    System.out.println("消息发送异常");
                }else {
                    System.out.println("主题："+recordMetadata.topic()+"\t"
                    +"分区："+recordMetadata.partition()+"\t"
                    +"生产者偏移量："+recordMetadata.offset());

                }
            }
        });
        producer.close();

    }
}
