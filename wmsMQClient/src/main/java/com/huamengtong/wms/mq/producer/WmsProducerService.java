package com.huamengtong.wms.mq.producer;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.MessageQueueSelector;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.client.producer.SendStatus;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageQueue;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.huamengtong.wms.constants.MqConstants;
import com.huamengtong.wms.core.mq.producer.AbstractProducer;
import com.huamengtong.wms.inner.SystemConfigUtil;
import com.huamengtong.wms.utils.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WmsProducerService extends AbstractProducer implements FactoryBean<DefaultMQProducer>,DisposableBean,InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(WmsProducerService.class);

    private DefaultMQProducer producer;

    /**
     * 顺序消息，按照参数queueId分配队列
     * @param topic 主题
     * @param tags 标记
     * @param body 消息内容
     * @param queueId 消息Id
     */
    public SendResult send(String topic, String tags, Object body, String keys, String queueId) throws Exception{
        Message msg = new Message(topic, tags, SerializationUtils.H2Serialize(body));
        SendResult result = null;
        if (StringUtils.isNotEmpty(queueId)) {
            result = producer.send(msg, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                    Integer id = Math.abs(arg.hashCode());
                    int index = id % mqs.size();
                    return mqs.get(index);
                }
            }, queueId);
        }else {
            result = sendResult(msg);
            if(result.getSendStatus() == SendStatus.SEND_OK){
                log.error("id:" + result.getMsgId() +  " result:" + result.getSendStatus()+ JSON.toJSONString(body));
            }
        }
        return result;
    }

    /**
     *  延迟队列
     * @param topic 主题
     * @param tags  标记
     * @param body 消息体
     * @param delayLevel 延迟级别
     *  1：1 秒
     *  2：5 秒
     *  3：10秒
     *  4：30秒
     *  5：60秒
     *  6：2分钟
     *  7：3分钟
     *  8：4分钟
     *  9：5分钟
     *  10：6分钟
     *  11：7分钟
     *  12：8分钟
     *  13：9分钟
     *  14：10分钟
     *  15：20分钟
     *  16：30分钟
     */
    public SendResult sendByDelay(String topic, String tags, Object body, int delayLevel) {

        try {
            Message msg = new Message(topic, tags,SerializationUtils.H2Serialize(body));
            msg.setDelayTimeLevel(delayLevel);
            SendResult result = sendResult(msg);
            if(result.getSendStatus() == SendStatus.SEND_OK){
                log.info("id:" + result.getMsgId() + " result:" + result.getSendStatus()+ JSON.toJSONString(body));
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("AbstractProducer send is error", e);
            return null;
        }
    }

    private SendResult sendResult(Message msg) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        return producer.send(msg);
    }

    @Override
    public void destroy() throws Exception {
        log.info("producer shutdown.");
        this.producer.shutdown();
    }

    @Override
    public DefaultMQProducer getObject() throws Exception {
        return this.producer;
    }

    @Override
    public Class<?> getObjectType() {
        return DefaultMQProducer.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.producer = new DefaultMQProducer(MqConstants.PRODUCER_ORDER_GROUP);
        this.producer.setDefaultTopicQueueNums(8);
        this.producer.setNamesrvAddr(SystemConfigUtil.get("rocketmq.address", "127.0.0.1:9876"));
        this.producer.start();
    }
}
