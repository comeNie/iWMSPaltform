package com.huamengtong.wms.outwh.mapper;

import com.huamengtong.wms.entity.outwh.TWmsUnfrozenHeaderEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface UnfrozenHeaderMapper {

    List<TWmsUnfrozenHeaderEntity> queryUnfrozenHeaderPages(@Param("entity") TWmsUnfrozenHeaderEntity unfrozenHeaderEntity,@Param("splitTableKey") String splitTableKey);

    Integer queryUnfrozenHeaderPageCount(@Param("entity") TWmsUnfrozenHeaderEntity unfrozenHeaderEntity,@Param("splitTableKey") String splitTableKey);

    TWmsUnfrozenHeaderEntity selectByPrimaryKey(@Param("id") Long id,@Param("splitTableKey") String splitTableKey);

    Integer insertUnfrozenHeader(@Param("entity") TWmsUnfrozenHeaderEntity unfrozenHeaderEntity,@Param("splitTableKey") String splitTableKey);

    Integer deleteUnfrozenHeader(@Param("id") Long id,@Param("splitTableKey") String splitTableKey);

    Integer updateUnfrozenHeader(@Param("entity") TWmsUnfrozenHeaderEntity unfrozenHeaderEntity,@Param("splitTableKey") String splitTableKey);

    TWmsUnfrozenHeaderEntity selectByFrozenId(@Param("frozenId") Long frozenId,@Param("splitTableKey") String splitTableKey);

}
