package com.huamengtong.wms.inwh.service;

import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.inwh.TWmsAdjustmentHeaderDTO;
import com.huamengtong.wms.entity.inwh.TWmsAdjustmentHeaderEntity;

import java.util.List;

/**
 * Created by mario on 2016/11/16.
 */
public interface IAdjustmentHeaderService {
    PageResponse<List<TWmsAdjustmentHeaderEntity>> getAdjustmentHeader(TWmsAdjustmentHeaderDTO adjustmentHeaderDTO, DbShardVO dbShardVO);

    TWmsAdjustmentHeaderEntity findByPrimaryKey(Long id,DbShardVO dbShardVO);

    MessageResult createAdjustmentHeader(TWmsAdjustmentHeaderDTO adjustmentHeaderDTO,DbShardVO dbShardVO);

    MessageResult removeAdjustmentHeader(Long id,DbShardVO dbShardVO);

    MessageResult modifyAdjustmentHeader(TWmsAdjustmentHeaderDTO adjustmentHeaderDTO,DbShardVO dbShardVO);

    MessageResult updateAdjustmentHeaderStatus(Long adjustId, String statusCode,String operationUser, DbShardVO dbShardVO);

}
