package com.huamengtong.wms.main.mapper.service.impl;

import com.alibaba.fastjson.JSON;
import com.huamengtong.wms.main.mapper.common.SpringTxTestCase;
import com.huamengtong.wms.main.service.impl.CodeService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CodeServiceTest extends SpringTxTestCase {

     @Autowired
     CodeService codeService;

    @Test
    public void testGetAllCodeDatas(){
        Map mapList = codeService.getAllCodeDatas(new HashMap());
        System.out.println("JSON-->"+ JSON.toJSONString(mapList));
    }

    @Test
    public  void getParseCodeList(){
        List<String> list = new ArrayList<>();
        list.add("OrderType");
        System.out.println(codeService.getCodeList(list));
    }

}
