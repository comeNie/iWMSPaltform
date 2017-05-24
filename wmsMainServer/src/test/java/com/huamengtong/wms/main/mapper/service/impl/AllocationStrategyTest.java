package com.huamengtong.wms.main.mapper.service.impl;

import com.huamengtong.wms.dto.TWmsAllocationStrategyDTO;
import com.huamengtong.wms.main.mapper.common.SpringTxTestCase;
import com.huamengtong.wms.main.service.IAllocationStrategyService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by mario on 2017/2/8.
 */
public class AllocationStrategyTest extends SpringTxTestCase {

    @Autowired
    IAllocationStrategyService allocationStrategyService;

    @Test
    public void TestAddAllocationStrategy(){
        TWmsAllocationStrategyDTO allocationStrategyDTO = new TWmsAllocationStrategyDTO();
        allocationStrategyDTO.setTenantId(88L);
        allocationStrategyDTO.setStrategyName("boom");
        allocationStrategyDTO.setIsActive(new Byte("1"));
        allocationStrategyDTO.setIsDefault(new Byte("1"));
        allocationStrategyDTO.setOrderFieldCode("BOOM");
        allocationStrategyDTO.setDirectionCode("FIFO");
        allocationStrategyService.createAllocationStrategy(allocationStrategyDTO);
        System.out.print("-------------------------");

    }

    @Test
    public void TestUpdateAllocationStrategy(){
        TWmsAllocationStrategyDTO allocationStrategyDTO = new TWmsAllocationStrategyDTO();
        allocationStrategyDTO.setId(1l);
        allocationStrategyDTO.setOrderFieldCode("boom");
        allocationStrategyService.modifyAllocationStrategy( allocationStrategyDTO);
        System.out.print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~`");
    }

    @Test
    public void TestDeleteAllocationStrategy(){
        TWmsAllocationStrategyDTO allocationStrategyDTO = new TWmsAllocationStrategyDTO();
        allocationStrategyService.removeAllocationStrategy(1l);
        System.out.print("~~~~~~~~~~~~~~~~~~~``");
    }

}
