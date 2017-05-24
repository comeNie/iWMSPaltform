package com.huamengtong.wms.inwh.mapper.service.impl;

import com.huamengtong.wms.dto.inwh.TWmsAdjustmentHeaderDTO;
import com.huamengtong.wms.inwh.mapper.common.SpringTxTestCase;
import com.huamengtong.wms.inwh.service.impl.AdjustmentHeaderService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by mario on 2016/11/16.
 */
public class AdjustmentHeaderTest extends SpringTxTestCase {

 @Autowired
 AdjustmentHeaderService adjustmentHeaderService;

    @Test
    public void insertTest() {
        TWmsAdjustmentHeaderDTO adjustmentHeaderDTO = new TWmsAdjustmentHeaderDTO();
        adjustmentHeaderDTO.setTenantId(1l);
        adjustmentHeaderDTO.setWarehouseId(1l);
        adjustmentHeaderDTO.setcargoOwnerId(1l);
        adjustmentHeaderDTO.setResonCode("mario");
        adjustmentHeaderDTO.setStatusCode("mario");
        adjustmentHeaderDTO.setIsDel(new Byte("1"));
        adjustmentHeaderService.createAdjustmentHeader(adjustmentHeaderDTO,getDbshardVO());
        System.out.print("!!!!@#@#@#@#@#@#@#@");
    }

    @Test
    public void selectTest(){
        System.out.print(adjustmentHeaderService.findByPrimaryKey(2003L,getDbshardVO()));
        System.out.print("!!!!@#@#@#@#@#@#@#@");

    }

    @Test
    public void deleteTest(){
        adjustmentHeaderService.removeAdjustmentHeader(2003L,getDbshardVO());
        System.out.print("!!!!@#@#@#@#@#@#@#@");
    }

    @Test
    public  void updateTest(){
        TWmsAdjustmentHeaderDTO adjustmentHeaderDTO = new TWmsAdjustmentHeaderDTO();
        adjustmentHeaderDTO.setId(3003l);
        adjustmentHeaderDTO.setTenantId(1l);
        adjustmentHeaderDTO.setWarehouseId(1l);
        adjustmentHeaderDTO.setcargoOwnerId(1l);
        adjustmentHeaderDTO.setResonCode("mario");
        adjustmentHeaderDTO.setStatusCode("mario");
        adjustmentHeaderDTO.setIsDel(new Byte("1"));
        adjustmentHeaderService.modifyAdjustmentHeader(adjustmentHeaderDTO,getDbshardVO());
        System.out.print("!!!!@#@#@#@#@#@#@#@");

    }


}
