package com.huamengtong.wms.inwh.service;

import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.inwh.TWmsStocktakingHeaderDTO;
import com.huamengtong.wms.entity.inwh.TWmsStocktakingHeaderEntity;

import java.util.List;

/**
 * Created by mario on 2016/11/22.
 */
public interface IStocktakingHeaderService {

    PageResponse<List<TWmsStocktakingHeaderEntity>> getStocktakingHeader(TWmsStocktakingHeaderDTO stocktakingHeaderDTO, DbShardVO dbShardVO);

    TWmsStocktakingHeaderEntity findByPrimaryKey(Long id,DbShardVO dbShardVO);

    MessageResult createStocktakingHeader(TWmsStocktakingHeaderDTO stocktakingHeaderDTO, DbShardVO dbShardVO);

    MessageResult removeStocktakingHeader(Long id,DbShardVO dbShardVO);

    MessageResult modifyStocktakingHeader(TWmsStocktakingHeaderDTO stocktakingHeaderDTO,DbShardVO dbShardVO);

    MessageResult updateSubmitStocktaking(Long headerId,String operationUser,DbShardVO dbShardVO);

    MessageResult updateConfirmStocktaking(Long headerId,String operationUser,DbShardVO dbShardVO);
}
