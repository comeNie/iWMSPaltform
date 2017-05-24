package com.huamengtong.wms.outwh.mapper.service.impl;

import com.huamengtong.wms.dto.outwh.TWmsShipmentHeaderDTO;
import com.huamengtong.wms.outwh.service.IShipmentHeaderService;
import com.huamengtong.wms.outwh.mapper.common.SpringTxTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

/**
 * Created by mario on 2016/11/1.
 */

@Service
public class ShipmentHeaderTest extends SpringTxTestCase {

    @Autowired
    IShipmentHeaderService shipmentHeaderService;


    @Test
    public void insertTest(){
        TWmsShipmentHeaderDTO shipmentHeaderDTO = new TWmsShipmentHeaderDTO();
        shipmentHeaderDTO.setId(123456L);
        shipmentHeaderDTO.setTenantId(3L);
        shipmentHeaderDTO.setWarehouseId(4L);
        shipmentHeaderDTO.setCargoOwnerId(5L);
        shipmentHeaderDTO.setOrderId(6L);
        shipmentHeaderDTO.setDatasourceCode("网络");
        shipmentHeaderDTO.setFromtypeCode("销售");
        shipmentHeaderDTO.setWaveSeq("3I3O3");
        shipmentHeaderDTO.setStatusCode("在线操作");
        shipmentHeaderDTO.setExpressName("SF");
        shipmentHeaderDTO.setTotalQty(200);
        shipmentHeaderDTO.setTotalNetweight(new BigDecimal("10.24"));
        shipmentHeaderDTO.setTotalGrossweight(new BigDecimal("10.24"));
        shipmentHeaderDTO.setTotalVolume(new BigDecimal("10.24"));
        shipmentHeaderDTO.setIsPrintExpress(new Byte("2"));
        shipmentHeaderDTO.setIsPrintDelivery(new Byte("2"));
        shipmentHeaderDTO.setIsPrintPicking(new Byte("2"));
        shipmentHeaderDTO.setIsPrintInvoice(new Byte("2"));
        shipmentHeaderDTO.setIsCancelled(new Byte("2"));
        shipmentHeaderDTO.setIsClosed(new Byte("2"));
        shipmentHeaderDTO.setTotalWeight(new BigDecimal("10.22"));
        shipmentHeaderDTO.setAllocateStatuscode("高级分配");
        shipmentHeaderDTO.setPickStatuscode("等待分配");
        shipmentHeaderDTO.setCheckStatuscode("符合");
        shipmentHeaderDTO.setPackageStatuscode("打包");
        shipmentHeaderDTO.setWeightStatuscode("超重");
        shipmentHeaderDTO.setHandoverStatuscode("已交接");
        shipmentHeaderDTO.setDeliveryStatuscode("已发货");
        shipmentHeaderDTO.setIsEwaybillFinished(new Byte("2"));
        shipmentHeaderDTO.setIsDel(new Byte("0"));
        shipmentHeaderService.createShipmentHeader(shipmentHeaderDTO,getDbshardVO());
        System.out.print("InsertSUCCESS~~~~~~~~~");
    }

    @Test
    public void selectsaleOrderByPrimaryKey(){
        System.out.println(shipmentHeaderService.findByPrimaryKey(18004L,getDbshardVO()));
        System.out.println("select Sucess~~~~~~~~~~~~!");
    }

    @Test
    public void deleteReceiptHeaderByPrimaryKey(){
        shipmentHeaderService.removeShipmentHeader(19001L,getDbshardVO());
        System.out.println("Delete Sucess~~~~~~~~~~~~!");
    }

    @Test
    public void updateSkuCategorys() {
        TWmsShipmentHeaderDTO shipmentHeaderDTO = new TWmsShipmentHeaderDTO();
        shipmentHeaderDTO.setTenantId(1L);
        shipmentHeaderDTO.setWarehouseId(2L);
        shipmentHeaderDTO.setCargoOwnerId(3L);
        shipmentHeaderDTO.setOrderId(4L);
        shipmentHeaderDTO.setDatasourceCode("网络");
        shipmentHeaderDTO.setFromtypeCode("销售");
        shipmentHeaderDTO.setWaveSeq("3I3O3");
        shipmentHeaderDTO.setStatusCode("离线操作");
        shipmentHeaderDTO.setTotalQty(100);
        shipmentHeaderDTO.setTotalNetweight(new BigDecimal("10.24"));
        shipmentHeaderDTO.setTotalGrossweight(new BigDecimal("10.24"));
        shipmentHeaderDTO.setTotalVolume(new BigDecimal("10.24"));
        shipmentHeaderDTO.setIsPrintExpress(new Byte("2"));
        shipmentHeaderDTO.setIsPrintDelivery(new Byte("2"));
        shipmentHeaderDTO.setIsPrintPicking(new Byte("2"));
        shipmentHeaderDTO.setIsPrintInvoice(new Byte("2"));
        shipmentHeaderDTO.setIsCancelled(new Byte("2"));
        shipmentHeaderDTO.setIsClosed(new Byte("2"));
        shipmentHeaderDTO.setTotalWeight(new BigDecimal("10.22"));
        shipmentHeaderDTO.setAllocateStatuscode("高级分配");
        shipmentHeaderDTO.setPickStatuscode("等待分配");
        shipmentHeaderDTO.setCheckStatuscode("符合");
        shipmentHeaderDTO.setPackageStatuscode("打包");
        shipmentHeaderDTO.setWeightStatuscode("超重");
        shipmentHeaderDTO.setHandoverStatuscode("已交接");
        shipmentHeaderDTO.setDeliveryStatuscode("已发货");
        shipmentHeaderDTO.setIsEwaybillFinished(new Byte("2"));
        shipmentHeaderDTO.setIsDel(new Byte("0"));
        shipmentHeaderService.createShipmentHeader(shipmentHeaderDTO,getDbshardVO());
        System.out.print("Update~~~~~~~~~");
    }
}
