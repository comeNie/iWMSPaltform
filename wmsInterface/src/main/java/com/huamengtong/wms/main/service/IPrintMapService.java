package com.huamengtong.wms.main.service;

import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.TWmsPrintMapDTO;
import com.huamengtong.wms.entity.main.TWmsPrintMapEntity;

import java.util.List;

public interface IPrintMapService {

    PageResponse<List<TWmsPrintMapEntity>> queryPrintMapPages(TWmsPrintMapDTO tWmsPrintMapDTO);

    MessageResult createPrintMap(TWmsPrintMapDTO tWmsPrintMapDTO);

    MessageResult updatePrintMap(TWmsPrintMapDTO tWmsPrintMapDTO);

    MessageResult removePrintMap(Integer id);

    TWmsPrintMapEntity findById(Integer id);

    List<TWmsPrintMapEntity> findPrintMap();
}
