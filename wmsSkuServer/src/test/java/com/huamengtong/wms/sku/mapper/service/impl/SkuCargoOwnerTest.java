package com.huamengtong.wms.sku.mapper.service.impl;

import com.huamengtong.wms.dto.sku.TWmsSkuCargoOwnerDTO;
import com.huamengtong.wms.sku.mapper.SkuCargoOwnerMapper;
import com.huamengtong.wms.sku.mapper.common.SpringTxTestCase;
import com.huamengtong.wms.sku.service.ISkuCargoOwnerService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by StickT on 2017/1/5.
 */
public class SkuCargoOwnerTest extends SpringTxTestCase {

    @Autowired
    ISkuCargoOwnerService skuCargoOwnerService;

    @Test
    public void insertSkuCargoOwner(){
        TWmsSkuCargoOwnerDTO skuCargoOwnerDTO = new TWmsSkuCargoOwnerDTO();
        skuCargoOwnerDTO.setSkuId(113L);
        skuCargoOwnerDTO.setSku("AAAAABBBBB");
        skuCargoOwnerDTO.setCargoOwnerId(88L);
        skuCargoOwnerDTO.setCargoOwnerBarcode("HMTHF");
        skuCargoOwnerService.insertSkuCargoOwner(skuCargoOwnerDTO,getDbshardVO());
    }


    @Test
    public void updateSkuCargoOwner(){
        TWmsSkuCargoOwnerDTO skuCargoOwnerDTO = new TWmsSkuCargoOwnerDTO();
        skuCargoOwnerDTO.setSkuId(112L);
        skuCargoOwnerDTO.setSku("AAAAABBBBBLL");
        skuCargoOwnerDTO.setCargoOwnerId(89L);
        skuCargoOwnerDTO.setCargoOwnerBarcode("HMTHFHH");
        skuCargoOwnerService.updateSkuCargoOwnerBySkuId(skuCargoOwnerDTO,getDbshardVO());
        System.out.println("update Success!");
    }

    @Test
    public void findAll(){
        System.out.println(skuCargoOwnerService.findAll(getDbshardVO()).toString());
        System.out.println(skuCargoOwnerService.selectBySkuIdAndCargoOwnerId(112L,89L,getDbshardVO()));
    }

    @Test
    public void findByCargoOwnerBarcode(){
        System.out.println(skuCargoOwnerService.findByCargoOwnerBarcode("201701093001",getDbshardVO()));
    }





}
