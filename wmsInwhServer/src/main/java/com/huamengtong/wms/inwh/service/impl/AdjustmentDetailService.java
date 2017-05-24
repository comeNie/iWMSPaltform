package com.huamengtong.wms.inwh.service.impl;

import com.huamengtong.wms.client.IAutoIdClient;
import com.huamengtong.wms.constants.AutoIdConstants;
import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.service.BaseService;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.inventory.TWmsInventoryDTO;
import com.huamengtong.wms.dto.inwh.TWmsAdjustmentDetailDTO;
import com.huamengtong.wms.em.TicketStatusCode;
import com.huamengtong.wms.entity.inventory.TWmsInventoryEntity;
import com.huamengtong.wms.entity.inwh.TWmsAdjustmentDetailEntity;
import com.huamengtong.wms.entity.inwh.TWmsAdjustmentHeaderEntity;
import com.huamengtong.wms.entity.sku.TWmsSkuEntity;
import com.huamengtong.wms.inventory.service.IInventoryService;
import com.huamengtong.wms.inwh.mapper.AdjustmentDetailMapper;
import com.huamengtong.wms.inwh.service.IAdjustmentDetailService;
import com.huamengtong.wms.inwh.service.IAdjustmentHeaderService;
import com.huamengtong.wms.sku.service.ISkuService;
import com.huamengtong.wms.utils.BeanUtils;
import com.huamengtong.wms.vo.SkuInventoryVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by mario on 2016/11/17.
 */
@Service
public class AdjustmentDetailService extends BaseService implements IAdjustmentDetailService {

    @Autowired
    IAutoIdClient autoIdClient;

    @Autowired
    AdjustmentDetailMapper adjustmentDetailMapper;

    @Autowired
    IAdjustmentHeaderService adjustmentHeaderService;

    @Autowired
    IInventoryService inventoryService;

    @Autowired
    ISkuService skuService;



    @Override
    public TWmsAdjustmentDetailEntity findByPrimaryKey(Long id, DbShardVO dbShardVO) {

        return adjustmentDetailMapper.selectByPrimaryKey(id,getSplitTableKey(dbShardVO));
    }

