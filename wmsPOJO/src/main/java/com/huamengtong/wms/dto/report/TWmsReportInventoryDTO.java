package com.huamengtong.wms.dto.report;

import com.huamengtong.wms.entity.po.BasePO;

import java.io.Serializable;

/**
 * Created by Edwin on 2016/12/7.
 */
public class TWmsReportInventoryDTO extends BasePO implements Serializable {
    private Long id;
    private Long cargoOwnerId;
    private Long storageRoomId;
    private Long warehouseId;
    private Long skuId;
    private String sku;
    private String skuBarcode;
    private String skuName;
    private String spec;
    private String inventoryStatusCode;
    private Integer onhandQty;
    private Integer activeQty;
    private Integer allocatedQty;
    private Integer pickedQty;
    private Integer mortgagedQty;
    private Integer workingQty;
    private Integer packageQty;
    private Long receiptTime;
    private Byte isHold;
    private Long createTime;
    private String updateUser;
    private Long updateTime;
    private Long inventoryCreateTimeFrom;
    private String unitType;

    public Integer getPackageQty() {
        return packageQty;
    }

    public void setPackageQty(Integer packageQty) {
        this.packageQty = packageQty;
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

    public Long getInventoryCreateTimeFrom() {
        return inventoryCreateTimeFrom;
    }

    public void setInventoryCreateTimeFrom(Long inventoryCreateTimeFrom) {
        this.inventoryCreateTimeFrom = inventoryCreateTimeFrom;
    }

    public Long getInventoryCreateTimeTo() {
        return inventoryCreateTimeTo;
    }

    public void setInventoryCreateTimeTo(Long inventoryCreateTimeTo) {
        this.inventoryCreateTimeTo = inventoryCreateTimeTo;
    }

    private Long inventoryCreateTimeTo;

    public Long getStorageRoomId() {
        return storageRoomId;
    }

    public void setStorageRoomId(Long storageRoomId) {
        this.storageRoomId = storageRoomId;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getSkuBarcode() {
        return skuBarcode;
    }

    public void setSkuBarcode(String skuBarcode) {
        this.skuBarcode = skuBarcode;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getInventoryStatusCode() {
        return inventoryStatusCode;
    }

    public void setInventoryStatusCode(String inventoryStatusCode) {
        this.inventoryStatusCode = inventoryStatusCode;
    }

    public Integer getOnhandQty() {
        return onhandQty;
    }

    public void setOnhandQty(Integer onhandQty) {
        this.onhandQty = onhandQty;
    }

    public Integer getActiveQty() {
        return activeQty;
    }

    public void setActiveQty(Integer activeQty) {
        this.activeQty = activeQty;
    }

    public Integer getAllocatedQty() {
        return allocatedQty;
    }

    public void setAllocatedQty(Integer allocatedQty) {
        this.allocatedQty = allocatedQty;
    }

    public Integer getPickedQty() {
        return pickedQty;
    }

    public void setPickedQty(Integer pickedQty) {
        this.pickedQty = pickedQty;
    }

    public Integer getMortgagedQty() {
        return mortgagedQty;
    }

    public void setMortgagedQty(Integer mortgagedQty) {
        this.mortgagedQty = mortgagedQty;
    }

    public Integer getWorkingQty() {
        return workingQty;
    }

    public void setWorkingQty(Integer workingQty) {
        this.workingQty = workingQty;
    }

    public Long getReceiptTime() {
        return receiptTime;
    }

    public void setReceiptTime(Long receiptTime) {
        this.receiptTime = receiptTime;
    }

    public Byte getIsHold() {
        return isHold;
    }

    public void setIsHold(Byte isHold) {
        this.isHold = isHold;
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


    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

}
