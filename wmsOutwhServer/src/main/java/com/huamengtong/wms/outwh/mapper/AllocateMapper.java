package com.huamengtong.wms.outwh.mapper;

import com.huamengtong.wms.entity.outwh.TWmsAllocateEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AllocateMapper {

    List<TWmsAllocateEntity> selectAllocates(Map paramMap);

    Integer insertAllocate(@Param("entity")TWmsAllocateEntity allocateEntity,@Param("splitTableKey") String splitTableKey);

    Integer deleteAllocate(@Param("detailId")Long detailId,@Param("splitTableKey") String splitTableKey);

    List<TWmsAllocateEntity> selectAllocatesByShipmentDetailId(@Param("detailId")Long detailId,@Param("splitTableKey") String splitTableKey);

    Integer updateAllocate(@Param("entity")TWmsAllocateEntity allocateEntity,@Param("splitTableKey") String splitTableKey);

}

