package com.mq.mqrocket.transaction;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.*;

public class Producer {

    public static void main(String[] args) throws Exception {
        TransactionListener listener = new TransactionListenerImpl();
        TransactionMQProducer producer = new TransactionMQProducer("transcation");
        ExecutorService service = new ThreadPoolExecutor(2, 5, 100, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(2000), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = newThread(r);
                thread.setName("client-transaction-msg-check-thread");
                return thread;
            }
        });
        producer.setExecutorService(service);
        producer.setTransactionListener(listener);
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.start();

        String[] tags = new String[] {"TagA","TagB","TagC","TagD","TagE"};
        for(int i =0;i<10;i++){
            try{
                Message msg = new Message("TopicTest",tags[i % tags.length],"KEY"+i,("Hello RocketMQ "+i).getBytes(RemotingHelper.DEFAULT_CHARSET));
                SendResult result = producer.sendMessageInTransaction(msg,null);
                System.out.printf("%s%n",result);
                Thread.sleep(10);
            }catch (MQClientException | UnsupportedEncodingException e){
                e.printStackTrace();
            }
        }
        for (int i = 0; i < 10; i++) {
            Thread.sleep(1000);
        }
        producer.shutdown();
    }
}
