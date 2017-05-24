package com.huamengtong.wms.inventory.service;


import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.inventory.TWmsInventoryDTO;
import com.huamengtong.wms.dto.report.TWmsReportInventoryDTO;
import com.huamengtong.wms.entity.inventory.TWmsInventoryEntity;
import com.huamengtong.wms.entity.inventory.TWmsProInventoryEntity;
import com.huamengtong.wms.entity.inwh.TWmsAdjustmentDetailEntity;
import com.huamengtong.wms.entity.inwh.TWmsAdjustmentHeaderEntity;
import com.huamengtong.wms.entity.inwh.TWmsProPackageDetailEntity;
import com.huamengtong.wms.entity.inwh.TWmsProPackageEntity;
import com.huamengtong.wms.entity.inwh.TWmsReceiptDetailEntity;
import com.huamengtong.wms.entity.inwh.TWmsReceiptHeaderEntity;
import com.huamengtong.wms.entity.outwh.TWmsShipmentDetailEntity;
import com.huamengtong.wms.entity.outwh.TWmsShipmentHeaderEntity;

import java.util.List;
import java.util.Map;


public interface IInventoryService {


    PageResponse<List<TWmsInventoryEntity>> queryInventoryPages(TWmsInventoryDTO inventoryDTO,DbShardVO dbShardVO);

    Integer queryInventoryPageCount(TWmsInventoryDTO inventoryDTO,DbShardVO dbShardVO);

    MessageResult addInventory(TWmsInventoryDTO inventoryDTO, DbShardVO dbShardVO);

    MessageResult modifyInventory(TWmsInventoryDTO inventoryDTO,DbShardVO dbShardVO);

    MessageResult removeInventory(Long id,DbShardVO dbShardVO);

    TWmsInventoryEntity findById(Long id,DbShardVO dbShardVO);

    TWmsInventoryEntity findInventoryFromCargo(Long skuId,Long cargoOwnerId,Long storageRoomId,String inventoryStatusCode,DbShardVO dbShardVO);

    TWmsInventoryEntity findInventoryFromCargoOwner(Long skuId,Long cargoOwnerId,Long storageRoomId,DbShardVO dbShardVO);

    MessageResult updateAllocateInventoryByShipmentDetail(List<TWmsShipmentDetailEntity> shipmentDetailEntityList, Long shipmentId, TWmsShipmentHeaderEntity shipmentHeaderEntity, DbShardVO dbShardVO);

    List<TWmsInventoryEntity> findInventoryStorageRoom(Long cargoOwnerId, Long storageRoomId, DbShardVO dbShardVO);


    List<TWmsInventoryEntity> findInventoryHold(Long cargoOwnerId, Long storageRoomId, DbShardVO dbShardVO);

    List<TWmsInventoryEntity> findByCargoOwnerId(Long cargoOwnerId,DbShardVO dbShardVO);

    MessageResult updateInventorys(List<TWmsInventoryDTO> changeInventoryDTOList, DbShardVO dbShardVO);

    MessageResult updateInventoryFromReceipt(TWmsReceiptHeaderEntity receiptHeaderEntity, List<TWmsReceiptDetailEntity> list, String operationUser, DbShardVO dbShardVO);

    MessageResult batchUpdateFromSubmitFrozen(List<TWmsInventoryEntity> inventoryEntityList,DbShardVO dbShardVO);

    MessageResult updateInventoryFromUnfrozen(List<TWmsInventoryEntity> inventoryEntityList, DbShardVO dbShardVO);

    List<TWmsInventoryEntity> findReportInventory(TWmsInventoryDTO inventoryDTO,List<Long>list,Long fromTime,Long toTime,DbShardVO dbShardVO);

    List<TWmsInventoryEntity> selectReportSumMap(TWmsInventoryDTO inventoryDTO,List<Long> list,Long fromTime,Long toTime, DbShardVO dbShardVO);

    Integer queryInventorySumPageCount(TWmsInventoryDTO inventoryDTO,List<Long> list,Long fromTime,Long toTime,DbShardVO dbShardVO);

    Integer queryInventoryReportPageCount(TWmsInventoryDTO inventoryDTO,List<Long> list,Long fromTime,Long toTime,DbShardVO dbShardVO);


    List<TWmsInventoryEntity> getInventoryList(TWmsInventoryDTO inventoryDTO,DbShardVO dbShardVO);

    List<TWmsInventoryEntity> findByIds(List<Long> ids,DbShardVO dbShardVO);

    PageResponse<List> getReportInventory(TWmsReportInventoryDTO reportInventoryDTO, DbShardVO dbShardVO);

    MessageResult updateInventoryFromProInventory(TWmsProInventoryEntity proInventoryEntity,String operationUser,DbShardVO dbShardVO);

    MessageResult updateInventoryFromProMaterial(TWmsInventoryEntity inventoryEntity,TWmsProInventoryEntity proInventoryEntity ,String operationUser,DbShardVO dbShardVO);

    MessageResult updateInventoryFromAdjustment (TWmsAdjustmentHeaderEntity adjustmentHeaderEntity, TWmsAdjustmentDetailEntity adjustmentDetailEntity, String operationUser, Long SkuId, Long CargoOwnerId, Long StorageRoomId, DbShardVO dbShardVO);

    PageResponse<List<TWmsInventoryEntity>> queryInventoryByCargoOwnerId(Map map,TWmsInventoryDTO inventoryDTO, DbShardVO dbShardVO);

    MessageResult updateInventoryFromProPackage(TWmsProPackageEntity proPackageEntity,List<TWmsProPackageDetailEntity> proPackageDetailEntityList, String operationUser,DbShardVO dbShardVO);


    MessageResult updateAllocateInventory(List<TWmsShipmentDetailEntity> shipmentDetailEntityList, Long shipmentId, TWmsShipmentHeaderEntity shipmentHeaderEntity, DbShardVO dbShardVO);

    Integer queryActiveQty(Long[] storageRoomIds,Long skuId,Long cargoOwnerId,DbShardVO dbShardVO);

    List<TWmsInventoryEntity> selectInventorBySkuId(Long skuId,DbShardVO dbShardVO);

    MessageResult addInventory(List<TWmsInventoryDTO> list, DbShardVO dbShardVO);

}
