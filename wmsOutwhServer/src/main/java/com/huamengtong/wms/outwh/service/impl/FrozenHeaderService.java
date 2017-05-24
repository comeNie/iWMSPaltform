package com.huamengtong.wms.outwh.service.impl;

import com.huamengtong.wms.client.IAutoIdClient;
import com.huamengtong.wms.constants.AutoIdConstants;
import com.huamengtong.wms.core.annotation.NeedLog;
import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.service.BaseService;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.inventory.TWmsInventoryDTO;
import com.huamengtong.wms.dto.outwh.TWmsFrozenHeaderDTO;
import com.huamengtong.wms.em.ActionCode;
import com.huamengtong.wms.em.LogType;
import com.huamengtong.wms.em.OperationStatusCode;
import com.huamengtong.wms.em.OrderTypeCode;
import com.huamengtong.wms.em.TicketStatusCode;
import com.huamengtong.wms.entity.inventory.TWmsInventoryEntity;
import com.huamengtong.wms.entity.outwh.TWmsFrozenDetailEntity;
import com.huamengtong.wms.entity.outwh.TWmsFrozenHeaderEntity;
import com.huamengtong.wms.entity.sku.TWmsSkuEntity;
import com.huamengtong.wms.inventory.service.IInventoryService;
import com.huamengtong.wms.outwh.mapper.FrozenHeaderMapper;
import com.huamengtong.wms.outwh.service.IFrozenHeaderService;
import com.huamengtong.wms.outwh.service.IUnfrozenDetailService;
import com.huamengtong.wms.outwh.service.IUnfrozenHeaderService;
import com.huamengtong.wms.sku.service.ISkuService;
import com.huamengtong.wms.utils.BeanUtils;
import com.huamengtong.wms.vo.FrozenInventoryVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class FrozenHeaderService extends BaseService implements IFrozenHeaderService {

    @Autowired
    private FrozenHeaderMapper frozenHeaderMapper;

    @Autowired
    private IAutoIdClient autoIdClient;

    @Autowired
    FrozenDetailService frozenDetailService;

    @Autowired
    IUnfrozenHeaderService unfrozenHeaderService;

    @Autowired
    IUnfrozenDetailService unfrozenDetailService;


    @Autowired
    IInventoryService inventoryService;

    @Autowired
    ISkuService skuService;




    @Override
    public PageResponse<List<TWmsFrozenHeaderEntity>> getFrozenHeader(TWmsFrozenHeaderDTO frozenHeaderDTO, DbShardVO dbShardVO) {
        TWmsFrozenHeaderEntity frozenHeaderEntity = BeanUtils.copyBeanPropertyUtils(frozenHeaderDTO,TWmsFrozenHeaderEntity.class);
        List<TWmsFrozenHeaderEntity>  frozenHeaderEntityList= frozenHeaderMapper.queryFrozenHeaderPages(frozenHeaderEntity,getSplitTableKey(dbShardVO));
        Integer totalSize = frozenHeaderMapper.queryFrozenHeaderPageCount(frozenHeaderEntity,getSplitTableKey(dbShardVO));
        PageResponse<List<TWmsFrozenHeaderEntity>> response = new PageResponse();
        response.setRows(frozenHeaderEntityList);
        response.setTotal(totalSize);
        return response;
    }

    @Override
    public TWmsFrozenHeaderEntity findByPrimaryKey(Long id, DbShardVO dbShardVO) {
        return frozenHeaderMapper.selectByPrimaryKey(id,getSplitTableKey(dbShardVO));
    }

    @Override
    @NeedLog(type = LogType.OPREATION)
    public MessageResult createFrozenHeader(TWmsFrozenHeaderDTO frozenHeaderDTO, DbShardVO dbShardVO) {
        if (frozenHeaderDTO.getCargoOwnerId() == null || frozenHeaderDTO.getCargoOwnerId() == 0){
            return MessageResult.getMessage("E40001");
        }
        List<TWmsInventoryEntity> inventoryEntityList = inventoryService.findByCargoOwnerId(frozenHeaderDTO.getCargoOwnerId(),changeInwhDbShareVO(dbShardVO));
        if(CollectionUtils.isEmpty(inventoryEntityList)){
            return MessageResult.getMessage("E70016");
        }
        if(frozenHeaderDTO.getFactoringOrganizeId()==null || frozenHeaderDTO.getFactoringOrganizeId()==0){
            return MessageResult.getMessage("E70014");
        }

        TWmsFrozenHeaderEntity frozenHeaderEntity = BeanUtils.copyBeanPropertyUtils(frozenHeaderDTO,TWmsFrozenHeaderEntity.class);
        frozenHeaderEntity.setId(autoIdClient.getAutoId(AutoIdConstants.TWmsFrozenHeaderEntity));
        frozenHeaderEntity.setStatusCode(TicketStatusCode.INIT.toString());
        frozenHeaderMapper.insertFrozenHeader(frozenHeaderEntity,getSplitTableKey(dbShardVO));
        this.writeOperationLog(frozenHeaderEntity.getId(),frozenHeaderEntity.getCargoOwnerId(),ActionCode.Create.toString(),frozenHeaderEntity.getCreateUser()+"创建质押单",OrderTypeCode.FROZEN.toString(),OperationStatusCode.Finished.toString(),dbShardVO);
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult removeFrozenHeader(Long id, DbShardVO dbShardVO) {
        frozenDetailService.removeByFrozenId(id,dbShardVO);//删除明细数据
        frozenHeaderMapper.deleteFrozenHeader(id,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult modifyFrozenHeader(TWmsFrozenHeaderDTO frozenHeaderDTO, DbShardVO dbShardVO) {
        TWmsFrozenHeaderEntity frozenHeaderEntity=BeanUtils.copyBeanPropertyUtils(frozenHeaderDTO,TWmsFrozenHeaderEntity.class);
        frozenHeaderMapper.updateFrozenHeader(frozenHeaderEntity,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }


    @Override
    @NeedLog(type = LogType.OPREATION)
    public MessageResult executeSubmitFrozen(Long frozenId, String operationUser, DbShardVO dbShardVO) {
        if(frozenId==null){
            return MessageResult.getMessage("E70001");
        }
        TWmsFrozenHeaderEntity frozenHeaderEntity = frozenHeaderMapper.selectByPrimaryKey(frozenId,getSplitTableKey(dbShardVO));
        if(frozenHeaderEntity==null){
            return MessageResult.getMessage("E70001");
        }
        if(!(unfrozenHeaderService.findByFrozenId(frozenId,dbShardVO)==null)){
            return MessageResult.getMessage("E70010");
        }
        if(!frozenHeaderEntity.getStatusCode().equals(TicketStatusCode.INIT.toString())){
            return MessageResult.getMessage("E70005");
        }
        List<TWmsFrozenDetailEntity> frozenDetailEntityList = frozenDetailService.getFrozenDetailsByHeaderId(frozenId,dbShardVO);
        if (CollectionUtils.isEmpty(frozenDetailEntityList)){
            return MessageResult.getMessage("E70002");
        }
        Long currentTime = new Date().getTime();
        List<TWmsInventoryEntity> inventoryEntityList = new ArrayList<TWmsInventoryEntity>();
        for (TWmsFrozenDetailEntity frozenDetailEntity:frozenDetailEntityList){
            TWmsInventoryEntity inventoryEntity = inventoryService.findInventoryFromCargoOwner(frozenDetailEntity.getSkuId(),frozenHeaderEntity.getCargoOwnerId(),frozenDetailEntity.getStorageRoomId(),changeInwhDbShareVO(dbShardVO));
            if(inventoryEntity.getIsHold() ==1){
                return MessageResult.getMessage("E70020");
            }
            // 逐个验证库存中的可用数量是否小于质押数量
            if(frozenDetailEntity.getFactoringQty()>inventoryEntity.getOnhandQty()-inventoryEntity.getAllocatedQty()-inventoryEntity.getPickedQty()-inventoryEntity.getMortgagedQty()-inventoryEntity.getWorkingQty()){
                return MessageResult.getMessage("E70019",new Object[]{frozenDetailEntity.getId()});
            }
            inventoryEntity.setMortgagedQty(inventoryEntity.getMortgagedQty()+frozenDetailEntity.getFactoringQty());
            inventoryEntity.setUpdateUser(operationUser);
            inventoryEntity.setUpdateTime(currentTime);
            inventoryEntityList.add(inventoryEntity);
        }
        //更新库存中已质押数量
        MessageResult messageResult = inventoryService.batchUpdateFromSubmitFrozen(inventoryEntityList,changeInwhDbShareVO(dbShardVO));
        if(!messageResult.isSuc()){
            messageResult=MessageResult.getFailMessage();
            messageResult.setMessage(messageResult.getMessage()+"质押失败！");
            return messageResult;
        }
        messageResult = frozenDetailService.updateFrozenDetailStatus(frozenDetailEntityList,TicketStatusCode.SUBMIT.toString(),operationUser,dbShardVO);
        if(!messageResult.isSuc()){
            messageResult = MessageResult.getFailMessage();
            messageResult.setMessage(messageResult.getMessage()+"明细修改状态失败");
            return messageResult;
        }
        frozenHeaderEntity.setStatusCode(TicketStatusCode.SUBMIT.toString());
        frozenHeaderEntity.setUpdateUser(operationUser);
        frozenHeaderEntity.setUpdateTime(currentTime);
        frozenHeaderMapper.updateFrozenHeader(frozenHeaderEntity,getSplitTableKey(dbShardVO));
        this.writeOperationLog(frozenId,frozenHeaderEntity.getCargoOwnerId(),ActionCode.Submit.toString(),operationUser+"提交质押单",OrderTypeCode.FROZEN.toString(),OperationStatusCode.Finished.toString(),dbShardVO);
        return messageResult;
    }

    @Override
    @NeedLog(type = LogType.OPREATION)
    public MessageResult executeRepealedFrozen(Long frozenId, String operationUser, DbShardVO dbShardVO) {
        if(frozenId==null){
            return MessageResult.getMessage("E70001");
        }
        TWmsFrozenHeaderEntity frozenHeaderEntity = frozenHeaderMapper.selectByPrimaryKey(frozenId,getSplitTableKey(dbShardVO));
        if(frozenHeaderEntity==null){
            return MessageResult.getMessage("E70001");
        }
        List<TWmsFrozenDetailEntity> frozenDetailEntityList = frozenDetailService.getFrozenDetailsByHeaderId(frozenId,dbShardVO);
        if (CollectionUtils.isEmpty(frozenDetailEntityList)){
            return MessageResult.getMessage("E70002");
        }
        Long currentTime = new Date().getTime();
        List<TWmsInventoryEntity> inventoryEntityList = new ArrayList<>();
        for (TWmsFrozenDetailEntity frozenDetailEntity : frozenDetailEntityList){
            TWmsInventoryEntity inventoryEntity = inventoryService.findInventoryFromCargoOwner(frozenDetailEntity.getSkuId(),frozenHeaderEntity.getCargoOwnerId(),frozenDetailEntity.getStorageRoomId(),changeInwhDbShareVO(dbShardVO));
            inventoryEntity.setMortgagedQty(inventoryEntity.getMortgagedQty()-frozenDetailEntity.getFactoringQty());
            inventoryEntity.setUpdateUser(operationUser);
            inventoryEntity.setUpdateTime(currentTime);
            inventoryEntityList.add(inventoryEntity);
        }
        MessageResult messageResult = inventoryService.batchUpdateFromSubmitFrozen(inventoryEntityList,changeInwhDbShareVO(dbShardVO));
        if(!messageResult.isSuc()){
            messageResult = MessageResult.getFailMessage();
            return messageResult;
        }
        messageResult = frozenDetailService.updateFrozenDetailStatus(frozenDetailEntityList,TicketStatusCode.REPEALED.toString(),operationUser,dbShardVO);
        if(!messageResult.isSuc()){
            messageResult = MessageResult.getFailMessage();
            return messageResult;
        }
        frozenHeaderEntity.setStatusCode(TicketStatusCode.INIT.toString());
        frozenHeaderEntity.setUpdateUser(operationUser);
        frozenHeaderEntity.setUpdateTime(currentTime);
        frozenHeaderMapper.updateFrozenHeader(frozenHeaderEntity,getSplitTableKey(dbShardVO));
        this.writeOperationLog(frozenId,frozenHeaderEntity.getCargoOwnerId(),ActionCode.Repealed.toString(),operationUser+"撤销质押单",OrderTypeCode.FROZEN.toString(),OperationStatusCode.Finished.toString(),dbShardVO);
        return MessageResult.getSucMessage();
    }

    @Override
    @NeedLog(type = LogType.OPREATION)
    public MessageResult executeRejectFrozen(Long frozenId, String rejectReason, String operationUser, DbShardVO dbShardVO) {
        if(frozenId==null){
            return MessageResult.getMessage("E70001");
        }
        //判断拒绝原因是否填写，去除字符串中空格
        if(rejectReason==null || StringUtils.isEmpty(rejectReason.trim())){
            return MessageResult.getMessage("E70007");
        }
        MessageResult messageResult = executeRepealedFrozen(frozenId,operationUser,dbShardVO);
        if (!messageResult.isSuc()){
            messageResult = MessageResult.getFailMessage();
            return messageResult;
        }
        TWmsFrozenHeaderEntity frozenHeaderEntity = frozenHeaderMapper.selectByPrimaryKey(frozenId,getSplitTableKey(dbShardVO));
        //提取字符串中的前300
        String string;
        if(rejectReason.length()>300){
            string = rejectReason.substring(0,299);
        }else{
            string = rejectReason;
        }
        frozenHeaderEntity.setDescription(string);//拒绝原因
        frozenHeaderEntity.setUpdateUser(operationUser);
        frozenHeaderEntity.setUpdateTime(new Date().getTime());
        frozenHeaderMapper.updateFrozenHeader(frozenHeaderEntity,getSplitTableKey(dbShardVO));
        this.writeOperationLog(frozenId,frozenHeaderEntity.getCargoOwnerId(),ActionCode.Reject.toString(),operationUser+"拒绝审核通过质押单,拒绝原因:"+rejectReason,OrderTypeCode.FROZEN.toString(),OperationStatusCode.Finished.toString(),dbShardVO);
        return MessageResult.getSucMessage();
    }

    @Override
    @NeedLog(type = LogType.OPREATION)
    public MessageResult executeAuditFrozen(Long frozenId, String operationUser, DbShardVO dbShardVO) {
        if (frozenId==null){
            return MessageResult.getMessage("E70005");
        }
        TWmsFrozenHeaderEntity frozenHeaderEntity = frozenHeaderMapper.selectByPrimaryKey(frozenId,getSplitTableKey(dbShardVO));
        if(frozenHeaderEntity==null){
            return MessageResult.getMessage("E70005");
        }
        if(!(unfrozenHeaderService.findByFrozenId(frozenId,dbShardVO)==null)){
            return MessageResult.getMessage("E70010");
        }
        List<TWmsFrozenDetailEntity> frozenDetailEntityList = frozenDetailService.getFrozenDetailsByHeaderId(frozenId,dbShardVO);
        if (CollectionUtils.isEmpty(frozenDetailEntityList)){
            return MessageResult.getMessage("E70008");
        }
        Long currentTime = new Date().getTime();
        Long unFrozenHeaderId=autoIdClient.getAutoId(AutoIdConstants.TWmsUnfrozenHeaderEntity);//解押单主表ID
        unfrozenDetailService.createUnFrozenDetailFromFrozen(unFrozenHeaderId,operationUser,frozenDetailEntityList,dbShardVO);//生成解押明细单据
        TWmsFrozenHeaderDTO frozenHeaderDTO = BeanUtils.copyBeanPropertyUtils(frozenHeaderEntity,TWmsFrozenHeaderDTO.class);
        unfrozenHeaderService.createUnFrozenFromFrozen(unFrozenHeaderId,frozenHeaderDTO,operationUser,dbShardVO);//生成解押主表单
        frozenDetailService.updateFrozenDetailStatus(frozenDetailEntityList,TicketStatusCode.REVIEWED.toString(),operationUser,dbShardVO);//更新质押明细状态为已审核
        frozenHeaderEntity.setStatusCode(TicketStatusCode.REVIEWED.toString());
        frozenHeaderEntity.setAuditedOrganizeId(13L);//暂时写入固定的值，固定的账号,审核专用帐号
        frozenHeaderEntity.setAuditedUser(operationUser);
        frozenHeaderEntity.setAuditedTime(currentTime);
        frozenHeaderEntity.setIsAudited(new Byte("1"));//设置单据已审核
        frozenHeaderEntity.setUpdateUser(operationUser);
        frozenHeaderEntity.setUpdateTime(currentTime);
        frozenHeaderMapper.updateFrozenHeader(frozenHeaderEntity,getSplitTableKey(dbShardVO));
        this.writeOperationLog(frozenId,frozenHeaderEntity.getCargoOwnerId(),ActionCode.AUDITED.toString(),operationUser+"审核通过质押单",OrderTypeCode.FROZEN.toString(),OperationStatusCode.Finished.toString(),dbShardVO);
        return MessageResult.getSucMessage();
    }


    @Override
    public PageResponse<List<FrozenInventoryVO>> getFrozenInventoryVOs(Map map, DbShardVO dbShardVO) {
        Long cargoOwnerId = MapUtils.getLong(map,"cargoOwnerId");
        List<FrozenInventoryVO> frozenInventoryVOList = new ArrayList<>();
        PageResponse<List<FrozenInventoryVO>> response = new PageResponse<>();
        TWmsInventoryDTO inventoryDTO = new TWmsInventoryDTO();
        inventoryDTO.setCargoOwnerId(cargoOwnerId);
        inventoryDTO.setIsHold(new Byte("0"));
        inventoryDTO.setPage(MapUtils.getInteger(map,"page"));
        inventoryDTO.setPageSize(MapUtils.getInteger(map,"pageSize"));
        inventoryDTO.setOffset(MapUtils.getInteger(map,"offset"));
        if(map.containsKey("sku") && StringUtils.isNotEmpty(MapUtils.getString(map,"sku"))){
            List<TWmsSkuEntity> skuEntityList = skuService.findSkuCargoOwner(MapUtils.getString(map,"sku"),null,cargoOwnerId,null,getSkuDbShareVO(dbShardVO));
            if(CollectionUtils.isEmpty(skuEntityList)){
                return null;
            }
            inventoryDTO.setSkuId(skuEntityList.get(0).getId());
            List<TWmsInventoryEntity> inventoryEntityList = inventoryService.getInventoryList(inventoryDTO,changeInwhDbShareVO(dbShardVO));
            if (CollectionUtils.isEmpty(inventoryEntityList)){
                return null;
            }
            for (TWmsInventoryEntity inventoryEntity:inventoryEntityList){
                frozenInventoryVOList.add(createFrozenInventoryVO(cargoOwnerId,inventoryEntity,skuEntityList.get(0)));
            }
        } else {
            List<TWmsInventoryEntity> inventoryEntityList = inventoryService.findByCargoOwnerId(cargoOwnerId,changeInwhDbShareVO(dbShardVO));
            for(TWmsInventoryEntity inventoryEntity:inventoryEntityList){
                List<TWmsSkuEntity> skuEntityList = skuService.findSkuCargoOwner(null,null,cargoOwnerId,inventoryEntity.getSkuId(),getSkuDbShareVO(dbShardVO));
                if(CollectionUtils.isEmpty(skuEntityList)){
                    continue;
                }
                frozenInventoryVOList.add(createFrozenInventoryVO(cargoOwnerId,inventoryEntity,skuEntityList.get(0)));
            }
        }
        response.setTotal(inventoryService.queryInventoryPageCount(inventoryDTO,changeInwhDbShareVO(dbShardVO)));
        response.setRows(frozenInventoryVOList);
        return response;

    }


    /**
     * 根据一条库存数据和一条商品数据创建一个VO对象
     * @param cargoOwnerId  货主ID
     * @param inventoryEntity  库存数据
     * @param skuEntity   商品数据
     * @return
     */
    private FrozenInventoryVO createFrozenInventoryVO(Long cargoOwnerId,TWmsInventoryEntity inventoryEntity,TWmsSkuEntity skuEntity){
        FrozenInventoryVO frozenInventoryVO = new FrozenInventoryVO();
        frozenInventoryVO.setInventoryId(inventoryEntity.getId());
        frozenInventoryVO.setStorageRoomId(inventoryEntity.getStorageRoomId());
        frozenInventoryVO.setCargoOwnerId(cargoOwnerId);
        frozenInventoryVO.setWorkGroupNo(inventoryEntity.getWorkGroupNo());
        frozenInventoryVO.setSkuId(inventoryEntity.getSkuId());
        frozenInventoryVO.setSku(skuEntity.getSku());
        frozenInventoryVO.setBarcode(skuEntity.getBarcode());
        //frozenInventoryVO.setCostPrice(skuEntity.getCostPrice());暂时不用
        frozenInventoryVO.setItemName(skuEntity.getItemName());
        frozenInventoryVO.setSpec(skuEntity.getSpec());
        frozenInventoryVO.setUnitType(skuEntity.getUnitType());
        frozenInventoryVO.setOnhandQty(inventoryEntity.getOnhandQty());
        frozenInventoryVO.setPickedQty(inventoryEntity.getPickedQty());
        frozenInventoryVO.setMortgagedQty(inventoryEntity.getMortgagedQty());
        frozenInventoryVO.setWorkingQty(inventoryEntity.getWorkingQty());
        frozenInventoryVO.setIsHold(inventoryEntity.getIsHold());
        frozenInventoryVO.setMortgagedAbleQty(inventoryEntity.getOnhandQty()-inventoryEntity.getAllocatedQty()-inventoryEntity.getPickedQty()-inventoryEntity.getMortgagedQty()-inventoryEntity.getWorkingQty());
        return frozenInventoryVO;
    }
}
