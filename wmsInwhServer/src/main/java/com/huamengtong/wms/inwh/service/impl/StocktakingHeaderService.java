package com.huamengtong.wms.inwh.service.impl;

import com.huamengtong.wms.client.IAutoIdClient;
import com.huamengtong.wms.constants.AutoIdConstants;
import com.huamengtong.wms.core.annotation.NeedLog;
import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.service.BaseService;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.inventory.TWmsInventoryDTO;
import com.huamengtong.wms.dto.inwh.TWmsAdjustmentDetailDTO;
import com.huamengtong.wms.dto.inwh.TWmsAdjustmentHeaderDTO;
import com.huamengtong.wms.dto.inwh.TWmsStocktakingDetailDTO;
import com.huamengtong.wms.dto.inwh.TWmsStocktakingHeaderDTO;
import com.huamengtong.wms.entity.inventory.TWmsInventoryEntity;
import com.huamengtong.wms.entity.inwh.TWmsStocktakingDetailEntity;
import com.huamengtong.wms.entity.inwh.TWmsStocktakingHeaderEntity;
import com.huamengtong.wms.entity.sku.TWmsSkuEntity;
import com.huamengtong.wms.inventory.service.IInventoryService;
import com.huamengtong.wms.inwh.mapper.StocktakingDetailMapper;
import com.huamengtong.wms.inwh.mapper.StocktakingHeaderMapper;
import com.huamengtong.wms.inwh.service.IStocktakingDetailService;
import com.huamengtong.wms.inwh.service.IStocktakingHeaderService;
import com.huamengtong.wms.main.service.IStorageRoomService;
import com.huamengtong.wms.sku.service.ISkuService;
import com.huamengtong.wms.utils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huamengtong.wms.em.ActionCode;
import com.huamengtong.wms.em.LogType;
import com.huamengtong.wms.em.OperationStatusCode;
import com.huamengtong.wms.em.OrderTypeCode;
import com.huamengtong.wms.em.TicketStatusCode;
import com.huamengtong.wms.em.AdjustTypeCode;

import java.util.Date;
import java.util.List;

/**
 * Created by mario on 2016/11/22.
 */
@Service
public class StocktakingHeaderService extends BaseService implements IStocktakingHeaderService{

    @Autowired
    StocktakingHeaderMapper stocktakingHeaderMapper;
    @Autowired
    StocktakingDetailMapper stocktakingDetailMapper;
    @Autowired
    IAutoIdClient autoIdClient;
    @Autowired
    IStocktakingDetailService stocktakingDetailService;
    @Autowired
    IInventoryService inventoryService;
    @Autowired
    IStorageRoomService storageRoomService;
    @Autowired
    ISkuService skuService;
    @Autowired
    AdjustmentHeaderService adjustmentHeaderService;
    @Autowired
    AdjustmentDetailService adjustmentDetailService;


    @Override
    public PageResponse<List<TWmsStocktakingHeaderEntity>> getStocktakingHeader(TWmsStocktakingHeaderDTO stocktakingHeaderDTO, DbShardVO dbShardVO) {
        TWmsStocktakingHeaderEntity stocktakingHeaderEntity = BeanUtils.copyBeanPropertyUtils(stocktakingHeaderDTO,TWmsStocktakingHeaderEntity.class);
        List<TWmsStocktakingHeaderEntity> stocktakingHeaderEntityList = stocktakingHeaderMapper.queryStocktakingHeaderPages(stocktakingHeaderEntity,getSplitTableKey(dbShardVO));
        Integer totalSize = stocktakingHeaderMapper.queryStocktakingHeaderPageCount(stocktakingHeaderEntity,getSplitTableKey(dbShardVO));
        PageResponse<List<TWmsStocktakingHeaderEntity>> response = new PageResponse();
        response.setRows(stocktakingHeaderEntityList);
        response.setTotal(totalSize);
        return response;
    }

    @Override
    public TWmsStocktakingHeaderEntity findByPrimaryKey(Long id, DbShardVO dbShardVO) {
        return stocktakingHeaderMapper.selectByPrimaryKey(id,getSplitTableKey(dbShardVO));
    }

