package com.mq.mqrabbit.direct;

import com.mq.mqrabbit.util.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class DReceive {

    public static final String EXCHANGE_NAME = "test_exchange_direct";

    public static final String DIRECT_NAME = "test_exchange_direct_sms";

    public static void main(String agrs[]) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        channel.queueDeclare(DIRECT_NAME,false,false,false,null);
        channel.queueBind(DIRECT_NAME,EXCHANGE_NAME,"update");
        channel.queueBind(DIRECT_NAME,EXCHANGE_NAME,"delete");
        channel.queueBind(DIRECT_NAME,EXCHANGE_NAME,"add");
        channel.basicQos(1);
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body,"utf-8");
                System.out.println("[1] direct: "+msg);
                try {
                    TimeUnit.MILLISECONDS.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            }
        };
        channel.basicConsume(DIRECT_NAME,false,consumer);
    }
}
