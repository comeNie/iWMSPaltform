package com.huamengtong.wms.outwh.service.impl;

import com.huamengtong.wms.client.IAutoIdClient;
import com.huamengtong.wms.constants.AutoIdConstants;
import com.huamengtong.wms.core.annotation.NeedLog;
import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.service.BaseService;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.outwh.TWmsUnfrozenDetailDTO;
import com.huamengtong.wms.dto.outwh.TWmsUnfrozenHeaderDTO;
import com.huamengtong.wms.em.ActionCode;
import com.huamengtong.wms.em.LogType;
import com.huamengtong.wms.em.OperationStatusCode;
import com.huamengtong.wms.em.OrderTypeCode;
import com.huamengtong.wms.em.TicketStatusCode;
import com.huamengtong.wms.em.UnfrozenStatusCode;
import com.huamengtong.wms.entity.outwh.TWmsFrozenDetailEntity;
import com.huamengtong.wms.entity.outwh.TWmsUnfrozenDetailEntity;
import com.huamengtong.wms.entity.outwh.TWmsUnfrozenHeaderEntity;
import com.huamengtong.wms.outwh.mapper.UnfrozenDetailMapper;
import com.huamengtong.wms.outwh.service.IUnfrozenDetailService;
import com.huamengtong.wms.outwh.service.IUnfrozenHeaderService;
import com.huamengtong.wms.utils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class UnfrozenDetailService extends BaseService implements IUnfrozenDetailService {

    @Autowired
    UnfrozenDetailMapper unfrozenDetailMapper;

    @Autowired
    IAutoIdClient autoIdClient;

    @Autowired
    IUnfrozenHeaderService unfrozenHeaderService;

    @Override
    public TWmsUnfrozenDetailEntity findByPrimaryKey(Long id, DbShardVO dbShardVO) {
        return unfrozenDetailMapper.selectByPrimaryKey(id,getSplitTableKey(dbShardVO));
    }

    @Override
    public MessageResult createUnfrozenDetail(TWmsUnfrozenDetailDTO unfrozenDetailDTO, DbShardVO dbShardVO) {
        TWmsUnfrozenDetailEntity unfrozenDetailEntity= BeanUtils.copyBeanPropertyUtils(unfrozenDetailDTO,TWmsUnfrozenDetailEntity.class);
        unfrozenDetailEntity.setId(autoIdClient.getAutoId(AutoIdConstants.TWmsUnfrozenDetailEntity));
        unfrozenDetailEntity.setStatusCode(TicketStatusCode.INIT.toString());
        unfrozenDetailMapper.insertUnfrozenDetail(unfrozenDetailEntity,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult removeUnfrozenDetail(Long id, DbShardVO dbShardVO) {
        unfrozenDetailMapper.deleteUnfrozenDetail(id,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult modifyUnfrozenDetail(TWmsUnfrozenDetailDTO unfrozenDetailDTO, DbShardVO dbShardVO) {
        TWmsUnfrozenDetailEntity unfrozenDetailEntity= BeanUtils.copyBeanPropertyUtils(unfrozenDetailDTO,TWmsUnfrozenDetailEntity.class);
        unfrozenDetailMapper.updateUnfrozenDetail(unfrozenDetailEntity,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();

    }

    @Override
    public PageResponse<List> queryUnfrozenDetailByHeader(Map paraMap, DbShardVO dbShardVO) {
        paraMap.put("splitTableKey",getSplitTableKey(dbShardVO));
        List<TWmsUnfrozenDetailEntity> mapList = unfrozenDetailMapper.queryDetailPages(paraMap);
        Integer totalSize = unfrozenDetailMapper.queryDetailPageCount(paraMap);
        PageResponse<List> response = new PageResponse();
        response.setRows(mapList);
        response.setTotal(totalSize);
        return response;
    }

    @Override
    public List<TWmsUnfrozenDetailEntity> getUnfrozenDetailsByHeaderId(Long unfrozenId, DbShardVO dbShardVO) {
            List<TWmsUnfrozenDetailEntity> mapList = unfrozenDetailMapper.queryUnfrozenDetails(unfrozenId,getSplitTableKey(dbShardVO));
        if (CollectionUtils.isNotEmpty(mapList))
        {
            return mapList;
        }
        return null;
    }

    /**
     * 从质押明细单创建解质押单据
     * @param unFrozenHeaderId  解质押主表单id
     * @param operationUser  操作人
     * @param frozenDetailEntityList   质押明细单据
     * @param dbShardVO  分库标识
     * @return
     */
    @Override
    public MessageResult createUnFrozenDetailFromFrozen(Long unFrozenHeaderId, String operationUser, List<TWmsFrozenDetailEntity> frozenDetailEntityList, DbShardVO dbShardVO) {
        for (TWmsFrozenDetailEntity frozenDetailEntity:frozenDetailEntityList){
            TWmsUnfrozenDetailEntity unfrozenDetailEntity = new TWmsUnfrozenDetailEntity();
            unfrozenDetailEntity.setId(autoIdClient.getAutoId(AutoIdConstants.TWmsUnfrozenDetailEntity));
            unfrozenDetailEntity.setUnfrozenId(unFrozenHeaderId);
            unfrozenDetailEntity.setFrozenDetailId(frozenDetailEntity.getId());
            unfrozenDetailEntity.setTenantId(frozenDetailEntity.getTenantId());
            unfrozenDetailEntity.setWarehouseId(frozenDetailEntity.getWarehouseId());
            unfrozenDetailEntity.setFrozenedQty(frozenDetailEntity.getFactoringQty());//原质押数量
            unfrozenDetailEntity.setSkuId(frozenDetailEntity.getSkuId());
            unfrozenDetailEntity.setStorageRoomId(frozenDetailEntity.getStorageRoomId());
            if (!(frozenDetailEntity.getSku()==null)){
                unfrozenDetailEntity.setSku(frozenDetailEntity.getSku());
            }
            if (frozenDetailEntity.getSkuName()!=null)
            {
                unfrozenDetailEntity.setSkuName(frozenDetailEntity.getSkuName());
            }
            if (!(frozenDetailEntity.getSkuBarcode()==null)){
                unfrozenDetailEntity.setSkuBarcode(frozenDetailEntity.getSkuBarcode());
            }
            if(!(frozenDetailEntity.getCostPrice()==null)){
                unfrozenDetailEntity.setCostPrice(frozenDetailEntity.getCostPrice());
            }
            if (!(frozenDetailEntity.getCategorysId()==null)){
                unfrozenDetailEntity.setCategorysId(frozenDetailEntity.getCategorysId());
            }
            if (!(frozenDetailEntity.getSpec()==null)){
                unfrozenDetailEntity.setSpec(frozenDetailEntity.getSpec());
            }
            if(!(frozenDetailEntity.getUnitType()==null)){
                unfrozenDetailEntity.setUnitType(frozenDetailEntity.getUnitType());
            }
            if(!(frozenDetailEntity.getWorkGroupNo()==null)){
                unfrozenDetailEntity.setWorkGroupNo(frozenDetailEntity.getWorkGroupNo());
            }
            if(!(frozenDetailEntity.getNetWeight()==null)){
                unfrozenDetailEntity.setNetWeight(frozenDetailEntity.getNetWeight());
            }
            if(!(frozenDetailEntity.getGrossWeight()==null)){
                unfrozenDetailEntity.setGrossWeight(frozenDetailEntity.getGrossWeight());
            }
            if(!(frozenDetailEntity.getVolume()==null)){
                unfrozenDetailEntity.setVolume(frozenDetailEntity.getVolume());
            }
            if(!(frozenDetailEntity.getDescription()==null)){
                unfrozenDetailEntity.setDescription(frozenDetailEntity.getDescription());
            }
            if(!(frozenDetailEntity.getTotalPrice()==null)){
                unfrozenDetailEntity.setTotalPrice(frozenDetailEntity.getTotalPrice());
            }
            unfrozenDetailEntity.setStatusCode(TicketStatusCode.INIT.toString());
            Long currentTime = new Date().getTime();
            unfrozenDetailEntity.setCreateUser(operationUser);
            unfrozenDetailEntity.setCreateTime(currentTime);
            unfrozenDetailEntity.setUpdateUser(operationUser);
            unfrozenDetailEntity.setUpdateTime(currentTime);
            unfrozenDetailMapper.insertUnfrozenDetail(unfrozenDetailEntity,getSplitTableKey(dbShardVO));
        }
        return MessageResult.getSucMessage();
    }


    @Override
    public MessageResult updateDetailFromSubmitUnfrozen(TWmsUnfrozenDetailEntity unfrozenDetailEntity,String operationUser, DbShardVO dbShardVO) {
        unfrozenDetailEntity.setStatusCode(TicketStatusCode.SUBMIT.toString());//提交状态
        unfrozenDetailEntity.setFactoringStatusCode(UnfrozenStatusCode.UNFROZENING.toString());//解押状态为解押中
        //本次解押数量等于原质押数量减去已解押总数量
        unfrozenDetailEntity.setFactoringQty(unfrozenDetailEntity.getFrozenedQty()-unfrozenDetailEntity.getUnfrozenedQty());
        unfrozenDetailEntity.setUpdateUser(operationUser);//操作人
        unfrozenDetailEntity.setUpdateTime(new Date().getTime());//操作时间
        unfrozenDetailMapper.updateUnfrozenDetail(unfrozenDetailEntity,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult updateDetailFromRepealedUnfrozen(TWmsUnfrozenDetailEntity unfrozenDetailEntity, String operationUser, DbShardVO dbShardVO) {
        unfrozenDetailEntity.setStatusCode(TicketStatusCode.INIT.toString());
        if (unfrozenDetailEntity.getUnfrozenedQty()==0){
            unfrozenDetailEntity.setFactoringStatusCode(UnfrozenStatusCode.NONE.toString());
        }else {
            unfrozenDetailEntity.setFactoringStatusCode(UnfrozenStatusCode.UNFROZENED_PART.toString());
        }
        unfrozenDetailEntity.setFactoringQty(0);
        unfrozenDetailEntity.setUpdateUser(operationUser);
        unfrozenDetailEntity.setUpdateTime(new Date().getTime());
        unfrozenDetailMapper.updateUnfrozenDetail(unfrozenDetailEntity,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }


    @Override
    public MessageResult updateDetailFromInvalidUnfrozen(TWmsUnfrozenDetailEntity unfrozenDetailEntity, String operationUser, DbShardVO dbShardVO) {
        unfrozenDetailEntity.setStatusCode(TicketStatusCode.INVALID.toString());
        unfrozenDetailEntity.setUpdateUser(operationUser);
        unfrozenDetailEntity.setUpdateTime(new Date().getTime());
        unfrozenDetailMapper.updateUnfrozenDetail(unfrozenDetailEntity,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult updateDetailFromAuditUnfrozen(List<TWmsUnfrozenDetailEntity> unfrozenDetailEntities, String operationUser, DbShardVO dbShardVO) {
        for (TWmsUnfrozenDetailEntity unfrozenDetailEntity:unfrozenDetailEntities){
            Integer unfrozenedQty = unfrozenDetailEntity.getFactoringQty()+unfrozenDetailEntity.getUnfrozenedQty();
            //判断本次解押为何种解押方式，并回写状态
            if (unfrozenDetailEntity.getFrozenedQty().equals(unfrozenedQty)){
                unfrozenDetailEntity.setStatusCode(TicketStatusCode.REVIEWED.toString());
                unfrozenDetailEntity.setFactoringStatusCode(UnfrozenStatusCode.UNFROZENED_ALL.toString());
            }else if(unfrozenedQty==0){
                unfrozenDetailEntity.setStatusCode(TicketStatusCode.INIT.toString());
                unfrozenDetailEntity.setFactoringStatusCode(UnfrozenStatusCode.NONE.toString());
            }else {
                unfrozenDetailEntity.setStatusCode(TicketStatusCode.INIT.toString());
                unfrozenDetailEntity.setFactoringStatusCode(UnfrozenStatusCode.UNFROZENED_PART.toString());
            }
            unfrozenDetailEntity.setUnfrozenedQty(unfrozenedQty);
            unfrozenDetailEntity.setFactoringQty(0);
            unfrozenDetailEntity.setUpdateUser(operationUser);
            unfrozenDetailEntity.setUpdateTime(new Date().getTime());
            unfrozenDetailMapper.updateUnfrozenDetail(unfrozenDetailEntity,getSplitTableKey(dbShardVO));
        }
        return MessageResult.getSucMessage();
    }

    @Override
    @NeedLog(type = LogType.OPREATION)
    public MessageResult executeSubmitUnfrozenDetail(Long unfrozenDetailId, Integer factoringQty, String operationUser, DbShardVO dbShardVO) {
        if(unfrozenDetailId == null || unfrozenDetailId==0 ){
            return MessageResult.getMessage("E71012");
        }
        if( factoringQty==null || factoringQty<=0){
            return MessageResult.getMessage("E71013");
        }
        TWmsUnfrozenDetailEntity unfrozenDetailEntity = unfrozenDetailMapper.selectByPrimaryKey(unfrozenDetailId,getSplitTableKey(dbShardVO));
        if(unfrozenDetailEntity == null){
            return MessageResult.getMessage("E71014",new Object[]{unfrozenDetailId});
        }
        if(factoringQty > (unfrozenDetailEntity.getFrozenedQty()-unfrozenDetailEntity.getUnfrozenedQty())){
            return MessageResult.getMessage("E71016");
        }
        TWmsUnfrozenHeaderEntity unfrozenHeaderEntity = unfrozenHeaderService.findByPrimaryKey(unfrozenDetailEntity.getUnfrozenId(),dbShardVO);
        if(unfrozenHeaderEntity==null){
            return MessageResult.getMessage("E71015");
        }
        unfrozenDetailEntity.setStatusCode(TicketStatusCode.SUBMIT.toString());
        unfrozenDetailEntity.setFactoringQty(factoringQty);
        unfrozenDetailEntity.setFactoringStatusCode(UnfrozenStatusCode.UNFROZENING.toString());
        unfrozenDetailEntity.setUpdateUser(operationUser);
        unfrozenDetailEntity.setUpdateTime(new Date().getTime());
        unfrozenDetailMapper.updateUnfrozenDetail(unfrozenDetailEntity,getSplitTableKey(dbShardVO));
        TWmsUnfrozenHeaderDTO unfrozenHeaderDTO = BeanUtils.copyBeanPropertyUtils(unfrozenHeaderEntity,TWmsUnfrozenHeaderDTO.class);
        unfrozenHeaderService.updateStatusFromSubmitDetail(unfrozenHeaderDTO,operationUser,dbShardVO);
        this.writeOperationLog(unfrozenDetailEntity.getUnfrozenId(), unfrozenHeaderEntity.getCargoOwnerId(),ActionCode.Submit.toString(),operationUser+"提交质押明细"+unfrozenDetailId+"||解押数量"+factoringQty, OrderTypeCode.UNFROZEN.toString(), OperationStatusCode.Finished.toString(),dbShardVO);
        return MessageResult.getSucMessage();
    }
}
