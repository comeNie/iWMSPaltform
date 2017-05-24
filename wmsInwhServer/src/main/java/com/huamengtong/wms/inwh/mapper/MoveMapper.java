package com.huamengtong.wms.inwh.mapper;

import com.huamengtong.wms.entity.inwh.TWmsMoveEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by mario on 2016/11/18.
 */
public interface MoveMapper {

    List<TWmsMoveEntity> queryMovePages(@Param("entity") TWmsMoveEntity moveEntity,@Param("splitTableKey") String splitTableKey);

    Integer queryMovePageCount(@Param("entity") TWmsMoveEntity moveEntity,@Param("splitTableKey") String splitTableKey);

    TWmsMoveEntity selectByPrimaryKey(@Param("id") Long id,@Param("splitTableKey") String splitTableKey);

    Integer insertMove(@Param("entity") TWmsMoveEntity moveEntity,@Param("splitTableKey") String splitTableKey);

    Integer deleteMove(@Param("id") Long id,@Param("splitTableKey") String splitTableKey);

    Integer updateMove(@Param("entity") TWmsMoveEntity moveEntity,@Param("splitTableKey") String splitTableKey);

}
