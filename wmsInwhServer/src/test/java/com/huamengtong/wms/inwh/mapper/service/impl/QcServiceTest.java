package com.huamengtong.wms.inwh.mapper.service.impl;

import com.huamengtong.wms.dto.inwh.TWmsQcDetailDTO;
import com.huamengtong.wms.dto.inwh.TWmsQcHeaderDTO;
import com.huamengtong.wms.inwh.mapper.common.SpringTxTestCase;
import com.huamengtong.wms.inwh.service.IQcDetailService;
import com.huamengtong.wms.inwh.service.IQcHeaderService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created by StickT on 2016/11/3.
 */
public class QcServiceTest extends SpringTxTestCase {

    @Autowired
    IQcHeaderService qcHeaderService;

    @Autowired
    IQcDetailService qcDetailService;

    @Test
    public void createQcHeader(){
        TWmsQcHeaderDTO qcHeaderDTO=new TWmsQcHeaderDTO();
        qcHeaderDTO.setTenantId(88L);
        qcHeaderDTO.setAsnId(2001L);
        qcHeaderDTO.setUpdateUser("YYM");
        qcHeaderDTO.setCreateUser("YYM");
        qcHeaderDTO.setCreateTime(new Date().getTime());
        qcHeaderDTO.setUpdateTime(new Date().getTime());
        qcHeaderDTO.setTotalQty(222);
        qcHeaderService.insertQcHeader(qcHeaderDTO,getDbshardVO());
        System.out.println("insert Success!");
    }

    @Test
    public void updateQcHeader(){
        TWmsQcHeaderDTO qcHeaderDTO=new TWmsQcHeaderDTO();
        qcHeaderDTO.setId(2001L);
        qcHeaderDTO.setTenantId(88L);
        qcHeaderDTO.setAsnId(3001L);
        qcHeaderDTO.setUpdateUser("YYM");
        qcHeaderDTO.setCreateUser("YYM");
        qcHeaderDTO.setCreateTime(new Date().getTime());
        qcHeaderDTO.setUpdateTime(new Date().getTime());
        qcHeaderDTO.setTotalQty(222);
        qcHeaderService.modifyQcHeader(qcHeaderDTO,getDbshardVO());
        System.out.println("update Success!");
    }

    @Test
    public void selectHeaderAndDetail(){
        System.out.println(qcHeaderService.findByPrimaryKey(2001L,getDbshardVO()));
        System.out.println(qcDetailService.findByPrimaryKey(1L,getDbshardVO()));
    }

    @Test
    public void createQcDetail(){
        TWmsQcDetailDTO qcDetailDTO=new TWmsQcDetailDTO();
        qcDetailDTO.setTenantId(88L);
        qcDetailDTO.setQcId(2001L);
        qcDetailDTO.setWarehouseId(88L);
        qcDetailDTO.setSkuBarcode("skubarcode");
        qcDetailDTO.setStatusCode("statusCode");
        qcDetailDTO.setUpdateUser("YYM");
        qcDetailDTO.setCreateUser("YYM");
        qcDetailDTO.setCreateTime(new Date().getTime());
        qcDetailDTO.setUpdateTime(new Date().getTime());
        qcDetailDTO.setSkuId(22L);
        qcDetailDTO.setSku("sku");
        qcDetailDTO.setSkuName("skuName");
        qcDetailService.createQcDetail(qcDetailDTO,getDbshardVO());
        System.out.println("insert Success!");
    }

    @Test
    public void createQCDetails(){
        int i=0;
        while(i<30){
            createQcDetail();
            ++i;
        }
    }

    @Test
    public void updateDetail(){
        TWmsQcDetailDTO qcDetailDTO=new TWmsQcDetailDTO();
        qcDetailDTO.setId(6004L);
        qcDetailDTO.setTenantId(88L);
        qcDetailDTO.setUpdateUser("YYM");
        qcDetailDTO.setCreateUser("YYM");
        qcDetailDTO.setCreateTime(new Date().getTime());
        qcDetailDTO.setUpdateTime(new Date().getTime());
        qcDetailDTO.setSkuId(22L);
        qcDetailDTO.setSku("sku");
        qcDetailDTO.setSkuName("skuNameByYYM");
        qcDetailService.updateQcDetail(qcDetailDTO,getDbshardVO());
        System.out.println("update Success!");

    }


}
