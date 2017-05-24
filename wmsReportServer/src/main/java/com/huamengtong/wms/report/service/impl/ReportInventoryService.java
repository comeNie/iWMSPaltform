package com.huamengtong.wms.report.service.impl;

import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.service.BaseService;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.inventory.TWmsInventoryDTO;
import com.huamengtong.wms.dto.inventory.TWmsInventoryLogDTO;
import com.huamengtong.wms.dto.report.TWmsReportInventoryDTO;
import com.huamengtong.wms.dto.sku.TWmsSkuDTO;
import com.huamengtong.wms.entity.inventory.TWmsInventoryEntity;
import com.huamengtong.wms.entity.inventory.TWmsInventoryLogEntity;
import com.huamengtong.wms.entity.sku.TWmsSkuEntity;
import com.huamengtong.wms.inventory.service.IInventoryLogService;
import com.huamengtong.wms.inventory.service.IInventoryService;
import com.huamengtong.wms.inventory.service.IProInventoryService;
import com.huamengtong.wms.report.service.IReportInventoryService;
import com.huamengtong.wms.sku.service.ISkuService;
import com.huamengtong.wms.utils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edwin on 2016/12/7.
 * 库存报表
 */
@Service
public class ReportInventoryService extends BaseService implements IReportInventoryService {

    @Autowired
    IInventoryService inventoryService;

    @Autowired
    IInventoryLogService inventoryLogService;

    @Autowired
    ISkuService skuService;


    @Autowired
    IProInventoryService proInventoryService;



    /***
     * 库存明细报表查询
     * @param dbShardVO
     * @return
     */
    @Override
    public PageResponse<List> getReportInventory(TWmsReportInventoryDTO reportInventoryDTO, DbShardVO dbShardVO) {
            TWmsInventoryDTO inventoryDTO = new TWmsInventoryDTO();
            TWmsSkuDTO skuDTO = new TWmsSkuDTO();
            skuEntiyEvaluate(reportInventoryDTO,skuDTO);//组装商品实体类
            Long fromTime = reportInventoryDTO.getInventoryCreateTimeFrom();
            Long toTime = reportInventoryDTO.getInventoryCreateTimeTo();
            List<Long> list = new ArrayList<>();
            if(skuDTO.getSku() != null || skuDTO.getBarcode() != null || skuDTO.getItemName() != null){
                list = skuService.findSkuLists(skuDTO,getSkuDbShareVO(dbShardVO));
                if(CollectionUtils.isEmpty(list)){
                    return null;
                }
            }
            inventoryDTO.setCargoOwnerId(reportInventoryDTO.getCargoOwnerId());
            inventoryDTO.setStorageRoomId(reportInventoryDTO.getStorageRoomId());
            inventoryDTO.setPage(reportInventoryDTO.getPage());
            inventoryDTO.setPageSize(reportInventoryDTO.getPageSize());
            inventoryDTO.setOffset(reportInventoryDTO.getOffset());
            //根据输入的条件进行查询
            List<TWmsInventoryEntity> inventoryEntities = inventoryService.findReportInventory(inventoryDTO,list,fromTime,toTime,dbShardVO);

            if(CollectionUtils.isEmpty(inventoryEntities)) {
                return null;
            }
            List<Long> skuIdList = new ArrayList<Long>();
            for (TWmsInventoryEntity i : inventoryEntities) {
                skuIdList.add(i.getSkuId());
            }
            List<TWmsSkuEntity> skuEntities = skuService.findByIdList(skuIdList,getSkuDbShareVO(dbShardVO));
            List<TWmsReportInventoryDTO> mapList = new ArrayList();
                for (TWmsInventoryEntity x: inventoryEntities) {
                    TWmsInventoryDTO y = BeanUtils.copyBeanPropertyUtils(x, TWmsInventoryDTO.class);
                    TWmsReportInventoryDTO reportInventoryDTO1 = new TWmsReportInventoryDTO();
                    reportInventoryDTO1.setId(y.getId());
                    reportInventoryDTO1.setCargoOwnerId(y.getCargoOwnerId());
                    reportInventoryDTO1.setStorageRoomId(y.getStorageRoomId());
                    reportInventoryDTO1.setWarehouseId(y.getWarehouseId());
                    turns(skuEntities, reportInventoryDTO1, x);//商品转换
                    reportInventoryDTO1.setInventoryStatusCode(y.getInventoryStatusCode());
                    reportInventoryDTO1.setOnhandQty(y.getOnhandQty());
                    reportInventoryDTO1.setActiveQty(y.getActiveQty());
                    reportInventoryDTO1.setAllocatedQty(y.getAllocatedQty());
                    reportInventoryDTO1.setPickedQty(y.getPickedQty());
                    reportInventoryDTO1.setMortgagedQty(y.getMortgagedQty());
                    reportInventoryDTO1.setWorkingQty(y.getWorkingQty());
                    reportInventoryDTO1.setReceiptTime(y.getCreateTime());
                    reportInventoryDTO1.setIsHold(y.getIsHold());
                    reportInventoryDTO1.setCreateTime(y.getCreateTime());
                    reportInventoryDTO1.setUpdateUser(y.getUpdateUser());
                    reportInventoryDTO1.setUpdateTime(y.getUpdateTime());
                    reportInventoryDTO1.setPackageQty(y.getPackageQty());
                    mapList.add(reportInventoryDTO1);
                }
            Integer totalSize = inventoryService.queryInventoryReportPageCount(inventoryDTO,list,fromTime,toTime,dbShardVO);
            PageResponse<List> response=new PageResponse();
            response.setTotal(totalSize);
            response.setRows(mapList);
            return response;
    }

