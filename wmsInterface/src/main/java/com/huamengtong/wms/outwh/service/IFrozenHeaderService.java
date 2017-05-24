package com.huamengtong.wms.outwh.service;

import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.outwh.TWmsFrozenHeaderDTO;
import com.huamengtong.wms.entity.outwh.TWmsFrozenHeaderEntity;
import com.huamengtong.wms.vo.FrozenInventoryVO;

import java.util.List;
import java.util.Map;


public interface IFrozenHeaderService {

    PageResponse<List<TWmsFrozenHeaderEntity>>  getFrozenHeader(TWmsFrozenHeaderDTO frozenHeaderDTO, DbShardVO dbShardVO);

    TWmsFrozenHeaderEntity findByPrimaryKey(Long id,DbShardVO dbShardVO);

    MessageResult createFrozenHeader(TWmsFrozenHeaderDTO frozenHeaderDTO,DbShardVO dbShardVO);

    MessageResult removeFrozenHeader(Long id,DbShardVO dbShardVO);

    MessageResult modifyFrozenHeader(TWmsFrozenHeaderDTO frozenHeaderDTO,DbShardVO dbShardVO);

    MessageResult executeSubmitFrozen(Long frozenId,String operationUser,DbShardVO dbShardVO);

    MessageResult executeRepealedFrozen(Long frozenId,String operationUser,DbShardVO dbShardVO);

    MessageResult executeRejectFrozen(Long frozenId,String rejectReason,String operationUser,DbShardVO dbShardVO);

    MessageResult executeAuditFrozen(Long frozenId,String operationUser,DbShardVO dbShardVO);

    PageResponse<List<FrozenInventoryVO>> getFrozenInventoryVOs(Map map, DbShardVO dbShardVO);


}
