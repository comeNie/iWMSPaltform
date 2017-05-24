package com.huamengtong.wms.dto.inventory;

import com.huamengtong.wms.entity.po.BasePO;

import java.io.Serializable;

/**
 * Created by StickT on 2017/1/9.
 */
public class TWmsProInventoryDTO extends BasePO implements Serializable{
    private Long id;
    private Long tenantId;
    private Long warehouseId;
    private Long parentId;
    private Long inventoryId;
    private Long cargoOwnerId;
    private Long storageRoomId;
    private Long skuId;
    private String sku;
    private String spec;
    private Integer qty;
    private String unitType;
    private String workGroupNo;
    private String statusCode;
    private String createUser;
    private Long createTime;
    private String updateUser;
    private Long updateTime;
    private Long proSkuId;
    private String proSpec;
    private String proUnitType;
    private Integer proQty;
    private Long proStorageRoomId;

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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
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

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public String getWorkGroupNo() {
        return workGroupNo;
    }

    public void setWorkGroupNo(String workGroupNo) {
        this.workGroupNo = workGroupNo;
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

    public String getProSpec() {
        return proSpec;
    }

    public void setProSpec(String proSpec) {
        this.proSpec = proSpec;
    }

    public String getProUnitType() {
        return proUnitType;
    }

    public void setProUnitType(String proUnitType) {
        this.proUnitType = proUnitType;
    }

    public Integer getProQty() {
        return proQty;
    }

    public void setProQty(Integer proQty) {
        this.proQty = proQty;
    }

    public Long getProStorageRoomId() {
        return proStorageRoomId;
    }

    public void setProStorageRoomId(Long proStorageRoomId) {
        this.proStorageRoomId = proStorageRoomId;
    }

    public Long getProSkuId() {
        return proSkuId;
    }

    public void setProSkuId(Long proSkuId) {
        this.proSkuId = proSkuId;
    }
}
