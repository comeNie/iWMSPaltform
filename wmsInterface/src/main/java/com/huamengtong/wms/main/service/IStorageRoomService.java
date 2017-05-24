package com.huamengtong.wms.main.service;

import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.TWmsStorageRoomDTO;
import com.huamengtong.wms.entity.main.TWmsStorageRoomEntity;

import java.util.List;


public interface IStorageRoomService {

    PageResponse<List<TWmsStorageRoomEntity>> queryStorageRoomPages(TWmsStorageRoomDTO roomDTO);

    TWmsStorageRoomEntity findByPrimaryKey(Long id);

    MessageResult removeByPrimaryKey(Long id);

    MessageResult createStorageRoom(TWmsStorageRoomDTO roomDTO);

    MessageResult modifyStorageRoom(TWmsStorageRoomDTO roomDTO);

    List<TWmsStorageRoomEntity> findStorageRoomsBywhId(Long id);

    List<TWmsStorageRoomEntity> getUseStorageRooms(Long warehouseId);

    List<TWmsStorageRoomEntity> getByIds(List<Long> ids);

    List<String> getRoomNoByIds(List<Long> ids);
}
