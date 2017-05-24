package com.huamengtong.wms.inventory.mapper.service.impl;

import com.alibaba.fastjson.JSON;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.inventory.TWmsInventoryLogDTO;
import com.huamengtong.wms.inventory.mapper.common.SpringTxTestCase;
import com.huamengtong.wms.inventory.service.IInventoryLogService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by mario on 2017/3/15.
 */
public class inventoryLogService extends SpringTxTestCase {
    @Autowired
    IInventoryLogService iInventoryLogService;

//    @Test
//    public void pages(){
//        TWmsInventoryLogDTO inventoryLogDTO = new TWmsInventoryLogDTO();
//       // PageResponse pageResponse = iInventoryLogService.selectInOutMap(inventoryLogDTO,getDbshardVO());
//        System.out.println(JSON.toJSONString(pageResponse.getRows()));
//    }
}
