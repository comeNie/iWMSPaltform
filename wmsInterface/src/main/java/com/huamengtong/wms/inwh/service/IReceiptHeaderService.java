package com.huamengtong.wms.inwh.service;

import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.domain.TWmsReceiptHeaderPrintEntity;
import com.huamengtong.wms.dto.inwh.TWmsAsnHeaderDTO;
import com.huamengtong.wms.dto.inwh.TWmsQcHeaderDTO;
import com.huamengtong.wms.dto.inwh.TWmsReceiptHeaderDTO;
import com.huamengtong.wms.dto.report.TWmsReportReceiptDetailDTO;
import com.huamengtong.wms.entity.inwh.TWmsReceiptHeaderEntity;

import java.util.List;
import java.util.Map;

public interface IReceiptHeaderService {

    PageResponse<List<TWmsReceiptHeaderEntity>> getReceiptHeader(TWmsReceiptHeaderDTO receiptHeaderDTO, DbShardVO dbShardVO);

    TWmsReceiptHeaderEntity findByPrimaryKey(Long id, DbShardVO dbShardVO);

    MessageResult removeReceiptHeader(Long id, DbShardVO dbShardVO);

    MessageResult createReceiptHeader(TWmsReceiptHeaderDTO receiptHeaderDTO, DbShardVO dbShardVO);

    MessageResult modifyReceiptHeader(TWmsReceiptHeaderDTO receiptHeaderDTO, DbShardVO dbShardVO);

    TWmsReceiptHeaderEntity findByAsnId(Long asnId,DbShardVO dbShardVO);

    TWmsReceiptHeaderPrintEntity findPrintEntityByPrimaryKey(Long id, DbShardVO dbShardVO);

    MessageResult createReceiptHeaderFromQc(Long receiptId, String operationUser, TWmsQcHeaderDTO qcHeaderDTO, TWmsAsnHeaderDTO asnHeaderDTO, DbShardVO dbShardVO);

    MessageResult updateReceiptStatusFromQcFinish(Long asnId,String receiptStatusCode,String operationUser,DbShardVO dbShardVO);

    List<TWmsReceiptHeaderEntity> getHeaderListByIds(List<Long> ids,DbShardVO dbShardVO);

    List<TWmsReceiptHeaderEntity> getIdsByCargoOwnerId(Long cargoOwnerId, DbShardVO dbShardVO);

    List<TWmsReportReceiptDetailDTO> getReceiptDetailList(Map map, DbShardVO dbShardVO);

    Integer getReceiptDetailPageCount(Map map,DbShardVO dbShardVO);

    List<TWmsReportReceiptDetailDTO> getReceiptSummaryList(Map map,DbShardVO dbShardVO);

    Integer getReceiptSummaryPageCount(Map map,DbShardVO dbShardVO);

    MessageResult updateConfirmReceipt(Long[] headerIds,String operationUser,DbShardVO dbShardVO);

    MessageResult updateTransReceipt(Long[] headerIds,String operationUser,DbShardVO dbShardVO);



}
