package com.huamengtong.wms.mq.consumer.listener;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.huamengtong.wms.constants.GlobalConstants;
import com.huamengtong.wms.core.formwork.db.domain.CurrentUserEntity;
import com.huamengtong.wms.core.formwork.db.util.DbShareField;
import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.mq.consumer.ConsumeListener;
import com.huamengtong.wms.entity.inventory.TWmsInventoryLogEntity;
import com.huamengtong.wms.inventory.service.IInventoryLogService;
import com.huamengtong.wms.utils.SerializationUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by Edwin on 2016/11/5.
 */
@Component
public class InventoryLogListener extends ConsumeListener {

    public static final Logger log = LoggerFactory.getLogger(InventoryLogListener.class);

    @Autowired
    IInventoryLogService inventoryLogService;

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> messageExts, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        try {
            Map map = (Map) SerializationUtils.H2Deserialize(messageExts.get(0).getBody(), Map.class);
            TWmsInventoryLogEntity inventoryLogEntity = (TWmsInventoryLogEntity)map.get("TWmsInventoryLogEntity");
            Long warehouseId = MapUtils.getLong(map,"warehouseId");
            Long tenantId = MapUtils.getLong(map,"tenantId");
            inventoryLogService.createInventoryLog(inventoryLogEntity,getDbShardVO(tenantId,warehouseId));
            log.info("mq:exec mq message is{} ex:{} ",messageExts.get(0));
        } catch (Exception e) {
            log.error("mq:exec mq message is{} ex:{} ",messageExts.get(0),e.getMessage());
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

    private DbShardVO getDbShardVO(long tenantId, long warehouseId) {
        CurrentUserEntity user = new CurrentUserEntity();
        user.setUserName(GlobalConstants.GLOBAL_USER_NAME);
        user.setTenantId(tenantId);
        DbShardVO dbShardVO = DbShardVO.getInstance(user);
        dbShardVO.setWarehouseId(warehouseId);
        dbShardVO.setSource(DbShareField.IN_WH);
        return dbShardVO;
    }

}
