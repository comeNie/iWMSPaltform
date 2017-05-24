package com.huamengtong.wms.inwh.service;

import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.inwh.TWmsQcCheckDTO;
import com.huamengtong.wms.entity.inwh.TWmsQcCheckEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by could.hao on 2017/3/21.
 */
public interface IQcCheckService {

    TWmsQcCheckEntity selectByPrimaryKey(Long id, DbShardVO dbShardVO);

    PageResponse<List<TWmsQcCheckEntity>> selectQcCheckPage(TWmsQcCheckDTO qcCheckDTO,DbShardVO dbShardVO);

    PageResponse<List<TWmsQcCheckEntity>> selectQcCheckPagedetail(Map map, DbShardVO dbShardVO);

    MessageResult addQcCheck(TWmsQcCheckDTO qcCheckDTO,DbShardVO dbShardVO);

    MessageResult updateQcCheck(TWmsQcCheckDTO qcCheckDTO,DbShardVO dbShardVO);

    MessageResult deleteQcCheck(Long id,DbShardVO dbShardVO);

    MessageResult updateQcCheckBatch(Long headerId,Map<Long,Long> map,String typeCode,Long tenantId,Long warehouseId,String operationUser,DbShardVO dbShardVO);

    MessageResult updateQcCheck(Long id,String operationUser,DbShardVO dbShardVO);
}
