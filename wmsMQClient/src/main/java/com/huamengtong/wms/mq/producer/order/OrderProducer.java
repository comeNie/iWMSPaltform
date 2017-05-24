package com.huamengtong.wms.mq.producer.order;

import com.alibaba.rocketmq.client.producer.SendResult;
import com.huamengtong.wms.constants.MqConstants;
import com.huamengtong.wms.mq.producer.WmsProducerService;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class OrderProducer {

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
        return producer.send(MqConstants.C_TOPIC_CONSUMER_ORDER,MqConstants.C_TAG_ORDER,requstMap,null,queueId);
    }

    /**
     * order producer delay
     * @param requestMap 消息体
     * @param priority true 优先级为高
     * @param delayLevel 延时级别
     */
    public SendResult sendOrder(Map requestMap,boolean priority,int ...delayLevel) {
        if (MapUtils.isEmpty(requestMap)) {
            return null;
        }
        String topic = MqConstants.C_TOPIC_CONSUMER_ORDER;
        String tag = MqConstants.C_TAG_ORDER;
        if(priority){
            topic = MqConstants.C_TOPIC_CONSUMER_ORDER_DELAY;
            tag = MqConstants.C_TAG_ORDER_DELAY;
        }
        int delay =0;
        if(delayLevel != null && delayLevel.length>0){
            delay = delayLevel[0];
        }
        //判断map中是否有count,如果map中有count获取值进行判断，大于等于十次就不再进入消息队列等待，没有count就创建一个放到map中
        if(requestMap.containsKey("count")){
            int count = MapUtils.getInteger(requestMap,"count");
            if(count >= 10){
                return null;
            }else{
                count++;
                requestMap.put("count",count);
            }
        }else {
            requestMap.put("count",0);
        }
        return producer.sendByDelay(topic,tag,requestMap,delay);
    }

}
