package com.huamengtong.wms.sku.service;

import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.dto.sku.TWmsSkuCargoOwnerDTO;
import com.huamengtong.wms.entity.sku.TWmsSkuCargoOwnerEntity;
import com.huamengtong.wms.entity.sku.TWmsSkuEntity;

import java.util.List;

/**
 * Created by StickT on 2017/1/5.
 */
public interface ISkuCargoOwnerService {

    List<TWmsSkuCargoOwnerEntity> findAll(DbShardVO dbShardVO);

    MessageResult insertSkuCargoOwner(TWmsSkuCargoOwnerDTO skuCargoOwnerDTO, DbShardVO dbShardVO);

    MessageResult updateSkuCargoOwnerBySkuId(TWmsSkuCargoOwnerDTO skuCargoOwnerDTO, DbShardVO dbShardVO);

    TWmsSkuCargoOwnerEntity selectBySkuIdAndCargoOwnerId(Long skuId,Long cargoOwnerId,DbShardVO dbShardVO);

    TWmsSkuEntity findByCargoOwnerBarcode(String cargoOwnerBarcode,DbShardVO dbShardVO);

    List<Long> findSkuIdByCargoOwner(Long cargoOwnerId ,DbShardVO dbShardVO);

    List<TWmsSkuCargoOwnerEntity> findEntitysByCargoOwner(Long cargoOwnerId ,DbShardVO dbShardVO);

    List<TWmsSkuCargoOwnerEntity> findBysku(Long skuId,DbShardVO dbShardVO);

}
