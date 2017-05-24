package com.huamengtong.wms.inventory.service;

import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.inventory.TWmsProInventoryDTO;
import com.huamengtong.wms.entity.inventory.TWmsProInventoryEntity;
import com.huamengtong.wms.vo.ProducerProcessVO;

import java.util.List;
import java.util.Map;

/**
 * Created by could.hao on 2017/1/6.
 */
public interface IProInventoryService {

    PageResponse<List<TWmsProInventoryEntity>> queryProInventoryPages(TWmsProInventoryDTO proInventoryDTO, DbShardVO dbShardVO);

    MessageResult updateProInventory(TWmsProInventoryDTO proInventoryDTO,DbShardVO dbShardVO);

    List<TWmsProInventoryEntity> getProInventoryListForReport(TWmsProInventoryDTO proInventoryDTO,List<Long> list, DbShardVO dbShardVO);

    MessageResult deleteProInventory(Long id,DbShardVO dbShardVO);

    TWmsProInventoryEntity findProInventoryById(Long id,DbShardVO dbShardVO);

    Integer getPageCountForReport(TWmsProInventoryDTO proInventoryDTO,List<Long> list, DbShardVO dbShardVO);

    List<TWmsProInventoryEntity> getProInventoryList(TWmsProInventoryDTO proInventoryDTO, DbShardVO dbShardVO);

    MessageResult updateProInventoryBatch(List<TWmsProInventoryDTO> list,DbShardVO dbShardVO);

    List<ProducerProcessVO> getProInventoryReport(Map map, DbShardVO dbShardVO);

    MessageResult createProInventory(TWmsProInventoryDTO proInventoryDTO, List<Map> proInventoryDTOList,DbShardVO dbShardVO);

    MessageResult executeAuditProInventory(Long id , String operationUser , DbShardVO dbShardVO);

    MessageResult executeRevokeProInventory(Long id , String operationUser , DbShardVO dbShardVO);
}
