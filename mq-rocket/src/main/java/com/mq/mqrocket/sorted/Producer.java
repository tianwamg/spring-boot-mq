package com.mq.mqrocket.sorted;


import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 顺序生成
 */
public class Producer {

    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.start();

        String[] tags = new String[]{"TagA","TagB","TagC"};
        //订单列表
        List<OrderStep> list = new Producer().buildOrders();

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String datestr = sdf.format(date);
        for(int i=0;i<10;i++){
            String body = datestr + "hello rocketmq "+ list.get(i);
            Message msg = new Message("TopicTest",tags[i % tags.length],"KEY "+i,body.getBytes());
            SendResult result = producer.send(msg, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> list, Message message, Object o) {
                    Long id = (Long) o;
                    long index = id % list.size();
                    return list.get((int) index);
                }
            },list.get(i).getOrderId());

            System.out.println(String.format("SendResult status : %s ,queueId : %d, body : %s",result.getSendStatus(),result.getMessageQueue().getQueueId(),body));
        }
        producer.shutdown();

    }

    //订单步骤
    private class OrderStep{
        private long orderId;
        private String desc;

        public long getOrderId () {
            return orderId;
        }

        public void setOrderId (long orderId) {
            this.orderId = orderId;
        }

        public String getDesc () {
            return desc;
        }

        public void setDesc (String desc) {
            this.desc = desc;
        }

        @Override
        public String toString () {
            return "OrderStep{" + "orderId=" + orderId + ", desc='" + desc + '\'' + '}';
        }
    }

    //生成模拟订单数据
    private List<OrderStep> buildOrders(){
        List<OrderStep> orderList = new ArrayList<OrderStep>();

        OrderStep orderDemo = new OrderStep();
        orderDemo.setOrderId(15103111039L);
        orderDemo.setDesc("创建");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(15103111065L);
        orderDemo.setDesc("创建");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(15103111039L);
        orderDemo.setDesc("付款");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(15103117235L);
        orderDemo.setDesc("创建");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(15103111065L);
        orderDemo.setDesc("付款");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(15103117235L);
        orderDemo.setDesc("付款");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(15103111065L);
        orderDemo.setDesc("完成");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(15103111039L);
        orderDemo.setDesc("推送");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(15103117235L);
        orderDemo.setDesc("完成");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(15103111039L);
        orderDemo.setDesc("完成");
        orderList.add(orderDemo);

        return orderList;
    }
}
