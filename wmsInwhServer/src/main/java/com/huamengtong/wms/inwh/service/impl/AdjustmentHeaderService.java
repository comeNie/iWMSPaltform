package com.huamengtong.wms.inwh.service.impl;

import com.huamengtong.wms.client.IAutoIdClient;
import com.huamengtong.wms.constants.AutoIdConstants;
import com.huamengtong.wms.core.annotation.NeedLog;
import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.service.BaseService;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.inwh.TWmsAdjustmentHeaderDTO;
import com.huamengtong.wms.em.ActionCode;
import com.huamengtong.wms.em.LogType;
import com.huamengtong.wms.em.OperationStatusCode;
import com.huamengtong.wms.em.OrderTypeCode;
import com.huamengtong.wms.em.TicketStatusCode;
import com.huamengtong.wms.em.AdjustTypeCode;
import com.huamengtong.wms.entity.inwh.TWmsAdjustmentDetailEntity;
import com.huamengtong.wms.entity.inwh.TWmsAdjustmentHeaderEntity;
import com.huamengtong.wms.inventory.service.IInventoryService;
import com.huamengtong.wms.inwh.mapper.AdjustmentDetailMapper;
import com.huamengtong.wms.inwh.mapper.AdjustmentHeaderMapper;
import com.huamengtong.wms.inwh.service.IAdjustmentHeaderService;
import com.huamengtong.wms.sku.service.ISkuService;
import com.huamengtong.wms.utils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by mario on 2016/11/16.
 */
@Service
public class AdjustmentHeaderService extends BaseService implements IAdjustmentHeaderService{
    @Autowired
    AdjustmentHeaderMapper adjustmentHeaderMapper;

    @Autowired
    AdjustmentDetailMapper adjustmentDetailMapper;

    @Autowired
    IAutoIdClient autoIdClient;

    @Autowired
    AdjustmentDetailService adjustmentDetailService;

    @Autowired
    IInventoryService inventoryService;

    @Autowired
    ISkuService skuService;


    @Override
    public PageResponse<List<TWmsAdjustmentHeaderEntity>> getAdjustmentHeader(TWmsAdjustmentHeaderDTO adjustmentHeaderDTO, DbShardVO dbShardVO) {
        TWmsAdjustmentHeaderEntity adjustmentHeaderEntity = BeanUtils.copyBeanPropertyUtils(adjustmentHeaderDTO,TWmsAdjustmentHeaderEntity.class);
        List<TWmsAdjustmentHeaderEntity> adjustmentHeaderEntityList = adjustmentHeaderMapper.queryAdjustmentHeaderPages(adjustmentHeaderEntity,getSplitTableKey(dbShardVO));
        Integer totalSize = adjustmentHeaderMapper.queryAdjustmentHeaderPageCount(adjustmentHeaderEntity,getSplitTableKey(dbShardVO));
        PageResponse<List<TWmsAdjustmentHeaderEntity>> response = new PageResponse<>();
        response.setRows(adjustmentHeaderEntityList);
        response.setTotal(totalSize);
        return response;
    }

    @Override
    public TWmsAdjustmentHeaderEntity findByPrimaryKey(Long id, DbShardVO dbShardVO) {
        return adjustmentHeaderMapper.selectByPrimaryKey(id,getSplitTableKey(dbShardVO));
    }

