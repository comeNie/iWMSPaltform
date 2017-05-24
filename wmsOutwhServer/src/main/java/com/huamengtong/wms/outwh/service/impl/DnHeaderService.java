package com.huamengtong.wms.outwh.service.impl;

import com.huamengtong.wms.client.IAutoIdClient;
import com.huamengtong.wms.constants.AutoIdConstants;
import com.huamengtong.wms.core.annotation.NeedLog;
import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.service.BaseService;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.outwh.TWmsDnDetailDTO;
import com.huamengtong.wms.dto.outwh.TWmsDnHeaderDTO;
import com.huamengtong.wms.dto.outwh.TWmsDnInvoiceDTO;
import com.huamengtong.wms.dto.outwh.TWmsSaleOrderDTO;
import com.huamengtong.wms.dto.outwh.TWmsShipmentHeaderDTO;
import com.huamengtong.wms.em.ActionCode;
import com.huamengtong.wms.em.LogType;
import com.huamengtong.wms.em.OperationStatusCode;
import com.huamengtong.wms.em.OrderTypeCode;
import com.huamengtong.wms.em.TicketStatusCode;
import com.huamengtong.wms.entity.outwh.TWmsDnDetailEntity;
import com.huamengtong.wms.entity.outwh.TWmsDnHeaderEntity;
import com.huamengtong.wms.entity.outwh.TWmsDnInvoiceEntity;
import com.huamengtong.wms.entity.outwh.TWmsSaleOrderEntity;
import com.huamengtong.wms.entity.outwh.TWmsShipmentDetailEntity;
import com.huamengtong.wms.entity.sku.TWmsSkuCargoOwnerEntity;
import com.huamengtong.wms.entity.sku.TWmsSkuEntity;
import com.huamengtong.wms.main.service.ICargoOwnerService;
import com.huamengtong.wms.main.service.IStorageRoomService;
import com.huamengtong.wms.mq.producer.order.OrderProducer;
import com.huamengtong.wms.outwh.mapper.DnHeaderMapper;
import com.huamengtong.wms.outwh.service.IAllocateService;
import com.huamengtong.wms.outwh.service.IDnHeaderService;
import com.huamengtong.wms.outwh.service.IShipmentDetailService;
import com.huamengtong.wms.outwh.service.IShipmentHeaderService;
import com.huamengtong.wms.outwh.service.IShipmentService;
import com.huamengtong.wms.sku.service.ISkuCargoOwnerService;
import com.huamengtong.wms.sku.service.ISkuService;
import com.huamengtong.wms.utils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Date;


@Service
public class DnHeaderService extends BaseService implements IDnHeaderService {

    @Autowired
    DnHeaderMapper dnHeaderMapper;

    @Autowired
    DnDetailService dnDetailService;

    @Autowired
    IAutoIdClient autoIdClient;

    @Autowired
    IAllocateService allocateService;

    @Autowired
    SaleOrderService  saleOrderService;

    @Autowired
    DnInvoiceService dnInvoiceService;


    @Autowired
    OrderProducer orderProducer;

    @Autowired
    ICargoOwnerService cargoOwnerService;

    @Autowired
    IStorageRoomService storageRoomService;

    @Autowired
    IShipmentService shipmentService;

    @Autowired
    IShipmentHeaderService shipmentHeaderService;

    @Autowired
    IShipmentDetailService shipmentDetailService;

    @Autowired
    ISkuCargoOwnerService skuCargoOwnerService;

    @Autowired
    ISkuService skuService;

