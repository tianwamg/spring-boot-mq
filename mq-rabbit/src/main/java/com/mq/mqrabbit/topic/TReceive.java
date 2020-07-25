package com.mq.mqrabbit.topic;

import com.mq.mqrabbit.util.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class TReceive {

    public static final String EXCHANEGE_NAME = "test_topic_exchange";

    public static final String TOPIC_NAME = "test_topic_sms";

    public static void main(String args[]) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(TOPIC_NAME,false,false,false,null);
        //.#匹配一个或多个
        channel.queueBind(TOPIC_NAME,EXCHANEGE_NAME,"update.#");
        channel.basicQos(1);
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body,"utf-8");
                System.out.println("topic [1] :"+msg);
                try {
                    TimeUnit.MILLISECONDS.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            }
        };
        channel.basicConsume(TOPIC_NAME,false,consumer);
    }
}
