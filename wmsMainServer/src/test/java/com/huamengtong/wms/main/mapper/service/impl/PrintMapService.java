package com.huamengtong.wms.main.mapper.service.impl;


import com.huamengtong.wms.dto.TWmsPrintMapDTO;
import com.huamengtong.wms.entity.main.TWmsPrintMapEntity;
import com.huamengtong.wms.main.mapper.common.SpringTxTestCase;
import com.huamengtong.wms.main.service.IPrintMapService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class PrintMapService extends SpringTxTestCase{

    @Autowired
    IPrintMapService printMapService;

    @Test
    public void creatPrintMap(){
        TWmsPrintMapDTO tWmsPrintMapDTO = new TWmsPrintMapDTO();
        tWmsPrintMapDTO.setUpdateUser("user1");
        tWmsPrintMapDTO.setCreateUser("admin");
        tWmsPrintMapDTO.setCreateTime(new Date().getTime());
        tWmsPrintMapDTO.setUpdateTime(new Date().getTime());
        tWmsPrintMapDTO.setCode("code");
        tWmsPrintMapDTO.setDetail("detail");
        tWmsPrintMapDTO.setField("field");
        tWmsPrintMapDTO.setName("name");
        tWmsPrintMapDTO.setHasDetail((byte)1);
        printMapService.createPrintMap(tWmsPrintMapDTO);
    }

    @Test
    public void queryById(){
        TWmsPrintMapEntity tWmsPrintMapEntity = printMapService.findById(12);
        System.out.print(tWmsPrintMapEntity);
    }

    @Test
    public void updateTest(){
        TWmsPrintMapDTO tWmsPrintMapDTO = new TWmsPrintMapDTO();
        tWmsPrintMapDTO.setName("upadateTest");
        tWmsPrintMapDTO.setId(12);
        printMapService.updatePrintMap(tWmsPrintMapDTO);
    }

    @Test
    public void deleteTest(){
        printMapService.removePrintMap(12);
    }
}
