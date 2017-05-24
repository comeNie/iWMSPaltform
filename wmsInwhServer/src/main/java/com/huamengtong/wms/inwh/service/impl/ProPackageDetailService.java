package com.huamengtong.wms.inwh.service.impl;

import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.service.BaseService;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.inwh.TWmsProPackageDetailDTO;
import com.huamengtong.wms.entity.inventory.TWmsInventoryEntity;
import com.huamengtong.wms.entity.inwh.TWmsProPackageDetailEntity;
import com.huamengtong.wms.entity.sku.TWmsSkuEntity;
import com.huamengtong.wms.inventory.service.IInventoryService;
import com.huamengtong.wms.inwh.mapper.ProPackageDetailMapper;
import com.huamengtong.wms.inwh.service.IProPackageDetailService;
import com.huamengtong.wms.sku.service.ISkuService;
import com.huamengtong.wms.utils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by mario on 2017/2/20.
 */
@Service
public class ProPackageDetailService extends BaseService implements IProPackageDetailService {

    @Autowired
    ProPackageDetailMapper proPackageDetailMapper;

    @Autowired
    ISkuService skuService;

    @Autowired
    IInventoryService inventoryService;

    @Override
    public TWmsProPackageDetailEntity findByPrimarykey(Long id, DbShardVO dbShardVO) {

        return proPackageDetailMapper.selectByprimarkey(id,getSplitTableKey(dbShardVO));
    }

    @Override
    public MessageResult createProPackageDetail(TWmsProPackageDetailDTO proPackageDetailDTO, DbShardVO dbShardVO) {
        TWmsProPackageDetailEntity proPackageDetailEntity = BeanUtils.copyBeanPropertyUtils(proPackageDetailDTO,TWmsProPackageDetailEntity.class);
        proPackageDetailMapper.insertProPackageDetail(proPackageDetailEntity,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult removeProPackageDetail(Long id, DbShardVO dbShardVO) {
        proPackageDetailMapper.deleteProPackageDetail(id,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult modifyProPackageDetail(TWmsProPackageDetailDTO proPackageDetailDTO, DbShardVO dbShardVO) {
        TWmsProPackageDetailEntity proPackageDetailEntity = BeanUtils.copyBeanPropertyUtils(proPackageDetailDTO,TWmsProPackageDetailEntity.class);
        proPackageDetailMapper.updateProPackageDetail(proPackageDetailEntity,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }

    @Override
    public PageResponse<List> queryProPackageDetailByParent(Map paraMap, DbShardVO dbShardVO) {
        paraMap.put("splitTableKey",getSplitTableKey(dbShardVO));
        List<TWmsProPackageDetailEntity> mapList =proPackageDetailMapper.queryDetailPages(paraMap);
        if (CollectionUtils.isEmpty(mapList)){
            return  null;
        }
        List<TWmsProPackageDetailEntity> proPackageDetailEntityList = new ArrayList<>();
        mapList.stream().forEach(x ->{
            TWmsInventoryEntity inventoryEntity = inventoryService.findById(x.getProInventoryId(),changeInwhDbShareVO(dbShardVO));
            TWmsSkuEntity skuEntity = skuService.findByPrimaryKey(inventoryEntity.getSkuId(),getSkuDbShareVO(dbShardVO));
            if (skuEntity != null){
                x.setSku(skuEntity.getSku());
                x.setSkuName(skuEntity.getItemName());
                x.setBarcode(skuEntity.getBarcode());
                x.setUnitType(skuEntity.getUnitType());
                x.setSpec(skuEntity.getSpec());
            }
            proPackageDetailEntityList.add(x);
        });

        Integer totalSize = proPackageDetailMapper.queryDetailPageCount(paraMap);
        PageResponse<List> response = new PageResponse();
        response.setRows(proPackageDetailEntityList);
        response.setTotal(totalSize);
        return response;
    }

    @Override
    public List<TWmsProPackageDetailEntity> getProPackageDetails(Long parentId, DbShardVO dbShardVO) {
        List<TWmsProPackageDetailEntity> mapList = proPackageDetailMapper.queryProPackageDetails(parentId,getSplitTableKey(dbShardVO));
        if (CollectionUtils.isNotEmpty(mapList))
        {
            return mapList;
        }
        return null;
    }
}
