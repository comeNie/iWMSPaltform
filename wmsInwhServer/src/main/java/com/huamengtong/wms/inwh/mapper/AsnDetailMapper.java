package com.huamengtong.wms.inwh.mapper;


import com.huamengtong.wms.entity.inwh.TWmsAsnDetailEntity;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;


public interface AsnDetailMapper {

    List<TWmsAsnDetailEntity> queryAsnDetailPages(Map map, @Param("splitTableKey") String splitTableKey);

    Integer queryAsnDetailPageCount(Map map, @Param("splitTableKey") String splitTableKey);

    TWmsAsnDetailEntity selectByPrimaryKey(@Param("id") Long id, @Param("splitTableKey") String splitTableKey);

    Integer deleteByPrimaryKey(@Param("id") Long id, @Param("splitTableKey") String splitTableKey);

    Integer insertAsnDetail(@Param("entity") TWmsAsnDetailEntity asnDetailEntity, @Param("splitTableKey") String splitTableKey);

    Integer updateAsnDetail(@Param("entity") TWmsAsnDetailEntity asnDetailEntity, @Param("splitTableKey") String splitTableKey);

    List<TWmsAsnDetailEntity> queryDetailsPages(Map map);

    Integer queryDetailPageCount(Map map);

    List<TWmsAsnDetailEntity> selectAsnDetailsByHeaderId(Map map);

    List<TWmsAsnDetailEntity> selectByHeaderIdAndSku(Map map);

    Integer deleteAsnDetailsByHeaderId(@Param("headerId") Long headerId, @Param("splitTableKey") String splitTableKey);

    TWmsAsnDetailEntity selectByHeaderIdAndSkuId(@Param("headerId") Long headerId,@Param("skuId") Long skuId, @Param("splitTableKey") String splitTableKey);

}
