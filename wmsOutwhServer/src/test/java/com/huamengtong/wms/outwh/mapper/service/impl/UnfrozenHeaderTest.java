package com.huamengtong.wms.outwh.mapper.service.impl;

import com.huamengtong.wms.dto.outwh.TWmsUnfrozenHeaderDTO;
import com.huamengtong.wms.outwh.service.impl.UnfrozenHeaderService;
import com.huamengtong.wms.outwh.mapper.common.SpringTxTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by mario on 2016/11/11.
 */
public class UnfrozenHeaderTest extends SpringTxTestCase {

    @Autowired
    UnfrozenHeaderService unfrozenHeaderService;

    @Test
    public void insertUnfrozenHeader(){
        TWmsUnfrozenHeaderDTO unfrozenHeaderDTO = new TWmsUnfrozenHeaderDTO();
        unfrozenHeaderDTO.setTenantId(1L);
        unfrozenHeaderDTO.setWarehouseId(1l);
        unfrozenHeaderDTO.setCargoOwnerId(11L);
        unfrozenHeaderDTO.setName("mario");
        unfrozenHeaderDTO.setFactoringType("gg");
        unfrozenHeaderDTO.setStatusCode("gg");
        unfrozenHeaderDTO.setTotalQty(11);
        unfrozenHeaderDTO.setTotalCartonQty(11);
        unfrozenHeaderDTO.setTotalAmount(new BigDecimal("1"));
        unfrozenHeaderDTO.setTotalNetWeight(new BigDecimal("1"));
        unfrozenHeaderDTO.setTotalGrossWeight(new BigDecimal("1"));
        unfrozenHeaderDTO.setTotalVolume(new BigDecimal("1"));
        unfrozenHeaderDTO.setFactoringOrganizeId(11L);
        unfrozenHeaderDTO.setFactoringTime(new Date().getTime());
        unfrozenHeaderDTO.setAuditedOrganizeId(11l);
        unfrozenHeaderDTO.setAuditedUser("mario");
        unfrozenHeaderDTO.setAuditedTime(new Date().getTime());
        unfrozenHeaderDTO.setIsInvalided(new Byte("1"));
        unfrozenHeaderDTO.setIsAudited(new Byte("1"));
        unfrozenHeaderDTO.setIsDel(new Byte("1"));
        unfrozenHeaderDTO.setIsCancelled(new Byte("1"));
        unfrozenHeaderService.createUnfrozenHeader(unfrozenHeaderDTO,getDbshardVO());
        System.out.print("!!!!@#@#@#@#@#@#@#@");
    }

    @Test
    public void selectUnfrozenHeader(){
        System.out.print(unfrozenHeaderService.findByPrimaryKey(3001l,getDbshardVO()));
        System.out.print("mario6666666~~~~~~~~~~~");
    }

    @Test
    public void updateunfrozenHeader(){
        TWmsUnfrozenHeaderDTO unfrozenHeaderDTO = new TWmsUnfrozenHeaderDTO();
        unfrozenHeaderDTO.setId(3001l);
        unfrozenHeaderDTO.setTenantId(1L);
        unfrozenHeaderDTO.setWarehouseId(1l);
        unfrozenHeaderDTO.setCargoOwnerId(11L);
        unfrozenHeaderDTO.setName("mario");
        unfrozenHeaderDTO.setFactoringType("gg");
        unfrozenHeaderDTO.setStatusCode("gg");
        unfrozenHeaderDTO.setTotalQty(11);
        unfrozenHeaderDTO.setTotalCartonQty(11);
        unfrozenHeaderDTO.setTotalAmount(new BigDecimal("1"));
        unfrozenHeaderDTO.setTotalNetWeight(new BigDecimal("1"));
        unfrozenHeaderDTO.setTotalGrossWeight(new BigDecimal("1"));
        unfrozenHeaderDTO.setTotalVolume(new BigDecimal("1"));
        unfrozenHeaderDTO.setFactoringOrganizeId(11L);
        unfrozenHeaderDTO.setFactoringTime(new Date().getTime());
        unfrozenHeaderDTO.setAuditedOrganizeId(11l);
        unfrozenHeaderDTO.setAuditedUser("mario");
        unfrozenHeaderDTO.setAuditedTime(new Date().getTime());
        unfrozenHeaderDTO.setIsInvalided(new Byte("1"));
        unfrozenHeaderDTO.setIsAudited(new Byte("1"));
        unfrozenHeaderDTO.setIsDel(new Byte("1"));
        unfrozenHeaderDTO.setIsCancelled(new Byte("1"));
        unfrozenHeaderService.modifyUnfrozenHeader(unfrozenHeaderDTO,getDbshardVO());
        System.out.print("mario66666666666666~~~~~~~~~~~~~~");
    }
}
