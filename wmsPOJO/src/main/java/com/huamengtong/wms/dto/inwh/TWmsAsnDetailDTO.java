package com.huamengtong.wms.dto.inwh;

import com.huamengtong.wms.entity.po.BasePO;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by StickT on 2016/10/31.
 */
public class TWmsAsnDetailDTO extends BasePO implements Serializable {

    private Long id;


    private Long tenantId;


    private Long warehouseId;


    private Long asnId;


    private Long skuId;


    private String sku;


    private String skuName;


    private String skuBarcode;


    private String fromOrderNo;


    private String invoiceNo;


    private Integer expectedQty;


    private Integer adjustQty;


    private String containerNo;


    private Integer containerQty;


    private Integer receivedQty;


    private BigDecimal price;


    private Long lastReceiptTime;


    private String statusCode;


    private Byte isNeedQc;


    private String qcReferNo;


    private BigDecimal netWeight;


    private BigDecimal grossWeight;


    private BigDecimal volume;


    private String description;


    private String createUser;


    private Long createTime;


    private String updateUser;


    private Long updateTime;


    private String rejectReason;


    private Byte isReject;


    private Byte isDel;


    private BigDecimal qcQualifiedRate;


    private Integer qualifiedQty;


    private Integer unqualifiedQty;

    private String property;

    private String spec;

    private String unitType;

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

    public Long getAsnId() {
        return asnId;
    }

    public void setAsnId(Long asnId) {
        this.asnId = asnId;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getSkuBarcode() {
        return skuBarcode;
    }

    public void setSkuBarcode(String skuBarcode) {
        this.skuBarcode = skuBarcode;
    }

    public String getFromOrderNo() {
        return fromOrderNo;
    }

    public void setFromOrderNo(String fromOrderNo) {
        this.fromOrderNo = fromOrderNo;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public Integer getExpectedQty() {
        return expectedQty;
    }

    public void setExpectedQty(Integer expectedQty) {
        this.expectedQty = expectedQty;
    }

    public Integer getAdjustQty() {
        return adjustQty;
    }

    public void setAdjustQty(Integer adjustQty) {
        this.adjustQty = adjustQty;
    }

    public String getContainerNo() {
        return containerNo;
    }

    public void setContainerNo(String containerNo) {
        this.containerNo = containerNo;
    }

    public Integer getContainerQty() {
        return containerQty;
    }

    public void setContainerQty(Integer containerQty) {
        this.containerQty = containerQty;
    }

    public Integer getReceivedQty() {
        return receivedQty;
    }

    public void setReceivedQty(Integer receivedQty) {
        this.receivedQty = receivedQty;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getLastReceiptTime() {
        return lastReceiptTime;
    }

    public void setLastReceiptTime(Long lastReceiptTime) {
        this.lastReceiptTime = lastReceiptTime;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public Byte getIsNeedQc() {
        return isNeedQc;
    }

    public void setIsNeedQc(Byte isNeedQc) {
        this.isNeedQc = isNeedQc;
    }

    public String getQcReferNo() {
        return qcReferNo;
    }

    public void setQcReferNo(String qcReferNo) {
        this.qcReferNo = qcReferNo;
    }

    public BigDecimal getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(BigDecimal netWeight) {
        this.netWeight = netWeight;
    }

    public BigDecimal getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(BigDecimal grossWeight) {
        this.grossWeight = grossWeight;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public Byte getIsReject() {
        return isReject;
    }

    public void setIsReject(Byte isReject) {
        this.isReject = isReject;
    }

    public Byte getIsDel() {
        return isDel;
    }

    public void setIsDel(Byte isDel) {
        this.isDel = isDel;
    }

    public BigDecimal getQcQualifiedRate() {
        return qcQualifiedRate;
    }

    public void setQcQualifiedRate(BigDecimal qcQualifiedRate) {
        this.qcQualifiedRate = qcQualifiedRate;
    }

    public Integer getQualifiedQty() {
        return qualifiedQty;
    }

    public void setQualifiedQty(Integer qualifiedQty) {
        this.qualifiedQty = qualifiedQty;
    }

    public Integer getUnqualifiedQty() {
        return unqualifiedQty;
    }

    public void setUnqualifiedQty(Integer unqualifiedQty) {
        this.unqualifiedQty = unqualifiedQty;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }
}
