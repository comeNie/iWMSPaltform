package com.huamengtong.wms.main.mapper;

import com.huamengtong.wms.entity.main.TWmsPrintMapEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PrintMapMapper {

    List<TWmsPrintMapEntity> queryPrintMapPages(TWmsPrintMapEntity tWmsPrintMapEntity);

    Integer queryPrintMapPageCount(TWmsPrintMapEntity tWmsPrintMapEntity);

    TWmsPrintMapEntity selectById(@Param("id")Integer id);

    Integer insertPrintMap(TWmsPrintMapEntity tWmsPrintMapEntity);

    Integer updatePrintMap(TWmsPrintMapEntity tWmsPrintMapEntity);

    Integer deletePrintMap(@Param("id") Integer id);

    List<TWmsPrintMapEntity> selectPrintMap();
}
