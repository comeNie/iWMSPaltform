package com.huamengtong.wms.inwh.mapper;

import com.huamengtong.wms.entity.inwh.TWmsAdjustmentDetailEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by mario on 2016/11/16.
 */
public interface AdjustmentDetailMapper {

    TWmsAdjustmentDetailEntity selectByPrimaryKey(@Param("id") Long id,@Param("splitTableKey") String splitTableKey);

    Integer insertAdjustmentDetail(@Param("entity") TWmsAdjustmentDetailEntity adjustmentDetailEntity,@Param("splitTableKey") String splitTableKey);

    Integer deleteAdjustmentDetail(@Param("id")Long id,@Param("splitTableKey") String splitTableKey);

    Integer updateAdjustmentDetail(@Param("entity") TWmsAdjustmentDetailEntity adjustmentDetailEntity,@Param("splitTableKey") String splitTableKey);

    List<TWmsAdjustmentDetailEntity> queryDetailPages(Map map);

    Integer queryDetailPageCount(Map map);

    List<TWmsAdjustmentDetailEntity> queryAdjustmentDetails (@Param("adjustId") Long adjustId,@Param("splitTableKey") String splitTableKey);

    Integer insertAdjustmentDetailByMove(@Param("entity") TWmsAdjustmentDetailEntity adjustmentDetailEntity,@Param("splitTableKey") String splitTableKey);
}
