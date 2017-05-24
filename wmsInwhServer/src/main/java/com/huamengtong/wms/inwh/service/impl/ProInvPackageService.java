package com.huamengtong.wms.inwh.service.impl;

import com.huamengtong.wms.client.IAutoIdClient;
import com.huamengtong.wms.constants.AutoIdConstants;
import com.huamengtong.wms.core.annotation.NeedLog;
import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.service.BaseService;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.inventory.TWmsInventoryDTO;
import com.huamengtong.wms.dto.inwh.TWmsProInvPackageDTO;
import com.huamengtong.wms.dto.inwh.TWmsProInvPackageDetailDTO;
import com.huamengtong.wms.dto.sku.TWmsSkuCargoOwnerDTO;
import com.huamengtong.wms.em.LogType;
import com.huamengtong.wms.em.MachiningType;
import com.huamengtong.wms.em.OrderTypeCode;
import com.huamengtong.wms.em.TicketStatusCode;
import com.huamengtong.wms.entity.inventory.TWmsInventoryEntity;
import com.huamengtong.wms.entity.inwh.TWmsProInvPackageDetailEntity;
import com.huamengtong.wms.entity.inwh.TWmsProInvPackageEntity;
import com.huamengtong.wms.entity.sku.TWmsSkuCargoOwnerEntity;
import com.huamengtong.wms.entity.sku.TWmsSkuEntity;
import com.huamengtong.wms.inventory.service.IInventoryService;
import com.huamengtong.wms.inwh.mapper.ProInvPackageMapper;
import com.huamengtong.wms.inwh.service.IProInvPackageDetailService;
import com.huamengtong.wms.inwh.service.IProInvPackageService;
import com.huamengtong.wms.sku.service.ISkuCargoOwnerService;
import com.huamengtong.wms.sku.service.ISkuService;
import com.huamengtong.wms.utils.BeanUtils;
import com.huamengtong.wms.utils.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by mario on 2017/3/28.
 */
@Service
public class ProInvPackageService extends BaseService implements IProInvPackageService {
    @Autowired
    ProInvPackageMapper proInvPackageMapper;

    @Autowired
    IAutoIdClient autoIdClient;

    @Autowired
    IProInvPackageDetailService proInvPackageDetailService;

    @Autowired
    IInventoryService inventoryService;

    @Autowired
    ISkuCargoOwnerService skuCargoOwnerService;

    @Autowired
    ISkuService skuService;

    @Override
    public PageResponse<List<TWmsProInvPackageEntity>> getProInvPackage(TWmsProInvPackageDTO proInvPackageDTO, DbShardVO dbShardVO) {
        TWmsProInvPackageEntity proInvPackageEntity = BeanUtils.copyBeanPropertyUtils(proInvPackageDTO, TWmsProInvPackageEntity.class);
        List<TWmsProInvPackageEntity> proInvPackageEntityList = proInvPackageMapper.queryProInvPackagePages(proInvPackageEntity, getSplitTableKey(dbShardVO));
        Integer totalSize = proInvPackageMapper.queryProInvPackagePageCount(proInvPackageEntity, getSplitTableKey(dbShardVO));
        PageResponse<List<TWmsProInvPackageEntity>> response = new PageResponse<>();
        response.setRows(proInvPackageEntityList);
        response.setTotal(totalSize);
        return response;
    }

    @Override
    public TWmsProInvPackageEntity findByPrimaryKey(Long id, DbShardVO dbShardVO) {
        return proInvPackageMapper.selectByPrimarykey(id, getSplitTableKey(dbShardVO));
    }

