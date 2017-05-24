package com.huamengtong.wms.queue.service;

import com.huamengtong.wms.core.mq.consumer.ConsumeListener;

public interface IConsumerService {

     void registerListenerConsumer(String groupName, String topic, final ConsumeListener consumeListener);

     void registerListenerConsumer(String groupName, String topic, String tag, final ConsumeListener consumeListener);

}