    @Override
    public PageResponse<List<TWmsDnHeaderEntity>> getDnHeader(TWmsDnHeaderDTO tWmsDnHeaderDTO, DbShardVO dbShardVO) {
        TWmsDnHeaderEntity DnHeaderEntity = BeanUtils.copyBeanPropertyUtils(tWmsDnHeaderDTO,TWmsDnHeaderEntity.class);
        List<TWmsDnHeaderEntity> dnHeaderEntityList = dnHeaderMapper.queryDnHeaderPages(DnHeaderEntity,getSplitTableKey(dbShardVO));
        if(CollectionUtils.isEmpty(dnHeaderEntityList)){
            return null;
        }
        List<Long> orderIdList = new ArrayList<>();
        List<Long> invoiceIdList = new ArrayList<>();
        for (TWmsDnHeaderEntity x:dnHeaderEntityList) {
            orderIdList.add(x.getOrderId());
            invoiceIdList.add(x.getInvoiceId());
        }
        List<TWmsSaleOrderEntity> saleOrderList = saleOrderService.findByPrimaryKey(orderIdList,dbShardVO);
        List<TWmsDnInvoiceEntity> invoiceList = dnInvoiceService.findByPrimaryKey(invoiceIdList,dbShardVO);
        for (TWmsDnHeaderEntity x:dnHeaderEntityList) {
            if( x!= null && x.getOrderId() > 0 && x.getInvoiceId() > 0){
                for (TWmsSaleOrderEntity s:saleOrderList) {
                    if(s.getId().equals(x.getOrderId())){
                        x.setIsUrgent(s.getIsUrgent());
                        x.setOrder(BeanUtils.copyBeanPropertyUtils(s,TWmsSaleOrderDTO.class));
                        saleOrderList.remove(s);
                        break;
                    }
                }
                for (TWmsDnInvoiceEntity i:invoiceList) {
                    if(i.getId().equals(x.getInvoiceId())) {
                        x.setIsPrintsku(i.getIsPrintsku());
                        x.setInvoiceTypeCode(i.getInvoiceTypeCode());
                        x.setInvoice(BeanUtils.copyBeanPropertyUtils(i,TWmsDnInvoiceDTO.class));
                        invoiceIdList.remove(i);
                        break;
                    }
                }
            }
        }
        Integer totalSize = dnHeaderMapper.queryDnHeaderPageCount(DnHeaderEntity,getSplitTableKey(dbShardVO));
        PageResponse<List<TWmsDnHeaderEntity>> response = new PageResponse();
        response.setRows(dnHeaderEntityList);
        response.setTotal(totalSize);
        return response;
    }


    @Override
    public PageResponse<List<TWmsDnHeaderEntity>> getDnHeaderList(TWmsDnHeaderDTO tWmsDnHeaderDTO, DbShardVO dbShardVO) {
        TWmsDnHeaderEntity dnHeaderEntity  = BeanUtils.copyBeanPropertyUtils(tWmsDnHeaderDTO,TWmsDnHeaderEntity.class);
        List<TWmsDnHeaderEntity> dnHeaderEntityList = dnHeaderMapper.queryDnHeaderPages(dnHeaderEntity,getSplitTableKey(dbShardVO));
        if(CollectionUtils.isEmpty(dnHeaderEntityList)){
            return null;
        }
        List<Long> orderIdList = new ArrayList<>();
        List<Long> invoiceIdList = new ArrayList<>();
        dnHeaderEntityList.stream()
                .forEach(x->{
                    orderIdList.add(x.getOrderId());
                    invoiceIdList.add(x.getInvoiceId());
                });
        List<TWmsSaleOrderEntity> saleOrderList = saleOrderService.findByPrimaryKey(orderIdList,dbShardVO);
        List<TWmsDnInvoiceEntity> invoiceList = dnInvoiceService.findByPrimaryKey(invoiceIdList,dbShardVO);
        Map<Long,TWmsSaleOrderDTO> orderMap = new HashMap<>();
        Map<Long,TWmsDnInvoiceDTO> invoiceMap = new HashMap<>();
        saleOrderList.stream().forEach(x->orderMap.put(x.getId(),BeanUtils.copyBeanPropertyUtils(x,TWmsSaleOrderDTO.class)));
        invoiceList.stream().forEach(x->invoiceMap.put(x.getId(),BeanUtils.copyBeanPropertyUtils(x,TWmsDnInvoiceDTO.class)));
        dnHeaderEntityList.stream().forEach(param-> {
            param.setOrder(orderMap.get(param.getOrderId()));
            param.setIsUrgent(orderMap.get(param.getOrderId()).getIsUrgent());
            param.setInvoice(invoiceMap.get(param.getInvoiceId()));
            param.setIsPrintsku(invoiceMap.get(param.getInvoiceId()).getIsPrintsku());
            param.setInvoiceTypeCode(invoiceMap.get(param.getInvoiceId()).getInvoiceTypeCode());
        });
        PageResponse<List<TWmsDnHeaderEntity>> response = new PageResponse<>();
        response.setRows(dnHeaderEntityList);
        response.setTotal(dnHeaderMapper.queryDnHeaderPageCount(dnHeaderEntity,getSplitTableKey(dbShardVO)));
        return response;
    }

    @Override
    public TWmsDnHeaderEntity findByPrimaryKey(Long id, DbShardVO dbShardVO) {
        return dnHeaderMapper.selectByPrimaryKey(id,getSplitTableKey(dbShardVO));
    }

