package com.huamengtong.wms.sku.mapper.service.impl;

import com.huamengtong.wms.dto.TWmsSkuCategorysDTO;
import com.huamengtong.wms.entity.sku.TWmsSkuCategorysEntity;
import com.huamengtong.wms.sku.service.ISkuCategorysService;
import com.huamengtong.wms.sku.mapper.common.SpringTxTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mario on 2016/10/19.
 */
public class SkuCategorysTest extends SpringTxTestCase {

    @Autowired
    ISkuCategorysService iSkuCategorysService;

    @Test
    public void selectSkuCategorys (){
        List<TWmsSkuCategorysEntity> entity = new ArrayList<TWmsSkuCategorysEntity>();
            entity = iSkuCategorysService.findByParentId(0L,getDbshardVO());
        for (TWmsSkuCategorysEntity i: entity
             ) {
            System.out.print(i);
        }
       /* System.out.print(iSkuCategorysService.findByParentId(0L,getDbshardVO()));*/

    }

    @Test
    public void insertSkuCategorys (){
        TWmsSkuCategorysDTO entity = new TWmsSkuCategorysDTO();
        //entity.setParentId(0L);
        entity.setTenantId(77L);
        entity.setCategoryCode("DF-21-B");
        entity.setCategoryName("蔬菜类");
        entity.setPosition(7);
        entity.setDescription("极好");
        entity.setCreateUser("admin");
        entity.setCreateTime(77L);
        entity.setUpdateUser("admin");
        entity.setUpdateTime(77L);
        iSkuCategorysService.createSkuCategorys(entity,getDbshardVO());
        System.out.print("InsertSUCCESS~~~~~~~~~");
    }

    @Test
    public void updateSkuCategorys(){
        TWmsSkuCategorysDTO entity = new TWmsSkuCategorysDTO();
        //entity.setParentId(0L);
        entity.setTenantId(88L);
        entity.setCategoryCode("DF-21-D");
        entity.setCategoryName("水果类");
        entity.setPosition(1);
        entity.setDescription("完美");
        entity.setCreateUser("admin");
        entity.setCreateTime(9L);
        entity.setUpdateUser("admin");
        entity.setUpdateTime(9L);
        iSkuCategorysService.modifySkuCategorys(entity,getDbshardVO());
        System.out.print("Update.SUCCESS~~~~~~~~~");
    }

    @Test
    public void deleteSkuCategorys(){
        TWmsSkuCategorysDTO entity = new TWmsSkuCategorysDTO();
        //entity.setParentId(0L);
        entity.setCategoryName("水果");
        iSkuCategorysService.querySkuCategorysPages(entity,getDbshardVO());
        System.out.print("Update.SUCCESS~~~~~~~~~");
    }

}
