package com.huamengtong.wms.main.mapper;


import com.huamengtong.wms.entity.main.TWmsZoneEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ZoneMapper {

    List<TWmsZoneEntity> queryZonePages(TWmsZoneEntity zoneEntity);

    Integer queryZonePageCount(TWmsZoneEntity zoneEntity);

    TWmsZoneEntity selectByPrimaryKey(Long id);

    Integer deleteByPrimaryKey(Long id);

    Integer insertZone(TWmsZoneEntity wmsZoneEntity);

    Integer updateZone(TWmsZoneEntity wmsZoneEntity);

    List<TWmsZoneEntity> selectZoneByList(@Param("warehouseId") Long warehouseId);

    List selectLocationsByZoneId(@Param("id") Long userId);

    List<Map<String,Object>> queryZoneByLocationPages(Map searchMap);

    Integer queryZoneByLocationPageCount(Map searchMap);

    List<TWmsZoneEntity> selectZonesByWarehouse(Long warehouseId);

    List<TWmsZoneEntity> selectAllZones(Map searchMap);

}
