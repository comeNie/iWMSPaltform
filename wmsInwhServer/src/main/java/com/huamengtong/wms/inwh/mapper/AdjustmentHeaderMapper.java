package com.huamengtong.wms.inwh.mapper;

import com.huamengtong.wms.entity.inwh.TWmsAdjustmentHeaderEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by mario on 2016/11/16.
 */
public interface AdjustmentHeaderMapper {

    List<TWmsAdjustmentHeaderEntity> queryAdjustmentHeaderPages(@Param("entity")TWmsAdjustmentHeaderEntity adjustmentHeaderEntity,@Param("splitTableKey") String splitTableKey);

    Integer queryAdjustmentHeaderPageCount(@Param("entity") TWmsAdjustmentHeaderEntity adjustmentHeaderEntity,@Param("splitTableKey") String splitTableKey);

    TWmsAdjustmentHeaderEntity selectByPrimaryKey(@Param("id") Long id,@Param("splitTableKey") String splitTableKey);

    Integer insertAdjustmentHeader(@Param("entity") TWmsAdjustmentHeaderEntity adjustmentHeaderEntity,@Param("splitTableKey") String splitTableKey);

    Integer deleteAdjustmentHeader(@Param("id") Long id,@Param("splitTableKey") String splitTableKey);

    Integer updateAdjustmentHeader(@Param("entity") TWmsAdjustmentHeaderEntity adjustmentHeaderEntity,@Param("splitTableKey") String splitTableKey);

}
