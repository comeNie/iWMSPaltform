package com.huamengtong.wms.main.mapper.service.impl;

import com.huamengtong.wms.dto.TWmsTenantDTO;
import com.huamengtong.wms.main.mapper.common.SpringTxTestCase;
import com.huamengtong.wms.main.service.ITenantService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class TenantServiceTest extends SpringTxTestCase {

    @Autowired
    ITenantService tenantService;

    @Test
    public void testDeleteTenantById(){
        tenantService.removeByPrimaryKey(91L);
        System.out.println("-----");
    }

    @Test
    public void testCreateTenant(){
        TWmsTenantDTO tenantEntity = new TWmsTenantDTO();
        tenantEntity.setTypeCode("WMS");
        tenantEntity.setTenantNo("ABC");
        tenantEntity.setDescription("红牛");
        tenantService.createTenant(tenantEntity);
        System.out.println("-----");
    }

    @Test
    public void testUpdateTenant(){
        TWmsTenantDTO tenantDTO = new TWmsTenantDTO();
        tenantDTO.setId(88L);
        tenantDTO.setTenantNo("呼和浩特");
        tenantDTO.setCreateTime(68126367362L);
        tenantService.modifyTenant(tenantDTO);
        System.out.println("Update success!");
    }

}
