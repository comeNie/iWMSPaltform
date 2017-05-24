package com.huamengtong.wms.inwh.mapper.service.impl;

import com.huamengtong.wms.dto.inwh.TWmsAdjustmentDetailDTO;
import com.huamengtong.wms.inwh.mapper.common.SpringTxTestCase;
import com.huamengtong.wms.inwh.service.IAdjustmentDetailService;
import com.huamengtong.wms.inwh.service.impl.AdjustmentDetailService;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * Created by mario on 2016/11/17.
 */
public class AdjustmentDetailTest extends SpringTxTestCase{

    @Autowired
    AdjustmentDetailService adjustmentDetailService;

    @Test
    public void insertTest() {
        TWmsAdjustmentDetailDTO adjustmentDetailDTO = new TWmsAdjustmentDetailDTO();
        adjustmentDetailDTO.setAdjustId(3003l);
        adjustmentDetailDTO.setTenantId(88l);
        adjustmentDetailDTO.setStorageRoomId(1l);
        adjustmentDetailDTO.setSkuId(1l);
        adjustmentDetailDTO.setAdjustedBeforeQty(1);
        adjustmentDetailDTO.setAdjustedAfterQty(1);
        adjustmentDetailDTO.setAdjustedQty(1);
        adjustmentDetailDTO.setAdjustedBeforePrice(new BigDecimal("1"));
        adjustmentDetailDTO.setAdjustedAfterPrice(new BigDecimal("1"));
        adjustmentDetailDTO.setStatusCode("mario");
        adjustmentDetailDTO.setIsDel(new Byte("1"));
        adjustmentDetailService.createAdjustmentDetail(adjustmentDetailDTO,getDbshardVO());
        System.out.print("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

    }

    @Test
    public void selectTest(){
        System.out.print(adjustmentDetailService.findByPrimaryKey(2001l,getDbshardVO()));
        System.out.print("gg");
    }

    @Test
    public void delete(){
        adjustmentDetailService.removeAdjustmentDetail(2001l,getDbshardVO());
        System.out.print("~~~~~~~~~~~~~~~~~~~~~~~~~~~`");
    }

    @Test
    public void updateTest() {
        TWmsAdjustmentDetailDTO adjustmentDetailDTO = new TWmsAdjustmentDetailDTO();
        adjustmentDetailDTO.setId(3003l);
        adjustmentDetailDTO.setAdjustId(3003l);
        adjustmentDetailDTO.setTenantId(88l);
        adjustmentDetailDTO.setStorageRoomId(1l);
        adjustmentDetailDTO.setSkuId(1l);
        adjustmentDetailDTO.setAdjustedBeforeQty(1);
        adjustmentDetailDTO.setAdjustedAfterQty(1);
        adjustmentDetailDTO.setAdjustedQty(1);
        adjustmentDetailDTO.setAdjustedBeforePrice(new BigDecimal("1"));
        adjustmentDetailDTO.setAdjustedAfterPrice(new BigDecimal("1"));
        adjustmentDetailDTO.setStatusCode("mario");
        adjustmentDetailDTO.setIsDel(new Byte("1"));
        adjustmentDetailService.modifyAdjustmentDetail(adjustmentDetailDTO,getDbshardVO());
        System.out.print("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

    }


}
