package com.huamengtong.wms.outwh.mapper.service.impl;

import com.huamengtong.wms.dto.outwh.TWmsDnDetailDTO;
import com.huamengtong.wms.outwh.service.impl.DnDetailService;
import com.huamengtong.wms.outwh.mapper.common.SpringTxTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * Created by mario on 2016/11/3.
 */
public class DnDetailTest  extends SpringTxTestCase {

    @Autowired
    DnDetailService dnDetailService;

    @Test
    public void insertTest() {
        TWmsDnDetailDTO dnDetailDTO = new TWmsDnDetailDTO();
        dnDetailDTO.setTenantId(8L);
        dnDetailDTO.setDnId(8L);
        dnDetailDTO.setSkuId(8L);
        dnDetailDTO.setIsGroup(new Byte("8"));
        dnDetailDTO.setQty(8);
        dnDetailDTO.setPrice(new BigDecimal("1"));
        dnDetailDTO.setAmount(new BigDecimal("1"));
        dnDetailDTO.setPayment(new BigDecimal("1"));
        dnDetailDTO.setIsGift(new Byte("1"));
        dnDetailDTO.setIsCancelled(new Byte("1"));
        dnDetailDTO.setIsDel(new Byte("1"));
        dnDetailService.createDnDetail(dnDetailDTO,getDbshardVO());
        System.out.print("InsertSUCCESS~~~~~~~~~");
    }

    @Test
    public void selectsaleOrderByPrimaryKey(){
        System.out.println(dnDetailService.findByPrimaryKey(1001L,getDbshardVO()));
        System.out.println("select Sucess~~~~~~~~~~~~!");
    }

    @Test
    public void deleteReceiptHeaderByPrimaryKey(){
        dnDetailService.removeByPrimaryKey(1001L,getDbshardVO());
        System.out.println("Delete Sucess~~~~~~~~~~~~!");
    }

    @Test
    public void UpdateTest() {
        TWmsDnDetailDTO dnDetailDTO = new TWmsDnDetailDTO();
        dnDetailDTO.setId(1001L);
        dnDetailDTO.setTenantId(6L);
        dnDetailDTO.setDnId(6L);
        dnDetailDTO.setSkuId(6L);
        dnDetailDTO.setIsGroup(new Byte("1"));
        dnDetailDTO.setQty(66);
        dnDetailDTO.setPrice(new BigDecimal("1"));
        dnDetailDTO.setAmount(new BigDecimal("1"));
        dnDetailDTO.setPayment(new BigDecimal("1"));
        dnDetailDTO.setIsGift(new Byte("1"));
        dnDetailDTO.setIsCancelled(new Byte("1"));
        dnDetailDTO.setIsDel(new Byte("1"));
        dnDetailService.modifyDnDetail(dnDetailDTO,getDbshardVO());
        System.out.print("Update~~~~~~~~~");
    }


}
