package org.feather.kafka.demo.producer;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @program: kafka-demo
 * @description:
 * @author: 杜雪松(feather)
 * @since: 2022-02-17 20:33
 **/
public class MyProducer1 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Map<String, Object> configs=new HashMap<>();
        //指定初始连接用到的broker地址
        configs.put("bootstrap.servers","127.0.0.1:9092");
        //指定key的序列化类
        configs.put("key.serializer", IntegerSerializer.class);
        // 指定value的序列化类
        configs.put("value.serializer", StringSerializer.class);
//        configs.put("acks","all");
//        configs.put("reties","3");
        // 用于设置用户自定义的消息头字段
        List<Header> headers=new ArrayList<>();
        headers.add(new RecordHeader("biz.name","producer.demo".getBytes()));
        ProducerRecord <Integer ,String> record=new ProducerRecord<Integer,String>("topic_1",0,0,"hello kafka",headers);
//消息的同步确认
          KafkaProducer<Integer,String> producer=new KafkaProducer<Integer, String>(configs);
      final   Future<RecordMetadata> metadataFuture = producer.send(record);
      final   RecordMetadata recordMetadata = metadataFuture.get();
        System.out.println("消息的主题:"+recordMetadata.topic());
        System.out.println("消息的偏移量:"+recordMetadata.offset());
        System.out.println("消息的分区:"+recordMetadata.partition());
        //消息的异步确认
        producer.send(record, new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                if (e==null){
                    System.out.println("消息的主题:"+recordMetadata.topic());
                    System.out.println("消息的偏移量:"+recordMetadata.offset());
                    System.out.println("消息的分区:"+recordMetadata.partition());
                }else {
                    System.out.println("异常消息:"+e.getMessage());
                }
            }
        });
        //关闭生产者
        producer.close();

    }
}
