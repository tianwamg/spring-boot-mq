package com.mq.mqrocket.sorted;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 顺序消费
 */
public class Consumer {


    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("sorted_consumer");
        consumer.setNamesrvAddr("127.0.0.1:9876");
        /**
         * 设置consumer第一次启动是按照从队列头部开始消费还是从尾部开始
         * 如果非第一次启动，那么按照上次消费的位置继续
         */
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.subscribe("TopicTest","TagA || TagB || TagC");
        consumer.registerMessageListener(new MessageListenerOrderly() {
            Random random = new Random();
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext) {
                consumeOrderlyContext.setAutoCommit(true);
                for(MessageExt ext : list){
                    System.out.println("consumeThread="+Thread.currentThread().getName()+ " queueId="+ext.getMsgId()+",content:"+new String(ext.getBody()));
                }
                try{
                    //模拟业务逻辑处理
                    TimeUnit.SECONDS.sleep(random.nextInt(10));
                }catch (Exception e){
                    e.printStackTrace();
                }
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        consumer.start();
        System.out.println("Consumer started...");
    }
}
