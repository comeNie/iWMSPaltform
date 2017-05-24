package com.huamengtong.wms.inwh.mapper.service.impl;

import com.huamengtong.wms.dto.inwh.TWmsAsnDetailDTO;
import com.huamengtong.wms.dto.inwh.TWmsAsnHeaderDTO;
import com.huamengtong.wms.inwh.mapper.common.SpringTxTestCase;
import com.huamengtong.wms.inwh.service.IAsnDetailService;
import com.huamengtong.wms.inwh.service.IAsnHeaderService;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by StickT on 2016/10/31.
 */
public class AsnServiceTest extends SpringTxTestCase {

    @Autowired
    IAsnHeaderService asnHeaderService;

    @Autowired
    IAsnDetailService asnDetailService;


    @Test
    public void insertAsnHeaderTest(){
        TWmsAsnHeaderDTO asnHeaderDTO=new TWmsAsnHeaderDTO();
        asnHeaderDTO.setTenantId(88L);
        asnHeaderDTO.setUpdateUser("YYM");
        asnHeaderDTO.setCreateUser("YYM");
        asnHeaderDTO.setCreateTime(new Date().getTime());
        asnHeaderDTO.setUpdateTime(new Date().getTime());
        asnHeaderDTO.setWarehouseId(88L);
        asnHeaderDTO.setCargoOwnerId(22L);
        asnHeaderDTO.setDatasourceCode("LLLL");
        asnHeaderDTO.setStatusCode("LLLLL");
        asnHeaderDTO.setFromTypeCode("LLLLL");
        asnHeaderDTO.setTotalQty(222);
        asnHeaderDTO.setTotalCartonQty(3333);
        asnHeaderDTO.setTotalAmount(new BigDecimal(22.00155));
        asnHeaderDTO.setTotalNetWeight(new BigDecimal(2221));
        asnHeaderDTO.setTotalGrossWeight(new BigDecimal(333.0145454));
        asnHeaderDTO.setTotalCube(new BigDecimal(555.015));
        asnHeaderDTO.setTotalPalletQty(555);
        asnHeaderService.insertAsnHeader(asnHeaderDTO,getDbshardVO());
        System.out.println("insert success!");
    }

    @Test
    public void updateAsnHeaderTest(){
        TWmsAsnHeaderDTO asnHeaderDTO=new TWmsAsnHeaderDTO();
        asnHeaderDTO.setId(6003L);
//        asnHeaderDTO.setTenantId(88L);
        asnHeaderDTO.setUpdateUser("YYM");
//        asnHeaderDTO.setCreateUser("YYM");
//        asnHeaderDTO.setCreateTime(new Date().getTime());
        asnHeaderDTO.setUpdateTime(new Date().getTime());
//        asnHeaderDTO.setWarehouseId(88L);
//        asnHeaderDTO.setCargoOwnerId(22L);
//        asnHeaderDTO.setDatasourceCode("LLLL");
//        asnHeaderDTO.setStatusCode("LLLLL");
//        asnHeaderDTO.setFromTypeCode("LLLLL");
//        asnHeaderDTO.setTotalQty(333);//修改内容
//        asnHeaderDTO.setTotalCartonQty(3333);
//        asnHeaderDTO.setTotalAmount(new BigDecimal(22.00155));
//        asnHeaderDTO.setTotalNetWeight(new BigDecimal(2221));
//        asnHeaderDTO.setTotalGrossWeight(new BigDecimal(333.0145454));
//        asnHeaderDTO.setTotalCube(new BigDecimal(555.015));
//        asnHeaderDTO.setTotalPalletQty(555);
        asnHeaderService.updateAsnHeader(asnHeaderDTO,getDbshardVO());
        System.out.println("update success!");
    }


    @Test
    public void selectAsnHeaderSysAndDetail(){
        System.out.println(asnHeaderService.findByPrimaryKey(1001L,getDbshardVO()).toString());
        System.out.println(asnDetailService.findByPrimaryKey(1L,getDbshardVO()).toString());
        System.out.println("success selected!");
    }

    @Test
    public void deleteAsnHeaderAndDetail(){
        //System.out.println(asnHeaderService.removeByPrimaryKey(1L,getDbshardVO()));
        //System.out.println(asnDetailService.removeByPrimaryKey(1L,getDbshardVO()));
        System.out.println("delete Success!");
    }

    @Test
    public void insertAsnDetail(){
        TWmsAsnDetailDTO asnDetailDTO=new TWmsAsnDetailDTO();
        asnDetailDTO.setTenantId(88L);
        asnDetailDTO.setWarehouseId(88L);
        asnDetailDTO.setAsnId(3003L);
        asnDetailDTO.setSkuId(111L);
        asnDetailDTO.setSku("sku");
        //asnDetailDTO.setItemName("苹果");
        asnDetailDTO.setExpectedQty(220);
        asnDetailDTO.setContainerNo("555");
        asnDetailDTO.setContainerQty(5665);
        asnDetailDTO.setReceivedQty(555);
        asnDetailDTO.setPrice(new BigDecimal(22.004));
        asnDetailDTO.setStatusCode("LLLL");
        asnDetailDTO.setIsNeedQc(new Byte("3"));
        asnDetailDTO.setIsReject(new Byte("1"));
        asnDetailDTO.setIsDel(new Byte("0"));
        asnDetailService.insertAsnDetail(asnDetailDTO,getDbshardVO());
        System.out.println("Insert Success");

    }


    @Test
    public void insertAsnDetails(){
        int i=0;
        while(i<=31){
            insertAsnDetail();
            i++;
            System.out.println("i:"+i);
        }
    }


    @Test
    public void updateAsnDetail(){
        TWmsAsnDetailDTO asnDetailDTO=new TWmsAsnDetailDTO();
        asnDetailDTO.setId(1001L);
        asnDetailDTO.setAsnId(1001L);
        asnDetailService.updateAsnDetail(asnDetailDTO,getDbshardVO());
        System.out.println("Update Success!");
    }





}
