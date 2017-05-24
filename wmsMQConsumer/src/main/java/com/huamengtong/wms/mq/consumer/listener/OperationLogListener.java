package com.huamengtong.wms.mq.consumer.listener;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.huamengtong.wms.core.mq.consumer.ConsumeListener;
import com.huamengtong.wms.entity.main.TWmsOperationLogEntity;
import com.huamengtong.wms.main.service.IOperationLogService;
import com.huamengtong.wms.utils.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class OperationLogListener extends ConsumeListener {

    public static final Logger log = LoggerFactory.getLogger(OperationLogListener.class);

    @Autowired
    private IOperationLogService operationLogService;

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> messageExts, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        try {
            Map map = (Map)SerializationUtils.H2Deserialize(messageExts.get(0).getBody(), Map.class);
            TWmsOperationLogEntity operationLogEntity = (TWmsOperationLogEntity)map.get("TWmsOperationLogEntity")  ;
            operationLogEntity.setTenantId(88L);
            operationLogService.createOperationLog(operationLogEntity);
            log.info("mq:exec mq message is{} ex:{} ",messageExts.get(0));
        } catch (Exception e) {
            log.error("mq:exec mq message is{} ex:{} ",messageExts.get(0),e.getMessage());
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
