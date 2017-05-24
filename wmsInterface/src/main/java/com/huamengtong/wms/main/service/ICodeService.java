package com.huamengtong.wms.main.service;


import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.TWmsCodeDetailDTO;
import com.huamengtong.wms.dto.TWmsCodeHeaderDTO;
import com.huamengtong.wms.entity.main.TWmsCodeDetailEntity;
import com.huamengtong.wms.entity.main.TWmsCodeHeaderEntity;

import java.util.List;
import java.util.Map;

public interface ICodeService {

     PageResponse<List<TWmsCodeHeaderEntity>> getCodeHeaderLists(TWmsCodeHeaderDTO codeHeaderDTO);

     PageResponse<List<TWmsCodeDetailEntity>>  getCodeDetailLists(Map map);

     MessageResult createCodeHeader(TWmsCodeHeaderDTO codeHeaderDTO);

     MessageResult modifyCodeHeader(TWmsCodeHeaderDTO codeHeaderDTO);

     MessageResult removeCodeHeader(Long id);

     MessageResult removeCodeDetail(Long detailId);

     MessageResult createCodeDetail(TWmsCodeDetailDTO codeDetailDTO);

     MessageResult modifyCodeDetail(TWmsCodeDetailDTO codeDetailDTO);

     Map getAllCodeDatas(Map searchMap);

     MessageResult flushAllRedis();

     List getDetailByHeaderName(String headerName);

     String format(String en, String listName);

    List<Map<String,String>> getCodeList(List<String> listNames);
}
