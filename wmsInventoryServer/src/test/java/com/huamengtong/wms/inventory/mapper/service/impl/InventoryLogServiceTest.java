package com.huamengtong.wms.inventory.mapper.service.impl;


import com.huamengtong.wms.entity.inventory.TWmsInventoryLogEntity;
import com.huamengtong.wms.inventory.mapper.common.SpringTxTestCase;
import com.huamengtong.wms.inventory.service.impl.InventoryLogService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class InventoryLogServiceTest extends SpringTxTestCase {

    @Autowired
    private InventoryLogService inventoryLogService;

    @Test
    public void createTest(){
        TWmsInventoryLogEntity inventoryLog = new TWmsInventoryLogEntity();
        inventoryLog.setCreateTime(new Date().getTime());
        inventoryLog.setUpdateTime(new Date().getTime());
        inventoryLog.setCreateUser("admin");
        inventoryLog.setUpdateUser("could");
        inventoryLog.setSkuId(11L);
        inventoryLog.setTenantId(112L);
        inventoryLog.setWarehouseId(88L);
        inventoryLog.setInventoryId(4100L);
        inventoryLog.setTypeCode("A-type");
        inventoryLog.setOrderId(12345L);
        inventoryLog.setDetailId(22222L);
        inventoryLogService.createInventoryLog(inventoryLog,getDbshardVO());
    }

    @Test
    public void selectByPrimaryKey(){
        System.out.print(inventoryLogService.findByPrimaryKey(5001L,getDbshardVO()));
    }
}
