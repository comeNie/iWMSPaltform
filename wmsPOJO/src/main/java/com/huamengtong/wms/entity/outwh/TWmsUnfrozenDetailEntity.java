package com.huamengtong.wms.entity.outwh;

import com.huamengtong.wms.entity.po.BasePO;

import java.io.Serializable;
import java.math.BigDecimal;

public class TWmsUnfrozenDetailEntity extends BasePO implements Serializable {

    private Long id;

    private Long unfrozenId;

    private Long frozenDetailId;

    private Long tenantId;

    private Long warehouseId;

    private Long skuId;

    private String sku;

    private String skuName;

    private String skuBarcode;

    private String workGroupNo;

    private Long locationId;

    private Long storageRoomId;

    private String palletNo;

    private String containerNo;

    private Integer frozenedQty;

    private Integer unfrozenedQty;

    private Integer factoringQty;

    private BigDecimal netWeight;

    private BigDecimal grossWeight;

    private BigDecimal volume;

    private String description;

    private BigDecimal costPrice;

    private BigDecimal totalPrice;

    private Long categorysId;

    private String spec;

    private String factoringStatusCode;

    private String statusCode;

    private String unitType;

    private String createUser;

    private Long createTime;

    private String updateUser;

    private Long updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFrozenDetailId() {
        return frozenDetailId;
    }

    public void setFrozenDetailId(Long frozenDetailId) {
        this.frozenDetailId = frozenDetailId;
    }

    public Long getUnfrozenId() {
        return unfrozenId;
    }

    public void setUnfrozenId(Long unfrozenId) {
        this.unfrozenId = unfrozenId;
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
        this.sku = sku == null ? null : sku.trim();
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName == null ? null : skuName.trim();
    }

    public String getSkuBarcode() {
        return skuBarcode;
    }

    public void setSkuBarcode(String skuBarcode) {
        this.skuBarcode = skuBarcode == null ? null : skuBarcode.trim();
    }

    public String getWorkGroupNo() {
        return workGroupNo;
    }

    public void setWorkGroupNo(String workGroupNo) {
        this.workGroupNo = workGroupNo == null ? null : workGroupNo.trim();
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
        this.palletNo = palletNo == null ? null : palletNo.trim();
    }

    public String getContainerNo() {
        return containerNo;
    }

    public void setContainerNo(String containerNo) {
        this.containerNo = containerNo == null ? null : containerNo.trim();
    }

    public Integer getFrozenedQty() {
        return frozenedQty;
    }

    public void setFrozenedQty(Integer frozenedQty) {
        this.frozenedQty = frozenedQty;
    }

    public Integer getUnfrozenedQty() {
        return unfrozenedQty;
    }

    public void setUnfrozenedQty(Integer unfrozenedQty) {
        this.unfrozenedQty = unfrozenedQty;
    }

    public Integer getFactoringQty() {
        return factoringQty;
    }

    public void setFactoringQty(Integer factoringQty) {
        this.factoringQty = factoringQty;
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
        this.description = description == null ? null : description.trim();
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getCategorysId() {
        return categorysId;
    }

    public void setCategorysId(Long categorysId) {
        this.categorysId = categorysId;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec == null ? null : spec.trim();
    }

    public String getFactoringStatusCode() {
        return factoringStatusCode;
    }

    public void setFactoringStatusCode(String factoringStatusCode) {
        this.factoringStatusCode = factoringStatusCode;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType == null ? null : unitType.trim();
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
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
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "TWmsUnfrozenDetailEntity{" +
                "id=" + id +
                ", unfrozenId=" + unfrozenId +
                ", tenantId=" + tenantId +
                ", warehouseId=" + warehouseId +
                ", skuId=" + skuId +
                ", sku='" + sku + '\'' +
                ", skuName='" + skuName + '\'' +
                ", skuBarcode='" + skuBarcode + '\'' +
                ", workGroupNo='" + workGroupNo + '\'' +
                ", locationId=" + locationId +
                ", storageRoomId=" + storageRoomId +
                ", palletNo='" + palletNo + '\'' +
                ", containerNo='" + containerNo + '\'' +
                ", factoringQty=" + factoringQty +
                ", netWeight=" + netWeight +
                ", grossWeight=" + grossWeight +
                ", volume=" + volume +
                ", description='" + description + '\'' +
                ", costPrice=" + costPrice +
                ", totalPrice=" + totalPrice +
                ", categorysId=" + categorysId +
                ", spec='" + spec + '\'' +
                ", unitType='" + unitType + '\'' +
                ", createUser='" + createUser + '\'' +
                ", createTime=" + createTime +
                ", updateUser='" + updateUser + '\'' +
                ", updateTime=" + updateTime +
                '}';
    }
}