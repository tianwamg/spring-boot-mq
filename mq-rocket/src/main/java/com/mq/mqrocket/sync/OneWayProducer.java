package com.mq.mqrocket.sync;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * 单向发送
 */
public class OneWayProducer {

    public static void main(String[] args) throws Exception{
        DefaultMQProducer producer = new DefaultMQProducer("rename_producer_name");
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.start();
        for(int i=0;i<100;i++){
            Message message = new Message("TestTopic","TagA",("hello rocketmq"+i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            //单向发送，无返回结果
            producer.sendOneway(message);
        }
        producer.shutdown();
    }

}
