package com.huamengtong.wms.inwh.service;

import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.inwh.TWmsAsnDetailDTO;
import com.huamengtong.wms.entity.inwh.TWmsAsnDetailEntity;

import java.util.List;
import java.util.Map;


public interface IAsnDetailService {

    PageResponse<List<TWmsAsnDetailEntity>> queryAsnDetailPages(Map map, DbShardVO dbShardVO);

    TWmsAsnDetailEntity findByPrimaryKey(Long id ,DbShardVO dbShardVO);

    MessageResult removeByPrimaryKey(Long id,String operationUser,DbShardVO dbShardVO);

    MessageResult insertAsnDetail(TWmsAsnDetailDTO asnDetailDTO, DbShardVO dbShardVO);

    MessageResult updateAsnDetail(TWmsAsnDetailDTO asnDetailDTO, DbShardVO dbShardVO);

    PageResponse<List> queryDetailsByHeaderId(Map map,DbShardVO dbShardVO);

    List<TWmsAsnDetailEntity> findAsnDetailsByHeaderId(Long headerId,DbShardVO dbShardVO);

    MessageResult deleteAsnDetailsByHeaderId(Long headerId,DbShardVO dbShardVO);

    List<TWmsAsnDetailEntity> findByHeaderIdAndSku(Long headerId,String sku,DbShardVO dbShardVO);

    MessageResult updateAsnDetailStatus(String operationCode,String operationUser,TWmsAsnDetailDTO asnDetailDTO,DbShardVO dbShardVO);

    MessageResult updateFromQcReceipt(Integer qcQty,String operationUser,TWmsAsnDetailDTO asnDetailDTO,DbShardVO dbShardVO);



}
