package com.huamengtong.wms.vo;

import com.huamengtong.wms.entity.po.BasePO;

import java.io.Serializable;
import java.math.BigDecimal;


public class FrozenInventoryVO extends BasePO implements Serializable {

    private Long skuId;

    private Long inventoryId;

    private Long cargoOwnerId;

    private Long storageRoomId;

    private String itemName;

    private String sku;

    private String barcode;

    private String spec;

    private String unitType;

    private BigDecimal costPrice;

    private Integer onhandQty;

    private Integer pickedQty;

    private Integer mortgagedQty;

    private Integer workingQty;

    private Integer mortgagedAbleQty;

    private String workGroupNo;

    private Byte isHold;



    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Long getCargoOwnerId() {
        return cargoOwnerId;
    }

    public void setCargoOwnerId(Long cargoOwnerId) {
        this.cargoOwnerId = cargoOwnerId;
    }

    public Long getStorageRoomId() {
        return storageRoomId;
    }

    public void setStorageRoomId(Long storageRoomId) {
        this.storageRoomId = storageRoomId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
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

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public Integer getOnhandQty() {
        return onhandQty;
    }

    public void setOnhandQty(Integer onhandQty) {
        this.onhandQty = onhandQty;
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

    public void setMortgagedAbleQty(Integer mortgagedAbleQty) {
        this.mortgagedAbleQty = mortgagedAbleQty;
    }

    public Integer getMortgagedAbleQty() {
        return mortgagedAbleQty;
    }

    public String getWorkGroupNo() {
        return workGroupNo;
    }

    public void setWorkGroupNo(String workGroupNo) {
        this.workGroupNo = workGroupNo;
    }


    public Byte getIsHold() {
        return isHold;
    }

    public void setIsHold(Byte isHold) {
        this.isHold = isHold;
    }
}