    @Override
    @NeedLog(type = LogType.OPREATION)
    public MessageResult createStocktakingHeader(TWmsStocktakingHeaderDTO stocktakingHeaderDTO, DbShardVO dbShardVO) {
        if (stocktakingHeaderDTO.getCargoOwnerId() == null || stocktakingHeaderDTO.getCargoOwnerId() == 0){
            return MessageResult.getMessage("E40001");
        }
        if(stocktakingHeaderDTO.getStorageRoomId() == null || stocktakingHeaderDTO.getStorageRoomId() == 0){
            return MessageResult.getMessage("E70011");
        }
        List<TWmsInventoryEntity> inventoryEntities = inventoryService.findInventoryStorageRoom(stocktakingHeaderDTO.getCargoOwnerId(),stocktakingHeaderDTO.getStorageRoomId(),dbShardVO);
        if(CollectionUtils.isEmpty(inventoryEntities)){
            return MessageResult.getMessage("E81006");
        }
        if(checkIsHold(inventoryEntities)){
            return MessageResult.getMessage("E81010");
        }
        stocktakingHeaderDTO.setId(autoIdClient.getAutoId(AutoIdConstants.TWmsStocktakingHeaderEntity));
        stocktakingHeaderDTO.setStatusCode(TicketStatusCode.INIT.toString());
        TWmsStocktakingHeaderEntity stocktakingHeaderEntity = BeanUtils.copyBeanPropertyUtils(stocktakingHeaderDTO,TWmsStocktakingHeaderEntity.class);
        stocktakingHeaderMapper.insertStocktakingHeader(stocktakingHeaderEntity,getSplitTableKey(dbShardVO));
        this.writeOperationLog(stocktakingHeaderEntity.getId(),stocktakingHeaderEntity.getCargoOwnerId(), ActionCode.Create.toString(),stocktakingHeaderEntity.getCreateUser()+"创建盘点单", OrderTypeCode.CycleCount.toString(),OperationStatusCode.Finished.toString(),dbShardVO);
        return MessageResult.getSucMessage();
    }

    private boolean checkIsHold(List<TWmsInventoryEntity> inventoryEntityList){
        for (TWmsInventoryEntity inventoryEntity:inventoryEntityList){
            if(inventoryEntity.getIsHold() == 1){
                return true;
            }
        }
        return false;
    }

