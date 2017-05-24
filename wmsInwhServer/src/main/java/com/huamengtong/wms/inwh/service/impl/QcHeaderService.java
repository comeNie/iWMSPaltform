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
import com.huamengtong.wms.dto.inwh.TWmsQcDetailDTO;
import com.huamengtong.wms.dto.inwh.TWmsQcHeaderDTO;
import com.huamengtong.wms.em.ActionCode;
import com.huamengtong.wms.em.LogType;
import com.huamengtong.wms.em.OperationStatusCode;
import com.huamengtong.wms.em.OrderTypeCode;
import com.huamengtong.wms.em.ReceiptStatusCode;
import com.huamengtong.wms.em.TicketStatusCode;
import com.huamengtong.wms.entity.inwh.TWmsAsnDetailEntity;
import com.huamengtong.wms.entity.inwh.TWmsAsnHeaderEntity;
import com.huamengtong.wms.entity.inwh.TWmsQcDetailEntity;
import com.huamengtong.wms.entity.inwh.TWmsQcHeaderEntity;
import com.huamengtong.wms.entity.sku.TWmsSkuEntity;
import com.huamengtong.wms.inwh.mapper.QcHeaderMapper;
import com.huamengtong.wms.inwh.service.IAsnDetailService;
import com.huamengtong.wms.inwh.service.IAsnHeaderService;
import com.huamengtong.wms.inventory.service.IInventoryService;
import com.huamengtong.wms.inwh.service.IQcDetailService;
import com.huamengtong.wms.inwh.service.IQcHeaderService;
import com.huamengtong.wms.inwh.service.IReceiptDetailService;
import com.huamengtong.wms.inwh.service.IReceiptHeaderService;
import com.huamengtong.wms.sku.service.ISkuService;
import com.huamengtong.wms.utils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Service
public class QcHeaderService extends BaseService implements IQcHeaderService {

    @Autowired
    QcHeaderMapper qcHeaderMapper;

    @Autowired
    IQcDetailService qcDetailService;

    @Autowired
    IAsnHeaderService asnHeaderService;

    @Autowired
    IAsnDetailService asnDetailService;

    @Autowired
    IReceiptHeaderService receiptHeaderService;

    @Autowired
    IReceiptDetailService receiptDetailService;

    @Autowired
    ISkuService skuService;

    @Autowired
    IInventoryService inventoryService;

    @Autowired
    IAutoIdClient autoIdClient;

    @Override
    public PageResponse<List<TWmsQcHeaderEntity>> getQcHeaders(TWmsQcHeaderDTO qcHeaderDTO, DbShardVO dbShardVO) {
        TWmsQcHeaderEntity qcHeaderEntity= BeanUtils.copyBeanPropertyUtils(qcHeaderDTO,TWmsQcHeaderEntity.class);
        List<TWmsQcHeaderEntity> qcHeaderEntities = qcHeaderMapper.queryQcHeaderPages(qcHeaderEntity,getSplitTableKey(dbShardVO));
        Integer totalSize=qcHeaderMapper.queryQcHeadersPageCount(qcHeaderEntity,getSplitTableKey(dbShardVO));
        PageResponse<List<TWmsQcHeaderEntity>> response=new PageResponse();
        response.setTotal(totalSize);
        response.setRows(qcHeaderEntities);
        return response;
    }

    @Override
    public TWmsQcHeaderEntity findByPrimaryKey(Long id, DbShardVO dbShardVO) {
        return qcHeaderMapper.selectByPrimaryKey(id,getSplitTableKey(dbShardVO));
    }

