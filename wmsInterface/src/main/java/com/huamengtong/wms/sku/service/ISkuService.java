package com.huamengtong.wms.sku.service;

import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.sku.TWmsSkuDTO;
import com.huamengtong.wms.entity.sku.TWmsSkuEntity;

import java.util.List;

public interface ISkuService {

    PageResponse<List<TWmsSkuEntity>> querySkuPages(TWmsSkuDTO skuDTO, DbShardVO dbshardVO);

    MessageResult createSku(TWmsSkuDTO skuDTO, DbShardVO dbshardVO) throws Exception;

    MessageResult updateSku(TWmsSkuDTO skuDTO, DbShardVO dbshardVO);

    MessageResult removeSku(Long id,DbShardVO dbShardVO);

    MessageResult removeSkuBatch(String ids, DbShardVO dbShardVO);

    TWmsSkuEntity querySkuById(Long id,DbShardVO dbShardVO);

    List<TWmsSkuEntity> findSkuLists(String ids, DbShardVO dbShardVO);

    MessageResult updateSkuUpLoad(List list, DbShardVO dbShardVO);

    Boolean validateHasSku(Long cargoOwnerId,String barcode, DbShardVO dbShardVO);

    TWmsSkuEntity findByPrimaryKey(Long id,DbShardVO dbShardVO);

    TWmsSkuEntity findByBarcode(String barcode,DbShardVO dbShardVO);

    List<TWmsSkuEntity> querySkuAll(DbShardVO dbShardVO);

    List<TWmsSkuEntity> findByCargoOwnerId(Long cargoOwnerId,DbShardVO dbShardVO);

    List<Long> findSkuLists(TWmsSkuDTO skuDTO, DbShardVO dbShardVO);

    List<TWmsSkuEntity> findByIdList(List<Long> list, DbShardVO dbShardVO);

    TWmsSkuEntity findSkuByItemName(String itemName,DbShardVO dbShardVO);

    List<TWmsSkuEntity> findSkuCargoOwner(String sku,String itemName , Long cargoOwnerId,Long skuId, DbShardVO dbShardVO);

    PageResponse<List<TWmsSkuEntity>> querySkuPagesByCargoOwner(TWmsSkuDTO skuDTO, DbShardVO dbshardVO);

    TWmsSkuEntity findByItemName(String itemName,DbShardVO dbShardVO);

    TWmsSkuEntity findByUpc(String upc,DbShardVO dbShardVO);

}
