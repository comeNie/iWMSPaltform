package com.huamengtong.wms.app.controller.report;

import com.huamengtong.wms.core.formwork.db.util.DbShareField;
import com.huamengtong.wms.core.web.BaseController;
import com.huamengtong.wms.core.web.ResponseResult;
import com.huamengtong.wms.report.service.IReportReceiptService;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping(value = "/report/receipt")
public class ReportReceiptController extends BaseController {

    @Autowired
    IReportReceiptService reportReceiptService;


    @RequestMapping(value = "/detail",method = RequestMethod.GET)
    public ResponseResult getReceiptDetailList(@RequestParam Map map)throws Exception{
        map.put("offset",getOffset(MapUtils.getInteger(map,"page"),MapUtils.getInteger(map,"pageSize")));
        return getSucResultData(reportReceiptService.getReceiptDetailListByMap(map,getDbShardVO(DbShareField.IN_WH)));
    }

    @RequestMapping(value = "/detail/export",method = RequestMethod.GET)
    public ResponseResult exportReceiptDetailList(@RequestParam Map map)throws Exception{
        map.remove("page");
        map.remove("pageSize");
        return getSucResultData(reportReceiptService.getReceiptDetailListByMap(map,getDbShardVO(DbShareField.IN_WH)));
    }

    @RequestMapping(value = "/summary",method = RequestMethod.GET)
    public ResponseResult getReceiptSummaryList(@RequestParam Map map)throws Exception{
        map.put("offset",getOffset(MapUtils.getInteger(map,"page"),MapUtils.getInteger(map,"pageSize")));
        return getSucResultData(reportReceiptService.getReceiptSummaryListByMap(map,getDbShardVO(DbShareField.IN_WH)));
    }

    @RequestMapping(value = "/summary/export",method = RequestMethod.GET)
    public ResponseResult exportReceiptSummaryList(@RequestParam Map map)throws Exception{
        map.remove("page");
        map.remove("pageSize");
        return getSucResultData(reportReceiptService.getReceiptSummaryListByMap(map,getDbShardVO(DbShareField.IN_WH)));
    }
}
