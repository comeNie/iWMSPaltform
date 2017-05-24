package com.huamengtong.wms.core.mq.producer;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.MessageQueueSelector;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.client.producer.SendStatus;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageQueue;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.huamengtong.wms.constants.MqConstants;
import com.huamengtong.wms.utils.SerializationUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class NeedLogProducer extends MqProducerFactory {

    private static final Logger log = LoggerFactory.getLogger(NeedLogProducer.class);

    public SendResult sendNeedLog(Map requstMap) throws Exception {
        if (MapUtils.isEmpty(requstMap)) {
            return null;
        }
        String keys = String.valueOf(new Date().getTime());
        String queueId = MapUtils.getString(requstMap,"userId");
        if(requstMap.containsKey("TWmsOperationLogEntity")) {
            return this.send(MqConstants.C_TOPIC_CONSUMER_LOG, MqConstants.C_TAG_OPERATON_LOG, requstMap, keys, queueId);
        }
        if(requstMap.containsKey("TWmsInventoryLogEntity")) {
            return this.send(MqConstants.C_TOPIC_CONSUMER_LOG, MqConstants.C_TAG_INVENTORY_LOG, requstMap, keys, queueId);
        }
        if(requstMap.containsKey("TWmsTransactionEntity")) {
            return this.send(MqConstants.C_TOPIC_CONSUMER_LOG, MqConstants.C_TAG_TRANSACTION_LOG, requstMap, keys, queueId);
        }
        if(requstMap.containsKey("TWmsProInventoryLogEntity")){
            return this.send(MqConstants.C_TOPIC_CONSUMER_LOG,MqConstants.C_TAG_PRO_INVENTORY_LOG,requstMap,keys,queueId);
        }
        return null;
    }

    /**
     * 顺序消息，按照参数queueId分配队列
     * @param topic 主题
     * @param tags 标记
     * @param body 消息内容
     * @param queueId 消息Id
     */
    private SendResult send(String topic, String tags, Object body, String keys, String queueId) throws Exception{
        Message msg = new Message(topic, tags, SerializationUtils.H2Serialize(body));
        SendResult result = null;
        if (StringUtils.isNotEmpty(queueId)) {
            result = getObject().send(msg, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                    Integer id = Math.abs(arg.hashCode());
                    int index = id % mqs.size();
                    return mqs.get(index);
                }
            }, queueId);
        }else {
            result = sendResult(msg);
        }
        if(result.getSendStatus() == SendStatus.SEND_OK){
            log.info("id:" + result.getMsgId() +" || key:"+keys+  " || result:" + result.getSendStatus() +" || body "+ JSON.toJSONString(body));
        }
        return result;
    }

    private SendResult sendResult(Message msg) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        return getObject().send(msg);
    }
}
