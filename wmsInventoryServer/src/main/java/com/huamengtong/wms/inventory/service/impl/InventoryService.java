package com.huamengtong.wms.inventory.service.impl;

import com.huamengtong.wms.client.IAutoIdClient;
import com.huamengtong.wms.constants.AutoIdConstants;
import com.huamengtong.wms.constants.GlobalConstants;
import com.huamengtong.wms.core.annotation.NeedLog;
import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.service.BaseService;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.inventory.TWmsInventoryDTO;
import com.huamengtong.wms.dto.report.TWmsReportInventoryDTO;
import com.huamengtong.wms.dto.sku.TWmsSkuDTO;
import com.huamengtong.wms.em.ActionCode;
import com.huamengtong.wms.em.AllocationStrategy;
import com.huamengtong.wms.em.InventoryStatusCode;
import com.huamengtong.wms.em.LogType;
import com.huamengtong.wms.em.OperationStatusCode;
import com.huamengtong.wms.em.OrderTypeCode;
import com.huamengtong.wms.entity.inventory.TWmsInventoryEntity;
import com.huamengtong.wms.entity.inventory.TWmsProInventoryEntity;
import com.huamengtong.wms.entity.inwh.TWmsAdjustmentDetailEntity;
import com.huamengtong.wms.entity.inwh.TWmsAdjustmentHeaderEntity;
import com.huamengtong.wms.entity.inwh.TWmsProPackageDetailEntity;
import com.huamengtong.wms.entity.inwh.TWmsProPackageEntity;
import com.huamengtong.wms.entity.inwh.TWmsReceiptDetailEntity;
import com.huamengtong.wms.entity.inwh.TWmsReceiptHeaderEntity;
import com.huamengtong.wms.entity.main.TWmsAllocationStrategyEntity;
import com.huamengtong.wms.entity.main.TWmsStorageRoomEntity;
import com.huamengtong.wms.entity.outwh.TWmsAllocateEntity;
import com.huamengtong.wms.entity.outwh.TWmsShipmentDetailEntity;
import com.huamengtong.wms.entity.outwh.TWmsShipmentHeaderEntity;
import com.huamengtong.wms.entity.sku.TWmsSkuEntity;
import com.huamengtong.wms.inventory.mapper.InventoryMapper;
import com.huamengtong.wms.inventory.service.IInventoryService;
import com.huamengtong.wms.inventory.service.IProInventoryService;
import com.huamengtong.wms.main.service.IAllocationStrategyService;
import com.huamengtong.wms.main.service.IStorageRoomService;
import com.huamengtong.wms.sku.service.ISkuService;
import com.huamengtong.wms.utils.ArrayUtil;
import com.huamengtong.wms.utils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class InventoryService extends BaseService implements IInventoryService {
    @Autowired
    private InventoryMapper inventoryMapper;

    @Autowired
    private IAutoIdClient autoIdClient;

    @Autowired
    IStorageRoomService storageRoomService;

    @Autowired
    ISkuService skuService;


    @Autowired
    IProInventoryService proInventoryService;

    @Autowired
    IAllocationStrategyService allocationStrategyService;


    @Override
    public PageResponse<List<TWmsInventoryEntity>> queryInventoryPages(TWmsInventoryDTO inventoryDTO, DbShardVO dbShardVO) {
        TWmsInventoryEntity inventoryEntity = BeanUtils.copyBeanPropertyUtils(inventoryDTO, TWmsInventoryEntity.class);
        List<TWmsInventoryEntity> inventoryEntityList = inventoryMapper.queryInventoryPages(inventoryEntity, getSplitTableKey(dbShardVO));
        Integer totalSize = inventoryMapper.queryInventoryPageCount(inventoryEntity, getSplitTableKey(dbShardVO));
        PageResponse<List<TWmsInventoryEntity>> response = new PageResponse<>();
        response.setRows(inventoryEntityList);
        response.setTotal(totalSize);
        return response;
    }

    @Override
    public Integer queryInventoryPageCount(TWmsInventoryDTO inventoryDTO, DbShardVO dbShardVO) {
        TWmsInventoryEntity inventoryEntity = BeanUtils.copyBeanPropertyUtils(inventoryDTO, TWmsInventoryEntity.class);
        return inventoryMapper.queryInventoryPageCount(inventoryEntity, getSplitTableKey(dbShardVO));
    }

    @Override
    public MessageResult addInventory(TWmsInventoryDTO inventoryDTO, DbShardVO dbShardVO) {
        TWmsInventoryEntity inventoryEntity = BeanUtils.copyBeanPropertyUtils(inventoryDTO, TWmsInventoryEntity.class);
        if (inventoryEntity.getId() == null || inventoryEntity.getId() == 0) {
            inventoryEntity.setId(autoIdClient.getAutoId(AutoIdConstants.TWmsInventoryEntity));
        }
        inventoryMapper.insertInventory(inventoryEntity, getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }


    @Override
    public MessageResult modifyInventory(TWmsInventoryDTO inventoryDTO, DbShardVO dbShardVO) {
        TWmsInventoryEntity inventoryEntity = BeanUtils.copyBeanPropertyUtils(inventoryDTO, TWmsInventoryEntity.class);
        inventoryMapper.updateInventory(inventoryEntity, getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult removeInventory(Long id, DbShardVO dbShardVO) {
        inventoryMapper.deleteByPrimaryKey(id, getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }

    @Override
    public TWmsInventoryEntity findById(Long id, DbShardVO dbShardVO) {
        return inventoryMapper.selectByPK(id, getSplitTableKey(dbShardVO));
    }


    @Override
    public TWmsInventoryEntity findInventoryFromCargo(Long skuId, Long cargoOwnerId, Long storageRoomId, String inventoryStatusCode, DbShardVO dbShardVO) {
        Map<String, Object> map = new HashMap();
        map.put("skuId", skuId);
        map.put("cargoOwnerId", cargoOwnerId);
        map.put("storageRoomId", storageRoomId);
        map.put("inventoryStatusCode", inventoryStatusCode);
        map.put("splitTableKey", getSplitTableKey(dbShardVO));
        return inventoryMapper.selectByCargo(map);
    }

    @Override
    public TWmsInventoryEntity findInventoryFromCargoOwner(Long skuId, Long cargoOwnerId, Long storageRoomId, DbShardVO dbShardVO) {
        Map<String, Object> map = new HashMap();
        map.put("skuId", skuId);
        map.put("cargoOwnerId", cargoOwnerId);
        map.put("storageRoomId", storageRoomId);
        map.put("splitTableKey", getSplitTableKey(dbShardVO));
        return inventoryMapper.selectByCargo(map);
    }

    /**
     * 根据条件查询出未被冻结的库存数据
     *
     * @param cargoOwnerId  货主id
     * @param storageRoomId 库房id
     * @param dbShardVO     分表标识
     * @return 返回List
     */
    @Override
    public List<TWmsInventoryEntity> findInventoryStorageRoom(Long cargoOwnerId, Long storageRoomId, DbShardVO dbShardVO) {
        Map<String, Object> map = new HashMap();
        map.put("cargoOwnerId", cargoOwnerId);
        map.put("storageRoomId", storageRoomId);
        map.put("splitTableKey", getSplitTableKey(dbShardVO));
        return inventoryMapper.selectByStorageRoom(map);
    }


    @Override
    public List<TWmsInventoryEntity> findInventoryHold(Long cargoOwnerId, Long storageRoomId, DbShardVO dbShardVO) {
        Map<String, Object> map = new HashMap();
        map.put("cargoOwnerId", cargoOwnerId);
        map.put("storageRoomId", storageRoomId);
        map.put("splitTableKey", getSplitTableKey(dbShardVO));
        return inventoryMapper.selectByHold(map);
    }

    @Override
    public List<TWmsInventoryEntity> findByCargoOwnerId(Long cargoOwnerId, DbShardVO dbShardVO) {
        return inventoryMapper.selectByCargoOwnerId(cargoOwnerId, getSplitTableKey(dbShardVO));
    }

    /***
     * 库存分配
     * @param shipmentDetailEntityList
     * @param dbShardVO
     * @return
     */
    @Override
    @NeedLog(type = LogType.OPREATION)
    public MessageResult updateAllocateInventoryByShipmentDetail(List<TWmsShipmentDetailEntity> shipmentDetailEntityList, Long shipmentId, TWmsShipmentHeaderEntity shipmentHeaderEntity, DbShardVO dbShardVO) {

        MessageResult result = MessageResult.getSucMessage();
        List<TWmsAllocateEntity> allocateEntityList = new ArrayList();
        Set<String> storageRoomIds = new HashSet();
        Long cargoOwnerId = shipmentHeaderEntity.getCargoOwnerId();
        //根据仓库Id查找可使用库房
        List<TWmsStorageRoomEntity> storageRooms = storageRoomService.getUseStorageRooms(dbShardVO.getWarehouseId());
        List<Long> roomIds = new ArrayList();
        storageRooms.forEach(storageRoom -> {
            roomIds.add(storageRoom.getId());
        });
        List<TWmsInventoryDTO> changeInventoryDTOList = new ArrayList();
        for (TWmsShipmentDetailEntity shipmentDetailEntity : shipmentDetailEntityList) {

            List<Long> shipmentDetailRoomIds = new ArrayList<>();
            if (shipmentDetailEntity.getStorageRoomId() != null && shipmentDetailEntity.getStorageRoomId() != "") {
                Long[] roomsArray = ArrayUtil.toLongArray(shipmentDetailEntity.getStorageRoomId().split(","));
                for (Long roomId : roomsArray) {
                    shipmentDetailRoomIds.add(roomId);
                }
            } else {
                shipmentDetailRoomIds = roomIds;
            }
            //根据商品Id查询库房是否存在该商品的库存
            List<TWmsInventoryDTO> oldInventoryDTOList = getAllocateInventoryDtoBySku(shipmentDetailEntity.getSkuId(), cargoOwnerId, shipmentDetailRoomIds, dbShardVO);
            if (CollectionUtils.isEmpty(oldInventoryDTOList)) {
                result = MessageResult.getFailMessage();
                result.setMessage("商品[" + shipmentDetailEntity.getSku() + "]没有找到库存信息.");
                return result;
            }

            int orderedQty = shipmentDetailEntity.getOrderedQty();//需要分配数量
            for (TWmsInventoryDTO inventoryDTO : oldInventoryDTOList) {
                cargoOwnerId = inventoryDTO.getCargoOwnerId();
                //如果需要分配数递减到0，则结束循环
                if (orderedQty == 0) {
                    break;
                }
                int activeQty = inventoryDTO.getActiveQty();//可分配数量 = 在库数量 - 已分配数量 - 已质押数量
                //如果当前库存记录可分配数量为0，则跳出循环，继续找到第二条可分配库存数据
                if (activeQty == 0) {
                    continue;
                }
                int allocateQty = Math.min(activeQty, orderedQty); //重新计算已分配数
                orderedQty -= allocateQty; //期望分配数
                inventoryDTO.setAllocatedQty(inventoryDTO.getAllocatedQty() + allocateQty);

                TWmsAllocateEntity allocateEntity = new TWmsAllocateEntity();
                allocateEntity.setDetailId(shipmentDetailEntity.getId());
                allocateEntity.setInventoryId(inventoryDTO.getId());
                allocateEntity.setAllocatedQty(allocateQty);
                allocateEntity.setCreateUser(dbShardVO.getCurrentUser().getUserName());
                allocateEntity.setCreateTime(new Date().getTime());
                allocateEntity.setUpdateUser(dbShardVO.getCurrentUser().getUserName());
                allocateEntity.setUpdateTime(new Date().getTime());
                allocateEntity.setAllocateUser(GlobalConstants.GLOBAL_USER_NAME);
                allocateEntity.setStorageRoomId(inventoryDTO.getStorageRoomId());
                allocateEntityList.add(allocateEntity);
                changeInventoryDTOList.add(inventoryDTO);
                storageRoomIds.add(String.valueOf(inventoryDTO.getStorageRoomId()));
            }
            //如果最后可分配数量大于0，说明库存不足
            if (orderedQty > 0) {
                result = MessageResult.getFailMessage();
                result.setMessage("商品[" + shipmentDetailEntity.getSku() + "]可用库存不足.");
                this.writeOperationLog(shipmentId, cargoOwnerId, ActionCode.Allocate.toString(), result.getMessage(), OrderTypeCode.Shipment.toString(), OperationStatusCode.Failed.toString(), dbShardVO);
                return result;
            }
        }
        //修改库存信息
        result = updateInventorys(changeInventoryDTOList, dbShardVO);
        if (!result.isSuc()) {
            result = MessageResult.getFailMessage();
            result.setMessage(result.getMessage() + "商品扣减库存失败.");
            this.writeOperationLog(shipmentId, cargoOwnerId, ActionCode.Allocate.toString(), result.getMessage(), OrderTypeCode.Shipment.toString(), OperationStatusCode.Failed.toString(), dbShardVO);
            return result;
        }
        Map resultMap = new HashMap();
        resultMap.put("storageRoomIds", StringUtils.join(storageRoomIds.toArray(), ","));
        resultMap.put("allocates", allocateEntityList);//返回分配记录
        result.setResult(resultMap);
        return result;
    }

    @Override
    public MessageResult updateInventorys(List<TWmsInventoryDTO> changeInventoryDTOList, DbShardVO dbShardVO) {
        for (TWmsInventoryDTO inventoryDTO : changeInventoryDTOList) {
            TWmsInventoryEntity inventoryEntity = BeanUtils.copyBeanPropertyUtils(inventoryDTO, TWmsInventoryEntity.class);
            Integer count = inventoryMapper.updateInventory(inventoryEntity, getSplitTableKey(dbShardVO));
            if (count == 0) {
                MessageResult mr = MessageResult.getFailMessage();
                mr.setMessage(inventoryEntity.getSkuId().toString());
                return mr;
            }
        }
        return MessageResult.getSucMessage();
    }

    /***
     * 根据商品id查询商品库存信息
     * @param skuId  商品Id
     * @param roomIds  库房Ids
     * @param dbShardVO
     * @return
     */
    private List<TWmsInventoryDTO> getAllocateInventoryDtoBySku(Long skuId, Long cargoOwnerId, List<Long> roomIds, DbShardVO
            dbShardVO) {
        List<TWmsInventoryEntity> inventoryEntityList = inventoryMapper.selectInventoryBySku(skuId, cargoOwnerId, roomIds, getSplitTableKey(dbShardVO));
        List<TWmsInventoryDTO> inventoryDTOList = inventoryEntityList.stream()
                .filter(inventoryDTO -> inventoryDTO != null)
                .map(inventoryDTO -> BeanUtils.copyBeanPropertyUtils(inventoryDTO, TWmsInventoryDTO.class))
                .collect(Collectors.toList());
        return inventoryDTOList;

    }


    /**
     * 生成库存，根据skuId,cargoOwnerId,storageRoomId查询库存管理中是否有记录
     * 如已经存在，更新库存数量即可，如不存在，则创建新纪录
     *
     * @param receiptHeaderEntity 入库头
     * @param list                入库明细
     * @param operationUser       操作人
     * @param dbShardVO
     * @return
     */
    @Override
    @NeedLog(type = LogType.INVENTORY)
    public MessageResult updateInventoryFromReceipt(TWmsReceiptHeaderEntity receiptHeaderEntity, List<TWmsReceiptDetailEntity> list, String operationUser, DbShardVO dbShardVO) {
        Long currentTime = new Date().getTime();
        if (CollectionUtils.isNotEmpty(list)) {
            for (TWmsReceiptDetailEntity r : list) {
                TWmsInventoryEntity inventoryEntity = findInventoryFromCargoOwner(r.getSkuId(), receiptHeaderEntity.getCargoOwnerId(), Long.parseLong(r.getStorageRoomId()), dbShardVO);
                if (inventoryEntity == null) {
                    TWmsInventoryEntity inventoryEntity1 = new TWmsInventoryEntity();
                    inventoryEntity1.setId(autoIdClient.getAutoId(AutoIdConstants.TWmsInventoryEntity));
                    inventoryEntity1.setTenantId(receiptHeaderEntity.getTenantId());
                    inventoryEntity1.setWarehouseId(receiptHeaderEntity.getWarehouseId());
                    inventoryEntity1.setCargoOwnerId(receiptHeaderEntity.getCargoOwnerId());
                    inventoryEntity1.setStorageRoomId(Long.parseLong(r.getStorageRoomId()));
                    inventoryEntity1.setSkuId(r.getSkuId());
                    inventoryEntity1.setPrice(r.getCostPrice());
                    inventoryEntity1.setOnhandQty(r.getReceivedQty());
                    inventoryEntity1.setInventoryStatusCode(r.getInventoryStatusCode());
                    inventoryEntity1.setCreateUser(operationUser);
                    inventoryEntity1.setCreateTime(currentTime);
                    inventoryEntity1.setUpdateUser(operationUser);
                    inventoryEntity1.setUpdateTime(currentTime);
                    inventoryMapper.insertInventory(inventoryEntity1, getSplitTableKey(dbShardVO));
                    this.writeInventoryLog(inventoryEntity1.getId(), inventoryEntity1.getSkuId(), OrderTypeCode.Receipt.toString(), receiptHeaderEntity.getId(), r.getId(), r.getReceivedQty(), dbShardVO);
                } else {
                    inventoryEntity.setOnhandQty(inventoryEntity.getOnhandQty() + r.getReceivedQty());//更新库存数量
                    inventoryEntity.setUpdateUser(operationUser);
                    inventoryEntity.setUpdateTime(currentTime);
                    inventoryMapper.updateInventory(inventoryEntity, getSplitTableKey(dbShardVO));
                    this.writeInventoryLog(inventoryEntity.getId(), inventoryEntity.getSkuId(), OrderTypeCode.QC.toString(), receiptHeaderEntity.getId(), r.getId(), r.getReceivedQty(), dbShardVO);
                }
            }
        }

        return MessageResult.getSucMessage();
    }


    @Override
    public MessageResult batchUpdateFromSubmitFrozen(List<TWmsInventoryEntity> inventoryEntityList, DbShardVO dbShardVO) {
        for (TWmsInventoryEntity inventoryEntity : inventoryEntityList) {
            inventoryMapper.updateInventory(inventoryEntity, getSplitTableKey(dbShardVO));
        }
        return MessageResult.getSucMessage();
    }

    /**
     * 根据解押单据来更新仓库内库存，这是库内肯定有记录，无需判断直接更新即可
     *
     * @param inventoryEntityList 需要更新的库存信息实体类
     * @param dbShardVO           分库标识
     * @return
     */
    @Override
    @NeedLog(type = LogType.INVENTORY)
    public MessageResult updateInventoryFromUnfrozen(List<TWmsInventoryEntity> inventoryEntityList, DbShardVO dbShardVO) {
        for (TWmsInventoryEntity inventoryEntity : inventoryEntityList) {
            inventoryMapper.updateInventory(inventoryEntity, getSplitTableKey(dbShardVO));
        }
        return MessageResult.getSucMessage();
    }

    /**
     * 查找库存报表
     *
     * @param inventoryDTO 查询条件DTO
     * @param list         商品ID list
     * @param dbShardVO
     * @return
     */
    @Override
    public List<TWmsInventoryEntity> findReportInventory(TWmsInventoryDTO inventoryDTO, List<Long> list, Long fromTime, Long toTime, DbShardVO dbShardVO) {
        TWmsInventoryEntity inventoryEntity = BeanUtils.copyBeanPropertyUtils(inventoryDTO, TWmsInventoryEntity.class);
        return inventoryMapper.selectInventoryReport(inventoryEntity, list, fromTime, toTime, getSplitTableKey(dbShardVO));
    }

    /**
     * 查询库存汇总
     *
     * @param inventoryDTO 查询条件DTO
     * @param list         商品ID list
     * @param dbShardVO
     * @return
     */
    @Override
    public List<TWmsInventoryEntity> selectReportSumMap(TWmsInventoryDTO inventoryDTO, List<Long> list, Long fromTime, Long toTime, DbShardVO dbShardVO) {
        TWmsInventoryEntity inventoryEntity = BeanUtils.copyBeanPropertyUtils(inventoryDTO, TWmsInventoryEntity.class);
        return inventoryMapper.selectInventoryReportSum(inventoryEntity, list, fromTime, toTime, getSplitTableKey(dbShardVO));
    }

    @Override
    public Integer queryInventorySumPageCount(TWmsInventoryDTO inventoryDTO, List<Long> list, Long fromTime, Long toTime, DbShardVO dbShardVO) {
        TWmsInventoryEntity inventoryEntity = BeanUtils.copyBeanPropertyUtils(inventoryDTO, TWmsInventoryEntity.class);
        return inventoryMapper.queryInventorySumPageCount(inventoryEntity, list, fromTime, toTime, getSplitTableKey(dbShardVO));
    }

    @Override
    public Integer queryInventoryReportPageCount(TWmsInventoryDTO inventoryDTO, List<Long> list, Long fromTime, Long toTime, DbShardVO dbShardVO) {
        TWmsInventoryEntity inventoryEntity = BeanUtils.copyBeanPropertyUtils(inventoryDTO, TWmsInventoryEntity.class);
        return inventoryMapper.queryInventoryReportPageCount(inventoryEntity, list, fromTime, toTime, getSplitTableKey(dbShardVO));
    }


    @Override
    public List<TWmsInventoryEntity> getInventoryList(TWmsInventoryDTO inventoryDTO, DbShardVO dbShardVO) {
        TWmsInventoryEntity inventoryEntity = BeanUtils.copyBeanPropertyUtils(inventoryDTO, TWmsInventoryEntity.class);
        return inventoryMapper.queryInventoryPages(inventoryEntity, getSplitTableKey(dbShardVO));
    }

    @Override
    public List<TWmsInventoryEntity> findByIds(List<Long> ids, DbShardVO dbShardVO) {
        return inventoryMapper.selectByIds(ids, getSplitTableKey(dbShardVO));

    }


    /***
     * 库存明细查询
     * @param dbShardVO
     * @return
     */
    @Override
    public PageResponse<List> getReportInventory(TWmsReportInventoryDTO reportInventoryDTO, DbShardVO dbShardVO) {
        TWmsInventoryDTO inventoryDTO = new TWmsInventoryDTO();
        TWmsSkuDTO skuDTO = new TWmsSkuDTO();
        skuEntiyEvaluate(reportInventoryDTO, skuDTO);//组装商品实体类
        Long fromTime = reportInventoryDTO.getInventoryCreateTimeFrom();
        Long toTime = reportInventoryDTO.getInventoryCreateTimeTo();
        List<Long> list = new ArrayList<>();
        if (skuDTO.getSku() != null || skuDTO.getBarcode() != null || skuDTO.getItemName() != null) {
            list = skuService.findSkuLists(skuDTO, getSkuDbShareVO(dbShardVO));
        }
        inventoryDTO.setCargoOwnerId(reportInventoryDTO.getCargoOwnerId());
        inventoryDTO.setStorageRoomId(reportInventoryDTO.getStorageRoomId());
        inventoryDTO.setPage(reportInventoryDTO.getPage());
        inventoryDTO.setPageSize(reportInventoryDTO.getPageSize());
        inventoryDTO.setOffset(reportInventoryDTO.getOffset());
        //根据输入的条件进行查询
        List<TWmsInventoryEntity> inventoryEntities = this.findReportInventory(inventoryDTO, list, fromTime, toTime, dbShardVO);

        if (CollectionUtils.isEmpty(inventoryEntities)) {
            return null;
        }
        List<Long> skuIdList = new ArrayList<Long>();
        for (TWmsInventoryEntity i : inventoryEntities) {
            skuIdList.add(i.getSkuId());
        }
        List<TWmsSkuEntity> skuEntities = skuService.findByIdList(skuIdList, getSkuDbShareVO(dbShardVO));
        List<TWmsReportInventoryDTO> mapList = new ArrayList();
        for (TWmsInventoryEntity x : inventoryEntities) {
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
            reportInventoryDTO1.setReceiptTime(y.getCreateTime());
            reportInventoryDTO1.setIsHold(y.getIsHold());
            reportInventoryDTO1.setCreateTime(y.getCreateTime());
            reportInventoryDTO1.setUpdateUser(y.getUpdateUser());
            reportInventoryDTO1.setUpdateTime(y.getUpdateTime());
            mapList.add(reportInventoryDTO1);
        }
        Integer totalSize = queryInventoryReportPageCount(inventoryDTO, list, fromTime, toTime, dbShardVO);
        PageResponse<List> response = new PageResponse();
        response.setTotal(totalSize);
        response.setRows(mapList);
        return response;
    }


    private void skuEntiyEvaluate(TWmsReportInventoryDTO reportInventoryDTO, TWmsSkuDTO skuDTO) {
        if (reportInventoryDTO.getSku() != null && !reportInventoryDTO.getSku().equals("")) {
            skuDTO.setSku(reportInventoryDTO.getSku());
        }
        if (reportInventoryDTO.getSkuBarcode() != null && !reportInventoryDTO.getSkuBarcode().equals("")) {
            skuDTO.setBarcode(reportInventoryDTO.getSkuBarcode());
        }
        if (reportInventoryDTO.getSkuName() != null && !reportInventoryDTO.getSkuName().equals("")) {
            skuDTO.setItemName(reportInventoryDTO.getSkuName());
        }
    }

    private void turns(List<TWmsSkuEntity> skuEntities, TWmsReportInventoryDTO reportInventoryDTO1, TWmsInventoryEntity x) {
        for (TWmsSkuEntity s : skuEntities) {
            if (s.getId().equals(x.getSkuId())) {
                reportInventoryDTO1.setSkuId(s.getId());
                reportInventoryDTO1.setSku(s.getSku());
                reportInventoryDTO1.setSkuBarcode(s.getBarcode());
                reportInventoryDTO1.setSkuName(s.getItemName());
                reportInventoryDTO1.setUnitType(s.getUnitType());
                reportInventoryDTO1.setSpec(s.getSpec());
                break;
            }
        }
    }


    @Override
    @NeedLog(type = LogType.INVENTORY)
    public MessageResult updateInventoryFromProInventory(TWmsProInventoryEntity proInventoryEntity, String operationUser, DbShardVO dbShardVO) {
        TWmsInventoryEntity inventoryEntity = findInventoryFromCargoOwner(proInventoryEntity.getSkuId(), proInventoryEntity.getCargoOwnerId(), proInventoryEntity.getStorageRoomId(), dbShardVO);
        if (inventoryEntity == null) {
            TWmsInventoryEntity x = new TWmsInventoryEntity();
            x.setId(autoIdClient.getAutoId(AutoIdConstants.TWmsInventoryEntity));
            x.setTenantId(proInventoryEntity.getTenantId());
            x.setWarehouseId(proInventoryEntity.getWarehouseId());
            x.setCargoOwnerId(proInventoryEntity.getCargoOwnerId());
            x.setStorageRoomId(proInventoryEntity.getStorageRoomId());
            x.setSkuId(proInventoryEntity.getSkuId());
            x.setOnhandQty(proInventoryEntity.getQty());
            x.setInventoryStatusCode(InventoryStatusCode.GOOD.toString());
            x.setCreateUser(operationUser);
            x.setCreateTime(new Date().getTime());
            x.setUpdateUser(operationUser);
            x.setUpdateTime(new Date().getTime());
            inventoryMapper.insertInventory(x, getSplitTableKey(dbShardVO));
            this.writeInventoryLog(x.getId(), x.getSkuId(), OrderTypeCode.PROCESSING.toString(), proInventoryEntity.getParentId(), proInventoryEntity.getId(), proInventoryEntity.getQty(), dbShardVO);
        } else {
            inventoryEntity.setOnhandQty(inventoryEntity.getOnhandQty() + proInventoryEntity.getQty());
            inventoryEntity.setUpdateUser(operationUser);
            inventoryEntity.setUpdateTime(new Date().getTime());
            inventoryMapper.updateInventory(inventoryEntity, getSplitTableKey(dbShardVO));
            this.writeInventoryLog(inventoryEntity.getId(), inventoryEntity.getSkuId(), OrderTypeCode.PROCESSING.toString(), proInventoryEntity.getParentId(), proInventoryEntity.getId(), proInventoryEntity.getQty(), dbShardVO);
        }
        return MessageResult.getSucMessage();
    }


    @Override
    @NeedLog(type = LogType.INVENTORY)
    public MessageResult updateInventoryFromProMaterial(TWmsInventoryEntity inventoryEntity, TWmsProInventoryEntity proInventoryEntity, String operationUser, DbShardVO dbShardVO) {
        inventoryEntity.setWorkingQty(inventoryEntity.getWorkingQty() - proInventoryEntity.getQty());
        inventoryEntity.setOnhandQty(inventoryEntity.getOnhandQty() - proInventoryEntity.getQty());
        inventoryEntity.setUpdateUser(operationUser);
        inventoryEntity.setUpdateTime(new Date().getTime());
        inventoryMapper.updateInventory(inventoryEntity, getSplitTableKey(dbShardVO));
        this.writeInventoryLog(inventoryEntity.getId(), inventoryEntity.getSkuId(), OrderTypeCode.PROCESSING.toString(), proInventoryEntity.getId(), proInventoryEntity.getId(), 0 - proInventoryEntity.getQty(), dbShardVO);
        return MessageResult.getSucMessage();
    }


    @Override
    @NeedLog(type = LogType.INVENTORY)
    public MessageResult updateInventoryFromAdjustment(TWmsAdjustmentHeaderEntity adjustmentHeaderEntity, TWmsAdjustmentDetailEntity adjustmentDetailEntity, String operationUser, Long SkuId, Long CargoOwnerId, Long StorageRoomId, DbShardVO dbShardVO) {
        TWmsInventoryEntity inventoryEntity = findInventoryFromCargoOwner(SkuId, CargoOwnerId, StorageRoomId, dbShardVO);
        if (inventoryEntity == null) {
            TWmsInventoryDTO inventoryDTO = new TWmsInventoryDTO();
            TWmsSkuEntity skuEntity = skuService.findByPrimaryKey(SkuId, getSkuDbShareVO(dbShardVO));
            inventoryDTO.setId(autoIdClient.getAutoId(AutoIdConstants.TWmsInventoryEntity));
            inventoryDTO.setTenantId(adjustmentHeaderEntity.getTenantId());
            inventoryDTO.setWarehouseId(adjustmentHeaderEntity.getWarehouseId());
            inventoryDTO.setCargoOwnerId(adjustmentHeaderEntity.getCargoOwnerId());
            inventoryDTO.setSkuId(adjustmentDetailEntity.getSkuId());
            inventoryDTO.setStorageRoomId(adjustmentDetailEntity.getStorageRoomId());
            inventoryDTO.setPrice(skuEntity.getCostPrice());
            inventoryDTO.setOnhandQty(adjustmentDetailEntity.getAdjustedAfterQty());
            inventoryDTO.setCreateUser(operationUser);
            inventoryDTO.setCreateTime(new Date().getTime());
            inventoryDTO.setUpdateUser(operationUser);
            inventoryDTO.setUpdateTime(new Date().getTime());
            addInventory(inventoryDTO, dbShardVO);
            this.writeInventoryLog(inventoryDTO.getId(), inventoryDTO.getSkuId(), OrderTypeCode.Adjustment.toString(), adjustmentDetailEntity.getAdjustId(), adjustmentDetailEntity.getId(), adjustmentDetailEntity.getAdjustedQty(), dbShardVO);
        } else {
            //审核时改变货物数量和解冻
            inventoryEntity.setOnhandQty(inventoryEntity.getOnhandQty() + adjustmentDetailEntity.getAdjustedQty());
            inventoryEntity.setIsHold(new Byte("0"));
            inventoryEntity.setUpdateUser(operationUser);
            inventoryEntity.setUpdateTime(new Date().getTime());
            modifyInventory(BeanUtils.copyBeanPropertyUtils(inventoryEntity, TWmsInventoryDTO.class), dbShardVO);
            this.writeInventoryLog(inventoryEntity.getId(), inventoryEntity.getSkuId(), OrderTypeCode.Adjustment.toString(), adjustmentDetailEntity.getAdjustId(), adjustmentDetailEntity.getId(), adjustmentDetailEntity.getAdjustedQty(), dbShardVO);
        }
        return MessageResult.getSucMessage();
    }

    @Override
    public PageResponse<List<TWmsInventoryEntity>> queryInventoryByCargoOwnerId(Map map, TWmsInventoryDTO inventoryDTO, DbShardVO dbShardVO) {
        List<TWmsSkuEntity> skuEntityList = new ArrayList<>();
        List<Long> skuIdList = new ArrayList<Long>();
        if (map.containsKey("sku") && org.apache.commons.lang.StringUtils.isNotEmpty(MapUtils.getString(map, "sku"))
                || map.containsKey("itemName") && org.apache.commons.lang.StringUtils.isNotEmpty(MapUtils.getString(map, "itemName"))) {
            skuEntityList = skuService.findSkuCargoOwner(MapUtils.getString(map, "sku"), MapUtils.getString(map, "itemName"), inventoryDTO.getCargoOwnerId(), null, getSkuDbShareVO(dbShardVO));
            if (CollectionUtils.isEmpty(skuEntityList)) {
                return null;
            }
        }
        for (TWmsSkuEntity skuEntity : skuEntityList) {
            skuIdList.add(skuEntity.getId());
        }
        //根据输入的条件进行查询
        List<TWmsInventoryEntity> inventoryEntities = findReportInventory(inventoryDTO, skuIdList, null, null, dbShardVO);
        TWmsInventoryEntity inventoryEntity = BeanUtils.copyBeanPropertyUtils(inventoryDTO, TWmsInventoryEntity.class);
        if (CollectionUtils.isEmpty(inventoryEntities)) {
            return null;
        }
        List<TWmsInventoryEntity> inventoryEntityListDTO = new ArrayList<>();
        inventoryEntities.stream().forEach(x -> {
            TWmsSkuEntity skuEntity = skuService.findByPrimaryKey(x.getSkuId(), getSkuDbShareVO(dbShardVO));
            if (skuEntity != null) {
                x.setSku(skuEntity.getSku());
                x.setSkuName(skuEntity.getItemName());
                x.setBarcode(skuEntity.getBarcode());
                x.setSpec(skuEntity.getSpec());
                x.setUnitType(skuEntity.getUnitType());
                x.setActiveQty(x.countActiveQty());
            }
            inventoryEntityListDTO.add(x);
        });
        Integer totalSize = inventoryMapper.queryInventoryPageCount(inventoryEntity, getSplitTableKey(dbShardVO));
        PageResponse<List<TWmsInventoryEntity>> response = new PageResponse<>();
        response.setRows(inventoryEntityListDTO);
        response.setTotal(totalSize);
        return response;
    }

    @Override
    @NeedLog(type = LogType.INVENTORY)
    public MessageResult updateInventoryFromProPackage(TWmsProPackageEntity proPackageEntity, List<TWmsProPackageDetailEntity> proPackageDetailEntityList, String operationUser, DbShardVO dbShardVO) {
        //头表大礼包库存新增
        TWmsInventoryEntity inventoryEntity = findInventoryFromCargoOwner(proPackageEntity.getSkuId(), proPackageEntity.getCargoOwnerId(), proPackageEntity.getStorageRoomId(), dbShardVO);
        if (inventoryEntity == null) {
            TWmsInventoryDTO inventoryDTO = new TWmsInventoryDTO();
            TWmsSkuEntity skuEntity = skuService.findByPrimaryKey(proPackageEntity.getSkuId(), getSkuDbShareVO(dbShardVO));
            inventoryDTO.setId(autoIdClient.getAutoId(AutoIdConstants.TWmsInventoryEntity));
            inventoryDTO.setTenantId(proPackageEntity.getTenantId());
            inventoryDTO.setWarehouseId(proPackageEntity.getWarehouseId());
            inventoryDTO.setCargoOwnerId(proPackageEntity.getCargoOwnerId());
            inventoryDTO.setSkuId(proPackageEntity.getSkuId());
            inventoryDTO.setStorageRoomId(proPackageEntity.getStorageRoomId());
            inventoryDTO.setPrice(skuEntity.getCostPrice());

            inventoryDTO.setOnhandQty(proPackageEntity.getQty());

            inventoryDTO.setCreateUser(operationUser);
            inventoryDTO.setCreateTime(new Date().getTime());
            inventoryDTO.setUpdateUser(operationUser);
            inventoryDTO.setUpdateTime(new Date().getTime());
            addInventory(inventoryDTO, dbShardVO);
            this.writeInventoryLog(inventoryDTO.getId(), inventoryDTO.getSkuId(), OrderTypeCode.PROPACKAGE.toString(), proPackageEntity.getId(), proPackageEntity.getId(), proPackageEntity.getQty(), dbShardVO);
        } else {
            inventoryEntity.setOnhandQty(proPackageEntity.getQty() + inventoryEntity.getOnhandQty());
            inventoryEntity.setUpdateUser(operationUser);
            inventoryEntity.setUpdateTime(new Date().getTime());
            modifyInventory(BeanUtils.copyBeanPropertyUtils(inventoryEntity, TWmsInventoryDTO.class), dbShardVO);
            this.writeInventoryLog(inventoryEntity.getId(), inventoryEntity.getSkuId(), OrderTypeCode.PROPACKAGE.toString(), inventoryEntity.getId(), proPackageEntity.getId(), proPackageEntity.getQty(), dbShardVO);
        }
        //明细商品减少
        for (TWmsProPackageDetailEntity proPackageDetailEntity : proPackageDetailEntityList) {
            TWmsInventoryEntity inventoryDetailEntity = findById(proPackageDetailEntity.getProInventoryId(), dbShardVO);
            if (inventoryDetailEntity.getIsHold() == 1) {
                return MessageResult.getMessage("E12013");
            }
            inventoryDetailEntity.setOnhandQty(inventoryDetailEntity.getOnhandQty() - proPackageDetailEntity.getProInventoryQty());
            inventoryDetailEntity.setPackageQty(inventoryDetailEntity.getPackageQty() - proPackageDetailEntity.getProInventoryQty());
            inventoryDetailEntity.setUpdateUser(operationUser);
            inventoryDetailEntity.setUpdateTime(new Date().getTime());
            modifyInventory(BeanUtils.copyBeanPropertyUtils(inventoryDetailEntity, TWmsInventoryDTO.class), dbShardVO);
            this.writeInventoryLog(inventoryDetailEntity.getId(), inventoryDetailEntity.getSkuId(), OrderTypeCode.PROPACKAGE.toString(), proPackageDetailEntity.getParentId(), proPackageDetailEntity.getId(), -proPackageDetailEntity.getProInventoryQty(), dbShardVO);
        }
        return MessageResult.getSucMessage();
    }

    /***
     * 库存集成策略分配
     * @param shipmentDetailEntityList
     * @param dbShardVO
     * @return
     */
    @Override
    @NeedLog(type = LogType.OPREATION)
    public MessageResult updateAllocateInventory(List<TWmsShipmentDetailEntity> shipmentDetailEntityList, Long shipmentId, TWmsShipmentHeaderEntity shipmentHeaderEntity, DbShardVO dbShardVO) {
        MessageResult result = MessageResult.getSucMessage();
        List<TWmsAllocateEntity> allocateEntityList = new ArrayList();
        List<Long> allocationStrategyroomIds = new ArrayList();//可分配库房

        TWmsAllocationStrategyEntity allocationStrategyEntity = allocationStrategyService.queryAllocationStrategy(shipmentHeaderEntity.getWarehouseId());//查询策略
        String orderBy = null;
        String ascOrDesc = null;
        //读取默认策略
        if (allocationStrategyEntity != null) {
            if (allocationStrategyEntity.getStorageRoomId() != null && !allocationStrategyEntity.getStorageRoomId().equals("")) {
                Long[] ids = ArrayUtil.toLongArray(allocationStrategyEntity.getStorageRoomId().split(","));
                allocationStrategyroomIds = Arrays.asList(ids);
            }
            //排序属性
            orderBy = allocationStrategyEntity.getOrderFieldCode().equals(AllocationStrategy.LOT_ATTRIBUTE.toString()) ? AllocationStrategy.LOT_ATTRIBUTE.toSql() :
                    allocationStrategyEntity.getOrderFieldCode().equals(AllocationStrategy.RECEIPT_TIME.toString()) ? AllocationStrategy.RECEIPT_TIME.toSql() : "";
            //进出策略(先进先出或者后进先出)
            ascOrDesc = allocationStrategyEntity.getDirectionCode().equals(AllocationStrategy.FILO.toString()) ? AllocationStrategy.FILO.toString() : AllocationStrategy.FIFO.toSql();
        } else {
            orderBy = AllocationStrategy.RECEIPT_TIME.toSql();//默认时间排序方法
            ascOrDesc = AllocationStrategy.FIFO.toSql();//默认先进先出
        }
        if(allocationStrategyroomIds.size() < 1){
            //如果没有设置库存分配策略,则取默认所有可用库房
            List<TWmsStorageRoomEntity> storageRooms = storageRoomService.getUseStorageRooms(dbShardVO.getWarehouseId());
            for (TWmsStorageRoomEntity storageRoom : storageRooms) {
                allocationStrategyroomIds.add(storageRoom.getId());
            }
        }

        List<TWmsInventoryDTO> changeInventoryDTOList = new ArrayList();
        for (TWmsShipmentDetailEntity shipmentDetailEntity : shipmentDetailEntityList) {
            Set<String> storageRoomIds = new HashSet();
            List<Long> roomIds = new ArrayList<>();
            //已选择库房则按照选择的库房进行分配
            String partOrderBy;
            String partAscOrDesc;
            if (shipmentDetailEntity.getStorageRoomId() != null && shipmentDetailEntity.getStorageRoomId() != "") {
                Long[] storageRoomIdArr = shipmentDetailEntity.getStorageRoomIds();
                for (int i = 0; i < storageRoomIdArr.length; i++) {
                    roomIds.add(storageRoomIdArr[i]);
                }
                partOrderBy = AllocationStrategy.RECEIPT_TIME.toSql();//默认时间排序方法
                partAscOrDesc = AllocationStrategy.FIFO.toSql();//默认先进先出
            } else {
                //如果明细没有指定库房信息
                roomIds = allocationStrategyroomIds;
                partOrderBy = orderBy;
                partAscOrDesc = ascOrDesc;
            }
            //根据商品Id查询库房是否存在该商品的库存
            List<TWmsInventoryDTO> oldInventoryDTOList = getAllocateInventoryDtoBySku(shipmentDetailEntity.getSkuId(), shipmentHeaderEntity.getCargoOwnerId(), roomIds, partOrderBy, partAscOrDesc, dbShardVO);
            if (CollectionUtils.isEmpty(oldInventoryDTOList)) {
                result = MessageResult.getFailMessage();
                result.setMessage("商品[" + shipmentDetailEntity.getSku() + "]在库房中没有找到库存信息.");
                return result;
            }
            int orderedQty = shipmentDetailEntity.getOrderedQty();//需要分配数量
            for (TWmsInventoryDTO inventoryDTO : oldInventoryDTOList) {
                //如果需要分配数递减到0，则结束循环
                if (orderedQty == 0) {
                    break;
                }
                int activeQty = inventoryDTO.getActiveQty();//可分配数量 = 在库数量 - 已分配数量 - 已质押数量
                //如果当前库存记录可分配数量为0，则跳出循环，继续找到第二条可分配库存数据
                if (activeQty == 0) {
                    continue;
                }
                int allocateQty = Math.min(activeQty, orderedQty); //重新计算已分配数
                orderedQty -= allocateQty; //期望分配数
                allocate(inventoryDTO, shipmentDetailEntity, allocateQty, allocateEntityList, storageRoomIds, changeInventoryDTOList, dbShardVO);
            }
            //如果最后可分配数量大于0，说明库存不足
            if (orderedQty > 0) {
                result = MessageResult.getFailMessage();
                result.setMessage("商品[" + shipmentDetailEntity.getSku() + "]可用库存不足.");
                this.writeOperationLog(shipmentId, shipmentHeaderEntity.getCargoOwnerId(), ActionCode.Allocate.toString(), result.getMessage(), OrderTypeCode.Shipment.toString(), OperationStatusCode.Failed.toString(), dbShardVO);
                return result;
            }
            shipmentDetailEntity.setStorageRoomId(StringUtils.join(storageRoomIds.toArray(), ","));
        }
        //修改库存信息
        result = updateInventorys(changeInventoryDTOList, dbShardVO);
        if (!result.isSuc()) {
            result = MessageResult.getFailMessage();
            result.setMessage(result.getMessage() + "商品扣减库存失败.");
            this.writeOperationLog(shipmentId, shipmentHeaderEntity.getCargoOwnerId(), ActionCode.Allocate.toString(), result.getMessage(), OrderTypeCode.Shipment.toString(), OperationStatusCode.Failed.toString(), dbShardVO);
            return result;
        }
        Map resultMap = new HashMap();
        resultMap.put("shipmentDetailEntityList", shipmentDetailEntityList);
        resultMap.put("allocates", allocateEntityList);//返回分配记录
        result.setResult(resultMap);
        return result;
    }

    private List<TWmsInventoryDTO> getAllocateInventoryDtoBySku(Long skuId, Long cargoOwnerId, List<Long> roomIds, String orderBy, String ascOrDesc, DbShardVO
            dbShardVO) {
        List<TWmsInventoryEntity> inventoryEntityList = inventoryMapper.selectInventoryAllocationStrategy(skuId, cargoOwnerId, roomIds, orderBy, ascOrDesc, getSplitTableKey(dbShardVO));
        List<TWmsInventoryDTO> inventoryDTOList = inventoryEntityList.stream()
                .filter(inventoryDTO -> inventoryDTO != null)
                .map(inventoryDTO -> BeanUtils.copyBeanPropertyUtils(inventoryDTO, TWmsInventoryDTO.class))
                .collect(Collectors.toList());
        return inventoryDTOList;

    }

    /**
     * 库存表生成分配表
     *
     * @param inventoryDTO
     * @param shipmentDetailEntity
     * @param allocateQty
     * @param allocateEntityList
     * @param storageRoomIds
     * @param dbShardVO
     */
    private void allocate(TWmsInventoryDTO inventoryDTO, TWmsShipmentDetailEntity shipmentDetailEntity, Integer allocateQty,
                          List<TWmsAllocateEntity> allocateEntityList, Set<String> storageRoomIds,
                          List<TWmsInventoryDTO> changeInventoryDTOList, DbShardVO dbShardVO) {
        inventoryDTO.setAllocatedQty(inventoryDTO.getAllocatedQty() + allocateQty);
        TWmsAllocateEntity allocateEntity = new TWmsAllocateEntity();
        allocateEntity.setDetailId(shipmentDetailEntity.getId());
        allocateEntity.setInventoryId(inventoryDTO.getId());
        allocateEntity.setAllocatedQty(allocateQty);
        allocateEntity.setCreateUser(dbShardVO.getCurrentUser().getUserName());
        allocateEntity.setCreateTime(new Date().getTime());
        allocateEntity.setUpdateUser(dbShardVO.getCurrentUser().getUserName());
        allocateEntity.setUpdateTime(new Date().getTime());
        allocateEntity.setAllocateUser(GlobalConstants.GLOBAL_USER_NAME);
        allocateEntity.setStorageRoomId(inventoryDTO.getStorageRoomId());
        allocateEntityList.add(allocateEntity);
        changeInventoryDTOList.add(inventoryDTO);
        storageRoomIds.add(String.valueOf(inventoryDTO.getStorageRoomId()));
    }


    /**
     * 出库通知单明细核查在库可用数量是否满足添加的数量
     *
     * @param storageRoomIds
     * @param skuId
     * @param cargoOwnerId
     * @param dbShardVO
     * @return
     */
    @Override
    public Integer queryActiveQty(Long[] storageRoomIds, Long skuId, Long cargoOwnerId, DbShardVO dbShardVO) {
        return inventoryMapper.queryActiveQty(storageRoomIds, skuId, cargoOwnerId, getSplitTableKey(dbShardVO));
    }

    @Override
    public List<TWmsInventoryEntity> selectInventorBySkuId(Long skuId, DbShardVO dbShardVO) {
        return inventoryMapper.queryInventorBySkuId(skuId,getSplitTableKey(dbShardVO));
    }

    @Override
    public MessageResult addInventory(List<TWmsInventoryDTO> list, DbShardVO dbShardVO) {
        for (TWmsInventoryDTO dto:list) {
            addInventory(dto,dbShardVO);
        }
        return MessageResult.getSucMessage();
    }
}
