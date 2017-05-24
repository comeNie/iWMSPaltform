package com.huamengtong.wms.outwh.mapper.service.impl;

import com.huamengtong.wms.dto.outwh.TWmsFrozenHeaderDTO;
import com.huamengtong.wms.outwh.service.impl.FrozenHeaderService;
import com.huamengtong.wms.outwh.mapper.common.SpringTxTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by mario on 2016/11/10.
 */
public class FrozenHeaderTest extends SpringTxTestCase {

    @Autowired
    FrozenHeaderService frozenHeaderService;

    @Test
    public void insertTest() {
        TWmsFrozenHeaderDTO frozenHeaderDTO = new TWmsFrozenHeaderDTO();
        frozenHeaderDTO.setTenantId(1L);
        frozenHeaderDTO.setWarehouseId(1L);
        frozenHeaderDTO.setCargoOwnerId(2L);
        frozenHeaderDTO.setName("mario");
        frozenHeaderDTO.setFactoringType("GG");
        frozenHeaderDTO.setStatusCode("AA");
        frozenHeaderDTO.setTotalQty(122);
        frozenHeaderDTO.setTotalCartonQty(232);
        frozenHeaderDTO.setTotalAmount(new BigDecimal("2323"));
        frozenHeaderDTO.setTotalNetWeight(new BigDecimal("2323"));
        frozenHeaderDTO.setTotalGrossWeight(new BigDecimal("2323"));
        frozenHeaderDTO.setTotalVolume(new BigDecimal("2323"));
        frozenHeaderDTO.setFactoringOrganizeId(11L);
        frozenHeaderDTO.setFactoringTime(new Date().getTime());
        frozenHeaderDTO.setAuditedOrganizeId(22L);
        frozenHeaderDTO.setAuditedUser("mario");
        frozenHeaderDTO.setAuditedTime(new Date().getTime());
        frozenHeaderDTO.setIsInvalided(new Byte("1"));
        frozenHeaderDTO.setIsAudited(new Byte("1"));
        frozenHeaderDTO.setIsDel(new Byte("1"));
        frozenHeaderDTO.setIsCancelled(new Byte("1"));
        frozenHeaderService.createFrozenHeader(frozenHeaderDTO,getDbshardVO());
        System.out.print("!!!!@#@#@#@#@#@#@#@");
    }

    @Test
    public void SearchByFrozenHeader(){
        System.out.print(frozenHeaderService.findByPrimaryKey(3001L,getDbshardVO()));
        System.out.println("select Sucess~~~~~~~~~~~~!");

    }



    @Test
    public void updateFrozenHeaderTest() {
        TWmsFrozenHeaderDTO frozenHeaderDTO = new TWmsFrozenHeaderDTO();
        frozenHeaderDTO.setId(3001L);
        frozenHeaderDTO.setTenantId(6L);
        frozenHeaderDTO.setWarehouseId(6L);
        frozenHeaderDTO.setCargoOwnerId(6L);
        frozenHeaderDTO.setName("mario");
        frozenHeaderDTO.setFactoringType("GG");
        frozenHeaderDTO.setStatusCode("AA");
        frozenHeaderDTO.setTotalQty(122);
        frozenHeaderDTO.setTotalCartonQty(232);
        frozenHeaderDTO.setTotalAmount(new BigDecimal("2323"));
        frozenHeaderDTO.setTotalNetWeight(new BigDecimal("2323"));
        frozenHeaderDTO.setTotalGrossWeight(new BigDecimal("2323"));
        frozenHeaderDTO.setTotalVolume(new BigDecimal("2323"));
        frozenHeaderDTO.setFactoringOrganizeId(66L);
        frozenHeaderDTO.setFactoringTime(new Date().getTime());
        frozenHeaderDTO.setAuditedOrganizeId(66L);
        frozenHeaderDTO.setAuditedUser("mario");
        frozenHeaderDTO.setAuditedTime(new Date().getTime());
        frozenHeaderDTO.setIsInvalided(new Byte("1"));
        frozenHeaderDTO.setIsAudited(new Byte("1"));
        frozenHeaderDTO.setIsDel(new Byte("1"));
        frozenHeaderDTO.setIsCancelled(new Byte("1"));
        frozenHeaderService.modifyFrozenHeader(frozenHeaderDTO,getDbshardVO());
        System.out.print("!!!!@#@#@#@#@#@#@#@");
    }

}
