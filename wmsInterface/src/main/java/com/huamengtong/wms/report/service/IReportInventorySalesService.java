package com.huamengtong.wms.report.service;


import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.domain.TWmsInventorySalesReportEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by mario on 2017/3/15.
 */
public interface IReportInventorySalesService {

    PageResponse<List<TWmsInventorySalesReportEntity>> getInventorySalesReportList(Map paramMap, DbShardVO dbShardVO);

}
