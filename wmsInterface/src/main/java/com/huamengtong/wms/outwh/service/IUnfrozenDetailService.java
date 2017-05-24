package com.huamengtong.wms.outwh.service;

import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.outwh.TWmsUnfrozenDetailDTO;
import com.huamengtong.wms.entity.outwh.TWmsFrozenDetailEntity;
import com.huamengtong.wms.entity.outwh.TWmsUnfrozenDetailEntity;

import java.util.List;
import java.util.Map;

public interface IUnfrozenDetailService {
    TWmsUnfrozenDetailEntity findByPrimaryKey(Long id, DbShardVO dbShardVO);

    MessageResult createUnfrozenDetail(TWmsUnfrozenDetailDTO unfrozenDetailDTO,DbShardVO dbShardVO);

    MessageResult removeUnfrozenDetail(Long id ,DbShardVO dbShardVO);

    MessageResult modifyUnfrozenDetail(TWmsUnfrozenDetailDTO unfrozenDetailDTO,DbShardVO dbShardVO);

    PageResponse<List> queryUnfrozenDetailByHeader(Map map, DbShardVO dbShardVO);

    List<TWmsUnfrozenDetailEntity> getUnfrozenDetailsByHeaderId(Long unfrozenId, DbShardVO dbShardVO);

    MessageResult createUnFrozenDetailFromFrozen(Long unFrozenHeaderId, String operationUser, List<TWmsFrozenDetailEntity> frozenDetailEntityList,DbShardVO dbShardVO);

    MessageResult updateDetailFromSubmitUnfrozen(TWmsUnfrozenDetailEntity unfrozenDetailEntity,String operationUser,DbShardVO dbShardVO);

    MessageResult updateDetailFromRepealedUnfrozen(TWmsUnfrozenDetailEntity unfrozenDetailEntity,String operationUser,DbShardVO dbShardVO);

    MessageResult updateDetailFromInvalidUnfrozen(TWmsUnfrozenDetailEntity unfrozenDetailEntity,String operationUser,DbShardVO dbShardVO);

    MessageResult updateDetailFromAuditUnfrozen(List<TWmsUnfrozenDetailEntity> unfrozenDetailEntities,String operationUser,DbShardVO dbShardVO);

    MessageResult executeSubmitUnfrozenDetail(Long unfrozenDetailId,Integer factoringQty,String operationUser,DbShardVO dbShardVO);
}
