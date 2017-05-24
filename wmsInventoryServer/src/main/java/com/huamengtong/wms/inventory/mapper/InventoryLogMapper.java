package com.huamengtong.wms.inventory.mapper;

import com.huamengtong.wms.entity.inventory.TWmsInventoryLogEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InventoryLogMapper {
    Integer insertInventoryLogMapper(@Param("entity") TWmsInventoryLogEntity inventoryLogEntity, @Param("splitTableKey") String splitTableKey);

    TWmsInventoryLogEntity selectInventoryLogByPrimaryKey(@Param("id") Long id, @Param("splitTableKey") String splitTableKey);

    List<TWmsInventoryLogEntity> selectInventoryLogPage(@Param("entity")TWmsInventoryLogEntity inventoryLogEntity,@Param("skuIds")List<Long> skuIds,
                                                        @Param("fromTime")Long fromTime, @Param("toTime")Long toTime,
                                                        @Param("splitTableKey")String splitTableKey);
    Integer selectInventoryLogPageCounts(@Param("entity")TWmsInventoryLogEntity inventoryLogEntity,
                                         @Param("skuIds")List<Long> skuIds, @Param("fromTime")Long fromTime,
                                         @Param("toTime")Long toTime,@Param("splitTableKey")String splitTableKey);

    List<TWmsInventoryLogEntity>  selectInventoryInOutPage(@Param("entity")TWmsInventoryLogEntity inventoryLogEntity,@Param("skuIds")List<Long> skuIds,
                                                           @Param("fromTime")Long fromTime,@Param("toTime")Long toTime,
                                                           @Param("splitTableKey")String splitTableKey);



   List<TWmsInventoryLogEntity> selectInventorySkuIdPage(@Param("entity")TWmsInventoryLogEntity inventoryLogEntity, @Param("splitTableKey") String splitTableKey);

    Integer selectInventorySkuIdPageCount(@Param("entity")TWmsInventoryLogEntity inventoryLogEntity, @Param("splitTableKey") String splitTableKey);
}
