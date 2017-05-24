package com.huamengtong.wms.outwh.service.impl;

import com.huamengtong.wms.client.IAutoIdClient;
import com.huamengtong.wms.constants.AutoIdConstants;
import com.huamengtong.wms.core.annotation.NeedLog;
import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.service.BaseService;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.domain.TWmsInventoryDomain;
import com.huamengtong.wms.domain.TWmsShipmentHeaderPrintEntity;
import com.huamengtong.wms.dto.outwh.TWmsDnInvoiceDTO;
import com.huamengtong.wms.dto.outwh.TWmsSaleOrderDTO;
import com.huamengtong.wms.dto.outwh.TWmsShipmentHeaderDTO;
import com.huamengtong.wms.dto.outwh.TWmsWaveDTO;
import com.huamengtong.wms.em.ActionCode;
import com.huamengtong.wms.em.LogType;
import com.huamengtong.wms.em.OperationStatusCode;
import com.huamengtong.wms.em.OrderTypeCode;
import com.huamengtong.wms.em.TicketStatusCode;
import com.huamengtong.wms.entity.inventory.TWmsInventoryEntity;
import com.huamengtong.wms.entity.main.TWmsCargoOwnerEntity;
import com.huamengtong.wms.entity.main.TWmsStorageRoomEntity;
import com.huamengtong.wms.entity.outwh.TWmsAllocateEntity;
import com.huamengtong.wms.entity.outwh.TWmsDnInvoiceEntity;
import com.huamengtong.wms.entity.outwh.TWmsSaleOrderEntity;
import com.huamengtong.wms.entity.outwh.TWmsShipmentDetailEntity;
import com.huamengtong.wms.entity.outwh.TWmsShipmentHeaderEntity;
import com.huamengtong.wms.inventory.service.IInventoryService;
import com.huamengtong.wms.main.service.ICargoOwnerService;
import com.huamengtong.wms.main.service.IStorageRoomService;
import com.huamengtong.wms.mq.producer.order.OrderDeliverProducer;
import com.huamengtong.wms.mq.producer.order.OrderProducer;
import com.huamengtong.wms.outwh.mapper.ShipmentHeaderMapper;
import com.huamengtong.wms.outwh.service.IAllocateService;
import com.huamengtong.wms.outwh.service.IShipmentDetailService;
import com.huamengtong.wms.outwh.service.IShipmentHeaderService;
import com.huamengtong.wms.outwh.service.IShipmentService;
import com.huamengtong.wms.outwh.service.IWaveService;
import com.huamengtong.wms.utils.ArrayUtil;
import com.huamengtong.wms.utils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by mario on 2016/10/31.
 */
@Service
public class ShipmentHeaderService extends BaseService implements IShipmentHeaderService {

    @Autowired
    ShipmentHeaderMapper shipmentHeaderMapper;

    @Autowired
    IAutoIdClient autoIdClient;

    @Autowired
    IShipmentDetailService shipmentDetailService;

    @Autowired
    IShipmentService shipmentService;

    @Autowired
    IAllocateService allocateService;

    @Autowired
    SaleOrderService  saleOrderService;

    @Autowired
    DnInvoiceService dnInvoiceService;


    @Autowired
    OrderDeliverProducer orderDeliverProducer;

    @Autowired
    OrderProducer orderProducer;

    @Autowired
    ICargoOwnerService cargoOwnerService;

    @Autowired
    IStorageRoomService storageRoomService;

    @Autowired
    IWaveService waveService;

    @Autowired
    IInventoryService inventoryService;


