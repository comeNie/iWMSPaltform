package com.huamengtong.wms.main.mapper.service.impl;

import com.huamengtong.wms.dto.TWmsLocationDTO;
import com.huamengtong.wms.main.mapper.common.SpringTxTestCase;
import com.huamengtong.wms.main.service.ILocationService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class LocationServiceTest extends SpringTxTestCase {

    @Autowired
    ILocationService locationService;

//    @Test
//    public void TestLocationInsert(){
//        TWmsLocationDTO locationEntity = new TWmsLocationDTO();
//        locationEntity.setWarehouseId(9L);
//        locationEntity.setZoneId(9L);
//        locationEntity.setLocationNo("three");
//        locationEntity.setIsActive(new Byte("1"));
//        locationEntity.setTypeCode("货架类型");
//        locationEntity.setClassCode("存货分类");
//        locationEntity.setHandlingCode("操作方式");
//        locationEntity.setCategoryCode("存放方式");
//        locationEntity.setAbcCode("abc分类");
//        locationEntity.setDescription("完美描述");
//        locationEntity.setIsMultisku(new Byte("1"));
//        locationEntity.setIsMultilot(new Byte("1"));
//        locationEntity.setPutawaySeq(1);
//        locationEntity.setPickupSeq(2);
//        locationEntity.setVolume(1.1);
//        locationEntity.setLength(0.99999);
//        locationEntity.setWidth(1.1);
//        locationEntity.setHeight(1.1);
//        locationEntity.setWeightcapacity(1.1);
//        locationEntity.setIsDefault(new Byte("1"));
//        locationEntity.setCreateUser("管理员");
//        locationEntity.setCreateTime(9L);
//        locationEntity.setUpdateUser("管理二号");
//        locationEntity.setUpdateTime(8L);
//        locationEntity.setIsDel(new Byte("1"));
//        locationEntity.setIsUsed(new Byte("1"));
//        locationService.createLocation(locationEntity);
//        System.out.print("-------------------------");
//
//    }

    @Test
    public void testUpdateLocation(){
        TWmsLocationDTO locationEntity = new TWmsLocationDTO();
        locationEntity.setId(5L);
        locationEntity.setLocationNo("five");
        locationEntity.setAbcCode("毛线分类");
        locationEntity.setIsDel(new Byte("5"));
        locationEntity.setIsUsed(new Byte("5"));
        locationService.modifyLocation(locationEntity);
        System.out.print("~~~~~~~~~~success");
    }

    @Test
    public void testDeleteLocation(){
        locationService.removeByPrimaryKey(1L);
        System.out.print("~~~~~~~~~~success");

    }

    @Test
    public void testSelectLocation(){
        //locationService.findByPrimaryKey(2L);
        System.out.println(locationService.findByPrimaryKey(2L).toString());
        System.out.print("~~~~~~~~~~success");
    }
}