    @Override
    @NeedLog(type = LogType.OPREATION)
    public MessageResult createAdjustmentHeader(TWmsAdjustmentHeaderDTO adjustmentHeaderDTO, DbShardVO dbShardVO) {
       if (adjustmentHeaderDTO.getcargoOwnerId()== null || adjustmentHeaderDTO.getcargoOwnerId() == 0){
            return MessageResult.getMessage("E40001");
        }
        if (adjustmentHeaderDTO.getId() == null){
            adjustmentHeaderDTO.setId(autoIdClient.getAutoId(AutoIdConstants.TWmsAdjustmentHeaderEntity));
        }
        if (StringUtils.isEmpty(adjustmentHeaderDTO.getResonCode())) {
            adjustmentHeaderDTO.setResonCode(AdjustTypeCode.MANUAL.toString());
        }
        adjustmentHeaderDTO.setStatusCode(TicketStatusCode.INIT.toString());
        TWmsAdjustmentHeaderEntity adjustmentHeaderEntity = BeanUtils.copyBeanPropertyUtils(adjustmentHeaderDTO,TWmsAdjustmentHeaderEntity.class);
        adjustmentHeaderMapper.insertAdjustmentHeader(adjustmentHeaderEntity,getSplitTableKey(dbShardVO));
        this.writeOperationLog(adjustmentHeaderEntity.getId(),adjustmentHeaderEntity.getCargoOwnerId(), ActionCode.Create.toString(),adjustmentHeaderEntity.getCreateUser()+"创建调整单", OrderTypeCode.Adjustment.toString(),OperationStatusCode.Finished.toString(),dbShardVO);
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult removeAdjustmentHeader(Long id, DbShardVO dbShardVO) {
        List<TWmsAdjustmentDetailEntity> adjustmentDetailEntities = adjustmentDetailService.getAdjustmentDetails(id,dbShardVO);
        if (CollectionUtils.isNotEmpty(adjustmentDetailEntities)) {
            for (TWmsAdjustmentDetailEntity adjustmentDetailEntity : adjustmentDetailEntities) {
                adjustmentDetailService.removeAdjustmentDetail(adjustmentDetailEntity.getId(), dbShardVO);
            }
        }
        adjustmentHeaderMapper.deleteAdjustmentHeader(id,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult modifyAdjustmentHeader(TWmsAdjustmentHeaderDTO adjustmentHeaderDTO, DbShardVO dbShardVO) {
        TWmsAdjustmentHeaderEntity adjustmentHeaderEntity=BeanUtils.copyBeanPropertyUtils(adjustmentHeaderDTO,TWmsAdjustmentHeaderEntity.class);
        adjustmentHeaderMapper.updateAdjustmentHeader(adjustmentHeaderEntity,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }


    @Override
    @NeedLog(type = {LogType.OPREATION})
    public MessageResult updateAdjustmentHeaderStatus(Long adjustId, String statusCode, String operationUser, DbShardVO dbShardVO) {
        if(adjustId ==null || adjustId==0){
            return MessageResult.getMessage("E80001");
        }
        TWmsAdjustmentHeaderEntity adjustmentHeaderEntity = this.findByPrimaryKey(adjustId,dbShardVO);
        if (adjustmentHeaderEntity == null){
            return  MessageResult.getMessage("E80003",new Object[]{adjustId});
        }
        if (checkAdjustmentDetail(adjustId,dbShardVO)) {
            return MessageResult.getMessage("E80002", new Object[]{adjustId});
        }
        MessageResult messageResult;
        //初始化状态才能执行提交
        if (statusCode.equals(TicketStatusCode.SUBMIT.toString()) && TicketStatusCode.INIT.toString().equals(adjustmentHeaderEntity.getStatusCode())){
            messageResult = adjustmentSubmit(adjustmentHeaderEntity,operationUser,dbShardVO);
            this.writeOperationLog(adjustId,adjustmentHeaderEntity.getCargoOwnerId(), ActionCode.Submit.toString(),operationUser+"提交调整单",OrderTypeCode.Adjustment.toString(),OperationStatusCode.Finished.toString(),dbShardVO);
        } else if (statusCode.equals(TicketStatusCode.REVIEWED.toString()) && TicketStatusCode.SUBMIT.toString().equals(adjustmentHeaderEntity.getStatusCode())) {
            messageResult = adjustmentReviewed(adjustmentHeaderEntity,operationUser,dbShardVO);
            this.writeOperationLog(adjustId,adjustmentHeaderEntity.getCargoOwnerId(), ActionCode.AUDITED.toString(),operationUser+"审核通过调整单",OrderTypeCode.Adjustment.toString(),OperationStatusCode.Finished.toString(),dbShardVO);
        } else {
            return MessageResult.getMessage("E80004",new Object[]{ adjustId });
        }
        return messageResult;
    }

    private boolean checkAdjustmentDetail(Long adjustId,DbShardVO dbShardVO){
        List<TWmsAdjustmentDetailEntity> adjustmentDetailEntities = adjustmentDetailService.getAdjustmentDetails(adjustId,dbShardVO);
        return CollectionUtils.isNotEmpty(adjustmentDetailEntities) ? false : true;
    }

    /**
     * 调整提交
     * @param adjustmentHeaderEntity
     * @param operationUser
     * @param dbShardVO
     * @return
     */
    private MessageResult adjustmentSubmit(TWmsAdjustmentHeaderEntity adjustmentHeaderEntity,String operationUser, DbShardVO dbShardVO){
        List<TWmsAdjustmentDetailEntity> adjustmentDetailEntities = adjustmentDetailService.getAdjustmentDetails(adjustmentHeaderEntity.getId(),dbShardVO);
        for (TWmsAdjustmentDetailEntity  adjustmentDetailEntity : adjustmentDetailEntities){
            adjustmentDetailEntity.setStatusCode(TicketStatusCode.SUBMIT.toString());
            adjustmentDetailEntity.setUpdateUser(operationUser);
            adjustmentDetailEntity.setUpdateTime(new Date().getTime());
            adjustmentDetailMapper.updateAdjustmentDetail(adjustmentDetailEntity,getSplitTableKey(dbShardVO));
        }
        adjustmentHeaderEntity.setStatusCode(TicketStatusCode.SUBMIT.toString());
        adjustmentHeaderEntity.setUpdateUser(operationUser);
        adjustmentHeaderEntity.setUpdateTime(new Date().getTime());
        adjustmentHeaderMapper.updateAdjustmentHeader(adjustmentHeaderEntity,getSplitTableKey(dbShardVO));
        return  MessageResult.getSucMessage();
    }


    /**
     * 调整审核
     * @param adjustmentHeaderEntity
     * @param operationUser
     * @param dbShardVO
     * @return
     */
    private MessageResult adjustmentReviewed (TWmsAdjustmentHeaderEntity adjustmentHeaderEntity,String operationUser, DbShardVO dbShardVO){
        //审核后更改库存数量
       List<TWmsAdjustmentDetailEntity> adjustmentDetailEntities =   adjustmentDetailService.getAdjustmentDetails(adjustmentHeaderEntity.getId(),dbShardVO);
        for (TWmsAdjustmentDetailEntity adjustmentDetailEntity : adjustmentDetailEntities){
            inventoryService.updateInventoryFromAdjustment(adjustmentHeaderEntity,adjustmentDetailEntity,operationUser,adjustmentDetailEntity.getSkuId(),adjustmentHeaderEntity.getCargoOwnerId(),adjustmentDetailEntity.getStorageRoomId(),dbShardVO);
            adjustmentDetailEntity.setStatusCode(TicketStatusCode.REVIEWED.toString());
            adjustmentDetailEntity.setUpdateUser(operationUser);
            adjustmentDetailEntity.setUpdateTime(new Date().getTime());
            adjustmentDetailMapper.updateAdjustmentDetail(adjustmentDetailEntity,getSplitTableKey(dbShardVO));
        }
        adjustmentHeaderEntity.setStatusCode(TicketStatusCode.REVIEWED.toString());
        adjustmentHeaderEntity.setUpdateUser(operationUser);
        adjustmentHeaderEntity.setUpdateTime(new Date().getTime());
        adjustmentHeaderMapper.updateAdjustmentHeader(adjustmentHeaderEntity,getSplitTableKey(dbShardVO));
        return  MessageResult.getSucMessage();
    }

}
