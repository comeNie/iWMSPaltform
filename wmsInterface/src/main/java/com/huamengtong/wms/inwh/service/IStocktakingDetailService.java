package com.huamengtong.wms.inwh.service;

import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.inwh.TWmsStocktakingDetailDTO;
import com.huamengtong.wms.entity.inwh.TWmsStocktakingDetailEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by mario on 2016/11/22.
 */
public interface IStocktakingDetailService {

    TWmsStocktakingDetailEntity findByPrimaryKey(Long id, DbShardVO dbShardVO);

    MessageResult createStocktakingDetailDetail(TWmsStocktakingDetailDTO stocktakingDetailDTO,DbShardVO dbShardVO);

    MessageResult removeStocktakingDetail(Long id,DbShardVO dbShardVO);

    MessageResult modifyStocktakingDetail(TWmsStocktakingDetailDTO stocktakingDetailDTO,DbShardVO dbShardVO );

    PageResponse<List> queryStocktakingDetailByHeader(Map map, DbShardVO dbShardVO);

    List<TWmsStocktakingDetailEntity> getStocktakingDetails(Long headerId,DbShardVO dbShardVO);

    TWmsStocktakingDetailEntity findStocktakingDetailFromCargo(Long skuId,Long headerId,DbShardVO dbShardVO);

    MessageResult modifySubmitStocktakingDetail(Long id,Integer countQty,String operationUser,DbShardVO dbShardVO);



}
