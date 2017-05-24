package com.huamengtong.wms.inwh.mapper;


import com.huamengtong.wms.entity.inwh.TWmsQcHeaderEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QcHeaderMapper {

    List<TWmsQcHeaderEntity> queryQcHeaderPages(@Param("entity") TWmsQcHeaderEntity qcHeaderEntity,@Param("splitTableKey") String splitTableKey);

    Integer queryQcHeadersPageCount(@Param("entity") TWmsQcHeaderEntity qcHeaderEntity,@Param("splitTableKey") String splitTableKey);

    TWmsQcHeaderEntity selectByPrimaryKey(@Param("id") Long id,@Param("splitTableKey") String splitTableKey);
    
    Integer deleteByPrimaryKey(@Param("id") Long id,@Param("splitTableKey") String splitTableKey);
    
    Integer insertQcHeader(@Param("entity") TWmsQcHeaderEntity qcHeaderEntity,@Param("splitTableKey") String splitTableKey);
    
    Integer updateQcHeader(@Param("entity") TWmsQcHeaderEntity qcHeaderEntity,@Param("splitTableKey") String splitTableKey);

    TWmsQcHeaderEntity selectByAsnId(@Param("asnId") Long asnId,@Param("splitTableKey") String splitTableKey);
}
