package com.huamengtong.wms.sku.mapper;

import com.huamengtong.wms.entity.sku.TWmsSkuEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SkuMapper {

    /***
     * 通过barcode获取sku信息
     * @param barcode 条码
     * @param splitTableKey 分表标示
     * @return
     */
    TWmsSkuEntity getSkuByBarcode(@Param("barcode") String barcode, @Param("splitTableKey") String splitTableKey);

    Integer selectSkuPageCount(@Param("skuEntity") TWmsSkuEntity skuEntity,@Param("splitTableKey") String splitTableKey);

    List<TWmsSkuEntity> selectSkuPages(@Param("skuEntity") TWmsSkuEntity skuEntity,@Param("splitTableKey") String splitTableKey);

    Integer insertSku(@Param("skuEntity") TWmsSkuEntity skuEntity, @Param("splitTableKey") String splitTableKey);

    Integer deleteSku(@Param("id") Long id, @Param("splitTableKey") String splitTableKey);

    Integer updateSku(@Param("skuEntity") TWmsSkuEntity skuEntity,@Param("splitTableKey") String splitTableKey);

    TWmsSkuEntity selectSkuById(@Param("id")Long id,@Param("splitTableKey") String splitTableKey);

    /*   Integer insertBatchSku(@Param("list")  List<TWmsSkuEntity> skuEntityLists, @Param("splitTableKey") String splitTableKey);*/
    Integer insertBatchSku(@Param("list")  List<TWmsSkuEntity> skuEntityLists, @Param("splitTableKey") String splitTableKey);

    Integer deleteSkuBatch(@Param("list")List<Long> list,@Param("splitTableKey") String splitTableKey);

    List<TWmsSkuEntity>  selectValidateHasSku(@Param("cargoOwnerId")Long cargoOwnerId,@Param("barcode")String barcode,@Param("splitTableKey") String splitTableKey );

    TWmsSkuEntity getSkuBySku(@Param("sku") String sku, @Param("splitTableKey") String splitTableKey);

    List<TWmsSkuEntity> selectSkuAll(@Param("splitTableKey") String splitTableKey);

    List<TWmsSkuEntity> selectByCargoOwnerId(@Param("cargoOwnerId") Long cargoOwnerId,@Param("splitTableKey") String splitTableKey);

    List<Long> selectSkuIdList(@Param("skuEntity") TWmsSkuEntity skuEntity,@Param("splitTableKey") String splitTableKey);

    List<TWmsSkuEntity> selectByIdList(@Param("list") List<Long> list,@Param("splitTableKey") String splitTableKey);

    TWmsSkuEntity getSkuByItemName(@Param("itemName") String itemName,@Param("splitTableKey") String splitTableKey);

    List<TWmsSkuEntity> findSkuCargoOwner(Map map);

    List<TWmsSkuEntity> selectSkuPagesByCargoOwnerId(@Param("skuEntity") TWmsSkuEntity skuEntity,@Param("splitTableKey") String splitTableKey);

    Integer  queryPageCountByCargoOwnerId(@Param("skuEntity") TWmsSkuEntity skuEntity, @Param("splitTableKey") String splitTableKey);

    TWmsSkuEntity selectSkuByUpc(@Param("upc") String upc, @Param("splitTableKey") String splitTableKey);

}