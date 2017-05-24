package com.huamengtong.wms.inwh.service.impl;

import com.huamengtong.wms.client.IAutoIdClient;
import com.huamengtong.wms.constants.AutoIdConstants;
import com.huamengtong.wms.core.annotation.NeedLog;
import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.service.BaseService;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.domain.TWmsReceiptHeaderPrintEntity;
import com.huamengtong.wms.dto.inwh.TWmsAsnHeaderDTO;
import com.huamengtong.wms.dto.inwh.TWmsQcHeaderDTO;
import com.huamengtong.wms.dto.inwh.TWmsReceiptHeaderDTO;
import com.huamengtong.wms.dto.report.TWmsReportReceiptDetailDTO;
import com.huamengtong.wms.em.ActionCode;
import com.huamengtong.wms.em.LogType;
import com.huamengtong.wms.em.OperationStatusCode;
import com.huamengtong.wms.em.OrderTypeCode;
import com.huamengtong.wms.em.QualityStatus;
import com.huamengtong.wms.em.ReceiptStatusCode;
import com.huamengtong.wms.em.TicketStatusCode;
import com.huamengtong.wms.entity.inwh.TWmsReceiptDetailEntity;
import com.huamengtong.wms.entity.inwh.TWmsReceiptHeaderEntity;
import com.huamengtong.wms.entity.main.TWmsCargoOwnerEntity;
import com.huamengtong.wms.inventory.service.IInventoryService;
import com.huamengtong.wms.inwh.mapper.ReceiptHeaderMapper;
import com.huamengtong.wms.inwh.service.IQcCheckService;
import com.huamengtong.wms.inwh.service.IReceiptHeaderService;
import com.huamengtong.wms.main.service.ICargoOwnerService;
import com.huamengtong.wms.main.service.ICodeService;
import com.huamengtong.wms.main.service.IStorageRoomService;
import com.huamengtong.wms.utils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReceiptHeaderService extends BaseService implements IReceiptHeaderService {

    @Autowired
    ReceiptHeaderMapper receiptHeaderMapper;

    @Autowired
    ReceiptDetailService receiptDetailService;

    @Autowired
    IAutoIdClient autoIdClient;

    @Autowired
    ICargoOwnerService cargoOwnerService;

    @Autowired
    IStorageRoomService storageRoomService;

    @Autowired
    ICodeService codeService;

    @Autowired
    IInventoryService inventoryService;

    @Autowired
    IQcCheckService qcCheckService;

    @Override
    public PageResponse<List<TWmsReceiptHeaderEntity>> getReceiptHeader(TWmsReceiptHeaderDTO receiptHeaderDTO, DbShardVO dbShardVO) {
        TWmsReceiptHeaderEntity receiptHeaderEntity=BeanUtils.copyBeanPropertyUtils(receiptHeaderDTO,TWmsReceiptHeaderEntity.class);
        List<TWmsReceiptHeaderEntity> receiptHeaderEntityList=receiptHeaderMapper.queryReceiptHeaderPages(receiptHeaderEntity,getSplitTableKey(dbShardVO));
        Integer totalSize=receiptHeaderMapper.queryReceiptHeaderPageCount(receiptHeaderEntity,getSplitTableKey(dbShardVO));
        PageResponse<List<TWmsReceiptHeaderEntity>> response=new PageResponse();
        response.setTotal(totalSize);
        response.setRows(receiptHeaderEntityList);
        return response;
    }

    @Override
    public MessageResult removeReceiptHeader(Long id, DbShardVO dbShardVO) {
        receiptDetailService.deleteReceiptDetailsByHeaderId(id,dbShardVO);//删除明细数据
        receiptHeaderMapper.deleteByPrimaryKey(id,getSplitTableKey(dbShardVO));//删除主表数据
        return MessageResult.getSucMessage();
    }

    @Override
    @NeedLog( type =LogType.OPREATION)
    public MessageResult createReceiptHeader(TWmsReceiptHeaderDTO receiptHeaderDTO, DbShardVO dbShardVO) {
        TWmsReceiptHeaderEntity receiptHeaderEntity = BeanUtils.copyBeanPropertyUtils(receiptHeaderDTO,TWmsReceiptHeaderEntity.class);
        //如果已经生成ID这里不再生成
        if (receiptHeaderEntity.getId() == null) {
            receiptHeaderEntity.setId(autoIdClient.getAutoId(AutoIdConstants.TWmsReceiptHeaderEntity));
        }
        if (receiptHeaderEntity.getCargoOwnerId() == null || receiptHeaderEntity.getCargoOwnerId() == 0){
            return  MessageResult.getMessage("E40001");
        }
        receiptHeaderMapper.insertReceiptHeader(receiptHeaderEntity,getSplitTableKey(dbShardVO));

        //记录日志
        this.writeOperationLog(receiptHeaderEntity.getId(),receiptHeaderEntity.getCargoOwnerId(),ActionCode.Create.toString(),"创建入库单",OrderTypeCode.Receipt.toString(),OperationStatusCode.Finished.toString(),dbShardVO);

        return MessageResult.getSucMessage();
    }



    @Override
    public MessageResult modifyReceiptHeader(TWmsReceiptHeaderDTO receiptHeaderDTO, DbShardVO dbShardVO) {
        TWmsReceiptHeaderEntity receiptHeaderEntity=BeanUtils.copyBeanPropertyUtils(receiptHeaderDTO,TWmsReceiptHeaderEntity.class);
        receiptHeaderMapper.updateReceiptHeader(receiptHeaderEntity,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }

    @Override
    public TWmsReceiptHeaderEntity findByPrimaryKey(Long id, DbShardVO dbShardVO) {
        return receiptHeaderMapper.selectByPrimaryKey(id, getSplitTableKey(dbShardVO));
    }


    @Override
    public TWmsReceiptHeaderEntity findByAsnId(Long asnId, DbShardVO dbShardVO) {
        return receiptHeaderMapper.selectByAsnId(asnId,getSplitTableKey(dbShardVO));
    }



    /**
     * 打印类组装
     * @param id 要打印入库单的id
     * @param dbShardVO
     * @return 返回打印类实例
     */
    @Override
    public TWmsReceiptHeaderPrintEntity findPrintEntityByPrimaryKey(Long id, DbShardVO dbShardVO) {
        TWmsReceiptHeaderEntity receiptHeaderEntity = findByPrimaryKey(id,dbShardVO);
        TWmsCargoOwnerEntity cargoOwnerEntity = cargoOwnerService.findCargoOwnerById(receiptHeaderEntity.getCargoOwnerId());
        TWmsReceiptHeaderPrintEntity receiptHeaderPrintEntity = new TWmsReceiptHeaderPrintEntity();
        receiptHeaderPrintEntity.setAddress(cargoOwnerEntity.getAddress());
        receiptHeaderPrintEntity.setContact(cargoOwnerEntity.getContact());
        receiptHeaderPrintEntity.setFullName(cargoOwnerEntity.getFullName());
        receiptHeaderPrintEntity.setTelephone(cargoOwnerEntity.getTelephone());
        receiptHeaderPrintEntity.setId(id.toString());
        receiptHeaderPrintEntity.setReceiptDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        receiptHeaderPrintEntity.setReceiptUser(receiptHeaderEntity.getReceiptUser());
        receiptHeaderPrintEntity.setCreateTableUser(receiptHeaderEntity.getUpdateUser());
        receiptHeaderPrintEntity.setReceiptType(receiptHeaderEntity.getReceiptTypeCode());
        receiptHeaderPrintEntity.setStartTime(receiptHeaderEntity.getReceiptDate() != null ?new SimpleDateFormat("yyyy-MM-dd").format(new Date(receiptHeaderEntity.getReceiptDate())):null);
        List<TWmsReceiptDetailEntity> list = receiptDetailService.getReceiptDetails(id,dbShardVO);
        Set set = new HashSet();
        if(list != null){
            for (TWmsReceiptDetailEntity receiptDetailEntity: list) {
                receiptDetailEntity.setUnitType(codeService.format(receiptDetailEntity.getUnitType(),"MasterUnit"));
                receiptDetailEntity.setProperty(codeService.format(receiptDetailEntity.getProperty(),"SkuProperty"));
                if(receiptDetailEntity.getStorageRoomId() !=null){
                    List<Long> ids = Arrays.stream(receiptDetailEntity.getStorageRoomId().split(",")).filter(StringUtils::isNotBlank).map(Long::parseLong).collect(Collectors.toList());
                    set.add(storageRoomService.getRoomNoByIds(ids).toString());
                }
            }
        }
        receiptHeaderPrintEntity.setRoomNo(set.toString().substring(1,set.toString().length()-1));
        receiptHeaderPrintEntity.setList(list);
        return receiptHeaderPrintEntity;
    }

    /**
     * 点击收货完成，根据通知明细单中数据创建入库单主表数据
     * @param receiptId
     * @param operationUser
     * @param qcHeaderDTO
     * @param asnHeaderDTO
     * @param dbShardVO
     * @return
     */
    @Override
    public MessageResult createReceiptHeaderFromQc(Long receiptId, String operationUser, TWmsQcHeaderDTO qcHeaderDTO, TWmsAsnHeaderDTO asnHeaderDTO, DbShardVO dbShardVO){
        TWmsReceiptHeaderEntity receiptHeaderEntity = new TWmsReceiptHeaderEntity();
        receiptHeaderEntity.setId(receiptId);//id
        receiptHeaderEntity.setWarehouseId(qcHeaderDTO.getWarehouseId());//仓库id
        receiptHeaderEntity.setTenantId(qcHeaderDTO.getTenantId()); //租户id
        receiptHeaderEntity.setAsnId(qcHeaderDTO.getAsnId());// 通知单主表id
        receiptHeaderEntity.setCargoOwnerId(asnHeaderDTO.getCargoOwnerId());//货主id
        receiptHeaderEntity.setDatasourceCode(asnHeaderDTO.getDatasourceCode());//数据来源
        receiptHeaderEntity.setReceiptTypeCode(asnHeaderDTO.getReceiptTypeCode());//入库类型
        receiptHeaderEntity.setFromTypeCode(asnHeaderDTO.getFromTypeCode());//单据来源
        receiptHeaderEntity.setTotalGrossWeight(asnHeaderDTO.getTotalGrossWeight());// 总毛重
        receiptHeaderEntity.setTotalNetWeight(asnHeaderDTO.getTotalNetWeight());//总净重
        receiptHeaderEntity.setTotalVolume(asnHeaderDTO.getTotalCube());//总体积
        receiptHeaderEntity.setStatusCode(TicketStatusCode.SUBMIT.toString());//生成即为提交状态
        receiptHeaderEntity.setReceiptStatusCode(ReceiptStatusCode.Receipting.toString());// 收货状态为收货中
        receiptHeaderEntity.setQcStatusCode(QualityStatus.INITIAL.toString());// 质检状态为未提交
        receiptHeaderEntity.setCreateUser(operationUser);
        receiptHeaderEntity.setCreateTime(new Date().getTime());
        receiptHeaderEntity.setUpdateUser(operationUser);
        receiptHeaderEntity.setUpdateTime(new Date().getTime());
        receiptHeaderMapper.insertReceiptHeader(receiptHeaderEntity,getSplitTableKey(dbShardVO));
        this.writeOperationLog(receiptId,receiptHeaderEntity.getCargoOwnerId(),ActionCode.Create.toString(),"创建入库单",OrderTypeCode.Receipt.toString(),OperationStatusCode.Finished.toString(),dbShardVO);
        return MessageResult.getSucMessage();
    }


    @Override
    @NeedLog(type = LogType.OPREATION)
    public MessageResult updateReceiptStatusFromQcFinish(Long asnId, String receiptStatusCode, String operationUser, DbShardVO dbShardVO) {
        TWmsReceiptHeaderEntity receiptHeaderEntity = receiptHeaderMapper.selectByAsnId(asnId,getSplitTableKey(dbShardVO));
        receiptHeaderEntity.setStatusCode(TicketStatusCode.FINISH.toString());//单据状态置为已完成
        receiptHeaderEntity.setReceiptStatusCode(receiptStatusCode);//收货状态
        receiptHeaderEntity.setReceiptUser(operationUser);//收货人
        receiptHeaderEntity.setQcInspector(operationUser);//质检负责人
        receiptHeaderEntity.setReceiptDate(new Date().getTime());//收货时间
        receiptHeaderEntity.setUpdateUser(operationUser); //最后更新人
        receiptHeaderEntity.setUpdateTime(new Date().getTime());//最后更新时间
        receiptHeaderMapper.updateReceiptHeader(receiptHeaderEntity,getSplitTableKey(dbShardVO));
        this.writeOperationLog(receiptHeaderEntity.getId(),receiptHeaderEntity.getCargoOwnerId(),ActionCode.QCFINISH.toString(),"入库单"+receiptHeaderEntity.getId().toString()+"收货完成",OrderTypeCode.Receipt.toString(),OperationStatusCode.Finished.toString(),dbShardVO);
        return MessageResult.getSucMessage();
    }


    @Override
    public List<TWmsReceiptHeaderEntity> getHeaderListByIds(List<Long> ids, DbShardVO dbShardVO) {
        return receiptHeaderMapper.getHeaderListByIds(ids,getSplitTableKey(dbShardVO));
    }

    @Override
    public List<TWmsReceiptHeaderEntity> getIdsByCargoOwnerId(Long cargoOwnerId, DbShardVO dbShardVO) {
        return receiptHeaderMapper.getIdsByCargoOwnerId(cargoOwnerId,getSplitTableKey(dbShardVO));
    }

    @Override
    public List<TWmsReportReceiptDetailDTO> getReceiptDetailList(Map map,DbShardVO dbShardVO) {
        map.put("splitTableKey",getSplitTableKey(dbShardVO));
        return receiptHeaderMapper.getReceiptDetailList(map);
    }

    @Override
    public Integer getReceiptDetailPageCount(Map map, DbShardVO dbShardVO) {
        map.put("splitTableKey",getSplitTableKey(dbShardVO));
        return receiptHeaderMapper.queryReceiptDetailPageCount(map);
    }

    @Override
    public List<TWmsReportReceiptDetailDTO> getReceiptSummaryList(Map map, DbShardVO dbShardVO) {
        map.put("splitTableKey",getSplitTableKey(dbShardVO));
        return receiptHeaderMapper.getReceiptSummaryList(map);
    }

    @Override
    public Integer getReceiptSummaryPageCount(Map map, DbShardVO dbShardVO) {
        map.put("splitTableKey",getSplitTableKey(dbShardVO));
        return receiptHeaderMapper.getReceiptSummaryPageCount(map);
    }

    /**
     * 入库确认
     * @param headerIds
     * @param operationUser
     * @param dbShardVO
     * @return
     */
    @Override
    @NeedLog(type = LogType.OPREATION)
    public MessageResult updateConfirmReceipt(Long[] headerIds, String operationUser, DbShardVO dbShardVO) {
        for (Long headerId:headerIds) {
            //判断参数是否为空
            if (headerId == null){
                return MessageResult.getMessage("E50001");
            }
            TWmsReceiptHeaderEntity receiptHeaderEntity  = receiptHeaderMapper.selectByPrimaryKey(headerId,getSplitTableKey(dbShardVO));
            List<TWmsReceiptDetailEntity> receiptDetailEntityList = receiptDetailService.getReceiptDetails(receiptHeaderEntity.getId(),dbShardVO);
            //判断入库单主表是否存在&是否包含明细单&主表状态是否为已完成
            if (receiptHeaderEntity == null){
                return MessageResult.getMessage("E50003",new Object[]{headerId});
            }
            if (CollectionUtils.isEmpty(receiptDetailEntityList)){
                return MessageResult.getMessage("E50002",new Object[]{headerId});
            }
            if (!receiptHeaderEntity.getStatusCode().equals(TicketStatusCode.FINISH.toString())){
                return MessageResult.getMessage("E50004",new Object[]{headerId});
            }
            receiptHeaderEntity.setStatusCode(TicketStatusCode.CONFIRM.toString());
            receiptHeaderEntity.setUpdateUser(operationUser);
            receiptHeaderEntity.setUpdateTime(new Date().getTime());
            //生成库存信息
            inventoryService.updateInventoryFromReceipt(receiptHeaderEntity,receiptDetailEntityList,operationUser,dbShardVO);
            receiptHeaderMapper.updateReceiptHeader(receiptHeaderEntity,getSplitTableKey(dbShardVO));
            this.writeOperationLog(headerId,receiptHeaderEntity.getCargoOwnerId(),ActionCode.Confirm.toString(),operationUser+"确认入库单",OrderTypeCode.Receipt.toString(),OperationStatusCode.Finished.toString(),dbShardVO);
        }
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult updateTransReceipt(Long[] headerIds, String operationUser, DbShardVO dbShardVO) {
        
        for(Long headerId: headerIds){
            TWmsReceiptHeaderEntity receiptHeaderEntity  = receiptHeaderMapper.selectByPrimaryKey(headerId,getSplitTableKey(dbShardVO));
            List<TWmsReceiptDetailEntity> receiptDetailEntityList = receiptDetailService.getReceiptDetails(headerId,dbShardVO);
            Map<Long,Long> map = new HashMap<Long,Long>();
            receiptDetailEntityList.stream().forEach(x->{
                map.put(x.getId(),x.getSkuId());
            });
            qcCheckService.updateQcCheckBatch(headerId,map,OrderTypeCode.Receipt.toString(),receiptHeaderEntity.getTenantId(),
                    receiptHeaderEntity.getWarehouseId(), operationUser,
                    changeInwhDbShareVO(dbShardVO));
            receiptHeaderEntity.setQcStatusCode(QualityStatus.DOING.toString());
            receiptHeaderMapper.updateReceiptHeader(receiptHeaderEntity,getSplitTableKey(dbShardVO));
        }
        return MessageResult.getSucMessage();
    }
}
