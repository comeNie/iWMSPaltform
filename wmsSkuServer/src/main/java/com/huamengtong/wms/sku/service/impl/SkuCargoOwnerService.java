package com.huamengtong.wms.sku.service.impl;

import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.service.BaseService;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.dto.sku.TWmsSkuCargoOwnerDTO;
import com.huamengtong.wms.entity.sku.TWmsSkuCargoOwnerEntity;
import com.huamengtong.wms.entity.sku.TWmsSkuEntity;
import com.huamengtong.wms.sku.mapper.SkuCargoOwnerMapper;
import com.huamengtong.wms.sku.service.ISkuCargoOwnerService;
import com.huamengtong.wms.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by StickT on 2017/1/5.
 */
@Service
public class SkuCargoOwnerService extends BaseService implements ISkuCargoOwnerService {


    @Autowired
    SkuCargoOwnerMapper skuCargoOwnerMapper;


    @Override
    public List<TWmsSkuCargoOwnerEntity> findAll(DbShardVO dbShardVO) {
        return skuCargoOwnerMapper.selectAll(getSplitTableKey(dbShardVO));
    }

    @Override
    public MessageResult insertSkuCargoOwner(TWmsSkuCargoOwnerDTO skuCargoOwnerDTO, DbShardVO dbShardVO) {
        TWmsSkuCargoOwnerEntity skuCargoOwnerEntity = BeanUtils.copyBeanPropertyUtils(skuCargoOwnerDTO,TWmsSkuCargoOwnerEntity.class);
        skuCargoOwnerMapper.insertSkuCargoOwner(skuCargoOwnerEntity,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult updateSkuCargoOwnerBySkuId(TWmsSkuCargoOwnerDTO skuCargoOwnerDTO, DbShardVO dbShardVO) {
        TWmsSkuCargoOwnerEntity skuCargoOwnerEntity = BeanUtils.copyBeanPropertyUtils(skuCargoOwnerDTO,TWmsSkuCargoOwnerEntity.class);
        skuCargoOwnerMapper.updateSkuCargoOwnerBySkuId(skuCargoOwnerEntity,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }

    @Override
    public TWmsSkuCargoOwnerEntity selectBySkuIdAndCargoOwnerId(Long skuId,Long cargoOwnerId, DbShardVO dbShardVO) {
        return skuCargoOwnerMapper.selectBySkuIdAndCargoOwnerId(skuId,cargoOwnerId,getSplitTableKey(dbShardVO));
    }

    @Override
    public TWmsSkuEntity findByCargoOwnerBarcode(String cargoOwnerBarcode, DbShardVO dbShardVO) {
        Map<String,Object> map  = new HashMap<>();
        map.put("splitTableKey",getSplitTableKey(dbShardVO));
        map.put("cargoOwnerBarcode",cargoOwnerBarcode);
        return skuCargoOwnerMapper.selectByCargoOwnerBarcode(map);
    }

    @Override
    public List<Long> findSkuIdByCargoOwner(Long cargoOwnerId, DbShardVO dbShardVO) {
        return skuCargoOwnerMapper.selectSkuIdByCargoOwnerId(cargoOwnerId,getSplitTableKey(dbShardVO));
    }

    @Override
    public List<TWmsSkuCargoOwnerEntity> findEntitysByCargoOwner(Long cargoOwnerId, DbShardVO dbShardVO) {
        return skuCargoOwnerMapper.selectEntitysByCargoOwnerId(cargoOwnerId,getSplitTableKey(dbShardVO));
    }

    @Override
    public List<TWmsSkuCargoOwnerEntity> findBysku(Long skuId, DbShardVO dbShardVO) {
        return skuCargoOwnerMapper.selectBysku(skuId,getSplitTableKey(dbShardVO));
    }
}