    /**
     * 创建出库通知单
     * @param tWmsDnHeaderDTO
     * @param dbShardVO
     * @return
     */
    @Override
    @NeedLog( type = LogType.OPREATION)
    public MessageResult createDnHeader(TWmsDnHeaderDTO tWmsDnHeaderDTO, DbShardVO dbShardVO) {
        tWmsDnHeaderDTO.setStatusCode(TicketStatusCode.INIT.toString());
        if (tWmsDnHeaderDTO.getCargoOwnerId() == null || tWmsDnHeaderDTO.getCargoOwnerId() == 0){
            return  MessageResult.getMessage("E40001");
        }
        if(tWmsDnHeaderDTO.getWarehouseId() == null || tWmsDnHeaderDTO.getWarehouseId() == 0){
            return MessageResult.getMessage("E40002");
        }
        if(tWmsDnHeaderDTO.getCarrierId() == null || tWmsDnHeaderDTO.getCarrierId() == 0){
            return MessageResult.getMessage("E60017");
        }

        TWmsDnHeaderEntity dnHeaderEntity = BeanUtils.copyBeanPropertyUtils(tWmsDnHeaderDTO,TWmsDnHeaderEntity.class);

        Long dnHeaderId = autoIdClient.getAutoId(AutoIdConstants.TWmsDnHeaderEntity);
        dnHeaderEntity.setId(dnHeaderId);

        //订单信息
        TWmsSaleOrderDTO order = tWmsDnHeaderDTO.getOrder();
        order.setIsUrgent(tWmsDnHeaderDTO.getIsUrgent());
        Long orderId = saleOrderService.saveOrUpdateSaleOrder(order,dbShardVO);
        order.setId(orderId);

        //发票信息
        TWmsDnInvoiceDTO invoice = tWmsDnHeaderDTO.getInvoice();
        invoice.setIsPrintsku(tWmsDnHeaderDTO.getIsPrintsku());
        invoice.setInvoiceTypeCode(tWmsDnHeaderDTO.getInvoiceTypeCode());
        Long invoiceId = dnInvoiceService.saveOrUpdateDnInvoice(invoice,dbShardVO);
        invoice.setId(invoiceId);

        //绑定orderId和invoiceID
        dnHeaderEntity.setInvoiceId(invoiceId);
        dnHeaderEntity.setOrderId(orderId);
        dnHeaderMapper.insertDnHeader(dnHeaderEntity,getSplitTableKey(dbShardVO));
        this.writeOperationLog(dnHeaderId, dnHeaderEntity.getCargoOwnerId(),ActionCode.Create.toString(),null, OrderTypeCode.ShipmentDn.toString(),OperationStatusCode.Finished.toString(),dbShardVO);
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult removeDnHeader(Long id, DbShardVO dbShardVO) {
        TWmsDnHeaderEntity dnHeaderEntity = dnHeaderMapper.selectByPrimaryKey(id,getSplitTableKey(dbShardVO));
        saleOrderService.removeSaleOrder(dnHeaderEntity.getOrderId(),dbShardVO);
        dnInvoiceService.removeDnInvoice(dnHeaderEntity.getInvoiceId(),dbShardVO);
        dnDetailService.deleteDnDetailsByDnId(id,dbShardVO);
        dnHeaderMapper.deleteByPrimaryKey(id,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }

    /**
     * 更新通知单
     * @param tWmsDnHeaderDTO 通知单头信息
     * @param dbShardVO
     * @return
     */
    @Override
    public MessageResult modifyDnHeader(TWmsDnHeaderDTO tWmsDnHeaderDTO, DbShardVO dbShardVO) {
        //更新通知单
        TWmsDnHeaderEntity DnHeaderEntity = BeanUtils.copyBeanPropertyUtils(tWmsDnHeaderDTO,TWmsDnHeaderEntity.class);
        dnHeaderMapper.UpdateDnHeader(DnHeaderEntity,getSplitTableKey(dbShardVO));
        //更新订单表
        TWmsSaleOrderDTO saleOrderDTO = BeanUtils.copyBeanPropertyUtils(DnHeaderEntity.getOrder(),TWmsSaleOrderDTO.class);
        saleOrderDTO.setIsUrgent(tWmsDnHeaderDTO.getIsUrgent());
        saleOrderService.saveOrUpdateSaleOrder(saleOrderDTO,dbShardVO);
        //更新发票表
        TWmsDnInvoiceDTO invoiceDTO =  BeanUtils.copyBeanPropertyUtils(DnHeaderEntity.getInvoice(),TWmsDnInvoiceDTO.class);
        invoiceDTO.setInvoiceTypeCode(tWmsDnHeaderDTO.getInvoiceTypeCode());
        invoiceDTO.setIsPrintsku(tWmsDnHeaderDTO.getIsPrintsku());
        invoiceDTO.setId(tWmsDnHeaderDTO.getInvoiceId());
        invoiceDTO.setTenantId(tWmsDnHeaderDTO.getTenantId());
        dnInvoiceService.saveOrUpdateDnInvoice(invoiceDTO,dbShardVO);
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult removeByPrimaryKey(Long id, DbShardVO dbShardVO) {
        return  dnDetailService.removeByPrimaryKey(id,dbShardVO);
    }

    @Override
    public MessageResult createDnDetail(TWmsDnDetailDTO dnDetailDTO, DbShardVO dbShardVO) {
        return dnDetailService.createDnDetail(dnDetailDTO,dbShardVO);
    }

    @Override
    public MessageResult modifyDnDetail(TWmsDnDetailDTO dnDetailDTO, DbShardVO dbShardVO) {
        return dnDetailService.modifyDnDetail(dnDetailDTO,dbShardVO);
    }

    @Override
    public MessageResult batchDeleteDnHeaders(Long[] dnIds, String operationUser, DbShardVO dbShardVO) {

        if(ArrayUtils.isEmpty(dnIds)){
            return MessageResult.getMessage("E50001");
        }
        for(Long dnId : dnIds) {
            //check receiptHeader isNotEmpty
            TWmsDnHeaderEntity dnHeaderEntity = this.findByPrimaryKey(dnId, dbShardVO);
            if(!TicketStatusCode.INIT.toString().equals(dnHeaderEntity.getStatusCode())){
                return MessageResult.getMessage("E50005",new Object[]{dnId});
            }
            //批量删除出库单明细信息
            dnDetailService.batchDeleteDnDetails(dnIds,dbShardVO);
            //删除出库单头信息
            dnHeaderMapper.deleteByPrimaryKey(dnId,getSplitTableKey(dbShardVO));
        }
        return MessageResult.getSucMessage();
    }

    /**
     * 通知单提交生成出库单
     * @param tWmsDnHeaderDTO
     * @param dbShardVO
     * @return
     */
    @Override
    @NeedLog( type =LogType.OPREATION)
    public MessageResult updateDnToShipment(TWmsDnHeaderDTO tWmsDnHeaderDTO,String submitUser, DbShardVO dbShardVO) {
        Long dnId = tWmsDnHeaderDTO.getId();
        TWmsDnHeaderEntity dnHeaderEntity = dnHeaderMapper.selectByPrimaryKey(dnId,getSplitTableKey(dbShardVO));
        if (dnHeaderEntity.getCargoOwnerId() == null || dnHeaderEntity.getCargoOwnerId() == 0){
            return  MessageResult.getMessage("E40001");
        }
        //获取当前系统时间
        Long dataTime = new Date().getTime();
        //初始化byte型0
        Byte b = new Byte("0");
        //获取通知单详细
        List<TWmsDnDetailEntity> dnDetailsList = dnDetailService.getDnDetails(dnId,dbShardVO);
        if(CollectionUtils.isEmpty(dnDetailsList)){
            return MessageResult.getMessage("E60007",new Object[]{dnId});
        }
        //创建出库单明细集合
        List<TWmsShipmentDetailEntity> shipmentDetailList = new ArrayList();
        TWmsShipmentHeaderDTO shipmentHeaderDTO = new TWmsShipmentHeaderDTO();
        shipmentHeaderDTO.setId(autoIdClient.getAutoId(AutoIdConstants.TwmsShipmentHeaderEntity));

        //遍历出库通知单明细生成出库单明细
        TWmsSaleOrderEntity saleOrderEntity = saleOrderService.findByPrimaryKey(dnHeaderEntity.getOrderId(),dbShardVO);
        for (TWmsDnDetailEntity dnDetailEntity:dnDetailsList) {
            //在商品列表里查找是否拥有该商品
            TWmsSkuCargoOwnerEntity skuCargoOwnerEntity = skuCargoOwnerService
                    .selectBySkuIdAndCargoOwnerId(dnDetailEntity.getSkuId(),dnHeaderEntity.getCargoOwnerId(),getSkuDbShareVO(dbShardVO));
            if(skuCargoOwnerEntity == null){
                return MessageResult.getMessage("E60006",new Object[]{dnDetailEntity.getSkuName()});
            }
            TWmsSkuEntity skuEntity = skuService.findByPrimaryKey(dnDetailEntity.getSkuId(),getSkuDbShareVO(dbShardVO));
            TWmsShipmentDetailEntity shipmentDetailEntity = new TWmsShipmentDetailEntity();
            shipmentDetailEntity.setId(autoIdClient.getAutoId(AutoIdConstants.TwmsShipmentDetailEntity));
            shipmentDetailEntity.setTenantId(dnDetailEntity.getTenantId());
            shipmentDetailEntity.setWarehouseId(dnHeaderEntity.getWarehouseId());
            shipmentDetailEntity.setShipmentId(shipmentHeaderDTO.getId());
            shipmentDetailEntity.setDnDetailId(dnDetailEntity.getId());
            shipmentDetailEntity.setSaleOrderNo(dnHeaderEntity.getOrderId().toString());
            if(saleOrderEntity != null && saleOrderEntity.getReferNo() != null){
                shipmentDetailEntity.setReferNo(saleOrderEntity.getReferNo());
                shipmentHeaderDTO.setReferNo(saleOrderEntity.getReferNo());
            }
            shipmentDetailEntity.setSkuId(dnDetailEntity.getSkuId());
            shipmentDetailEntity.setSku(skuEntity.getSku());
            shipmentDetailEntity.setSkuName(dnDetailEntity.getSkuName());
            shipmentDetailEntity.setSkuBarcode(skuCargoOwnerEntity.getCargoOwnerBarcode());
            shipmentDetailEntity.setInventoryStatusCode(dnDetailEntity.getInventoryStatusCode());
            shipmentDetailEntity.setOrderedQty(dnDetailEntity.getQty());
            shipmentDetailEntity.setAmount(dnDetailEntity.getAmount());
            shipmentDetailEntity.setPrice(skuEntity.getCostPrice());
            shipmentDetailEntity.setDiscountAmount(dnDetailEntity.getDiscountFee());
            shipmentDetailEntity.setPromotionAmount(dnDetailEntity.getAdjustFee());
            shipmentDetailEntity.setActualPayment(dnDetailEntity.getPayment());
            shipmentDetailEntity.setIsGift(dnDetailEntity.getIsGift());
            shipmentDetailEntity.setStorageRoomId(dnDetailEntity.getStorageRoomId());
            if(skuEntity.getGrossWeight() != null){
                shipmentDetailEntity.setGrossWeight(skuEntity.getGrossWeight().multiply(new BigDecimal(dnDetailEntity.getQty())));
                shipmentHeaderDTO.setTotalGrossweight(shipmentHeaderDTO.getTotalGrossweight() == null?
                        shipmentDetailEntity.getGrossWeight() : shipmentHeaderDTO.getTotalGrossweight().add(shipmentDetailEntity.getGrossWeight()));
            }else{
                shipmentDetailEntity.setGrossWeight(new BigDecimal("0.00"));
            }
            if(skuEntity.getNetWeight() != null){
                shipmentDetailEntity.setNetWeight(skuEntity.getNetWeight().multiply(new BigDecimal(dnDetailEntity.getQty())));
                shipmentHeaderDTO.setTotalNetweight(shipmentHeaderDTO.getTotalNetweight() == null?
                        shipmentDetailEntity.getNetWeight() : shipmentHeaderDTO.getTotalNetweight().add(shipmentDetailEntity.getNetWeight()));
            }else{
                shipmentDetailEntity.setNetWeight(new BigDecimal("0.00"));
            }
            if(skuEntity.getVolume() != null){
                shipmentDetailEntity.setVolume(skuEntity.getVolume().multiply(new BigDecimal(dnDetailEntity.getQty())));
                shipmentHeaderDTO.setTotalVolume(shipmentHeaderDTO.getTotalVolume() == null?
                        shipmentDetailEntity.getVolume() : shipmentHeaderDTO.getTotalVolume().add(shipmentDetailEntity.getVolume()));
            }else{
                shipmentDetailEntity.setVolume(new BigDecimal("0.00"));
            }
            shipmentDetailEntity.setCreateUser(submitUser);
            shipmentDetailEntity.setUpdateUser(submitUser);
            shipmentDetailEntity.setUpdateTime(dataTime);
            shipmentDetailEntity.setCreateTime(dataTime);
            shipmentDetailEntity.setIsCanceled(b);
            shipmentDetailEntity.setIsDel(b);
            shipmentDetailEntity.setAllocatedQty(0);
            shipmentDetailEntity.setPickedQty(0);
            shipmentDetailEntity.setShippedQty(0);
            shipmentDetailList.add(shipmentDetailEntity);
            shipmentHeaderDTO.setTotalQty(shipmentHeaderDTO.getTotalQty() == null?dnDetailEntity.getQty():shipmentHeaderDTO.getTotalQty()+dnDetailEntity.getQty());
        }
        shipmentHeaderDTO.setTenantId(dnHeaderEntity.getTenantId());
        shipmentHeaderDTO.setWarehouseId(dnHeaderEntity.getWarehouseId());
        if(dnHeaderEntity.getCargoOwnerId() != null && dnHeaderEntity.getCargoOwnerId() != 0L){
            shipmentHeaderDTO.setCargoOwnerId(dnHeaderEntity.getCargoOwnerId());
        }
        shipmentHeaderDTO.setDnId(dnId);
        shipmentHeaderDTO.setOrderId(dnHeaderEntity.getOrderId());
        shipmentHeaderDTO.setInvoiceId(dnHeaderEntity.getInvoiceId());
        shipmentHeaderDTO.setDatasourceCode("System");
        shipmentHeaderDTO.setFromtypeCode(dnHeaderEntity.getFromtypeCode());
        shipmentHeaderDTO.setTotalCategoryQty(shipmentDetailList.size());
        shipmentHeaderDTO.setExpressName(dnHeaderEntity.getCarrierName());
        shipmentHeaderDTO.setIsPrintDelivery(b);
        shipmentHeaderDTO.setIsPrintExpress(b);
        shipmentHeaderDTO.setIsPrintInvoice(b);
        shipmentHeaderDTO.setIsPrintPicking(b);
        shipmentHeaderDTO.setCreateUser(submitUser);
        shipmentHeaderDTO.setUpdateUser(submitUser);
        shipmentHeaderDTO.setCreateTime(dataTime);
        shipmentHeaderDTO.setUpdateTime(dataTime);
        shipmentHeaderDTO.setIsDel(b);
        if(shipmentHeaderDTO.getTotalNetweight() == null){
            shipmentHeaderDTO.setTotalNetweight(new BigDecimal("0.00"));
        }
        if(shipmentHeaderDTO.getTotalGrossweight() == null){
            shipmentHeaderDTO.setTotalGrossweight(new BigDecimal("0.00"));
        }
        if(shipmentHeaderDTO.getTotalVolume() == null){
            shipmentHeaderDTO.setTotalVolume(new BigDecimal("0.00"));
        }
        //生成出库单头
        shipmentHeaderService.createShipmentHeader(shipmentHeaderDTO,dbShardVO);
        //生成出库单明细
        shipmentDetailService.batchcreateShipmentDetail(shipmentDetailList,dbShardVO);
        //更改通知单状态
        dnHeaderEntity.setStatusCode(TicketStatusCode.SUBMIT.toString());
        dnHeaderEntity.setUpdateTime(dataTime);
        dnHeaderEntity.setUpdateUser(submitUser);
        dnHeaderMapper.UpdateDnHeader(dnHeaderEntity,getSplitTableKey(dbShardVO));
        this.writeOperationLog(dnId,dnHeaderEntity.getCargoOwnerId(),ActionCode.Submit.toString(),submitUser+"创建通知单成功",OrderTypeCode.ShipmentDn.toString(),OperationStatusCode.Finished.toString(),dbShardVO);
        return MessageResult.getSucMessage();
    }



    /**
     * 根据通知单id查询相对应客户和发票的信息，
     * @param paramMap
     * @param dbShardVO
     * @return
     */
    @Override
    public Map queryDnBasics(Map paramMap, DbShardVO dbShardVO) {
        Map resultMap = new HashMap();
        paramMap.put("splitTableKey",getSplitTableKey(dbShardVO));
        Long dnHeaderId = MapUtils.getLong(paramMap,"dnHeaderId");
        TWmsDnHeaderEntity dnHeaderEntity = findByPrimaryKey(dnHeaderId,dbShardVO);
        if(dnHeaderEntity != null){
            TWmsSaleOrderEntity order = saleOrderService.findByPrimaryKey(dnHeaderEntity.getOrderId(),dbShardVO);
            TWmsDnInvoiceEntity invoice = dnInvoiceService.findByPrimaryKey(dnHeaderEntity.getInvoiceId(),dbShardVO);
            resultMap.put("order",order);
            resultMap.put("invoice",invoice);
        }
        return resultMap;
    }


}
