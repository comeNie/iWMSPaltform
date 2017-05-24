package com.huamengtong.wms.inwh.service.impl;

import com.huamengtong.wms.client.IAutoIdClient;
import com.huamengtong.wms.constants.AutoIdConstants;
import com.huamengtong.wms.core.annotation.NeedLog;
import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.service.BaseService;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.inventory.TWmsInventoryDTO;
import com.huamengtong.wms.dto.inwh.TWmsProPackageDTO;
import com.huamengtong.wms.dto.inwh.TWmsProPackageDetailDTO;
import com.huamengtong.wms.dto.sku.TWmsSkuCargoOwnerDTO;
import com.huamengtong.wms.em.ActionCode;
import com.huamengtong.wms.em.LogType;
import com.huamengtong.wms.em.OperationStatusCode;
import com.huamengtong.wms.em.OrderTypeCode;
import com.huamengtong.wms.em.TicketStatusCode;
import com.huamengtong.wms.entity.inventory.TWmsInventoryEntity;
import com.huamengtong.wms.entity.inwh.TWmsProPackageDetailEntity;
import com.huamengtong.wms.entity.inwh.TWmsProPackageEntity;
import com.huamengtong.wms.entity.sku.TWmsSkuCargoOwnerEntity;
import com.huamengtong.wms.entity.sku.TWmsSkuEntity;
import com.huamengtong.wms.inventory.service.IInventoryService;
import com.huamengtong.wms.inwh.mapper.ProPackageMapper;
import com.huamengtong.wms.inwh.service.IProPackageService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by mario on 2017/2/20.
 */
@Service
public class ProPackageService extends BaseService implements IProPackageService {

    @Autowired
    ProPackageMapper proPackageMapper;

    @Autowired
    ProPackageDetailService proPackageDetailService;

    @Autowired
    ISkuService skuService;

    @Autowired
    IInventoryService inventoryService;

    @Autowired
    IAutoIdClient autoIdClient;

    @Autowired
    ISkuCargoOwnerService skuCargoOwnerService;

    @Override
    public PageResponse<List<TWmsProPackageEntity>> getProPackage(TWmsProPackageDTO proPackageDTO, DbShardVO dbShardVO) {
        TWmsProPackageEntity proPackageEntity = BeanUtils.copyBeanPropertyUtils(proPackageDTO, TWmsProPackageEntity.class);
        List<TWmsProPackageEntity> proPackageEntityList = proPackageMapper.queryProPackagePages(proPackageEntity, getSplitTableKey(dbShardVO));
        if (CollectionUtils.isEmpty(proPackageEntityList)) {
            return null;
        }
        List<Long> skuIds = proPackageEntityList.stream().map(TWmsProPackageEntity::getSkuId).collect(Collectors.toList());
        List<TWmsSkuEntity> skuEntityList = skuService.findByIdList(skuIds,getSkuDbShareVO(dbShardVO));
        Map<Long,TWmsSkuEntity> skuList = new HashMap<>();
        skuEntityList.stream().forEach(x->skuList.put(x.getId(),x));

        proPackageEntityList.stream().forEach(x -> {
            TWmsSkuEntity skuEntity = (TWmsSkuEntity) MapUtils.getObject(skuList,x.getSkuId()) ;
            x.setSku(skuEntity.getSku());
            x.setSkuName(skuEntity.getItemName());
            x.setBarcode(skuEntity.getBarcode());
        });
        Integer totalSize = proPackageMapper.queryProPackagePageCount(proPackageEntity, getSplitTableKey(dbShardVO));
        PageResponse<List<TWmsProPackageEntity>> response = new PageResponse();
        response.setRows(proPackageEntityList);
        response.setTotal(totalSize);
        return response;
    }

    @Override
    public TWmsProPackageEntity findByPrimarkey(Long id, DbShardVO dbShardVO) {
        return proPackageMapper.selectByPrimarkey(id, getSplitTableKey(dbShardVO));
    }