    @Override
    public MessageResult createAdjustmentDetail(TWmsAdjustmentDetailDTO adjustmentDetailDTO, DbShardVO dbShardVO) {
       if (adjustmentDetailDTO.getSkuId() == null ||  adjustmentDetailDTO.getSkuId() == 0){
            return MessageResult.getMessage("E40003");
       }
       if (adjustmentDetailDTO.getStorageRoomId() == null || adjustmentDetailDTO.getStorageRoomId() == 0) {
            return MessageResult.getMessage("E70011");
       }
       if (adjustmentDetailDTO.getId() == 0 || adjustmentDetailDTO.getId() == null ){
           adjustmentDetailDTO.setId(autoIdClient.getAutoId(AutoIdConstants.TWmsAdjustmentDetailEntity));
       }
       TWmsAdjustmentHeaderEntity adjustmentHeaderEntity = adjustmentHeaderService.findByPrimaryKey(adjustmentDetailDTO.getAdjustId(), dbShardVO);
       TWmsInventoryEntity inventoryEntity = inventoryService.findInventoryFromCargoOwner(adjustmentDetailDTO.getSkuId(), adjustmentHeaderEntity.getCargoOwnerId(), adjustmentDetailDTO.getStorageRoomId(), dbShardVO);
       if(inventoryEntity.getIsHold() == 1){
            return MessageResult.getMessage("E80007");
       }
       inventoryEntity.setIsHold(new Byte("1"));
       inventoryService.modifyInventory(BeanUtils.copyBeanPropertyUtils(inventoryEntity,TWmsInventoryDTO.class),dbShardVO);
       TWmsAdjustmentDetailEntity adjustmentDetailEntity= BeanUtils.copyBeanPropertyUtils(adjustmentDetailDTO,TWmsAdjustmentDetailEntity.class);
       adjustmentDetailEntity.setStatusCode(TicketStatusCode.INIT.toString());
       adjustmentDetailMapper.insertAdjustmentDetail(adjustmentDetailEntity,getSplitTableKey(dbShardVO));
       return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult removeAdjustmentDetail(Long id, DbShardVO dbShardVO) {
        TWmsAdjustmentDetailEntity adjustmentDetailEntity = adjustmentDetailMapper.selectByPrimaryKey(id,getSplitTableKey(dbShardVO));
        TWmsAdjustmentHeaderEntity adjustmentHeaderEntity = adjustmentHeaderService.findByPrimaryKey(adjustmentDetailEntity.getAdjustId(), dbShardVO);
        TWmsInventoryEntity inventoryEntity = inventoryService.findInventoryFromCargoOwner(adjustmentDetailEntity.getSkuId(), adjustmentHeaderEntity.getCargoOwnerId(), adjustmentDetailEntity.getStorageRoomId(), dbShardVO);
        inventoryEntity.setIsHold(new Byte("0"));
        inventoryService.modifyInventory(BeanUtils.copyBeanPropertyUtils(inventoryEntity,TWmsInventoryDTO.class), dbShardVO);
        adjustmentDetailMapper.deleteAdjustmentDetail(id,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult modifyAdjustmentDetail(TWmsAdjustmentDetailDTO adjustmentDetailDTO, DbShardVO dbShardVO) {
        TWmsAdjustmentDetailEntity adjustmentDetailEntity= BeanUtils.copyBeanPropertyUtils(adjustmentDetailDTO,TWmsAdjustmentDetailEntity.class);
        adjustmentDetailMapper.updateAdjustmentDetail(adjustmentDetailEntity,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }

    @Override
    public PageResponse<List> queryAdjustmentDetailByHeader(Map paraMap, DbShardVO dbShardVO) {
       paraMap.put("splitTableKey",getSplitTableKey(dbShardVO));
        List<TWmsAdjustmentDetailEntity> mapList = adjustmentDetailMapper.queryDetailPages(paraMap);
        if(CollectionUtils.isEmpty(mapList)){
            return null;
        }
        List<TWmsAdjustmentDetailEntity> adjustmentDetailEntityList = new ArrayList<>();
        for (TWmsAdjustmentDetailEntity x:mapList){
            TWmsSkuEntity skuEntity = skuService.findByPrimaryKey(x.getSkuId(),getSkuDbShareVO(dbShardVO));
            if (skuEntity != null){
                x.setSku(skuEntity.getSku());
                x.setSkuName(skuEntity.getItemName());
                x.setBarcode(skuEntity.getBarcode());
            }
            adjustmentDetailEntityList.add(x);
        }
        Integer totalSize = adjustmentDetailMapper.queryDetailPageCount(paraMap);
        PageResponse<List> response = new PageResponse();
        response.setRows(adjustmentDetailEntityList);
        response.setTotal(totalSize);
        return response;
    }

    @Override
    public List<TWmsAdjustmentDetailEntity> getAdjustmentDetails(Long adjustId, DbShardVO dbShardVO) {
       List<TWmsAdjustmentDetailEntity> mapList = adjustmentDetailMapper.queryAdjustmentDetails(adjustId,getSplitTableKey(dbShardVO));
       if (CollectionUtils.isNotEmpty(mapList)) {
            return mapList;
       }
       return null;
    }


    @Override
    public PageResponse<List<SkuInventoryVO>> getSkuInventoryList(Map map, DbShardVO dbShardVO) {
        Long cargoOwnerId = MapUtils.getLong(map,"cargoOwnerId");
        Long storageRoomId = MapUtils.getLong(map,"storageRoomId");
        List<SkuInventoryVO> skuInventoryVOs = new ArrayList<SkuInventoryVO>();
        TWmsInventoryDTO inventoryDTO = new TWmsInventoryDTO();
        inventoryDTO.setCargoOwnerId(cargoOwnerId);
        inventoryDTO.setStorageRoomId(storageRoomId);
        inventoryDTO.setIsHold(new Byte("0"));
        inventoryDTO.setPage(MapUtils.getInteger(map,"page"));
        inventoryDTO.setPageSize(MapUtils.getInteger(map,"pageSize"));
        inventoryDTO.setOffset(MapUtils.getInteger(map,"offset"));
        if(map.containsKey("sku") && StringUtils.isNotEmpty(MapUtils.getString(map,"sku"))){
            List<TWmsSkuEntity> skuEntityList = skuService.findSkuCargoOwner(MapUtils.getString(map,"sku"),null,cargoOwnerId,null,getSkuDbShareVO(dbShardVO));
            if(CollectionUtils.isEmpty(skuEntityList)){
                return null;
            }
            TWmsSkuEntity skuEntity = skuEntityList.get(0);
            //此时只能取出一条记录，list size为1 直接使用
            inventoryDTO.setSkuId(skuEntity.getId());
            TWmsInventoryEntity inventoryEntity = inventoryService.findInventoryFromCargoOwner(skuEntity.getId(),cargoOwnerId,storageRoomId,dbShardVO);
            SkuInventoryVO skuInventoryVO = new SkuInventoryVO();
            skuInventoryVO.setCargoOwnerId(inventoryEntity.getCargoOwnerId());
            skuInventoryVO.setSkuId(inventoryEntity.getSkuId());

            //skuInventoryVO.setOnhandQty(inventoryEntity.getOnhandQty() -inventoryEntity. getAllocatedQty() - inventoryEntity.getMortgagedQty()-inventoryEntity.getWorkingQty()-inventoryEntity.getPickedQty());
            int qty = getActiveQty(inventoryEntity);
            skuInventoryVO.setOnhandQty(qty);
            skuInventoryVO.setStorageRoomId(inventoryEntity.getStorageRoomId());
            skuInventoryVO.setItemName(skuEntity.getItemName());
            skuInventoryVO.setSku(skuEntity.getSku());
            skuInventoryVO.setBarcode(skuEntity.getBarcode());
            skuInventoryVOs.add(skuInventoryVO);
        } else if (map.containsKey("itemName") && StringUtils.isNotEmpty(MapUtils.getString(map,"itemName"))){
            List<TWmsSkuEntity> skuEntities = skuService.findSkuCargoOwner(null,MapUtils.getString(map,"itemName"),cargoOwnerId,null,getSkuDbShareVO(dbShardVO));

            for (TWmsSkuEntity skuEntity:skuEntities){
                TWmsInventoryEntity inventoryEntity = inventoryService.findInventoryFromCargoOwner(skuEntity.getId(),cargoOwnerId,storageRoomId,dbShardVO);
                if(inventoryEntity == null){
                  continue;
                }
               SkuInventoryVO skuInventoryVO = new SkuInventoryVO();
               skuInventoryVO.setCargoOwnerId(inventoryEntity.getCargoOwnerId());
               skuInventoryVO.setSkuId(inventoryEntity.getSkuId());

               //skuInventoryVO.setOnhandQty(inventoryEntity.getOnhandQty() -inventoryEntity. getAllocatedQty() - inventoryEntity.getMortgagedQty()-inventoryEntity.getWorkingQty());
               int qty = getActiveQty(inventoryEntity);
               skuInventoryVO.setOnhandQty(qty);
               skuInventoryVO.setStorageRoomId(inventoryEntity.getStorageRoomId());
               skuInventoryVO.setItemName(skuEntity.getItemName());
               skuInventoryVO.setSku(skuEntity.getSku());
               skuInventoryVO.setBarcode(skuEntity.getBarcode());
               skuInventoryVOs.add(skuInventoryVO);
            }
        } else {
            List<TWmsInventoryEntity> inventoryEntities = inventoryService.findInventoryHold(cargoOwnerId,storageRoomId,dbShardVO);
            if(CollectionUtils.isEmpty(inventoryEntities)){
                return null;
            }
            for (TWmsInventoryEntity inventoryEntity:inventoryEntities){
                List<TWmsSkuEntity> skuEntityList = skuService.findSkuCargoOwner(null,null,cargoOwnerId,inventoryEntity.getSkuId(),getSkuDbShareVO(dbShardVO));
                if(CollectionUtils.isEmpty(skuEntityList)){
                    continue;
                }
                SkuInventoryVO skuInventoryVO = new SkuInventoryVO();
                skuInventoryVO.setCargoOwnerId(inventoryEntity.getCargoOwnerId());
                skuInventoryVO.setSkuId(inventoryEntity.getSkuId());
                //skuInventoryVO.setOnhandQty(inventoryEntity.getOnhandQty() -inventoryEntity. getAllocatedQty() - inventoryEntity.getMortgagedQty()-inventoryEntity.getWorkingQty()-inventoryEntity.getPickedQty());
                int qty = getActiveQty(inventoryEntity);
                skuInventoryVO.setOnhandQty(qty);
                skuInventoryVO.setStorageRoomId(inventoryEntity.getStorageRoomId());
                skuInventoryVO.setItemName(skuEntityList.get(0).getItemName());
                skuInventoryVO.setSku(skuEntityList.get(0).getSku());
                skuInventoryVO.setBarcode(skuEntityList.get(0).getBarcode());
                skuInventoryVOs.add(skuInventoryVO);
            }
        }
        PageResponse<List<SkuInventoryVO>> response = new PageResponse<>();
        response.setTotal(inventoryService.queryInventoryPageCount(inventoryDTO,dbShardVO));
        response.setRows(skuInventoryVOs);
        return response;
    }

    @Override
    public MessageResult createAdjustmentDetailByMove(TWmsAdjustmentDetailDTO adjustmentDetailDTO, DbShardVO dbShardVO) {
        TWmsAdjustmentDetailEntity adjustmentDetailEntity= BeanUtils.copyBeanPropertyUtils(adjustmentDetailDTO,TWmsAdjustmentDetailEntity.class);
        adjustmentDetailEntity.setStatusCode(TicketStatusCode.INIT.toString());
        adjustmentDetailMapper.insertAdjustmentDetailByMove(adjustmentDetailEntity,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }

    private Integer getActiveQty(TWmsInventoryEntity inventoryEntity){
        return  inventoryEntity.getOnhandQty() -inventoryEntity. getAllocatedQty() - inventoryEntity.getMortgagedQty()-inventoryEntity.getWorkingQty()-inventoryEntity.getPickedQty()-inventoryEntity.getPackageQty();
    }
}
