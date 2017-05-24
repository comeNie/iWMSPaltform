package com.huamengtong.wms.main.service.impl;

import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.TWmsTenantDTO;
import com.huamengtong.wms.entity.main.TWmsTenantEntity;
import com.huamengtong.wms.main.mapper.TenantMapper;
import com.huamengtong.wms.main.service.ITenantService;
import com.huamengtong.wms.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TenantService implements ITenantService {

    @Autowired
    TenantMapper tenantMapper;

    @Override
    public PageResponse<List<TWmsTenantEntity>> queryTenantPages(TWmsTenantDTO tenantDTO) {
        TWmsTenantEntity tenantEntity = BeanUtils.copyBeanPropertyUtils(tenantDTO,TWmsTenantEntity.class);
        List<TWmsTenantEntity> tenantEntityList = tenantMapper.queryTenantPages(tenantEntity);
        Integer totalSize = tenantMapper.queryTenantPageCount(tenantEntity);
        PageResponse<List<TWmsTenantEntity>> response = new PageResponse();
        response.setTotal(totalSize);
        response.setRows(tenantEntityList);
        return  response;
    }

    @Override
    public TWmsTenantEntity findByPrimaryKey(Long id) {
        return tenantMapper.selectByPrimaryKey(id);
    }

    @Override
    public MessageResult removeByPrimaryKey(Long id) {
         tenantMapper.deleteByPrimaryKey(id);
         return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult createTenant(TWmsTenantDTO tenantDTO) {
        TWmsTenantEntity entity = BeanUtils.copyBeanPropertyUtils(tenantDTO,TWmsTenantEntity.class);
        tenantMapper.insertTenant(entity);
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult modifyTenant(TWmsTenantDTO tenantDTO) {
        TWmsTenantEntity entity = BeanUtils.copyBeanPropertyUtils(tenantDTO,TWmsTenantEntity.class);
        tenantMapper.updateTenant(entity);
        return MessageResult.getSucMessage();
    }

}
