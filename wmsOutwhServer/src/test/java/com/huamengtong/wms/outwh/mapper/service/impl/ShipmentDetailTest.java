package com.huamengtong.wms.outwh.mapper.service.impl;


import com.huamengtong.wms.dto.outwh.TWmsShipmentDetailDTO;
import com.huamengtong.wms.outwh.service.IShipmentDetailService;
import com.huamengtong.wms.outwh.mapper.common.SpringTxTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

/**
 * Created by Edwin on 2016/10/20.
 */

@Service
public class ShipmentDetailTest extends SpringTxTestCase {

    @Autowired
    IShipmentDetailService shipmentDetailService;

    @Test
    public void insertTest(){

        TWmsShipmentDetailDTO shipmentDetailDTO = new TWmsShipmentDetailDTO();
        shipmentDetailDTO.setShipmentId(2L);
        shipmentDetailDTO.setTenantId(2L);
        shipmentDetailDTO.setSaleOrderNo("2k3K6K");
        shipmentDetailDTO.setSkuId(99L);
        shipmentDetailDTO.setOrderedQty(200);
        shipmentDetailDTO.setAllocatedQty(300);
        shipmentDetailDTO.setPickedQty(400);
        shipmentDetailDTO.setShippedQty(500);
        shipmentDetailDTO.setGrossWeight(new BigDecimal("22.6"));
        shipmentDetailDTO.setNetWeight(new BigDecimal("22.7"));
        shipmentDetailDTO.setVolume(new BigDecimal("22.8"));
        shipmentDetailDTO.setIsGift(new Byte("1"));
        shipmentDetailDTO.setIsCanceled(new Byte("0"));
        shipmentDetailDTO.setIsDel(new Byte("0"));
        shipmentDetailService.createShipmentDetail(shipmentDetailDTO,getDbshardVO());
        System.out.print("InsertSUCCESS~~~~~~~~~");
    }

    @Test
    public void selectsaleOrderByPrimaryKey(){
        System.out.println(shipmentDetailService.findByPrimaryKey(3005L,getDbshardVO()));
        System.out.println("select Sucess~~~~~~~~~~~~!");
    }

    @Test
    public void deleteReceiptHeaderByPrimaryKey(){
        shipmentDetailService.removeShipmentDetail(3004L,getDbshardVO());
        System.out.println("Delete Sucess~~~~~~~~~~~~!");
    }

    @Test
    public void updateSkuCategorys() {
        TWmsShipmentDetailDTO shipmentDetailDTO = new TWmsShipmentDetailDTO();
        shipmentDetailDTO.setId(3005L);
//        shipmentDetailDTO.setShipmentId(2L);
//        shipmentDetailDTO.setTenantId(2L);
//        shipmentDetailDTO.setSaleOrderNo("2k3K7K");
//        shipmentDetailDTO.setSkuId(99L);
//        shipmentDetailDTO.setOrderedQty(200);
          shipmentDetailDTO.setAllocatedQty(0);
//        shipmentDetailDTO.setPickedQty(400);
//        shipmentDetailDTO.setShippedQty(500);
//        shipmentDetailDTO.setGrossWeight(new BigDecimal("22.6"));
//        shipmentDetailDTO.setNetWeight(new BigDecimal("22.7"));
//        shipmentDetailDTO.setVolume(new BigDecimal("22.8"));
//        shipmentDetailDTO.setIsGift(new Byte("1"));
//        shipmentDetailDTO.setIsCanceled(new Byte("0"));
//        shipmentDetailDTO.setIsDel(new Byte("0"));
        shipmentDetailService.modifyShipmentDetail(shipmentDetailDTO,getDbshardVO());
        System.out.print("InsertSUCCESS~~~~~~~~~");
    }

}
