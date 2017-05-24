package com.huamengtong.wms.inwh.mapper;

import com.huamengtong.wms.entity.inwh.TWmsQcCheckEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by could.hao on 2017/3/21.
 */
public interface QcCheckMapper {

    List<TWmsQcCheckEntity> selectQcCheckPage(@Param("entity")TWmsQcCheckEntity qcCheckEntity,@Param("splitTableKey")String splitTableKey);

    List<TWmsQcCheckEntity> selectQcCheckDetailPage(Map map);

    Integer selectQcCheckPageCount(@Param("entity")TWmsQcCheckEntity qcCheckEntity,@Param("splitTableKey")String splitTableKey);

    Integer selectQcCheckDetailPageCount(@Param("headerId")Long headerId,@Param("splitTableKey")String splitTableKey);

    TWmsQcCheckEntity selectByPrimaryKey (@Param("id")Long id,@Param("splitTableKey")String splitTableKey);

    Integer deleteByPrimaryKey (@Param("id")Long id,@Param("splitTableKey")String splitTableKey);

    Integer insertSelective (@Param("entity")TWmsQcCheckEntity qcCheckEntity,@Param("splitTableKey")String splitTableKey);

    Integer updateByPrimaryKeySelective (@Param("entity")TWmsQcCheckEntity qcCheckEntity,@Param("splitTableKey")String splitTableKey);
}
