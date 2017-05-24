package com.huamengtong.wms.inwh.mapper;

import com.huamengtong.wms.entity.inwh.TWmsProPackageDetailEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by mario on 2017/2/20.
 */
public interface ProPackageDetailMapper {
    TWmsProPackageDetailEntity selectByprimarkey(@Param("id")Long id,@Param("splitTableKey") String splitTableKey);

    Integer insertProPackageDetail(@Param("entity")TWmsProPackageDetailEntity proPackageDetailEntity,@Param("splitTableKey") String splitTableKey);

    Integer deleteProPackageDetail(@Param("id")Long id,@Param("splitTableKey") String splitTableKey);

    Integer updateProPackageDetail(@Param("entity") TWmsProPackageDetailEntity proPackageDetailEntity,@Param("splitTableKey") String splitTableKey);

    List<TWmsProPackageDetailEntity> queryDetailPages(Map map);

    Integer queryDetailPageCount(Map map);

    List<TWmsProPackageDetailEntity> queryProPackageDetails(@Param("parentId") Long parentId,@Param("splitTableKey") String splitTableKey);


}
