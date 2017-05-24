package com.huamengtong.wms.outwh.mapper;

import com.huamengtong.wms.entity.outwh.TWmsFrozenDetailEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface FrozenDetailMapper {

    TWmsFrozenDetailEntity selectByPrimaryKey(@Param("id") Long id,@Param("splitTableKey") String splitTableKey);

    Integer insertFrozenDetail(@Param("entity") TWmsFrozenDetailEntity frozenDetailEntity,@Param("splitTableKey") String splitTableKey);

    Integer deleteFrozenDetail(@Param("id") Long id, @Param("splitTableKey") String splitTableKey);

    Integer updateFrozenDetail(@Param("entity") TWmsFrozenDetailEntity frozenDetailEntity, @Param("splitTableKey") String splitTableKey);

    List<TWmsFrozenDetailEntity> queryDetailPages(Map map);

    Integer queryDetailPageCount(Map map);

    List<TWmsFrozenDetailEntity> queryFrozenDetails(@Param("frozenId") Long frozenId,@Param("splitTableKey") String splitTableKey);

    Integer deleteByFrozenId(Map map);

    TWmsFrozenDetailEntity selectByFrozenIdAndSkuId(@Param("frozenId") Long frozenId,@Param("skuId") Long skuId,@Param("storageRoomId") Long storageRoomId,@Param("splitTableKey") String splitTableKey);

}
