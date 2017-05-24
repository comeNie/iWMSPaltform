package com.huamengtong.wms.outwh.mapper;

import com.huamengtong.wms.entity.outwh.TWmsUnfrozenDetailEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Edwin on 2016/11/10.
 */
public interface UnfrozenDetailMapper {

    TWmsUnfrozenDetailEntity selectByPrimaryKey(@Param("id") Long id,@Param("splitTableKey") String splitTableKey);

    Integer insertUnfrozenDetail (@Param("entity") TWmsUnfrozenDetailEntity unfrozenDetailEntity,@Param("splitTableKey") String splitTableKey);

    Integer deleteUnfrozenDetail(@Param("id")Long id,@Param("splitTableKey") String splitTableKey);

    Integer updateUnfrozenDetail(@Param("entity") TWmsUnfrozenDetailEntity unfrozenDetailEntity,@Param("splitTableKey") String splitTableKey);

    List<TWmsUnfrozenDetailEntity> queryDetailPages(Map map);

    Integer queryDetailPageCount(Map map);

    List<TWmsUnfrozenDetailEntity> queryUnfrozenDetails(@Param("unFrozenId") Long unFrozenId,@Param("splitTableKey") String splitTableKey);

}
