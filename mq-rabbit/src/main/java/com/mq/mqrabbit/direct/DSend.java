package com.mq.mqrabbit.direct;

import com.mq.mqrabbit.util.ConnectionUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 路由模式
 */
public class DSend {

    public static final String EXCHANGE_NAME = "test_exchange_direct";

    public static void main(String args[]) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        String msg = "send direct message";
        channel.basicPublish(EXCHANGE_NAME,"update",null,msg.getBytes());
        System.out.println("direct producer send message end");
        channel.close();
        connection.close();
    }
}
