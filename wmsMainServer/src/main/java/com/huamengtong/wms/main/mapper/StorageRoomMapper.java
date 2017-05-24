package com.huamengtong.wms.main.mapper;

import com.huamengtong.wms.entity.main.TWmsStorageRoomEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface StorageRoomMapper {

    List<TWmsStorageRoomEntity> queryStorageRoomPages(TWmsStorageRoomEntity roomEntity);

    Integer queryStorageRoomPageCount(TWmsStorageRoomEntity roomEntity);

    TWmsStorageRoomEntity selectByPrimaryKey(Long id);

    Integer  deleteByPrimaryKey(Long id);

    Integer  insertStorageRoom(TWmsStorageRoomEntity roomEntity);

    Integer  updateStorageRoom(TWmsStorageRoomEntity roomEntity);

    List<TWmsStorageRoomEntity> queryStorageRoomsBywhId(@Param("warehouseId")Long warehouseId);

    List<TWmsStorageRoomEntity> selectByIds(@Param("ids")List<Long> ids);

    List<String> selectRoomNoByIds(@Param("ids") List<Long> ids);
}
