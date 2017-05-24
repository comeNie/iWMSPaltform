package com.huamengtong.wms.inwh.service;

import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.inwh.TWmsProPackageDetailDTO;
import com.huamengtong.wms.entity.inwh.TWmsProPackageDetailEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by mario on 2017/2/20.
 */
public interface IProPackageDetailService {

    TWmsProPackageDetailEntity findByPrimarykey(Long id, DbShardVO dbShardVO);

    MessageResult createProPackageDetail(TWmsProPackageDetailDTO proPackageDetailDTO, DbShardVO dbShardVO);

    MessageResult removeProPackageDetail(Long id,DbShardVO dbShardVO);

    MessageResult modifyProPackageDetail(TWmsProPackageDetailDTO proPackageDetailDTO,DbShardVO dbShardVO);

    PageResponse<List> queryProPackageDetailByParent(Map map, DbShardVO dbShardVO);

    List<TWmsProPackageDetailEntity> getProPackageDetails(Long parentId,DbShardVO dbShardVO);

}
