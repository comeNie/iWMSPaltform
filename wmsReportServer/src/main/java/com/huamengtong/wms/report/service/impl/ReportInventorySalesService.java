package com.huamengtong.wms.report.service.impl;

import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.service.BaseService;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.domain.TWmsInventorySalesReportEntity;
import com.huamengtong.wms.dto.inventory.TWmsInventoryLogDTO;
import com.huamengtong.wms.dto.sku.TWmsSkuDTO;
import com.huamengtong.wms.entity.inventory.TWmsInventoryEntity;
import com.huamengtong.wms.entity.inventory.TWmsInventoryLogEntity;
import com.huamengtong.wms.entity.sku.TWmsSkuEntity;
import com.huamengtong.wms.inventory.service.IInventoryLogService;
import com.huamengtong.wms.inventory.service.IInventoryService;
import com.huamengtong.wms.report.service.IReportInventorySalesService;
import com.huamengtong.wms.sku.service.ISkuService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by mario on 2017/3/15.
 */
@Service
public class ReportInventorySalesService extends BaseService implements IReportInventorySalesService {


    @Autowired
    IInventoryLogService inventoryLogService;

    @Autowired
    ISkuService skuService;

    @Autowired
    IInventoryService inventoryService;


    /***
     * 进销存报表统计
     * @param paramMap
     * @param dbShardVO
     * @return
     */
    @Override
    public PageResponse<List<TWmsInventorySalesReportEntity>> getInventorySalesReportList(Map paramMap, DbShardVO dbShardVO) {
        TWmsSkuDTO skuDTO = new TWmsSkuDTO();
        skuEntityChange(paramMap,skuDTO);
        List<Long> list = new ArrayList<>();
        if(skuDTO.getSku() != null || skuDTO.getBarcode() != null || skuDTO.getItemName() != null){
            list = skuService.findSkuLists(skuDTO,getSkuDbShareVO(dbShardVO));
            if(CollectionUtils.isEmpty(list)){
                return null;
            }
        }
        TWmsInventoryLogDTO inventoryLogDTO = new TWmsInventoryLogDTO();
        if(paramMap.containsKey("skuId")){
            inventoryLogDTO.setId(MapUtils.getLong(paramMap,"skuId"));
        }
        if (paramMap.containsKey("typeCode")){
            inventoryLogDTO.setTypeCode(MapUtils.getString(paramMap,"typeCode"));
        }
        if(paramMap.containsKey("page")){
            inventoryLogDTO.setPage(MapUtils.getInteger(paramMap,"page"));
        }
        if (paramMap.containsKey("pageSize")){
            inventoryLogDTO.setPageSize(MapUtils.getInteger(paramMap,"pageSize"));
        }
        Integer  totalSize = list.size();
        if(list.size() < 1){
            list = inventoryLogService.selectInventoryLogSkuId(inventoryLogDTO,dbShardVO);
            totalSize = inventoryLogService.selectInventoryLogSkuIdCount(inventoryLogDTO,dbShardVO);
        }
        //库存日志获取数据
        List<TWmsInventoryLogEntity> inventoryLogEntityList = inventoryLogService.getInventoryLogLists(inventoryLogDTO,list,null,null, dbShardVO);
        
        List<TWmsInventorySalesReportEntity> inventorySalesReportEntityList = new ArrayList<>();

        PageResponse<List<TWmsInventorySalesReportEntity>> response=new PageResponse();

        List<Long> skuIdList = new ArrayList<>();


        Map skuIdmap = new HashedMap();
        for (TWmsInventoryLogEntity l : inventoryLogEntityList) {
            if (!skuIdmap.containsKey(l.getSkuId().toString())) {
                skuIdmap.put(l.getSkuId().toString(),l.getSkuId().toString());
                skuIdList.add(l.getSkuId());

                TWmsInventorySalesReportEntity salesReportEntity = new TWmsInventorySalesReportEntity();
                salesReportEntity.setSkuId(l.getSkuId());
                if (l.getTypeCode().equals("Receipt")) {
                    salesReportEntity.setReceivedQty(l.getQty());
                }
                if (l.getTypeCode().equals("Shipment")){
                    salesReportEntity.setOrderedQty(Math.abs(l.getQty()));
                }
//                if (l.getTypeCode().equals("Processing")){
//                    if (l.getQty() > 0){
//                        salesReportEntity.setProReceiptQty(l.getQty());
//                    }else {
//                        salesReportEntity.setProShipmentQty(l.getQty());
//                    }
//                }
                if(l.getTypeCode().equals("Receipt") || l.getTypeCode().equals("Shipment")){
                    if(salesReportEntity.getSumQty()!=null){
                        salesReportEntity.setSumQty(salesReportEntity.getSumQty()+l.getQty());
                    }
                    else {
                        salesReportEntity.setSumQty(l.getQty());
                    }
                }
                inventorySalesReportEntityList.add(salesReportEntity);

            } else {
                for (TWmsInventorySalesReportEntity i : inventorySalesReportEntityList) {
                    if (i.getSkuId().equals(l.getSkuId()) ) {
                        if (l.getTypeCode().equals("Receipt")) {
                            i.setReceivedQty(l.getQty());
                        }
                        if (l.getTypeCode().equals("Shipment")){
                            i.setOrderedQty(Math.abs(l.getQty()));
                        }
//                        if (l.getTypeCode().equals("Processing")){
//                            if (l.getQty() > 0){
//                                i.setProReceiptQty(l.getQty());
//                            }else {
//                                i.setProShipmentQty(l.getQty());
//                            }
//                        }
                        if(l.getTypeCode().equals("Receipt") || l.getTypeCode().equals("Shipment")){
                            if(i.getSumQty()!=null){
                                i.setSumQty(i.getSumQty()+l.getQty());
                            }
                            else {
                                i.setSumQty(Math.abs(l.getQty()));
                            }
                        }
                        break;
                    }
                }
            }
        }
        List<TWmsSkuEntity> skuEntities = skuService.findByIdList(skuIdList, getSkuDbShareVO(dbShardVO));
        for (TWmsInventorySalesReportEntity salesReportEntity: inventorySalesReportEntityList) {
            turns(skuEntities, salesReportEntity, dbShardVO);//商品转换
        }
        response.setRows(inventorySalesReportEntityList);
        response.setTotal(totalSize);
     return response;
    }

