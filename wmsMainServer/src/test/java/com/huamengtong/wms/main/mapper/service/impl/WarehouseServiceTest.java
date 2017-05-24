package com.huamengtong.wms.main.mapper.service.impl;

import com.huamengtong.wms.dto.TWmsWarehouseDTO;
import com.huamengtong.wms.entity.main.TWmsWarehouseEntity;
import com.huamengtong.wms.main.mapper.common.SpringTxTestCase;
import com.huamengtong.wms.main.service.IWarehouseService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WarehouseServiceTest  extends SpringTxTestCase {

    @Autowired
    IWarehouseService warehouseService;

    @Test
    public void testCreateWarehouse(){
        TWmsWarehouseDTO warehouseDTO = new TWmsWarehouseDTO();
        warehouseDTO.setTenantId(88L);
        warehouseDTO.setTypeCode("RDC");
        warehouseDTO.setWarehouseNo("TJ1");
        warehouseDTO.setWarehouseName("天津仓");
        warehouseService.createWarehouse(warehouseDTO);
    }

    @Test
    public void testDeleteWarehouse(){
        warehouseService.removeWarehouse(2L);
    }

    @Test
    public void testGetWarehouse(){
        Map map = new HashMap<>();
        //map.put("offset",1);
        map.put("page","1");
        map.put("pageSize",20);
        warehouseService.queryUserByWarehousePage(map);
    }

    @Test
    public void testSearchWarehouseByIds(){
       List<Long> ids = Arrays.asList ( 88L,89L);
        List<TWmsWarehouseEntity> list = warehouseService.searchWarehouseByIds(ids);
        System.out.println(list.size());
    }


}