    @Override
    public PageResponse<List<TWmsShipmentHeaderEntity>> getShipmentHeader(TWmsShipmentHeaderDTO shipmentHeaderDTO, DbShardVO dbShardVO) {
        TWmsShipmentHeaderEntity shipmentHeaderEntity = BeanUtils.copyBeanPropertyUtils(shipmentHeaderDTO,TWmsShipmentHeaderEntity.class);
        List<TWmsShipmentHeaderEntity> shipmentHeaderEntityList = shipmentHeaderMapper.queryShipmentHeaderPages(shipmentHeaderEntity,getSplitTableKey(dbShardVO));
        if(CollectionUtils.isEmpty(shipmentHeaderEntityList)){
            return null;
        }
        List<Long> orderIdList = new ArrayList<>();
        //发票信息暂时隐藏
        //List<Long> invoiceIdList = new ArrayList<>();
        for (TWmsShipmentHeaderEntity x:shipmentHeaderEntityList) {
            orderIdList.add(x.getOrderId());
            //invoiceIdList.add(x.getInvoiceId());
        }
        List<TWmsSaleOrderEntity> saleOrderList = saleOrderService.findByPrimaryKey(orderIdList,dbShardVO);
        //List<TWmsDnInvoiceEntity> invoiceList = dnInvoiceService.findByPrimaryKey(invoiceIdList,dbShardVO);
        for (TWmsShipmentHeaderEntity x:shipmentHeaderEntityList) {
            if(x.getDeliveryTime().equals(0L)){
                x.setDeliveryTime(null);
            }
            if( x!= null && x.getOrderId() > 0 && x.getInvoiceId() > 0){
                for (TWmsSaleOrderEntity s:saleOrderList) {
                    if(s.getId().equals(x.getOrderId())){
                        x.setIsUrgent(s.getIsUrgent());
                        x.setOrder(BeanUtils.copyBeanPropertyUtils(s,TWmsSaleOrderDTO.class));
                        saleOrderList.remove(s);
                        break;
                    }
                }
//                for (TWmsDnInvoiceEntity i:invoiceList) {
//                    if(i.getId().equals(x.getInvoiceId())) {
//                        x.setIsPrintsku(i.getIsPrintsku());
//                        x.setInvoiceTypeCode(i.getInvoiceTypeCode());
//                        x.setInvoice(BeanUtils.copyBeanPropertyUtils(i,TWmsDnInvoiceDTO.class));
//                        invoiceList.remove(x);
//                        break;
//                    }
//                }
            }
        }
        Integer totalSize = shipmentHeaderMapper.queryShipmentHeaderPageCount(shipmentHeaderEntity,getSplitTableKey(dbShardVO));
        PageResponse<List<TWmsShipmentHeaderEntity>> response = new PageResponse();
        response.setRows(shipmentHeaderEntityList);
        response.setTotal(totalSize);
        return response;
    }

    @Override
    public TWmsShipmentHeaderEntity findByPrimaryKey(Long id, DbShardVO dbShardVO) {
        return shipmentHeaderMapper.selectByPrimaryKey(id,getSplitTableKey(dbShardVO));
    }

    @Override
    @NeedLog(type = LogType.OPREATION)
    public MessageResult createShipmentHeader(TWmsShipmentHeaderDTO shipmentHeaderDTO, DbShardVO dbShardVO) {
        shipmentHeaderDTO.setStatusCode(TicketStatusCode.SUBMIT.toString());
        shipmentHeaderDTO.setDeliveryStatuscode(OperationStatusCode.Doing.toString());
        shipmentHeaderDTO.setAllocateStatuscode(OperationStatusCode.None.toString());
        shipmentHeaderDTO.setPickStatuscode(OperationStatusCode.None.toString());
        shipmentHeaderDTO.setCheckStatuscode(OperationStatusCode.None.toString());
        shipmentHeaderDTO.setWeightStatuscode(OperationStatusCode.None.toString());
        shipmentHeaderDTO.setHandoverStatuscode(OperationStatusCode.None.toString());
        shipmentHeaderDTO.setDeliveryStatuscode(OperationStatusCode.None.toString());

       TWmsShipmentHeaderEntity shipmentHeaderEntity = BeanUtils.copyBeanPropertyUtils(shipmentHeaderDTO,TWmsShipmentHeaderEntity.class);
        shipmentHeaderMapper.insertShipmentHeader(shipmentHeaderEntity,getSplitTableKey(dbShardVO));

        //记录操作日志
        this.writeOperationLog(shipmentHeaderEntity.getId(),shipmentHeaderEntity.getCargoOwnerId(),ActionCode.Create.toString(),"出库单创建",OrderTypeCode.Shipment.toString(),OperationStatusCode.Finished.toString(),dbShardVO);

        //执行提交动作，将信息入队列，进行库存分配处理
        Map shipmentMap = new HashMap();
        shipmentMap.put("shipmentId",shipmentHeaderEntity.getId());
        shipmentMap.put("tenantId",shipmentHeaderEntity.getTenantId());
        shipmentMap.put("warehouseId",shipmentHeaderEntity.getWarehouseId());
        shipmentMap.put("from", "出库单提交");
        orderProducer.sendOrder(shipmentMap,false);
        return MessageResult.getSucMessage();
    }

