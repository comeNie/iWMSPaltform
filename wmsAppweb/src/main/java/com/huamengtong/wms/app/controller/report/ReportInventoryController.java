package com.huamengtong.wms.app.controller.report;

import com.huamengtong.wms.core.formwork.db.util.DbShareField;
import com.huamengtong.wms.core.web.BaseController;
import com.huamengtong.wms.core.web.ResponseResult;
import com.huamengtong.wms.dto.inventory.TWmsInventoryLogDTO;
import com.huamengtong.wms.dto.report.TWmsReportInventoryDTO;
import com.huamengtong.wms.inventory.service.IProInventoryLogService;
import com.huamengtong.wms.report.service.IReportInventorySalesService;
import com.huamengtong.wms.report.service.IReportInventoryService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by Edwin on 2016/12/7.
 */
@RestController
@RequestMapping(value = "/report")
public class ReportInventoryController extends BaseController {

    @Autowired
    IReportInventoryService reportInventoryService;

    @Autowired
    IProInventoryLogService proInventoryLogService;

    @Autowired
    IReportInventorySalesService reportInventorySalesService;


    /***
     * 库存明细报表查询
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/inventory", method = RequestMethod.GET)
    public ResponseResult getInventoryList(TWmsReportInventoryDTO reportInventoryDTO) throws Exception {
        return getSucResultData(reportInventoryService.getReportInventory(reportInventoryDTO, getDbShardVO(DbShareField.IN_WH)));
    }

    /***
     * 库存明细报表导出
     * @param paramMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/inventory/export", method = RequestMethod.GET)
    public ResponseResult exportInventoryList(@RequestParam Map paramMap) throws Exception {
        TWmsReportInventoryDTO reportInventoryDTO = new TWmsReportInventoryDTO();
        if (paramMap.containsKey("sku") && StringUtils.isNotEmpty(MapUtils.getString(paramMap, "sku"))) {
            reportInventoryDTO.setSku(MapUtils.getString(paramMap, "sku"));
        }
        if (paramMap.containsKey("skuBarcode") && StringUtils.isNotEmpty(MapUtils.getString(paramMap, "skuBarcode"))) {
            reportInventoryDTO.setSkuBarcode(MapUtils.getString(paramMap, "skuBarcode"));
        }
        if (paramMap.containsKey("skuName") && StringUtils.isNotEmpty(MapUtils.getString(paramMap, "skuName"))) {
            reportInventoryDTO.setSkuName(MapUtils.getString(paramMap, "skuName"));
        }
        if (paramMap.containsKey("cargoOwnerId") && MapUtils.getLong(paramMap, "cargoOwnerId") != null) {
            reportInventoryDTO.setCargoOwnerId(MapUtils.getLong(paramMap, "cargoOwnerId"));
        }
        if (paramMap.containsKey("storageRoomId") && MapUtils.getLong(paramMap, "storageRoomId") != null) {
            reportInventoryDTO.setStorageRoomId(MapUtils.getLong(paramMap, "storageRoomId"));
        }
        if (paramMap.containsKey("inventoryCreateTimeFrom") && MapUtils.getLong(paramMap, "inventoryCreateTimeFrom") != null) {
            reportInventoryDTO.setInventoryCreateTimeFrom(MapUtils.getLong(paramMap, "inventoryCreateTimeFrom"));
        }
        if (paramMap.containsKey("inventoryCreateTimeTo") && MapUtils.getLong(paramMap, "inventoryCreateTimeTo") != null) {
            reportInventoryDTO.setInventoryCreateTimeTo(MapUtils.getLong(paramMap, "inventoryCreateTimeTo"));
        }
        reportInventoryDTO.setPage(0);
        reportInventoryDTO.setPageSize(0);
        return getSucResultData(reportInventoryService.getReportInventory(reportInventoryDTO, getDbShardVO(DbShareField.IN_WH)));
    }


    @RequestMapping(value = "/inventoryLog", method = RequestMethod.GET)
    public ResponseResult getInventoryLogList(TWmsInventoryLogDTO inventoryLogDTO) throws Exception {
        return getSucResultData(reportInventoryService.getInventoryLog(inventoryLogDTO, getDbShardVO(DbShareField.IN_WH)));
    }

    @RequestMapping(value = "/inventoryLog/export", method = RequestMethod.GET)
    public ResponseResult exportInventoryLogList(@RequestParam Map map) throws Exception {
        TWmsInventoryLogDTO inventoryLogDTO = new TWmsInventoryLogDTO();
        if (map.containsKey("sku") && StringUtils.isNotEmpty(MapUtils.getString(map, "sku"))) {
            inventoryLogDTO.setSku(MapUtils.getString(map, "sku"));
        }
        if (map.containsKey("barCode") && StringUtils.isNotEmpty(MapUtils.getString(map, "barCode"))) {
            inventoryLogDTO.setBarCode(MapUtils.getString(map, "barCode"));
        }
        if (map.containsKey("skuName") && StringUtils.isNotEmpty(MapUtils.getString(map, "skuName"))) {
            inventoryLogDTO.setSkuName(MapUtils.getString(map, "skuName"));
        }
        if (map.containsKey("typeCode") && StringUtils.isNotEmpty(MapUtils.getString(map, "typeCode"))) {
            inventoryLogDTO.setTypeCode(MapUtils.getString(map, "typeCode"));
        }
        if (map.containsKey("orderId") && MapUtils.getLong(map, "orderId") != null) {
            inventoryLogDTO.setOrderId(MapUtils.getLong(map, "orderId"));
        }
        if (map.containsKey("fromTime") && MapUtils.getLong(map, "fromTime") != 0) {
            inventoryLogDTO.setFromTime(MapUtils.getLong(map, "fromTime"));
        }
        if (map.containsKey("toTime") && MapUtils.getLong(map, "toTime") != 0) {
            inventoryLogDTO.setToTime(MapUtils.getLong(map, "toTime"));
        }
        inventoryLogDTO.setPage(0);
        inventoryLogDTO.setPageSize(0);
        return getSucResultData(reportInventoryService.getInventoryLog(inventoryLogDTO, getDbShardVO(DbShareField.IN_WH)));
    }


    @RequestMapping(value = "/inventorySummary", method = RequestMethod.GET)
    public ResponseResult getInventorySummaryList(TWmsReportInventoryDTO reportInventoryDTO) throws Exception {
        return getSucResultData(reportInventoryService.getInventorySummary(reportInventoryDTO, getDbShardVO(DbShareField.IN_WH)));
    }

    @RequestMapping(value = "/inventorySummary/export", method = RequestMethod.GET)
    public ResponseResult exportInventorySummaryList(Map paramMap) throws Exception {
        TWmsReportInventoryDTO reportInventoryDTO = new TWmsReportInventoryDTO();
        if (paramMap.containsKey("sku") && StringUtils.isNotEmpty(MapUtils.getString(paramMap, "sku"))) {
            reportInventoryDTO.setSku(MapUtils.getString(paramMap, "sku"));
        }
        if (paramMap.containsKey("skuBarcode") && StringUtils.isNotEmpty(MapUtils.getString(paramMap, "skuBarcode"))) {
            reportInventoryDTO.setSkuBarcode(MapUtils.getString(paramMap, "skuBarcode"));
        }
        if (paramMap.containsKey("skuName") && StringUtils.isNotEmpty(MapUtils.getString(paramMap, "skuName"))) {
            reportInventoryDTO.setSkuName(MapUtils.getString(paramMap, "skuName"));
        }
        if (paramMap.containsKey("cargoOwnerId") && MapUtils.getLong(paramMap, "cargoOwnerId") != null) {
            reportInventoryDTO.setCargoOwnerId(MapUtils.getLong(paramMap, "cargoOwnerId"));
        }
        reportInventoryDTO.setPage(0);
        reportInventoryDTO.setPageSize(0);
        return getSucResultData(reportInventoryService.getInventorySummary(reportInventoryDTO, getDbShardVO(DbShareField.IN_WH)));
    }

    /***
     * 库存明细报表查询
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/inventory/{cargoOwnerId}", method = RequestMethod.GET)
    public ResponseResult getInventoryListByCargoOwner(@PathVariable Long cargoOwnerId, @RequestParam Map map) throws Exception {
        TWmsReportInventoryDTO reportInventoryDTO = new TWmsReportInventoryDTO();
        reportInventoryDTO.setCargoOwnerId(cargoOwnerId);
        if (map.containsKey("page")) {
            reportInventoryDTO.setPage(Integer.parseInt(map.get("page").toString()));
        }
        if (map.containsKey("pageSize")) {
            reportInventoryDTO.setPageSize(Integer.parseInt(map.get("pageSize").toString()));
        }
        if (map.containsKey("skuBarcode")) {
            reportInventoryDTO.setSkuBarcode(map.get("skuBarcode").toString());
        }
        if (map.containsKey("skuName")) {
            reportInventoryDTO.setSkuName(map.get("skuName").toString());
        }
        return getSucResultData(reportInventoryService.getReportInventory(reportInventoryDTO, getDbShardVO(DbShareField.IN_WH)));
    }


    /***
     * 进销存报表
     * @param paramMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/integration/reportSales",method = RequestMethod.GET)
    public ResponseResult queryInventorySalesList(@RequestParam  Map paramMap)throws  Exception {
        return  getSucResultData(reportInventorySalesService.getInventorySalesReportList(paramMap, getDbShardVO(DbShareField.IN_WH)));
    }


}
