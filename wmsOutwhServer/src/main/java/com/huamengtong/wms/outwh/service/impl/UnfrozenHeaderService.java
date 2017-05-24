package com.huamengtong.wms.outwh.service.impl;

import com.huamengtong.wms.client.IAutoIdClient;
import com.huamengtong.wms.constants.AutoIdConstants;
import com.huamengtong.wms.core.annotation.NeedLog;
import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.service.BaseService;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.outwh.TWmsFrozenHeaderDTO;
import com.huamengtong.wms.dto.outwh.TWmsUnfrozenHeaderDTO;
import com.huamengtong.wms.em.ActionCode;
import com.huamengtong.wms.em.LogType;
import com.huamengtong.wms.em.OperationStatusCode;
import com.huamengtong.wms.em.OrderTypeCode;
import com.huamengtong.wms.em.TicketStatusCode;
import com.huamengtong.wms.em.UnfrozenTypeCode;
import com.huamengtong.wms.entity.inventory.TWmsInventoryEntity;
import com.huamengtong.wms.entity.outwh.TWmsUnfrozenDetailEntity;
import com.huamengtong.wms.entity.outwh.TWmsUnfrozenHeaderEntity;
import com.huamengtong.wms.inventory.service.IInventoryService;
import com.huamengtong.wms.outwh.mapper.UnfrozenHeaderMapper;
import com.huamengtong.wms.outwh.service.IUnfrozenDetailService;
import com.huamengtong.wms.outwh.service.IUnfrozenHeaderService;
import com.huamengtong.wms.utils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class UnfrozenHeaderService extends BaseService implements IUnfrozenHeaderService {
    @Autowired
    UnfrozenHeaderMapper unfrozenHeaderMapper;

    @Autowired
    IAutoIdClient autoIdClient;

    @Autowired
    IUnfrozenDetailService unfrozenDetailService;

    @Autowired
    IInventoryService inventoryService;


    private static final int MILLSECONDSPERDAY = 86400000;



    @Override
    public PageResponse<List<TWmsUnfrozenHeaderEntity>> getUnfrozenHeader(TWmsUnfrozenHeaderDTO unfrozenHeaderDTO, DbShardVO dbShardVO) {
        TWmsUnfrozenHeaderEntity unfrozenHeaderEntity = BeanUtils.copyBeanPropertyUtils(unfrozenHeaderDTO,TWmsUnfrozenHeaderEntity.class);
        List<TWmsUnfrozenHeaderEntity>  unfrozenHeaderEntityList= unfrozenHeaderMapper.queryUnfrozenHeaderPages(unfrozenHeaderEntity,getSplitTableKey(dbShardVO));
        Integer totalSize = unfrozenHeaderMapper.queryUnfrozenHeaderPageCount(unfrozenHeaderEntity,getSplitTableKey(dbShardVO));
        PageResponse<List<TWmsUnfrozenHeaderEntity>> response = new PageResponse();
        response.setRows(unfrozenHeaderEntityList);
        response.setTotal(totalSize);
        return response;
    }

    @Override
    public TWmsUnfrozenHeaderEntity findByPrimaryKey(Long id, DbShardVO dbShardVO) {
        return unfrozenHeaderMapper.selectByPrimaryKey(id,getSplitTableKey(dbShardVO));
    }

    @Override
    public MessageResult createUnfrozenHeader(TWmsUnfrozenHeaderDTO unfrozenHeaderDTO, DbShardVO dbShardVO) {
        unfrozenHeaderDTO.setStatusCode(TicketStatusCode.INIT.toString());
        TWmsUnfrozenHeaderEntity unfrozenHeaderEntity = BeanUtils.copyBeanPropertyUtils(unfrozenHeaderDTO,TWmsUnfrozenHeaderEntity.class);
        if (unfrozenHeaderEntity.getCargoOwnerId() == null || unfrozenHeaderEntity.getCargoOwnerId() == 0){
            return MessageResult.getMessage("E40001");
        }
        unfrozenHeaderEntity.setId(autoIdClient.getAutoId(AutoIdConstants.TWmsUnfrozenHeaderEntity));
        unfrozenHeaderMapper.insertUnfrozenHeader(unfrozenHeaderEntity,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult removeUnfrozenHeader(Long id, DbShardVO dbShardVO) {
        unfrozenHeaderMapper.deleteUnfrozenHeader(id,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult modifyUnfrozenHeader(TWmsUnfrozenHeaderDTO unfrozenHeaderDTO, DbShardVO dbShardVO) {
        TWmsUnfrozenHeaderEntity unfrozenHeaderEntity=BeanUtils.copyBeanPropertyUtils(unfrozenHeaderDTO,TWmsUnfrozenHeaderEntity.class);
        unfrozenHeaderMapper.updateUnfrozenHeader(unfrozenHeaderEntity,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }


    private boolean checkUnFrozenDetail(Long unFrozenId,DbShardVO dbShardVO){
        List<TWmsUnfrozenDetailEntity> unfrozenDetailEntities = unfrozenDetailService.getUnfrozenDetailsByHeaderId(unFrozenId,dbShardVO);
        return CollectionUtils.isNotEmpty(unfrozenDetailEntities) ? false : true;
    }

    /**
     * 从质押单创建解质押主表单
     * @param unFrozenHeaderId 解质押主表单id
     * @param frozenHeaderDTO 质押主表单据
     * @param operationUser  操作人
     * @param dbShardVO   分库标识
     * @return
     */
    @Override
    @NeedLog(type = LogType.OPREATION)
    public MessageResult createUnFrozenFromFrozen(Long unFrozenHeaderId, TWmsFrozenHeaderDTO frozenHeaderDTO, String operationUser, DbShardVO dbShardVO) {
        TWmsUnfrozenHeaderEntity unfrozenHeaderEntity = new TWmsUnfrozenHeaderEntity();
        unfrozenHeaderEntity.setId(unFrozenHeaderId);// id
        unfrozenHeaderEntity.setWarehouseId(frozenHeaderDTO.getWarehouseId());
        unfrozenHeaderEntity.setTenantId(frozenHeaderDTO.getTenantId());
        unfrozenHeaderEntity.setFrozenId(frozenHeaderDTO.getId());
        unfrozenHeaderEntity.setCargoOwnerId(frozenHeaderDTO.getCargoOwnerId());
        unfrozenHeaderEntity.setFactoringTime(frozenHeaderDTO.getFactoringEndTime());//预计解押时间
        if(StringUtils.isNotEmpty(frozenHeaderDTO.getName())){
            unfrozenHeaderEntity.setName(frozenHeaderDTO.getName());
        }
        unfrozenHeaderEntity.setFactoringOrganizeId(frozenHeaderDTO.getFactoringOrganizeId());//提交机构
        unfrozenHeaderEntity.setTotalQty(frozenHeaderDTO.getTotalQty());
        unfrozenHeaderEntity.setTotalCartonQty(frozenHeaderDTO.getTotalCartonQty());
        unfrozenHeaderEntity.setTotalAmount(frozenHeaderDTO.getTotalAmount());
        unfrozenHeaderEntity.setTotalGrossWeight(frozenHeaderDTO.getTotalGrossWeight());
        unfrozenHeaderEntity.setTotalNetWeight(frozenHeaderDTO.getTotalNetWeight());
        unfrozenHeaderEntity.setStatusCode(TicketStatusCode.INIT.toString());
        Long currentTime = new Date().getTime();
        unfrozenHeaderEntity.setCreateUser(operationUser);
        unfrozenHeaderEntity.setCreateTime(currentTime);
        unfrozenHeaderEntity.setUpdateUser(operationUser);
        unfrozenHeaderEntity.setUpdateTime(currentTime);
        unfrozenHeaderMapper.insertUnfrozenHeader(unfrozenHeaderEntity,getSplitTableKey(dbShardVO));
        this.writeOperationLog(unfrozenHeaderEntity.getId(),unfrozenHeaderEntity.getCargoOwnerId(),ActionCode.Create.toString(),operationUser+"创建解押单",OrderTypeCode.UNFROZEN.toString(),OperationStatusCode.Finished.toString(),dbShardVO);
        return MessageResult.getSucMessage();
    }

    @Override
    public TWmsUnfrozenHeaderEntity findByFrozenId(Long frozenId, DbShardVO dbShardVO) {
        return unfrozenHeaderMapper.selectByFrozenId(frozenId,getSplitTableKey(dbShardVO));
    }


    /**
     * 作废未提交的质检单，剩余未解押货物不可再提交解押
     * 不修改数据，只更改状态,同时释放当前质押单已质押数量
     * @param unfrozenId  解押单主表id
     * @param invalidReason
     * @param operationUser
     * @param dbShardVO
     * @return
     */
    @Override
    @NeedLog(type = LogType.OPREATION)
    public MessageResult executeInvalidUnfrozen(Long unfrozenId, String invalidReason, String operationUser, DbShardVO dbShardVO) {
        if(unfrozenId==null ||invalidReason==null || invalidReason.trim() == null || invalidReason.trim().isEmpty() ){
            return MessageResult.getMessage("E71001");
        }
        if(checkUnFrozenDetail(unfrozenId,dbShardVO)){
            return MessageResult.getMessage("E71002",new Object[]{unfrozenId});
        }
        TWmsUnfrozenHeaderEntity unfrozenHeaderEntity = unfrozenHeaderMapper.selectByPrimaryKey(unfrozenId,getSplitTableKey(dbShardVO));
        if(unfrozenHeaderEntity==null){
            return MessageResult.getMessage("E71003",new Object[]{unfrozenId});
        }
        if(!unfrozenHeaderEntity.getStatusCode().equals(TicketStatusCode.INIT.toString())){
            return MessageResult.getMessage("E71004",new Object[]{unfrozenId});
        }
        List<TWmsUnfrozenDetailEntity> unfrozenDetailEntities = unfrozenDetailService.getUnfrozenDetailsByHeaderId(unfrozenId,dbShardVO);
        Long currentTime = new Date().getTime();
        List<TWmsInventoryEntity> inventoryEntityList = new ArrayList<>();
        for (TWmsUnfrozenDetailEntity unfrozenDetailEntity:unfrozenDetailEntities){
            unfrozenDetailEntity.setDescription(invalidReason);
            TWmsInventoryEntity inventoryEntity = inventoryService.findInventoryFromCargoOwner(unfrozenDetailEntity.getSkuId(),unfrozenHeaderEntity.getCargoOwnerId(),unfrozenDetailEntity.getStorageRoomId(),changeInwhDbShareVO(dbShardVO));
            if(inventoryEntity.getIsHold() == 1){
                return MessageResult.getMessage("E71017");
            }
            inventoryEntity.setMortgagedQty(inventoryEntity.getMortgagedQty()-unfrozenDetailEntity.getFrozenedQty()+unfrozenDetailEntity.getUnfrozenedQty());
            inventoryEntity.setUpdateUser(operationUser);
            inventoryEntity.setUpdateTime(currentTime);
            inventoryEntityList.add(inventoryEntity);
        }
        //更新库存中数据
        MessageResult messageResult= inventoryService.updateInventoryFromUnfrozen(inventoryEntityList,changeInwhDbShareVO(dbShardVO));
        if(!messageResult.isSuc()){
            return MessageResult.getMessage("E71018");
        }
        unfrozenDetailEntities.stream().forEach(x->{
            unfrozenDetailService.updateDetailFromInvalidUnfrozen(x,operationUser,dbShardVO);
        });
        unfrozenHeaderEntity.setStatusCode(TicketStatusCode.INVALID.toString());
        unfrozenHeaderEntity.setIsInvalided(new Byte("1"));
        unfrozenHeaderEntity.setDescription(invalidReason);
        unfrozenHeaderEntity.setUpdateUser(operationUser);
        unfrozenHeaderEntity.setUpdateTime(currentTime);
        unfrozenHeaderMapper.updateUnfrozenHeader(unfrozenHeaderEntity,getSplitTableKey(dbShardVO));
        this.writeOperationLog(unfrozenId,unfrozenHeaderEntity.getCargoOwnerId(), ActionCode.INVALID.toString(),operationUser+"作废解押单", OrderTypeCode.UNFROZEN.toString(), OperationStatusCode.Finished.toString(),dbShardVO);
        return MessageResult.getSucMessage();
    }

    /**
     * 提交单个未提交的解押单
     * 连带明细全部提交
     * @param unfrozenId  解押单ID
     * @param operationUser  操作人
     * @param dbShardVO   分库标识
     * @return
     */
    @Override
    @NeedLog(type = LogType.OPREATION)
    public MessageResult executeSubmitUnfrozen(Long unfrozenId, String operationUser, DbShardVO dbShardVO) {
        if(unfrozenId==null){
            return MessageResult.getMessage("E71005");
        }
        TWmsUnfrozenHeaderEntity unfrozenHeaderEntity = unfrozenHeaderMapper.selectByPrimaryKey(unfrozenId,getSplitTableKey(dbShardVO));
        if(unfrozenHeaderEntity==null){
            return MessageResult.getMessage("E71006",new Object[]{unfrozenId});
        }
        if(checkUnFrozenDetail(unfrozenId,dbShardVO)){
            return MessageResult.getMessage("E71007",new Object[]{unfrozenId});
        }
        if(unfrozenHeaderEntity.getStatusCode()==null|| !unfrozenHeaderEntity.getStatusCode().equals(TicketStatusCode.INIT.toString())){
            return MessageResult.getMessage("E71010",new Object[]{unfrozenId});
        }
        List<TWmsUnfrozenDetailEntity> unfrozenDetailEntities = unfrozenDetailService.getUnfrozenDetailsByHeaderId(unfrozenId,dbShardVO);
        for (TWmsUnfrozenDetailEntity unfrozenDetailEntity:unfrozenDetailEntities){
            unfrozenDetailService.updateDetailFromSubmitUnfrozen(unfrozenDetailEntity,operationUser,dbShardVO);
        }
        unfrozenHeaderEntity.setStatusCode(TicketStatusCode.SUBMIT.toString());
        unfrozenHeaderEntity.setUpdateUser(operationUser);
        unfrozenHeaderEntity.setUpdateTime(new Date().getTime());
        unfrozenHeaderMapper.updateUnfrozenHeader(unfrozenHeaderEntity,getSplitTableKey(dbShardVO));
        this.writeOperationLog(unfrozenId,unfrozenHeaderEntity.getCargoOwnerId(), ActionCode.Submit.toString(),operationUser+"提交解押单", OrderTypeCode.UNFROZEN.toString(), OperationStatusCode.Finished.toString(),dbShardVO);
        return MessageResult.getSucMessage();
    }

    /**
     * 支持单个对已提交的解押单据进行撤销
     * 连带撤销解押明细中的已提交的解押数量
     * @param unfrozenId  解押单ID
     * @param operationUser  操作人
     * @param dbShardVO   分库标识
     * @return
     */
    @Override
    @NeedLog(type = LogType.OPREATION)
    public MessageResult executeRepealedUnfrozen(Long unfrozenId, String operationUser, DbShardVO dbShardVO) {
        if(unfrozenId==null){
            return MessageResult.getMessage("E71005");
        }
        TWmsUnfrozenHeaderEntity unfrozenHeaderEntity = unfrozenHeaderMapper.selectByPrimaryKey(unfrozenId,getSplitTableKey(dbShardVO));
        if(unfrozenHeaderEntity==null){
            return MessageResult.getMessage("E71006",new Object[]{unfrozenId});
        }
        if(unfrozenHeaderEntity.getStatusCode()==null || !unfrozenHeaderEntity.getStatusCode().equals(TicketStatusCode.SUBMIT.toString())){
            return MessageResult.getMessage("E71009",new Object[]{unfrozenId});
        }
        //不再判断质押明细单是否存在,提交时已经判断过
        List<TWmsUnfrozenDetailEntity> unfrozenDetailEntities = unfrozenDetailService.getUnfrozenDetailsByHeaderId(unfrozenId,dbShardVO);
        for (TWmsUnfrozenDetailEntity unfrozenDetailEntity:unfrozenDetailEntities){
            //处理明细解押数量
            unfrozenDetailService.updateDetailFromRepealedUnfrozen(unfrozenDetailEntity,operationUser,dbShardVO);
        }
        unfrozenHeaderEntity.setStatusCode(TicketStatusCode.INIT.toString());
        unfrozenHeaderEntity.setUpdateUser(operationUser);
        unfrozenHeaderEntity.setUpdateTime(new Date().getTime());
        unfrozenHeaderMapper.updateUnfrozenHeader(unfrozenHeaderEntity,getSplitTableKey(dbShardVO));
        this.writeOperationLog(unfrozenId, unfrozenHeaderEntity.getCargoOwnerId(),ActionCode.Repealed.toString(),operationUser+"撤销解押单", OrderTypeCode.UNFROZEN.toString(), OperationStatusCode.Finished.toString(),dbShardVO);
        return MessageResult.getSucMessage();
    }

    @Override
    @NeedLog(type = LogType.OPREATION)
    public MessageResult executeRejectUnfrozen(Long unfrozenId, String rejectReason, String operationUser, DbShardVO dbShardVO) {
        if (unfrozenId==null){
            return MessageResult.getMessage("E71005");
        }
        if(rejectReason==null || StringUtils.isEmpty(rejectReason.trim())){
            return MessageResult.getMessage("E71008");
        }
        TWmsUnfrozenHeaderEntity unfrozenHeaderEntity = unfrozenHeaderMapper.selectByPrimaryKey(unfrozenId,getSplitTableKey(dbShardVO));
        if(unfrozenHeaderEntity == null){
            return MessageResult.getMessage("E71006",new Object[]{unfrozenId});
        }
        List<TWmsUnfrozenDetailEntity> unfrozenDetailEntities = unfrozenDetailService.getUnfrozenDetailsByHeaderId(unfrozenId,dbShardVO);
        for (TWmsUnfrozenDetailEntity unfrozenDetailEntity:unfrozenDetailEntities){
            //走撤销流程，重置明细单据
            unfrozenDetailService.updateDetailFromRepealedUnfrozen(unfrozenDetailEntity,operationUser,dbShardVO);
        }
        unfrozenHeaderEntity.setStatusCode(TicketStatusCode.INIT.toString());//单据状态重置
        unfrozenHeaderEntity.setDescription(rejectReason);//拒绝原因写入
        unfrozenHeaderEntity.setUpdateUser(operationUser);
        unfrozenHeaderEntity.setUpdateTime(new Date().getTime());
        unfrozenHeaderMapper.updateUnfrozenHeader(unfrozenHeaderEntity,getSplitTableKey(dbShardVO));
        this.writeOperationLog(unfrozenId, unfrozenHeaderEntity.getCargoOwnerId(),ActionCode.Reject.toString(),operationUser+"审核不通过解押单", OrderTypeCode.UNFROZEN.toString(),OperationStatusCode.Finished.toString(),dbShardVO);
        return MessageResult.getSucMessage();
    }


    /**
     * 审核通过质押单
     * @param unfrozenId
     * @param operationUser
     * @param dbShardVO
     * @return
     */
    @Override
    @NeedLog(type = LogType.OPREATION)
    public MessageResult executeAuditUnfrozen(Long unfrozenId, String operationUser, DbShardVO dbShardVO) {
        if(unfrozenId==null){
            return MessageResult.getMessage("E71005");
        }
        TWmsUnfrozenHeaderEntity unfrozenHeaderEntity = unfrozenHeaderMapper.selectByPrimaryKey(unfrozenId,getSplitTableKey(dbShardVO));
        if(unfrozenHeaderEntity==null){
            return MessageResult.getMessage("E71006");
        }
        List<TWmsUnfrozenDetailEntity> unfrozenDetailEntities = unfrozenDetailService.getUnfrozenDetailsByHeaderId(unfrozenId,dbShardVO);
        Integer unfrozenQty=unfrozenHeaderEntity.getFactoringTotalQty();
        List<TWmsInventoryEntity> inventoryEntityList = new ArrayList<>();
        for (TWmsUnfrozenDetailEntity unfrozenDetailEntity:unfrozenDetailEntities){
            unfrozenQty += unfrozenDetailEntity.getFactoringQty();//累加本次解押总数量
            // 取出库存信息,校验
            TWmsInventoryEntity inventoryEntity = inventoryService.findInventoryFromCargoOwner(unfrozenDetailEntity.getSkuId(),unfrozenHeaderEntity.getCargoOwnerId(),unfrozenDetailEntity.getStorageRoomId(),changeInwhDbShareVO(dbShardVO));
            //如果库房内质押库存小于解押数量,则return
            if (inventoryEntity.getMortgagedQty() < unfrozenDetailEntity.getFactoringQty()) {
                return MessageResult.getMessage("E91002");
            }
            if(inventoryEntity.getIsHold() == 1){
                return MessageResult.getMessage("E91003");
            }
            //inventory不可能为空，直接更新已质押数量即可
            inventoryEntity.setMortgagedQty(inventoryEntity.getMortgagedQty() - unfrozenDetailEntity.getFactoringQty());
            inventoryEntity.setUpdateUser(operationUser);
            inventoryEntity.setUpdateTime(new Date().getTime());
            inventoryEntityList.add(inventoryEntity);
        }
        //更新库存信息
        MessageResult ms = inventoryService.updateInventoryFromUnfrozen(inventoryEntityList,changeInwhDbShareVO(dbShardVO));
        if(!ms.isSuc()){
            ms = MessageResult.getFailMessage();
            return ms;
        }
        unfrozenDetailService.updateDetailFromAuditUnfrozen(unfrozenDetailEntities,operationUser,dbShardVO);
        //更新解押主表数据
        updateUnfrozenFromAudit(unfrozenQty,unfrozenHeaderEntity,operationUser);
        unfrozenHeaderMapper.updateUnfrozenHeader(unfrozenHeaderEntity,getSplitTableKey(dbShardVO));
        this.writeOperationLog(unfrozenId, unfrozenHeaderEntity.getCargoOwnerId(),ActionCode.AUDITED.toString(),operationUser+"审核通过解押单||已解押总数量"+unfrozenQty.toString(), OrderTypeCode.UNFROZEN.toString(), OperationStatusCode.Finished.toString(),dbShardVO);
        return MessageResult.getSucMessage();
    }


    private void updateUnfrozenFromAudit(Integer unfrozenQty,TWmsUnfrozenHeaderEntity unfrozenHeaderEntity,String operationUser){
        if (unfrozenHeaderEntity.getTotalQty().equals(unfrozenQty)){
            unfrozenHeaderEntity.setStatusCode(TicketStatusCode.REVIEWED.toString());
        }else{
            unfrozenHeaderEntity.setStatusCode(TicketStatusCode.INIT.toString());
        }
        //比较两个日期是否在同一天，如果是同一天则为正常解押，在预期解押时间之前为紧急解押，之后为延迟解押
        //除以1000*60*60*24=86400000一天的毫秒数
        Long currentTime = new Date().getTime()/MILLSECONDSPERDAY;
        Long factoringTime = unfrozenHeaderEntity.getFactoringTime()/MILLSECONDSPERDAY;//预期解押日期
        int result = currentTime.compareTo(factoringTime);//当前日期
        if(result==0){
            unfrozenHeaderEntity.setFactoringType(UnfrozenTypeCode.NORMALUNFROZEN.toString());
        }else if(result==-1){
            unfrozenHeaderEntity.setFactoringType(UnfrozenTypeCode.URGENTUNFROZEN.toString());
        }else {
            unfrozenHeaderEntity.setFactoringType(UnfrozenTypeCode.DELAYUNFROZEN.toString());
        }
        unfrozenHeaderEntity.setFactoringTotalQty(unfrozenQty);
        unfrozenHeaderEntity.setFactoringActualTime(new Date().getTime());
        unfrozenHeaderEntity.setAuditedOrganizeId(13L);//审核机构写入固定值
        unfrozenHeaderEntity.setIsAudited(new Byte("1"));
        unfrozenHeaderEntity.setAuditedUser(operationUser);
        unfrozenHeaderEntity.setAuditedTime(new Date().getTime());
        unfrozenHeaderEntity.setUpdateUser(operationUser);
        unfrozenHeaderEntity.setUpdateTime(new Date().getTime());
    }


    @Override
    public MessageResult updateStatusFromSubmitDetail(TWmsUnfrozenHeaderDTO unfrozenHeaderDTO, String operationUser, DbShardVO dbShardVO) {
        unfrozenHeaderDTO.setStatusCode(TicketStatusCode.SUBMIT.toString());
        unfrozenHeaderDTO.setUpdateUser(operationUser);
        unfrozenHeaderDTO.setUpdateTime(new Date().getTime());
        TWmsUnfrozenHeaderEntity unfrozenHeaderEntity = BeanUtils.copyBeanPropertyUtils(unfrozenHeaderDTO,TWmsUnfrozenHeaderEntity.class);
        unfrozenHeaderMapper.updateUnfrozenHeader(unfrozenHeaderEntity,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }


}
























