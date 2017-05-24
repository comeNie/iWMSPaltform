package com.huamengtong.wms.inwh.mapper;

import com.huamengtong.wms.entity.inwh.TWmsProInvPackageDetailEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by mario on 2017/3/28.
 */
public interface ProInvPackageDetailMapper {
    TWmsProInvPackageDetailEntity selectByPrimaryKey(@Param("id") Long id, @Param("splitTableKey") String splitTableKey);

    Integer insertProInvPackageDetail(@Param("entity") TWmsProInvPackageDetailEntity proInvPackageDetailEntity,@Param("splitTableKey") String splitTableKey);

    Integer updateProInvPackageDetail(@Param("entity") TWmsProInvPackageDetailEntity proInvPackageDetailEntity,@Param("splitTableKey") String splitTableKey);

    List<TWmsProInvPackageDetailEntity> queryDetailPages(Map map);

    Integer queryDetailPageCount(Map map);

    List<TWmsProInvPackageDetailEntity> queryProInvPackageDetails (@Param("parentId") Long parentId,@Param("splitTableKey") String splitTableKey);

}
