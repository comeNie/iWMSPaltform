package com.huamengtong.wms.outwh.mapper.service.impl;

import com.huamengtong.wms.dto.outwh.TWmsDnHeaderDTO;
import com.huamengtong.wms.outwh.service.impl.DnHeaderService;
import com.huamengtong.wms.outwh.mapper.common.SpringTxTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created by mario on 2016/11/3.
 */
public class DnHeaderTest extends SpringTxTestCase {

    @Autowired
    DnHeaderService dnHeaderService;

    @Test
    public void insertTest() {
        TWmsDnHeaderDTO tWmsDnHeaderDTO = new TWmsDnHeaderDTO();
        tWmsDnHeaderDTO.setTenantId(3L);
        tWmsDnHeaderDTO.setWarehouseId(4L);
        tWmsDnHeaderDTO.setCargoOwnerId(5L);
        tWmsDnHeaderDTO.setOrderId(6L);
        tWmsDnHeaderDTO.setInvoiceId(7L);
        tWmsDnHeaderDTO.setDatasourceCode("网络");
        tWmsDnHeaderDTO.setFromtypeCode("销售");
        tWmsDnHeaderDTO.setTypeCode("正常");
        tWmsDnHeaderDTO.setStatusCode("在线操作");
        tWmsDnHeaderDTO.setIsCancelled(new Byte("1"));
        tWmsDnHeaderDTO.setIsFailed(new Byte("1"));
        tWmsDnHeaderDTO.setAuditedTime(new Date().getTime());
        tWmsDnHeaderDTO.setAuditedUser("mario");
        tWmsDnHeaderDTO.setIsDel(new Byte("1"));
        dnHeaderService.createDnHeader(tWmsDnHeaderDTO,getDbshardVO());
        System.out.print("InsertSUCCESS~~~~~~~~~");
    }

    @Test
    public void selectsaleOrderByPrimaryKey(){
        System.out.println(dnHeaderService.findByPrimaryKey(2001L,getDbshardVO()));
        System.out.println("select Sucess~~~~~~~~~~~~!");
    }

    @Test
    public void deleteReceiptHeaderByPrimaryKey(){
        dnHeaderService.removeDnHeader(2001L,getDbshardVO());
        System.out.println("Delete Sucess~~~~~~~~~~~~!");
    }

    @Test
    public void UpdateTest() {
        TWmsDnHeaderDTO tWmsDnHeaderDTO = new TWmsDnHeaderDTO();
        tWmsDnHeaderDTO.setId(2001L);
        tWmsDnHeaderDTO.setTenantId(6L);
        tWmsDnHeaderDTO.setWarehouseId(6L);
        tWmsDnHeaderDTO.setCargoOwnerId(6L);
        tWmsDnHeaderDTO.setOrderId(6L);
        tWmsDnHeaderDTO.setInvoiceId(66L);
        tWmsDnHeaderDTO.setDatasourceCode("网络");
        tWmsDnHeaderDTO.setFromtypeCode("销售");
        tWmsDnHeaderDTO.setTypeCode("正常");
        tWmsDnHeaderDTO.setStatusCode("在线操作");
        tWmsDnHeaderDTO.setIsCancelled(new Byte("1"));
        tWmsDnHeaderDTO.setIsFailed(new Byte("1"));
        tWmsDnHeaderDTO.setAuditedTime(new Date().getTime());
        tWmsDnHeaderDTO.setAuditedUser("mario");
        tWmsDnHeaderDTO.setIsDel(new Byte("1"));
        dnHeaderService.modifyDnHeader(tWmsDnHeaderDTO,getDbshardVO());
        System.out.print("InsertSUCCESS~~~~~~~~~");
    }

}
