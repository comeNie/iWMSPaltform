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
import com.huamengtong.wms.dto.inwh.TWmsMoveDTO;
import com.huamengtong.wms.entity.inventory.TWmsInventoryEntity;
import com.huamengtong.wms.entity.inwh.TWmsMoveEntity;
import com.huamengtong.wms.entity.main.TWmsStorageRoomEntity;
import com.huamengtong.wms.entity.sku.TWmsSkuEntity;
import com.huamengtong.wms.inventory.service.IInventoryService;
import com.huamengtong.wms.inwh.service.IAdjustmentDetailService;
import com.huamengtong.wms.inwh.service.IAdjustmentHeaderService;
import com.huamengtong.wms.inwh.service.IMoveService;
import com.huamengtong.wms.inwh.mapper.MoveMapper;
import com.huamengtong.wms.main.service.IStorageRoomService;
import com.huamengtong.wms.sku.service.ISkuService;
import com.huamengtong.wms.utils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huamengtong.wms.em.ActionCode;
import com.huamengtong.wms.em.LogType;
import com.huamengtong.wms.em.OperationStatusCode;
import com.huamengtong.wms.em.OrderTypeCode;
import com.huamengtong.wms.em.TicketStatusCode;
import com.huamengtong.wms.em.AdjustTypeCode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mario on 2016/11/18.
 */
@Service
public class MoveService extends BaseService implements IMoveService {

    @Autowired
    IAutoIdClient autoIdClient;

    @Autowired
    MoveMapper moveMapper;

    @Autowired
    IInventoryService inventoryService;

    @Autowired
    ISkuService skuService;

    @Autowired
    IStorageRoomService storageRoomService;

    @Autowired
    IAdjustmentHeaderService adjustmentHeaderService;

    @Autowired
    IAdjustmentDetailService adjustmentDetailService;

    @Override
    public PageResponse<List<TWmsMoveEntity>> getMove(TWmsMoveDTO moveDTO, DbShardVO dbShardVO) {
        TWmsMoveEntity moveEntity = BeanUtils.copyBeanPropertyUtils(moveDTO,TWmsMoveEntity.class);
        List<TWmsMoveEntity> moveEntityList = moveMapper.queryMovePages(moveEntity,getSplitTableKey(dbShardVO));
        if(CollectionUtils.isEmpty(moveEntityList)){
            return null;
        }
        List<TWmsMoveEntity> moveEntityListDTO = new ArrayList<>();
        moveEntityList.stream().forEach(x -> {
            TWmsSkuEntity skuEntity = skuService.findByPrimaryKey(x.getSkuId(), getSkuDbShareVO(dbShardVO));
            if (skuEntity != null) {
                x.setSku(skuEntity.getSku());
                x.setSkuName(skuEntity.getItemName());
                x.setBarcode(skuEntity.getBarcode());
            }
            moveEntityListDTO.add(x);
        });
        Integer totalSize = moveMapper.queryMovePageCount(moveEntity,getSplitTableKey(dbShardVO));
        PageResponse<List<TWmsMoveEntity>> response = new PageResponse();
        response.setRows(moveEntityListDTO);
        response.setTotal(totalSize);
        return response;
    }

    @Override
    public TWmsMoveEntity findByPrimaryKey(Long id, DbShardVO dbShardVO) {
        return moveMapper.selectByPrimaryKey(id,getSplitTableKey(dbShardVO));
    }

