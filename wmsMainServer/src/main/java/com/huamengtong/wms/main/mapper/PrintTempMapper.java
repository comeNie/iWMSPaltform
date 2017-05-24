package com.huamengtong.wms.main.mapper;


import com.huamengtong.wms.entity.main.TWmsPrintTempEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PrintTempMapper {

    List<TWmsPrintTempEntity> queryPrintTempPages(TWmsPrintTempEntity tWmsPrintMapEntity);

    Integer queryPrintTempPageCount(TWmsPrintTempEntity tWmsPrintMapEntity);

    TWmsPrintTempEntity selectById(@Param("id")Integer id);

    Integer insertPrintTemp(TWmsPrintTempEntity tWmsPrintMapEntity);

    Integer updatePrintTemp(TWmsPrintTempEntity tWmsPrintMapEntity);

    Integer deletePrintTemp(@Param("id")Integer id);

    List<TWmsPrintTempEntity> queryPrintTempLists();

}