    @Override
    public Map queryShipmentBasics(Map paramMap, DbShardVO dbShardVO) {
        Map resultMap = new HashMap();
        paramMap.put("splitTableKey",getSplitTableKey(dbShardVO));
        Long shipmentId = MapUtils.getLong(paramMap,"shipmentId");
        TWmsShipmentHeaderEntity shipmentHeaderEntity = findByPrimaryKey(shipmentId,dbShardVO);
        if(shipmentHeaderEntity != null) {
            TWmsSaleOrderEntity order = saleOrderService.findByPrimaryKey(shipmentHeaderEntity.getOrderId(), dbShardVO);
            TWmsDnInvoiceEntity invoice = dnInvoiceService.findByPrimaryKey(shipmentHeaderEntity.getInvoiceId(), dbShardVO);
            resultMap.put("order",order);
            resultMap.put("invoice",invoice);
        }
        return  resultMap;
    }

    @Override
    public MessageResult removeShipmentHeader(Long id, DbShardVO dbShardVO) {
        shipmentHeaderMapper.deleteByPrimaryKey(id,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult modifyShipmentHeader(TWmsShipmentHeaderDTO shipmentHeaderDTO, DbShardVO dbShardVO) {
        TWmsShipmentHeaderEntity shipmentHeaderEntity=BeanUtils.copyBeanPropertyUtils(shipmentHeaderDTO,TWmsShipmentHeaderEntity.class);
        shipmentHeaderMapper.updateShipmentHeader(shipmentHeaderEntity,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }

    @Override
    @NeedLog(type = LogType.OPREATION)
    public MessageResult updateShipmentHeaderStatus(Long headerId, String statusCode, String operationUser, DbShardVO dbShardVO) {
       //记录操作日志
        MessageResult messageResult = null;
        if(headerId == null){
            return MessageResult.getMessage("E50001");
        }
        //check receiptHeader isNotEmpty
        TWmsShipmentHeaderEntity shipmentHeaderEntity = this.findByPrimaryKey(headerId,dbShardVO);
        if (shipmentHeaderEntity == null ){
            return MessageResult.getMessage("E50003",new Object[]{ headerId });
        }
        //check receiptDetail isNotEmpty
        if(checkshipmentDetail(headerId,dbShardVO)){
            return MessageResult.getMessage("E50002",new Object[]{ headerId });
        }
            //初始化状态才能执行"提交"动作
        if  (statusCode.equals(TicketStatusCode.SUBMIT.toString()) && TicketStatusCode.INIT.toString().equals(shipmentHeaderEntity.getStatusCode())){
            this.writeOperationLog(headerId,shipmentHeaderEntity.getCargoOwnerId(),ActionCode.Submit.toString(),"出库单提交",
                    OrderTypeCode.Shipment.toString(),OperationStatusCode.Finished.toString(),dbShardVO);
            shipmentHeaderEntity.setAllocateStatuscode(OperationStatusCode.Doing.toString());
            messageResult = shipmentSubmit(shipmentHeaderEntity,operationUser,dbShardVO);
            if(messageResult.isSuc()){
                //将信息入队列，进行库存分配处理
                Map shipmentMap = new HashMap();
                shipmentMap.put("shipmentId",shipmentHeaderEntity.getId());
                shipmentMap.put("tenantId",shipmentHeaderEntity.getTenantId());
                shipmentMap.put("warehouseId",shipmentHeaderEntity.getWarehouseId());
                shipmentMap.put("from", "出库单提交");
                orderProducer.sendOrder(shipmentMap,false);
            }
        }
            //提交状态才能执行"撤销"动作
        else if(statusCode.equals(TicketStatusCode.REPEALED.toString()) && TicketStatusCode.SUBMIT.toString().equals(shipmentHeaderEntity.getStatusCode())){
            this.writeOperationLog(headerId,shipmentHeaderEntity.getCargoOwnerId(),ActionCode.Repealed.toString(),"出库单撤销",
                    OrderTypeCode.Shipment.toString(),OperationStatusCode.Finished.toString(),dbShardVO);
            messageResult = shipmentService.updateShipmentRepealed(shipmentHeaderEntity,operationUser,dbShardVO);
        }else {
            return MessageResult.getMessage("E50004",new Object[]{ headerId });
        }
        return messageResult;
    }

    @Override
    public MessageResult batchDeleteShipmentHeaders(Long[] ShipmentIds, String operationUser, DbShardVO dbShardVO) {
        if (ArrayUtils.isEmpty(ShipmentIds))
        {
            return  MessageResult.getMessage("E60001");
        }
        for (Long headerId:ShipmentIds){
            //check receiptHeader isNotEmpty
            TWmsShipmentHeaderEntity shipmentHeaderEntity = this.findByPrimaryKey(headerId,dbShardVO);
            if (!TicketStatusCode.INIT.toString().equals(shipmentHeaderEntity.getStatusCode())){
                return MessageResult.getMessage("E60005");
            }
            //批量删除出库单明细
            shipmentDetailService.batchDeleteShipmentDetails(ShipmentIds,dbShardVO);
            //删除出库单头信息
            shipmentHeaderMapper.deleteByPrimaryKey(headerId,getSplitTableKey(dbShardVO));
        }
        return MessageResult.getSucMessage();
    }

    /****
     * 查询分配信息
     * @param paramMap
     * @param dbShardVO
     * @return
     */
    @Override
    public Map queryAllocateResult(Map paramMap, DbShardVO dbShardVO) {
        Map resultMap = new HashMap<>();
        List<TWmsAllocateEntity> allocateList = allocateService.searchAllocates(paramMap,dbShardVO);
        List<TWmsInventoryDomain> list = new ArrayList();
        if (CollectionUtils.isNotEmpty(allocateList)) {
            allocateList.stream().forEach(x->{
                TWmsShipmentDetailEntity shipmentDetailEntity = shipmentDetailService.findByPrimaryKey(x.getDetailId(),dbShardVO);
                TWmsInventoryDomain inventoryDomain = new TWmsInventoryDomain();
                inventoryDomain.setId(x.getId());
                TWmsInventoryEntity inventoryEntity = inventoryService.findById(x.getInventoryId(),changeInwhDbShareVO(dbShardVO));
                TWmsCargoOwnerEntity cargoOwnerEntity = cargoOwnerService.findCargoOwnerById(inventoryEntity.getCargoOwnerId());
                inventoryDomain.setCargoOwner(cargoOwnerEntity.getFullName());
                inventoryDomain.setAllocatedQty(x.getAllocatedQty());
                inventoryDomain.setCreateTime(x.getCreateTime());
                inventoryDomain.setCreateUser(x.getCreateUser());
                inventoryDomain.setPickedQty(x.getPickedQty());
                inventoryDomain.setSkuId(shipmentDetailEntity.getSkuId());
                inventoryDomain.setSku(shipmentDetailEntity.getSku());
                inventoryDomain.setSkuItemName(shipmentDetailEntity.getSkuName());
                inventoryDomain.setStorageRoomId(x.getStorageRoomId());
                TWmsStorageRoomEntity storageRoomEntity = storageRoomService.findByPrimaryKey(x.getStorageRoomId());
                inventoryDomain.setStorageRoomName(storageRoomEntity.getRoomNo());
                inventoryDomain.setSkuBarCode(shipmentDetailEntity.getSkuBarcode());
                list.add(inventoryDomain);
            });
            resultMap.put("total",allocateList.size());
            resultMap.put("rows", list);
        }
        return resultMap;
    }


    private boolean checkshipmentDetail(Long headerId,DbShardVO dbShardVO){
        List<TWmsShipmentDetailEntity> shipmentDetailEntityList =  shipmentDetailService.getShipmentDetails(headerId,dbShardVO);
        return CollectionUtils.isNotEmpty(shipmentDetailEntityList) ? false : true;
    }


    private MessageResult shipmentSubmit(TWmsShipmentHeaderEntity shipmentHeaderEntity,String operationUser, DbShardVO dbShardVO){
        shipmentHeaderEntity.setStatusCode(TicketStatusCode.SUBMIT.toString());
        shipmentHeaderEntity.setUpdateUser(operationUser);
        shipmentHeaderEntity.setUpdateTime(new Date().getTime());
        shipmentHeaderMapper.updateShipmentHeader(shipmentHeaderEntity,getSplitTableKey(dbShardVO));
        return  MessageResult.getSucMessage();
    }



    @Override
    public MessageResult modifyShipmentOrderAndInvoice(Long shipmentId, TWmsSaleOrderDTO order, TWmsDnInvoiceDTO invoice, DbShardVO dbShardVO) {
        TWmsShipmentHeaderEntity entity = shipmentHeaderMapper.selectByPrimaryKey(shipmentId,getSplitTableKey(dbShardVO));
        entity.setOrderId(order.getId());
        entity.setInvoiceId(invoice.getId());
        shipmentHeaderMapper.updateShipmentHeader(entity,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }


    @Override
    public MessageResult updateShipmentHeaderAllocateStatus(Long shipmentId, String allocateStatusCode, DbShardVO dbShardVO) {
        TWmsShipmentHeaderEntity shipmentHeaderEntity = new TWmsShipmentHeaderEntity();
        shipmentHeaderEntity.setId(shipmentId);
        shipmentHeaderEntity.setAllocateStatuscode(allocateStatusCode);
        shipmentHeaderMapper.updateShipmentHeader(shipmentHeaderEntity,getSplitTableKey(dbShardVO));
        //shipmentHeaderMapper.updateShipmentHeaderAllocateStatus(shipmentId,allocateStatusCode,getSplitTableKey(dbShardVO));
        return  MessageResult.getSucMessage();
    }

    /**
     * 出库单打印
     * @param id 头
     * @param dbShardVO
     * @return
     */
    @Override
    public TWmsShipmentHeaderPrintEntity findPrintEntityPrintShipment(Long id, DbShardVO dbShardVO) {
        TWmsShipmentHeaderPrintEntity shipmentHeaderPrintEntity = new TWmsShipmentHeaderPrintEntity();
        TWmsShipmentHeaderEntity shipmentHeaderEntity = findByPrimaryKey(id,dbShardVO);
        List<TWmsShipmentDetailEntity> list = shipmentDetailService.getShipmentDetails(id,dbShardVO);
        shipmentHeaderPrintEntity.setList(list);
        shipmentHeaderPrintEntity.setId(id);
        shipmentHeaderPrintEntity.setPrintDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        TWmsSaleOrderEntity saleOrderEntity = saleOrderService.findByPrimaryKey(shipmentHeaderEntity.getOrderId(),dbShardVO);
        shipmentHeaderPrintEntity.setTelephone(saleOrderEntity.getMobile());
        String receiptUser = saleOrderEntity.getReceiverName() + "  地址："+saleOrderEntity.getProvinceName()+
                saleOrderEntity.getCityName()+
                saleOrderEntity.getAreaName()+
                saleOrderEntity.getAddress() +"  联系电话："+saleOrderEntity.getMobile();
        shipmentHeaderPrintEntity.setReceiptUser(receiptUser);
        shipmentHeaderPrintEntity.setCarNo(shipmentHeaderEntity.getVehicleNo());
        shipmentHeaderPrintEntity.setOrderNo(saleOrderEntity.getId().toString());
        shipmentHeaderPrintEntity.setPlatformNumber(saleOrderEntity.getTradeNo());
        shipmentHeaderPrintEntity.setExpressName(shipmentHeaderEntity.getExpressName());
        Set set = new HashSet();
        if(CollectionUtils.isNotEmpty(list)){
            for(TWmsShipmentDetailEntity shipment : list) {
                if(shipment.getStorageRoomId() != null){
                    Long[] array = ArrayUtil.toLongArray(shipment.getStorageRoomId().split(","));
                    for(int i = 0 ; i < array.length;i++){
                        set.add(storageRoomService.findByPrimaryKey(array[i]).getRoomNo());
                    }
                }
            }
        }
        shipmentHeaderPrintEntity.setRoomNo(set.toString().substring(1,set.toString().length()-1));
        //shipmentHeaderPrintEntity.setPrintDate(shipmentHeaderEntity.getPrintExpressTime() == null? new SimpleDateFormat("yyyy-MM-dd").format(new Date()):new SimpleDateFormat("yyyy-MM-dd").format(shipmentHeaderEntity.getPrintExpressTime()));
        return shipmentHeaderPrintEntity;
    }

    /**
     * 发货交接
     * @param map map包含车号，承运商和出库单号
     * @param dbShardVO
     * @return
     */
    @Override
    @NeedLog(type = LogType.OPREATION)
    public MessageResult updateShipmentHeaderDelivery(Map map, DbShardVO dbShardVO) {
        MessageResult mr = MessageResult.getFailMessage();
        if(map == null || map.get("vehicleNo") == null ||map.get("vehicleNo").toString().equals("")){
            mr.setMessage("E60012");
            return mr;
        }else if(map.get("expressName") == null ||map.get("expressName").toString().equals("")){
            mr.setMessage("E60014");
            return mr;
        }else if(map.get("shipmentId") == null || map.get("shipmentId").toString().equals("")){
            mr.setMessage("E60013");
            return mr;
        }
        String shipmentId = map.get("shipmentId").toString();
        if(!shipmentId.matches("[0-9]+")){
            mr.setMessage("E60013");
            return mr;
        }
        Long id = Long.parseLong(shipmentId);
        String vehicleNo = map.get("vehicleNo").toString();
        String expressName = map.get("expressName").toString();
        String userName = map.get("userName").toString();
        TWmsShipmentHeaderEntity shipmentHeaderEntity = shipmentHeaderMapper.selectByPrimaryKey(id,getSplitTableKey(dbShardVO));
        if(shipmentHeaderEntity == null){
            mr.setMessage("E60003");
            return mr;
        }else if(!shipmentHeaderEntity.getExpressName().equals(expressName)){
            mr.setMessage("E60008");
            return mr;
        }else if(!shipmentHeaderEntity.getStatusCode().equals(TicketStatusCode.SUBMIT.toString())){
            mr.setMessage("E60011");
            return mr;
        } if(!shipmentHeaderEntity.getAllocateStatuscode().equals(OperationStatusCode.Finished.toString())){
            mr.setMessage("E60009");
            return mr;
        }else if(shipmentHeaderEntity.getHandoverStatuscode().equals(OperationStatusCode.Finished.toString())||
                shipmentHeaderEntity.getDeliveryStatuscode().equals(OperationStatusCode.Finished.toString())){
            mr.setMessage("E60010");
            return mr;
        }
        TWmsShipmentHeaderDTO shipmentHeaderDTO = new TWmsShipmentHeaderDTO();
        shipmentHeaderDTO.setId(id);
        shipmentHeaderDTO.setVehicleNo(vehicleNo);
        shipmentHeaderDTO.setExpressName(expressName);
        shipmentHeaderDTO.setUpdateUser(userName);
        shipmentHeaderDTO.setUpdateTime(new Date().getTime());

        //修改交接状态
        shipmentHeaderDTO.setHandoverStatuscode(OperationStatusCode.Finished.toString());
        shipmentHeaderDTO.setDeliveryTime(new Date().getTime());
        shipmentHeaderDTO.setExpressNo(new SimpleDateFormat("yyyyMMddHHmm").format(new Date())+
                autoIdClient.getAutoId(AutoIdConstants.TwmsShipmentHeaderEntity_Deliver));
        shipmentHeaderDTO.setDeliveryStatuscode(OperationStatusCode.Finished.toString());
        mr = modifyShipmentHeader(shipmentHeaderDTO,dbShardVO);
        if(mr.isSuc()){
            //将信息入队列，进行库存扣減处理
            Map shipmentMap = new HashMap();
            shipmentMap.put("shipmentId",shipmentHeaderEntity.getId());
            shipmentMap.put("tenantId",shipmentHeaderEntity.getTenantId());
            shipmentMap.put("warehouseId",shipmentHeaderEntity.getWarehouseId());
            shipmentMap.put("from", "出库单交接");
            orderDeliverProducer.sendOrder(shipmentMap,true);
        }
        Map resultMap = new HashMap();
        TWmsShipmentHeaderEntity shipmentHeaderEntityToSelect = new TWmsShipmentHeaderEntity();
        shipmentHeaderEntityToSelect.setExpressName(map.get("expressName").toString());
        shipmentHeaderEntityToSelect.setVehicleNo(map.get("vehicleNo").toString());
        List<TWmsShipmentHeaderEntity> list = shipmentHeaderMapper.queryShipmentHeaderPages(shipmentHeaderEntityToSelect,getSplitTableKey(dbShardVO));
        resultMap.put("rows",list);
        mr.setResult(resultMap);
        mr.setMessage("S60014");
        return mr;
    }

    @Override
    public TWmsShipmentHeaderPrintEntity findPrintEntityPrintPicking(Long id, DbShardVO dbShardVO) {
        TWmsShipmentHeaderPrintEntity shipmentHeaderPrintEntity = new TWmsShipmentHeaderPrintEntity();
        TWmsShipmentHeaderEntity shipmentHeaderEntity = findByPrimaryKey(id,dbShardVO);
        Map paramMap = new HashMap();
        paramMap.put("headerId",id);
        Map map = queryAllocateResult(paramMap,dbShardVO);
        shipmentHeaderPrintEntity.setList((List) map.get("rows"));
        shipmentHeaderPrintEntity.setId(id);
        shipmentHeaderPrintEntity.setPrintDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        TWmsCargoOwnerEntity cargoOwnerEntity = cargoOwnerService.findCargoOwnerById(shipmentHeaderEntity.getCargoOwnerId());
        shipmentHeaderPrintEntity.setTelephone(cargoOwnerEntity.getTelephone());
        shipmentHeaderPrintEntity.setFullName(cargoOwnerEntity.getFullName());
        shipmentHeaderPrintEntity.setContact(cargoOwnerEntity.getContact());
        return shipmentHeaderPrintEntity;
    }

    @Override
    public List<TWmsShipmentHeaderEntity> findOwnerIdByHeaderId(List<Long> list, DbShardVO dbShardVO) {
        return shipmentHeaderMapper.selectCargoOwnerIds(list,getSplitTableKey(dbShardVO));
    }
    @Override
    public List<TWmsShipmentHeaderEntity> findEntityBycargoOwnerId(Long cargoOwnerId, DbShardVO dbShardVO) {
        return shipmentHeaderMapper.selectByCargoOwnerId(cargoOwnerId,getSplitTableKey(dbShardVO));
    }


    /**
     * 出库单生成波次
     * @param ids 出库单ids
     * @param operationUser 修改人
     * @param dbShardVO
     * @return
     */
    @Override
    public MessageResult updateShipmentHeaderToWave(Long[] ids,Long tenantId,Long warehouseId, String operationUser, DbShardVO dbShardVO) {
        Long waveId = autoIdClient.getAutoId(AutoIdConstants.TWmsWaveEntity);
        TWmsWaveDTO waveDTO = new TWmsWaveDTO();
        waveDTO.setId(waveId);
        waveDTO.setTenantId(tenantId);
        waveDTO.setWarehouseId(warehouseId);
        waveDTO.setCargoOwnerId(2L);
        waveDTO.setTotalCategoryQty(ids.length);
        waveDTO.setTotalOrderQty(ids.length);
        waveDTO.setTotalQty(ids.length);
        waveDTO.setUpdateUser(operationUser);
        waveDTO.setCreateUser(operationUser);
        createWaveDTO(waveDTO);
        waveService.createWave(waveDTO,dbShardVO);
        for (int i = 0 ; i < ids.length ; i ++) {
            Map map = new HashMap();
            map.put("id",ids[i]);
            map.put("waveId",waveId);
            map.put("waveSeq",i+1);
            map.put("splitTableKey",getSplitTableKey(dbShardVO));
            shipmentHeaderMapper.updateShipmentHeaderWave(map);
        }
        return MessageResult.getSucMessage();
    }

    private void createWaveDTO(TWmsWaveDTO w){
        byte b = (byte) 0;
        w.setIsUrgent(0);
        w.setIsPrintPicking(b);
        w.setIsPrintExpress(b);
        w.setIsPrintDelivery(b);
        w.setIsPrintInvoice(b);
        w.setIsDel(b);
        w.setDatasourceCode("System");
        w.setFromTypeCode("System");
        w.setStatusCode("None");
        w.setUpdateTime(new Date().getTime());
        w.setCreateTime(new Date().getTime());
    }

    @Override
    public List<TWmsShipmentHeaderEntity> findShipmentIdsByWaveId(List<Long> list, DbShardVO dbShardVO) {
        return shipmentHeaderMapper.selectShipmentIdsByWaveId(list,getSplitTableKey(dbShardVO));
    }

}
