package com.huamengtong.wms.inwh.service;

import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.inwh.TWmsProInvPackageDTO;
import com.huamengtong.wms.entity.inwh.TWmsProInvPackageEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by mario on 2017/3/28.
 */
public interface IProInvPackageService {

    PageResponse<List<TWmsProInvPackageEntity>> getProInvPackage(TWmsProInvPackageDTO proInvPackageDTO, DbShardVO dbShardVO);

    TWmsProInvPackageEntity findByPrimaryKey(Long id,DbShardVO dbShardVO);

    MessageResult createProInvPackageHeader(TWmsProInvPackageDTO proInvPackageDTO, DbShardVO dbShardVO);

    MessageResult modifyProInvPackageHeader(TWmsProInvPackageDTO proInvPackageDTO,DbShardVO dbShardVO);

    MessageResult addProInvPackage(Map map,String logUser,Long tenantId,Long warehouseId, DbShardVO dbShardVO);

    MessageResult updateReviewProInvPackage(Long headerId,String logUser,DbShardVO dbShardVO);

    MessageResult updateInvalidProInvPackage(Long headerId,String logUser,DbShardVO dbShardVO);


}