    @Override
    public MessageResult removeProPackage(Long id, DbShardVO dbShardVO) {
        List<TWmsProPackageDetailEntity> proPackageDetailEntities = proPackageDetailService.getProPackageDetails(id, dbShardVO);
        if (CollectionUtils.isNotEmpty(proPackageDetailEntities)) {
            for (TWmsProPackageDetailEntity proPackageDetailEntity : proPackageDetailEntities) {
                proPackageDetailService.removeProPackageDetail(proPackageDetailEntity.getId(), dbShardVO);
            }
        }
        proPackageMapper.deleteProPackage(id, getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult modifyProPackage(TWmsProPackageDTO proPackageDTO, DbShardVO dbShardVO) {
        TWmsProPackageEntity proPackageEntity = BeanUtils.copyBeanPropertyUtils(proPackageDTO, TWmsProPackageEntity.class);
        proPackageMapper.updateProPackage(proPackageEntity, getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }

    /**
     * 包装单审核
     * @param id
     * @param operationUser
     * @param dbShardVO
     * @return
     */
    @Override
    @NeedLog(type = LogType.OPREATION)
    public MessageResult updateReviewProPackage(Long id, String operationUser, DbShardVO dbShardVO) {
        TWmsProPackageEntity proPackageEntity = proPackageMapper.selectByPrimarkey(id,getSplitTableKey(dbShardVO));
        if(proPackageEntity ==null){
            return MessageResult.getMessage("E12011");
        }
        List<TWmsProPackageDetailEntity> proPackageDetailEntityList = proPackageDetailService.getProPackageDetails(proPackageEntity.getId(), dbShardVO);
        if (CollectionUtils.isEmpty(proPackageDetailEntityList)) {
            return MessageResult.getMessage("E12012");
        }
        inventoryService.updateInventoryFromProPackage(proPackageEntity, proPackageDetailEntityList, operationUser, dbShardVO);
        proPackageEntity.setStatusCode(TicketStatusCode.REVIEWED.toString());
        proPackageEntity.setUpdateUser(operationUser);
        proPackageEntity.setUpdateTime(new Date().getTime());
        proPackageMapper.updateProPackage(proPackageEntity, getSplitTableKey(dbShardVO));
        this.writeOperationLog(id, proPackageEntity.getCargoOwnerId(), ActionCode.AUDITED.toString(), operationUser + "审核通过包装单", OrderTypeCode.PROPACKAGE.toString(), OperationStatusCode.Finished.toString(), dbShardVO);
        return MessageResult.getSucMessage();
    }

    //库存有效数量判断
    private Integer getActiveQty(TWmsInventoryEntity inventoryEntityTo) {
        return inventoryEntityTo.getOnhandQty() - inventoryEntityTo.getAllocatedQty() - inventoryEntityTo.getMortgagedQty() - inventoryEntityTo.getWorkingQty() - inventoryEntityTo.getPickedQty() - inventoryEntityTo.getPackageQty();
    }

    /**
     * 包装单新增
     * @param proPackageDTO
     * @param proPackageDetailDTOList
     * @param fuArrayList
     * @param dbShardVO
     * @return
     */
    @Override
    public MessageResult addProPackage(TWmsProPackageDTO proPackageDTO, List<Map> proPackageDetailDTOList, List<Map> fuArrayList, DbShardVO dbShardVO) {
        if (proPackageDTO.getSkuId() == 0 || proPackageDTO.getSkuId() == null) {
            return MessageResult.getMessage("E12015");
        }
        if (CollectionUtils.isEmpty(proPackageDetailDTOList)) {
            return MessageResult.getMessage("E12008");
        }
        if (CollectionUtils.isEmpty(fuArrayList)) {
            return MessageResult.getMessage("E12017");
        }

        //辅料信息与原料信息合并
        proPackageDetailDTOList.addAll(fuArrayList);
        //头表新增
        TWmsProPackageEntity proPackageEntity = BeanUtils.copyBeanPropertyUtils(proPackageDTO, TWmsProPackageEntity.class);
        proPackageEntity.setId(autoIdClient.getAutoId(AutoIdConstants.TWmsProPackageEntity));
        proPackageEntity.setSkuId(proPackageDTO.getSkuId());
        proPackageEntity.setCargoOwnerId(proPackageDTO.getCargoOwnerId());
        proPackageEntity.setStorageRoomId(proPackageDTO.getStorageRoomId());
        proPackageEntity.setQty(proPackageDTO.getQty());
        proPackageEntity.setWorkGroupNo(proPackageDTO.getWorkGroupNo());
        proPackageEntity.setSpec(proPackageDTO.getSpec());
        proPackageEntity.setStatusCode(TicketStatusCode.SUBMIT.toString());
        proPackageEntity.setCreateTime(new Date().getTime());
        proPackageEntity.setUpdateTime(new Date().getTime());
        //明细商品新增
        List<TWmsProPackageDetailDTO> detailList = new ArrayList<>();
        List<TWmsInventoryDTO> inventoryDTOList = new ArrayList<>();
        for (Map map : proPackageDetailDTOList) {
            TWmsProPackageDetailDTO packageDetailDTO = new TWmsProPackageDetailDTO();

            BeanUtils.transMapToBean(map, packageDetailDTO);

            TWmsInventoryEntity inventoryEntity = inventoryService.findById(packageDetailDTO.getProInventoryId(), dbShardVO);
            TWmsSkuEntity skuEntity = skuService.findByPrimaryKey(inventoryEntity.getSkuId(), getSkuDbShareVO(dbShardVO));

            if(proPackageEntity.getCargoOwnerId() != inventoryEntity.getCargoOwnerId()){
                return  MessageResult.getMessage("E12019", new Object[]{skuEntity.getItemName()});
            }
            if (inventoryEntity == null) {
                return MessageResult.getMessage("E12009");
            }
            if (inventoryEntity.getIsHold() == 1) {
                return MessageResult.getMessage("E12013", new Object[]{skuEntity.getItemName()});
            }
            Integer qty = getActiveQty(inventoryEntity);
            //可用数量判断
            if (qty <= 0) {
                return MessageResult.getMessage("E12014", new Object[]{skuEntity.getItemName()});
            }
            if (packageDetailDTO.getProInventoryQty() > qty) {
                return MessageResult.getMessage("E12016", new Object[]{skuEntity.getItemName()});
            }
            //更新库存中在包装数量
            inventoryEntity.setId(packageDetailDTO.getProInventoryId());
            inventoryEntity.setPackageQty(inventoryEntity.getPackageQty() + packageDetailDTO.getProInventoryQty());
            inventoryEntity.setUpdateUser(packageDetailDTO.getCreateUser());
            inventoryEntity.setUpdateTime(new Date().getTime());
            inventoryDTOList.add(BeanUtils.copyBeanPropertyUtils(inventoryEntity, TWmsInventoryDTO.class));
            //插入原材料以及包装成品明细
            packageDetailDTO.setId(autoIdClient.getAutoId(AutoIdConstants.TWmsProInventoryEntiy));
            packageDetailDTO.setParentId(proPackageEntity.getId());
            packageDetailDTO.setTenantId(proPackageEntity.getTenantId());
            packageDetailDTO.setWarehouseId(proPackageEntity.getWarehouseId());
            packageDetailDTO.setProStorageRoomId(packageDetailDTO.getProStorageRoomId());
            packageDetailDTO.setProInventoryId(packageDetailDTO.getProInventoryId());
            packageDetailDTO.setProInventoryQty(packageDetailDTO.getProInventoryQty());
            packageDetailDTO.setCreateUser(proPackageDTO.getCreateUser());
            packageDetailDTO.setCreateTime(new Date().getTime());
            packageDetailDTO.setUpdateUser(proPackageDTO.getUpdateUser());
            packageDetailDTO.setUpdateTime(new Date().getTime());
            detailList.add(packageDetailDTO);
        }
        //商品货主判断绑定
        TWmsSkuCargoOwnerEntity skuCargoOwnerEntity = skuCargoOwnerService.selectBySkuIdAndCargoOwnerId(proPackageDTO.getSkuId(), proPackageDTO.getCargoOwnerId(), getSkuDbShareVO(dbShardVO));
        if (skuCargoOwnerEntity == null) {
            TWmsSkuEntity skuEntity = skuService.findByPrimaryKey(proPackageDTO.getSkuId(), getSkuDbShareVO(dbShardVO));
            TWmsSkuCargoOwnerDTO skuCargoOwnerDTO = new TWmsSkuCargoOwnerDTO();
            skuCargoOwnerDTO.setCargoOwnerId(proPackageDTO.getCargoOwnerId());
            skuCargoOwnerDTO.setSkuId(proPackageDTO.getSkuId());
            skuCargoOwnerDTO.setSku(skuEntity.getSku());
            skuCargoOwnerDTO.setCargoOwnerBarcode(DateUtil.getNowDateTime().substring(0, 10).replaceAll("-", "").trim().concat(autoIdClient.getAutoId(AutoIdConstants.TWmsSkuCargoOwnerEntity, 1).toString()));
            skuCargoOwnerService.insertSkuCargoOwner(skuCargoOwnerDTO, getSkuDbShareVO(dbShardVO));
        }
        MessageResult mr = inventoryService.updateInventorys(inventoryDTOList,dbShardVO);
        if(mr.isSuc()){
            proPackageMapper.insertProPackage(proPackageEntity, getSplitTableKey(dbShardVO));
            detailList.stream().forEach(x->{
                proPackageDetailService.createProPackageDetail(x, dbShardVO);
            });
        }else {
            return MessageResult.getMessage("E12018");
        }
        return MessageResult.getSucMessage();
    }

    /**
     * 包装单作废
     * @param id
     * @param operationUser
     * @param dbShardVO
     * @return
     */
    @Override
    @NeedLog(type = LogType.OPREATION)
    public MessageResult updateInvalidProPackage(Long id, String operationUser, DbShardVO dbShardVO) {
        TWmsProPackageEntity proPackageEntity = proPackageMapper.selectByPrimarkey(id,getSplitTableKey(dbShardVO));
        if (proPackageEntity == null){
            return MessageResult.getMessage("E12020",new Object[]{id});
        }
        if (!proPackageEntity.getStatusCode().equals(TicketStatusCode.SUBMIT.toString())){
            return MessageResult.getMessage("E12021",new Object[]{id});
        }
        List<TWmsProPackageDetailEntity> proPackageDetailEntityList = proPackageDetailService.getProPackageDetails(id,dbShardVO);
        Long currentTime = new Date().getTime();
        List<TWmsInventoryDTO> inventoryDTOList = new ArrayList<>();
        for (TWmsProPackageDetailEntity proPackageDetailEntity:proPackageDetailEntityList){
            TWmsInventoryEntity inventoryEntity = inventoryService.findById(proPackageDetailEntity.getProInventoryId(),changeInwhDbShareVO(dbShardVO));
            //更新库存中在包装明细数量
            inventoryEntity.setId(proPackageDetailEntity.getProInventoryId());
            inventoryEntity.setPackageQty(inventoryEntity.getPackageQty() - proPackageDetailEntity.getProInventoryQty());
            inventoryEntity.setUpdateUser(proPackageDetailEntity.getCreateUser());
            inventoryEntity.setUpdateTime(new Date().getTime());
            inventoryDTOList.add(BeanUtils.copyBeanPropertyUtils(inventoryEntity, TWmsInventoryDTO.class));
            //更新库内包装明细修改人
            proPackageDetailEntity.setUpdateUser(operationUser);
            proPackageDetailEntity.setUpdateTime(currentTime);
            proPackageDetailService.modifyProPackageDetail(BeanUtils.copyBeanPropertyUtils(proPackageDetailEntity, TWmsProPackageDetailDTO.class),dbShardVO);
        }
        //更新库存中数据
        MessageResult mr = inventoryService.updateInventorys(inventoryDTOList,dbShardVO);
        if(!mr.isSuc()){
            return MessageResult.getMessage("E71018");
        }
        proPackageEntity.setStatusCode(TicketStatusCode.INVALID.toString());
        proPackageEntity.setUpdateUser(operationUser);
        proPackageEntity.setUpdateTime(currentTime);
        proPackageMapper.updateProPackage(proPackageEntity,getSplitTableKey(dbShardVO));
        this.writeOperationLog(id,proPackageEntity.getCargoOwnerId(), ActionCode.INVALID.toString(),operationUser + "作废包装单", OrderTypeCode.PROPACKAGE.toString(), OperationStatusCode.Finished.toString(), dbShardVO);
        return MessageResult.getSucMessage();

    }
}
