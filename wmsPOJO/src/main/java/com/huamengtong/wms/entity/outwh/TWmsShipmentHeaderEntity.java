package com.huamengtong.wms.entity.outwh;

import com.huamengtong.wms.dto.outwh.TWmsDnInvoiceDTO;
import com.huamengtong.wms.dto.outwh.TWmsSaleOrderDTO;
import com.huamengtong.wms.entity.po.BasePO;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by mario on 2016/10/31.
 */
public class TWmsShipmentHeaderEntity extends BasePO implements Serializable {

    private Long id;

    private Long tenantId;

    private Long warehouseId;

    private Long cargoOwnerId;

    private Long orderId;

    private Long dnId;

    private Long invoiceId;

    private String datasourceCode;

    private String fromtypeCode;

    private Long waveId;

    private String waveSeq;

    private String statusCode;

    private String referNo;

    private Long deliveryTime;

    private Integer totalCategoryQty;

    private Integer totalQty;

    private BigDecimal totalNetweight;

    private BigDecimal totalGrossweight;

    private BigDecimal totalVolume;

    private String expressName;

    private String expressNo;

    private String expressTypeCode;

    private String containerNo;

    private String picker;

    private Byte isPrintExpress;

    private Byte isPrintDelivery;

    private Byte isPrintPicking;

    private Byte isPrintInvoice;

    private String printExpressUser;

    private Long printExpressTime;

    private Byte isCancelled;

    private Byte isClosed;

    private BigDecimal totalWeight;

    private String allocateStatuscode;

    private String pickStatuscode;

    private String checkStatuscode;

    private String packageStatuscode;

    private String weightStatuscode;

    private String handoverStatuscode;

    private String deliveryStatuscode;

    private Byte isEwaybillFinished;

    private String logisticAreaName;

    private String defaultZoneNo;

    private String defaultLocationNo;

    private String cartongroupCode;

    private String remark;

    private String createUser;

    private Long createTime;

    private String updateUser;

    private Long updateTime;

    private Byte isDel;

    private String vehicleNo;

    private Byte isUrgent;

    private Byte isPrintsku;

    private String invoiceTypeCode;

    public String getInvoiceTypeCode() {
        return invoiceTypeCode;
    }

    public void setInvoiceTypeCode(String invoiceTypeCode) {
        this.invoiceTypeCode = invoiceTypeCode;
    }

    public Byte getIsUrgent() {
        return isUrgent;
    }

    public void setIsUrgent(Byte isUrgent) {
        this.isUrgent = isUrgent;
    }

    public Byte getIsPrintsku() {
        return isPrintsku;
    }

