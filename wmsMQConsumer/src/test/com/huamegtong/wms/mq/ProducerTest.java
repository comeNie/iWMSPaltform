package com.huamegtong.wms.mq;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.client.producer.SendStatus;
import com.alibaba.rocketmq.common.message.Message;

import java.util.Date;
import java.util.TimerTask;


public class ProducerTest {

    public static void main(String[] args) throws MQClientException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer("rmq-group");
        producer.setNamesrvAddr("10.21.1.31:9876");
        producer.setInstanceName("rmq-instance");
        producer.start();
        try {
            int index = 0;
            while (true) {
                Message msg = new Message("TopicA-test",// topic
                        "TagA",// tag
                        (new Date() + " Hello RocketMQ ,QuickStart - " + index)
                                .getBytes()// body
                );
                SendResult sendResult = producer.send(msg);
                if(sendResult.getSendStatus() == SendStatus.SEND_OK){
                    System.out.println(" Send message success." + index );
                }
                index ++;
                Thread.sleep(2000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        producer.shutdown();
    }

}
