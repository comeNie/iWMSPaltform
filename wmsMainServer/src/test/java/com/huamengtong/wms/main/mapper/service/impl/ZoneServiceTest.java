package com.huamengtong.wms.main.mapper.service.impl;

import com.huamengtong.wms.dto.TWmsZoneDTO;
import com.huamengtong.wms.entity.main.TWmsZoneEntity;
import com.huamengtong.wms.main.mapper.common.SpringTxTestCase;
import com.huamengtong.wms.main.service.IZoneService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class ZoneServiceTest extends SpringTxTestCase {

    @Autowired
    IZoneService zoneService;

    @Test
    public void testSelectById(){
        zoneService.findByPrimaryKey(3L);
        System.out.println("Selected success");
    }

    @Test
    public void testDeleteById(){
        zoneService.removeZone(3L);
        System.out.println("Deleted success");
    }

    @Test
    public void testCreateZone(){
        TWmsZoneDTO  zoneDTO = new TWmsZoneDTO();
        zoneDTO.setId(125L);
        zoneDTO.setWarehouseId(789L);
        zoneDTO.setZoneNo("库尔勒");
        zoneDTO.setCreateUser("YYY");
        zoneDTO.setCreateTime(7831278667326L);
        zoneDTO.setUpdateUser("YYm");
        zoneDTO.setUpdateTime(8998767324638L);
        zoneService.createZone(zoneDTO);
        System.out.println("Create success");
    }

    @Test
    public void testUpdateZone(){
        TWmsZoneDTO zoneDTO = new TWmsZoneDTO();
        zoneDTO.setId(123L);
        zoneDTO.setWarehouseId(667L);
        zoneDTO.setZoneNo("珠海");
        zoneDTO.setDescription("修改过的");
        zoneDTO.setUpdateUser("YGG");
        zoneDTO.setUpdateTime(7238428642642963492L);
        zoneService.modifyZone(zoneDTO);
        System.out.println("Update Success!");
    }



}
