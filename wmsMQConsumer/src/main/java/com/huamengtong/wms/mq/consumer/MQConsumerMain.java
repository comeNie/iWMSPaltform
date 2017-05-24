package com.huamengtong.wms.mq.consumer;

import com.huamengtong.wms.constants.MqConstants;
import com.huamengtong.wms.mq.consumer.listener.InventoryLogListener;
import com.huamengtong.wms.mq.consumer.listener.OperationLogListener;
import com.huamengtong.wms.mq.consumer.listener.OrderAllocateConsumerListener;
import com.huamengtong.wms.mq.consumer.listener.TransactionLogListener;
import com.huamengtong.wms.mq.consumer.listener.OrderAllocateDelayConsumerListener;
import com.huamengtong.wms.mq.consumer.listener.ProInventoryLogListener;
import com.huamengtong.wms.mq.consumer.listener.OrderDeliverInventoryFastListener;
import com.huamengtong.wms.mq.consumer.listener.OrderDeliverInventoryListener;
import com.huamengtong.wms.mq.consumer.service.ConsumerService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;

public class MQConsumerMain {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:mq"+ File.separator + "spring-core.xml");
        context.start();
        System.out.println(" WMS MQServer start success .");

        ConsumerService consumerService = (ConsumerService)context.getBean(ConsumerService.class);

        //注册OperationLOG消息队列
        consumerService.registerListenerConsumer(MqConstants.C_GROUP_NAME_OPERATION_LOG,
                MqConstants.C_TOPIC_CONSUMER_LOG,MqConstants.C_TAG_OPERATON_LOG, context.getBean(OperationLogListener.class));

        //注册InventoryLOG消息队列
        consumerService.registerListenerConsumer(MqConstants.C_GROUP_NAME_INVENTORY_LOG,
                MqConstants.C_TOPIC_CONSUMER_LOG,MqConstants.C_TAG_INVENTORY_LOG, context.getBean(InventoryLogListener.class));

        //注册TransactionLOG消息队列
        consumerService.registerListenerConsumer(MqConstants.C_GROUP_NAME_TRANSACTION_LOG,
                MqConstants.C_TOPIC_CONSUMER_LOG,MqConstants.C_TAG_TRANSACTION_LOG, context.getBean(TransactionLogListener.class));


        //注册OrderAllocate消息队列
        consumerService.registerListenerConsumer(MqConstants.C_GROUP_CONSUMER_ORDER,
                MqConstants.C_TOPIC_CONSUMER_ORDER,MqConstants.C_TAG_ORDER,context.getBean(OrderAllocateConsumerListener.class));

        //注册OrderAllocateDelay消息队列
        consumerService.registerListenerConsumer(MqConstants.C_GROUP_CONSUMER_ORDER_DELAY,
                MqConstants.C_TOPIC_CONSUMER_ORDER_DELAY,MqConstants.C_TAG_ORDER_DELAY, context.getBean(OrderAllocateDelayConsumerListener.class));

        //注册OrderDeliver消息队列
        consumerService.registerListenerConsumer(MqConstants.C_GROUP_CONSUMER_ORDER_DELIVER,
                MqConstants.C_TOPIC_CONSUMER_ORDER_DELIVER,MqConstants.C_TAG_ORDER_DELIVER,context.getBean(OrderDeliverInventoryFastListener.class));

        //注册OrderDeliverDelay消息队列
        consumerService.registerListenerConsumer(MqConstants.C_GROUP_CONSUMER_ORDER_DELIVER_DELAY,
                MqConstants.C_TOPIC_CONSUMER_ORDER_DELIVER_DELAY,MqConstants.C_TAG_ORDER_DELIVER_DELAY, context.getBean(OrderDeliverInventoryListener.class));

        //注册ProInventoryLog消息队列
        consumerService.registerListenerConsumer(MqConstants.C_GROUP_NAME_PRO_INVENTORY_LOG,
                MqConstants.C_TOPIC_CONSUMER_LOG,MqConstants.C_TAG_PRO_INVENTORY_LOG, context.getBean(ProInventoryLogListener.class));

    }
}
