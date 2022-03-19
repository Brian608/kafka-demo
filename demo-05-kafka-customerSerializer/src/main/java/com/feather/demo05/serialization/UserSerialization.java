package com.feather.demo05.serialization;

import com.feather.demo05.entity.User;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Map;

/**
 * @program: kafka-demo
 * @description:
 * @author: 杜雪松(feather)
 * @since: 2022-03-18 07:33
 **/
public class UserSerialization implements Serializer<User>  {
    @Override
    public void configure(Map<String, ?> map, boolean b) {
        //do nothing
        //用于接受对序列化器的配置参数，并对当前序列化器进行配置和初始化
    }

    @Override
    public byte[] serialize(String topic, User user) {
        try{
        if (user==null){
            return  null;
        }else {
          final   Integer userId = user.getUserId();
         final    String userName = user.getUserName();
         if (userId!=null){
             if (userName!=null){
                     byte[] userNameBytes = userName.getBytes("UTF-8");
                    int length = userNameBytes.length;
                    //第一个四个字节用于存储userid的值   因为userid是int 类型， int类型占四个字节
                 //第二个四个字节用于存储username字节数组的长度 int值
                 //第三个长度，用于存放username 序列化后的字节数组
                    ByteBuffer byteBuffer=ByteBuffer.allocate(4+4+length);
                    //设置userid
                    byteBuffer.put(ByteBuffer.allocateDirect(userId));
                    //设置username字节数组长度
                    byteBuffer.put((byte) length);
                    //设置username字节数组
                    byteBuffer.put(byteBuffer);
                    //以字节数组形式放回user对象
                 return  byteBuffer.array();
             }
             }
         }
        }
        catch (Exception e) {
                throw new SerializationException("数据序列化异常！");
            }
        return new byte[0];
    }

    @Override
    public void close() {
        // do nothing
        //用于关闭资源操作，需要幂等，即多次调用，效果是一样的。

    }
}
