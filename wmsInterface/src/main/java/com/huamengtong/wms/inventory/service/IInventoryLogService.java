package com.huamengtong.wms.inventory.service;


import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.inventory.TWmsInventoryLogDTO;
import com.huamengtong.wms.entity.inventory.TWmsInventoryLogEntity;

import java.util.List;

public interface IInventoryLogService {

    TWmsInventoryLogEntity findByPrimaryKey(Long id, DbShardVO dbShardVO);

    MessageResult createInventoryLog(TWmsInventoryLogEntity inventoryLogEntity, DbShardVO dbShardVO);

    PageResponse<List<TWmsInventoryLogEntity>> queryInventoryLogPages(TWmsInventoryLogDTO inventoryLogDTO,List<Long> skuIdList,Long fromTime,Long toTime, DbShardVO dbshardVO);

    List<TWmsInventoryLogEntity> getInventoryLogLists(TWmsInventoryLogDTO inventoryLogDTO,List<Long> skuIdList,Long fromTime,Long toTime,DbShardVO dbshardVO);

    List<Long> selectInventoryLogSkuId(TWmsInventoryLogDTO inventoryLogDTO,DbShardVO dbShardVO);

    Integer selectInventoryLogSkuIdCount(TWmsInventoryLogDTO inventoryLogDTO,DbShardVO dbShardVO);


}
