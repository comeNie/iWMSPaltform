package com.huamengtong.wms.outwh.service;

import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.outwh.TWmsFrozenHeaderDTO;
import com.huamengtong.wms.dto.outwh.TWmsUnfrozenHeaderDTO;
import com.huamengtong.wms.entity.outwh.TWmsUnfrozenHeaderEntity;

import java.util.List;


public interface IUnfrozenHeaderService {

    PageResponse<List<TWmsUnfrozenHeaderEntity>> getUnfrozenHeader(TWmsUnfrozenHeaderDTO unfrozenHeaderDTO, DbShardVO dbShardVO);

    TWmsUnfrozenHeaderEntity findByPrimaryKey(Long id, DbShardVO dbShardVO);

    MessageResult createUnfrozenHeader(TWmsUnfrozenHeaderDTO unfrozenHeaderDTO,DbShardVO dbShardVO);

    MessageResult removeUnfrozenHeader(Long id ,DbShardVO dbShardVO);

    MessageResult modifyUnfrozenHeader(TWmsUnfrozenHeaderDTO unfrozenHeaderDTO,DbShardVO dbShardVO);

    MessageResult createUnFrozenFromFrozen(Long unFrozenHeaderId, TWmsFrozenHeaderDTO frozenHeaderDTO, String operationUser, DbShardVO dbShardVO);

    TWmsUnfrozenHeaderEntity findByFrozenId(Long frozenId,DbShardVO dbShardVO);

    MessageResult executeInvalidUnfrozen(Long unfrozenId,String invalidReason,String operationUser,DbShardVO dbShardVO);

    MessageResult executeSubmitUnfrozen(Long unfrozenId,String operationUser,DbShardVO dbShardVO);

    MessageResult executeRepealedUnfrozen(Long unfrozenId,String operationUser,DbShardVO dbShardVO);

    MessageResult executeRejectUnfrozen(Long unfrozenId,String rejectReason,String operationUser,DbShardVO dbShardVO);

    MessageResult executeAuditUnfrozen(Long unfrozenId,String operationUser,DbShardVO dbShardVO);

    MessageResult updateStatusFromSubmitDetail(TWmsUnfrozenHeaderDTO unfrozenHeaderDTO,String operationUser,DbShardVO dbShardVO);


}
