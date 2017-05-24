package com.huamengtong.wms.outwh.mapper;

import com.huamengtong.wms.entity.outwh.TWmsDnHeaderEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * Created by mario on 2016/11/3.
 */
public interface DnHeaderMapper {

    List<TWmsDnHeaderEntity> queryDnHeaderPages (@Param("entity") TWmsDnHeaderEntity dnHeaderEntity, @Param("splitTableKey") String splitTableKey);

    Integer queryDnHeaderPageCount(@Param("entity") TWmsDnHeaderEntity dnHeaderEntity, @Param("splitTableKey") String splitTableKey);

    TWmsDnHeaderEntity selectByPrimaryKey (@Param("id") Long id, @Param("splitTableKey") String splitTableKey);

    Integer insertDnHeader(@Param("entity") TWmsDnHeaderEntity dnHeaderEntity, @Param("splitTableKey") String splitTableKey);

    Integer deleteByPrimaryKey(@Param("id") Long id,@Param("splitTableKey") String splitTableKey);

    Integer UpdateDnHeader(@Param("entity") TWmsDnHeaderEntity dnHeaderEntity, @Param("splitTableKey") String splitTableKey);

}
