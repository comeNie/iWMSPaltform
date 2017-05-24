package com.huamengtong.wms.dto.sku;

import com.huamengtong.wms.entity.po.BasePO;

import java.io.Serializable;
import java.math.BigDecimal;

public class TWmsSkuDTO extends BasePO implements Serializable {

    private Long id;

    private Long tenantId;

    private Long cargoOwnerId;

    private String sku;

    private String upc;

    private String barcode;

    private String itemName;

    private Long categorysId;

    private String spec;

    private String unitType;

    private BigDecimal netWeight;

    private BigDecimal grossWeight;

    private BigDecimal volume;

    private  BigDecimal warehouseTemp;

    private BigDecimal transportTemp;

    private BigDecimal costPrice;

    private Byte isActive;

    private Byte isDel;

    private String datasourceCode;

    private String producer;

    private String producerCode;

    private String originPlace;

    private BigDecimal price1;

    private BigDecimal price2;

    private BigDecimal price3;

    private String description;

    private String createUser;

    private Long createTime;

    private String updateUser;

    private Long updateTime;


    private String property;

    private String cargoOwnerBarcode;

    public String getProperty() {
            return property;
        }

    public void setProperty(String property) {
            this.property = property;
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

    public Long getCargoOwnerId() {
        return cargoOwnerId;
    }

    public void setCargoOwnerId(Long cargoOwnerId) {
        this.cargoOwnerId = cargoOwnerId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
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

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
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

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public Byte getIsActive() {
        return isActive;
    }

    public void setIsActive(Byte isActive) {
        this.isActive = isActive;
    }

    public Byte getIsDel() {
        return isDel;
    }

    public void setIsDel(Byte isDel) {
        this.isDel = isDel;
    }

    public String getDatasourceCode() {
        return datasourceCode;
    }

    public void setDatasourceCode(String datasourceCode) {
        this.datasourceCode = datasourceCode;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getProducerCode() {
        return producerCode;
    }

    public void setProducerCode(String producerCode) {
        this.producerCode = producerCode;
    }

    public String getOriginPlace() {
        return originPlace;
    }

    public void setOriginPlace(String originPlace) {
        this.originPlace = originPlace;
    }

    public BigDecimal getPrice1() {
        return price1;
    }

    public void setPrice1(BigDecimal price1) {
        this.price1 = price1;
    }

    public BigDecimal getPrice2() {
        return price2;
    }

    public void setPrice2(BigDecimal price2) {
        this.price2 = price2;
    }

    public BigDecimal getPrice3() {
        return price3;
    }

    public void setPrice3(BigDecimal price3) {
        this.price3 = price3;
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

    public String getCargoOwnerBarcode() {
        return cargoOwnerBarcode;
    }

    public void setCargoOwnerBarcode(String cargoOwnerBarcode) {
        this.cargoOwnerBarcode = cargoOwnerBarcode;
    }
}
