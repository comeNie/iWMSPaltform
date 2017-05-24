package com.huamengtong.wms.inwh.mapper.service.impl;

import com.huamengtong.wms.dto.inwh.TWmsReceiptDetailDTO;
import com.huamengtong.wms.dto.inwh.TWmsReceiptHeaderDTO;
import com.huamengtong.wms.dto.report.TWmsReportReceiptDetailDTO;
import com.huamengtong.wms.inwh.mapper.common.SpringTxTestCase;
import com.huamengtong.wms.inwh.service.IReceiptDetailService;
import com.huamengtong.wms.inwh.service.IReceiptHeaderService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ReceiptServiceTest extends SpringTxTestCase {

    @Autowired
    IReceiptHeaderService receiptHeaderService;

    @Autowired
    IReceiptDetailService receiptDetailService;

    @Test
    public void createReceiptHeader(){
        TWmsReceiptHeaderDTO receiptHeaderDTO=new TWmsReceiptHeaderDTO();
        receiptHeaderDTO.setWarehouseId(88L);
        receiptHeaderDTO.setTenantId(22L);
        receiptHeaderDTO.setCargoOwnerId(21L);
        receiptHeaderDTO.setFromTypeCode("LLL");
        receiptHeaderDTO.setReceiptDate(788L);
        receiptHeaderDTO.setOriginNo(445L);
        receiptHeaderDTO.setTotalQty(222);
        receiptHeaderDTO.setTotalPalletQty(112);;
        receiptHeaderDTO.setTotalCartonQty(113);
        receiptHeaderDTO.setTotalNetWeight(new BigDecimal(6.6666));
        receiptHeaderDTO.setTotalGrossWeight(new BigDecimal(10.223));
        receiptHeaderDTO.setTotalVolume(new BigDecimal(10.0002));
        receiptHeaderDTO.setReceiptUser("YYM");
        receiptHeaderDTO.setQcInspector("YYF");
        receiptHeaderDTO.setDescription("苹果");
        receiptHeaderDTO.setStatusCode("HH");
        receiptHeaderDTO.setCreateUser("YYM");
        receiptHeaderDTO.setCreateTime(797947L);
        receiptHeaderDTO.setUpdateUser("YYF");
        receiptHeaderDTO.setUpdateTime(44555L);
        receiptHeaderDTO.setAuditedUser("DQ");
        receiptHeaderDTO.setAuditedTime(5464L);
        receiptHeaderDTO.setReceiptTypeCode("222");
        receiptHeaderDTO.setIsDel(new Byte("1"));
        receiptHeaderDTO.setVersion(44);
        receiptHeaderDTO.setFromOrderNo("KKK");
        receiptHeaderDTO.setFromOmsNo("HHHH");
        receiptHeaderService.createReceiptHeader(receiptHeaderDTO,getDbshardVO());
        System.out.println("insert Sucess!");
    }

    @Test
    public void selectReceiptHeaderByPrimaryKey(){
        System.out.println(receiptHeaderService.findByPrimaryKey(1L,getDbshardVO()));

    }


    @Test
    public void modifyReceiptHeader(){
        TWmsReceiptHeaderDTO receiptHeaderDTO=new TWmsReceiptHeaderDTO();
        //receiptHeaderDTO.setId(1L);
        receiptHeaderDTO.setWarehouseId(89L);
        receiptHeaderDTO.setTenantId(22L);
        receiptHeaderDTO.setCargoOwnerId(21L);
        receiptHeaderDTO.setFromTypeCode("LLL");
        receiptHeaderDTO.setReceiptDate(788L);
        receiptHeaderDTO.setOriginNo(445L);
        receiptHeaderDTO.setTotalQty(222);
        receiptHeaderDTO.setTotalPalletQty(112);;
        receiptHeaderDTO.setTotalCartonQty(113);
        receiptHeaderDTO.setTotalNetWeight(new BigDecimal(6.6666));
        receiptHeaderDTO.setTotalGrossWeight(new BigDecimal(10.223));
        receiptHeaderDTO.setTotalVolume(new BigDecimal(10.0002));
        receiptHeaderDTO.setReceiptUser("YYM");
        receiptHeaderDTO.setQcInspector("YYF");
        receiptHeaderDTO.setDescription("苹果");
        receiptHeaderDTO.setStatusCode("HH");
        receiptHeaderDTO.setCreateUser("YYM");
        receiptHeaderDTO.setCreateTime(797947L);
        receiptHeaderDTO.setUpdateUser("YYF");
        receiptHeaderDTO.setUpdateTime(44555L);
        receiptHeaderDTO.setAuditedUser("DQ");
        receiptHeaderDTO.setAuditedTime(5464L);
        receiptHeaderDTO.setReceiptTypeCode("222");
        receiptHeaderDTO.setIsDel(new Byte("1"));
        receiptHeaderDTO.setVersion(44);
        receiptHeaderDTO.setFromOrderNo("KKK");
        receiptHeaderDTO.setFromOmsNo("HHHH");
        receiptHeaderService.modifyReceiptHeader(receiptHeaderDTO,getDbshardVO());
        System.out.println("Modify Sucess!");
    }



    @Test
    public void deleteReceiptHeaderByPrimaryKey(){
        receiptHeaderService.removeReceiptHeader(2L,getDbshardVO());
        System.out.println("Delete Sucess!");
    }


    @Test
    public void createReceiptDetail(){
        TWmsReceiptDetailDTO receiptDetailDTO=new TWmsReceiptDetailDTO();
        receiptDetailDTO.setReceiptId(1L);
        receiptDetailDTO.setTenantId(222L);
        receiptDetailDTO.setSkuId(11L);
        receiptDetailDTO.setWorkGroupNo("LLL");
        receiptDetailDTO.setLocationId(22L);
        receiptDetailDTO.setPalletNo("KKK");
        receiptDetailDTO.setContainerNo("TTT");
        receiptDetailDTO.setReceivedQty(444);
        receiptDetailDTO.setReceiptTime(545454L);
        receiptDetailDTO.setNetWeight(new BigDecimal(44.253));
        receiptDetailDTO.setGrossWeight(new BigDecimal(55.2223));
        receiptDetailDTO.setVolume(new BigDecimal(55.336));
        receiptDetailDTO.setCreateUser("YYF");
        receiptDetailDTO.setCreateTime(9898989L);
        receiptDetailDTO.setUpdateUser("YYM");
        receiptDetailDTO.setUpdateTime(8990L);
        receiptDetailDTO.setDescription("苹果");
        receiptDetailDTO.setInventoryStatusCode("UUUU");
        receiptDetailDTO.setIsDel(new Byte("88"));
        receiptDetailDTO.setVersion(545454);
        receiptDetailService.createReceiptDetail(receiptDetailDTO,getDbshardVO());
        System.out.println("create sucess!");
    }


    @Test
    public void modifyReceiptDetail(){
        TWmsReceiptDetailDTO receiptDetailDTO=new TWmsReceiptDetailDTO();
        receiptDetailDTO.setId(22L);
        receiptDetailDTO.setReceiptId(2L);
        receiptDetailDTO.setTenantId(222L);
        receiptDetailDTO.setSkuId(11L);
        receiptDetailDTO.setWorkGroupNo("LLL");
        receiptDetailDTO.setLocationId(22L);
        receiptDetailDTO.setPalletNo("KKK");
        receiptDetailDTO.setContainerNo("TTT");
        receiptDetailDTO.setReceivedQty(444);
        receiptDetailDTO.setReceiptTime(545454L);
        receiptDetailDTO.setNetWeight(new BigDecimal(44.253));
        receiptDetailDTO.setGrossWeight(new BigDecimal(55.2223));
        receiptDetailDTO.setVolume(new BigDecimal(55.336));
        receiptDetailDTO.setCreateUser("YYF");
        receiptDetailDTO.setCreateTime(9898989L);
        receiptDetailDTO.setUpdateUser("YYM");
        receiptDetailDTO.setUpdateTime(8990L);
        receiptDetailDTO.setDescription("苹果");
        receiptDetailDTO.setInventoryStatusCode("UUUU");
        receiptDetailDTO.setIsDel(new Byte("88"));
        receiptDetailDTO.setVersion(545454);
        receiptDetailService.modifyReceiptDetail(receiptDetailDTO,getDbshardVO());
        //receiptDetailService.modifyReceiptDetail(receiptDetailDTO,getDbshardVO());
        System.out.println("modify sucess!");

    }


    @Test
    public void deleteReceiptDetailByPrimaryKey(){
        System.out.println("delete Success!");

    }



    @Test
    public void selectReceipts(){
        Map<String,Object> map = new HashMap<>();
        Integer offset = 0,pageSize = 30;
        Long cargoOwnerId = 45L;
        map.put("offset",offset);
        map.put("pageSize",pageSize);
        map.put("cargoOwnerId",cargoOwnerId);
        List<TWmsReportReceiptDetailDTO> reportReceiptDetailDTOList = receiptHeaderService.getReceiptDetailList(map,getDbshardVO());
        System.out.println(reportReceiptDetailDTOList.toArray().toString());

    }


}
