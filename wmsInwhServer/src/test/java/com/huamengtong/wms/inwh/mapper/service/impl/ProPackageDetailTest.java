package com.huamengtong.wms.inwh.mapper.service.impl;

import com.huamengtong.wms.client.IAutoIdClient;
import com.huamengtong.wms.dto.inwh.TWmsProPackageDTO;
import com.huamengtong.wms.dto.inwh.TWmsProPackageDetailDTO;
import com.huamengtong.wms.inwh.mapper.common.SpringTxTestCase;
import com.huamengtong.wms.inwh.service.impl.ProPackageDetailService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created by mario on 2017/2/20.
 */
public class ProPackageDetailTest extends SpringTxTestCase {

    @Autowired
    ProPackageDetailService proPackageDetailService;


    @Test
    public void insertTest(){
        TWmsProPackageDetailDTO proPackageDetailDTO = new TWmsProPackageDetailDTO();
        proPackageDetailDTO.setId(6l);
        proPackageDetailDTO.setParentId(6l);
        proPackageDetailDTO.setTenantId(6l);
        proPackageDetailDTO.setWarehouseId(88l);
        proPackageDetailDTO.setProInventoryId(88l);
        proPackageDetailDTO.setProInventoryQty(100);
        proPackageDetailDTO.setProStorageRoomId(152l);
        proPackageDetailDTO.setCreateTime(new Date().getTime());
        proPackageDetailDTO.setCreateUser("mario");
        proPackageDetailDTO.setUpdateTime(new Date().getTime());
        proPackageDetailDTO.setUpdateUser("mario");
        proPackageDetailService.createProPackageDetail(proPackageDetailDTO,getDbshardVO());
        System.out.print("!~~~~~~~~~~~~~~~~");
    }

    @Test
    public void updateTest(){
        TWmsProPackageDetailDTO proPackageDetailDTO = new TWmsProPackageDetailDTO();
        proPackageDetailDTO.setId(2l);
        proPackageDetailDTO.setParentId(2l);
        proPackageDetailDTO.setTenantId(2l);
        proPackageDetailDTO.setWarehouseId(88l);
        proPackageDetailDTO.setProInventoryId(88l);
        proPackageDetailDTO.setProInventoryQty(100);
        proPackageDetailDTO.setProStorageRoomId(152l);
        proPackageDetailDTO.setCreateTime(new Date().getTime());
        proPackageDetailDTO.setCreateUser("mario");
        proPackageDetailDTO.setUpdateTime(new Date().getTime());
        proPackageDetailDTO.setUpdateUser("mario");
        proPackageDetailService.modifyProPackageDetail(proPackageDetailDTO,getDbshardVO());
        System.out.print("!~~~~~~~~~~~~~~~~");
    }

    @Test
    public void deleteTest(){
        TWmsProPackageDetailDTO proPackageDetailDTO = new TWmsProPackageDetailDTO();
        proPackageDetailService.removeProPackageDetail(1l,getDbshardVO());
    }
}
