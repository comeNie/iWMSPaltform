package com.huamengtong.wms.outwh.service;

import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.outwh.TWmsSaleOrderDTO;
import com.huamengtong.wms.entity.outwh.TWmsSaleOrderEntity;

import java.util.List;

public interface ISaleOrderService {

    PageResponse<List<TWmsSaleOrderEntity>>  getSaleOrder(TWmsSaleOrderDTO saleOrderDTO, DbShardVO dbShardVO);

    TWmsSaleOrderEntity findByPrimaryKey(Long id, DbShardVO dbShardVO);

    MessageResult createSaleOrder(TWmsSaleOrderDTO saleOrderDTO, DbShardVO dbShardVO);

    MessageResult removeSaleOrder(Long id, DbShardVO dbShardVO);

    MessageResult modifySaleOrder(TWmsSaleOrderDTO saleOrderDTO, DbShardVO dbShardVO);

    Long saveOrUpdateSaleOrder(TWmsSaleOrderDTO order, DbShardVO dbShardVO);

    List<TWmsSaleOrderEntity> findByPrimaryKey(List<Long> ids, DbShardVO dbShardVO);
}
