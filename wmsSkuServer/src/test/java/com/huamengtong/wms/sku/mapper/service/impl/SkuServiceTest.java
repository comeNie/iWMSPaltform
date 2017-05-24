
package com.huamengtong.wms.sku.mapper.service.impl;


import com.huamengtong.wms.entity.sku.TWmsSkuEntity;
import com.huamengtong.wms.sku.service.ISkuService;
import com.huamengtong.wms.sku.mapper.common.SpringTxTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class SkuServiceTest extends SpringTxTestCase {

    @Autowired
    ISkuService skuService;



    @Test
    public void skuSelectByIdTest(){
        TWmsSkuEntity skuEntity = skuService.querySkuById(3L,getDbshardVO());
        System.out.print(skuEntity);
    }

    @Test
    public void skkTest (){
        TWmsSkuEntity skuEntity = new TWmsSkuEntity();
        skuService.validateHasSku(skuEntity.getCargoOwnerId(),skuEntity.getBarcode(),getDbshardVO());
    }

    @Test
    public void selectSkuCargoOwner(){
        List<TWmsSkuEntity> skuEntityList = skuService.findSkuCargoOwner(null,null,null,null,getDbshardVO());
        System.out.println("----------");
    }

}
