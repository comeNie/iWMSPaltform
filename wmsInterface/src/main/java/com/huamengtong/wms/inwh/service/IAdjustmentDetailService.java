package com.huamengtong.wms.inwh.service;

import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.inwh.TWmsAdjustmentDetailDTO;
import com.huamengtong.wms.entity.inwh.TWmsAdjustmentDetailEntity;
import com.huamengtong.wms.vo.SkuInventoryVO;

import java.util.List;
import java.util.Map;

/**
 * Created by mario on 2016/11/16.
 */
public interface IAdjustmentDetailService {

    TWmsAdjustmentDetailEntity findByPrimaryKey(Long id, DbShardVO dbShardVO);

    MessageResult createAdjustmentDetail(TWmsAdjustmentDetailDTO adjustmentDetailDTO,DbShardVO dbShardVO);

    MessageResult removeAdjustmentDetail(Long id,DbShardVO dbShardVO);

    MessageResult modifyAdjustmentDetail(TWmsAdjustmentDetailDTO adjustmentDetailDTO,DbShardVO dbShardVO );

    PageResponse<List> queryAdjustmentDetailByHeader(Map map,DbShardVO dbShardVO);

    List<TWmsAdjustmentDetailEntity> getAdjustmentDetails(Long adjustId,DbShardVO dbShardVO);

    PageResponse<List<SkuInventoryVO>> getSkuInventoryList(Map map,DbShardVO dbShardVO);

    MessageResult createAdjustmentDetailByMove(TWmsAdjustmentDetailDTO adjustmentDetailDTO,DbShardVO dbShardVO);
}
