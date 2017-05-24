package com.huamengtong.wms.main.mapper.service.impl;

import com.alibaba.fastjson.JSON;
import com.huamengtong.wms.core.formwork.db.domain.CurrentUserEntity;
import com.huamengtong.wms.main.mapper.common.SpringTxTestCase;
import com.huamengtong.wms.main.service.IModuleService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class ModuleServiceTest extends SpringTxTestCase {

    @Autowired
    IModuleService moduleService;

    @Test
    public void testGetModelByUser(){
        CurrentUserEntity entity = new CurrentUserEntity();
        entity.setId(1L);
        entity.setTenantId(88L);
        entity.setUserName("admin");
        entity.setIsAdmin(new Byte("1"));
        List list = moduleService.getModulesByUser(entity);
        System.out.println("JSON-->"+JSON.toJSONString(list));
    }

    @Test
    public void testGetAllModuleActionByUser(){
        CurrentUserEntity entity = new CurrentUserEntity();
        entity.setId(1L);
        entity.setIsAdmin(new Byte("0"));
        Map map = moduleService.getAllModuleActionByUser(entity);
        System.out.println("Map-->"+JSON.toJSONString(map));
    }




}
