package com.huamengtong.wms.outwh.service;

import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.outwh.TWmsFrozenDetailDTO;
import com.huamengtong.wms.entity.outwh.TWmsFrozenDetailEntity;

import java.util.List;
import java.util.Map;


public interface IFrozenDetailService {

    TWmsFrozenDetailEntity findByPrimaryKey(Long id, DbShardVO dbShardVO);

    MessageResult createFrozenDetail(TWmsFrozenDetailDTO frozenDetailDTO,DbShardVO dbShardVO);

    MessageResult removeByPrimaryKey(Long id,DbShardVO dbShardVO);

    MessageResult removeByFrozenId(Long frozenId,DbShardVO dbShardVO);

    MessageResult modifyFrozenDetail(TWmsFrozenDetailDTO frozenDetailDTO,DbShardVO dbShardVO);

    PageResponse<List> queryFrozenDetailByHeader(Map map,DbShardVO dbShardVO);

    List<TWmsFrozenDetailEntity> getFrozenDetailsByHeaderId(Long frozenId,DbShardVO dbShardVO);

    MessageResult updateFrozenDetailStatus(List<TWmsFrozenDetailEntity> frozenDetailEntityList,String operationCode,String operationUser,DbShardVO dbShardVO);


}
