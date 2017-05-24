package com.huamengtong.wms.inwh.mapper;



import com.huamengtong.wms.entity.inwh.TWmsQcDetailEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface QcDetailMapper {

    TWmsQcDetailEntity selectByPrimaryKey(@Param("id") Long id,@Param("splitTableKey") String splitTableKey);

    Integer deleteByPrimaryKey(@Param("id") Long id,@Param("splitTableKey") String splitTableKey);

    Integer insertQcDetail(@Param("entity") TWmsQcDetailEntity qcDetailEntity,@Param("splitTableKey") String splitTableKey);
    
    Integer updateQcDetail(@Param("entity") TWmsQcDetailEntity qcDetailEntity,@Param("splitTableKey") String splitTableKey);
    
    List<TWmsQcDetailEntity> queryDetailsPagesByHeaderId(Map map);
    
    Integer queryDetailsPageCountByHeader(Map map);

    List<TWmsQcDetailEntity> selectQcDetailsByHeaderId(@Param("headerId") Long headerId,@Param("splitTableKey") String splitTableKey);

    Integer deleteQcDetailsByHeaderId(@Param("headerId") Long headerId,@Param("splitTableKey") String splitTableKey);

    TWmsQcDetailEntity selectByHeaderIdAndSku(Map map);
}
