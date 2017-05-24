package com.huamengtong.wms.inventory.service.impl;

import com.huamengtong.wms.client.IAutoIdClient;
import com.huamengtong.wms.constants.AutoIdConstants;
import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.service.BaseService;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.inventory.TWmsInventoryLogDTO;
import com.huamengtong.wms.entity.inventory.TWmsInventoryLogEntity;
import com.huamengtong.wms.inventory.mapper.InventoryLogMapper;
import com.huamengtong.wms.inventory.service.IInventoryLogService;
import com.huamengtong.wms.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InventoryLogService extends BaseService implements IInventoryLogService{

    @Autowired
    private InventoryLogMapper inventoryLogMapper;
    @Autowired
    private IAutoIdClient autoIdClient;

    @Override
    public TWmsInventoryLogEntity findByPrimaryKey(Long id, DbShardVO dbShardVO) {
        return inventoryLogMapper.selectInventoryLogByPrimaryKey(id,getSplitTableKey(dbShardVO));
    }

    @Override
    public MessageResult createInventoryLog(TWmsInventoryLogEntity inventoryLogEntity, DbShardVO dbShardVO) {
        inventoryLogEntity.setId(autoIdClient.getAutoId(AutoIdConstants.TWmsInventoryLogEntity));
        inventoryLogMapper.insertInventoryLogMapper(inventoryLogEntity,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }

    @Override
    public PageResponse<List<TWmsInventoryLogEntity>> queryInventoryLogPages(TWmsInventoryLogDTO inventoryLogDTO,List<Long> skuIdList,Long fromTime,Long toTime, DbShardVO dbshardVO) {
        TWmsInventoryLogEntity inventoryLogEntity = BeanUtils.copyBeanPropertyUtils(inventoryLogDTO,TWmsInventoryLogEntity.class);
        List<TWmsInventoryLogEntity> list = inventoryLogMapper.selectInventoryLogPage(inventoryLogEntity,skuIdList,fromTime,toTime,getSplitTableKey(dbshardVO));
        Integer totalSize = inventoryLogMapper.selectInventoryLogPageCounts(inventoryLogEntity,skuIdList,fromTime,toTime,getSplitTableKey(dbshardVO));
        PageResponse<List<TWmsInventoryLogEntity>> response = new PageResponse<>();
        response.setRows(list);
        response.setTotal(totalSize);
        return response;
    }

    @Override
    public List<TWmsInventoryLogEntity> getInventoryLogLists(TWmsInventoryLogDTO inventoryLogDTO,List<Long> skuIdList,Long fromTime,Long toTime, DbShardVO dbshardVO) {
        TWmsInventoryLogEntity inventoryLogEntity = BeanUtils.copyBeanPropertyUtils(inventoryLogDTO,TWmsInventoryLogEntity.class);
        return inventoryLogMapper.selectInventoryInOutPage(inventoryLogEntity,skuIdList,fromTime,toTime,getSplitTableKey(dbshardVO));
    }

    @Override
    public List<Long> selectInventoryLogSkuId(TWmsInventoryLogDTO inventoryLogDTO, DbShardVO dbShardVO) {
        TWmsInventoryLogEntity inventoryLogEntity = BeanUtils.copyBeanPropertyUtils(inventoryLogDTO,TWmsInventoryLogEntity.class);
        List<TWmsInventoryLogEntity> inventoryLogEntityList = inventoryLogMapper.selectInventorySkuIdPage(inventoryLogEntity,getSplitTableKey(dbShardVO));
        List skuIdList  =new ArrayList();
        for(TWmsInventoryLogEntity list: inventoryLogEntityList){
            skuIdList.add(list.getSkuId());
        }
        return skuIdList;
    }

    @Override
    public Integer selectInventoryLogSkuIdCount(TWmsInventoryLogDTO inventoryLogDTO, DbShardVO dbShardVO) {
        TWmsInventoryLogEntity inventoryLogEntity = BeanUtils.copyBeanPropertyUtils(inventoryLogDTO,TWmsInventoryLogEntity.class);
        return inventoryLogMapper.selectInventorySkuIdPageCount(inventoryLogEntity,getSplitTableKey(dbShardVO));
    }
}
