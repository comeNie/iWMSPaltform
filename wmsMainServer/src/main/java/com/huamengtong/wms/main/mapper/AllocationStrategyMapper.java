package com.huamengtong.wms.main.mapper;

import com.huamengtong.wms.entity.main.TWmsAllocationStrategyEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by mario on 2017/2/8.
 */
public interface AllocationStrategyMapper {
    List<TWmsAllocationStrategyEntity>  querySallocationStrategyPages(TWmsAllocationStrategyEntity sallocationStrategyEntity);

    Integer querySallocationStrategyCount(TWmsAllocationStrategyEntity sallocationStrategyEntity);

    Integer insertSallocationStrategy(TWmsAllocationStrategyEntity sallocationStrategyEntity);

    Integer updateSallocationStrategy(TWmsAllocationStrategyEntity sallocationStrategyEntity);

    Integer deleteSallocationStrategy(@Param("id") Long id);

    Integer selectByIsdefault(@Param("id")Long id,@Param("warehouseId")Long warehouseId);

    Integer selectByWarehouseId(@Param("warehouseId")Long warehouseId);

    TWmsAllocationStrategyEntity selectListByWarehouseId(@Param("warehouseId")Long warehouseId);


}
