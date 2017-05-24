package com.huamengtong.wms.report.service;

import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.inventory.TWmsInventoryLogDTO;
import com.huamengtong.wms.dto.report.TWmsReportInventoryDTO;
import com.huamengtong.wms.entity.inventory.TWmsInventoryLogEntity;

import java.util.List;

/**
 * Created by Edwin on 2016/12/7.
 */
public interface IReportInventoryService {

    PageResponse<List> getReportInventory(TWmsReportInventoryDTO reportInventoryDTO, DbShardVO dbShardVO);

    PageResponse<List<TWmsInventoryLogEntity>> getInventoryLog(TWmsInventoryLogDTO inventoryLogDTO, DbShardVO dbShardVO);

    PageResponse<List<TWmsReportInventoryDTO>> getInventorySummary(TWmsReportInventoryDTO reportInventoryDTO, DbShardVO dbShardVO);

}
