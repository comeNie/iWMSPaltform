package com.huamengtong.wms.inventory.service.impl;

import com.huamengtong.wms.client.IAutoIdClient;
import com.huamengtong.wms.constants.AutoIdConstants;
import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.service.BaseService;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.inventory.TWmsProInventoryLogDTO;
import com.huamengtong.wms.dto.sku.TWmsSkuDTO;
import com.huamengtong.wms.entity.inventory.TWmsProInventoryLogEntity;
import com.huamengtong.wms.entity.sku.TWmsSkuEntity;
import com.huamengtong.wms.inventory.mapper.ProInventoryLogMapper;
import com.huamengtong.wms.inventory.service.IProInventoryLogService;
import com.huamengtong.wms.sku.service.ISkuService;
import com.huamengtong.wms.utils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by StickT on 2017/1/9.
 */
@Service
public class ProInventoryLogService extends BaseService implements IProInventoryLogService {

    @Autowired
    ProInventoryLogMapper proInventoryLogMapper;

    @Autowired
    ISkuService skuService;

    @Autowired
    IAutoIdClient autoIdClient;

    @Override
    public PageResponse<List<TWmsProInventoryLogEntity>> getProInventoryList(TWmsProInventoryLogDTO proInventoryLogDTO, DbShardVO dbShardVO) {
        TWmsProInventoryLogEntity proInventoryLogEntity = BeanUtils.copyBeanPropertyUtils(proInventoryLogDTO, TWmsProInventoryLogEntity.class);
        TWmsSkuDTO skuDTO = new TWmsSkuDTO();
        checkParameters(proInventoryLogEntity,skuDTO);
        List<Long> skuIdList = new ArrayList<>();
        if(skuDTO.getSku()!=null || skuDTO.getBarcode()!=null || skuDTO.getItemName()!=null){
            skuIdList = skuService.findSkuLists(skuDTO,getSkuDbShareVO(dbShardVO));
        }
        List<TWmsProInventoryLogEntity> proInventoryLogEntityList = proInventoryLogMapper.queryProInventoryPages(proInventoryLogEntity,skuIdList, getSplitTableKey(dbShardVO));
        if(CollectionUtils.isEmpty(proInventoryLogEntityList)){
            return null;
        }
        proInventoryLogEntityList.stream().forEach(x->{
            TWmsSkuEntity skuEntity = skuService.findByPrimaryKey(x.getSkuId(),getSkuDbShareVO(dbShardVO));
            x.setSkuName(skuEntity.getItemName());
            x.setSku(skuEntity.getSku());
            x.setBarcode(skuEntity.getBarcode());
            x.setSkuSpec(skuEntity.getSpec());
            x.setUnitType(skuEntity.getUnitType());
        });
        Integer totalSize = proInventoryLogMapper.queryProInventoryPageCount(proInventoryLogEntity,skuIdList, getSplitTableKey(dbShardVO));
        PageResponse<List<TWmsProInventoryLogEntity>> response = new PageResponse<>();
        response.setRows(proInventoryLogEntityList);
        response.setTotal(totalSize);
        return response;
    }

    private void checkParameters(TWmsProInventoryLogEntity proInventoryLogEntity, TWmsSkuDTO skuDTO){
        if(proInventoryLogEntity.getSku() !=null){
            skuDTO.setSku(proInventoryLogEntity.getSku());
        }
        if(proInventoryLogEntity.getSkuName() !=null){
            skuDTO.setItemName(proInventoryLogEntity.getSkuName());
        }
        if(proInventoryLogEntity.getBarcode() !=null){
            skuDTO.setBarcode(proInventoryLogEntity.getBarcode());
        }
    }


    @Override
    public MessageResult createProInventoryLog(TWmsProInventoryLogEntity proInventoryLogEntity, DbShardVO dbShardVO) {
        if(proInventoryLogEntity.getId()==null){
            proInventoryLogEntity.setId(autoIdClient.getAutoId(AutoIdConstants.TWmsProInventoryLogEntity));
        }
        proInventoryLogMapper.insertProInventoryLogMapper(proInventoryLogEntity,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }


}


