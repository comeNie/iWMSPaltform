package com.huamengtong.wms.report.service;


import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.report.TWmsReportReceiptDetailDTO;

import java.util.List;
import java.util.Map;

public interface IReportReceiptService {


    PageResponse<List<TWmsReportReceiptDetailDTO>> getReceiptDetailList(TWmsReportReceiptDetailDTO reportReceiptDetailDTO, DbShardVO dbShardVO);

    PageResponse<List<TWmsReportReceiptDetailDTO>> getReceiptSummaryList(TWmsReportReceiptDetailDTO reportReceiptDetailDTO, DbShardVO dbShardVO);

    PageResponse<List<TWmsReportReceiptDetailDTO>> getReceiptDetailListByMap(Map map,DbShardVO dbShardVO);

    PageResponse<List<TWmsReportReceiptDetailDTO>> getReceiptSummaryListByMap(Map map,DbShardVO dbShardVO);



}
