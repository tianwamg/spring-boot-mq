package com.mq.mqrabbit.queue;

import com.mq.mqrabbit.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 生产者发送消息
 */
public class send {

    private static final String QUEUE_NAME = "test_simple_queue";

    public static void main(String args[]) throws IOException, TimeoutException {
        //获取连接
        Connection connection = ConnectionUtils.getConnection();
        //创建通道
        Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //定义消息内容
        String message = "hello rabbitmq";
        //发布消息
        channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
        System.out.println("[X] Sent ' "+message+"'");
        //关闭连接
        channel.close();
        connection.close();
    }
}
