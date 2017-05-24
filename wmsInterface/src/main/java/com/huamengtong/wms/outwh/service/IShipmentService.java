package com.huamengtong.wms.outwh.service;

import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.entity.outwh.TWmsShipmentHeaderEntity;

/**
 * Created by Edwin on 2016/10/20.
 */
public interface  IShipmentService {

    MessageResult updateShipmentMqAllocate(Long shipmentId, DbShardVO dbShardVO);

    MessageResult updateInventoryFromDelivery(Long shipmentId,DbShardVO dbShardVO);

    MessageResult updateShipmentRepealed(TWmsShipmentHeaderEntity shipmentHeaderEntity, String operationUser, DbShardVO dbShardVO);
}
