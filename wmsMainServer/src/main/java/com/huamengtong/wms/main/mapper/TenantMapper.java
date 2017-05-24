package com.huamengtong.wms.main.mapper;


import com.huamengtong.wms.entity.main.TWmsTenantEntity;

import java.util.List;

public interface TenantMapper {

    List<TWmsTenantEntity> queryTenantPages(TWmsTenantEntity tenantEntity);

    Integer queryTenantPageCount(TWmsTenantEntity tenantEntity);

    TWmsTenantEntity selectByPrimaryKey(Long id);

    Integer deleteByPrimaryKey(Long id);

    Integer insertTenant(TWmsTenantEntity entity);

    Integer updateTenant(TWmsTenantEntity entity);

}
