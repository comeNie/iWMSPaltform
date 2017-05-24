package com.huamengtong.wms.domain;

import java.io.Serializable;

public class TWmsInventoryDomain implements Serializable{
    private Long id;
    private Long storageRoomId;
    private String storageRoomName;
    private Integer allocatedQty;
    private Integer pickedQty;
    private String createUser;
    private Long createTime;
    private String sku;
    private Long skuId;
    private String skuBarCode;
    private String skuItemName;
    private String cargoOwner;

    public String getSkuBarCode() {
        return skuBarCode;
    }

    public void setSkuBarCode(String skuBarCode) {
        this.skuBarCode = skuBarCode;
    }

    public String getStorageRoomName() {
        return storageRoomName;
    }

    public void setStorageRoomName(String storageRoomName) {
        this.storageRoomName = storageRoomName;
    }

    public String getCargoOwner() {
        return cargoOwner;
    }

    public void setCargoOwner(String cargoOwner) {
        this.cargoOwner = cargoOwner;
    }

    public String getSku() {
        return sku;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStorageRoomId() {
        return storageRoomId;
    }

    public void setStorageRoomId(Long storageRoomId) {
        this.storageRoomId = storageRoomId;
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

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getSkuItemName() {
        return skuItemName;
    }

    public void setSkuItemName(String skuItemName) {
        this.skuItemName = skuItemName;
    }
}
