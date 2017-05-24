package com.huamengtong.wms.outwh.service.impl;

import com.huamengtong.wms.core.annotation.NeedLog;
import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.service.BaseService;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.dto.inventory.TWmsInventoryDTO;
import com.huamengtong.wms.dto.outwh.TWmsShipmentDetailDTO;
import com.huamengtong.wms.dto.outwh.TWmsShipmentHeaderDTO;
import com.huamengtong.wms.em.ActionCode;
import com.huamengtong.wms.em.LogType;
import com.huamengtong.wms.em.OperationStatusCode;
import com.huamengtong.wms.em.OrderTypeCode;
import com.huamengtong.wms.em.TicketStatusCode;
import com.huamengtong.wms.entity.inventory.TWmsInventoryEntity;
import com.huamengtong.wms.entity.outwh.TWmsAllocateEntity;
import com.huamengtong.wms.entity.outwh.TWmsShipmentDetailEntity;
import com.huamengtong.wms.entity.outwh.TWmsShipmentHeaderEntity;
import com.huamengtong.wms.inventory.service.IInventoryService;
import com.huamengtong.wms.inventory.service.IProInventoryService;
import com.huamengtong.wms.outwh.service.IAllocateService;
import com.huamengtong.wms.outwh.service.IShipmentDetailService;
import com.huamengtong.wms.outwh.service.IShipmentHeaderService;
import com.huamengtong.wms.outwh.service.IShipmentService;
import com.huamengtong.wms.utils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ShipmentService extends BaseService implements IShipmentService {


    @Autowired
    IShipmentHeaderService shipmentHeaderService;

    @Autowired
    IShipmentDetailService shipmentDetailService;

    @Autowired
    IInventoryService inventoryService;

    @Autowired
    IProInventoryService proInventoryService;

    @Autowired
    IAllocateService allocateService;



    /****
     * 库存分配
     * @param shipmentId
     * @param dbShardVO
     * @return
     */
    @Override
    @NeedLog( type = LogType.OPREATION)
    public MessageResult updateShipmentMqAllocate(Long shipmentId, DbShardVO dbShardVO){
        MessageResult mr = MessageResult.getFailMessage();
        TWmsShipmentHeaderEntity shipmentHeaderEntity = shipmentHeaderService.findByPrimaryKey(shipmentId,dbShardVO);
        if(shipmentHeaderEntity == null){
            mr.setMessage("出库单不存在!");
            return mr;
        }
        if (!shipmentHeaderEntity.getStatusCode().equals(TicketStatusCode.SUBMIT.toString())) {
            mr.setMessage("出库单不是提交状态不能分配!");
            return mr;
        }
        if (shipmentHeaderEntity.getAllocateStatuscode().equals(OperationStatusCode.Finished.toString())) {
            mr.setMessage("出库单已分配,不能再次库存分配!");
            return mr;
        }
        if (shipmentHeaderEntity.getIsCancelled().equals(new Byte("1"))) {
            mr.setMessage("出库单已取消,不能执行库存分配!");
            return mr;
        }
        List<TWmsShipmentDetailEntity> shipmentDetailEntityList = shipmentDetailService.getShipmentDetails(shipmentId,dbShardVO);
        if(CollectionUtils.isEmpty(shipmentDetailEntityList)){
            mr.setMessage("出库单没有明细信息,不能执行库存分配!");
            return mr;
        }
        for (TWmsShipmentDetailEntity shipmentDetailEntity : shipmentDetailEntityList){
            if(shipmentDetailEntity.getAllocatedQty() != null && shipmentDetailEntity.getAllocatedQty() > 0){
                mr.setMessage("出库单已分配过,不需要重复分配!");
                return mr;
            }
        }
        //调用库存分配方法

         //mr = inventoryService.updateAllocateInventoryByShipmentDetail(shipmentDetailEntityList,shipmentId,shipmentHeaderEntity,changeInwhDbShareVO(dbShardVO));
        //库存策略分配方法
        mr = inventoryService.updateAllocateInventory(shipmentDetailEntityList,shipmentId,shipmentHeaderEntity,changeInwhDbShareVO(dbShardVO));
        if (mr.isSuc()){
            //如果分配成功，回写出库单状态,数量、库房id
            Map resultMap = (Map) mr.getResult();
            //保存库存分配记录
            List<TWmsAllocateEntity> allocateEntities = (List)resultMap.get("allocates");
            allocateService.saveBatchAllocates(allocateEntities,dbShardVO);
            shipmentDetailEntityList = (List<TWmsShipmentDetailEntity>)MapUtils.getObject(resultMap, "shipmentDetailEntityList");
            mr = shipmentDetailService.updateShipmentDetailBatch(shipmentDetailEntityList,dbShardVO);
            if(!mr.isSuc()){ return mr;}
            mr = shipmentHeaderService.updateShipmentHeaderAllocateStatus(shipmentId,OperationStatusCode.Finished.toString(),dbShardVO);
        }else {
            shipmentHeaderService.updateShipmentHeaderAllocateStatus(shipmentId, OperationStatusCode.Failed.toString(), dbShardVO);
            this.writeOperationLog(shipmentId,shipmentHeaderEntity.getCargoOwnerId(), ActionCode.Allocate.toString(),
                    mr.getMessage(), OrderTypeCode.Shipment.toString(), OperationStatusCode.Failed.toString(),dbShardVO);
        }
        if(mr.isSuc()){
            mr.setMessage("库存分配成功");
            this.writeOperationLog(shipmentId,shipmentHeaderEntity.getCargoOwnerId(), ActionCode.Allocate.toString(),
                    mr.getMessage(), OrderTypeCode.Shipment.toString(), OperationStatusCode.Finished.toString(),dbShardVO);
        }
        return mr;
    }

    /**
     * 撤销库存分配
     * @param shipmentHeaderEntity 出库单头对象
     * @param operationUser 操作人
     * @param dbShardVO
     * @return
     */
    @Override
    public MessageResult updateShipmentRepealed(TWmsShipmentHeaderEntity shipmentHeaderEntity, String operationUser, DbShardVO dbShardVO){
        if(shipmentHeaderEntity.getStatusCode().equals(TicketStatusCode.SUBMIT)){
            return MessageResult.getFailMessage();
        }
        List<TWmsShipmentDetailEntity> shDetailList = shipmentDetailService.getShipmentDetails(shipmentHeaderEntity.getId(),dbShardVO);
        shDetailList.forEach(shipmentDetailEntity->{
            //根据分配表里的已分配数据去库存表里边清除已分配数据
            List<TWmsAllocateEntity> allocateEntitiesList = allocateService.searchAllocatesByShipmentDetailId(shipmentDetailEntity.getId(),dbShardVO);
            allocateEntitiesList.stream().forEach(allocateEntity ->{
                TWmsInventoryDTO inventoryDTO = new TWmsInventoryDTO();
                inventoryDTO.setId(allocateEntity.getInventoryId());
                TWmsInventoryEntity inventoryEntity =
                        inventoryService.findById(allocateEntity.getInventoryId(),changeInwhDbShareVO(dbShardVO));
                inventoryDTO.setAllocatedQty(inventoryEntity.getAllocatedQty()-allocateEntity.getAllocatedQty());
                inventoryDTO.setUpdateTime(new Date().getTime());
                inventoryDTO.setUpdateUser(operationUser);
                inventoryService.modifyInventory(inventoryDTO,changeInwhDbShareVO(dbShardVO));
            } );
            //删除分配表数据
            allocateService.deleteAllocate(shipmentDetailEntity.getId(),dbShardVO);
            //出库明细已分配数清0
            TWmsShipmentDetailDTO shipmentDetailDTO = new TWmsShipmentDetailDTO();
            shipmentDetailDTO.setId(shipmentDetailEntity.getId());
            shipmentDetailDTO.setAllocatedQty(0);
            shipmentDetailDTO.setUpdateTime(new Date().getTime());
            shipmentDetailDTO.setUpdateUser(operationUser);
            shipmentDetailService.modifyShipmentDetail(shipmentDetailDTO,dbShardVO);
        });
        shipmentHeaderEntity.setAllocateStatuscode(OperationStatusCode.None.toString());
        shipmentHeaderEntity.setStatusCode(TicketStatusCode.INIT.toString());
        shipmentHeaderEntity.setUpdateUser(operationUser);
        shipmentHeaderEntity.setUpdateTime(new Date().getTime());
        TWmsShipmentHeaderDTO shipmentHeaderDTO = BeanUtils.copyBeanPropertyUtils(shipmentHeaderEntity,TWmsShipmentHeaderDTO.class);
        shipmentHeaderService.modifyShipmentHeader(shipmentHeaderDTO,changeOutwhDbShareVO(dbShardVO));
        return  MessageResult.getSucMessage();
    }

    /**
     * 出仓交接
     * @param shipmentId  出库单头ID
     * @param dbShardVO
     * @return
     */
    @Override
    @NeedLog(type = {LogType.OPREATION,LogType.INVENTORY})
    public MessageResult updateInventoryFromDelivery(Long shipmentId, DbShardVO dbShardVO) {
        Long dateTime = new Date().getTime();
        String userName = dbShardVO.getCurrentUser().getUserName();
        MessageResult mr = MessageResult.getFailMessage();
        TWmsShipmentHeaderEntity shipmentHeaderEntity = shipmentHeaderService.findByPrimaryKey(shipmentId,dbShardVO);
        List<TWmsShipmentDetailEntity> shipmentdetailList = shipmentDetailService.getShipmentDetails(shipmentId, dbShardVO);
        List<TWmsInventoryDTO> inventoryDTOs = new ArrayList<>();
        shipmentdetailList.stream().forEach(x->{
            List<TWmsAllocateEntity> allocationList = allocateService.searchAllocatesByShipmentDetailId(x.getId(),dbShardVO);
            allocationList.stream().forEach(y->{
                TWmsInventoryEntity inventoryEntity = inventoryService.findById(y.getInventoryId(),changeInwhDbShareVO(dbShardVO));
                inventoryEntity.setOnhandQty(inventoryEntity.getOnhandQty() - y.getAllocatedQty());//扣减可用数量
                inventoryEntity.setAllocatedQty(inventoryEntity.getAllocatedQty() - y.getAllocatedQty());//分配数量重置为0
                inventoryEntity.setUpdateTime(dateTime);
                inventoryEntity.setUpdateUser(userName);
                TWmsInventoryDTO inventoryDTO = BeanUtils.copyBeanPropertyUtils(inventoryEntity,TWmsInventoryDTO.class);
                inventoryDTOs.add(inventoryDTO);

                //记录库存日志
                this.writeInventoryLog(y.getInventoryId(),inventoryEntity.getSkuId(),OrderTypeCode.Shipment.toString(),
                        shipmentId,x.getId(),-y.getAllocatedQty(),changeInwhDbShareVO(dbShardVO));
                y.setShippedQty(y.getAllocatedQty());
                y.setUpdateTime(dateTime);
                y.setUpdateUser(userName);
                allocateService.updateAllocate(y,dbShardVO);
            });
            x.setShippedQty(x.getAllocatedQty());
            x.setUpdateTime(dateTime);
            x.setUpdateUser(userName);
            TWmsShipmentDetailDTO shipmentDetailDTO = BeanUtils.copyBeanPropertyUtils(x,TWmsShipmentDetailDTO.class);
            shipmentDetailService.modifyShipmentDetail(shipmentDetailDTO,dbShardVO);
        });
        //批量更新inventory
        mr = inventoryService.updateInventorys(inventoryDTOs,changeInwhDbShareVO(dbShardVO));
        if(mr.isSuc()){
            //记录出库成功操作日志
            this.writeOperationLog(shipmentId,shipmentHeaderEntity.getCargoOwnerId(), ActionCode.Deliver.toString(),"出库完成",
                    OrderTypeCode.Shipment.toString(), OperationStatusCode.Finished.toString(),dbShardVO);
        }
        return mr;
    }
}
