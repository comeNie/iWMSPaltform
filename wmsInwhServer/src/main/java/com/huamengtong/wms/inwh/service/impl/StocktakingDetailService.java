package com.huamengtong.wms.inwh.service.impl;

import com.huamengtong.wms.client.IAutoIdClient;
import com.huamengtong.wms.constants.AutoIdConstants;
import com.huamengtong.wms.core.annotation.NeedLog;
import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.service.BaseService;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.inwh.TWmsStocktakingDetailDTO;
import com.huamengtong.wms.dto.inwh.TWmsStocktakingHeaderDTO;
import com.huamengtong.wms.em.ActionCode;
import com.huamengtong.wms.em.LogType;
import com.huamengtong.wms.em.OperationStatusCode;
import com.huamengtong.wms.em.OrderTypeCode;
import com.huamengtong.wms.entity.inventory.TWmsInventoryEntity;
import com.huamengtong.wms.entity.inwh.TWmsStocktakingDetailEntity;
import com.huamengtong.wms.entity.inwh.TWmsStocktakingHeaderEntity;
import com.huamengtong.wms.inventory.service.IInventoryService;
import com.huamengtong.wms.inwh.mapper.StocktakingDetailMapper;
import com.huamengtong.wms.inwh.service.IStocktakingDetailService;
import com.huamengtong.wms.inwh.service.IStocktakingHeaderService;
import com.huamengtong.wms.utils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mario on 2016/11/22.
 */
@Service
public class StocktakingDetailService extends BaseService implements IStocktakingDetailService {
    @Autowired
    StocktakingDetailMapper stocktakingDetailMapper;

    @Autowired
    IStocktakingHeaderService stocktakingHeaderService;

    @Autowired
    IAutoIdClient autoIdClient;

    @Autowired
    IInventoryService inventoryService;

    @Override
    public TWmsStocktakingDetailEntity findByPrimaryKey(Long id, DbShardVO dbShardVO) {
        return stocktakingDetailMapper.selectByPrimaryKey(id,getSplitTableKey(dbShardVO));

    }

