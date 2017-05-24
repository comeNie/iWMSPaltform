package com.huamengtong.wms.inwh.service;

import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.inwh.TWmsReceiptDetailDTO;
import com.huamengtong.wms.entity.inwh.TWmsAsnDetailEntity;
import com.huamengtong.wms.entity.inwh.TWmsReceiptDetailEntity;
import com.huamengtong.wms.entity.sku.TWmsSkuEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface IReceiptDetailService {

    TWmsReceiptDetailEntity findByPrimaryKey(Long id, DbShardVO dbShardVO);

    MessageResult removeByPrimaryKey(Long id, String operationUser, DbShardVO dbShardVO);

    MessageResult createReceiptDetail(TWmsReceiptDetailDTO receiptDetailDTO, DbShardVO dbShardVO);

    MessageResult modifyReceiptDetail(TWmsReceiptDetailDTO receiptDetailDTO, DbShardVO dbShardVO);

    PageResponse<List> queryReceiptDetailByHeader(Map map, DbShardVO dbShardVO);

    List<TWmsReceiptDetailEntity> getReceiptDetails(Long headerId, DbShardVO dbShardVO);

    MessageResult batchDeleteReceiptDetails(Long[] receiptIds, DbShardVO dbShardVO);

    MessageResult createReceiptDetailFromAsn(TWmsReceiptDetailDTO receiptDetailDTO,DbShardVO dbShardVO);

    MessageResult deleteReceiptDetailsByHeaderId(Long headerId,DbShardVO dbShardVO);

    MessageResult createReceiptDetailsFromQc(TWmsAsnDetailEntity asnDetailEntity, TWmsSkuEntity skuEntity, String operationUser, Long receiptHeaderId, String storageRoomId, Integer qcQty,String inventoryStatusCode, BigDecimal warehouseTemp, DbShardVO dbShardVO);

    List<TWmsReceiptDetailEntity> getReceiptDetailList(TWmsReceiptDetailDTO receiptDetailDTO,List<Long> headerIds,DbShardVO dbShardVO);

    Integer queryReceiptDetailPageCount(TWmsReceiptDetailDTO receiptDetailDTO,List<Long> headerIds,DbShardVO dbShardVO);

    Integer queryReceiptSummaryPageCount(TWmsReceiptDetailDTO receiptDetailDTO,List<Long> headerIds,DbShardVO dbShardVO);

    List<TWmsReceiptDetailEntity> getReceiptSummaryList(TWmsReceiptDetailDTO receiptDetailDTO,List<Long> headerIds,DbShardVO dbShardVO);
}
