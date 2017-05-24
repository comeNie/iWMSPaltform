package com.huamengtong.wms.outwh.service;

import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.domain.TWmsShipmentHeaderPrintEntity;
import com.huamengtong.wms.dto.outwh.TWmsDnInvoiceDTO;
import com.huamengtong.wms.dto.outwh.TWmsSaleOrderDTO;
import com.huamengtong.wms.dto.outwh.TWmsShipmentHeaderDTO;
import com.huamengtong.wms.entity.outwh.TWmsShipmentHeaderEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by mario on 2016/10/31.
 */
public interface IShipmentHeaderService {

    PageResponse<List<TWmsShipmentHeaderEntity>> getShipmentHeader(TWmsShipmentHeaderDTO shipmentHeaderDTO, DbShardVO dbShardVO);

    TWmsShipmentHeaderEntity findByPrimaryKey(Long id, DbShardVO dbShardVO);

    MessageResult createShipmentHeader(TWmsShipmentHeaderDTO shipmentHeaderDTO, DbShardVO dbShardVO);

    Map queryShipmentBasics(Map paramMap, DbShardVO dbShardVO);

    MessageResult removeShipmentHeader(Long id, DbShardVO dbShardVO);

    MessageResult modifyShipmentHeader(TWmsShipmentHeaderDTO shipmentHeaderDTO, DbShardVO dbShardVO);

    MessageResult updateShipmentHeaderStatus(Long id, String statusCode,String operationUser, DbShardVO dbShardVO);

    MessageResult batchDeleteShipmentHeaders(Long[] ShipmentIds, String operationUser, DbShardVO dbShardVO);

    Map queryAllocateResult(Map paramMap, DbShardVO dbShardVO);

    MessageResult modifyShipmentOrderAndInvoice(Long shipmentId, TWmsSaleOrderDTO order, TWmsDnInvoiceDTO invoice, DbShardVO dbShardVO);

    MessageResult updateShipmentHeaderAllocateStatus(Long shipmentId, String s, DbShardVO dbShardVO);

    TWmsShipmentHeaderPrintEntity findPrintEntityPrintShipment(Long id, DbShardVO dbShardVO);

    MessageResult updateShipmentHeaderDelivery(Map map, DbShardVO dbShardVO);

    TWmsShipmentHeaderPrintEntity findPrintEntityPrintPicking(Long id, DbShardVO dbShardVO);

    List<TWmsShipmentHeaderEntity> findOwnerIdByHeaderId(List<Long> list, DbShardVO dbShardVO);

    List<TWmsShipmentHeaderEntity> findEntityBycargoOwnerId(Long cargoOwnerId, DbShardVO dbShardVO);

    MessageResult updateShipmentHeaderToWave(Long[] ids,Long tenantId,Long warehouseId,String operationUser, DbShardVO dbShardVO);

    List<TWmsShipmentHeaderEntity> findShipmentIdsByWaveId(List<Long> list, DbShardVO dbShardVO);

}