    public void setIsPrintsku(Byte isPrintsku) {
        this.isPrintsku = isPrintsku;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    private TWmsDnInvoiceDTO invoice;

    private TWmsSaleOrderDTO order;

    public TWmsDnInvoiceDTO getInvoice() {
        return invoice;
    }

    public void setInvoice(TWmsDnInvoiceDTO invoice) {
        this.invoice = invoice;
    }

    public TWmsSaleOrderDTO getOrder() {
        return order;
    }

    public void setOrder(TWmsSaleOrderDTO order) {
        this.order = order;
    }

    public Long getDnId() {
        return dnId;
    }

    public void setDnId(Long dnId) {
        this.dnId = dnId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Long getCargoOwnerId() {
        return cargoOwnerId;
    }

    public void setCargoOwnerId(Long cargoOwnerId) {
        this.cargoOwnerId = cargoOwnerId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getDatasourceCode() {
        return datasourceCode;
    }

    public void setDatasourceCode(String datasourceCode) {
        this.datasourceCode = datasourceCode;
    }

    public String getFromtypeCode() {
        return fromtypeCode;
    }

    public void setFromtypeCode(String fromtypeCode) {
        this.fromtypeCode = fromtypeCode;
    }

    public Long getWaveId() {
        return waveId;
    }

    public void setWaveId(Long waveId) {
        this.waveId = waveId;
    }

    public String getWaveSeq() {
        return waveSeq;
    }

    public void setWaveSeq(String waveSeq) {
        this.waveSeq = waveSeq;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getReferNo() {
        return referNo;
    }

    public void setReferNo(String referNo) {
        this.referNo = referNo;
    }

    public Long getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Long deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Integer getTotalCategoryQty() {
        return totalCategoryQty;
    }

    public void setTotalCategoryQty(Integer totalCategoryQty) {
        this.totalCategoryQty = totalCategoryQty;
    }

    public Integer getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(Integer totalQty) {
        this.totalQty = totalQty;
    }

    public BigDecimal getTotalNetweight() {
        return totalNetweight;
    }

    public void setTotalNetweight(BigDecimal totalNetweight) {
        this.totalNetweight = totalNetweight;
    }

    public BigDecimal getTotalGrossweight() {
        return totalGrossweight;
    }

    public void setTotalGrossweight(BigDecimal totalGrossweight) {
        this.totalGrossweight = totalGrossweight;
    }

    public BigDecimal getTotalVolume() {
        return totalVolume;
    }

    public void setTotalVolume(BigDecimal totalVolume) {
        this.totalVolume = totalVolume;
    }

    public String getExpressName() {
        return expressName;
    }

    public void setExpressName(String expressName) {
        this.expressName = expressName;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public String getExpressTypeCode() {
        return expressTypeCode;
    }

    public void setExpressTypeCode(String expressTypeCode) {
        this.expressTypeCode = expressTypeCode;
    }

    public String getContainerNo() {
        return containerNo;
    }

    public void setContainerNo(String containerNo) {
        this.containerNo = containerNo;
    }

    public String getPicker() {
        return picker;
    }

    public void setPicker(String picker) {
        this.picker = picker;
    }

    public Byte getIsPrintExpress() {
        return isPrintExpress;
    }

    public void setIsPrintExpress(Byte isPrintExpress) {
        this.isPrintExpress = isPrintExpress;
    }

    public Byte getIsPrintDelivery() {
        return isPrintDelivery;
    }

    public void setIsPrintDelivery(Byte isPrintDelivery) {
        this.isPrintDelivery = isPrintDelivery;
    }

    public Byte getIsPrintPicking() {
        return isPrintPicking;
    }

    public void setIsPrintPicking(Byte isPrintPicking) {
        this.isPrintPicking = isPrintPicking;
    }

    public Byte getIsPrintInvoice() {
        return isPrintInvoice;
    }

    public void setIsPrintInvoice(Byte isPrintInvoice) {
        this.isPrintInvoice = isPrintInvoice;
    }

    public String getPrintExpressUser() {
        return printExpressUser;
    }

    public void setPrintExpressUser(String printExpressUser) {
        this.printExpressUser = printExpressUser;
    }

    public Long getPrintExpressTime() {
        return printExpressTime;
    }

    public void setPrintExpressTime(Long printExpressTime) {
        this.printExpressTime = printExpressTime;
    }

    public Byte getIsCancelled() {
        return isCancelled;
    }

    public void setIsCancelled(Byte isCancelled) {
        this.isCancelled = isCancelled;
    }

    public Byte getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(Byte isClosed) {
        this.isClosed = isClosed;
    }

    public BigDecimal getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(BigDecimal totalWeight) {
        this.totalWeight = totalWeight;
    }

    public String getAllocateStatuscode() {
        return allocateStatuscode;
    }

    public void setAllocateStatuscode(String allocateStatuscode) {
        this.allocateStatuscode = allocateStatuscode;
    }

    public String getPickStatuscode() {
        return pickStatuscode;
    }

    public void setPickStatuscode(String pickStatuscode) {
        this.pickStatuscode = pickStatuscode;
    }

    public String getCheckStatuscode() {
        return checkStatuscode;
    }

    public void setCheckStatuscode(String checkStatuscode) {
        this.checkStatuscode = checkStatuscode;
    }

    public String getPackageStatuscode() {
        return packageStatuscode;
    }

    public void setPackageStatuscode(String packageStatuscode) {
        this.packageStatuscode = packageStatuscode;
    }

    public String getWeightStatuscode() {
        return weightStatuscode;
    }

    public void setWeightStatuscode(String weightStatuscode) {
        this.weightStatuscode = weightStatuscode;
    }

    public String getHandoverStatuscode() {
        return handoverStatuscode;
    }

    public void setHandoverStatuscode(String handoverStatuscode) {
        this.handoverStatuscode = handoverStatuscode;
    }

    public String getDeliveryStatuscode() {
        return deliveryStatuscode;
    }

    public void setDeliveryStatuscode(String deliveryStatuscode) {
        this.deliveryStatuscode = deliveryStatuscode;
    }

    public Byte getIsEwaybillFinished() {
        return isEwaybillFinished;
    }

    public void setIsEwaybillFinished(Byte isEwaybillFinished) {
        this.isEwaybillFinished = isEwaybillFinished;
    }

    public String getLogisticAreaName() {
        return logisticAreaName;
    }

    public void setLogisticAreaName(String logisticAreaName) {
        this.logisticAreaName = logisticAreaName;
    }

    public String getDefaultZoneNo() {
        return defaultZoneNo;
    }

    public void setDefaultZoneNo(String defaultZoneNo) {
        this.defaultZoneNo = defaultZoneNo;
    }

    public String getDefaultLocationNo() {
        return defaultLocationNo;
    }

    public void setDefaultLocationNo(String defaultLocationNo) {
        this.defaultLocationNo = defaultLocationNo;
    }

    public String getCartongroupCode() {
        return cartongroupCode;
    }

    public void setCartongroupCode(String cartongroupCode) {
        this.cartongroupCode = cartongroupCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Byte getIsDel() {
        return isDel;
    }

    public void setIsDel(Byte isDel) {
        this.isDel = isDel;
    }
}
