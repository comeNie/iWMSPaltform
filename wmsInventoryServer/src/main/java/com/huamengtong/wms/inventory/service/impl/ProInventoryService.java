package com.huamengtong.wms.inventory.service.impl;

import com.huamengtong.wms.client.IAutoIdClient;
import com.huamengtong.wms.constants.AutoIdConstants;
import com.huamengtong.wms.convert.ListArraysConvert;
import com.huamengtong.wms.core.annotation.NeedLog;
import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.service.BaseService;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.inventory.TWmsInventoryDTO;
import com.huamengtong.wms.dto.inventory.TWmsProInventoryDTO;
import com.huamengtong.wms.dto.sku.TWmsSkuCargoOwnerDTO;
import com.huamengtong.wms.dto.sku.TWmsSkuDTO;
import com.huamengtong.wms.em.*;
import com.huamengtong.wms.entity.inventory.TWmsInventoryEntity;
import com.huamengtong.wms.entity.inventory.TWmsProInventoryEntity;
import com.huamengtong.wms.entity.sku.TWmsSkuCargoOwnerEntity;
import com.huamengtong.wms.entity.sku.TWmsSkuEntity;
import com.huamengtong.wms.inventory.mapper.ProInventoryMapper;
import com.huamengtong.wms.inventory.service.IInventoryService;
import com.huamengtong.wms.inventory.service.IProInventoryService;
import com.huamengtong.wms.main.service.IStorageRoomService;
import com.huamengtong.wms.sku.service.ISkuCargoOwnerService;
import com.huamengtong.wms.sku.service.ISkuService;
import com.huamengtong.wms.utils.BeanUtils;
import com.huamengtong.wms.utils.DateUtil;
import com.huamengtong.wms.vo.ProducerProcessVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProInventoryService extends BaseService implements IProInventoryService{

    @Autowired
    ProInventoryMapper proInventoryMapper;

    @Autowired
    IInventoryService inventoryService;

    @Autowired
    IAutoIdClient autoIdClient;

    @Autowired
    IStorageRoomService storageRoomService;

    @Autowired
    ISkuService skuService;


    @Autowired
    ISkuCargoOwnerService skuCargoOwnerService;

    @Override
    public PageResponse<List<TWmsProInventoryEntity>> queryProInventoryPages(TWmsProInventoryDTO proInventoryDTO, DbShardVO dbShardVO) {
        TWmsProInventoryEntity proInventoryEntity = BeanUtils.copyBeanPropertyUtils(proInventoryDTO,TWmsProInventoryEntity.class);
        List<TWmsProInventoryEntity> list = proInventoryMapper.queryProInventoryPages(proInventoryEntity,getSplitTableKey(dbShardVO));
        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        List<Long> skuIds = list.stream().map(TWmsProInventoryEntity::getSkuId).collect(Collectors.toList());
        List<TWmsSkuEntity> skuEntityList = skuService.findByIdList(skuIds,getSkuDbShareVO(dbShardVO));
        Map<Long,TWmsSkuEntity> skuList = new HashMap<>();
        skuEntityList.stream().forEach(x->skuList.put(x.getId(),x));
        list.stream().forEach(x->{
            TWmsSkuEntity skuEntity = (TWmsSkuEntity)MapUtils.getObject(skuList,x.getSkuId()) ;
            x.setSkuName(skuEntity.getItemName());
            x.setSku(skuEntity.getSku());
        });
        Integer total = proInventoryMapper.queryProInventoryPageCount(proInventoryEntity,getSplitTableKey(dbShardVO));
        PageResponse pageResponse = new PageResponse();
        pageResponse.setTotal(total);
        pageResponse.setRows(list);
        return pageResponse;
    }


    @Override
    public MessageResult updateProInventory(TWmsProInventoryDTO proInventoryDTO, DbShardVO dbShardVO) {
        TWmsProInventoryEntity proInventoryEntiy = BeanUtils.copyBeanPropertyUtils(proInventoryDTO,TWmsProInventoryEntity.class);
        proInventoryMapper.updateProInventory(proInventoryEntiy,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }

    @Override
    public List<TWmsProInventoryEntity> getProInventoryListForReport(TWmsProInventoryDTO proInventoryDTO,List<Long> list, DbShardVO dbShardVO) {
        TWmsProInventoryEntity proInventoryEntity = BeanUtils.copyBeanPropertyUtils(proInventoryDTO, TWmsProInventoryEntity.class);
        return proInventoryMapper.selectProInventoryListForReport(proInventoryEntity,list,getSplitTableKey(dbShardVO));
    }

    @Override
    public MessageResult deleteProInventory(Long id, DbShardVO dbShardVO) {
        proInventoryMapper.deleteProInventory(id,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }

    @Override
    public TWmsProInventoryEntity findProInventoryById(Long id, DbShardVO dbShardVO) {
        return proInventoryMapper.selectByPK(id,getSplitTableKey(dbShardVO));
    }


    @Override
    public Integer getPageCountForReport(TWmsProInventoryDTO proInventoryDTO, List<Long> list, DbShardVO dbShardVO) {
        TWmsProInventoryEntity proInventoryEntity = BeanUtils.copyBeanPropertyUtils(proInventoryDTO,TWmsProInventoryEntity.class);
        return proInventoryMapper.selectProInventoryPageCountForReport(proInventoryEntity,list,getSplitTableKey(dbShardVO));
    }

    @Override
    public List<TWmsProInventoryEntity> getProInventoryList(TWmsProInventoryDTO proInventoryDTO, DbShardVO dbShardVO) {
        TWmsProInventoryEntity proInventoryEntity = BeanUtils.copyBeanPropertyUtils(proInventoryDTO,TWmsProInventoryEntity.class);
        return proInventoryMapper.queryProInventoryPages(proInventoryEntity,getSplitTableKey(dbShardVO));
    }


//    private List<TWmsProInventoryDTO> getAllocateProInventoryDtoBySku(Long skuId,Long cargoOwnerId,List<Long> roomsIds,DbShardVO dbShardVO){
//        List<TWmsProInventoryEntity> proInventoryEntities = proInventoryMapper.selectProInventoryListBySku(skuId,cargoOwnerId,roomsIds,getSplitTableKey(dbShardVO));
//        List<TWmsProInventoryDTO> proInventoryDTOList = proInventoryEntities.stream().filter(x -> x != null)
//                .map(x -> BeanUtils.copyBeanPropertyUtils(x,TWmsProInventoryDTO.class))
//                .collect(Collectors.toList());
//
//        return proInventoryDTOList;
//    }

    /**
     * 批量修改proinventory
     * @param list 要修改数据集合
     * @param dbShardVO
     * @return
     */
    @Override
    public MessageResult updateProInventoryBatch(List<TWmsProInventoryDTO> list, DbShardVO dbShardVO) {
        for (TWmsProInventoryDTO dto:list) {
            updateProInventory(dto,dbShardVO);
        }
        return MessageResult.getSucMessage();
    }

    @Override
    public List<ProducerProcessVO> getProInventoryReport(Map map, DbShardVO dbShardVO) {
        //TODO sku查询条件一律转换为skuId,再做参数传递查询
        String skuName = MapUtils.getString(map,"skuName");
        String sku = MapUtils.getString(map,"sku");
        TWmsSkuDTO skuDTO = new TWmsSkuDTO();
        if(sku != null && sku != ""){
            skuDTO.setSku(sku);
        }
        if(skuName != null && skuName !=""){
            skuDTO.setItemName(skuName);
        }
        List skuIds = new ArrayList();
        if(skuDTO.getSku() != null || skuDTO.getItemName() != null){
            skuIds = skuService.findSkuLists(skuDTO,getSkuDbShareVO(dbShardVO));
        }
        Set<Long> skuIdSet = new HashSet<>(skuIds);
        Long storageRoomId = MapUtils.getLong(map,"storageRoomId");
        Long cargoOwnerId = MapUtils.getLong(map,"cargoOwnerId");
        List<Map<String, Object>> mapList = proInventoryMapper.getProInventoryGroupByList(skuIdSet,storageRoomId,cargoOwnerId,getSplitTableKey(dbShardVO));
        List<Object[]> list = ListArraysConvert.listMapToArrayConvert(mapList);
        List<ProducerProcessVO> proInventoryDTOList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(list)){
            for(Object[] object : list){
                ProducerProcessVO processVO = new ProducerProcessVO();
                processVO.setQty(Integer.parseInt(object[0].toString()));
                TWmsSkuEntity skuEntity = skuService.findByPrimaryKey(Long.parseLong(object[1].toString()),getSkuDbShareVO(dbShardVO));
                if(skuEntity != null) {
                    processVO.setSkuName(skuEntity.getItemName());
                    processVO.setSkuType( skuEntity.getCategorysId()== null ? 0 : skuEntity.getCategorysId().intValue());
                    processVO.setSpec(object[2] == null ? "" : object[2].toString());
                    proInventoryDTOList.add(processVO);
                }
            }
        }
        return proInventoryDTOList;
    }

    private boolean checkParameters(TWmsProInventoryDTO proInventoryDTO,List<Map> proInventoryDTOList){
        for (Map x : proInventoryDTOList){
            TWmsProInventoryDTO y = new TWmsProInventoryDTO();
            BeanUtils.transMapToBean(x,y);
            if(y.getProQty() <= 0){
                return false;
            }
            if(y.getProSkuId().equals(proInventoryDTO.getSkuId())){
                return false;
            }
        }
        return true;
    }

    @Override
    @NeedLog(type = LogType.OPREATION)
    public MessageResult createProInventory(TWmsProInventoryDTO proInventoryDTO, List<Map> proInventoryDTOList, DbShardVO dbShardVO) {
        if(CollectionUtils.isEmpty(proInventoryDTOList)){
            return MessageResult.getMessage("E12001");
        }
        TWmsInventoryEntity inventoryEntity = inventoryService.findById(proInventoryDTO.getInventoryId(),dbShardVO);
        if(inventoryEntity == null){
            return MessageResult.getMessage("E12002");
        }
        if(proInventoryDTO.getQty()==0 || proInventoryDTO.getQty() > inventoryEntity.getOnhandQty()-inventoryEntity.getMortgagedQty()-inventoryEntity.getAllocatedQty()-inventoryEntity.getPickedQty()-inventoryEntity.getWorkingQty()){
            return MessageResult.getMessage("E12003");
        }
        if(!checkParameters(proInventoryDTO,proInventoryDTOList)){
            return MessageResult.getMessage("E12007");
        }
        //更新库存中在加工数量
        inventoryEntity.setWorkingQty(inventoryEntity.getWorkingQty()+proInventoryDTO.getQty());
        inventoryEntity.setUpdateUser(proInventoryDTO.getCreateUser());
        inventoryEntity.setUpdateTime(new Date().getTime());
        inventoryService.modifyInventory(BeanUtils.copyBeanPropertyUtils(inventoryEntity,TWmsInventoryDTO.class),dbShardVO);
        proInventoryDTO.setId(autoIdClient.getAutoId(AutoIdConstants.TWmsProInventoryEntiy));
        //插入原材料以及加工成品明细
        proInventoryDTO.setParentId(0L);
        proInventoryDTOList.stream().forEach(x->{
            TWmsProInventoryDTO y =  new TWmsProInventoryDTO();
            BeanUtils.transMapToBean(x,y);
            y.setId(autoIdClient.getAutoId(AutoIdConstants.TWmsProInventoryEntiy));
            y.setParentId(proInventoryDTO.getId());
            y.setTenantId(proInventoryDTO.getTenantId());
            y.setWarehouseId(proInventoryDTO.getWarehouseId());
            y.setInventoryId(proInventoryDTO.getInventoryId());
            y.setSkuId(y.getProSkuId());
            y.setSpec(y.getProSpec());
            y.setUnitType(y.getProUnitType());
            y.setQty(y.getProQty());
            y.setWorkGroupNo(proInventoryDTO.getWorkGroupNo());
            y.setStorageRoomId(y.getProStorageRoomId());
            y.setStatusCode(TicketStatusCode.SUBMIT.toString());
            y.setCargoOwnerId(proInventoryDTO.getCargoOwnerId());
            y.setCreateUser(proInventoryDTO.getCreateUser());
            y.setCreateTime(new Date().getTime());
            y.setUpdateUser(proInventoryDTO.getUpdateUser());
            y.setUpdateTime(new Date().getTime());
            TWmsProInventoryEntity proInventoryEntity = BeanUtils.copyBeanPropertyUtils(y,TWmsProInventoryEntity.class);
            proInventoryMapper.insertProInventory(proInventoryEntity,getSplitTableKey(dbShardVO));
            TWmsSkuCargoOwnerEntity skuCargoOwnerEntity = skuCargoOwnerService.selectBySkuIdAndCargoOwnerId(y.getSkuId(),y.getCargoOwnerId(),getSkuDbShareVO(dbShardVO));
            if(skuCargoOwnerEntity == null){
                TWmsSkuEntity skuEntity = skuService.findByPrimaryKey(y.getSkuId(),getSkuDbShareVO(dbShardVO));
                TWmsSkuCargoOwnerDTO skuCargoOwnerDTO = new TWmsSkuCargoOwnerDTO();
                skuCargoOwnerDTO.setCargoOwnerId(y.getCargoOwnerId());
                skuCargoOwnerDTO.setSkuId(y.getSkuId());
                skuCargoOwnerDTO.setSku(skuEntity.getSku());
                skuCargoOwnerDTO.setCargoOwnerBarcode(DateUtil.getNowDateTime().substring(0,10).replaceAll("-","").trim().concat(autoIdClient.getAutoId(AutoIdConstants.TWmsSkuCargoOwnerEntity,1).toString()));
                skuCargoOwnerService.insertSkuCargoOwner(skuCargoOwnerDTO,getSkuDbShareVO(dbShardVO));
            }
        });
        proInventoryDTO.setStatusCode(TicketStatusCode.SUBMIT.toString());
        proInventoryDTO.setCreateTime(new Date().getTime());
        proInventoryDTO.setUpdateTime(new Date().getTime());
        TWmsProInventoryEntity proInventoryEntity = BeanUtils.copyBeanPropertyUtils(proInventoryDTO,TWmsProInventoryEntity.class);
        proInventoryMapper.insertProInventory(proInventoryEntity,getSplitTableKey(dbShardVO));
        this.writeOperationLog(proInventoryEntity.getId(),proInventoryEntity.getCargoOwnerId(),ActionCode.Create.toString(),proInventoryEntity.getCreateUser()+"创建加工单据",OrderTypeCode.PROCESSING.toString(),OperationStatusCode.Finished.toString(),dbShardVO);
        return MessageResult.getSucMessage();
    }


    @Override
    @NeedLog(type = LogType.OPREATION)
    public MessageResult executeAuditProInventory(Long id, String operationUser, DbShardVO dbShardVO) {
        TWmsProInventoryEntity proInventoryEntity = proInventoryMapper.selectByPK(id,getSplitTableKey(dbShardVO));
        if(proInventoryEntity ==null){
            return MessageResult.getMessage("E12004");
        }
        TWmsInventoryEntity inventoryEntity = inventoryService.findInventoryFromCargoOwner(proInventoryEntity.getSkuId(),proInventoryEntity.getCargoOwnerId(),proInventoryEntity.getStorageRoomId(),dbShardVO);
        if(inventoryEntity.getIsHold() == 1){
            return MessageResult.getMessage("E12006");
        }
        List<TWmsProInventoryEntity> proInventoryEntityList = proInventoryMapper.selectByParentId(id,getSplitTableKey(dbShardVO));
        if(CollectionUtils.isEmpty(proInventoryEntityList)){
            return MessageResult.getMessage("E12005");
        }
        proInventoryEntityList.stream().forEach(x->{
            //走入库流程
            inventoryService.updateInventoryFromProInventory(x,operationUser,dbShardVO);
            x.setStatusCode(TicketStatusCode.REVIEWED.toString());
            x.setUpdateUser(operationUser);
            x.setUpdateTime(new Date().getTime());
            proInventoryMapper.updateProInventory(x,getSplitTableKey(dbShardVO));
        });
        inventoryService.updateInventoryFromProMaterial(inventoryEntity,proInventoryEntity,operationUser,dbShardVO);
        proInventoryEntity.setStatusCode(TicketStatusCode.REVIEWED.toString());
        proInventoryEntity.setUpdateUser(operationUser);
        proInventoryEntity.setUpdateTime(new Date().getTime());
        proInventoryMapper.updateProInventory(proInventoryEntity,getSplitTableKey(dbShardVO));
        this.writeOperationLog(id,proInventoryEntity.getCargoOwnerId(), ActionCode.AUDITED.toString(),operationUser+"审核通过加工单",OrderTypeCode.PROCESSING.toString(), OperationStatusCode.Finished.toString(),dbShardVO);
        return MessageResult.getSucMessage();
    }

    @Override
    @NeedLog(type = LogType.OPREATION)
    public MessageResult executeRevokeProInventory(Long id, String operationUser, DbShardVO dbShardVO) {
        TWmsProInventoryEntity proInventoryEntity = proInventoryMapper.selectByPK(id,getSplitTableKey(dbShardVO));
        if(proInventoryEntity ==null){
            return MessageResult.getMessage("E12101");
        }
        if(!proInventoryEntity.getStatusCode().equals(TicketStatusCode.SUBMIT.toString())){
            return MessageResult.getMessage("E12103");
        }
        TWmsInventoryEntity inventoryEntity = inventoryService.findInventoryFromCargoOwner(proInventoryEntity.getSkuId(),proInventoryEntity.getCargoOwnerId(),proInventoryEntity.getStorageRoomId(),dbShardVO);
        if(inventoryEntity.getIsHold() == 1){
            return MessageResult.getMessage("E12006");
        }
        List<TWmsProInventoryEntity> proInventoryEntityList = proInventoryMapper.selectByParentId(id,getSplitTableKey(dbShardVO));
        //修改明细的状态
        proInventoryEntityList.stream().forEach(x->{
            x.setStatusCode(TicketStatusCode.INVALID.toString());
            x.setUpdateTime(new Date().getTime());
            x.setUpdateUser(operationUser);
            proInventoryMapper.updateProInventory(x,getSplitTableKey(dbShardVO));
        });
        inventoryEntity.setWorkingQty(inventoryEntity.getWorkingQty() - proInventoryEntity.getQty());
        inventoryEntity.setUpdateTime(new Date().getTime());
        inventoryEntity.setUpdateUser(operationUser);
        inventoryService.modifyInventory(BeanUtils.copyBeanPropertyUtils(inventoryEntity,TWmsInventoryDTO.class),dbShardVO);
        proInventoryEntity.setStatusCode(TicketStatusCode.INVALID.toString());
        proInventoryEntity.setUpdateUser(operationUser);
        proInventoryEntity.setUpdateTime(new Date().getTime());
        proInventoryMapper.updateProInventory(proInventoryEntity,getSplitTableKey(dbShardVO));
        this.writeOperationLog(id,proInventoryEntity.getCargoOwnerId(), ActionCode.INVALID.toString(),
                operationUser+"将加工单撤销作废",OrderTypeCode.PROCESSING.toString(), OperationStatusCode.Finished.toString(),dbShardVO);
        return MessageResult.getSucMessage();
    }
}
