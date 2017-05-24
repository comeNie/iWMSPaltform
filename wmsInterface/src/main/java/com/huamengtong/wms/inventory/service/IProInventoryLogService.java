package com.huamengtong.wms.inventory.service;

import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.inventory.TWmsProInventoryLogDTO;
import com.huamengtong.wms.entity.inventory.TWmsProInventoryLogEntity;

import java.util.List;

/**
 * Created by StickT on 2017/1/9.
 */
public interface IProInventoryLogService {

    PageResponse<List<TWmsProInventoryLogEntity>> getProInventoryList(TWmsProInventoryLogDTO proInventoryLogDTO, DbShardVO dbShardVO);

    MessageResult createProInventoryLog(TWmsProInventoryLogEntity proInventoryLogEntity,DbShardVO dbShardVO);
}
