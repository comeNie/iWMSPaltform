package com.huamengtong.wms.outwh.service;

import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.outwh.TWmsDnDetailDTO;
import com.huamengtong.wms.entity.outwh.TWmsDnDetailEntity;

import java.util.List;
import java.util.Map;

public interface IDnDetailService {

    TWmsDnDetailEntity findByPrimaryKey(Long id, DbShardVO dbShardVO);

    MessageResult removeByPrimaryKey(Long id, DbShardVO dbShardVO);

    MessageResult createDnDetail(TWmsDnDetailDTO dnDetailDTO, DbShardVO dbShardVO);

    MessageResult modifyDnDetail(TWmsDnDetailDTO dnDetailDTO, DbShardVO dbShardVO);

    PageResponse<List> queryDnDetailByHeader(Map map, DbShardVO dbShardVO);

    List<TWmsDnDetailEntity> getDnDetails(Long headerId, DbShardVO dbShardVO);

    MessageResult batchDeleteDnDetails(Long[] dnIds, DbShardVO dbShardVO);

    MessageResult deleteDnDetailsByDnId(Long dnId, DbShardVO dbShardVO);

}
