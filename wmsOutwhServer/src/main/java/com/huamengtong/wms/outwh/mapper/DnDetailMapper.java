package com.huamengtong.wms.outwh.mapper;

import com.huamengtong.wms.entity.outwh.TWmsDnDetailEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by mario on 2016/11/3.
 */
public interface DnDetailMapper {


    TWmsDnDetailEntity selectByPrimaryKey (@Param("id") Long id, @Param("splitTableKey") String splitTableKey);

    Integer insertDnDetail(@Param("entity") TWmsDnDetailEntity dnDetailEntity, @Param("splitTableKey") String splitTableKey);

    Integer deleteByPrimaryKey(@Param("id") Long id,@Param("splitTableKey") String splitTableKey);

    Integer UpdateDnDetail(@Param("entity") TWmsDnDetailEntity dnDetailEntity, @Param("splitTableKey") String splitTableKey);

    List<TWmsDnDetailEntity> queryDetailsPages(Map map);

    Integer queryDetailPageCount(Map map);

    List<TWmsDnDetailEntity> queryDnDetails(@Param("headerId") Long HeaderId,@Param("splitTableKey") String splitTableKey);

    Integer deleteDnDetails(@Param("dnId") Long receiptId,@Param("splitTableKey") String splitTableKey);

}
