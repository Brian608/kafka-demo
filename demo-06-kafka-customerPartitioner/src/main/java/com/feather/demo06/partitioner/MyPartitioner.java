package com.feather.demo06.partitioner;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

/**
 * @program: kafka-demo
 * @description:自定义分区器
 * @author: 杜雪松(feather)
 * @since: 2022-03-20 07:59
 **/
public class MyPartitioner implements Partitioner {
    @Override
    public int partition(String s, Object o, byte[] bytes, Object o1, byte[] bytes1, Cluster cluster) {
        //此处可以计算分区的数量，暂定写死为2
        return 2;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
