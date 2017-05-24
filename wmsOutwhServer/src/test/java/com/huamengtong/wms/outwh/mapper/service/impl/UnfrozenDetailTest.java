package com.huamengtong.wms.outwh.mapper.service.impl;

import com.huamengtong.wms.dto.outwh.TWmsUnfrozenDetailDTO;
import com.huamengtong.wms.outwh.service.impl.UnfrozenDetailService;
import com.huamengtong.wms.outwh.mapper.common.SpringTxTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * Created by mario on 2016/11/11.
 */
public class UnfrozenDetailTest  extends SpringTxTestCase{

    @Autowired
    UnfrozenDetailService unfrozenDetailService;

    @Test
    public void insertUnfrozenDetail(){
        TWmsUnfrozenDetailDTO unfrozenDetailDTO = new TWmsUnfrozenDetailDTO();
        unfrozenDetailDTO.setUnfrozenId(1l);
        unfrozenDetailDTO.setTenantId(88L);
        unfrozenDetailDTO.setWarehouseId(1L);
        unfrozenDetailDTO.setSkuId(1L);
        unfrozenDetailDTO.setSkuName("mario");
        unfrozenDetailDTO.setStorageRoomId(11L);
        unfrozenDetailDTO.setFactoringQty(11);
        unfrozenDetailDTO.setNetWeight(new BigDecimal("2323"));
        unfrozenDetailDTO.setGrossWeight(new BigDecimal("23232"));
        unfrozenDetailDTO.setVolume(new BigDecimal("23232"));
        unfrozenDetailService.createUnfrozenDetail(unfrozenDetailDTO,getDbshardVO());
        System.out.print("mario666666666666666666~~~~~~~~~~~~~```");
    }

    @Test
    public void selectunfrozenDetail(){
        System.out.print(unfrozenDetailService.findByPrimaryKey(1001L,getDbshardVO()));
        System.out.print("mario666666666666666666~~~~~~~~~~~~~```");
    }

    @Test
    public void updateUnfrozenDetail(){
        TWmsUnfrozenDetailDTO unfrozenDetailDTO = new TWmsUnfrozenDetailDTO();
        unfrozenDetailDTO.setId(1001L);
        unfrozenDetailDTO.setUnfrozenId(1l);
        unfrozenDetailDTO.setTenantId(88L);
        unfrozenDetailDTO.setWarehouseId(1L);
        unfrozenDetailDTO.setSkuId(1L);
        unfrozenDetailDTO.setSkuName("mario");
        unfrozenDetailDTO.setStorageRoomId(11L);
        unfrozenDetailDTO.setFactoringQty(11);
        unfrozenDetailDTO.setNetWeight(new BigDecimal("2323"));
        unfrozenDetailDTO.setGrossWeight(new BigDecimal("23232"));
        unfrozenDetailDTO.setVolume(new BigDecimal("23232"));
        unfrozenDetailService.modifyUnfrozenDetail(unfrozenDetailDTO,getDbshardVO());
        System.out.print("mario666666666666666666~~~~~~~~~~~~~```");
    }

    @Test
    public void delteteUnfrozenDetail(){
        System.out.print(unfrozenDetailService.removeUnfrozenDetail(1001L,getDbshardVO()));
        System.out.print("mario666666666666666666~~~~~~~~~~~~~```");
    }

}
