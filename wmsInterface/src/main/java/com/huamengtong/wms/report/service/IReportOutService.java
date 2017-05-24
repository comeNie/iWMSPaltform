package com.huamengtong.wms.report.service;

import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.outwh.TWmsShipmentDetailDTO;
import com.huamengtong.wms.entity.outwh.TWmsShipmentDetailEntity;

import java.util.List;

/**
 * Created by could.hao on 2016/12/12.
 */
public interface IReportOutService {
    PageResponse<List<TWmsShipmentDetailEntity>> queryShipmentDetail(TWmsShipmentDetailDTO shipmentDetailDTO, DbShardVO dbShardVO);
}
