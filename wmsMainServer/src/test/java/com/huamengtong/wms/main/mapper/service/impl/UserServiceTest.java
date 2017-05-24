package com.huamengtong.wms.main.mapper.service.impl;

import com.alibaba.fastjson.JSON;

import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.TWmsUserDTO;
import com.huamengtong.wms.entity.main.TWmsUserEntity;
import com.huamengtong.wms.main.mapper.common.SpringTxTestCase;
import com.huamengtong.wms.main.service.IUserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.List;


public class UserServiceTest extends SpringTxTestCase {

    @Autowired
    IUserService userService;

    @Test
    public void testGetUserById(){
      TWmsUserEntity HWmsUserEntity = userService.findUserById(1L);
      Assert.notNull(HWmsUserEntity);
    }

    @Test
    public void testGetUserByUserName(){
        TWmsUserEntity HWmsUserEntity = userService.findByUserName("test");
        System.out.println(HWmsUserEntity.getRealName());
    }

    @Test
    public void testGetUser(){
        TWmsUserEntity HWmsUserEntity = userService.findUser("test","2580ae4196df174e1d6f736f54281db8");
        System.out.println(HWmsUserEntity.getRealName());
    }

    @Test
    public void testQueryUserPages(){
        TWmsUserDTO userDTO = new TWmsUserDTO();
        userDTO.setPage(1);
        userDTO.setPageSize(15);
        PageResponse pageResponse = userService.queryUserPages(userDTO);
        System.out.println(JSON.toJSONString(pageResponse.getRows()));
    }

    @Test
    public void testRoleIdListByUserId(){
        List  list = userService.getRoleIdListByUserId(1L);
        System.out.println(JSON.toJSONString(list));
    }

}
