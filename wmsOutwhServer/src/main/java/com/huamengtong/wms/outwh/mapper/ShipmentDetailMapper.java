package com.huamengtong.wms.outwh.mapper;

import com.huamengtong.wms.entity.outwh.TWmsShipmentDetailEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by mario on 2016/10/31.
 */
public interface ShipmentDetailMapper {
    TWmsShipmentDetailEntity selectByPrimaryKey (@Param("id") Long id, @Param("splitTableKey") String splitTableKey);

    Integer insertShipmentDetail(@Param("entity") TWmsShipmentDetailEntity shipmentDetailEntity, @Param("splitTableKey") String splitTableKey);

    Integer deleteByPrimaryKey(@Param("id") Long id,@Param("splitTableKey") String splitTableKey);

    Integer updateShipmentDetail(@Param("entity") TWmsShipmentDetailEntity shipmentDetailEntity, @Param("splitTableKey") String splitTableKey);

    Integer updateShipmentDetailRoomId(@Param("entity") TWmsShipmentDetailEntity shipmentDetailEntity, @Param("splitTableKey") String splitTableKey);

    List<TWmsShipmentDetailEntity> queryDetailsPages(Map map);

    Integer queryDetailPageCount(Map map);

    List<TWmsShipmentDetailEntity> queryShipmentDetails(@Param("headerId") Long HeaderId,@Param("splitTableKey") String splitTableKey);

    Integer deleteShipmentDetails(@Param("shipmentId") Long receiptId,@Param("splitTableKey") String splitTableKey);

    Integer updateShipmentDetailAllocatedQty(@Param("shipmentId")Long shipmentId,@Param("storageRoomId") String storageRoomId, @Param("splitTableKey") String splitTableKey);

    Integer insertShipmentDetailList(@Param("list") List<TWmsShipmentDetailEntity> list, @Param("splitTableKey") String splitTableKey);

    List<TWmsShipmentDetailEntity> queryShipmentDetailPages(Map map);

    Integer queryShipmentDetailPageCount(Map map);

    Integer queryDetailSummaryPageCount(Map map);

    List<TWmsShipmentDetailEntity> queryDetailSummaryPages(Map map);

}
