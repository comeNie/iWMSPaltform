package com.huamengtong.wms.main.service.impl;

import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.TWmsOperationLogDTO;
import com.huamengtong.wms.entity.main.TWmsOperationLogEntity;
import com.huamengtong.wms.main.mapper.OperationLogMapper;
import com.huamengtong.wms.main.service.IOperationLogService;
import com.huamengtong.wms.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperationLogService implements IOperationLogService {

    @Autowired
    OperationLogMapper operationLogMapper;

    @Override
    public MessageResult createOperationLog(TWmsOperationLogEntity operationLogEntity) {
        operationLogMapper.insertOperationLog(operationLogEntity);
        return MessageResult.getSucMessage();
    }

    @Override
    public PageResponse<List<TWmsOperationLogEntity>> getOperationLog(TWmsOperationLogDTO operationLogDTO) {
        TWmsOperationLogEntity operationLogEntity = BeanUtils.copyBeanPropertyUtils(operationLogDTO,TWmsOperationLogEntity.class);
        List<TWmsOperationLogEntity> operationLogEntityList = operationLogMapper.queryOperationLogPages(operationLogEntity);
        Integer totalSize = operationLogMapper.queryOperationLogPageCounts(operationLogEntity);
        PageResponse<List<TWmsOperationLogEntity>> response = new PageResponse();
        response.setRows(operationLogEntityList);
        response.setTotal(totalSize);
        return response;

    }

    @Override
    public PageResponse<List<TWmsOperationLogEntity>> searchOperationLogsByOrderNo(Long orderNo, String orderTypeCode, Integer pageSize, Integer page) {
        TWmsOperationLogEntity entity = new TWmsOperationLogEntity();
        entity.setOrderNo(orderNo);
        entity.setOrderTypeCode(orderTypeCode);
        entity.setPage(page);
        entity.setPageSize(pageSize);
        List<TWmsOperationLogEntity> operationLogEntityList = operationLogMapper.queryOperationLogPages(entity);
        Integer totalSize = operationLogMapper.queryOperationLogPageCounts(entity);
        PageResponse<List<TWmsOperationLogEntity>> response =  new PageResponse();
        response.setTotal(totalSize);
        response.setRows(operationLogEntityList);
        return response;
    }

}
