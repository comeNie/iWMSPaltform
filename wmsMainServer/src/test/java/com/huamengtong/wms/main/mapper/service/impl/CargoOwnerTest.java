package com.huamengtong.wms.main.mapper.service.impl;


import com.huamengtong.wms.dto.TWmsCargoOwnerDTO;
import com.huamengtong.wms.main.mapper.common.SpringTxTestCase;
import com.huamengtong.wms.main.service.ICargoOwnerService;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CargoOwnerTest extends SpringTxTestCase {

    @Autowired
    ICargoOwnerService aOwnerService;

    @Test
    public void testCreateOwner(){
        TWmsCargoOwnerDTO aTWmsCargoOwnerDTO = new TWmsCargoOwnerDTO();
        aTWmsCargoOwnerDTO.setTypeCode("TypeCode");
        aTWmsCargoOwnerDTO.setAddress("上海");
        aTWmsCargoOwnerDTO.setTelephone("111111");
        aTWmsCargoOwnerDTO.setCreateUser("master");
        aTWmsCargoOwnerDTO.setArea("黄埔");
        aTWmsCargoOwnerDTO.setCity("上海市");
        aTWmsCargoOwnerDTO.setCountry("china");
        aTWmsCargoOwnerDTO.setContact("Contact");
        aTWmsCargoOwnerDTO.setEmail("aaa@email.com");
        aTWmsCargoOwnerDTO.setCreateTime(1L);
        aTWmsCargoOwnerDTO.setZip("zip");
        aTWmsCargoOwnerDTO.setFax("fax");
        aTWmsCargoOwnerDTO.setIsActive((byte)1);
        aTWmsCargoOwnerDTO.setUpdateTime(2L);
        aTWmsCargoOwnerDTO.setProvince("shanghai");
        aTWmsCargoOwnerDTO.setFullName("全名");
        aTWmsCargoOwnerDTO.setShortName("简称");
        aTWmsCargoOwnerDTO.setCargoOwnerNo("OwnerNo");
        aOwnerService.createOwner(aTWmsCargoOwnerDTO);

    }

     @Test
        public void updatae(){
            TWmsCargoOwnerDTO aTWmsCargoOwnerDTO = new TWmsCargoOwnerDTO();
            aTWmsCargoOwnerDTO.setTypeCode("测试更新");
            System.out.println(aOwnerService.updateOwner(aTWmsCargoOwnerDTO));
        }

    @Test
    public void show(){
        String str = "7766";
        List<Long> ids = Arrays.stream(str.split(",")).filter(StringUtils::isNotBlank).map(Long::parseLong).collect(Collectors.toList());
        System.out.println(ids.toString());
    }


}
