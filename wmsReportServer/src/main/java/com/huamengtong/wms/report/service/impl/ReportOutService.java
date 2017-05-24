package com.huamengtong.wms.report.service.impl;

import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.service.BaseService;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.outwh.TWmsShipmentDetailDTO;
import com.huamengtong.wms.entity.outwh.TWmsShipmentDetailEntity;
import com.huamengtong.wms.outwh.service.IShipmentDetailService;
import com.huamengtong.wms.report.service.IReportOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by could.hao on 2016/12/12.
 */
@Service
public class ReportOutService extends BaseService implements IReportOutService {

    @Autowired
    IShipmentDetailService shipmentDetailService;

    @Override
    public PageResponse<List<TWmsShipmentDetailEntity>> queryShipmentDetail(TWmsShipmentDetailDTO shipmentDetailDTO, DbShardVO dbShardVO) {
        return shipmentDetailService.queryShipmentDetail(shipmentDetailDTO,dbShardVO);
    }
}
