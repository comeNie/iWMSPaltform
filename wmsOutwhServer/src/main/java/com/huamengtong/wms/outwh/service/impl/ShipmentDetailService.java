package com.huamengtong.wms.outwh.service.impl;

import com.huamengtong.wms.client.IAutoIdClient;
import com.huamengtong.wms.constants.AutoIdConstants;
import com.huamengtong.wms.core.annotation.NeedLog;
import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.service.BaseService;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.outwh.TWmsShipmentDetailDTO;
import com.huamengtong.wms.dto.outwh.TWmsShipmentHeaderDTO;
import com.huamengtong.wms.em.ActionCode;
import com.huamengtong.wms.em.LogType;
import com.huamengtong.wms.em.OperationStatusCode;
import com.huamengtong.wms.em.OrderTypeCode;
import com.huamengtong.wms.entity.outwh.TWmsShipmentDetailEntity;
import com.huamengtong.wms.entity.outwh.TWmsShipmentHeaderEntity;
import com.huamengtong.wms.entity.sku.TWmsSkuEntity;
import com.huamengtong.wms.outwh.mapper.ShipmentDetailMapper;
import com.huamengtong.wms.outwh.service.IShipmentDetailService;
import com.huamengtong.wms.sku.service.ISkuService;
import com.huamengtong.wms.utils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mario on 2016/10/31.
 */
@Service
public class ShipmentDetailService  extends BaseService implements IShipmentDetailService {

    @Autowired
    ShipmentDetailMapper shipmentDetailMapper;

    @Autowired
    IAutoIdClient autoIdClient;

    @Autowired
    ShipmentHeaderService shipmentHeaderService;

    @Autowired
    ISkuService skuService;


    @Override
    public TWmsShipmentDetailEntity findByPrimaryKey(Long id, DbShardVO dbShardVO) {
        return shipmentDetailMapper.selectByPrimaryKey(id,getSplitTableKey(dbShardVO));
    }