    @Override
    @NeedLog(type = LogType.OPREATION)
    public MessageResult createMove(TWmsMoveDTO moveDTO, DbShardVO dbShardVO) {
        if (moveDTO.getCargoOwnerId()== null || moveDTO.getCargoOwnerId() == 0){
            return MessageResult.getMessage("E40001");
        }
        if (moveDTO.getFromRoomId() == null || moveDTO.getFromRoomId() == 0) {
            return MessageResult.getMessage("E40004");
        }
        if (moveDTO.getToRoomId() == null || moveDTO.getToRoomId() == 0) {
            return MessageResult.getMessage("E40005");
        }
        if (StringUtils.isEmpty(moveDTO.getMoveReason())){
            return MessageResult.getMessage("E40006");
        }
        if (moveDTO.getFromRoomId().equals(moveDTO.getToRoomId())){
            return  MessageResult.getMessage("E81023");
        }
        if (moveDTO.getSkuId() == null || moveDTO.getSkuId() ==0){
            List<TWmsInventoryEntity> inventoryEntitiesfo = inventoryService.findInventoryStorageRoom(moveDTO.getCargoOwnerId(),moveDTO.getFromRoomId(),dbShardVO);
            if (CollectionUtils.isEmpty(inventoryEntitiesfo)) {
                return  MessageResult.getMessage("E81024");
            }
            Integer sum = 0;
            for (TWmsInventoryEntity inventoryEntityfo : inventoryEntitiesfo) {
                 if(inventoryEntityfo.getAllocatedQty() !=0 || inventoryEntityfo.getMortgagedQty() !=0 || inventoryEntityfo.getWorkingQty()!=0 || inventoryEntityfo.getPickedQty()!=0 ){
                    return MessageResult.getMessage("E81013");
                 }
                 if(inventoryEntityfo.getIsHold() == 1){
                    return MessageResult.getMessage("E81010");
                 }
                //Integer foQty = inventoryEntityfo.getOnhandQty() -inventoryEntityfo. getAllocatedQty() - inventoryEntityfo.getMortgagedQty() - inventoryEntityfo.getWorkingQty()-inventoryEntityfo.getPickedQty();
                Integer foQty = getActiveQty(inventoryEntityfo);
                sum +=foQty;
            }
            moveDTO.setMovedQty(sum);
        }else {
            TWmsInventoryEntity inventoryEntity = inventoryService.findInventoryFromCargoOwner(moveDTO.getSkuId(),moveDTO.getCargoOwnerId(),moveDTO.getFromRoomId(),dbShardVO);
            if (inventoryEntity == null) {
                return  MessageResult.getMessage("E81024");
            }
            if(inventoryEntity.getAllocatedQty() !=0 || inventoryEntity.getMortgagedQty() !=0 || inventoryEntity.getWorkingQty()!=0 || inventoryEntity.getPickedQty()!=0 ){
                return MessageResult.getMessage("E81013");
            }
            if(inventoryEntity.getIsHold() == 1){
                return MessageResult.getMessage("E90006");
            }
            //Integer availableQty = inventoryEntity.getOnhandQty()-inventoryEntity.getMortgagedQty()-inventoryEntity.getPickedQty()-inventoryEntity.getWorkingQty()-inventoryEntity.getAllocatedQty();
            Integer availableQty = getActiveQty(inventoryEntity);
            if(availableQty<moveDTO.getMovedQty()){
                return MessageResult.getMessage("E81022");
            }
        }
        TWmsMoveEntity moveEntity = BeanUtils.copyBeanPropertyUtils(moveDTO,TWmsMoveEntity.class);
        moveEntity.setStatusCode(TicketStatusCode.INIT.toString());
        moveEntity.setId(autoIdClient.getAutoId(AutoIdConstants.TWmsMoveEntity));
        moveMapper.insertMove(moveEntity,getSplitTableKey(dbShardVO));
        this.writeOperationLog(moveEntity.getId(),moveEntity.getCargoOwnerId(), ActionCode.Create.toString(),"创建移库单", OrderTypeCode.Move.toString(),OperationStatusCode.Finished.toString(),dbShardVO);
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
    public MessageResult removeMove(Long id, DbShardVO dbShardVO) {
        moveMapper.deleteMove(id,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult modifyMove(TWmsMoveDTO moveDTO, DbShardVO dbShardVO) {
        if (moveDTO.getFromRoomId().equals(moveDTO.getToRoomId())){
            return  MessageResult.getMessage("E81023");
        }
        TWmsMoveEntity moveEntity  = moveMapper.selectByPrimaryKey(moveDTO.getId(),getSplitTableKey(dbShardVO));
        TWmsStorageRoomEntity storageRoomEntity = storageRoomService.findByPrimaryKey(moveDTO.getFromRoomId());
        //移动整个库房判断
        if (moveDTO.getSkuId() == null || moveDTO.getSkuId() ==0){
            if(!moveDTO.getFromRoomId().equals(moveEntity.getFromRoomId())){
                List<TWmsInventoryEntity> inventoryEntitiesfo = inventoryService.findInventoryStorageRoom(moveDTO.getCargoOwnerId(),moveDTO.getFromRoomId(),dbShardVO);
                if (CollectionUtils.isEmpty(inventoryEntitiesfo)){
                    return  MessageResult.getMessage("E81024");
                }
//                if(checkIsHold(inventoryEntitiesfo)){
//                    return MessageResult.getMessage("E81010");
//                }
                int sum = 0;
                for (TWmsInventoryEntity inventoryEntityfo : inventoryEntitiesfo) {
                    if (inventoryEntityfo == null) {
                        return  MessageResult.getMessage("E81020",new Object[]{storageRoomEntity.getRoomNo()});
                    }
                    if(inventoryEntityfo.getIsHold() == 1){
                        return MessageResult.getMessage("E81010");
                    }
                    //int foQty = inventoryEntityfo.getOnhandQty() -inventoryEntityfo. getAllocatedQty() - inventoryEntityfo.getMortgagedQty() - inventoryEntityfo.getWorkingQty()-inventoryEntityfo.getPickedQty();
                    Integer foQty = getActiveQty(inventoryEntityfo);
                    sum +=foQty;
                }
                moveDTO.setMovedQty(sum);
            }
        }else {
            //移动商品判断 来源库房是否有库存
            TWmsInventoryEntity inventoryEntity = inventoryService.findInventoryFromCargoOwner(moveDTO.getSkuId(),moveDTO.getCargoOwnerId(),moveDTO.getFromRoomId(),dbShardVO);
            if (inventoryEntity == null){
                return  MessageResult.getMessage("E81020",new Object[]{storageRoomEntity.getRoomNo()});
            }
            if(inventoryEntity.getIsHold() == 1){
                return MessageResult.getMessage("E90006");
            }
        }
        //判断来源库房与目的库房是否一致
        if (moveDTO.getFromRoomId() == moveDTO.getToRoomId()){
            return  MessageResult.getMessage("E81023");
        }
        TWmsMoveEntity moveEntity1 = BeanUtils.copyBeanPropertyUtils(moveDTO,TWmsMoveEntity.class);
        moveMapper.updateMove(moveEntity1,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }

    @Override
    @NeedLog(type = LogType.OPREATION)
    public MessageResult executeSubmitMove(Long id, String operationUser, DbShardVO dbShardVO) {
        if (id==null || id==0){
            return MessageResult.getMessage("E80001");
        }
        TWmsMoveEntity moveEntity = this.findByPrimaryKey(id,dbShardVO);
        if (moveEntity == null) {
            return MessageResult.getMessage("E90003", new Object[]{id});
        }
        if(!moveEntity.getStatusCode().equals(TicketStatusCode.INIT.toString())){
            return MessageResult.getMessage("E80004",new Object[]{ id });
        }
        TWmsStorageRoomEntity storageRoomEntity = storageRoomService.findByPrimaryKey(moveEntity.getFromRoomId());
        //移动某件商品的库存调整记录创建
        if (moveEntity.getSkuId() == null || moveEntity.getSkuId()==0){
            //根据移库信息创建调整表头
            List<TWmsInventoryEntity> inventoryEntitiesfo = inventoryService.findInventoryStorageRoom(moveEntity.getCargoOwnerId(),moveEntity.getFromRoomId(),dbShardVO);
            if (CollectionUtils.isEmpty(inventoryEntitiesfo)) {
                return  MessageResult.getMessage("E81020",new Object[]{storageRoomEntity.getRoomNo()});
            }
            if(checkIsHold(inventoryEntitiesfo)){
                return MessageResult.getMessage("E81010");
            }
            //move 创建调整头表数据
            TWmsAdjustmentHeaderDTO adjustmentHeaderDTO = new TWmsAdjustmentHeaderDTO();
            adjustmentHeaderDTO.setId(autoIdClient.getAutoId(AutoIdConstants.TWmsAdjustmentHeaderEntity));
            adjustmentHeaderDTO.setTenantId(moveEntity.getTenantId());
            adjustmentHeaderDTO.setWarehouseId(moveEntity.getWarehouseId());
            adjustmentHeaderDTO.setcargoOwnerId(moveEntity.getCargoOwnerId());
            adjustmentHeaderDTO.setReferNo(moveEntity.getReferNo());
            adjustmentHeaderDTO.setResonCode(AdjustTypeCode.MOVE.toString());
            adjustmentHeaderDTO.setCreateUser(operationUser);
            adjustmentHeaderDTO.setCreateTime(new Date().getTime());
            adjustmentHeaderDTO.setUpdateUser(operationUser);
            adjustmentHeaderDTO.setUpdateTime(new Date().getTime());
            //根据来源库房创建明细
            for (TWmsInventoryEntity inventoryEntityfo : inventoryEntitiesfo){
                //判断来源库房是否有满足调整数量
                //根据来源库房创建调整单明细
                //Integer foQty = inventoryEntityfo.getOnhandQty() - inventoryEntityfo. getAllocatedQty() - inventoryEntityfo.getMortgagedQty()-inventoryEntityfo.getWorkingQty()-inventoryEntityfo.getPickedQty();
                Integer foQty = getActiveQty(inventoryEntityfo);
                //如果库存中无可用库存，不生成调整记录
                if(foQty <= 0){
                    continue;
                }
                TWmsAdjustmentDetailDTO adjustmentDetailDTOFrom = new TWmsAdjustmentDetailDTO();
                adjustmentDetailDTOFrom.setId(autoIdClient.getAutoId(AutoIdConstants.TWmsAdjustmentDetailEntity));
                adjustmentDetailDTOFrom.setAdjustId(adjustmentHeaderDTO.getId());
                adjustmentDetailDTOFrom.setTenantId(moveEntity.getTenantId());
                adjustmentDetailDTOFrom.setReferDetailNo(moveEntity.getReferNo());
                adjustmentDetailDTOFrom.setZoneId(moveEntity.getFromZoneId());
                adjustmentDetailDTOFrom.setLocationId(moveEntity.getFromLocationId());
                adjustmentDetailDTOFrom.setStorageRoomId(moveEntity.getFromRoomId());
                adjustmentDetailDTOFrom.setSkuId(inventoryEntityfo.getSkuId());
                adjustmentDetailDTOFrom.setAdjustedBeforeQty(foQty);
                adjustmentDetailDTOFrom.setAdjustedAfterQty(0);
                adjustmentDetailDTOFrom.setAdjustedQty(0-foQty);
                adjustmentDetailDTOFrom.setCreateUser(operationUser);
                adjustmentDetailDTOFrom.setCreateTime(new Date().getTime());
                adjustmentDetailDTOFrom.setUpdateUser(operationUser);
                adjustmentDetailDTOFrom.setUpdateTime(new Date().getTime());
                //冻结来源库房
                inventoryEntityfo.setIsHold(new Byte("1"));
                //创建目的库房调整明细
                TWmsAdjustmentDetailDTO adjustmentDetailDTOTo = new TWmsAdjustmentDetailDTO();
                adjustmentDetailDTOTo.setId(autoIdClient.getAutoId(AutoIdConstants.TWmsAdjustmentDetailEntity));
                adjustmentDetailDTOTo.setAdjustId(adjustmentHeaderDTO.getId());
                adjustmentDetailDTOTo.setReferDetailNo(moveEntity.getReferNo());
                adjustmentDetailDTOTo.setTenantId(moveEntity.getTenantId());
                adjustmentDetailDTOTo.setZoneId(moveEntity.getToZoneId());
                adjustmentDetailDTOTo.setLocationId(moveEntity.getToLocationId());
                adjustmentDetailDTOTo.setStorageRoomId(moveEntity.getToRoomId());
                adjustmentDetailDTOTo.setSkuId(inventoryEntityfo.getSkuId());
                TWmsInventoryEntity inventoryEntityTo = inventoryService.findInventoryFromCargoOwner(inventoryEntityfo.getSkuId(),moveEntity.getCargoOwnerId(),moveEntity.getToRoomId(),dbShardVO);
                if (inventoryEntityTo == null){
                    adjustmentDetailDTOTo.setAdjustedBeforeQty(0);
                    adjustmentDetailDTOTo.setAdjustedAfterQty(foQty);
                    adjustmentDetailDTOTo.setAdjustedQty(foQty);
                }else {
                    //Integer toQty = inventoryEntityTo.getOnhandQty() -inventoryEntityTo. getAllocatedQty() - inventoryEntityTo.getMortgagedQty()-inventoryEntityTo.getWorkingQty()-inventoryEntityTo.getPickedQty();
                    Integer toQty = getActiveQty(inventoryEntityTo);
                    adjustmentDetailDTOTo.setAdjustedBeforeQty(toQty);
                    adjustmentDetailDTOTo.setAdjustedAfterQty(toQty + foQty);
                    adjustmentDetailDTOTo.setAdjustedQty(foQty);
                    //冻结目的库房
                    inventoryEntityTo.setIsHold(new Byte("1"));
                    inventoryService.modifyInventory(BeanUtils.copyBeanPropertyUtils(inventoryEntityTo,TWmsInventoryDTO.class),dbShardVO);
                }
                adjustmentDetailDTOTo.setCreateUser(operationUser);
                adjustmentDetailDTOTo.setCreateTime(new Date().getTime());
                adjustmentDetailDTOTo.setUpdateUser(operationUser);
                adjustmentDetailDTOTo.setUpdateTime(new Date().getTime());
                inventoryService.modifyInventory(BeanUtils.copyBeanPropertyUtils(inventoryEntityfo,TWmsInventoryDTO.class),dbShardVO);
                adjustmentDetailService.createAdjustmentDetailByMove(adjustmentDetailDTOTo,dbShardVO);
                adjustmentDetailService.createAdjustmentDetailByMove(adjustmentDetailDTOFrom, dbShardVO);
            }
            adjustmentHeaderService.createAdjustmentHeader(adjustmentHeaderDTO,dbShardVO);
        }else{
            TWmsInventoryEntity inventoryEntityfo = inventoryService.findInventoryFromCargoOwner(moveEntity.getSkuId(),moveEntity.getCargoOwnerId(),moveEntity.getFromRoomId(),dbShardVO);
            if (inventoryEntityfo == null) {
                return  MessageResult.getMessage("E81020",new Object[]{storageRoomEntity.getRoomNo()});
            }
            if(inventoryEntityfo.getIsHold() == 1){
                return MessageResult.getMessage("E81010");
            }
            //int foQty = inventoryEntityfo.getOnhandQty() - inventoryEntityfo. getAllocatedQty() - inventoryEntityfo.getMortgagedQty()-inventoryEntityfo.getWorkingQty()-inventoryEntityfo.getPickedQty();
            int foQty = getActiveQty(inventoryEntityfo);
            if (foQty < moveEntity.getMovedQty()) {
                return  MessageResult.getMessage("E81021",new Object[]{storageRoomEntity.getRoomNo()});
            }
            TWmsInventoryEntity inventoryEntityTo = inventoryService.findInventoryFromCargoOwner(moveEntity.getSkuId(),moveEntity.getCargoOwnerId(),moveEntity.getToRoomId(),dbShardVO);
            //move 创建调整头表数据
            TWmsAdjustmentHeaderDTO adjustmentHeaderDTO = new TWmsAdjustmentHeaderDTO();
            adjustmentHeaderDTO.setId(autoIdClient.getAutoId(AutoIdConstants.TWmsAdjustmentHeaderEntity));
            adjustmentHeaderDTO.setTenantId(moveEntity.getTenantId());
            adjustmentHeaderDTO.setWarehouseId(moveEntity.getWarehouseId());
            adjustmentHeaderDTO.setcargoOwnerId(moveEntity.getCargoOwnerId());
            adjustmentHeaderDTO.setReferNo(moveEntity.getReferNo());
            adjustmentHeaderDTO.setResonCode(AdjustTypeCode.MOVE.toString());
            adjustmentHeaderDTO.setCreateUser(operationUser);
            adjustmentHeaderDTO.setCreateTime(new Date().getTime());
            adjustmentHeaderDTO.setUpdateUser(operationUser);
            adjustmentHeaderDTO.setUpdateTime(new Date().getTime());
            // 根据来源库房创建调整单明细
            TWmsAdjustmentDetailDTO adjustmentDetailDTOFrom = new TWmsAdjustmentDetailDTO();
            adjustmentDetailDTOFrom.setId(autoIdClient.getAutoId(AutoIdConstants.TWmsAdjustmentDetailEntity));
            adjustmentDetailDTOFrom.setAdjustId(adjustmentHeaderDTO.getId());
            adjustmentDetailDTOFrom.setTenantId(moveEntity.getTenantId());
            adjustmentDetailDTOFrom.setReferDetailNo(moveEntity.getReferNo());
            adjustmentDetailDTOFrom.setZoneId(moveEntity.getFromZoneId());
            adjustmentDetailDTOFrom.setLocationId(moveEntity.getFromLocationId());
            adjustmentDetailDTOFrom.setStorageRoomId(moveEntity.getFromRoomId());
            adjustmentDetailDTOFrom.setSkuId(moveEntity.getSkuId());
            adjustmentDetailDTOFrom.setAdjustedBeforeQty(foQty);
            adjustmentDetailDTOFrom.setAdjustedAfterQty(foQty - moveEntity.getMovedQty());
            adjustmentDetailDTOFrom.setAdjustedQty(adjustmentDetailDTOFrom.getAdjustedAfterQty() - foQty);
            adjustmentDetailDTOFrom.setCreateUser(operationUser);
            adjustmentDetailDTOFrom.setCreateTime(new Date().getTime());
            adjustmentDetailDTOFrom.setUpdateUser(operationUser);
            adjustmentDetailDTOFrom.setUpdateTime(new Date().getTime());
            //冻结来源库房
            inventoryEntityfo.setIsHold(new Byte("1"));
            //根据目的库房创建调整表单明细
            TWmsAdjustmentDetailDTO adjustmentDetailDTOTo = new TWmsAdjustmentDetailDTO();
            adjustmentDetailDTOTo.setId(autoIdClient.getAutoId(AutoIdConstants.TWmsAdjustmentDetailEntity));
            adjustmentDetailDTOTo.setAdjustId(adjustmentHeaderDTO.getId());
            adjustmentDetailDTOTo.setReferDetailNo(moveEntity.getReferNo());
            adjustmentDetailDTOTo.setTenantId(moveEntity.getTenantId());
            adjustmentDetailDTOTo.setZoneId(moveEntity.getToZoneId());
            adjustmentDetailDTOTo.setLocationId(moveEntity.getToLocationId());
            adjustmentDetailDTOTo.setStorageRoomId(moveEntity.getToRoomId());
            adjustmentDetailDTOTo.setSkuId(moveEntity.getSkuId());
            if (inventoryEntityTo == null){
                adjustmentDetailDTOTo.setAdjustedBeforeQty(0);
                adjustmentDetailDTOTo.setAdjustedAfterQty(moveEntity.getMovedQty());
                adjustmentDetailDTOTo.setAdjustedQty(moveEntity.getMovedQty());
            }else {
                //int ToQty = inventoryEntityTo.getOnhandQty() -inventoryEntityTo. getAllocatedQty() - inventoryEntityTo.getMortgagedQty()-inventoryEntityTo.getWorkingQty()-inventoryEntityTo.getPickedQty();
                int ToQty = getActiveQty(inventoryEntityTo);
                adjustmentDetailDTOTo.setAdjustedBeforeQty(ToQty);
                adjustmentDetailDTOTo.setAdjustedAfterQty(ToQty + moveEntity.getMovedQty());
                adjustmentDetailDTOTo.setAdjustedQty(moveEntity.getMovedQty());
                //冻结目的库房
                inventoryEntityTo.setIsHold(new Byte("1"));
                inventoryService.modifyInventory(BeanUtils.copyBeanPropertyUtils(inventoryEntityTo,TWmsInventoryDTO.class),dbShardVO);
            }
            adjustmentDetailDTOTo.setCreateUser(operationUser);
            adjustmentDetailDTOTo.setCreateTime(new Date().getTime());
            adjustmentDetailDTOTo.setUpdateUser(operationUser);
            adjustmentDetailDTOTo.setUpdateTime(new Date().getTime());
            inventoryService.modifyInventory(BeanUtils.copyBeanPropertyUtils(inventoryEntityfo,TWmsInventoryDTO.class),dbShardVO);
            adjustmentDetailService.createAdjustmentDetailByMove(adjustmentDetailDTOFrom, dbShardVO);
            adjustmentDetailService.createAdjustmentDetailByMove(adjustmentDetailDTOTo,dbShardVO);
            adjustmentHeaderService.createAdjustmentHeader(adjustmentHeaderDTO,dbShardVO);
        }
        moveEntity.setStatusCode(TicketStatusCode.SUBMIT.toString());
        moveEntity.setUpdateUser(operationUser);
        moveEntity.setUpdateTime(new Date().getTime());
        moveEntity.setSubmitUser(operationUser);
        moveEntity.setSubmitTime(new Date().getTime());
        moveMapper.updateMove(moveEntity,getSplitTableKey(dbShardVO));
        this.writeOperationLog(id,moveEntity.getCargoOwnerId(),ActionCode.Submit.toString(),operationUser+"提交移库单",OrderTypeCode.Move.toString(),OperationStatusCode.Finished.toString(),dbShardVO);
        return MessageResult.getSucMessage();
    }

    private Integer getActiveQty(TWmsInventoryEntity inventoryEntityTo){
       return  inventoryEntityTo.getOnhandQty() -inventoryEntityTo. getAllocatedQty() - inventoryEntityTo.getMortgagedQty()-inventoryEntityTo.getWorkingQty()-inventoryEntityTo.getPickedQty()-inventoryEntityTo.getPackageQty();
    }

}