    @Override
    public MessageResult createProInvPackageHeader(TWmsProInvPackageDTO proInvPackageDTO, DbShardVO dbShardVO) {
        TWmsProInvPackageEntity proInvPackageEntity = BeanUtils.copyBeanPropertyUtils(proInvPackageDTO, TWmsProInvPackageEntity.class);
        proInvPackageMapper.insertProInvPackage(proInvPackageEntity, getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult modifyProInvPackageHeader(TWmsProInvPackageDTO proInvPackageDTO, DbShardVO dbShardVO) {
        TWmsProInvPackageEntity proInvPackageEntity = BeanUtils.copyBeanPropertyUtils(proInvPackageDTO, TWmsProInvPackageEntity.class);
        proInvPackageMapper.updateProInvPackage(proInvPackageEntity, getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }

    /**
     * 添加头及明细信息
     *
     * @param map
     * @param logUser
     * @param tenantId
     * @param warehouseId
     * @param dbShardVO
     * @return
     */
    @Override
    public MessageResult addProInvPackage(Map map, String logUser, Long tenantId, Long warehouseId, DbShardVO dbShardVO) {
        TWmsProInvPackageDTO proInvPackageDTO = new TWmsProInvPackageDTO();
        List<TWmsProInvPackageDetailDTO> proInvPackageDetailDTOList = new ArrayList<>();
        if (map.containsKey("header")) {
            BeanUtils.transMapToBean(MapUtils.getMap(map, "header"), proInvPackageDTO);
            if (proInvPackageDTO == null) {
                return MessageResult.getMessage("E12022");
            }
        } else {
            return MessageResult.getMessage("E12022");
        }
        proInvPackageDTO.setId(autoIdClient.getAutoId(AutoIdConstants.TWmsProInvPackageEntity));
        proInvPackageDTO.setStatusCode(TicketStatusCode.INIT.toString());
        proInvPackageDTO.setTenantId(tenantId);
        proInvPackageDTO.setWarehouseId(warehouseId);
        proInvPackageDTO.setUpdateTime(new Date().getTime());
        proInvPackageDTO.setUpdateUser(logUser);
        proInvPackageDTO.setCreateTime(new Date().getTime());
        proInvPackageDTO.setCreateUser(logUser);
        TWmsProInvPackageEntity proInvPackageEntity = BeanUtils.copyBeanPropertyUtils(proInvPackageDTO, TWmsProInvPackageEntity.class);
        List<TWmsInventoryEntity> inventoryEntities = new ArrayList<>();
        if (map.containsKey("newFuTableArray")) {
            List<Map> proInvPackageDetailDTOs = ((List<Map>) map.get("newFuTableArray"));
            detailInstall(MachiningType.OUTPUT.toString(), proInvPackageDetailDTOList, proInvPackageDetailDTOs, inventoryEntities,
                    proInvPackageEntity, dbShardVO);
        }
        if (map.containsKey("newProTableArray")) {
            List<Map> proInvPackageDetailDTOs = ((List<Map>) map.get("newProTableArray"));
            if (CollectionUtils.isEmpty(proInvPackageDetailDTOs)) {
                return MessageResult.getMessage("E12023");
            }
            detailInstall(MachiningType.OUTPUT.toString(), proInvPackageDetailDTOList, proInvPackageDetailDTOs, inventoryEntities,
                    proInvPackageEntity, dbShardVO);
        } else {
            return MessageResult.getMessage("E12023");
        }
        if (map.containsKey("oldFuTableArray")) {
            List<Map> proInvPackageDetailDTOs = ((List<Map>) map.get("oldFuTableArray"));
            detailInstall(MachiningType.CONSUME.toString(), proInvPackageDetailDTOList, proInvPackageDetailDTOs, inventoryEntities,
                    proInvPackageEntity, dbShardVO);
        }
        if (map.containsKey("oldProTableArray")) {
            List<Map> proInvPackageDetailDTOs = ((List<Map>) map.get("oldProTableArray"));
            if (CollectionUtils.isEmpty(proInvPackageDetailDTOs)) {
                return MessageResult.getMessage("E12024");
            }
            detailInstall(MachiningType.CONSUME.toString(), proInvPackageDetailDTOList, proInvPackageDetailDTOs, inventoryEntities,
                    proInvPackageEntity, dbShardVO);
        } else {
            return MessageResult.getMessage("E12024");
        }
        inventoryService.batchUpdateFromSubmitFrozen(inventoryEntities, changeInwhDbShareVO(dbShardVO));
        proInvPackageMapper.insertProInvPackage(proInvPackageEntity, getSplitTableKey(dbShardVO));
        proInvPackageDetailService.createProInvPackageDetailBatch(proInvPackageDetailDTOList, dbShardVO);
        return MessageResult.getSucMessage();
    }

    /**
     * 判断组装明细的方法
     *
     * @param typeCode
     * @param proInvPackageDetailDTOList
     * @param proInvPackageDetailDTOs
     * @param inventoryEntities
     * @param proInvPackageEntity
     * @param dbShardVO
     */
    private void detailInstall(String typeCode, List<TWmsProInvPackageDetailDTO> proInvPackageDetailDTOList, List<Map> proInvPackageDetailDTOs,
                               List<TWmsInventoryEntity> inventoryEntities, TWmsProInvPackageEntity proInvPackageEntity, DbShardVO dbShardVO) {
        if (CollectionUtils.isNotEmpty(proInvPackageDetailDTOs)) {
            for (Map map : proInvPackageDetailDTOs) {
                TWmsProInvPackageDetailDTO dto = new TWmsProInvPackageDetailDTO();
                BeanUtils.transMapToBean(map, dto);
                if (typeCode.equals(MachiningType.CONSUME.toString())) {
                    dto.setInvPackageQty(-dto.getInvPackageQty());//消耗数量记为负数
                    TWmsInventoryEntity inventoryEntity = inventoryService.findInventoryFromCargoOwner(dto.getInvPackageId(), proInvPackageEntity.getCargoOwnerId(), dto.getStorageRoomId(), changeInwhDbShareVO(dbShardVO));
                    inventoryEntity.setWorkingQty(inventoryEntity.getWorkingQty() - dto.getInvPackageQty());
                    inventoryEntities.add(inventoryEntity);
                }
                dto.setTypeCode(typeCode);
                dto.setParentId(proInvPackageEntity.getId());
                dto.setTenantId(proInvPackageEntity.getTenantId());
                dto.setWarehouseId(proInvPackageEntity.getWarehouseId());
                dto.setCreateUser(proInvPackageEntity.getCreateUser());
                dto.setCreateTime(new Date().getTime());
                dto.setUpdateUser(proInvPackageEntity.getCreateUser());
                dto.setUpdateTime(new Date().getTime());
                proInvPackageDetailDTOList.add(dto);
            }
        }
    }


    /**
     * 确认
     * @param headerId
     * @param logUser
     * @param dbShardVO
     * @return
     */
    @Override
    @NeedLog(type = {LogType.INVENTORY,LogType.OPREATION})
    public MessageResult updateReviewProInvPackage(Long headerId, String logUser, DbShardVO dbShardVO) {
        TWmsProInvPackageEntity proInvPackageEntity = proInvPackageMapper.selectByPrimarykey(headerId, getSplitTableKey(dbShardVO));
        List<TWmsProInvPackageDetailEntity> detailEntityList = proInvPackageDetailService.getProInvPackageDetails(headerId, dbShardVO);
        List<TWmsInventoryDTO> updateList = new ArrayList<>();
        List<TWmsInventoryDTO> addList = new ArrayList<>();
        for (TWmsProInvPackageDetailEntity entity : detailEntityList) {
            TWmsInventoryEntity inventoryEntity = new TWmsInventoryEntity();
            if (entity.getTypeCode().equals(MachiningType.CONSUME.toString())) {
                inventoryEntity = inventoryService.findInventoryFromCargoOwner(
                        entity.getInvPackageId(), proInvPackageEntity.getCargoOwnerId(), entity.getStorageRoomId(), changeInwhDbShareVO(dbShardVO));
                for (TWmsInventoryDTO inventoryDTO : updateList) {
                    if (inventoryDTO.getId().equals(inventoryEntity.getId())) {
                        inventoryEntity = BeanUtils.copyBeanPropertyUtils(inventoryDTO, TWmsInventoryEntity.class);
                    }
                }
                inventoryEntity.setWorkingQty(inventoryEntity.getWorkingQty() + entity.getInvPackageQty());
                inventoryEntity.setOnhandQty(inventoryEntity.getOnhandQty() + entity.getInvPackageQty());
                inventoryEntity.setUpdateTime(new Date().getTime());
                inventoryEntity.setUpdateUser(logUser);
                TWmsInventoryDTO inventoryDTO = BeanUtils.copyBeanPropertyUtils(inventoryEntity, TWmsInventoryDTO.class);
                updateList.add(inventoryDTO);
            } else {
                inventoryEntity = inventoryService.findInventoryFromCargoOwner(
                        entity.getInvPackageId(), proInvPackageEntity.getCargoOwnerId(), entity.getStorageRoomId(), changeInwhDbShareVO(dbShardVO));
                if (inventoryEntity == null) {
                    TWmsSkuCargoOwnerEntity skuCargoOwnerEntity = skuCargoOwnerService.selectBySkuIdAndCargoOwnerId(
                            entity.getInvPackageId(),proInvPackageEntity.getCargoOwnerId(),getSkuDbShareVO(dbShardVO));
                    if(skuCargoOwnerEntity == null){
                        TWmsSkuEntity skuEntity = skuService.findByPrimaryKey(entity.getInvPackageId(),getSkuDbShareVO(dbShardVO));
                        TWmsSkuCargoOwnerDTO skuCargoOwnerDTO = new TWmsSkuCargoOwnerDTO();
                        skuCargoOwnerDTO.setCargoOwnerId(proInvPackageEntity.getCargoOwnerId());
                        skuCargoOwnerDTO.setSkuId(entity.getInvPackageId());
                        skuCargoOwnerDTO.setSku(skuEntity.getSku());
                        skuCargoOwnerDTO.setCargoOwnerBarcode(DateUtil.getNowDateTime().substring(0,10).replaceAll("-","").trim().concat(autoIdClient.getAutoId(AutoIdConstants.TWmsSkuCargoOwnerEntity,1).toString()));
                        skuCargoOwnerService.insertSkuCargoOwner(skuCargoOwnerDTO,getSkuDbShareVO(dbShardVO));
                    }
                    inventoryEntity = new TWmsInventoryEntity();
                    inventoryEntity.setId(autoIdClient.getAutoId(AutoIdConstants.TWmsInventoryEntity));
                    inventoryEntity.setTenantId(entity.getTenantId());
                    inventoryEntity.setWarehouseId(entity.getWarehouseId());
                    inventoryEntity.setSkuId(entity.getInvPackageId());
                    inventoryEntity.setCargoOwnerId(proInvPackageEntity.getCargoOwnerId());
                    inventoryEntity.setStorageRoomId(entity.getStorageRoomId());
                    inventoryEntity.setOnhandQty(entity.getInvPackageQty());
                    inventoryEntity.setUpdateUser(logUser);
                    inventoryEntity.setUpdateTime(new Date().getTime());
                    inventoryEntity.setCreateTime(new Date().getTime());
                    inventoryEntity.setCreateUser(logUser);
                    TWmsInventoryDTO inventoryDTO = BeanUtils.copyBeanPropertyUtils(inventoryEntity, TWmsInventoryDTO.class);
                    addList.add(inventoryDTO);
                } else {
                    for (TWmsInventoryDTO inventoryDTO : updateList) {
                        if (inventoryDTO.getId().equals(inventoryEntity.getId())) {
                            inventoryEntity = BeanUtils.copyBeanPropertyUtils(inventoryDTO, TWmsInventoryEntity.class);
                        }
                    }
                    inventoryEntity.setOnhandQty(inventoryEntity.getOnhandQty() + entity.getInvPackageQty());
                    inventoryEntity.setUpdateTime(new Date().getTime());
                    inventoryEntity.setUpdateUser(logUser);
                    TWmsInventoryDTO inventoryDTO = BeanUtils.copyBeanPropertyUtils(inventoryEntity, TWmsInventoryDTO.class);
                    updateList.add(inventoryDTO);
                }
                this.writeInventoryLog(inventoryEntity.getId(),inventoryEntity.getSkuId(), OrderTypeCode.INVPROPACKAGE.toString(),
                        headerId,entity.getId(),entity.getInvPackageQty(),changeInwhDbShareVO(dbShardVO));
            }
        }
        inventoryService.updateInventorys(updateList, changeInwhDbShareVO(dbShardVO));
        inventoryService.addInventory(addList, dbShardVO);
        proInvPackageEntity.setStatusCode(TicketStatusCode.FINISH.toString());
        proInvPackageMapper.updateProInvPackage(proInvPackageEntity, getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }

    /**
     * 作废
     *
     * @param headerId
     * @param logUser
     * @param dbShardVO
     * @return
     */
    @Override
    public MessageResult updateInvalidProInvPackage(Long headerId, String logUser, DbShardVO dbShardVO) {
        TWmsProInvPackageEntity proInvPackageEntity = proInvPackageMapper.selectByPrimarykey(headerId, getSplitTableKey(dbShardVO));
        List<TWmsProInvPackageDetailEntity> detailEntityList = proInvPackageDetailService.getProInvPackageDetails(headerId, dbShardVO);
        List<TWmsInventoryDTO> updateList = new ArrayList<>();
        for (TWmsProInvPackageDetailEntity entity : detailEntityList) {
            if (entity.getTypeCode().equals(MachiningType.CONSUME.toString())) {
                TWmsInventoryEntity inventoryEntity = inventoryService.findInventoryFromCargoOwner(
                        entity.getInvPackageId(), proInvPackageEntity.getCargoOwnerId(), entity.getStorageRoomId(), changeInwhDbShareVO(dbShardVO));
                inventoryEntity.setWorkingQty(inventoryEntity.getWorkingQty() + entity.getInvPackageQty());
                inventoryEntity.setUpdateTime(new Date().getTime());
                inventoryEntity.setUpdateUser(logUser);
                updateList.add(BeanUtils.copyBeanPropertyUtils(inventoryEntity,TWmsInventoryDTO.class));
            }
        }
        inventoryService.updateInventorys(updateList,changeInwhDbShareVO(dbShardVO));
        proInvPackageMapper.updateInvalidProInvPackage(headerId, TicketStatusCode.INVALID.toString(), getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }
}
