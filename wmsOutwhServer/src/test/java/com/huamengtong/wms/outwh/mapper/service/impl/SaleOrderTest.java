package com.huamengtong.wms.outwh.mapper.service.impl;


import com.huamengtong.wms.dto.outwh.TWmsSaleOrderDTO;
import com.huamengtong.wms.outwh.service.ISaleOrderService;
import com.huamengtong.wms.outwh.mapper.common.SpringTxTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by mario on 2016/10/31.
 */

public class SaleOrderTest extends SpringTxTestCase {

    @Autowired
    ISaleOrderService saleOrderService;



    @Test
    public void createsaleOrder(){
        TWmsSaleOrderDTO saleOrderDTO = new TWmsSaleOrderDTO();
        saleOrderDTO.setTenantId(1L);
        saleOrderDTO.setReferNo("3B3X3C");
        saleOrderDTO.setTradeNo("3I3O3L");
        saleOrderDTO.setIsUrgent(new Byte("1"));
        saleOrderDTO.setOrderTime(new Date().getTime());
        saleOrderDTO.setPostPayTypecode("在线");
        saleOrderDTO.setPostFee(new BigDecimal(100.1));
        saleOrderDTO.setActualPayment(new BigDecimal(100.1));
        saleOrderDTO.setIsCod(new Byte("1"));
        saleOrderDTO.setIsNeedDelivery(new Byte("1"));
        saleOrderDTO.setIsNeedInvoice(new Byte("1"));
        saleOrderDTO.setIsDel(new Byte("0"));
        saleOrderService.createSaleOrder(saleOrderDTO,getDbshardVO());
        System.out.print("InsertSUCCESS~~~~~~~~~");
    }

    @Test
    public void selectsaleOrderByPrimaryKey(){
        System.out.println(saleOrderService.findByPrimaryKey(13002L,getDbshardVO()));
        System.out.println("select Sucess~~~~~~~~~~~~!");
    }

    @Test
    public void deleteReceiptHeaderByPrimaryKey(){
        saleOrderService.removeSaleOrder(1L,getDbshardVO());
        System.out.println("Delete Sucess~~~~~~~~~~~~!");
    }

    @Test
    public void updateSkuCategorys() {
        TWmsSaleOrderDTO saleOrderDTO = new TWmsSaleOrderDTO();
        saleOrderDTO.setTenantId(1L);
        saleOrderDTO.setReferNo("3B3X3C");
        saleOrderDTO.setTradeNo("3I3O3L");
        saleOrderDTO.setIsUrgent(new Byte("1"));
        saleOrderDTO.setOrderTime(new Date().getTime());
        saleOrderDTO.setPostPayTypecode("在线支付");
        saleOrderDTO.setPostFee(new BigDecimal(100.2));
        saleOrderDTO.setActualPayment(new BigDecimal(100.2));
        saleOrderDTO.setIsCod(new Byte("1"));
        saleOrderDTO.setIsNeedDelivery(new Byte("1"));
        saleOrderDTO.setIsNeedInvoice(new Byte("1"));
        saleOrderDTO.setIsDel(new Byte("0"));
        saleOrderService.modifySaleOrder(saleOrderDTO,getDbshardVO());
        System.out.print("yaoxiugai~~~~~~~~~");
    }

}
