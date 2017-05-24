package com.huamengtong.wms.inwh.service.impl;

import com.huamengtong.wms.client.IAutoIdClient;
import com.huamengtong.wms.constants.AutoIdConstants;
import com.huamengtong.wms.core.annotation.NeedLog;
import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.service.BaseService;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.inwh.TWmsAsnDetailDTO;
import com.huamengtong.wms.dto.inwh.TWmsAsnHeaderDTO;
import com.huamengtong.wms.em.ActionCode;
import com.huamengtong.wms.em.LogType;
import com.huamengtong.wms.em.OperationStatusCode;
import com.huamengtong.wms.em.OrderTypeCode;
import com.huamengtong.wms.em.TicketStatusCode;
import com.huamengtong.wms.entity.inwh.TWmsAsnDetailEntity;
import com.huamengtong.wms.entity.inwh.TWmsAsnHeaderEntity;
import com.huamengtong.wms.inwh.mapper.AsnHeaderMapper;
import com.huamengtong.wms.inwh.service.IAsnDetailService;
import com.huamengtong.wms.inwh.service.IAsnHeaderService;
import com.huamengtong.wms.inwh.service.IQcDetailService;
import com.huamengtong.wms.inwh.service.IQcHeaderService;
import com.huamengtong.wms.inwh.service.IReceiptDetailService;
import com.huamengtong.wms.inwh.service.IReceiptHeaderService;
import com.huamengtong.wms.utils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AsnHeaderService extends BaseService implements IAsnHeaderService{

    @Autowired
    AsnHeaderMapper asnHeaderMapper;

    @Autowired
    IAutoIdClient autoIdClient;

    @Autowired
    IAsnDetailService asnDetailService;

    @Autowired
    IReceiptHeaderService receiptHeaderService;

    @Autowired
    IReceiptDetailService receiptDetailService;

    @Autowired
    IQcHeaderService qcHeaderService;

    @Autowired
    IQcDetailService qcDetailService;


    @Override
    public PageResponse<List<TWmsAsnHeaderEntity>> getAsnHeaders(TWmsAsnHeaderDTO asnHeaderDTO, DbShardVO dbShardVO) {
        TWmsAsnHeaderEntity asnHeaderEntity= BeanUtils.copyBeanPropertyUtils(asnHeaderDTO,TWmsAsnHeaderEntity.class);
        List<TWmsAsnHeaderEntity> asnHeaderEntities=asnHeaderMapper.queryAsnHeaderPages(asnHeaderEntity,getSplitTableKey(dbShardVO));
        Integer totalSize=asnHeaderMapper.queryAsnHeaderPageCount(asnHeaderEntity,getSplitTableKey(dbShardVO));
        PageResponse<List<TWmsAsnHeaderEntity>> response=new PageResponse();
        response.setTotal(totalSize);
        response.setRows(asnHeaderEntities);
        return response;
    }

    @Override
    public TWmsAsnHeaderEntity findByPrimaryKey(Long id, DbShardVO dbShardVO) {
        return asnHeaderMapper.selectByPrimaryKey(id,getSplitTableKey(dbShardVO));
    }

    @Override
    public MessageResult removeByPrimaryKey(Long id, DbShardVO dbShardVO) {
        asnDetailService.deleteAsnDetailsByHeaderId(id,dbShardVO);//删除明细数据
        asnHeaderMapper.deleteByPrimaryKey(id,getSplitTableKey(dbShardVO));//删除主表数据
        return MessageResult.getSucMessage();
    }

    @Override
    @NeedLog( type =LogType.OPREATION)
    public MessageResult insertAsnHeader(TWmsAsnHeaderDTO asnHeaderDTO, DbShardVO dbShardVO) {
        if(asnHeaderDTO.getCargoOwnerId() == null || asnHeaderDTO.getCargoOwnerId() == 0){
            return MessageResult.getMessage("E40001");
        }
        if(StringUtils.isEmpty(asnHeaderDTO.getFromTypeCode())){
            return MessageResult.getMessage("E51012");
        }
        if(StringUtils.isEmpty(asnHeaderDTO.getReceiptTypeCode())){
            return MessageResult.getMessage("E51013");
        }
        if(StringUtils.isEmpty(asnHeaderDTO.getTransportModeCode())){
            return MessageResult.getMessage("E51014");
        }
        TWmsAsnHeaderEntity asnHeaderEntity= BeanUtils.copyBeanPropertyUtils(asnHeaderDTO,TWmsAsnHeaderEntity.class);
        asnHeaderEntity.setId(autoIdClient.getAutoId(AutoIdConstants.TWmsAsnHeaderEntity));
        asnHeaderEntity.setStatusCode(TicketStatusCode.INIT.toString());
        asnHeaderMapper.insertAsnHeader(asnHeaderEntity,getSplitTableKey(dbShardVO));
        //记录日志
        this.writeOperationLog(asnHeaderEntity.getId(),asnHeaderEntity.getCargoOwnerId(),ActionCode.Create.toString(),"创建入库单通知单",OrderTypeCode.ASN.toString(),OperationStatusCode.Finished.toString(),dbShardVO);
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult updateAsnHeader(TWmsAsnHeaderDTO asnHeaderDTO, DbShardVO dbShardVO) {
        TWmsAsnHeaderEntity asnHeaderEntity =BeanUtils.copyBeanPropertyUtils(asnHeaderDTO,TWmsAsnHeaderEntity.class);
        asnHeaderMapper.updateAsnHeader(asnHeaderEntity,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }


    /**
     * 支持单个入库通知单生成质检单
     * 若选中的通知单中已经有生成质检单的表单，则返回Message
     * @param headerId  通知单主表id
     * @param operationUser  操作人
     * @param dbShardVO   分库标识
     * @return   返回
     */
    @Override
    @NeedLog(type = LogType.OPREATION)
    public MessageResult createQc(Long headerId, String operationUser, DbShardVO dbShardVO) {
        if(headerId==null){
            return MessageResult.getMessage("E51008");
        }
       if(isAlreadyGenerateQc(headerId,dbShardVO)){
           return MessageResult.getMessage("E51006");
       }
       Long currentTime = new Date().getTime();
        //更新通知单主表状态&更新人&更新时间,update表中数据
        TWmsAsnHeaderEntity asnHeaderEntity = asnHeaderMapper.selectByPrimaryKey(headerId,getSplitTableKey(dbShardVO));//取出通知单主表
        if(asnHeaderEntity.getStatusCode().equals(TicketStatusCode.FINISH.toString())){
            return  MessageResult.getMessage("E51006");
        }
        asnHeaderEntity.setStatusCode(TicketStatusCode.FINISH.toString());//更新通知单状态为已完成
        asnHeaderEntity.setQcStatusCode(TicketStatusCode.SUBMIT.toString());//通知单质检状态为已提交
        asnHeaderEntity.setUpdateUser(operationUser);//最后更新人
        asnHeaderEntity.setUpdateTime(currentTime);//最后更新时间
        //赋值，生成入库明细单据,直接置为提交状态
        Long qcId=autoIdClient.getAutoId(AutoIdConstants.TWmsQcHeaderEntity);//生成唯一质检单主表ID
        // 生成明细数据
        List<TWmsAsnDetailEntity> asnDetailEntities = asnDetailService.findAsnDetailsByHeaderId(headerId,dbShardVO);
        for(TWmsAsnDetailEntity asnDetailEntity:asnDetailEntities){
            //生成质检明细数据，赋值见函数
            TWmsAsnDetailDTO asnDetailDTO = BeanUtils.copyBeanPropertyUtils(asnDetailEntity, TWmsAsnDetailDTO.class);
            qcDetailService.createQcDetailFromAsn(qcId,asnDetailDTO,operationUser,dbShardVO);//插入数据
            asnDetailService.updateAsnDetailStatus(TicketStatusCode.FINISH.toString(),operationUser,asnDetailDTO,dbShardVO);
        }
        TWmsAsnHeaderDTO asnHeaderDTO = BeanUtils.copyBeanPropertyUtils(asnHeaderEntity,TWmsAsnHeaderDTO.class);
        qcHeaderService.createQcHeaderFromAsn(qcId,asnHeaderDTO,operationUser,dbShardVO);//生成质检主表数据
        asnHeaderMapper.updateAsnHeader(asnHeaderEntity,getSplitTableKey(dbShardVO));
        //记录通知单操作日志
        this.writeOperationLog(headerId,asnHeaderEntity.getCargoOwnerId(),ActionCode.Create.toString(),"生成质检单",OrderTypeCode.ASN.toString(),OperationStatusCode.Finished.toString(),dbShardVO);
        //记录质检单操作日志
        this.writeOperationLog(qcId,asnHeaderEntity.getCargoOwnerId(),ActionCode.Create.toString(),"创建质检单",OrderTypeCode.QC.toString(),OperationStatusCode.Finished.toString(),dbShardVO);

        return MessageResult.getSucMessage();
    }

    /**
     * 判断是否已生成质检单,若已经生成,则返回true
     * @param headerId
     * @param dbShardVO
     * @return
     */
    private boolean isAlreadyGenerateQc(Long headerId,DbShardVO dbShardVO) {
        if (qcHeaderService.findByAsnId(headerId, dbShardVO) == null) {
            return false;
        }
        return true;
    }


    /**
     * 提交通知单主表，同时更新明细单状态为提交
     * @param headerId
     * @param operationUser
     * @param dbShardVO
     * @return
     */
    @Override
    @NeedLog(type = LogType.OPREATION)
    public MessageResult updateSubmitAsn(Long headerId, String operationUser, DbShardVO dbShardVO) {
        //判断参数是否为空
        if(headerId==null){
            return MessageResult.getMessage("E51001");
        }
        TWmsAsnHeaderEntity asnHeaderEntity=asnHeaderMapper.selectByPrimaryKey(headerId,getSplitTableKey(dbShardVO));
        //判断通知单主表是否存在&是否包含明细单&主表状态是否为未提交,全为true可进行提交
        if(asnHeaderEntity == null){
            return MessageResult.getMessage("E51002",new Object[]{headerId});
        }
        if(checkAsnDetail(headerId,dbShardVO)){
            return MessageResult.getMessage("E51003",new Object[]{headerId});
        }
        if(!asnHeaderEntity.getStatusCode().equals(TicketStatusCode.INIT.toString())){
            return MessageResult.getMessage("E51007",new Object[]{headerId});
        }
        //当前时间定义为操作提交的时间
        Long currentTime = new Date().getTime();
        //更新明细状态为已提交
        List<TWmsAsnDetailEntity> asnDetailEntities = asnDetailService.findAsnDetailsByHeaderId(headerId,dbShardVO);
        for(TWmsAsnDetailEntity asnDetailEntity:asnDetailEntities){
            TWmsAsnDetailDTO asnDetailDTO = BeanUtils.copyBeanPropertyUtils(asnDetailEntity,TWmsAsnDetailDTO.class);
            asnDetailService.updateAsnDetailStatus(TicketStatusCode.SUBMIT.toString(),operationUser,asnDetailDTO,dbShardVO);
        }
        asnHeaderEntity.setStatusCode(TicketStatusCode.SUBMIT.toString());
        asnHeaderEntity.setUpdateUser(operationUser);
        asnHeaderEntity.setUpdateTime(currentTime);
        asnHeaderMapper.updateAsnHeader(asnHeaderEntity,getSplitTableKey(dbShardVO));
        //记录操作日志
        this.writeOperationLog(headerId,asnHeaderEntity.getCargoOwnerId(),ActionCode.Submit.toString(),"提交入库通知单",OrderTypeCode.ASN.toString(),OperationStatusCode.Finished.toString(),dbShardVO);

        return MessageResult.getSucMessage();
    }

    /**
     * 判断明细表中是否有对应的数据
     * @param headerId
     * @param dbShardVO
     * @return
     */
    private boolean checkAsnDetail(Long headerId, DbShardVO dbShardVO) {
        List<TWmsAsnDetailEntity> asnDetailEntities=asnDetailService.findAsnDetailsByHeaderId(headerId,dbShardVO);
        return CollectionUtils.isNotEmpty(asnDetailEntities) ? false:true;
    }

    /***
     * 入库通知单撤销
     * @param headerId
     * @param operationUser
     * @param dbShardVO
     * @return
     */
    @Override
    @NeedLog(type = LogType.OPREATION)
    public MessageResult updateRepealedAsn(Long headerId, String operationUser, DbShardVO dbShardVO) {
        //判断参数是否为空
        if(headerId==null){
            return MessageResult.getMessage("E51004");
        }
        TWmsAsnHeaderEntity asnHeaderEntity = asnHeaderMapper.selectByPrimaryKey(headerId,getSplitTableKey(dbShardVO));
        //判断通知单主表是否存在&是否包含明细单&主表状态是否为未提交,全为true可进行提交
        if(asnHeaderEntity == null){
            return MessageResult.getMessage("E51002",new Object[]{headerId});
        }
        if(checkAsnDetail(headerId,dbShardVO)){
            return MessageResult.getMessage("E51005",new Object[]{headerId});
        }
        if(!asnHeaderEntity.getStatusCode().equals(TicketStatusCode.SUBMIT.toString())){
            return MessageResult.getMessage("E51009",new Object[]{headerId});
        }
        List<TWmsAsnDetailEntity> asnDetailEntities = asnDetailService.findAsnDetailsByHeaderId(headerId,dbShardVO);
        for(TWmsAsnDetailEntity asnDetailEntity:asnDetailEntities){
            TWmsAsnDetailDTO asnDetailDTO = BeanUtils.copyBeanPropertyUtils(asnDetailEntity,TWmsAsnDetailDTO.class);
            asnDetailService.updateAsnDetailStatus(TicketStatusCode.REPEALED.toString(),operationUser,asnDetailDTO,dbShardVO);
        }
        asnHeaderEntity.setStatusCode(TicketStatusCode.INIT.toString());
        asnHeaderEntity.setUpdateUser(operationUser);
        asnHeaderEntity.setUpdateTime(new Date().getTime());
        asnHeaderMapper.updateAsnHeader(asnHeaderEntity,getSplitTableKey(dbShardVO));

        //记录操作日志
        this.writeOperationLog(headerId,asnHeaderEntity.getCargoOwnerId(),ActionCode.Repealed.toString(),"撤销入库通知单",OrderTypeCode.ASN.toString(),OperationStatusCode.Finished.toString(),dbShardVO);

        return MessageResult.getSucMessage();
    }

    /**
     * 点击质检完成后，判断检收数量和到货总数量是否相等，回写通知单状态以及实际收货数量
     * @param id  通知单主表id
     * @param qcQty  检收总数量
     * @param operationUser  操作人
     * @param dbShardVO   分库标识
     * @return   返回 messageResult
     */
    @Override
    @NeedLog(type = LogType.OPREATION)
    public MessageResult updateAsnFromQcFinish(Long id, Integer qcQty, String operationUser, String receiptStatusCode, DbShardVO dbShardVO) {
        TWmsAsnHeaderEntity asnHeaderEntity = asnHeaderMapper.selectByPrimaryKey(id,getSplitTableKey(dbShardVO));
        asnHeaderEntity.setTotalQtyReal(qcQty);
        asnHeaderEntity.setQcStatusCode(TicketStatusCode.FINISH.toString());
        asnHeaderEntity.setUpdateUser(operationUser);
        asnHeaderEntity.setUpdateTime(new Date().getTime());
        asnHeaderMapper.updateAsnHeader(asnHeaderEntity,getSplitTableKey(dbShardVO));
        this.writeOperationLog(id,asnHeaderEntity.getCargoOwnerId(),ActionCode.QCFINISH.toString(),"通知单"+id.toString()+"收货完成,检收数量"+qcQty.toString(),OrderTypeCode.ASN.toString(),OperationStatusCode.Finished.toString(),dbShardVO);
        return MessageResult.getSucMessage();
    }

}























