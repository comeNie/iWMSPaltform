package com.huamengtong.wms.outwh.service.impl;

import com.huamengtong.wms.client.IAutoIdClient;
import com.huamengtong.wms.constants.AutoIdConstants;
import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.service.BaseService;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.outwh.TWmsSaleOrderDTO;
import com.huamengtong.wms.entity.outwh.TWmsSaleOrderEntity;
import com.huamengtong.wms.outwh.mapper.SaleOrderMapper;
import com.huamengtong.wms.outwh.service.ISaleOrderService;
import com.huamengtong.wms.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by mario on 2016/10/31.
 */
@Service
public class SaleOrderService extends BaseService implements ISaleOrderService {

    @Autowired
    SaleOrderMapper saleOrderMapper;

    @Autowired
    IAutoIdClient autoIdClient;

    @Override
    public PageResponse<List<TWmsSaleOrderEntity>> getSaleOrder(TWmsSaleOrderDTO saleOrderDTO, DbShardVO dbShardVO) {
        TWmsSaleOrderEntity SaleOrderEntity= BeanUtils.copyBeanPropertyUtils(saleOrderDTO,TWmsSaleOrderEntity.class);
        List<TWmsSaleOrderEntity> SaleOrderEntityList = saleOrderMapper.querySaleOrderPages(SaleOrderEntity,getSplitTableKey(dbShardVO));
        Integer TotalSize = saleOrderMapper.querySaleOrderPageCount(SaleOrderEntity,getSplitTableKey(dbShardVO));
        PageResponse<List<TWmsSaleOrderEntity>> response=new PageResponse();
        response.setTotal(TotalSize);
        response.setRows(SaleOrderEntityList);
        return response;
    }

    @Override
    public TWmsSaleOrderEntity findByPrimaryKey(Long id, DbShardVO dbShardVO) {

        return saleOrderMapper.selectByPrimaryKey(id, getSplitTableKey(dbShardVO));
    }

    @Override
    public MessageResult createSaleOrder(TWmsSaleOrderDTO saleOrderDTO, DbShardVO dbShardVO) {
        TWmsSaleOrderEntity SaleOrderEntity= BeanUtils.copyBeanPropertyUtils(saleOrderDTO,TWmsSaleOrderEntity.class);
        SaleOrderEntity.setId(autoIdClient.getAutoId(AutoIdConstants.TWmsSaleOrderEntity));
        saleOrderMapper.insertSaleOrder(SaleOrderEntity,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult removeSaleOrder(Long id, DbShardVO dbShardVO) {
        saleOrderMapper.deleteByPrimaryKey(id,getSplitTableKey(dbShardVO));
        return  MessageResult.getSucMessage();
    }

    @Override
    public MessageResult modifySaleOrder(TWmsSaleOrderDTO saleOrderDTO, DbShardVO dbShardVO) {
        TWmsSaleOrderEntity SaleOrderEntity= BeanUtils.copyBeanPropertyUtils(saleOrderDTO,TWmsSaleOrderEntity.class);
        saleOrderMapper.updateSaleOrder(SaleOrderEntity,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }

    @Override
    public Long saveOrUpdateSaleOrder(TWmsSaleOrderDTO order, DbShardVO dbShardVO) {
        Long orderId = 0L;
        TWmsSaleOrderEntity SaleOrderEntity = BeanUtils.copyBeanPropertyUtils(order, TWmsSaleOrderEntity.class);
        if(order.getId() != null ) {
            orderId = order.getId();
            saleOrderMapper.updateSaleOrder(SaleOrderEntity, getSplitTableKey(dbShardVO));
        }else{
            orderId = autoIdClient.getAutoId(AutoIdConstants.TWmsSaleOrderEntity);
            SaleOrderEntity.setId(orderId);
            saleOrderMapper.insertSaleOrder(SaleOrderEntity, getSplitTableKey(dbShardVO));
        }
        return orderId;
    }

    @Override
    public List<TWmsSaleOrderEntity> findByPrimaryKey(List<Long> ids, DbShardVO dbShardVO) {

        return saleOrderMapper.selectByPrimaryKeyList(ids, getSplitTableKey(dbShardVO));
    }
}
