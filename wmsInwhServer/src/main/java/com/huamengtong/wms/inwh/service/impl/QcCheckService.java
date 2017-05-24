package com.huamengtong.wms.inwh.service.impl;

import com.huamengtong.wms.client.IAutoIdClient;
import com.huamengtong.wms.constants.AutoIdConstants;
import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.service.BaseService;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.inwh.TWmsQcCheckDTO;
import com.huamengtong.wms.dto.inwh.TWmsReceiptHeaderDTO;
import com.huamengtong.wms.em.QualityStatus;
import com.huamengtong.wms.entity.inwh.TWmsQcCheckEntity;
import com.huamengtong.wms.entity.inwh.TWmsReceiptHeaderEntity;
import com.huamengtong.wms.entity.sku.TWmsSkuEntity;
import com.huamengtong.wms.inwh.mapper.QcCheckMapper;
import com.huamengtong.wms.inwh.service.IQcCheckService;
import com.huamengtong.wms.inwh.service.IReceiptHeaderService;
import com.huamengtong.wms.sku.service.ISkuService;
import com.huamengtong.wms.utils.BeanUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by could.hao on 2017/3/21.
 */
@Service
public class QcCheckService extends BaseService implements IQcCheckService {
    @Autowired
    QcCheckMapper qcCheckMapper;

    @Autowired
    IAutoIdClient autoIdClient;

    @Autowired
    ISkuService skuService;

    @Autowired
    IReceiptHeaderService receiptHeaderService;

    @Override
    public TWmsQcCheckEntity selectByPrimaryKey(Long id, DbShardVO dbShardVO) {
        return qcCheckMapper.selectByPrimaryKey(id,getSplitTableKey(dbShardVO));
    }

    /**
     * 查询头表信息
     * @param qcCheckDTO
     * @param dbShardVO
     * @return
     */
    @Override
    public PageResponse<List<TWmsQcCheckEntity>> selectQcCheckPage(TWmsQcCheckDTO qcCheckDTO, DbShardVO dbShardVO) {
        TWmsQcCheckEntity qcCheckEntity = BeanUtils.copyBeanPropertyUtils(qcCheckDTO,TWmsQcCheckEntity.class);
        List<TWmsQcCheckEntity> list = qcCheckMapper.selectQcCheckPage(qcCheckEntity,getSplitTableKey(dbShardVO));
        Integer total = qcCheckMapper.selectQcCheckPageCount(qcCheckEntity,getSplitTableKey(dbShardVO));
        PageResponse<List<TWmsQcCheckEntity>> page = new PageResponse<>();
        page.setRows(list);
        page.setTotal(total);
        return page;
    }

    //根据头表查明细
    @Override
    public PageResponse<List<TWmsQcCheckEntity>> selectQcCheckPagedetail(Map map, DbShardVO dbShardVO) {
        map.put("splitTableKey",getSplitTableKey(dbShardVO));
        List<TWmsQcCheckEntity> list = qcCheckMapper.selectQcCheckDetailPage(map);
        Integer total = qcCheckMapper.selectQcCheckDetailPageCount(MapUtils.getLong(map,"headerId"),getSplitTableKey(dbShardVO));
        list.stream().forEach(x->{
            TWmsSkuEntity skuEntity = skuService.findByPrimaryKey(x.getSkuId(),getSkuDbShareVO(dbShardVO));
            if(skuEntity != null){
                x.setSkuName(skuEntity.getItemName());
                x.setSku(skuEntity.getSku());
                x.setSkuBarcode(skuEntity.getBarcode());
            }
        });
        PageResponse<List<TWmsQcCheckEntity>> page = new PageResponse<>();
        page.setRows(list);
        page.setTotal(total);
        return page;
    }

