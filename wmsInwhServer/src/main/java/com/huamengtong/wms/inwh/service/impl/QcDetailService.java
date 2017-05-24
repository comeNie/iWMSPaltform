package com.huamengtong.wms.inwh.service.impl;

import com.huamengtong.wms.client.IAutoIdClient;
import com.huamengtong.wms.constants.AutoIdConstants;
import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.service.BaseService;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.inwh.TWmsAsnDetailDTO;
import com.huamengtong.wms.dto.inwh.TWmsQcDetailDTO;
import com.huamengtong.wms.dto.inwh.TWmsQcHeaderDTO;
import com.huamengtong.wms.em.ReceiptStatusCode;
import com.huamengtong.wms.entity.inwh.TWmsQcDetailEntity;
import com.huamengtong.wms.entity.inwh.TWmsQcHeaderEntity;
import com.huamengtong.wms.inwh.mapper.QcDetailMapper;
import com.huamengtong.wms.inwh.service.IQcDetailService;
import com.huamengtong.wms.inwh.service.IQcHeaderService;
import com.huamengtong.wms.utils.BeanUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class QcDetailService extends BaseService implements IQcDetailService {

    @Autowired
    QcDetailMapper qcDetailMapper;

    @Autowired
    IQcHeaderService qcHeaderService;

    @Autowired
    IAutoIdClient autoIdClient;

    @Override
    public TWmsQcDetailEntity findByPrimaryKey(Long id, DbShardVO dbShardVO) {
        return qcDetailMapper.selectByPrimaryKey(id,getSplitTableKey(dbShardVO));
    }

    @Override
    public MessageResult removeByPrimaryKey(Long id,String operationUser, DbShardVO dbShardVO) {
        TWmsQcDetailEntity qcDetailEntity=qcDetailMapper.selectByPrimaryKey(id,getSplitTableKey(dbShardVO));
        TWmsQcHeaderEntity qcHeaderEntity = qcHeaderService.findByPrimaryKey(qcDetailEntity.getQcId(),dbShardVO);
        qcHeaderEntity.setTotalQty(qcHeaderEntity.getTotalQty()-qcDetailEntity.getTotalQty());
        qcHeaderEntity.setUpdateUser(operationUser);
        qcHeaderEntity.setUpdateTime(new Date().getTime());
        qcDetailMapper.deleteByPrimaryKey(id,getSplitTableKey(dbShardVO));
        //更新主表数据
        TWmsQcHeaderDTO qcHeaderDTO = BeanUtils.copyBeanPropertyUtils(qcHeaderEntity,TWmsQcHeaderDTO.class);
        qcHeaderService.modifyQcHeader(qcHeaderDTO,dbShardVO);
        return MessageResult.getSucMessage();
    }

    /**
     * 新增质检明细表，需要回写质检主表的数据,暂时未用到
     * @param qcDetailDTO
     * @param dbShardVO
     * @return
     */
    @Override
    public MessageResult createQcDetail(TWmsQcDetailDTO qcDetailDTO, DbShardVO dbShardVO) {
        TWmsQcDetailEntity qcDetailEntity= BeanUtils.copyBeanPropertyUtils(qcDetailDTO,TWmsQcDetailEntity.class);
        if(qcDetailEntity.getSkuId() ==null){
            return MessageResult.getMessage("E51016");
        }
        if (qcDetailEntity.getId() == null) {
            qcDetailEntity.setId(autoIdClient.getAutoId(AutoIdConstants.TWmsQcDetailEntity));
        }
        //回写主表数据，总数量
        TWmsQcHeaderEntity qcHeaderEntity = qcHeaderService.findByPrimaryKey(qcDetailDTO.getQcId(),dbShardVO);
        qcHeaderEntity.setTotalQty(qcHeaderEntity.getTotalQty()+qcDetailEntity.getTotalQty());//总数量
        qcHeaderEntity.setUpdateUser(qcDetailEntity.getUpdateUser());
        qcHeaderEntity.setUpdateTime(new Date().getTime());

        qcDetailMapper.insertQcDetail(qcDetailEntity,getSplitTableKey(dbShardVO));//插入明细数据
        //更改主表数据
        TWmsQcHeaderDTO qcHeaderDTO = BeanUtils.copyBeanPropertyUtils(qcHeaderEntity,TWmsQcHeaderDTO.class);
        qcHeaderService.modifyQcHeader(qcHeaderDTO,dbShardVO);
        return MessageResult.getSucMessage();
    }

    /**
     *  更新表单时确认是否更改数量,如果更改,需要回写主表数据
     * @param qcDetailDTO
     * @param dbShardVO
     * @return
     */
    @Override
    public MessageResult updateQcDetail(TWmsQcDetailDTO qcDetailDTO, DbShardVO dbShardVO) {
        TWmsQcDetailEntity qcDetailEntity=BeanUtils.copyBeanPropertyUtils(qcDetailDTO,TWmsQcDetailEntity.class);
        if(qcDetailEntity.getSkuId() ==null){
            return MessageResult.getMessage("E51016");
        }
        TWmsQcDetailEntity qcDetailEntity1=qcDetailMapper.selectByPrimaryKey(qcDetailEntity.getId(),getSplitTableKey(dbShardVO));
        TWmsQcHeaderEntity qcHeaderEntity = qcHeaderService.findByPrimaryKey(qcDetailEntity.getQcId(),dbShardVO);
        if(!(qcDetailEntity.getTotalQty()==null)){
            qcHeaderEntity.setTotalQty(qcHeaderEntity.getTotalQty()+qcDetailEntity.getTotalQty()-qcDetailEntity1.getTotalQty());
        }
        qcHeaderEntity.setUpdateUser(qcDetailEntity.getUpdateUser());
        qcHeaderEntity.setUpdateTime(new Date().getTime());
        qcDetailMapper.updateQcDetail(qcDetailEntity,getSplitTableKey(dbShardVO));//更新明细表中的此条数据
        TWmsQcHeaderDTO qcHeaderDTO = BeanUtils.copyBeanPropertyUtils(qcHeaderEntity,TWmsQcHeaderDTO.class);
        qcHeaderService.modifyQcHeader(qcHeaderDTO,dbShardVO);
        return MessageResult.getSucMessage();
    }

    @Override
    public PageResponse<List> queryDetailsByHeaderId(Map map, DbShardVO dbShardVO) {
        map.put("splitTableKey",getSplitTableKey(dbShardVO));
        List<TWmsQcDetailEntity> qcDetailEntities=qcDetailMapper.queryDetailsPagesByHeaderId(map);
        Integer totalSize = qcDetailMapper.queryDetailsPageCountByHeader(map);
        PageResponse<List> response=new PageResponse();
        response.setTotal(totalSize);
        response.setRows(qcDetailEntities);
        return response;
    }

    @Override
    public List<TWmsQcDetailEntity> findQcDetailsByHeaderId(Long headerId, DbShardVO dbShardVO) {
        return qcDetailMapper.selectQcDetailsByHeaderId(headerId,getSplitTableKey(dbShardVO));
    }

    /**
     * 从通知单创建质检单专用方法,无需把detail表中的总数量等累加至主表中
     * @param asnDetailDTO  通知明细单
     * @param qcId   质检单主表id
     * @param operationUser 操作人
     * @param dbShardVO    分库标识
     * @return
     */
    @Override
    public MessageResult createQcDetailFromAsn(Long qcId, TWmsAsnDetailDTO asnDetailDTO, String operationUser, DbShardVO dbShardVO) {
        Long currentTime = new Date().getTime();
        TWmsQcDetailEntity qcDetailEntity= new TWmsQcDetailEntity();
        qcDetailEntity.setId(autoIdClient.getAutoId(AutoIdConstants.TWmsQcDetailEntity));
        qcDetailEntity.setAsnDetailId(asnDetailDTO.getId());//通知明细单ID
        qcDetailEntity.setTenantId(asnDetailDTO.getTenantId());//租户ID
        qcDetailEntity.setWarehouseId(asnDetailDTO.getWarehouseId());//仓库ID
        qcDetailEntity.setQcId(qcId);//质检主表ID
        qcDetailEntity.setTotalQty(asnDetailDTO.getExpectedQty());//设置明细表单的数量
        qcDetailEntity.setSkuId(asnDetailDTO.getSkuId());//商品ID
        qcDetailEntity.setSku(asnDetailDTO.getSku());//商品条码
        qcDetailEntity.setSkuName(asnDetailDTO.getSkuName());//商品名称
        qcDetailEntity.setSkuBarcode(asnDetailDTO.getSkuBarcode());//条形码
        qcDetailEntity.setProperty(asnDetailDTO.getProperty());//商品用途
        qcDetailEntity.setStatusCode(ReceiptStatusCode.None.toString());//质检明细单状态置为提交，不可再被更改
        qcDetailEntity.setCreateUser(operationUser);//创建人
        qcDetailEntity.setCreateTime(currentTime);//创建时间
        qcDetailEntity.setUpdateUser(operationUser);//最后更新人
        qcDetailEntity.setUpdateTime(currentTime);//最后更新时间
        qcDetailMapper.insertQcDetail(qcDetailEntity,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }


    @Override
    public MessageResult deleteQcDetailsByHeaderId(Long headerId, DbShardVO dbShardVO) {
        qcDetailMapper.deleteQcDetailsByHeaderId(headerId,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }

    @Override
    public TWmsQcDetailEntity findByHeaderIdAndBarcode(Long headerId, String barcode, DbShardVO dbShardVO) {
        Map<String,Object> map=new HashedMap();
        map.put("headerId",headerId);
        map.put("barcode",barcode);
        map.put("splitTableKey",getSplitTableKey(dbShardVO));
        return  qcDetailMapper.selectByHeaderIdAndSku(map);
    }

    @Override
    public MessageResult updateQcDetailFromQcReceipt(TWmsQcDetailDTO qcDetailDTO,Integer qcQty, String storageRoomId, String operationUser, DbShardVO dbShardVO) {
        //更新质检明细表单状态和数据
        qcDetailDTO.setQcQty(qcDetailDTO.getQcQty()+qcQty);//回写实收总数量
        if(qcDetailDTO.getStorageRoomId()==null){
            qcDetailDTO.setStorageRoomId(storageRoomId);//库房ID
        }else if(qcDetailDTO.getStorageRoomId().contains(storageRoomId)){
            qcDetailDTO.setStorageRoomId(qcDetailDTO.getStorageRoomId());
        }else {
            qcDetailDTO.setStorageRoomId(qcDetailDTO.getStorageRoomId().concat(",").concat(storageRoomId));
        }
        qcDetailDTO.setStatusCode(ReceiptStatusCode.Receipting.toString());//设置单据状态已结束
        qcDetailDTO.setUpdateUser(operationUser);
        qcDetailDTO.setUpdateTime(new Date().getTime());
        TWmsQcDetailEntity qcDetailEntity = BeanUtils.copyBeanPropertyUtils(qcDetailDTO,TWmsQcDetailEntity.class);
        qcDetailMapper.updateQcDetail(qcDetailEntity,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }


    @Override
    public MessageResult updateQcDetailFromQcFinish(List<TWmsQcDetailEntity> qcDetailEntityList,String operationUser, DbShardVO dbShardVO) {
        for(TWmsQcDetailEntity qcDetailEntity:qcDetailEntityList){
            int compare = qcDetailEntity.getQcQty().compareTo(qcDetailEntity.getTotalQty());
            if( compare == 0){
                qcDetailEntity.setStatusCode(ReceiptStatusCode.ReceiptAll.toString());
            }else if (compare == -1){
                qcDetailEntity.setStatusCode(ReceiptStatusCode.ReceiptPart.toString());
            }else {
                qcDetailEntity.setStatusCode(ReceiptStatusCode.ReceiptOver.toString());
            }
            qcDetailEntity.setUpdateUser(operationUser);
            qcDetailEntity.setUpdateTime(new Date().getTime());
            qcDetailMapper.updateQcDetail(qcDetailEntity,getSplitTableKey(dbShardVO));
        }
        return MessageResult.getSucMessage();
    }
}
