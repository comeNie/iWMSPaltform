package com.huamengtong.wms.outwh.service;

import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.entity.outwh.TWmsAllocateEntity;

import java.util.List;
import java.util.Map;

public interface IAllocateService {

    List<TWmsAllocateEntity> searchAllocates(Map paramMap, DbShardVO dbShardVO);

    MessageResult saveBatchAllocates(List<TWmsAllocateEntity> allocateEntities, DbShardVO dbShardVO);

    MessageResult deleteAllocate(Long detailId,DbShardVO dbShardVO);

    List<TWmsAllocateEntity> searchAllocatesByShipmentDetailId(Long detailId, DbShardVO dbShardVO);

    MessageResult updateAllocate(TWmsAllocateEntity allocateEntity,DbShardVO dbShardVO);

}
