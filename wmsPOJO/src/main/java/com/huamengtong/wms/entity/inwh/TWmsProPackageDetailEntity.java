package com.huamengtong.wms.entity.inwh;

import com.huamengtong.wms.entity.po.BasePO;

import java.io.Serializable;

/**
 * Created by mario on 2017/2/20.
 */
public class TWmsProPackageDetailEntity extends BasePO implements Serializable {

    private Long id;

    private Long parentId;

    private Long tenantId;

    private Long warehouseId;

    private Long proInventoryId;

    private Integer proInventoryQty;

    private Long proStorageRoomId;

    private String createUser;

    private Long createTime;

    private String updateUser;

    private Long updateTime;

    private String sku;

    private String skuName;

    private String barcode;

    private String spec;

    private String unitType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
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

    public Long getProInventoryId() {
        return proInventoryId;
    }

    public void setProInventoryId(Long proInventoryId) {
        this.proInventoryId = proInventoryId;
    }

    public Integer getProInventoryQty() {
        return proInventoryQty;
    }

    public void setProInventoryQty(Integer proInventoryQty) {
        this.proInventoryQty = proInventoryQty;
    }

    public Long getProStorageRoomId() {
        return proStorageRoomId;
    }

    public void setProStorageRoomId(Long proStorageRoomId) {
        this.proStorageRoomId = proStorageRoomId;
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
