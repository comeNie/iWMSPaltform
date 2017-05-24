package com.huamengtong.wms.inwh.mapper;

import com.huamengtong.wms.entity.inwh.TWmsStocktakingDetailEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by mario on 2016/11/22.
 */
public interface StocktakingDetailMapper {

    TWmsStocktakingDetailEntity selectByPrimaryKey(@Param("id") Long id,@Param("splitTableKey") String splitTableKey);

    Integer insertStocktakingDetail(@Param("entity") TWmsStocktakingDetailEntity stocktakingDetailEntity,@Param("splitTableKey") String splitTableKey);

    Integer deleteStocktakingDetail(@Param("id") Long id,@Param("splitTableKey") String splitTableKey);

    Integer updateStocktakingDetail(@Param("entity") TWmsStocktakingDetailEntity stocktakingDetailEntity,@Param("splitTableKey") String splitTableKey);

    List<TWmsStocktakingDetailEntity> queryDetailPages(Map map);

    Integer queryDetailPageCount(Map map);

    List<TWmsStocktakingDetailEntity> queryStocktakingDetails(@Param("headerId")Long headerId,@Param("splitTableKey") String splitTableKey);

    TWmsStocktakingDetailEntity selectByCargo(Map map);

}
