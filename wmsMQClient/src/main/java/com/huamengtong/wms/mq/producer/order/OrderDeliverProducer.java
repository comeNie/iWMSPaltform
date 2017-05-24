package com.huamengtong.wms.mq.producer.order;

import com.alibaba.rocketmq.client.producer.SendResult;
import com.huamengtong.wms.constants.MqConstants;
import com.huamengtong.wms.mq.producer.WmsProducerService;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class OrderDeliverProducer {

    @Autowired
    WmsProducerService producer;

    /**
     * order producer
     * @param requstMap 消息体
     * @return
     * @throws Exception
     */
    public SendResult sendOrder(Map requstMap) throws Exception {
        if (MapUtils.isEmpty(requstMap)) {
            return null;
        }
        String queueId= MapUtils.getString(requstMap,"whCode");
        return producer.send(MqConstants.C_TOPIC_CONSUMER_ORDER_DELIVER,MqConstants.C_TAG_ORDER_DELIVER,requstMap,null,queueId);
    }

    /**
     * order producer delay
     * @param requstMap 消息体
     * @param priority true 优先级为高
     * @param delayLevel 延时级别
     */
    public SendResult sendOrder(Map requstMap,boolean priority,int ...delayLevel) {
        if (MapUtils.isEmpty(requstMap)) {
            return null;
        }
        String topic = MqConstants.C_TOPIC_CONSUMER_ORDER_DELIVER;
        String tag = MqConstants.C_TAG_ORDER_DELAY;
        if(priority){
            topic = MqConstants.C_TOPIC_CONSUMER_ORDER_DELIVER_DELAY;
            tag = MqConstants.C_TAG_ORDER_DELIVER_DELAY;
        }
        int delay =0;
        if(delayLevel != null && delayLevel.length>0){
            delay = delayLevel[0];
        }
        return producer.sendByDelay(topic,tag,requstMap,delay);
    }
}
