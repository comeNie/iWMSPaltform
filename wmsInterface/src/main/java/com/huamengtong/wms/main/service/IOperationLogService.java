package com.huamengtong.wms.main.service;

import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.TWmsOperationLogDTO;
import com.huamengtong.wms.entity.main.TWmsOperationLogEntity;

import java.util.List;

public interface IOperationLogService {

    MessageResult createOperationLog(TWmsOperationLogEntity operationLogEntity);

    PageResponse<List<TWmsOperationLogEntity>> getOperationLog(TWmsOperationLogDTO operationLogDTO);

    PageResponse<List<TWmsOperationLogEntity>> searchOperationLogsByOrderNo(Long orderNo, String orderTypeCode,  Integer pageSize, Integer page);

}
