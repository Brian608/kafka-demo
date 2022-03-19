package org.feather.kafka.demo.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Consumer;

/**
 * @program: kafka-demo
 * @description:
 * @author: 杜雪松(feather)
 * @since: 2022-02-20 09:17
 **/
public class MyConsumer1 {
    public static void main(String[] args) {
        HashMap<String, Object> congigs=new HashMap<>();

        congigs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"node1:9092");
        congigs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
        congigs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringSerializer.class);
        //配置消费组ID
        congigs.put(ConsumerConfig.GROUP_ID_CONFIG,"consumer_demo");
        //如果找不到当前消费者的有效偏移量，则自动重置到开始
        //latest表示直接虫子到消息偏移量的最后一个
        congigs.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");

        KafkaConsumer<Integer,String> consumer=new KafkaConsumer<Integer, String>(congigs);
        //先订阅，在消费
        consumer.subscribe(Arrays.asList("topic_1"));
//        while (true){
//            final   ConsumerRecords<Integer, String> consumerRecords = consumer.poll(3000);
//        }
        //如果主题中没有可以消费的消息，则该方法可以放到while 循环中，每过三秒拉取一次
        //如果还没有拉取到过三秒再拉取一次，防止while循环太密集的poll调用
        //批量从分区主题拉去消息
      final   ConsumerRecords<Integer, String> consumerRecords = consumer.poll(3000);

      //遍历本次从主题的分区拉取到的消息
      consumerRecords.forEach(new Consumer<ConsumerRecord<Integer, String>>() {
          @Override
          public void accept(ConsumerRecord<Integer, String> record) {
              System.out.println(
                      record.topic()+"\t"+
                      record.partition()+"\t"+
                      record.offset()+"\t"+
                      record.key()+"\t"+
                      record.value());
          }
      });
      consumer.close();

    }

}
