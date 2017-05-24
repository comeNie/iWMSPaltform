package com.huamengtong.wms.main.service;

import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.TWmsPrintTempDTO;
import com.huamengtong.wms.entity.main.TWmsPrintTempEntity;

import java.util.List;

public interface IPrintTempService {

    PageResponse<List<TWmsPrintTempEntity>> queryPrintTempPages(TWmsPrintTempDTO tWmsPrintTempDTO);

    MessageResult removePrintTemp(Integer id);

    MessageResult createPrintTemp(TWmsPrintTempDTO tWmsPrintTempDTO);

    MessageResult updatePrintTemp(TWmsPrintTempDTO tWmsPrintTempDTO);

    TWmsPrintTempEntity findById(Integer id);

    List<TWmsPrintTempEntity> queryPrintTempLists();
}
