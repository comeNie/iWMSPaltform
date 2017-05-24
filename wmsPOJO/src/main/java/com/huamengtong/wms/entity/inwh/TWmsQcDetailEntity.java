package com.huamengtong.wms.entity.inwh;

import com.huamengtong.wms.entity.po.BasePO;

import java.io.Serializable;
import java.math.BigDecimal;


public class TWmsQcDetailEntity extends BasePO implements Serializable{


    private Long id;


    private Long tenantId;


    private Long warehouseId;


    private Long qcId;


    private Long asnDetailId;


    private Long locationId;


    private String storageRoomId;


    private Long skuId;


    private String sku;


    private String skuName;


    private String skuBarcode;


    private Integer totalQty;


    private BigDecimal samplingRatio;


    private Integer qcQty;


    private Integer checkedQty;


    private Integer qualifiedQty;


    private Integer unqualifiedQty;


    private String description;


    private String statusCode;


    private String createUser;


    private Long createTime;


    private String updateUser;


    private Long updateTime;


    private Byte isDel;

    private String property;

    private String inventoryStatusCode;

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

    public Long getQcId() {
        return qcId;
    }

    public void setQcId(Long qcId) {
        this.qcId = qcId;
    }

    public Long getAsnDetailId() {
        return asnDetailId;
    }

    public void setAsnDetailId(Long asnDetailId) {
        this.asnDetailId = asnDetailId;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getStorageRoomId() {
        return storageRoomId;
    }

    public void setStorageRoomId(String storageRoomId) {
        this.storageRoomId = storageRoomId;
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

    public Integer getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(Integer totalQty) {
        this.totalQty = totalQty;
    }

    public BigDecimal getSamplingRatio() {
        return samplingRatio;
    }

    public void setSamplingRatio(BigDecimal samplingRatio) {
        this.samplingRatio = samplingRatio;
    }

    public Integer getQcQty() {
        return qcQty;
    }

    public void setQcQty(Integer qcQty) {
        this.qcQty = qcQty;
    }

    public Integer getCheckedQty() {
        return checkedQty;
    }

    public void setCheckedQty(Integer checkedQty) {
        this.checkedQty = checkedQty;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
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

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getInventoryStatusCode() {
        return inventoryStatusCode;
    }

    public void setInventoryStatusCode(String inventoryStatusCode) {
        this.inventoryStatusCode = inventoryStatusCode;
    }

    @Override
    public String toString() {
        return "TWmsQcDetailEntity{" +
                "id=" + id +
                ", tenantId=" + tenantId +
                ", warehouseId=" + warehouseId +
                ", qcId=" + qcId +
                ", asnDetailId=" + asnDetailId +
                ", locationId=" + locationId +
                ", storageRoomId=" + storageRoomId +
                ", skuId=" + skuId +
                ", sku='" + sku + '\'' +
                ", skuName='" + skuName + '\'' +
                ", skuBarcode='" + skuBarcode + '\'' +
                ", totalQty=" + totalQty +
                ", samplingRatio=" + samplingRatio +
                ", qcQty=" + qcQty +
                ", checkedQty=" + checkedQty +
                ", qualifiedQty=" + qualifiedQty +
                ", unqualifiedQty=" + unqualifiedQty +
                ", description='" + description + '\'' +
                ", statusCode='" + statusCode + '\'' +
                ", createUser='" + createUser + '\'' +
                ", createTime=" + createTime +
                ", updateUser='" + updateUser + '\'' +
                ", updateTime=" + updateTime +
                ", isDel=" + isDel +
                ", property='" + property + '\'' +
                ", inventoryStatusCode='" + inventoryStatusCode + '\'' +
                '}';
    }
}