    @Override
    public MessageResult removeByPrimaryKey(Long id, DbShardVO dbShardVO) {
        qcDetailService.deleteQcDetailsByHeaderId(id, dbShardVO);// 删除明细数据
        qcHeaderMapper.deleteByPrimaryKey(id,getSplitTableKey(dbShardVO));//删除主表数据
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult insertQcHeader(TWmsQcHeaderDTO qcHeaderDTO, DbShardVO dbShardVO) {
        TWmsQcHeaderEntity qcHeaderEntity=BeanUtils.copyBeanPropertyUtils(qcHeaderDTO,TWmsQcHeaderEntity.class);
        if(qcHeaderEntity.getId() == null){
            qcHeaderEntity.setId(autoIdClient.getAutoId(AutoIdConstants.TWmsQcHeaderEntity));
        }
        qcHeaderMapper.insertQcHeader(qcHeaderEntity,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult modifyQcHeader(TWmsQcHeaderDTO qcHeaderDTO, DbShardVO dbShardVO) {
        TWmsQcHeaderEntity qcHeaderEntity=BeanUtils.copyBeanPropertyUtils(qcHeaderDTO,TWmsQcHeaderEntity.class);
        qcHeaderMapper.updateQcHeader(qcHeaderEntity,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }


    /**
     * 单个提交质检表单和明细单
     * 检查是否满足提交条件,满足则提交
     * 暂时保留功能
     * @param headerId
     * @param operationUser
     * @param dbShardVO
     * @return
     */
    @Override
    public MessageResult updateSubmitQc(Long headerId, String operationUser, DbShardVO dbShardVO) {

        //判断传参是否为空
        if(headerId==null){
            return MessageResult.getMessage("E52005");
        }
        //若无明细不可提交
        if(!isDetailsExists(headerId,dbShardVO)){
            return MessageResult.getMessage("E52004",new Object[]{headerId});
        }
        TWmsQcHeaderEntity qcHeaderEntity = qcHeaderMapper.selectByPrimaryKey(headerId,getSplitTableKey(dbShardVO));
        //质检单不存在，返回
        if (qcHeaderEntity == null){
            return MessageResult.getMessage("E52005");
        }
        //质检单是初始化状态才可提交
        if (!qcHeaderEntity.getStatusCode().equals(TicketStatusCode.INIT.toString())){
            return MessageResult.getMessage("E52001");
        }
        qcHeaderEntity.setStatusCode(TicketStatusCode.SUBMIT.toString());
        qcHeaderEntity.setUpdateUser(operationUser);//最后更新人
        qcHeaderEntity.setUpdateTime(new Date().getTime());//最后更新时间
        qcHeaderMapper.updateQcHeader(qcHeaderEntity,getSplitTableKey(dbShardVO));//更新主表状态
        return MessageResult.getSucMessage();
    }


    /**
     * 单个撤销提交的质检表单和明细单
     *暂时保留功能
     * @param headerId
     * @param operationUser
     * @param dbShardVO
     * @return
     */
    @Override
    public MessageResult updateRepealedQc(Long headerId, String operationUser, DbShardVO dbShardVO) {

        if(headerId==null){
            return MessageResult.getMessage("E52006");
        }
        TWmsQcHeaderEntity qcHeaderEntity = qcHeaderMapper.selectByPrimaryKey(headerId,getSplitTableKey(dbShardVO));
        if(qcHeaderEntity==null){
            return MessageResult.getMessage("E52006");
        }
        //check质检单是否为提交状态,不是即返回客户信息
        if(!qcHeaderEntity.getStatusCode().equals(TicketStatusCode.SUBMIT.toString())){
            return MessageResult.getMessage("E52002",new Object[]{headerId});
        }
        qcHeaderEntity.setStatusCode(TicketStatusCode.INIT.toString());//设置状态为撤销状态
        qcHeaderEntity.setUpdateUser(operationUser);//最后更新人
        qcHeaderEntity.setUpdateTime(new Date().getTime());//最后更新时间
        qcHeaderMapper.updateQcHeader(qcHeaderEntity,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }


    /**
     * 单个对质检单进行质检完成
     * 回写通知单中状态
     * @param headerId  质检单主表ID
     * @param operationUser  操作人
     * @param dbShardVO  分库标识
     * @return  返回messageResult
     */
    @Override
    @NeedLog(type = LogType.OPREATION)
    public MessageResult updateFinishQc(Long headerId, String operationUser, DbShardVO dbShardVO) {
        //校验参数
        if(headerId==null){
            return MessageResult.getMessage("E52007");
        }
        //这里不再判断明细是否存在，在Asn中做判断
        TWmsQcHeaderEntity qcHeaderEntity = qcHeaderMapper.selectByPrimaryKey(headerId,getSplitTableKey(dbShardVO));
        TWmsAsnHeaderEntity asnHeaderEntity = asnHeaderService.findByPrimaryKey(qcHeaderEntity.getAsnId(),dbShardVO);
        if (qcHeaderEntity == null) {
            return MessageResult.getMessage("E52007");
        }
        List<TWmsQcDetailEntity> qcDetailEntities = qcDetailService.findQcDetailsByHeaderId(headerId,dbShardVO);
        //检查质检明细单是否全为已完成状态,已完成才可继续收货完成
        if(!isDetailFinished(qcDetailEntities)){
            return  MessageResult.getMessage("E52014",new Object[]{headerId});
        }
        //检查质检单是否为提交状态
        if(!qcHeaderEntity.getStatusCode().equals(TicketStatusCode.SUBMIT.toString())){
            return MessageResult.getMessage("E52003",new Object[]{headerId});
        }
        //更新明细状态
        qcDetailService.updateQcDetailFromQcFinish(qcDetailEntities,operationUser,dbShardVO);
        //当前时间为操作时间
        Long currentTime = new Date().getTime();
        Integer qty=0;//初始化累加检收数量
        for(TWmsQcDetailEntity qcDetailEntity:qcDetailEntities){
            if(!(qcDetailEntity.getQcQty() == null)){
                qty=qty+qcDetailEntity.getQcQty();//累加明细单表的总数量
            }
        }
        //判断收货状态全部收货OR部分收货OR超收
        String receiptStatusCode;
        if(qcHeaderEntity.getTotalQty().compareTo(qty)==0){
            receiptStatusCode=ReceiptStatusCode.ReceiptAll.toString();
        }else if(qcHeaderEntity.getTotalQty().compareTo(qty)==-1){
            receiptStatusCode=ReceiptStatusCode.ReceiptOver.toString();
        }else{
            receiptStatusCode=ReceiptStatusCode.ReceiptPart.toString();
        }
        //更新通知单中的质检状态&实收数量&更新人&更新时间
        asnHeaderService.updateAsnFromQcFinish(qcHeaderEntity.getAsnId(),qty,operationUser,receiptStatusCode,dbShardVO);
        //更新入库单中的状态和收货状态, 实收数量在质检收货中更新
        receiptHeaderService.updateReceiptStatusFromQcFinish(qcHeaderEntity.getAsnId(),receiptStatusCode,operationUser,dbShardVO);
        qcHeaderEntity.setStatusCode(TicketStatusCode.FINISH.toString());
        qcHeaderEntity.setQcPrincipalUser(operationUser);//质检负责人
        qcHeaderEntity.setUpdateUser(operationUser);//最后更新人
        qcHeaderEntity.setUpdateTime(currentTime); //最后更新时间
        qcHeaderMapper.updateQcHeader(qcHeaderEntity,getSplitTableKey(dbShardVO));
        //记录操作日志
        this.writeOperationLog(headerId,asnHeaderEntity.getCargoOwnerId(),ActionCode.QCFINISHED.toString(),"收货完成",OrderTypeCode.QC.toString(),OperationStatusCode.Finished.toString(),dbShardVO);
        return MessageResult.getSucMessage();
    }

    @Override
    public TWmsQcHeaderEntity findByAsnId(Long asnId, DbShardVO dbShardVO) {
        return qcHeaderMapper.selectByAsnId(asnId,getSplitTableKey(dbShardVO));
    }

    /**
     * 根据主表ID判断是否存在明细数据,若无明细则返回false;
     * @param headerId
     * @param dbShardVO
     * @return
     */
    private boolean isDetailsExists(Long headerId,DbShardVO dbShardVO){
        if(CollectionUtils.isEmpty(qcDetailService.findQcDetailsByHeaderId(headerId,dbShardVO))){
            return false;
        }
        return true;
    }

    @Override
    public MessageResult createQcHeaderFromAsn(Long id, TWmsAsnHeaderDTO asnHeaderDTO, String operationUser, DbShardVO dbShardVO) {
        TWmsQcHeaderEntity qcHeaderEntity = new TWmsQcHeaderEntity();
        Long currentTime = new Date().getTime();
        qcHeaderEntity.setId(id);//qc主表id赋值
        qcHeaderEntity.setTenantId(asnHeaderDTO.getTenantId());//租户ID
        qcHeaderEntity.setWarehouseId(asnHeaderDTO.getWarehouseId());//仓库ID
        qcHeaderEntity.setAsnId(asnHeaderDTO.getId());//通知单主表ID
        qcHeaderEntity.setTotalQty(asnHeaderDTO.getTotalQty());//总数量
        qcHeaderEntity.setTotalCategoryQty(asnHeaderDTO.getTotalCategoryQty());//商品总数量
        qcHeaderEntity.setStatusCode(TicketStatusCode.SUBMIT.toString());//质检单状态置为已提交,生成后不可撤销
        qcHeaderEntity.setDescription(asnHeaderDTO.getDescription());
        qcHeaderEntity.setSubmitUser(operationUser);// 提交人
        qcHeaderEntity.setSubmitDate(currentTime);// 提交时间
        qcHeaderEntity.setCreateUser(operationUser);//记录创建人
        qcHeaderEntity.setCreateTime(currentTime);//记录创建时间
        qcHeaderEntity.setUpdateUser(operationUser);//记录修改人
        qcHeaderEntity.setUpdateTime(currentTime);//记录修改时间
        qcHeaderMapper.insertQcHeader(qcHeaderEntity,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }

    /**
     * 质检收货功能
     * 由质检单明细完成生成入库单,同时生成库存
     * 质检明细单状态只有提交和已完成两种
     * 参数：质检主表ID,商品条码,操作用户,库房ID,实际检收数量,库房温度
     * @param qcId
     * @param barcode
     * @param operationUser
     * @param storageRoomId
     * @param qcQty
     * @param warehouseTemp
     * @param dbShardVO
     * @return
     */
    @Override
    @NeedLog(type = LogType.OPREATION)
    public MessageResult createReceipt(Long qcId, String barcode,String operationUser,Integer qcQty,String inventoryStatusCode,String storageRoomId,BigDecimal warehouseTemp, DbShardVO dbShardVO) {
        //参数是否可用，不可用则return
        if(qcId == null || operationUser==null || StringUtils.isEmpty(storageRoomId) || qcQty==null){
            return MessageResult.getMessage("E52011");
        }
        //取出公共数据数据
        TWmsQcDetailEntity qcDetailEntity = qcDetailService.findByHeaderIdAndBarcode(qcId,barcode,dbShardVO);//取出质检单明细表
        //取不出数据，请核实填写的信息
        if(qcDetailEntity==null){
            return MessageResult.getMessage("E52009");
        }
        TWmsQcDetailDTO qcDetailDTO = BeanUtils.copyBeanPropertyUtils(qcDetailEntity,TWmsQcDetailDTO.class);
        TWmsQcHeaderEntity qcHeaderEntity = qcHeaderMapper.selectByPrimaryKey(qcId,getSplitTableKey(dbShardVO));//取出质检单主表
        TWmsQcHeaderDTO qcHeaderDTO = BeanUtils.copyBeanPropertyUtils(qcHeaderEntity,TWmsQcHeaderDTO.class);
        TWmsSkuEntity skuEntity = skuService.findByPrimaryKey(qcDetailEntity.getSkuId(),getSkuDbShareVO(dbShardVO));//取出商品明细表
        //商品不存在则return错误信息
        if(skuEntity == null){
            return MessageResult.getMessage("E52010",new Object[]{barcode});
        }
        //通知主表单&通知明细单
        TWmsAsnHeaderEntity asnHeaderEntity = asnHeaderService.findByPrimaryKey(qcHeaderEntity.getAsnId(),dbShardVO);//取出通知单主表
        TWmsAsnHeaderDTO asnHeaderDTO = BeanUtils.copyBeanPropertyUtils(asnHeaderEntity,TWmsAsnHeaderDTO.class);
        TWmsAsnDetailEntity asnDetailEntity = asnDetailService.findByPrimaryKey(qcDetailEntity.getAsnDetailId(),dbShardVO);//取出通知单明细表
        TWmsAsnDetailDTO asnDetailDTO = BeanUtils.copyBeanPropertyUtils(asnDetailEntity,TWmsAsnDetailDTO.class);
        //取不出数据，请核实填写的信息
        if(asnHeaderEntity==null || asnDetailEntity==null){
            return MessageResult.getMessage("E52008");
        }
        //如果还未生成入库单主表，则生成入库单主表再生成入库单明细表
        Long receiptId = getReceiptId(asnHeaderEntity.getId(),dbShardVO);
        if(receiptId == null){
            //还未生成入库单主表，则生成入库单主表
            //获取入库单主表唯一Id
            Long receiptHeaderId = autoIdClient.getAutoId(AutoIdConstants.TWmsReceiptHeaderEntity);
            receiptHeaderService.createReceiptHeaderFromQc(receiptHeaderId,operationUser,qcHeaderDTO,asnHeaderDTO,dbShardVO);//记录操作日志
            //生成入库单明细数据
            receiptDetailService.createReceiptDetailsFromQc(asnDetailEntity,skuEntity,operationUser,receiptHeaderId,storageRoomId,qcQty,inventoryStatusCode,warehouseTemp,dbShardVO);
        }else {
            //如果已生成入库单主表，则只需要生成明细表及生成库存信息即可回写入库主表总数量
            //生成入库单明细数据
            receiptDetailService.createReceiptDetailsFromQc(asnDetailEntity,skuEntity,operationUser,receiptId,storageRoomId,qcQty,inventoryStatusCode,warehouseTemp,dbShardVO);
        }
        //更新通知明细单状态,无需更新通知单主表状态
        asnDetailService.updateFromQcReceipt(qcQty,operationUser,asnDetailDTO,dbShardVO);
        //更新质检明细表状态
        qcDetailService.updateQcDetailFromQcReceipt(qcDetailDTO,qcQty,storageRoomId,operationUser,dbShardVO);//最后更新质检明细表数据及状态
        //记录操作日志
        this.writeOperationLog(qcId,asnHeaderEntity.getCargoOwnerId(),ActionCode.Create.toString(),"收货[商品条码:"+barcode+"+||收货数量:"+qcQty+"]",OrderTypeCode.QC.toString(),OperationStatusCode.Finished.toString(),dbShardVO);
        return MessageResult.getSucMessage();
    }


    /**
     * 通过质检主表ID判断是否已经生成入库主单，如果生成则返回入库单ID,未生成返回null
     * @param asnId
     * @param dbShardVO
     * @return
     */
    private Long getReceiptId(Long asnId,DbShardVO dbShardVO){
        if(receiptHeaderService.findByAsnId(asnId,dbShardVO) == null){
            return null;
        }
        return receiptHeaderService.findByAsnId(asnId,dbShardVO).getId();
    }

    /**
     * 根据主表Id判断是否所有明细都处于已完成状态，全部完成返回true，否则返回false
     * @param qcDetailEntities
     * @return
     */
    private boolean isDetailFinished( List<TWmsQcDetailEntity> qcDetailEntities){
        for (TWmsQcDetailEntity qcDetailEntity:qcDetailEntities){
            if(!qcDetailEntity.getStatusCode().equals(ReceiptStatusCode.Receipting.toString())){
                return false;
            }
        }
        return true;
    }

}



















