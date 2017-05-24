package com.huamengtong.wms.inventory.mapper;

import com.huamengtong.wms.entity.inventory.TWmsInventoryEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface InventoryMapper {


    List<TWmsInventoryEntity> queryInventoryPages(@Param("entity") TWmsInventoryEntity inventoryEntity,@Param("splitTableKey") String splitTableKey);

    Integer queryInventoryPageCount(@Param("entity") TWmsInventoryEntity inventoryEntity, @Param("splitTableKey") String splitTableKey);

    Integer insertInventory(@Param("entity") TWmsInventoryEntity inventoryEntity, @Param("splitTableKey") String splitTableKey);

    Integer updateInventory(@Param("entity") TWmsInventoryEntity inventoryEntity, @Param("splitTableKey") String splitTableKey);

    Integer deleteByPrimaryKey(@Param("id") Long id, @Param("splitTableKey") String splitTableKey);

    TWmsInventoryEntity selectByPK(@Param("id") Long id, @Param("splitTableKey") String splitTableKey);

    TWmsInventoryEntity selectByCargo(Map map);

    List<TWmsInventoryEntity> selectByStorageRoom(Map map);

    List<TWmsInventoryEntity> selectByHold(Map map);

    List<TWmsInventoryEntity> selectInventoryBySku(@Param("skuId") Long skuId,@Param("cargoOwnerId") Long cargoOwnerId, @Param("roomIds") List<Long> roomIds,  @Param("splitTableKey")String splitTableKey);

    List<TWmsInventoryEntity> selectInventoryReport(@Param("entity") TWmsInventoryEntity inventoryEntity,@Param("skuIds")List<Long> skuIds,@Param("fromTime")Long fromTime,
                                                    @Param("toTime")Long toTime, @Param("splitTableKey") String splitTableKey);

    List<TWmsInventoryEntity> selectInventoryReportSum( @Param("entity") TWmsInventoryEntity inventoryEntity,@Param("skuIds")List<Long> skuIds,
                                                        @Param("fromTime")Long fromTime,
                                                        @Param("toTime")Long toTime,@Param("splitTableKey") String splitTableKey);

    Integer queryInventorySumPageCount(@Param("entity") TWmsInventoryEntity inventoryEntity, @Param("skuIds")List<Long> skuIds,@Param("fromTime")Long fromTime,
                                       @Param("toTime")Long toTime, @Param("splitTableKey") String splitTableKey);

    List<TWmsInventoryEntity> selectByCargoOwnerId(@Param("cargoOwnerId") Long cargoOwnerId,@Param("splitTableKey")String splitTableKey);


  
    Integer queryInventoryReportPageCount(@Param("entity") TWmsInventoryEntity inventoryEntity,@Param("skuIds")List<Long> skuIds,
                                          @Param("fromTime")Long fromTime,
                                          @Param("toTime")Long toTime,@Param("splitTableKey") String splitTableKey);

    List<TWmsInventoryEntity> selectByIds(@Param("ids")List<Long> ids,@Param("splitTableKey") String splitTableKey);


    List<TWmsInventoryEntity> selectInventoryAllocationStrategy(@Param("skuId") Long skuId,@Param("cargoOwnerId") Long cargoOwnerId, @Param("roomIds") List<Long> roomIds,
                                                   @Param("orderBy") String orderBy,
                                                   @Param("ascOrDesc") String ascOrDesc,
                                                   @Param("splitTableKey")String splitTableKey);

    Integer queryActiveQty(@Param("list") Long[] storageRoomIds,@Param("skuId") Long skuId,@Param("cargoOwnerId") Long cargoOwnerId,@Param("splitTableKey") String splitTableKey);

    List<TWmsInventoryEntity> queryInventorBySkuId(@Param("skuId") Long skuId, @Param("splitTableKey")String splitTableKey);


}
