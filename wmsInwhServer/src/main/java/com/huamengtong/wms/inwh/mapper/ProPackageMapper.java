package com.huamengtong.wms.inwh.mapper;

import com.huamengtong.wms.entity.inwh.TWmsProPackageEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by mario on 2017/2/20.
 */
public interface ProPackageMapper {
    List<TWmsProPackageEntity> queryProPackagePages(@Param("entity")TWmsProPackageEntity proPackageEntity,@Param("splitTableKey") String splitTableKey);

    Integer queryProPackagePageCount(@Param("entity")TWmsProPackageEntity proPackageEntity,@Param("splitTableKey") String splitTableKey);

    TWmsProPackageEntity selectByPrimarkey(@Param("id")Long id,@Param("splitTableKey")String splitTableKey);

    Integer insertProPackage(@Param("entity")TWmsProPackageEntity proPackageEntity,@Param("splitTableKey")String splitTableKey);

    Integer deleteProPackage(@Param("id")Long id,@Param("splitTableKey")String splitTableKey);

    Integer updateProPackage(@Param("entity")TWmsProPackageEntity proPackageEntity,@Param("splitTableKey")String splitTableKey);
}
