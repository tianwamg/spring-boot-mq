package com.mq.mqrabbit.topic;

import com.mq.mqrabbit.util.ConnectionUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TSend {

    public static final String EXCHANGE_NAME = "test_topic_exchange";

    public static void main(String agrs[]) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        String msg = "producer send exchange topic message";
        channel.basicPublish(EXCHANGE_NAME,"update.name",null,msg.getBytes());
        System.out.println("send msg end");
        channel.close();
        connection.close();
    }
}