    /**
     * 库存日志报表查询
     * @param inventoryLogDTO 前端查询条件DTO实体
     * @param dbShardVO
     * @return
     */
    @Override
    public PageResponse<List<TWmsInventoryLogEntity>> getInventoryLog(TWmsInventoryLogDTO inventoryLogDTO, DbShardVO dbShardVO) {
        TWmsSkuDTO skuDTO = new TWmsSkuDTO();
        if(inventoryLogDTO.getSku() != null && !inventoryLogDTO.getSku().equals("")){
            skuDTO.setSku(inventoryLogDTO.getSku());
        }
        if(inventoryLogDTO.getBarCode() != null && !inventoryLogDTO.getBarCode().equals("")){
            skuDTO.setBarcode(inventoryLogDTO.getBarCode());
        }
        if(inventoryLogDTO.getSkuName() != null && !inventoryLogDTO.getSkuName().equals("")){
            skuDTO.setItemName(inventoryLogDTO.getSkuName());
        }
        List<Long> skuIdList = new ArrayList<>();
        if(skuDTO != null){
            skuIdList = skuService.findSkuLists(skuDTO,getSkuDbShareVO(dbShardVO));
            if(CollectionUtils.isEmpty(skuIdList)){
                return null;
            }
        }
        Long fromTime = inventoryLogDTO.getFromTime();
        Long toTime = inventoryLogDTO.getToTime();
        PageResponse<List<TWmsInventoryLogEntity>> response= inventoryLogService.queryInventoryLogPages(inventoryLogDTO,skuIdList,fromTime,toTime,dbShardVO);
        List<TWmsInventoryLogEntity> list = response.getRows();
        if(CollectionUtils.isEmpty(list)) {
            return null;
        }
        List<Long> skuIdList1 = new ArrayList<Long>();
        List<Long> inventoryId = new ArrayList<Long>();
        for (TWmsInventoryLogEntity i : list) {
            skuIdList1.add(i.getSkuId());
            inventoryId.add(i.getInventoryId());
        }
        List<TWmsInventoryEntity> inventoryEntitys = inventoryService.findByIds(inventoryId,dbShardVO);
        List<TWmsSkuEntity> skuEntities = skuService.findByIdList(skuIdList1,getSkuDbShareVO(dbShardVO));
        for (TWmsInventoryLogEntity x:list) {
            for (TWmsInventoryEntity i:inventoryEntitys) {
                if(i.getId().equals(x.getInventoryId())){
                    x.setStorageRoomId(i.getStorageRoomId());
                }
            }
            for (TWmsSkuEntity skuEntity:skuEntities) {
                if(skuEntity.getId().equals(x.getSkuId())){
                    x.setSku(skuEntity.getSku());
                    x.setSkuName(skuEntity.getItemName());
                    x.setBarCode(skuEntity.getBarcode());
                    x.setSkuSpec(skuEntity.getSpec());
                    x.setUnitType(skuEntity.getUnitType());
                    break;
                }
            }
        }
        response.setRows(list);
        return response;
    }

