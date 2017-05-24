package com.huamengtong.wms.inventory.mapper.service.impl;

import com.huamengtong.wms.dto.inventory.TWmsInventoryDTO;
import com.huamengtong.wms.entity.outwh.TWmsShipmentDetailEntity;
import com.huamengtong.wms.entity.outwh.TWmsShipmentHeaderEntity;
import com.huamengtong.wms.inventory.mapper.common.SpringTxTestCase;
import com.huamengtong.wms.inventory.service.impl.InventoryService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InventoryServiceTest extends SpringTxTestCase {

    @Autowired
    InventoryService inventoryService;
    @Test
    public void addIn(){
        TWmsInventoryDTO inventoryDTO = new TWmsInventoryDTO();
        inventoryDTO.setUpdateTime(new Date().getTime());
        inventoryDTO.setUpdateUser("could");
        inventoryDTO.setCreateUser("could");
        inventoryDTO.setCreateTime(new Date().getTime());
        inventoryDTO.setAllocatedQty(1);
        inventoryDTO.setContainerNo("A41");
        inventoryDTO.setIsHold((byte)1);
        inventoryDTO.setWarehouseId(12000L);
        inventoryDTO.setAllocatedQty(1300);
        inventoryDTO.setContainerNo("AE34");
        inventoryDTO.setPrice(new BigDecimal("1.21"));
        inventoryDTO.setLocationId(123L);
        inventoryDTO.setVersion(123456);
        inventoryDTO.setTenantId(88L);
        inventoryDTO.setSkuId(1234L);
        inventoryDTO.setZoneId(111L);
        inventoryService.addInventory(inventoryDTO,getDbshardVO());
    }

    @Test
    public  void removeTest(){
        inventoryService.removeInventory(4001L,getDbshardVO());
    }

    @Test
    public  void modifyTest(){
        TWmsInventoryDTO inventoryDTO = new TWmsInventoryDTO();
        inventoryDTO.setVersion(111);
        inventoryDTO.setId(4001L);
        inventoryService.modifyInventory(inventoryDTO,getDbshardVO());
    }

    @Test
    public  void selectByIdTest(){
        System.out.print(inventoryService.findById(4001L,getDbshardVO()));
    }

    @Test
    public void allocationStrategyTest(){
        List<TWmsShipmentDetailEntity> list = new ArrayList<>();
        TWmsShipmentDetailEntity shipmentDetailEntity = new TWmsShipmentDetailEntity();
        shipmentDetailEntity.setSkuId(10055L);
        list.add(shipmentDetailEntity);
        TWmsShipmentHeaderEntity shipmentHeaderEntity = new TWmsShipmentHeaderEntity();
        shipmentHeaderEntity.setWarehouseId(88L);
        shipmentHeaderEntity.setCargoOwnerId(74L);
        inventoryService.updateAllocateInventory(list,16073L,shipmentHeaderEntity,getDbshardVO());
    }
}
