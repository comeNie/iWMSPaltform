package com.huamengtong.wms.outwh.mapper.service.impl;

import com.huamengtong.wms.dto.outwh.TWmsDnInvoiceDTO;
import com.huamengtong.wms.outwh.service.IDnInvoiceService;
import com.huamengtong.wms.outwh.mapper.common.SpringTxTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * Created by mario on 2016/11/10.
 */
public class DnInvoiceTest  extends SpringTxTestCase{

    @Autowired
    IDnInvoiceService dnInvoiceService;

    @Test
    public void createDnInvoice(){
        TWmsDnInvoiceDTO dnInvoiceDTO = new TWmsDnInvoiceDTO();
        dnInvoiceDTO.setTenantId(88L);
        dnInvoiceDTO.setInvoiceTotal(new BigDecimal("6000"));
        dnInvoiceDTO.setIsPrintsku(new Byte("6"));
        dnInvoiceDTO.setIsDel(new Byte("6"));
        dnInvoiceService.createDnInvoice(dnInvoiceDTO,getDbshardVO());
        System.out.print("InsertSUCCESS~~~~~~~~~");
    }

    @Test
    public void selectByDnInvoice(){
        System.out.print(dnInvoiceService.findByPrimaryKey(5001L,getDbshardVO()));
        System.out.println("select Sucess~~~~~~~~~~~~!");
    }

    @Test
    public  void updateDnInvoice(){
        TWmsDnInvoiceDTO dnInvoiceDTO = new TWmsDnInvoiceDTO();
        dnInvoiceDTO.setId(5001L);
        dnInvoiceDTO.setTenantId(88L);
        dnInvoiceDTO.setInvoiceTotal(new BigDecimal("2000"));
        dnInvoiceDTO.setIsPrintsku(new Byte("2"));
        dnInvoiceDTO.setIsDel(new Byte("2"));
        dnInvoiceService.modifyDnInvoice(dnInvoiceDTO,getDbshardVO());
        System.out.print("updateSUCCESS~~~~~~~~~");
    }

    @Test
    public void deleteDnInvoice(){
        System.out.print(dnInvoiceService.removeDnInvoice(5001L,getDbshardVO()));
        System.out.println("delete Sucess~~~~~~~~~~~~!");
    }
}