    @Override
    public MessageResult createStocktakingDetailDetail(TWmsStocktakingDetailDTO stocktakingDetailDTO, DbShardVO dbShardVO) {
        if (stocktakingDetailDTO.getSkuId() == null ||  stocktakingDetailDTO.getSkuId() == 0){
            return MessageResult.getMessage("E40003");
        }
        TWmsStocktakingHeaderEntity stocktakingHeaderEntity = stocktakingHeaderService.findByPrimaryKey(stocktakingDetailDTO.getHeaderId(),dbShardVO);
        TWmsInventoryEntity inventoryEntity = inventoryService.findInventoryFromCargoOwner(stocktakingDetailDTO.getSkuId(),stocktakingHeaderEntity.getCargoOwnerId(),stocktakingDetailDTO.getStorageRoomId(),dbShardVO);
        if (inventoryEntity == null){
            return MessageResult.getMessage("E81024");
        }
        stocktakingDetailDTO.setId(autoIdClient.getAutoId(AutoIdConstants.TWmsStocktakingDetailEntity));
        stocktakingDetailDTO.setStorageRoomId(stocktakingHeaderEntity.getStorageRoomId());
        stocktakingDetailDTO.setOperationName(stocktakingDetailDTO.getUpdateUser());
        TWmsStocktakingDetailEntity stocktakingDetailEntity = BeanUtils.copyBeanPropertyUtils(stocktakingDetailDTO,TWmsStocktakingDetailEntity.class);
        stocktakingDetailMapper.insertStocktakingDetail(stocktakingDetailEntity,getSplitTableKey(dbShardVO));

        stocktakingHeaderEntity.setTotalCategoryQty(stocktakingHeaderEntity.getTotalCategoryQty()+1);
        stocktakingHeaderEntity.setUpdateUser(stocktakingDetailEntity.getUpdateUser());
        stocktakingHeaderEntity.setUpdateTime(new Date().getTime());
        TWmsStocktakingHeaderDTO stocktakingHeaderDTO= BeanUtils.copyBeanPropertyUtils(stocktakingHeaderEntity,TWmsStocktakingHeaderDTO.class);
        stocktakingHeaderService.modifyStocktakingHeader(stocktakingHeaderDTO,dbShardVO);
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult removeStocktakingDetail(Long id, DbShardVO dbShardVO) {
        TWmsStocktakingDetailEntity stocktakingDetailEntity= stocktakingDetailMapper.selectByPrimaryKey(id,getSplitTableKey(dbShardVO));
        TWmsStocktakingHeaderEntity stocktakingHeaderEntity = stocktakingHeaderService.findByPrimaryKey(stocktakingDetailEntity.getHeaderId(),dbShardVO);
        stocktakingDetailMapper.deleteStocktakingDetail(id,getSplitTableKey(dbShardVO));
        stocktakingHeaderEntity.setTotalCategoryQty(stocktakingHeaderEntity.getTotalCategoryQty()-1);
        stocktakingHeaderEntity.setUpdateUser(stocktakingDetailEntity.getUpdateUser());
        stocktakingHeaderEntity.setUpdateTime(new Date().getTime());
        TWmsStocktakingHeaderDTO stocktakingHeaderDTO= BeanUtils.copyBeanPropertyUtils(stocktakingHeaderEntity,TWmsStocktakingHeaderDTO.class);
        stocktakingHeaderService.modifyStocktakingHeader(stocktakingHeaderDTO,dbShardVO);
        return MessageResult.getSucMessage();

    }

    @Override
    public MessageResult modifyStocktakingDetail(TWmsStocktakingDetailDTO stocktakingDetailDTO, DbShardVO dbShardVO) {
        TWmsStocktakingDetailEntity stocktakingDetailEntityByPk = stocktakingDetailMapper.selectByPrimaryKey(stocktakingDetailDTO.getId(),getSplitTableKey(dbShardVO));
        TWmsStocktakingHeaderEntity stocktakingHeaderEntity = stocktakingHeaderService.findByPrimaryKey(stocktakingDetailDTO.getHeaderId(),dbShardVO);
        TWmsInventoryEntity inventoryEntity = inventoryService.findInventoryFromCargoOwner(stocktakingDetailDTO.getSkuId(),stocktakingHeaderEntity.getCargoOwnerId(),stocktakingDetailDTO.getStorageRoomId(),dbShardVO);
        if (inventoryEntity == null){
            return MessageResult.getMessage("E81024");
        }
        if(!stocktakingDetailDTO.getCountQty().equals(stocktakingDetailEntityByPk.getCountQty())){
            stocktakingDetailDTO.setCounter(stocktakingDetailEntityByPk.getCounter()+1);
        }
        TWmsStocktakingDetailEntity stocktakingDetailEntity= BeanUtils.copyBeanPropertyUtils(stocktakingDetailDTO,TWmsStocktakingDetailEntity.class);
        stocktakingDetailMapper.updateStocktakingDetail(stocktakingDetailEntity,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();

    }

    @Override
    public PageResponse<List> queryStocktakingDetailByHeader(Map paraMap, DbShardVO dbShardVO) {
        paraMap.put("splitTableKey",getSplitTableKey(dbShardVO));
        List<TWmsStocktakingDetailEntity> mapList = stocktakingDetailMapper.queryDetailPages(paraMap);
        Integer totalSize = stocktakingDetailMapper.queryDetailPageCount(paraMap);
        PageResponse<List> response = new PageResponse();
        response.setRows(mapList);
        response.setTotal(totalSize);
        return response;
    }

    @Override
    public List<TWmsStocktakingDetailEntity> getStocktakingDetails(Long headerId, DbShardVO dbShardVO) {
        List<TWmsStocktakingDetailEntity> mapList = stocktakingDetailMapper.queryStocktakingDetails(headerId,getSplitTableKey(dbShardVO));
        if (CollectionUtils.isNotEmpty(mapList))
        {
            return mapList;
        }
        return null;

    }

    @Override
    public TWmsStocktakingDetailEntity findStocktakingDetailFromCargo(Long skuId,Long headerId, DbShardVO dbShardVO) {
        Map<String, Object> map = new HashMap();
        map.put("skuId", skuId);
        map.put("headerId", headerId);
        map.put("splitTableKey", getSplitTableKey(dbShardVO));
        return stocktakingDetailMapper.selectByCargo(map);
    }

    @Override
    @NeedLog(type = LogType.OPREATION)
    public MessageResult modifySubmitStocktakingDetail(Long id,Integer countQty,String operationUser, DbShardVO dbShardVO) {
        if(id ==null || id==0){
            return MessageResult.getMessage("E80001");
        }
        if(countQty==null){
            return MessageResult.getMessage("E81011");
        }
        TWmsStocktakingDetailEntity stocktakingDetailEntity = stocktakingDetailMapper.selectByPrimaryKey(id,getSplitTableKey(dbShardVO));
        if(stocktakingDetailEntity == null){
            return MessageResult.getMessage("E80001");
        }
        if(stocktakingDetailEntity.getIsTacked() == 0){
            stocktakingDetailEntity.setRecountQty(countQty);
        }else {
            stocktakingDetailEntity.setCountQty(stocktakingDetailEntity.getRecountQty());
            stocktakingDetailEntity.setRecountQty(countQty);
        }
        stocktakingDetailEntity.setCounter(stocktakingDetailEntity.getCounter()+1);
        stocktakingDetailEntity.setIsTacked(new Byte("1"));
        stocktakingDetailEntity.setUpdateUser(operationUser);
        stocktakingDetailEntity.setUpdateTime(new Date().getTime());
        stocktakingDetailMapper.updateStocktakingDetail(stocktakingDetailEntity,getSplitTableKey(dbShardVO));
        this.writeOperationLog(stocktakingDetailEntity.getHeaderId(),stocktakingHeaderService.findByPrimaryKey(stocktakingDetailEntity.getHeaderId(),dbShardVO).getCargoOwnerId(), ActionCode.CYCLECOUNT.toString(),operationUser+"对"+id.toString()+"盘点完成,本次盘点数量为"+countQty.toString(), OrderTypeCode.CycleCount.toString(), OperationStatusCode.Finished.toString(),dbShardVO);
        return MessageResult.getSucMessage();
    }


}
