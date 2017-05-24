package com.huamengtong.wms.inwh.service;

import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.inwh.TWmsProInvPackageDetailDTO;
import com.huamengtong.wms.entity.inwh.TWmsProInvPackageDetailEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by mario on 2017/3/28.
 */
public interface IProInvPackageDetailService {

    TWmsProInvPackageDetailEntity findByPrimaryKey(Long id, DbShardVO dbShardVO);

    MessageResult createProInvPackageDetail(TWmsProInvPackageDetailDTO proInvPackageDetailDTO, DbShardVO dbShardVO);

    MessageResult modifyProInvPackageDetail(TWmsProInvPackageDetailDTO proInvPackageDetailDTO,DbShardVO dbShardVO );

    PageResponse<List> queryProInvPackageDetailByHeader(Map map, DbShardVO dbShardVO);

    List<TWmsProInvPackageDetailEntity> getProInvPackageDetails(long parentId,DbShardVO dbShardVO);

    MessageResult createProInvPackageDetailBatch(List<TWmsProInvPackageDetailDTO> list,DbShardVO dbShardVO);

}
