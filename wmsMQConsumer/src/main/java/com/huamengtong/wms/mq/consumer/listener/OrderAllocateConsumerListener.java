package com.huamengtong.wms.mq.consumer.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.huamengtong.wms.constants.GlobalConstants;
import com.huamengtong.wms.constants.MqConstants;
import com.huamengtong.wms.core.formwork.db.domain.CurrentUserEntity;
import com.huamengtong.wms.core.formwork.db.util.DbShareField;
import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.mq.consumer.ConsumeListener;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.mq.producer.order.OrderProducer;
import com.huamengtong.wms.outwh.service.IShipmentService;
import com.huamengtong.wms.utils.SerializationUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/***
 * 出库单库存分配消费队列
 */
@Component
public class OrderAllocateConsumerListener extends ConsumeListener {

    public static final Logger log = LoggerFactory.getLogger(OrderAllocateConsumerListener.class);

    @Autowired
    private OrderProducer orderProducer;

    @Autowired
    IShipmentService shipmentService;

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> messageExts, ConsumeConcurrentlyContext consumeConcurrentlyContext) {

        try {
            Map map = (Map) SerializationUtils.H2Deserialize(messageExts.get(0).getBody(), Map.class);
            Long shipmentId = MapUtils.getLong(map,"shipmentId");
            Long warehouseId = MapUtils.getLong(map,"warehouseId");
            Long tenantId = MapUtils.getLong(map,"tenantId");
            String from = MapUtils.getString(map,"from");

            MessageResult mr = shipmentService.updateShipmentMqAllocate(shipmentId,getDbShardVO(tenantId, warehouseId));
            if(!mr.isSuc()){
                 //如果分配失败,重新放入分配队列,5分钟后重新分配
                orderProducer.sendOrder(map,true, MqConstants.MQ_DELAY_LEVEL);
            }
            System.out.println("Message content : " + from + "||" + JSON.toJSONString(map));

        } catch (Exception e) {
            log.error("mq:exec mq message is{} ex:{} ",messageExts.get(0),e.getMessage());
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

    private DbShardVO getDbShardVO(long tenantId,long warehouseId) {
        CurrentUserEntity user = new CurrentUserEntity();
        user.setUserName(GlobalConstants.GLOBAL_USER_NAME);
        user.setTenantId(tenantId);
        DbShardVO dbShardVO = DbShardVO.getInstance(user);
        dbShardVO.setWarehouseId(warehouseId);
        dbShardVO.setSource(DbShareField.OUT_WH);
        return dbShardVO;
    }

}
