package com.huamengtong.wms.sku.mapper;

import com.huamengtong.wms.entity.sku.TWmsSkuCargoOwnerEntity;
import com.huamengtong.wms.entity.sku.TWmsSkuEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by StickT on 2017/1/5.
 */
public interface SkuCargoOwnerMapper {

    List<TWmsSkuCargoOwnerEntity> selectAll(@Param("splitTableKey") String splitTableKey);

    Integer insertSkuCargoOwner(@Param("entity") TWmsSkuCargoOwnerEntity skuCargoOwnerEntity,@Param("splitTableKey") String splitTableKey);

    TWmsSkuCargoOwnerEntity selectBySkuIdAndCargoOwnerId(@Param("skuId")Long skuId,@Param("cargoOwnerId") Long cargoOwnerId,@Param("splitTableKey")String splitTableKey);

    Integer updateSkuCargoOwnerBySkuId(@Param("entity")TWmsSkuCargoOwnerEntity skuCargoOwnerEntity,@Param("splitTableKey")String splitTableKey);

    TWmsSkuEntity selectByCargoOwnerBarcode(Map map);

    List<Long> selectSkuIdByCargoOwnerId(@Param("cargoOwnerId")Long cargoOwnerId,@Param("splitTableKey")String splitTableKey);

    List<TWmsSkuCargoOwnerEntity> selectEntitysByCargoOwnerId(@Param("cargoOwnerId")Long cargoOwnerId,@Param("splitTableKey")String splitTableKey);

    List<TWmsSkuCargoOwnerEntity> selectBysku(@Param("skuId") Long skuId,@Param("splitTableKey")String splitTableKey);

}
