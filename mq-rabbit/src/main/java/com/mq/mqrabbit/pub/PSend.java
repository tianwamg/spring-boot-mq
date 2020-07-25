package com.mq.mqrabbit.pub;

import com.mq.mqrabbit.util.ConnectionUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 发布订阅模式
 */
public class PSend {

    private static final String EXCHANGE_NAME = "test_exchange_fanout";

    public static void main(String args[]) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        //声明交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);//分发

        String msg = "pub/sub";
        channel.basicPublish(EXCHANGE_NAME,"",null,msg.getBytes());//s1:routingkey
        System.out.println("send");
        channel.close();
        connection.close();
    }
}
