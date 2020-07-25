package com.mq.mqrabbit.workqueue;

import com.mq.mqrabbit.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * work模式
 */
public class WSend {

    private static final String QUEUE_NAME = "test_work_queue";

    public static void main(String args[]) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //每个消费者发送确认消息之前，消息队列不发送下一个消息到消费者，一次只处理一个消息,实现负载均衡
        int prefetchCount = 1;
        //channel.basicQos(prefetchCount);
        for(int i=0;i<50;i++){
            String msg = "hello "+i;
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
            System.out.println("MQ Send "+msg);
            Thread.sleep(100);
        }
        channel.close();
        connection.close();
    }
}
