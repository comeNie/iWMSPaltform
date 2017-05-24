package com.huamengtong.wms.outwh.mapper.service.impl;

import com.huamengtong.wms.dto.outwh.TWmsFrozenDetailDTO;
import com.huamengtong.wms.outwh.service.impl.FrozenDetailService;
import com.huamengtong.wms.outwh.mapper.common.SpringTxTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * Created by mario on 2016/11/10.
 */
public class FrozenDetailTest extends SpringTxTestCase {

    @Autowired
    FrozenDetailService frozenDetailService;

    @Test
    public void insertFrozenDetailTest(){
        TWmsFrozenDetailDTO frozenDetailDTO = new TWmsFrozenDetailDTO();
        frozenDetailDTO.setTenantId(88L);
        frozenDetailDTO.setFrozenId(6001L);
        frozenDetailDTO.setWarehouseId(8L);
        frozenDetailDTO.setSkuId(8L);
        frozenDetailDTO.setSkuName("mario");
        frozenDetailDTO.setStorageRoomId(8L);
        frozenDetailDTO.setFactoringQty(231231);
        frozenDetailDTO.setNetWeight(new BigDecimal("2323"));
        frozenDetailDTO.setGrossWeight(new BigDecimal("23232"));
        frozenDetailDTO.setVolume(new BigDecimal("23232"));
        frozenDetailService.createFrozenDetail(frozenDetailDTO,getDbshardVO());
        System.out.print("GG~~~~~~~~~~~~~~~~~~");
    }

    @Test
    public void updateFrozenDetail(){
        TWmsFrozenDetailDTO frozenDetailDTO = new TWmsFrozenDetailDTO();
        frozenDetailDTO.setTenantId(1L);
        frozenDetailDTO.setFrozenId(301L);
        frozenDetailDTO.setWarehouseId(2L);
        frozenDetailDTO.setSkuId(2L);
        frozenDetailDTO.setSkuName("mario");
        frozenDetailDTO.setStorageRoomId(2L);
        frozenDetailDTO.setFactoringQty(231231);
        frozenDetailDTO.setNetWeight(new BigDecimal("2323"));
        frozenDetailDTO.setGrossWeight(new BigDecimal("23232"));
        frozenDetailDTO.setVolume(new BigDecimal("23232"));
        frozenDetailService.modifyFrozenDetail(frozenDetailDTO,getDbshardVO());
        System.out.print("GG~~~~~~~~~~~~~~~~~~~");
    }

    @Test
    public void selectFrozenDetail(){
        System.out.print(frozenDetailService.findByPrimaryKey(3001L,getDbshardVO()));
        System.out.print("GG~~~~~~~~~~~~~~~~~~~");
    }

    @Test
    public void deteteFrozenDetail(){
        frozenDetailService.removeByPrimaryKey(3001L,getDbshardVO());
        System.out.print("GG~~~~~~~~~~~~~~~~~~~");
    }

}
