package com.huamengtong.wms.sku.mapper;

import com.huamengtong.wms.entity.sku.TWmsSkuCategorysEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by mario on 2016/10/19.
 */
public interface SkuCategorysMapper {

    List<TWmsSkuCategorysEntity> querySkuCategorysPages(@Param("entity") TWmsSkuCategorysEntity skuCategorysEntity, @Param("splitTableKey") String splitTableKey);

    Integer querySkuCategorysPageCount(@Param("entity") TWmsSkuCategorysEntity skuCategorysEntity, @Param("splitTableKey") String splitTableKey);

    TWmsSkuCategorysEntity selectByPrimaryKey(@Param("id") Long id, @Param("splitTableKey") String splitTableKey);

    Integer deleteSkuCategorys(@Param("id") Long id, @Param("splitTableKey") String splitTableKey);

    Integer insertSkuCategorys(@Param("entity") TWmsSkuCategorysEntity skuCategorysEntity, @Param("splitTableKey") String splitTableKey);

    Integer updateSkuCategorys(@Param("entity") TWmsSkuCategorysEntity skuCategorysEntity, @Param("splitTableKey") String splitTableKey);

    List<TWmsSkuCategorysEntity> selectByParentId(@Param("parentId") Long parentId,@Param("splitTableKey") String splitTableKey);

    List<TWmsSkuCategorysEntity> selectAll(@Param("splitTableKey") String splitTableKey);


}

