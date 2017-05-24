package com.huamengtong.wms.outwh.service.impl;

import com.huamengtong.wms.client.IAutoIdClient;
import com.huamengtong.wms.constants.AutoIdConstants;
import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.service.BaseService;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.outwh.TWmsDnInvoiceDTO;
import com.huamengtong.wms.entity.outwh.TWmsDnInvoiceEntity;
import com.huamengtong.wms.outwh.mapper.DnInvoiceMapper;
import com.huamengtong.wms.outwh.service.IDnInvoiceService;
import com.huamengtong.wms.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by mario on 2016/11/10.
 */
@Service
public class DnInvoiceService extends BaseService implements IDnInvoiceService {

    @Autowired
    DnInvoiceMapper dnInvoiceMapper;

    @Autowired
    IAutoIdClient autoIdClient;


    @Override
    public PageResponse<List<TWmsDnInvoiceEntity>> getDnInvoice(TWmsDnInvoiceDTO DninvoiceDTO, DbShardVO dbShardVO) {

        TWmsDnInvoiceEntity dninvoiceEntity = BeanUtils.copyBeanPropertyUtils(DninvoiceDTO,TWmsDnInvoiceEntity.class);
        List<TWmsDnInvoiceEntity> dnInvoiceEntityList = dnInvoiceMapper.queryDnInvoicePages(dninvoiceEntity,getSplitTableKey(dbShardVO));
        Integer totalSize = dnInvoiceMapper.queryDnInvoicePageCount(dninvoiceEntity,getSplitTableKey(dbShardVO));
        PageResponse<List<TWmsDnInvoiceEntity>> response = new PageResponse();
        response.setRows(dnInvoiceEntityList);
        response.setTotal(totalSize);
        return response;
    }

    @Override
    public TWmsDnInvoiceEntity findByPrimaryKey(Long id, DbShardVO dbShardVO) {
        return dnInvoiceMapper.selectByPrimaryKey(id,getSplitTableKey(dbShardVO));
    }

    @Override
    public MessageResult createDnInvoice(TWmsDnInvoiceDTO DninvoiceDTO, DbShardVO dbShardVO) {
          TWmsDnInvoiceEntity dnInvoiceEntity = BeanUtils.copyBeanPropertyUtils(DninvoiceDTO,TWmsDnInvoiceEntity.class);
          dnInvoiceEntity.setId(autoIdClient.getAutoId(AutoIdConstants.TWmsDnInvoiceEntity));
          dnInvoiceMapper.insertDnInvoice(dnInvoiceEntity,getSplitTableKey(dbShardVO));
          return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult removeDnInvoice(Long id, DbShardVO dbShardVO) {
         dnInvoiceMapper.deleteByPrimaryKey(id,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult modifyDnInvoice(TWmsDnInvoiceDTO DninvoiceDTO, DbShardVO dbShardVO) {
        TWmsDnInvoiceEntity dnInvoiceEntity = BeanUtils.copyBeanPropertyUtils(DninvoiceDTO,TWmsDnInvoiceEntity.class);
        dnInvoiceMapper.updateDnInvoice(dnInvoiceEntity,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }

    @Override
    public Long saveOrUpdateDnInvoice(TWmsDnInvoiceDTO invoice, DbShardVO dbShardVO) {
        Long orderId = 0L;
        TWmsDnInvoiceEntity  dnInvoiceEntity = BeanUtils.copyBeanPropertyUtils(invoice, TWmsDnInvoiceEntity.class);
        if(invoice.getId() != null ) {
            orderId = invoice.getId();
            dnInvoiceMapper.updateDnInvoice(dnInvoiceEntity, getSplitTableKey(dbShardVO));
        }else{
            orderId = autoIdClient.getAutoId(AutoIdConstants.TWmsSaleOrderEntity);
            dnInvoiceEntity.setId(orderId);
            dnInvoiceMapper.insertDnInvoice(dnInvoiceEntity, getSplitTableKey(dbShardVO));
        }
        return orderId;
    }

    @Override
    public List<TWmsDnInvoiceEntity> findByPrimaryKey(List<Long> ids, DbShardVO dbShardVO) {
        return dnInvoiceMapper.selectByPrimaryKeyList(ids,getSplitTableKey(dbShardVO));
    }
}
