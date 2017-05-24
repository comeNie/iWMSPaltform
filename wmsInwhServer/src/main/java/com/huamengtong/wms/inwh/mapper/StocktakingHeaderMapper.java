package com.huamengtong.wms.inwh.mapper;

import com.huamengtong.wms.entity.inwh.TWmsStocktakingHeaderEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by mario on 2016/11/22.
 */
public interface StocktakingHeaderMapper {

    List<TWmsStocktakingHeaderEntity> queryStocktakingHeaderPages(@Param("entity") TWmsStocktakingHeaderEntity stocktakingHeaderEntity, @Param("splitTableKey") String splitTableKey);

    Integer queryStocktakingHeaderPageCount(@Param("entity") TWmsStocktakingHeaderEntity stocktakingHeaderEntity,@Param("splitTableKey") String splitTableKey);

    TWmsStocktakingHeaderEntity selectByPrimaryKey(@Param("id") Long id,@Param("splitTableKey") String splitTableKey);

    Integer insertStocktakingHeader(@Param("entity") TWmsStocktakingHeaderEntity stocktakingHeaderEntity,@Param("splitTableKey") String splitTableKey);

    Integer deleteStocktakingHeader(@Param("id") Long id,@Param("splitTableKey") String splitTableKey);

    Integer updateStocktakingHeader(@Param("entity") TWmsStocktakingHeaderEntity stocktakingHeaderEntity,@Param("splitTableKey") String splitTableKey);

}
