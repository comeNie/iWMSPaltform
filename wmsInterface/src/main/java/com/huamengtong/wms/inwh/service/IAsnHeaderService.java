package com.huamengtong.wms.inwh.service;

import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.inwh.TWmsAsnHeaderDTO;
import com.huamengtong.wms.entity.inwh.TWmsAsnHeaderEntity;

import java.util.List;

public interface IAsnHeaderService {

    PageResponse<List<TWmsAsnHeaderEntity>> getAsnHeaders(TWmsAsnHeaderDTO asnHeaderDTO, DbShardVO dbShardVO);

    TWmsAsnHeaderEntity findByPrimaryKey(Long id,DbShardVO dbShardVO);

    MessageResult removeByPrimaryKey(Long id , DbShardVO dbShardVO);

    MessageResult insertAsnHeader(TWmsAsnHeaderDTO asnHeaderDTO, DbShardVO dbShardVO);

    MessageResult updateAsnHeader(TWmsAsnHeaderDTO asnHeaderDTO, DbShardVO dbShardVO);

    MessageResult createQc(Long headerId,String operationUser,DbShardVO dbShardVO);

    MessageResult updateSubmitAsn(Long headerId,String operationUser,DbShardVO dbShardVO);

    MessageResult updateRepealedAsn(Long headerId,String operationUser,DbShardVO dbShardVO);

    MessageResult updateAsnFromQcFinish(Long id,Integer qcQty,String operationUser,String receiptStatusCode,DbShardVO dbShardVO);

}
