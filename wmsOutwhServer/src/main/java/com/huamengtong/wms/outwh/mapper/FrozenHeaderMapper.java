package com.huamengtong.wms.outwh.mapper;

import com.huamengtong.wms.entity.outwh.TWmsFrozenHeaderEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Edwin on 2016/11/10.
 */
public interface FrozenHeaderMapper {

    List<TWmsFrozenHeaderEntity> queryFrozenHeaderPages(@Param("entity") TWmsFrozenHeaderEntity frozenHeaderEntity,@Param("splitTableKey") String splitTableKey);

    Integer queryFrozenHeaderPageCount(@Param("entity") TWmsFrozenHeaderEntity frozenHeaderEntity,@Param("splitTableKey") String splitTableKey);

    TWmsFrozenHeaderEntity selectByPrimaryKey(@Param("id") Long id, @Param("splitTableKey") String splitTableKey);

    Integer insertFrozenHeader(@Param("entity") TWmsFrozenHeaderEntity frozenHeaderEntity,@Param("splitTableKey") String splitTableKey);

    Integer deleteFrozenHeader(@Param("id") Long id,@Param("splitTableKey") String splitTableKey);

    Integer updateFrozenHeader(@Param("entity") TWmsFrozenHeaderEntity frozenHeaderEntity,@Param("splitTableKey") String splitTableKey);

}
