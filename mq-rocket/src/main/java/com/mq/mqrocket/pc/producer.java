package com.mq.mqrocket.pc;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

public class producer {

    public static void main(String[] args) throws Exception {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer("test_producer");
        defaultMQProducer.setNamesrvAddr("127.0.0.1:9876");
        defaultMQProducer.start();
        for(int i=0;i<100;i++){
            Message message = new Message("Topic","Tag",("hello rocket "+i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            SendResult result = defaultMQProducer.send(message);
            System.out.printf("%s%n",result);
        }
    }
}
