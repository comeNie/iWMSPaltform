package com.huamengtong.wms.mq.consumer.service;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.huamengtong.wms.constants.MqConstants;
import com.huamengtong.wms.core.mq.consumer.ConsumeListener;
import com.huamengtong.wms.inner.SystemConfigUtil;
import com.huamengtong.wms.queue.service.IConsumerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService implements IConsumerService {

    public static final Logger LOGGER = LoggerFactory.getLogger(ConsumerService.class);

    @Override
    public void registerListenerConsumer(String groupName, String topic, final ConsumeListener consumeListener){
        registerListenerConsumer(groupName,topic,"",consumeListener);
    }

    @Override
    public void registerListenerConsumer(String groupName, String topic, String tag, final ConsumeListener consumeListener){

        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName);
        consumer.setConsumeThreadMin(1);
        consumer.setConsumeThreadMax(10);
        consumer.setNamesrvAddr(SystemConfigUtil.get("rocketmq.address", "127.0.0.1:9876"));
        consumer.setInstanceName(MqConstants.CONSUMER_INSTANCENAME);
        try {
            //订阅PushTopic下Tag为push的消息
            consumer.subscribe(topic, tag);
            //程序第一次启动从消息队列头取数据
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.registerMessageListener(consumeListener);
            consumer.start();
            LOGGER.info("Smart registerListenerConsumer:{}",consumeListener.getClass().getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