    /**
     * 商品转换
     *
     * @param skuEntities
     * @param inventorySalesReportEntity
     */
    private void turns(List<TWmsSkuEntity> skuEntities, TWmsInventorySalesReportEntity inventorySalesReportEntity, DbShardVO dbShardVO) {
        for (TWmsSkuEntity s : skuEntities) {
            if (s.getId().equals(inventorySalesReportEntity.getSkuId())) {
                inventorySalesReportEntity.setSkuId(s.getId());
                inventorySalesReportEntity.setSku(s.getSku());
                inventorySalesReportEntity.setSkuBarCode(s.getBarcode());
                inventorySalesReportEntity.setSkuItemName(s.getItemName());
                inventorySalesReportEntity.setSpec(s.getSpec());
                inventorySalesReportEntity.setUnitType(s.getUnitType());
                inventorySalesReportEntity.setCategorysId(s.getCategorysId());
                List<TWmsInventoryEntity> inventoryEntityList = inventoryService.selectInventorBySkuId(s.getId(),dbShardVO);
                for (TWmsInventoryEntity i :inventoryEntityList){
                    if(inventorySalesReportEntity.getStartQty() != null){
                        inventorySalesReportEntity.setStartQty(inventorySalesReportEntity.getStartQty()+i.getOnhandQty());
                    }else{
                        inventorySalesReportEntity.setStartQty(i.getOnhandQty());
                    }
                    if (inventorySalesReportEntity.getSumQty() != null){
                        inventorySalesReportEntity.setSumQty(inventorySalesReportEntity.getSumQty()+i.getOnhandQty());
                    }else {
                        inventorySalesReportEntity.setSumQty(i.getOnhandQty());
                    }
                }

                break;
            }
        }
    }


    private void skuEntityChange(Map paramMap, TWmsSkuDTO skuDTO){
        if (paramMap.get("sku") != null&& !paramMap.get("sku").equals("")){
            skuDTO.setSku(paramMap.get("sku").toString());
        }
        if(paramMap.get("skuBarCode") != null && !paramMap.get("skuBarCode").equals("")){
            skuDTO.setBarcode(paramMap.get("skuBarCode").toString());
        }
        if(paramMap.get("skuItemName") != null && !paramMap.get("skuItemName").equals("")){
            skuDTO.setItemName(paramMap.get("skuItemName").toString());
        }
    }


}
