package com.huamengtong.wms.inwh.service;


import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.inwh.TWmsAsnHeaderDTO;
import com.huamengtong.wms.dto.inwh.TWmsQcHeaderDTO;
import com.huamengtong.wms.entity.inwh.TWmsQcHeaderEntity;

import java.math.BigDecimal;
import java.util.List;

public interface IQcHeaderService {
    PageResponse<List<TWmsQcHeaderEntity>> getQcHeaders(TWmsQcHeaderDTO qcHeaderDTO, DbShardVO dbShardVO);

    TWmsQcHeaderEntity findByPrimaryKey(Long id,DbShardVO dbShardVO);

    MessageResult removeByPrimaryKey(Long id ,DbShardVO dbShardVO);

    MessageResult insertQcHeader(TWmsQcHeaderDTO qcHeaderDTO,DbShardVO dbShardVO);

    MessageResult modifyQcHeader(TWmsQcHeaderDTO qcHeaderDTO,DbShardVO dbShardVO);

    MessageResult updateSubmitQc(Long headerId,String operationUser,DbShardVO dbShardVO);

    MessageResult updateRepealedQc(Long headerId,String operationUser,DbShardVO dbShardVO);

    MessageResult updateFinishQc(Long headerId,String operationUser,DbShardVO dbShardVO);

    TWmsQcHeaderEntity findByAsnId(Long asnId,DbShardVO dbShardVO);

    MessageResult createQcHeaderFromAsn(Long id, TWmsAsnHeaderDTO asnHeaderDTO, String operationUser, DbShardVO dbShardVO);

    MessageResult createReceipt(Long qcId, String barcode, String operationUser, Integer qcQty,String inventoryStatusCode,String storageRoomId,  BigDecimal warehouseTemp, DbShardVO dbShardVO);


}
