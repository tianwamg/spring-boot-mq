package com.mq.mqrocket.sync;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * 同步发送同步
 */
public class Producer {

    public static void main(String[] args) throws Exception{
        DefaultMQProducer producer = new DefaultMQProducer("rename_producer_name");
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.start();
        for(int i=0;i<100;i++){
            Message message = new Message("SyncTopicTest","TagA",("hello rocketmq:"+i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            SendResult result = producer.send(message);
            System.out.printf("%s%n",result);
        }
        producer.shutdown();
    }
}
