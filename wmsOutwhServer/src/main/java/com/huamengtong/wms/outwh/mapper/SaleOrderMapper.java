package com.huamengtong.wms.outwh.mapper;


import com.huamengtong.wms.entity.outwh.TWmsSaleOrderEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by mario on 2016/10/31.
 */
public interface SaleOrderMapper {

    List<TWmsSaleOrderEntity> querySaleOrderPages (@Param("entity") TWmsSaleOrderEntity saleOrderEntity, @Param("splitTableKey") String splitTableKey);

    Integer querySaleOrderPageCount(@Param("entity") TWmsSaleOrderEntity saleOrderEntity, @Param("splitTableKey") String splitTableKey);

    TWmsSaleOrderEntity selectByPrimaryKey(@Param("id") Long id, @Param("splitTableKey") String splitTableKey);

    Integer deleteByPrimaryKey(@Param("id") Long id,@Param("splitTableKey") String splitTableKey);

    Integer insertSaleOrder(@Param("entity") TWmsSaleOrderEntity saleOrderEntity, @Param("splitTableKey") String splitTableKey);

    Integer updateSaleOrder(@Param("entity") TWmsSaleOrderEntity saleOrderEntity, @Param("splitTableKey") String splitTableKey);

    List<TWmsSaleOrderEntity> selectByPrimaryKeyList(@Param("ids") List<Long> ids, @Param("splitTableKey") String splitTableKey);
}
