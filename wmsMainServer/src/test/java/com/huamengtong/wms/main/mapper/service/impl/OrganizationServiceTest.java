package com.huamengtong.wms.main.mapper.service.impl;

import com.huamengtong.wms.dto.TWmsOrganizationsDTO;
import com.huamengtong.wms.entity.main.TWmsOrganizationsEntity;
import com.huamengtong.wms.main.mapper.common.SpringTxTestCase;
import com.huamengtong.wms.main.service.IOrganizationsService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class OrganizationServiceTest extends SpringTxTestCase {

//    @Autowired
//    IOrganizationsService organizationsService;

    @Autowired
    IOrganizationsService organizationsService;


    @Test
    public void insertTest(){
        TWmsOrganizationsDTO organizationsDTO = new TWmsOrganizationsDTO();
        organizationsDTO.setName("name");
        organizationsDTO.setTenantId(88L);
        organizationsDTO.setAddress("address");
        organizationsDTO.setDescription("描述");
        organizationsDTO.setIsActive((byte)1);
        organizationsDTO.setTelephone("123456789");
        organizationsDTO.setTypeCode("type");
        organizationsDTO.setContacts("165432");
        organizationsService.createOrganization(organizationsDTO);
    }
    @Test
    public  void updateTest(){
        TWmsOrganizationsDTO organizationsDTO = new TWmsOrganizationsDTO();
        organizationsDTO.setId(2L);
        organizationsDTO.setName("测试更新");
        organizationsService.modifyOrganization(organizationsDTO);
    }

    @Test
    public  void queryTest(){
        TWmsOrganizationsEntity organizationsEntity = organizationsService.findById(1L);
        System.out.print("12345678900000");
        System.out.print(organizationsEntity);
    }

    @Test
    public void deleteTest(){
        organizationsService.removeOrganization(1L);
    }

}
