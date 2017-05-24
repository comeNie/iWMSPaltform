package com.huamengtong.wms.entity.inwh;

import com.huamengtong.wms.entity.po.BasePO;

import java.math.BigDecimal;

/**
 * Created by mario on 2016/11/16.
 */
public class TWmsAdjustmentDetailEntity extends BasePO{

    private Long id;

    private Long adjustId;

    private Long tenantId;

    private String referDetailNo;

    private Long zoneId;

    private Long locationId;

    private Long storageRoomId;

    private String palletNo;

    private String containerNo;

    private Long skuId;

    private String sku;

    private String skuName;

    private String barcode;

    private Integer adjustedBeforeQty;

    private Integer adjustedAfterQty;

    private Integer adjustedQty;

    private BigDecimal adjustedBeforePrice;

    private BigDecimal adjustedAfterPrice;

    private String beforeStatusCode;

    private String afterStatusCode;

    private String description;

    private String createUser;

    private Long createTime;

    private String updateUser;

    private Long updateTime;

    private String statusCode;

    private Byte isDel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAdjustId() {
        return adjustId;
    }

    public void setAdjustId(Long adjustId) {
        this.adjustId = adjustId;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getReferDetailNo() {
        return referDetailNo;
    }

    public void setReferDetailNo(String referDetailNo) {
        this.referDetailNo = referDetailNo;
    }

    public Long getZoneId() {
        return zoneId;
    }

    public void setZoneId(Long zoneId) {
        this.zoneId = zoneId;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Long getStorageRoomId() {
        return storageRoomId;
    }

    public void setStorageRoomId(Long storageRoomId) {
        this.storageRoomId = storageRoomId;
    }

    public String getPalletNo() {
        return palletNo;
    }

    public void setPalletNo(String palletNo) {
        this.palletNo = palletNo;
    }

    public String getContainerNo() {
        return containerNo;
    }

    public void setContainerNo(String containerNo) {
        this.containerNo = containerNo;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Integer getAdjustedBeforeQty() {
        return adjustedBeforeQty;
    }

    public void setAdjustedBeforeQty(Integer adjustedBeforeQty) {
        this.adjustedBeforeQty = adjustedBeforeQty;
    }

    public Integer getAdjustedAfterQty() {
        return adjustedAfterQty;
    }

    public void setAdjustedAfterQty(Integer adjustedAfterQty) {
        this.adjustedAfterQty = adjustedAfterQty;
    }

    public Integer getAdjustedQty() {
        return adjustedQty;
    }

    public void setAdjustedQty(Integer adjustedQty) {
        this.adjustedQty = adjustedQty;
    }

    public BigDecimal getAdjustedBeforePrice() {
        return adjustedBeforePrice;
    }

    public void setAdjustedBeforePrice(BigDecimal adjustedBeforePrice) {
        this.adjustedBeforePrice = adjustedBeforePrice;
    }

    public BigDecimal getAdjustedAfterPrice() {
        return adjustedAfterPrice;
    }

    public void setAdjustedAfterPrice(BigDecimal adjustedAfterPrice) {
        this.adjustedAfterPrice = adjustedAfterPrice;
    }

    public String getBeforeStatusCode() {
        return beforeStatusCode;
    }

    public void setBeforeStatusCode(String beforeStatusCode) {
        this.beforeStatusCode = beforeStatusCode;
    }

    public String getAfterStatusCode() {
        return afterStatusCode;
    }

    public void setAfterStatusCode(String afterStatusCode) {
        this.afterStatusCode = afterStatusCode;
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

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public Byte getIsDel() {
        return isDel;
    }

    public void setIsDel(Byte isDel) {
        this.isDel = isDel;
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

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    @Override
    public String toString() {
        return "TWmsAdjustmentDetailEntity{" +
                "id=" + id +
                ", adjustId=" + adjustId +
                ", tenantId=" + tenantId +
                ", referDetailNo='" + referDetailNo + '\'' +
                ", zoneId=" + zoneId +
                ", locationId=" + locationId +
                ", palletNo='" + palletNo + '\'' +
                ", containerNo='" + containerNo + '\'' +
                ", skuId=" + skuId +
                ", adjustedBeforeQty=" + adjustedBeforeQty +
                ", adjustedAfterQty=" + adjustedAfterQty +
                ", adjustedQty=" + adjustedQty +
                ", adjustedBeforePrice=" + adjustedBeforePrice +
                ", adjustedAfterPrice=" + adjustedAfterPrice +
                ", beforeStatusCode='" + beforeStatusCode + '\'' +
                ", afterStatusCode='" + afterStatusCode + '\'' +
                ", description='" + description + '\'' +
                ", createUser='" + createUser + '\'' +
                ", createTime=" + createTime +
                ", updateUser='" + updateUser + '\'' +
                ", updateTime=" + updateTime +
                ", statusCode='" + statusCode + '\'' +
                ", isDel=" + isDel +
                '}';
    }
}