    @Override
    public MessageResult removeStocktakingHeader(Long id, DbShardVO dbShardVO) {
        List<TWmsStocktakingDetailEntity> stocktakingDetailEntities = stocktakingDetailService.getStocktakingDetails(id,dbShardVO);
        if (CollectionUtils.isNotEmpty(stocktakingDetailEntities)) {
            for (TWmsStocktakingDetailEntity stocktakingDetailEntity : stocktakingDetailEntities) {
                stocktakingDetailService.removeStocktakingDetail(stocktakingDetailEntity.getId(), dbShardVO);
            }
        }
        stocktakingHeaderMapper.deleteStocktakingHeader(id,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult modifyStocktakingHeader(TWmsStocktakingHeaderDTO stocktakingHeaderDTO, DbShardVO dbShardVO) {
        TWmsStocktakingHeaderEntity stocktakingHeaderEntity = BeanUtils.copyBeanPropertyUtils(stocktakingHeaderDTO,TWmsStocktakingHeaderEntity.class);
        List<TWmsInventoryEntity> inventoryEntities = inventoryService.findInventoryStorageRoom(stocktakingHeaderEntity.getCargoOwnerId(),stocktakingHeaderEntity.getStorageRoomId(),dbShardVO);
        if(CollectionUtils.isEmpty(inventoryEntities)){
            return MessageResult.getMessage("E81006");
        }
        stocktakingHeaderMapper.updateStocktakingHeader(stocktakingHeaderEntity,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }


    /**
     * 盘点提交
     * @param headerId
     * @param operationUser
     * @param dbShardVO
     * @return
     */
    @Override
    @NeedLog(type = LogType.OPREATION)
    public MessageResult updateSubmitStocktaking(Long headerId, String operationUser, DbShardVO dbShardVO) {
        //判断参数是否为空
        if (headerId == null || headerId==0){
            return MessageResult.getMessage("E81004");
        }
        TWmsStocktakingHeaderEntity stocktakingHeaderEntity = stocktakingHeaderMapper.selectByPrimaryKey(headerId,getSplitTableKey(dbShardVO));
        //判断通知单主表是否存在&是否包含明细单&主表状态是否为未提交
        if (stocktakingHeaderEntity == null){
            return MessageResult.getMessage("E81002",new Object[]{headerId});
        }
        if(!stocktakingHeaderEntity.getStatusCode().equals(TicketStatusCode.INIT.toString())){
            return  MessageResult.getMessage("E81009",new Object[]{headerId});
        }
        //根据盘点头表数据查询得到库存数据
        List<TWmsInventoryEntity> inventoryEntities = inventoryService.findInventoryStorageRoom(stocktakingHeaderEntity.getCargoOwnerId(),stocktakingHeaderEntity.getStorageRoomId(),dbShardVO);
        if (CollectionUtils.isEmpty(inventoryEntities)){
            return  MessageResult.getMessage("E81003");
        }
       for (TWmsInventoryEntity inventoryEntity:inventoryEntities){
           //如果货主商品已冻结
           if(inventoryEntity.getIsHold() == 1){
               continue;
           }
           //锁住库存
           inventoryEntity.setIsHold(new Byte("1"));
           inventoryService.modifyInventory(BeanUtils.copyBeanPropertyUtils(inventoryEntity,TWmsInventoryDTO.class),dbShardVO);
           // 生成盘点明细数据
           TWmsSkuEntity skuEntity = skuService.findByPrimaryKey(inventoryEntity.getSkuId(),getSkuDbShareVO(dbShardVO));
           TWmsStocktakingDetailDTO stocktakingDetailDTO = new TWmsStocktakingDetailDTO();
           stocktakingDetailDTO.setHeaderId(headerId);
           stocktakingDetailDTO.setTenantId(inventoryEntity.getTenantId());
           stocktakingDetailDTO.setStorageRoomId(inventoryEntity.getStorageRoomId());
           stocktakingDetailDTO.setSkuId(inventoryEntity.getSkuId());
           stocktakingDetailDTO.setSkuName(skuEntity.getItemName());
           stocktakingDetailDTO.setSkuBarcode(skuEntity.getBarcode());

           int qty = getActiveQty(inventoryEntity);
           stocktakingDetailDTO.setSystemQty(qty);

           stocktakingDetailDTO.setOperationName(operationUser);
           stocktakingDetailDTO.setCreateUser(operationUser);
           stocktakingDetailDTO.setCreateTime(new Date().getTime());
           stocktakingDetailDTO.setUpdateUser(operationUser);
           stocktakingDetailDTO.setUpdateTime(new Date().getTime());
           stocktakingDetailService.createStocktakingDetailDetail(stocktakingDetailDTO,dbShardVO);
        }
        stocktakingHeaderEntity.setStatusCode(TicketStatusCode.SUBMIT.toString());
        stocktakingHeaderEntity.setTotalCategoryQty(inventoryEntities.size());
        stocktakingHeaderEntity.setSubmitUser(operationUser);
        stocktakingHeaderEntity.setSubmitTime(new Date().getTime());
        stocktakingHeaderEntity.setUpdateUser(operationUser);
        stocktakingHeaderEntity.setUpdateTime(new Date().getTime());
        stocktakingHeaderMapper.updateStocktakingHeader(stocktakingHeaderEntity,getSplitTableKey(dbShardVO));
        this.writeOperationLog(headerId,stocktakingHeaderEntity.getCargoOwnerId(),ActionCode.Submit.toString(),operationUser+"提交盘点单",OrderTypeCode.CycleCount.toString(),OperationStatusCode.Finished.toString(),dbShardVO);
        return MessageResult.getSucMessage();

    }


    /**
     * 盘点确认
     * @param headerId
     * @param operationUser
     * @param dbShardVO
     * @return
     */
    @Override
    @NeedLog(type = LogType.OPREATION)
    public MessageResult updateConfirmStocktaking(Long headerId, String operationUser, DbShardVO dbShardVO) {
        //判断参数是否为空
        if (headerId == null){
            return MessageResult.getMessage("E81004");
        }
        TWmsStocktakingHeaderEntity stocktakingHeaderEntity = stocktakingHeaderMapper.selectByPrimaryKey(headerId,getSplitTableKey(dbShardVO));
        List<TWmsStocktakingDetailEntity> stocktakingDetailEntityList = stocktakingDetailMapper.queryStocktakingDetails(stocktakingHeaderEntity.getId(),getSplitTableKey(dbShardVO));

        //判断盘点单主表是否存在&是否包含明细单&主表状态是否为未提交
        if (stocktakingHeaderEntity == null){
            return MessageResult.getMessage("E81002",new Object[]{headerId});
        }
        if (CollectionUtils.isEmpty(stocktakingDetailEntityList)){
            return MessageResult.getMessage("E81005",new Object[]{headerId});
        }
        if(!stocktakingHeaderEntity.getStatusCode().equals(TicketStatusCode.SUBMIT.toString())){
            return  MessageResult.getMessage("E81009",new Object[]{headerId});
        }
        if(!checkIsTracked(stocktakingDetailEntityList)){
            return MessageResult.getMessage("E81012");
        }
        //根据盘点明细数据查询得到库存数据
        Long adjustHeaderId = autoIdClient.getAutoId(AutoIdConstants.TWmsAdjustmentHeaderEntity);
        for(TWmsStocktakingDetailEntity stocktakingDetailEntity:stocktakingDetailEntityList){
            TWmsInventoryEntity inventoryEntity = inventoryService.findInventoryFromCargoOwner(stocktakingDetailEntity.getSkuId(),stocktakingHeaderEntity.getCargoOwnerId(),stocktakingDetailEntity.getStorageRoomId(),dbShardVO);
            //通过盘点明细创建调整头表数据
            //Integer qty = inventoryEntity.getOnhandQty() -inventoryEntity.getAllocatedQty() - inventoryEntity.getMortgagedQty()-inventoryEntity.getWorkingQty()-inventoryEntity.getPickedQty();
            int qty = getActiveQty(inventoryEntity);
            //根据库存数据得到盘点明细数据,通过盘点明细生成调整明细
            TWmsAdjustmentDetailDTO adjustmentDetailDTO = new TWmsAdjustmentDetailDTO();
            adjustmentDetailDTO.setId(autoIdClient.getAutoId(AutoIdConstants.TWmsAdjustmentDetailEntity));
            adjustmentDetailDTO.setAdjustId(adjustHeaderId);
            adjustmentDetailDTO.setTenantId(stocktakingDetailEntity.getTenantId());
            adjustmentDetailDTO.setZoneId(stocktakingDetailEntity.getZoneId());
            adjustmentDetailDTO.setLocationId(stocktakingDetailEntity.getLocationId());
            adjustmentDetailDTO.setStorageRoomId(stocktakingDetailEntity.getStorageRoomId());
            adjustmentDetailDTO.setSkuId(stocktakingDetailEntity.getSkuId());
            adjustmentDetailDTO.setAdjustedBeforeQty(qty);
            adjustmentDetailDTO.setAdjustedAfterQty(stocktakingDetailEntity.getRecountQty());
            adjustmentDetailDTO.setAdjustedQty(stocktakingDetailEntity.getRecountQty()-qty);
            adjustmentDetailDTO.setCreateUser(operationUser);
            adjustmentDetailDTO.setCreateTime(new Date().getTime());
            adjustmentDetailDTO.setUpdateUser(operationUser);
            adjustmentDetailDTO.setUpdateTime(new Date().getTime());
            adjustmentDetailService.createAdjustmentDetailByMove(adjustmentDetailDTO, dbShardVO);
        }
        //创建调整单主表信息
        TWmsAdjustmentHeaderDTO adjustmentHeaderDTO = new TWmsAdjustmentHeaderDTO();
        adjustmentHeaderDTO.setId(adjustHeaderId);
        adjustmentHeaderDTO.setTenantId(stocktakingHeaderEntity.getTenantId());
        adjustmentHeaderDTO.setWarehouseId(stocktakingHeaderEntity.getWarehouseId());
        adjustmentHeaderDTO.setcargoOwnerId(stocktakingHeaderEntity.getCargoOwnerId());
        adjustmentHeaderDTO.setCreateUser(operationUser);
        adjustmentHeaderDTO.setResonCode(AdjustTypeCode.STOCKTAKING.toString());
        adjustmentHeaderDTO.setCreateTime(new Date().getTime());
        adjustmentHeaderDTO.setUpdateUser(operationUser);
        adjustmentHeaderDTO.setUpdateTime(new Date().getTime());
        adjustmentHeaderService.createAdjustmentHeader(adjustmentHeaderDTO,dbShardVO);

        //修改盘点单信息
        stocktakingHeaderEntity.setStatusCode(TicketStatusCode.CONFIRM.toString());
        stocktakingHeaderEntity.setUpdateUser(operationUser);
        stocktakingHeaderEntity.setUpdateTime(new Date().getTime());
        stocktakingHeaderMapper.updateStocktakingHeader(stocktakingHeaderEntity,getSplitTableKey(dbShardVO));
        this.writeOperationLog(headerId,stocktakingHeaderEntity.getCargoOwnerId(),ActionCode.Confirm.toString(),operationUser+"确认盘点单",OrderTypeCode.CycleCount.toString(),OperationStatusCode.Finished.toString(),dbShardVO);
        return MessageResult.getSucMessage();
    }

    private boolean checkIsTracked(List<TWmsStocktakingDetailEntity> stocktakingDetailEntityList){
        for (TWmsStocktakingDetailEntity stocktakingDetailEntity:stocktakingDetailEntityList){
            if(stocktakingDetailEntity.getIsTacked() == 0){
                return false;
            }
        }
        return true;
    }
    private Integer getActiveQty(TWmsInventoryEntity inventoryEntity){
        return  inventoryEntity.getOnhandQty() -inventoryEntity. getAllocatedQty() - inventoryEntity.getMortgagedQty()-inventoryEntity.getWorkingQty()-inventoryEntity.getPickedQty()-inventoryEntity.getPackageQty();
    }

}
