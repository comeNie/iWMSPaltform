package com.huamengtong.wms.app.controller.inwh;

import com.huamengtong.wms.core.formwork.db.util.DbShareField;
import com.huamengtong.wms.core.web.BaseController;
import com.huamengtong.wms.core.web.ResponseResult;
import com.huamengtong.wms.dto.inventory.TWmsInventoryDTO;
import com.huamengtong.wms.dto.report.TWmsReportInventoryDTO;
import com.huamengtong.wms.inventory.service.IInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by could.hao on 2017/1/5.
 */
@RestController
@RequestMapping(value = "/inventory")
public class InventoryController extends BaseController{

    @Autowired
    private IInventoryService inventoryService;
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseResult getInventoryList(TWmsReportInventoryDTO reportInventoryDTO) throws  Exception{
        return getSucResultData(inventoryService.getReportInventory(reportInventoryDTO,getDbShardVO(DbShareField.IN_WH)));
    }


    @RequestMapping(value = "/popup/{cargoOwnerId}",method = RequestMethod.GET)
    public ResponseResult getInventoryByCargoOwnerId(@RequestParam Map map, TWmsInventoryDTO inventoryDTO)throws  Exception{
        return getSucResultData(inventoryService.queryInventoryByCargoOwnerId( map,inventoryDTO,getDbShardVO(DbShareField.IN_WH)));

    }
}
