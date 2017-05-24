package com.huamengtong.wms.main.service;

import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.TWmsTenantDTO;
import com.huamengtong.wms.entity.main.TWmsTenantEntity;

import java.util.List;


public interface ITenantService {

    PageResponse<List<TWmsTenantEntity>> queryTenantPages(TWmsTenantDTO tenantDTO);

    TWmsTenantEntity findByPrimaryKey(Long id);

    MessageResult removeByPrimaryKey(Long id);

    MessageResult createTenant(TWmsTenantDTO tenantDTO);

    MessageResult modifyTenant(TWmsTenantDTO tenantDTO);

}
