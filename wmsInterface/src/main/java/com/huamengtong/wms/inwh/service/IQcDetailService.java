package com.huamengtong.wms.inwh.service;


import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.inwh.TWmsAsnDetailDTO;
import com.huamengtong.wms.dto.inwh.TWmsQcDetailDTO;
import com.huamengtong.wms.entity.inwh.TWmsQcDetailEntity;

import java.util.List;
import java.util.Map;

public interface IQcDetailService {

    TWmsQcDetailEntity findByPrimaryKey(Long id, DbShardVO dbShardVO);

    MessageResult removeByPrimaryKey(Long id,String operationUser, DbShardVO dbShardVO);

    MessageResult createQcDetail(TWmsQcDetailDTO qcDetailDTO,DbShardVO dbShardVO);

    MessageResult updateQcDetail(TWmsQcDetailDTO qcDetailDTO,DbShardVO dbShardVO);

    PageResponse<List> queryDetailsByHeaderId(Map map,DbShardVO dbShardVO);

    List<TWmsQcDetailEntity> findQcDetailsByHeaderId(Long headerId,DbShardVO dbShardVO);

    MessageResult createQcDetailFromAsn(Long qcId, TWmsAsnDetailDTO asnDetailDTO, String operationUser, DbShardVO dbShardVO);

    MessageResult deleteQcDetailsByHeaderId(Long headerId,DbShardVO dbShardVO);

    TWmsQcDetailEntity findByHeaderIdAndBarcode(Long headerId,String barcode,DbShardVO dbShardVO);

    MessageResult updateQcDetailFromQcReceipt(TWmsQcDetailDTO qcDetailDTO,Integer qcQty,String storageRoomId,String operationUser,DbShardVO dbShardVO);

    MessageResult updateQcDetailFromQcFinish(List<TWmsQcDetailEntity> qcDetailEntityList,String operationUser,DbShardVO dbShardVO);


}
