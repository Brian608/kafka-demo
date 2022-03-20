package com.feather.demo06.producer;

import com.feather.demo06.partitioner.MyPartitioner;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: kafka-demo
 * @description:
 * @author: 杜雪松(feather)
 * @since: 2022-03-20 08:08
 **/
public class MyProducer {
    public static void main(String[] args) {
        Map<String, Object> configs=new HashMap<>();
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"node1:9002");
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class);
        //指定自已定义的分区器
        configs.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, MyPartitioner.class);
    //此处不要设置partition的值
        KafkaProducer<String, String> producer=new KafkaProducer<String, String>(configs);
        ProducerRecord<String, String> record=new ProducerRecord<String, String>(
          "tp_part_01",
                "myKey",
                "myValue"
        );
        producer.send(record, new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                if (e!=null){
                    System.out.println("消息发送失败");
                }else {
                    System.out.println(recordMetadata.topic());
                    System.out.println(recordMetadata.partition());
                    System.out.println(recordMetadata.offset());
                }
            }
        });
        //关闭生产者
        producer.close();
    }
}
