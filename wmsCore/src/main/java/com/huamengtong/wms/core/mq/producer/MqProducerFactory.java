package com.huamengtong.wms.core.mq.producer;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.huamengtong.wms.constants.MqConstants;
import com.huamengtong.wms.inner.SystemConfigUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;

public class MqProducerFactory implements FactoryBean<DefaultMQProducer>,DisposableBean,InitializingBean {

    private DefaultMQProducer producer;

    public boolean isSingleton(){
        return true;
    }

    public void destroy()throws Exception{
        this.producer.shutdown();
    }

    /***
     * rocketMQ address
     */
    @Value(value = "#{wmsConfigs['rocketmq.address']}")
    private String rocketmqAddress;

    @Override
    public DefaultMQProducer getObject() throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        return this.producer;
    }

    @Override
    public Class<?> getObjectType(){
        return DefaultMQProducer.class;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.producer = new DefaultMQProducer(MqConstants.PRODUCER_GROUP);
        this.producer.setDefaultTopicQueueNums(8);
        this.producer.setNamesrvAddr(StringUtils.isNotEmpty(rocketmqAddress)? rocketmqAddress: SystemConfigUtil.get("rocketmq.address", "127.0.0.1:9876") );
        this.producer.start();
    }
}
