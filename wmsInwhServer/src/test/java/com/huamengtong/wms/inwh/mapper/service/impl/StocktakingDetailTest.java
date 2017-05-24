package com.huamengtong.wms.inwh.mapper.service.impl;

import com.huamengtong.wms.dto.inwh.TWmsStocktakingDetailDTO;
import com.huamengtong.wms.inwh.mapper.StocktakingDetailMapper;
import com.huamengtong.wms.inwh.mapper.common.SpringTxTestCase;
import com.huamengtong.wms.inwh.service.IStocktakingDetailService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by mario on 2016/11/22.
 */
public class StocktakingDetailTest extends SpringTxTestCase {

    @Autowired
    IStocktakingDetailService stocktakingDetailService;


    @Test
    public void testInsertStocktakingDetail(){
        TWmsStocktakingDetailDTO stocktakingDetailDTO = new TWmsStocktakingDetailDTO();
        stocktakingDetailDTO.setTenantId(88L);
        stocktakingDetailDTO.setHeaderId(88L);
        stocktakingDetailDTO.setStorageRoomId(88l);
        stocktakingDetailDTO.setSkuId(88l);
        stocktakingDetailDTO.setSkuName("mario");
        stocktakingDetailDTO.setSkuBarcode("NigihayaMi");
        stocktakingDetailDTO.setCountQty(10);
        stocktakingDetailDTO.setRecountQty(10);
        stocktakingDetailDTO.setSystemQty(10);
        stocktakingDetailDTO.setIsTacked(new Byte("1"));
        stocktakingDetailDTO.setCounter(10);
        stocktakingDetailDTO.setIsDel(new Byte("1"));
        stocktakingDetailService.createStocktakingDetailDetail(stocktakingDetailDTO,getDbshardVO());
        System.out.print("mario66666666666666666666666");
    }

    @Test
    public void testUpdateStocktakingDetail(){
        TWmsStocktakingDetailDTO stocktakingDetailDTO = new TWmsStocktakingDetailDTO();
        stocktakingDetailDTO.setId(2014l);
        stocktakingDetailDTO.setTenantId(88L);
        stocktakingDetailDTO.setHeaderId(88L);
        stocktakingDetailDTO.setStorageRoomId(88l);
        stocktakingDetailDTO.setSkuId(88l);
        stocktakingDetailDTO.setSkuName("mario");
        stocktakingDetailDTO.setSkuBarcode("琥珀川NigihayaMi");
        stocktakingDetailDTO.setCountQty(10);
        stocktakingDetailDTO.setRecountQty(10);
        stocktakingDetailDTO.setSystemQty(10);
        stocktakingDetailDTO.setIsTacked(new Byte("1"));
        stocktakingDetailDTO.setCounter(10);
        stocktakingDetailDTO.setIsDel(new Byte("1"));
        stocktakingDetailService.modifyStocktakingDetail(stocktakingDetailDTO,getDbshardVO());
        System.out.print("mario66666666666666666666666");
    }

    @Test
    public void testSelectStocktakingDetail(){
        System.out.print(stocktakingDetailService.findByPrimaryKey(2016L,getDbshardVO()));
    }

    @Test
    public void testDeleteStocktakingDetail(){
        System.out.print(stocktakingDetailService.removeStocktakingDetail(2014l,getDbshardVO()));
    }

}
