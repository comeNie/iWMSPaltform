package com.huamengtong.wms.outwh.mapper;

import com.huamengtong.wms.entity.outwh.TWmsWaveEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Edwin on 2016/11/10.
 */
public interface WaveMapper {
    List<TWmsWaveEntity> queryWavePages(@Param("entity") TWmsWaveEntity waveEntity, @Param("splitTableKey") String splitTableKey);

    Integer queryWavePageCount(@Param("entity") TWmsWaveEntity waveEntity,@Param("splitTableKey") String splitTableKey);

    TWmsWaveEntity selectByPrimaryKey(@Param("id") Long id,@Param("splitTableKey") String splitTableKey);

    Integer insert(@Param("entity") TWmsWaveEntity waveEntity,@Param("splitTableKey") String splitTableKey);

    Integer deleteByPrimaryKey(@Param("id") Long id,@Param("splitTableKey") String splitTableKey);

    Integer update(@Param("entity") TWmsWaveEntity waveEntity,@Param("splitTableKey") String splitTableKey);

}
