package com.huamengtong.wms.main.service.impl;

import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.TWmsPrintTempDTO;
import com.huamengtong.wms.entity.main.TWmsPrintTempEntity;
import com.huamengtong.wms.main.mapper.PrintTempMapper;
import com.huamengtong.wms.main.service.IPrintTempService;
import com.huamengtong.wms.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrintTempService implements IPrintTempService{

    @Autowired
    PrintTempMapper printTempMapper;

    @Override
    public MessageResult removePrintTemp(Integer id) {
        printTempMapper.deletePrintTemp(id);
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult createPrintTemp(TWmsPrintTempDTO tWmsPrintTempDTO) {
        TWmsPrintTempEntity tWmsPrintTempEntity = BeanUtils.copyBeanPropertyUtils(tWmsPrintTempDTO,TWmsPrintTempEntity.class);
        printTempMapper.insertPrintTemp(tWmsPrintTempEntity);
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult updatePrintTemp(TWmsPrintTempDTO tWmsPrintTempDTO) {
        TWmsPrintTempEntity tWmsPrintTempEntity = BeanUtils.copyBeanPropertyUtils(tWmsPrintTempDTO,TWmsPrintTempEntity.class);
        printTempMapper.updatePrintTemp(tWmsPrintTempEntity);
        return MessageResult.getSucMessage();
    }

    @Override
    public TWmsPrintTempEntity findById(Integer id) {
        return printTempMapper.selectById(id);
    }

    @Override
    public List<TWmsPrintTempEntity> queryPrintTempLists() {
        return printTempMapper.queryPrintTempLists();
    }

    @Override
    public PageResponse<List<TWmsPrintTempEntity>> queryPrintTempPages(TWmsPrintTempDTO tWmsPrintTempDTO) {
        TWmsPrintTempEntity tWmsPrintTempEntity = BeanUtils.copyBeanPropertyUtils(tWmsPrintTempDTO,TWmsPrintTempEntity.class);
        List<TWmsPrintTempEntity> list = printTempMapper.queryPrintTempPages(tWmsPrintTempEntity);
        Integer totalSize = printTempMapper.queryPrintTempPageCount(tWmsPrintTempEntity);
        PageResponse<List<TWmsPrintTempEntity>> response = new PageResponse();
        response.setRows(list);
        response.setTotal(totalSize);
        return response;
    }
}