    /**
     * 库存总计
     * @param reportInventoryDTO 前端传入查询dto
     * @param dbShardVO
     * @return
     */
    @Override
    public PageResponse<List<TWmsReportInventoryDTO>> getInventorySummary(TWmsReportInventoryDTO reportInventoryDTO, DbShardVO dbShardVO) {
        TWmsInventoryDTO inventoryDTO = new TWmsInventoryDTO();
        TWmsSkuDTO skuDTO = new TWmsSkuDTO();
        skuEntiyEvaluate(reportInventoryDTO,skuDTO);//组装商品实体类
        Long fromTime = reportInventoryDTO.getInventoryCreateTimeFrom();
        Long toTime = reportInventoryDTO.getInventoryCreateTimeTo();
        List<Long> list = new ArrayList<>();
        if(skuDTO.getSku() != null || skuDTO.getBarcode() != null || skuDTO.getItemName() != null){
            list = skuService.findSkuLists(skuDTO,getSkuDbShareVO(dbShardVO));
            if(CollectionUtils.isEmpty(list)){
                return null;
            }
        }
        inventoryDTO.setCargoOwnerId(reportInventoryDTO.getCargoOwnerId());
        inventoryDTO.setStorageRoomId(reportInventoryDTO.getStorageRoomId());
        inventoryDTO.setPageSize(reportInventoryDTO.getPageSize());
        inventoryDTO.setPage(reportInventoryDTO.getPage());
        reportInventoryDTO.setOffset(reportInventoryDTO.getOffset());
        List<TWmsInventoryEntity> inventoryEntityList = inventoryService.selectReportSumMap(inventoryDTO,list,fromTime,toTime,dbShardVO);
        if(CollectionUtils.isEmpty(inventoryEntityList)) {
            return null;
        }
        List<Long> skuIdList = new ArrayList<Long>();
        for (TWmsInventoryEntity i : inventoryEntityList) {
            skuIdList.add(i.getSkuId());
        }
        List<TWmsSkuEntity> skuEntities = skuService.findByIdList(skuIdList,getSkuDbShareVO(dbShardVO));
        Integer totalSize = inventoryService.queryInventorySumPageCount(inventoryDTO,list,fromTime,toTime,dbShardVO);
        PageResponse<List<TWmsReportInventoryDTO>> response=new PageResponse();
        List<TWmsReportInventoryDTO> mapList = new ArrayList();
        if(CollectionUtils.isNotEmpty(inventoryEntityList)){
            for (TWmsInventoryEntity x: inventoryEntityList) {
                TWmsInventoryDTO y = BeanUtils.copyBeanPropertyUtils(x,TWmsInventoryDTO.class);
                TWmsReportInventoryDTO reportInventoryDTO1 = new TWmsReportInventoryDTO();
                turns(skuEntities,reportInventoryDTO1,x);//商品转换
                reportInventoryDTO1.setCargoOwnerId(y.getCargoOwnerId());
                reportInventoryDTO1.setStorageRoomId(y.getStorageRoomId());
                reportInventoryDTO1.setWarehouseId(y.getWarehouseId());
                reportInventoryDTO1.setInventoryStatusCode(y.getInventoryStatusCode());
                reportInventoryDTO1.setOnhandQty(y.getOnhandQty());
                reportInventoryDTO1.setActiveQty(y.getActiveQty());
                reportInventoryDTO1.setAllocatedQty(y.getAllocatedQty());
                reportInventoryDTO1.setPickedQty(y.getPickedQty());
                reportInventoryDTO1.setMortgagedQty(y.getMortgagedQty());
                reportInventoryDTO1.setWorkingQty(y.getWorkingQty());
                reportInventoryDTO1.setPackageQty(y.getPackageQty());
                mapList.add(reportInventoryDTO1);
            }
        }
        response.setTotal(totalSize);
        response.setRows(mapList);
        return response;
    }

    private void turns(List<TWmsSkuEntity> skuEntities,TWmsReportInventoryDTO reportInventoryDTO1,TWmsInventoryEntity x){
        for (TWmsSkuEntity s :skuEntities) {
            if(s.getId().equals(x.getSkuId())){
                reportInventoryDTO1.setSkuId(s.getId());
                reportInventoryDTO1.setSku(s.getSku());
                reportInventoryDTO1.setSkuBarcode(s.getBarcode());
                reportInventoryDTO1.setSkuName(s.getItemName());
                reportInventoryDTO1.setSpec(s.getSpec());
                reportInventoryDTO1.setUnitType(s.getUnitType());
                break;
            }
        }
    }

    private void skuEntiyEvaluate(TWmsReportInventoryDTO reportInventoryDTO, TWmsSkuDTO skuDTO){
        if(reportInventoryDTO.getSku() != null && !reportInventoryDTO.getSku().equals("")){
            skuDTO.setSku(reportInventoryDTO.getSku());
        }
        if(reportInventoryDTO.getSkuBarcode() != null && !reportInventoryDTO.getSkuBarcode().equals("")){
            skuDTO.setBarcode(reportInventoryDTO.getSkuBarcode());
        }
        if(reportInventoryDTO.getSkuName() != null && !reportInventoryDTO.getSkuName().equals("")){
            skuDTO.setItemName(reportInventoryDTO.getSkuName());
        }
    }

}
