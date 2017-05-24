package com.huamengtong.wms.main.mapper;

import com.huamengtong.wms.entity.main.TWmsLocationEntity;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;


public interface LocationMapper {

    List<TWmsLocationEntity> selectLocationList(@Param("warehouseId") Long warehouseId);

    List<TWmsLocationEntity>  queryLocationPages(Map map);

    Integer queryLocationPageCount(Map map);

    TWmsLocationEntity selectByPrimaryKey(Long id);

    Integer deleteByPrimaryKey(Long id);

    Integer insertLocation(TWmsLocationEntity entity);

    Integer updateLocation(TWmsLocationEntity entity);

    List<TWmsLocationEntity> selectAllLocations(@Param("zoneId") Long zoneId);

    List<TWmsLocationEntity> queryLocationsByZonePages(Map searchMap);

    Integer queryLocationsByZonePageCount(Map searchMap);

}
