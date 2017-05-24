package com.huamengtong.wms.inwh.mapper.service.impl;

import com.huamengtong.wms.dto.inwh.TWmsProPackageDTO;
import com.huamengtong.wms.inwh.mapper.common.SpringTxTestCase;
import com.huamengtong.wms.inwh.service.impl.ProPackageService;
import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created by mario on 2017/2/20.
 */
public class PackageTest extends SpringTxTestCase {
     @Autowired
    ProPackageService proPackageService;

    @Test
    public void inserTest(){
        TWmsProPackageDTO proPackageDTO = new TWmsProPackageDTO();
        proPackageDTO.setTenantId(88l);
        proPackageDTO.setWarehouseId(88l);
        proPackageDTO.setCargoOwnerId(99l);
        proPackageDTO.setStatusCode("NigihayaMi");
        proPackageDTO.setSkuId(6l);
        proPackageDTO.setStorageRoomId(6l);
        proPackageDTO.setQty(8);
        proPackageDTO.setWorkGroupNo("A02");
        proPackageDTO.setSpec("BOOM");
        proPackageDTO.setCreateTime(new Date().getTime());
        proPackageDTO.setCreateUser("mario");
        proPackageDTO.setUpdateTime(new Date().getTime());
        proPackageDTO.setUpdateUser("mario");
       // proPackageService.createProPackage(proPackageDTO,getDbshardVO());
        System.out.print("!~~~~~~~~~~~~~~~~");
    }

    @Test
    public void updateTest(){
        TWmsProPackageDTO proPackageDTO = new TWmsProPackageDTO();
        proPackageDTO.setId(1l);
        proPackageDTO.setTenantId(66l);
        proPackageDTO.setWarehouseId(66l);
        proPackageDTO.setCargoOwnerId(33l);
        proPackageDTO.setStatusCode("init");
        proPackageDTO.setSkuId(3l);
        proPackageDTO.setStorageRoomId(3l);
        proPackageDTO.setQty(8);
        proPackageDTO.setWorkGroupNo("A01");
        proPackageDTO.setSpec("BOOM");
        proPackageDTO.setCreateTime(new Date().getTime());
        proPackageDTO.setCreateUser("mario");
        proPackageDTO.setUpdateTime(new Date().getTime());
        proPackageDTO.setUpdateUser("mario");
        proPackageService.modifyProPackage(proPackageDTO,getDbshardVO());
        System.out.print("!~~~~~~~~~~~~~~~~");
    }

    @Test
    public  void deleteTest(){
        TWmsProPackageDTO proPackageDTO = new TWmsProPackageDTO();
        proPackageService.removeProPackage(1l,getDbshardVO());
    }


}
