package com.huamengtong.wms.inwh.mapper;

import com.huamengtong.wms.entity.inwh.TWmsProInvPackageEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by mario on 2017/3/28.
 */
public interface ProInvPackageMapper {

    List<TWmsProInvPackageEntity> queryProInvPackagePages(@Param("entity")TWmsProInvPackageEntity proInvPackageEntity, @Param("splitTableKey")String splitTableKey);

    Integer queryProInvPackagePageCount(@Param("entity")TWmsProInvPackageEntity proInvPackageEntity,@Param("splitTableKey")String splitTableKey);

    TWmsProInvPackageEntity selectByPrimarykey(@Param("id")Long id,@Param("splitTableKey")String splitTableKey);

    Integer insertProInvPackage(@Param("entity") TWmsProInvPackageEntity proInvPackageEntity,@Param("splitTableKey")String splitTableKey);

    Integer updateProInvPackage(@Param("entity")TWmsProInvPackageEntity proInvPackageEntity,@Param("splitTableKey")String splitTableKey);

    Integer updateInvalidProInvPackage(@Param("id")Long id,@Param("statusCode")String statusCode,@Param("splitTableKey")String splitTableKey);

}
