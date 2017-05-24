package com.huamengtong.wms.main.mapper.service.impl;

import com.huamengtong.wms.main.mapper.common.SpringTxTestCase;
import com.huamengtong.wms.main.service.IRoleService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class RoleServiceTest extends SpringTxTestCase {

    @Autowired
    IRoleService roleService;

    @Test
    public void testGetRoleNameByIds(){
        List<Long> roleList = new ArrayList<>();
        roleList.add(1L);
        List<String> roleNameList = roleService.getRoleNameByIds(roleList);
        System.out.println("size-->" + roleNameList.size());
    }



}
