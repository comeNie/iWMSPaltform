package com.huamengtong.wms.inwh.service;

import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.inwh.TWmsProPackageDTO;
import com.huamengtong.wms.entity.inwh.TWmsProPackageEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by mario on 2017/2/20.
 */
public interface IProPackageService {
    PageResponse<List<TWmsProPackageEntity>>   getProPackage (TWmsProPackageDTO proPackageDTO, DbShardVO dbShardVO);

    TWmsProPackageEntity findByPrimarkey(Long id,DbShardVO dbShardVO);

    MessageResult removeProPackage(Long id,DbShardVO dbShardVO);

    MessageResult modifyProPackage(TWmsProPackageDTO proPackageDTO,DbShardVO dbShardVO);

    MessageResult updateReviewProPackage(Long id , String operationUser , DbShardVO dbShardVO);

    MessageResult addProPackage(TWmsProPackageDTO proPackageDTO, List<Map> proPackageDetailDTOList,List<Map> fuArrayList, DbShardVO dbShardVO);

    MessageResult updateInvalidProPackage(Long id,String operationUser,DbShardVO dbShardVO);

}
