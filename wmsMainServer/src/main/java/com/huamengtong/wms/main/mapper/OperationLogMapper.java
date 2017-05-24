package com.huamengtong.wms.main.mapper;

import com.huamengtong.wms.entity.main.TWmsOperationLogEntity;

import java.util.List;

public interface OperationLogMapper {

    Integer insertOperationLog(TWmsOperationLogEntity record);

    List<TWmsOperationLogEntity> queryOperationLogPages(TWmsOperationLogEntity operationLogEntity);

    Integer queryOperationLogPageCounts(TWmsOperationLogEntity operationLogEntity);


}