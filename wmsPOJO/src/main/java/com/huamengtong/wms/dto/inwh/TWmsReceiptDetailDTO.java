package com.huamengtong.wms.dto.inwh;


import com.huamengtong.wms.entity.po.BasePO;

import java.io.Serializable;
import java.math.BigDecimal;


public class TWmsReceiptDetailDTO extends BasePO implements Serializable{

    private Long id;

    private Long receiptId;

    private Long tenantId;

    private Long warehouseId;

    private Long asnDetailId;

    private Long skuId;

    private String sku;

    private String skuName;

    private String skuBarcode;

    private String workGroupNo;

    private Long locationId;

    private String storageRoomId;

    private String palletNo;

    private String containerNo;

    private Integer receivedQty;

    private Long receiptTime;

    private BigDecimal netWeight;

    private BigDecimal grossWeight;

    private BigDecimal volume;

    private Byte isDel;

    private String inventoryStatusCode;

    private String description;

    private BigDecimal costPrice;

    private BigDecimal totalPrice;

    private Long categorysId;

    private String spec;

    private String property;

    private String unitType;

    private BigDecimal warehouseTemp;

    private BigDecimal transportTemp;

    private String createUser;

    private Long createTime;

    private String updateUser;

    private Long updateTime;

    private Integer version;

    private Long receiptStartTime;

    private Long receiptEndTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(Long receiptId) {
        this.receiptId = receiptId;
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

    public Long getAsnDetailId() {
        return asnDetailId;
    }

    public void setAsnDetailId(Long asnDetailId) {
        this.asnDetailId = asnDetailId;
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

    public String getWorkGroupNo() {
        return workGroupNo;
    }

    public void setWorkGroupNo(String workGroupNo) {
        this.workGroupNo = workGroupNo;
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

    public Integer getReceivedQty() {
        return receivedQty;
    }

    public void setReceivedQty(Integer receivedQty) {
        this.receivedQty = receivedQty;
    }

    public Long getReceiptTime() {
        return receiptTime;
    }

    public void setReceiptTime(Long receiptTime) {
        this.receiptTime = receiptTime;
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

    public Byte getIsDel() {
        return isDel;
    }

    public void setIsDel(Byte isDel) {
        this.isDel = isDel;
    }

    public String getInventoryStatusCode() {
        return inventoryStatusCode;
    }

    public void setInventoryStatusCode(String inventoryStatusCode) {
        this.inventoryStatusCode = inventoryStatusCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        this.spec = spec;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public BigDecimal getWarehouseTemp() {
        return warehouseTemp;
    }

    public void setWarehouseTemp(BigDecimal warehouseTemp) {
        this.warehouseTemp = warehouseTemp;
    }

    public BigDecimal getTransportTemp() {
        return transportTemp;
    }

    public void setTransportTemp(BigDecimal transportTemp) {
        this.transportTemp = transportTemp;
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Long getReceiptStartTime() {
        return receiptStartTime;
    }

    public void setReceiptStartTime(Long receiptStartTime) {
        this.receiptStartTime = receiptStartTime;
    }

    public Long getReceiptEndTime() {
        return receiptEndTime;
    }

    public void setReceiptEndTime(Long receiptEndTime) {
        this.receiptEndTime = receiptEndTime;
    }
}