    /**
     * 头表添加
     * @param qcCheckDTO
     * @param dbShardVO
     * @return
     */
    @Override
    public MessageResult addQcCheck(TWmsQcCheckDTO qcCheckDTO, DbShardVO dbShardVO) {
        TWmsQcCheckEntity qcCheckEntity = BeanUtils.copyBeanPropertyUtils(qcCheckDTO,TWmsQcCheckEntity.class);
        qcCheckEntity.setId(autoIdClient.getAutoId(AutoIdConstants.TWmsQcCheckEntity));
        qcCheckMapper.insertSelective(qcCheckEntity,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }

    /**
     * 头表修改
     * @param qcCheckDTO
     * @param dbShardVO
     * @return
     */
    @Override
    public MessageResult updateQcCheck(TWmsQcCheckDTO qcCheckDTO, DbShardVO dbShardVO) {
        TWmsQcCheckEntity qcCheckEntity = BeanUtils.copyBeanPropertyUtils(qcCheckDTO,TWmsQcCheckEntity.class);
        qcCheckMapper.updateByPrimaryKeySelective(qcCheckEntity,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }

    /**
     * 头表删除
     * @param id
     * @param dbShardVO
     * @return
     */
    @Override
    public MessageResult deleteQcCheck(Long id, DbShardVO dbShardVO) {
        qcCheckMapper.deleteByPrimaryKey(id,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult updateQcCheckBatch(Long headerId, Map<Long,Long> map, String typeCode,Long tenantId,Long warehouseId,String operationUser,DbShardVO dbShardVO) {
        TWmsQcCheckEntity qcCheckHeaderEntity = new TWmsQcCheckEntity();
        qcCheckHeaderEntity.setQcId(headerId);
        qcCheckHeaderEntity.setQcDetailId(headerId);//明细与头id一致
        qcCheckHeaderEntity.setParentId(0L);
        qcCheckHeaderEntity.setSkuId(0L);
        qcCheckHeaderEntity.setTypeCode(typeCode);
        qcCheckHeaderEntity.setWarehouseId(warehouseId);
        qcCheckHeaderEntity.setTenantId(tenantId);
        qcCheckHeaderEntity.setStatusCode(QualityStatus.INITIAL.toString());
        qcCheckHeaderEntity.setId(autoIdClient.getAutoId(AutoIdConstants.TWmsQcCheckEntity));
        qcCheckHeaderEntity.setUpdateTime(new Date().getTime());
        qcCheckHeaderEntity.setUpdateUser(operationUser);
        qcCheckHeaderEntity.setCreateTime(new Date().getTime());
        qcCheckHeaderEntity.setCreateUser(operationUser);
        qcCheckMapper.insertSelective(qcCheckHeaderEntity,getSplitTableKey(dbShardVO));
        for (Map.Entry<Long,Long> entry:map.entrySet()) {
            TWmsQcCheckEntity qcCheckDetailEntity = new TWmsQcCheckEntity();
            qcCheckDetailEntity.setId(autoIdClient.getAutoId(AutoIdConstants.TWmsQcCheckEntity));
            qcCheckDetailEntity.setTenantId(tenantId);
            qcCheckDetailEntity.setWarehouseId(warehouseId);
            qcCheckDetailEntity.setParentId(qcCheckHeaderEntity.getId());
            qcCheckDetailEntity.setQcId(headerId);
            qcCheckDetailEntity.setIsQualified(new Byte("1"));
            qcCheckDetailEntity.setQcDetailId(entry.getKey());
            qcCheckDetailEntity.setSkuId(entry.getValue());
            qcCheckDetailEntity.setStatusCode(QualityStatus.INITIAL.toString());
            qcCheckDetailEntity.setUpdateTime(new Date().getTime());
            qcCheckDetailEntity.setUpdateUser(operationUser);
            qcCheckDetailEntity.setCreateTime(new Date().getTime());
            qcCheckDetailEntity.setCreateUser(operationUser);
            qcCheckMapper.insertSelective(qcCheckDetailEntity,getSplitTableKey(dbShardVO));
        }
        return MessageResult.getSucMessage();
    }

    /**
     * 质检单提交动作
     * @param id 要修改质检单的id
     * @param operationUser 修改人
     * @param dbShardVO
     * @return
     */
    @Override
    public MessageResult updateQcCheck(Long id,String operationUser, DbShardVO dbShardVO) {
        TWmsQcCheckEntity qcCheckEntity = qcCheckMapper.selectByPrimaryKey(id,getSplitTableKey(dbShardVO));
        qcCheckEntity.setStatusCode(QualityStatus.FINISHED.toString());
        qcCheckEntity.setUpdateTime(new Date().getTime());
        qcCheckEntity.setUpdateUser(operationUser);
        if(qcCheckEntity.getTypeCode().equals("Receipt")){
            TWmsReceiptHeaderEntity receiptHeaderEntity = receiptHeaderService.findByPrimaryKey(qcCheckEntity.getQcId(),changeInwhDbShareVO(dbShardVO));
            receiptHeaderEntity.setQcStatusCode(QualityStatus.FINISHED.toString());
            TWmsReceiptHeaderDTO receiptHeaderDTO = BeanUtils.copyBeanPropertyUtils(receiptHeaderEntity,TWmsReceiptHeaderDTO.class);
            receiptHeaderService.modifyReceiptHeader(receiptHeaderDTO,changeInwhDbShareVO(dbShardVO));
        }
        qcCheckMapper.updateByPrimaryKeySelective(qcCheckEntity,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }
}
