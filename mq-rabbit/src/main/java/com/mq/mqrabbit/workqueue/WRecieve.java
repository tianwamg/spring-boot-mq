package com.mq.mqrabbit.workqueue;

import com.mq.mqrabbit.util.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class WRecieve {

    private static final String QUEUE_NAME = "test_work_queue";

    public static void main(String args[]) throws IOException, TimeoutException {

        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        channel.basicQos(1);

        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                  String msg = new String(body,"utf-8");
                  System.out.println("[1] Recv-msg: "+msg);
                  try{
                      Thread.sleep(200);
                  }catch (InterruptedException e){
                      e.printStackTrace();
                  }finally {
                      System.out.println("[1] done");
                      //手动回执消息
                      channel.basicAck(envelope.getDeliveryTag(),false);
                  }
            }
        };
        boolean autoAck = false;//自动应答关闭
        channel.basicConsume(QUEUE_NAME,autoAck,consumer);
    }
}
