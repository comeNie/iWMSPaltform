package com.huamengtong.wms.inwh.mapper.service.impl;

import com.huamengtong.wms.dto.inwh.TWmsStocktakingHeaderDTO;
import com.huamengtong.wms.inwh.mapper.common.SpringTxTestCase;
import com.huamengtong.wms.inwh.service.IStocktakingHeaderService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by mario on 2016/11/22.
 */
public class StocktakingHeaderTest extends SpringTxTestCase {

    @Autowired
    IStocktakingHeaderService stocktakingHeaderService;


    @Test
    public void insert(){
        TWmsStocktakingHeaderDTO stocktakingHeaderDTO = new TWmsStocktakingHeaderDTO();
        stocktakingHeaderDTO.setTenantId(88l);
        stocktakingHeaderDTO.setCargoOwnerId(88l);
        stocktakingHeaderDTO.setWarehouseId(88l);
        stocktakingHeaderDTO.setStorageRoomId(128l);
        stocktakingHeaderDTO.setTypeCode("爆炸");
        stocktakingHeaderDTO.setIsAutoAdjust(new Byte("1"));
        stocktakingHeaderDTO.setIsDel(new Byte("1"));
        stocktakingHeaderDTO.setStatusCode("GG");
        stocktakingHeaderDTO.setTotalCategoryQty(1);
        stocktakingHeaderDTO.setTotalLocationQty(1);
        stocktakingHeaderDTO.setTotalStorageRoomQty(1);
        stocktakingHeaderService.createStocktakingHeader(stocktakingHeaderDTO,getDbshardVO());
        System.out.print("GG思密达");
    }

    @Test
    public void select(){
        System.out.print(stocktakingHeaderService.findByPrimaryKey(6666L,getDbshardVO()));
    }

    @Test
    public void update(){
        TWmsStocktakingHeaderDTO stocktakingHeaderDTO = new TWmsStocktakingHeaderDTO();
        stocktakingHeaderDTO.setId(6008l);
        stocktakingHeaderDTO.setTenantId(88l);
        stocktakingHeaderDTO.setCargoOwnerId(88l);
        stocktakingHeaderDTO.setWarehouseId(88l);
        stocktakingHeaderDTO.setTypeCode("BOOM");
        stocktakingHeaderDTO.setIsAutoAdjust(new Byte("1"));
        stocktakingHeaderDTO.setIsDel(new Byte("1"));
        stocktakingHeaderDTO.setStatusCode("GG");
        stocktakingHeaderDTO.setTotalCategoryQty(1);
        stocktakingHeaderDTO.setTotalLocationQty(1);
        stocktakingHeaderDTO.setTotalStorageRoomQty(1);
        stocktakingHeaderService.modifyStocktakingHeader(stocktakingHeaderDTO,getDbshardVO());
        System.out.print("GG思密达");

    }
    @Test
    public void delete(){
    System.out.print(stocktakingHeaderService.findByPrimaryKey(6008L,getDbshardVO()));

}



}
