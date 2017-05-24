package com.huamengtong.wms.outwh.service;

import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.outwh.TWmsShipmentDetailDTO;
import com.huamengtong.wms.entity.outwh.TWmsShipmentDetailEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by mario on 2016/10/31.
 */
public interface IShipmentDetailService {

    TWmsShipmentDetailEntity findByPrimaryKey(Long id, DbShardVO dbShardVO);

    MessageResult createShipmentDetail(TWmsShipmentDetailDTO shipmentDetailDTO, DbShardVO dbShardVO);

    MessageResult removeShipmentDetail(Long id, DbShardVO dbShardVO);

    MessageResult modifyShipmentDetail(TWmsShipmentDetailDTO shipmentDetailDTO, DbShardVO dbShardVO);

    MessageResult updateShipmentDetail(TWmsShipmentDetailDTO shipmentDetailDTO, DbShardVO dbShardVO);

    PageResponse<List> queryShipmentDetailByHeader(Map map, DbShardVO dbShardVO);

    List<TWmsShipmentDetailEntity> getShipmentDetails(Long headerId, DbShardVO dbShardVO);

    MessageResult batchDeleteShipmentDetails(Long[] ShipmentIds, DbShardVO dbShardVO);

    MessageResult updateShipmentDetailAllocatedQty(Long shipmentId, String storageRoomId, DbShardVO dbShardVO);

    MessageResult batchcreateShipmentDetail(List<TWmsShipmentDetailEntity> list, DbShardVO dbShardVO);

    PageResponse<List<TWmsShipmentDetailEntity>> queryShipmentDetail(TWmsShipmentDetailDTO shipmentDetailDTO, DbShardVO dbShardVO);

    PageResponse<List<TWmsShipmentDetailEntity>> queryShipmentSummaryDetail(TWmsShipmentDetailDTO shipmentDetailDTO, DbShardVO dbShardVO);

    MessageResult updateShipmentDetailBatch(List<TWmsShipmentDetailEntity> list,DbShardVO dbShardVO);

}
