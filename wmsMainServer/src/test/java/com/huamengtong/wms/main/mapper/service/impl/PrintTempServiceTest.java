package com.huamengtong.wms.main.mapper.service.impl;

import com.huamengtong.wms.dto.TWmsPrintTempDTO;
import com.huamengtong.wms.entity.main.TWmsPrintTempEntity;
import com.huamengtong.wms.main.mapper.common.SpringTxTestCase;
import com.huamengtong.wms.main.service.IPrintTempService;
import com.huamengtong.wms.main.service.impl.PrintTempService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class PrintTempServiceTest extends SpringTxTestCase {

    @Autowired
    PrintTempService printTempService;

    @Test
    public void createTest(){
        TWmsPrintTempDTO tWmsPrintTempDTO = new TWmsPrintTempDTO();
        tWmsPrintTempDTO.setUpdateTime(new Date().getTime());
        tWmsPrintTempDTO.setUpdateUser("user1");
        tWmsPrintTempDTO.setCreateUser("admin");
        tWmsPrintTempDTO.setCreateTime(new Date().getTime());
        tWmsPrintTempDTO.setCarrier("test");
        tWmsPrintTempDTO.setContent("test");
        tWmsPrintTempDTO.setPrintOptions("test");
        tWmsPrintTempDTO.setReportName("测试");
        tWmsPrintTempDTO.setIsDefault((byte)1);
        tWmsPrintTempDTO.setReportCategoryCode("123456");
        tWmsPrintTempDTO.setProjectCode("projectcode");
        tWmsPrintTempDTO.setTenantId((long) 88);
        printTempService.createPrintTemp(tWmsPrintTempDTO);
    }

    @Test
    public void updateTest(){
        TWmsPrintTempDTO tWmsPrintTempDTO = new TWmsPrintTempDTO();
        tWmsPrintTempDTO.setId(4);
        tWmsPrintTempDTO.setUpdateTime(new Date().getTime());
        tWmsPrintTempDTO.setCarrier("updateTest");
        printTempService.updatePrintTemp(tWmsPrintTempDTO);
    }

    @Test
    public void findById(){
        TWmsPrintTempEntity tWmsPrintTempEntity = printTempService.findById(4);
        System.out.print(tWmsPrintTempEntity);
    }

    @Test
    public void deleteById(){
        printTempService.removePrintTemp(4);
    }
}
