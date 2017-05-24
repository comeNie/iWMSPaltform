package com.huamengtong.wms.outwh.service;

import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.outwh.TWmsDnDetailDTO;
import com.huamengtong.wms.dto.outwh.TWmsDnHeaderDTO;
import com.huamengtong.wms.entity.outwh.TWmsDnHeaderEntity;

import java.util.List;
import java.util.Map;

public interface IDnHeaderService {

    PageResponse<List<TWmsDnHeaderEntity>> getDnHeader(TWmsDnHeaderDTO tWmsDnHeaderDTO, DbShardVO dbShardVO);

    PageResponse<List<TWmsDnHeaderEntity>> getDnHeaderList(TWmsDnHeaderDTO tWmsDnHeaderDTO,DbShardVO dbShardVO);

    TWmsDnHeaderEntity findByPrimaryKey(Long id, DbShardVO dbShardVO);

    MessageResult createDnHeader(TWmsDnHeaderDTO tWmsDnHeaderDTO, DbShardVO dbShardVO);

    MessageResult removeDnHeader(Long id, DbShardVO dbShardVO);

    MessageResult modifyDnHeader(TWmsDnHeaderDTO tWmsDnHeaderDTO,DbShardVO dbShardVO);

    MessageResult removeByPrimaryKey(Long id, DbShardVO dbShardVO);

    MessageResult createDnDetail(TWmsDnDetailDTO dnDetailDTO, DbShardVO dbShardVO);

    MessageResult modifyDnDetail(TWmsDnDetailDTO dnDetailDTO, DbShardVO dbShardVO);


    MessageResult batchDeleteDnHeaders(Long[] dnIds, String operationUser, DbShardVO dbShardVO);

    MessageResult updateDnToShipment(TWmsDnHeaderDTO tWmsDnHeaderDTO ,String submitUser,DbShardVO dbShardVO);

    Map queryDnBasics(Map paramMap,DbShardVO dbShardVO);


}
