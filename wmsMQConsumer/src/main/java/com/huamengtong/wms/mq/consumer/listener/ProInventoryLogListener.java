package com.huamengtong.wms.mq.consumer.listener;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.huamengtong.wms.constants.GlobalConstants;
import com.huamengtong.wms.core.formwork.db.domain.CurrentUserEntity;
import com.huamengtong.wms.core.formwork.db.util.DbShareField;
import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.mq.consumer.ConsumeListener;
import com.huamengtong.wms.entity.inventory.TWmsProInventoryLogEntity;
import com.huamengtong.wms.inventory.service.IProInventoryLogService;
import com.huamengtong.wms.utils.SerializationUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by StickT on 2017/1/11.
 */
@Component
public class ProInventoryLogListener extends ConsumeListener {

    public static final Logger log = LoggerFactory.getLogger(ProInventoryLogListener.class);

    @Autowired
    IProInventoryLogService proInventoryLogService;

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        try {
            Map map = (Map) SerializationUtils.H2Deserialize(msgs.get(0).getBody(), Map.class);
            TWmsProInventoryLogEntity proInventoryLogEntity = (TWmsProInventoryLogEntity)map.get("TWmsProInventoryLogEntity");
            Long warehouseId = MapUtils.getLong(map,"warehouseId");
            Long tenantId = MapUtils.getLong(map,"tenantId");
            proInventoryLogService.createProInventoryLog(proInventoryLogEntity,getDbShardVO(tenantId,warehouseId));
            log.info("mq:exec mq message is{} ex:{} ",msgs.get(0));
        }catch (Exception e){
            log.error("mq:exec mq message is{} ex:{} ",msgs.get(0),e.getMessage());
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

    private DbShardVO getDbShardVO(Long tenantId, Long warehouseId) {
        CurrentUserEntity user = new CurrentUserEntity();
        user.setUserName(GlobalConstants.GLOBAL_USER_NAME);
        user.setTenantId(tenantId);
        DbShardVO dbShardVO = DbShardVO.getInstance(user);
        dbShardVO.setWarehouseId(warehouseId);
        dbShardVO.setSource(DbShareField.IN_WH);
        return dbShardVO;
    }




}
