package com.huamengtong.wms.outwh.service.impl;

import com.huamengtong.wms.client.IAutoIdClient;
import com.huamengtong.wms.constants.AutoIdConstants;
import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.service.BaseService;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.entity.outwh.TWmsAllocateEntity;
import com.huamengtong.wms.outwh.mapper.AllocateMapper;
import com.huamengtong.wms.outwh.service.IAllocateService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Edwin on 2016/11/11.
 */
@Service
public class AllocateService  extends BaseService implements IAllocateService {

    @Autowired
    AllocateMapper allocateMapper;

    @Autowired
    IAutoIdClient autoIdClient;

    @Override
    public List<TWmsAllocateEntity> searchAllocates(Map paramMap, DbShardVO dbShardVO) {
        paramMap.put("splitTableKey",getSplitTableKey(dbShardVO));
        List<TWmsAllocateEntity> allocateEntityList = allocateMapper.selectAllocates(paramMap);
        if (CollectionUtils.isNotEmpty(allocateEntityList)) {
            return allocateEntityList;
        }
        return null;
    }

    @Override
    public MessageResult saveBatchAllocates(List<TWmsAllocateEntity> allocateEntities, DbShardVO dbShardVO) {
         if(CollectionUtils.isNotEmpty(allocateEntities)){
             allocateEntities.stream().forEach(allocateEntity->{
                 allocateEntity.setId(autoIdClient.getAutoId(AutoIdConstants.TWmsAllocateEntity));
                 allocateMapper.insertAllocate(allocateEntity,getSplitTableKey(dbShardVO));
                 }
             );
         }
         return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult deleteAllocate(Long detailId, DbShardVO dbShardVO) {
        allocateMapper.deleteAllocate(detailId,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }

    @Override
    public List<TWmsAllocateEntity> searchAllocatesByShipmentDetailId(Long detailId, DbShardVO dbShardVO) {
        return allocateMapper.selectAllocatesByShipmentDetailId(detailId,getSplitTableKey(dbShardVO));
    }

    @Override
    public MessageResult updateAllocate(TWmsAllocateEntity allocateEntity, DbShardVO dbShardVO) {
        allocateMapper.updateAllocate(allocateEntity,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }
}
