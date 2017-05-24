package com.huamengtong.wms.main.mapper;

import com.huamengtong.wms.dto.TWmsUserWarehouseDTO;
import com.huamengtong.wms.entity.main.TWmsWarehouseEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface WarehouseMapper {

    List<TWmsWarehouseEntity> selectWarehouseLists(@Param("userId") Long userId, @Param("tenantId") Long tenantId);

    TWmsWarehouseEntity selectByPrimaryKey(@Param("id") Long id);

    TWmsWarehouseEntity selectWarehouseByWarehouseNo(@Param("warehouseNo") String warehouseNo);

    List<TWmsWarehouseEntity> queryWarehousePages(TWmsWarehouseEntity warehouseEntity);

    Integer queryWarehousePageCount(TWmsWarehouseEntity warehouseEntity);

    Integer insertWarehouse(TWmsWarehouseEntity warehouseEntity);

    Integer updateWarehouse(TWmsWarehouseEntity warehouseEntity);

    Integer deleteByPrimaryKey(@Param("id") Long id);

    List selectUserByWarehouseId(@Param("id") Long id);

    List queryUserByWarehousePages(Map searchMap);

    Integer queryUserByWarehousePageCount(Map searchMap);

    Integer insertBatchUserWarehouse(List<TWmsUserWarehouseDTO> userWarehouseDTOList);

    Integer deleteBatchUserWarehouse(@Param("ids") Long[] ids);

    List<TWmsWarehouseEntity> selectWarehouseByIds(@Param("ids") List<Long> ids);

}
