package com.huamengtong.wms.outwh.mapper;


import com.huamengtong.wms.entity.outwh.TWmsShipmentHeaderEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by mario on 2016/10/31.
 */
public interface ShipmentHeaderMapper {

    List<TWmsShipmentHeaderEntity> queryShipmentHeaderPages (@Param("entity") TWmsShipmentHeaderEntity shipmentHeaderEntity, @Param("splitTableKey") String splitTableKey);

    Integer queryShipmentHeaderPageCount(@Param("entity") TWmsShipmentHeaderEntity shipmentHeaderEntity, @Param("splitTableKey") String splitTableKey);

    TWmsShipmentHeaderEntity selectByPrimaryKey (@Param("id") Long id, @Param("splitTableKey") String splitTableKey);

    Integer insertShipmentHeader(@Param("entity") TWmsShipmentHeaderEntity shipmentHeaderEntity, @Param("splitTableKey") String splitTableKey);

    Integer deleteByPrimaryKey(@Param("id") Long id,@Param("splitTableKey") String splitTableKey);

    Integer updateShipmentHeader(@Param("entity") TWmsShipmentHeaderEntity shipmentHeaderEntity, @Param("splitTableKey") String splitTableKey);

    Integer updateShipmentHeaderAllocateStatus(@Param("entity") TWmsShipmentHeaderEntity shipmentHeaderEntity, @Param("splitTableKey")String splitTableKey);

    List<TWmsShipmentHeaderEntity> selectCargoOwnerIds(@Param("ids") List<Long> list, @Param("splitTableKey")String splitTableKey);

    List<TWmsShipmentHeaderEntity> selectByCargoOwnerId(@Param("cargoOwnerId") Long cargoOwnerId, @Param("splitTableKey")String splitTableKey);

    Integer updateShipmentHeaderWave(Map map);

    List<TWmsShipmentHeaderEntity> selectShipmentIdsByWaveId(@Param("ids") List<Long> list, @Param("splitTableKey")String splitTableKey);

}
