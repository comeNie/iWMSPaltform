package com.huamengtong.wms.app.controller.report;

import com.huamengtong.wms.core.formwork.db.util.DbShareField;
import com.huamengtong.wms.core.web.BaseController;
import com.huamengtong.wms.core.web.ResponseResult;
import com.huamengtong.wms.dto.outwh.TWmsShipmentDetailDTO;
import com.huamengtong.wms.outwh.service.IShipmentDetailService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by could.hao on 2016/12/12.
 */
@RestController
@RequestMapping(value="/report/out")
public class ReportShipmentController extends BaseController {

    @Autowired
    IShipmentDetailService shipmentDetailService;

    @RequestMapping(value = "/detail",method = RequestMethod.GET)
    public ResponseResult getOutList(TWmsShipmentDetailDTO shipmentDetailDTO)throws Exception{
        return getSucResultData(shipmentDetailService.queryShipmentDetail(shipmentDetailDTO,getDbShardVO(DbShareField.OUT_WH)));
    }
    @RequestMapping(value = "/summary",method = RequestMethod.GET)
    public ResponseResult getOutSummaryList(TWmsShipmentDetailDTO shipmentDetailDTO)throws Exception{
        return getSucResultData(shipmentDetailService.queryShipmentSummaryDetail(shipmentDetailDTO,getDbShardVO(DbShareField.OUT_WH)));
    }

    /**
     * 出库报表明细导出
     * @param paramMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/detail/export",method = RequestMethod.GET)
    public ResponseResult getOutList(@RequestParam Map paramMap)throws Exception{
        TWmsShipmentDetailDTO shipmentDetailDTO = new TWmsShipmentDetailDTO();
        if (paramMap.containsKey("sku") && StringUtils.isNotEmpty(MapUtils.getString(paramMap,"sku"))){
            shipmentDetailDTO.setSku(MapUtils.getString(paramMap,"sku"));
        }
        if (paramMap.containsKey("skuBarcode") && StringUtils.isNotEmpty(MapUtils.getString(paramMap,"skuBarcode"))){
            shipmentDetailDTO.setSkuBarcode(MapUtils.getString(paramMap,"skuBarcode"));
        }
        if (paramMap.containsKey("skuName") && StringUtils.isNotEmpty(MapUtils.getString(paramMap,"skuName"))){
            shipmentDetailDTO.setSkuName(MapUtils.getString(paramMap,"skuName"));
        }
        if (paramMap.containsKey("cargoOwnerId") && MapUtils.getLong(paramMap,"cargoOwnerId") != null){
            shipmentDetailDTO.setCargoOwnerId(MapUtils.getLong(paramMap,"cargoOwnerId"));
        }
        if (paramMap.containsKey("storageRoomId") && MapUtils.getLong(paramMap,"storageRoomId") != null){
            shipmentDetailDTO.setStorageRoomId(MapUtils.getString(paramMap,"storageRoomId"));
        }
        if (paramMap.containsKey("startTime") && MapUtils.getLong(paramMap,"startTime") != null){
            shipmentDetailDTO.setStartTime(MapUtils.getLong(paramMap,"startTime"));
        }
        if (paramMap.containsKey("endTime") && MapUtils.getLong(paramMap,"endTime") != null){
            shipmentDetailDTO.setEndTime(MapUtils.getLong(paramMap,"endTime"));
        }
        shipmentDetailDTO.setPage(0);
        shipmentDetailDTO.setPageSize(0);
        return getSucResultData(shipmentDetailService.queryShipmentDetail(shipmentDetailDTO,getDbShardVO(DbShareField.OUT_WH)));
    }

    /**
     * 出库报表总计导出
     * @param paramMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/summary/export",method = RequestMethod.GET)
    public ResponseResult getOutSummaryList(@RequestParam Map paramMap)throws Exception{
        TWmsShipmentDetailDTO shipmentDetailDTO = new TWmsShipmentDetailDTO();
        if (paramMap.containsKey("skuBarcode") && StringUtils.isNotEmpty(MapUtils.getString(paramMap,"skuBarcode"))){
            shipmentDetailDTO.setSkuBarcode(MapUtils.getString(paramMap,"skuBarcode"));
        }
        if (paramMap.containsKey("skuName") && StringUtils.isNotEmpty(MapUtils.getString(paramMap,"skuName"))){
            shipmentDetailDTO.setSkuName(MapUtils.getString(paramMap,"skuName"));
        }
        if (paramMap.containsKey("cargoOwnerId") && MapUtils.getLong(paramMap,"cargoOwnerId") != null){
            shipmentDetailDTO.setCargoOwnerId(MapUtils.getLong(paramMap,"cargoOwnerId"));
        }
        shipmentDetailDTO.setPage(0);
        shipmentDetailDTO.setPageSize(0);
        return getSucResultData(shipmentDetailService.queryShipmentSummaryDetail(shipmentDetailDTO,getDbShardVO(DbShareField.OUT_WH)));
    }
}
