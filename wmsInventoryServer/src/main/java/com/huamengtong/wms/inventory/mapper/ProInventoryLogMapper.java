package com.huamengtong.wms.inventory.mapper;

import com.huamengtong.wms.entity.inventory.TWmsInventoryLogEntity;
import com.huamengtong.wms.entity.inventory.TWmsProInventoryLogEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProInventoryLogMapper {
    Integer insertProInventoryLogMapper(@Param("entity") TWmsProInventoryLogEntity entity, @Param("splitTableKey") String splitTableKey);

    TWmsInventoryLogEntity selectProInventoryLogByPrimaryKey(@Param("id") Long id, @Param("splitTableKey") String splitTableKey);

    List<TWmsInventoryLogEntity> selectProInventoryLogPage(@Param("entity")TWmsProInventoryLogEntity entity, @Param("skuIds")List<Long> skuIds,
                                                        @Param("fromTime")Long fromTime, @Param("toTime")Long toTime,
                                                        @Param("splitTableKey")String splitTableKey);
    Integer selectProInventoryLogPageCounts(@Param("entity")TWmsProInventoryLogEntity entity,
                                         @Param("skuIds")List<Long> skuIds, @Param("fromTime")Long fromTime,
                                         @Param("toTime")Long toTime,@Param("splitTableKey")String splitTableKey);

    List<TWmsProInventoryLogEntity> queryProInventoryPages(@Param("entity") TWmsProInventoryLogEntity entity,@Param("list") List<Long> list,@Param("splitTableKey") String splitTableKey);

    Integer queryProInventoryPageCount(@Param("entity") TWmsProInventoryLogEntity proInventoryLogEntity ,@Param("list") List<Long> list, @Param("splitTableKey") String splitTableKey);
}
