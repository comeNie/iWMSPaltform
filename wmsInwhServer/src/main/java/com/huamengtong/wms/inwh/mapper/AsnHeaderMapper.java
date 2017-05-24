package com.huamengtong.wms.inwh.mapper;


import com.huamengtong.wms.entity.inwh.TWmsAsnHeaderEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AsnHeaderMapper {

    List<TWmsAsnHeaderEntity> queryAsnHeaderPages(@Param("entity") TWmsAsnHeaderEntity asnHeader, @Param("splitTableKey") String splitTableKey);

    Integer queryAsnHeaderPageCount(@Param("entity") TWmsAsnHeaderEntity asnHeader, @Param("splitTableKey") String splitTableKey);

    TWmsAsnHeaderEntity selectByPrimaryKey(@Param("id") Long id, @Param("splitTableKey") String splitTableKey);

    Integer deleteByPrimaryKey(@Param("id") Long id,@Param("splitTableKey") String splitTableKey);

    Integer insertAsnHeader(@Param("entity") TWmsAsnHeaderEntity asnHeader, @Param("splitTableKey") String splitTableKey);

    Integer updateAsnHeader(@Param("entity") TWmsAsnHeaderEntity asnHeader, @Param("splitTableKey") String splitTableKey);


}
