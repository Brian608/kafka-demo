package com.feather.demo05.entity;

/**
 * @program: kafka-demo
 * @description:用户自定义封装消息类
 * @author: 杜雪松(feather)
 * @since: 2022-03-18 07:31
 **/
public class User {
    private  Integer userId;

    private String userName;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                '}';
    }
}
