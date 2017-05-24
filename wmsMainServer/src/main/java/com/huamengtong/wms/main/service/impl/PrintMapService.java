package com.huamengtong.wms.main.service.impl;


import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.TWmsPrintMapDTO;
import com.huamengtong.wms.entity.main.TWmsPrintMapEntity;
import com.huamengtong.wms.main.mapper.PrintMapMapper;
import com.huamengtong.wms.main.service.IPrintMapService;
import com.huamengtong.wms.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrintMapService implements IPrintMapService {

    @Autowired
    PrintMapMapper printMapMapper;

    @Override
    public MessageResult createPrintMap(TWmsPrintMapDTO tWmsPrintMapDTO) {
        TWmsPrintMapEntity tWmsPrintMapEntity = BeanUtils.copyBeanPropertyUtils(tWmsPrintMapDTO,TWmsPrintMapEntity.class);
        printMapMapper.insertPrintMap(tWmsPrintMapEntity);
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult updatePrintMap(TWmsPrintMapDTO tWmsPrintMapDTO) {
        TWmsPrintMapEntity tWmsPrintMapEntity = BeanUtils.copyBeanPropertyUtils(tWmsPrintMapDTO,TWmsPrintMapEntity.class);
        printMapMapper.updatePrintMap(tWmsPrintMapEntity);
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult removePrintMap(Integer id) {
        printMapMapper.deletePrintMap(id);
        return MessageResult.getSucMessage();
    }

    @Override
    public TWmsPrintMapEntity findById(Integer id) {
        TWmsPrintMapEntity tWmsPrintMapEntity = printMapMapper.selectById(id);
        return tWmsPrintMapEntity == null?null:tWmsPrintMapEntity;
    }

    @Override
    public List<TWmsPrintMapEntity> findPrintMap() {
        return printMapMapper.selectPrintMap();
    }

    @Override
    public PageResponse<List<TWmsPrintMapEntity>> queryPrintMapPages(TWmsPrintMapDTO tWmsPrintMapDTO) {
        TWmsPrintMapEntity tWmsPrintTempEntity = BeanUtils.copyBeanPropertyUtils(tWmsPrintMapDTO,TWmsPrintMapEntity.class);
        List<TWmsPrintMapEntity> list = printMapMapper.queryPrintMapPages(tWmsPrintTempEntity);
        Integer totalSize = printMapMapper.queryPrintMapPageCount(tWmsPrintTempEntity);
        PageResponse<List<TWmsPrintMapEntity>> pageResponse = new PageResponse();
        pageResponse.setRows(list);
        pageResponse.setTotal(totalSize);
        return pageResponse;
    }


}
