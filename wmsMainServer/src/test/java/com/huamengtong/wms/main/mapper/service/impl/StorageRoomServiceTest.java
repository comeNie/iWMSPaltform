package com.huamengtong.wms.main.mapper.service.impl;

import com.huamengtong.wms.dto.TWmsStorageRoomDTO;
import com.huamengtong.wms.main.mapper.common.SpringTxTestCase;
import com.huamengtong.wms.main.service.IStorageRoomService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


public class StorageRoomServiceTest extends SpringTxTestCase {

    @Autowired
    IStorageRoomService storageRoomService;

    @Test
    public void testDeleteById(){
        storageRoomService.removeByPrimaryKey(3L);
        System.out.println("delete success!");
    }

    @Test
    public void testCreateStorageRoom(){
        TWmsStorageRoomDTO storageRoomDTO = new TWmsStorageRoomDTO();
        storageRoomDTO.setId(127L);
        storageRoomDTO.setWarehouseId(222L);
        storageRoomDTO.setRoomNo("uuuu");
        storageRoomDTO.setTypeCode("CCC");
        storageRoomDTO.setIsActive((byte)1);
        storageRoomDTO.setDescription("苹果区");
        storageRoomDTO.setPriority(667);
        storageRoomDTO.setIsDefault((byte)2);
        storageRoomDTO.setCreateUser("YYM");
        storageRoomDTO.setCreateTime(66677L);
        storageRoomDTO.setUpdateUser("DD");
        storageRoomDTO.setUpdateTime(877878L);
        storageRoomService.createStorageRoom(storageRoomDTO);
        System.out.println("create success!");
    }

    @Test
    public void testUpdateStorageRoom(){
        TWmsStorageRoomDTO storageRoomDTO =new TWmsStorageRoomDTO();
        storageRoomDTO.setId(127L);
        storageRoomDTO.setWarehouseId(222L);
        storageRoomDTO.setRoomNo("uuuu");
        storageRoomDTO.setTypeCode("BBB");
        storageRoomDTO.setIsActive((byte)1);
        storageRoomDTO.setDescription("香蕉区");
        storageRoomDTO.setPriority(667);
        storageRoomDTO.setIsDefault((byte)2);
        storageRoomDTO.setCreateUser("YYM");
        storageRoomDTO.setCreateTime(66677L);
        storageRoomDTO.setUpdateUser("WWC");
        storageRoomDTO.setUpdateTime(877878L);
        storageRoomService.modifyStorageRoom(storageRoomDTO);
        System.out.println("updated success!");
    }

    @Test
    public void selectRoomNoByIds(){
        List<Long> ids = new ArrayList<>();
        ids.add(128L);
        ids.add(133L);
        System.out.println(storageRoomService.getRoomNoByIds(ids).toString());
    }
}