    @Override
    public MessageResult createShipmentDetail(TWmsShipmentDetailDTO shipmentDetailDTO, DbShardVO dbShardVO) {
        TWmsShipmentDetailEntity shipmentDetailEntity = BeanUtils.copyBeanPropertyUtils(shipmentDetailDTO,TWmsShipmentDetailEntity.class);
        TWmsShipmentHeaderEntity shipmentHeaderEntity = shipmentHeaderService.findByPrimaryKey(shipmentDetailEntity.getShipmentId(),dbShardVO);
        shipmentDetailEntity.setId(autoIdClient.getAutoId(AutoIdConstants.TwmsShipmentDetailEntity));
        shipmentDetailMapper.insertShipmentDetail(shipmentDetailEntity,getSplitTableKey(dbShardVO));
        shipmentHeaderEntity.setTotalCategoryQty(shipmentHeaderEntity.getTotalCategoryQty()+1);
        shipmentHeaderEntity.setTotalQty(shipmentHeaderEntity.getTotalQty()+shipmentDetailEntity.getOrderedQty());
        TWmsShipmentHeaderDTO shipmentHeaderDTO = BeanUtils.copyBeanPropertyUtils(shipmentHeaderEntity,TWmsShipmentHeaderDTO.class);
        shipmentHeaderService.modifyShipmentHeader(shipmentHeaderDTO,dbShardVO);
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult removeShipmentDetail(Long id, DbShardVO dbShardVO) {
        TWmsShipmentDetailEntity shipmentDetailEntity = shipmentDetailMapper.selectByPrimaryKey(id,getSplitTableKey(dbShardVO));
        TWmsShipmentHeaderEntity shipmentHeaderEntity = shipmentHeaderService.findByPrimaryKey(shipmentDetailEntity.getShipmentId(),dbShardVO);
        shipmentDetailMapper.deleteByPrimaryKey( id,getSplitTableKey(dbShardVO));
        shipmentHeaderEntity.setTotalCategoryQty(shipmentHeaderEntity.getTotalCategoryQty()-1);
        shipmentHeaderEntity.setTotalQty(shipmentHeaderEntity.getTotalQty()-shipmentDetailEntity.getOrderedQty());
        TWmsShipmentHeaderDTO shipmentHeaderDTO = BeanUtils.copyBeanPropertyUtils(shipmentHeaderEntity,TWmsShipmentHeaderDTO.class);
        shipmentHeaderService.modifyShipmentHeader(shipmentHeaderDTO,dbShardVO);
        return MessageResult.getSucMessage();
    }


    @Override
    public MessageResult modifyShipmentDetail(TWmsShipmentDetailDTO shipmentDetailDTO, DbShardVO dbShardVO) {
        TWmsShipmentDetailEntity TWmsShipmentDetailEntity = BeanUtils.copyBeanPropertyUtils(shipmentDetailDTO,TWmsShipmentDetailEntity.class);
        shipmentDetailMapper.updateShipmentDetail(TWmsShipmentDetailEntity,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }

    /**
     * 修改出库单明细库房
     * @param shipmentDetailDTO
     * @param dbShardVO
     * @return
     */
    @Override
    @NeedLog(type = LogType.OPREATION)
    public MessageResult updateShipmentDetail(TWmsShipmentDetailDTO shipmentDetailDTO, DbShardVO dbShardVO) {
        TWmsShipmentHeaderEntity headerEntity = shipmentHeaderService.findByPrimaryKey(shipmentDetailDTO.getShipmentId(),dbShardVO);
        TWmsShipmentDetailEntity TWmsShipmentDetailEntity = BeanUtils.copyBeanPropertyUtils(shipmentDetailDTO,TWmsShipmentDetailEntity.class);
        shipmentDetailMapper.updateShipmentDetailRoomId(TWmsShipmentDetailEntity,getSplitTableKey(dbShardVO));
        this.writeOperationLog(headerEntity.getId(),headerEntity.getCargoOwnerId(), ActionCode.Update.toString(),
                "分配库房变更，明细单号："+shipmentDetailDTO.getId()+"", OrderTypeCode.Shipment.toString(),
                OperationStatusCode.Finished.toString(),dbShardVO);
        return MessageResult.getSucMessage();
    }

    @Override
    public PageResponse<List> queryShipmentDetailByHeader(Map paramMap, DbShardVO dbShardVO) {
        paramMap.put("splitTableKey",getSplitTableKey(dbShardVO));
        List<TWmsShipmentDetailEntity> mapList = shipmentDetailMapper.queryDetailsPages(paramMap);
        Integer totalSize = shipmentDetailMapper.queryDetailPageCount(paramMap);
        PageResponse<List> response=new PageResponse();
        response.setTotal(totalSize);
        response.setRows(mapList);
        return response;
    }

    @Override
    public List<TWmsShipmentDetailEntity> getShipmentDetails(Long headerId, DbShardVO dbShardVO) {
        List<TWmsShipmentDetailEntity> mapList = shipmentDetailMapper.queryShipmentDetails(headerId, getSplitTableKey(dbShardVO));
        if (CollectionUtils.isNotEmpty(mapList)) {
            return mapList;
        }
        return null;
    }

    @Override
    public MessageResult batchDeleteShipmentDetails(Long[] ShipmentIds, DbShardVO dbShardVO) {
        for (Long ShipmentId:ShipmentIds){
            shipmentDetailMapper.deleteShipmentDetails(ShipmentId,getSplitTableKey(dbShardVO));
        }
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult updateShipmentDetailAllocatedQty(Long shipmentId, String storageRoomId, DbShardVO dbShardVO) {
        shipmentDetailMapper.updateShipmentDetailAllocatedQty(shipmentId,storageRoomId,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult batchcreateShipmentDetail(List<TWmsShipmentDetailEntity> list, DbShardVO dbShardVO) {
        shipmentDetailMapper.insertShipmentDetailList(list,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }

    @Override
    public PageResponse<List<TWmsShipmentDetailEntity>> queryShipmentDetail(TWmsShipmentDetailDTO shipmentDetailDTO, DbShardVO dbShardVO) {
        TWmsShipmentDetailEntity shipmentDetailEntity = BeanUtils.copyBeanPropertyUtils(shipmentDetailDTO,TWmsShipmentDetailEntity.class);
        List<TWmsShipmentHeaderEntity> list = new ArrayList<>();
        List<Long> shipmentIds = new ArrayList<>();
        if(shipmentDetailEntity.getCargoOwnerId() != null && shipmentDetailEntity.getCargoOwnerId() != 0){
            list = shipmentHeaderService.findEntityBycargoOwnerId(shipmentDetailEntity.getCargoOwnerId(),dbShardVO);
            if(CollectionUtils.isEmpty(list)){
                return null;
            }else{
                for (TWmsShipmentHeaderEntity s:list) {
                    shipmentIds.add(s.getId());
                }
            }
        }
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("entity",shipmentDetailEntity);
        map.put("shipmentIds",shipmentIds);
        map.put("startTime",shipmentDetailEntity.getStartTime());
        map.put("endTime",shipmentDetailEntity.getEndTime());
        map.put("splitTableKey",getSplitTableKey(dbShardVO));
        List<TWmsShipmentDetailEntity> mapList = shipmentDetailMapper.queryShipmentDetailPages(map);
        List<Long> headerIds = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(mapList)){
            for (TWmsShipmentDetailEntity s:mapList) {
                TWmsSkuEntity skuEntity = skuService.findByPrimaryKey(s.getSkuId(),getSkuDbShareVO(dbShardVO));
                if(skuEntity !=null){
                    s.setSpec(skuEntity.getSpec());
                    s.setUnitType(skuEntity.getUnitType());
                }
                headerIds.add(s.getShipmentId());
            }
        }
        List<TWmsShipmentHeaderEntity> headerList = new ArrayList<>();
        if(CollectionUtils.isEmpty(list)){
            headerList = shipmentHeaderService.findOwnerIdByHeaderId(headerIds,dbShardVO);
        }else{
            headerList = list;
        }
        setCargoOwnerId(mapList,headerList);//将header中货主Id赋值给明细
        Integer totalSize = shipmentDetailMapper.queryShipmentDetailPageCount(map);
        PageResponse<List<TWmsShipmentDetailEntity>> response=new PageResponse();
        response.setTotal(totalSize);
        response.setRows(mapList);
        return response;
    }

    @Override
    public PageResponse<List<TWmsShipmentDetailEntity>> queryShipmentSummaryDetail(TWmsShipmentDetailDTO shipmentDetailDTO, DbShardVO dbShardVO) {
        TWmsShipmentDetailEntity shipmentDetailEntity = BeanUtils.copyBeanPropertyUtils(shipmentDetailDTO,TWmsShipmentDetailEntity.class);
        List<TWmsShipmentHeaderEntity> list = new ArrayList<>();
        List<Long> shipmentIds = new ArrayList<>();
        if(shipmentDetailEntity.getCargoOwnerId() != null && shipmentDetailEntity.getCargoOwnerId() != 0){
            list = shipmentHeaderService.findEntityBycargoOwnerId(shipmentDetailEntity.getCargoOwnerId(),dbShardVO);
            if(CollectionUtils.isEmpty(list)){
                return null;
            }else{
                for (TWmsShipmentHeaderEntity s:list) {
                    shipmentIds.add(s.getId());
                }
            }
        }
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("entity",shipmentDetailEntity);
        map.put("shipmentIds",shipmentIds);
        map.put("startTime",shipmentDetailEntity.getStartTime());
        map.put("endTime",shipmentDetailEntity.getEndTime());
        map.put("splitTableKey",getSplitTableKey(dbShardVO));
        List<TWmsShipmentDetailEntity> mapList = shipmentDetailMapper.queryDetailSummaryPages(map);
        List<Long> headerIds = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(mapList)){
            for (TWmsShipmentDetailEntity s:mapList) {
                TWmsSkuEntity skuEntity = skuService.findByPrimaryKey(s.getSkuId(),getSkuDbShareVO(dbShardVO));
                if(skuEntity !=null){
                    s.setSpec(skuEntity.getSpec());
                    s.setUnitType(skuEntity.getUnitType());
                }
                headerIds.add(s.getShipmentId());
            }
        }
        List<TWmsShipmentHeaderEntity> headerList = new ArrayList<>();
        if(CollectionUtils.isEmpty(list)){
            headerList = shipmentHeaderService.findOwnerIdByHeaderId(headerIds,dbShardVO);
        }else{
            headerList = list;
        }
        setCargoOwnerId(mapList,headerList);//将header中货主Id赋值给明细
        Integer totalSize = shipmentDetailMapper.queryDetailSummaryPageCount(map);
        PageResponse<List<TWmsShipmentDetailEntity>> response=new PageResponse();
        response.setTotal(totalSize);
        response.setRows(mapList);
        return response;
    }

    private void setCargoOwnerId(List<TWmsShipmentDetailEntity> mapList,List<TWmsShipmentHeaderEntity> headerList){
        for (TWmsShipmentDetailEntity sd:mapList) {
                for (TWmsShipmentHeaderEntity sh: headerList) {
                    if(sh.getId().equals(sd.getShipmentId()))
                        sd.setCargoOwnerId(sh.getCargoOwnerId());
                }

        }
    }

    /**
     * 批量修改出库单明细
     * @param list
     * @param dbShardVO
     * @return
     */
    @Override
    public MessageResult updateShipmentDetailBatch(List<TWmsShipmentDetailEntity> list, DbShardVO dbShardVO) {
        for (TWmsShipmentDetailEntity s:list) {
            s.setUpdateTime(new Date().getTime());
            s.setAllocatedQty(s.getOrderedQty());
            shipmentDetailMapper.updateShipmentDetail(s,getSplitTableKey(dbShardVO));
        }
        return MessageResult.getSucMessage();
    }
}
