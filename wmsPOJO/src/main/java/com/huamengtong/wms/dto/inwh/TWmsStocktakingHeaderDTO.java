package com.huamengtong.wms.dto.inwh;

import com.huamengtong.wms.entity.po.BasePO;

import java.io.Serializable;

/**
 * Created by mario on 2016/11/22.
 */
public class TWmsStocktakingHeaderDTO extends BasePO implements Serializable {

    private Long id;

    private Long tenantId;

    private Long warehouseId;

    private Long cargoOwnerId;

    private Long storageRoomId;

    private String typeCode;

    private Long stocktakingTime;

    private Byte isAutoAdjust;

    private String statusCode;

    private Integer totalCategoryQty;

    private Integer totalLocationQty;

    private Integer totalStorageRoomQty;

    private String description;

    private String createUser;

    private Long createTime;

    private String updateUser;

    private Long updateTime;

    private String submitUser;

    private Long submitTime;

    private Byte isDel;

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

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public Long getStocktakingTime() {
        return stocktakingTime;
    }

    public void setStocktakingTime(Long stocktakingTime) {
        this.stocktakingTime = stocktakingTime;
    }

    public Byte getIsAutoAdjust() {
        return isAutoAdjust;
    }

    public void setIsAutoAdjust(Byte isAutoAdjust) {
        this.isAutoAdjust = isAutoAdjust;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public Integer getTotalCategoryQty() {
        return totalCategoryQty;
    }

    public void setTotalCategoryQty(Integer totalCategoryQty) {
        this.totalCategoryQty = totalCategoryQty;
    }

    public Integer getTotalLocationQty() {
        return totalLocationQty;
    }

    public void setTotalLocationQty(Integer totalLocationQty) {
        this.totalLocationQty = totalLocationQty;
    }

    public Integer getTotalStorageRoomQty() {
        return totalStorageRoomQty;
    }

    public void setTotalStorageRoomQty(Integer totalStorageRoomQty) {
        this.totalStorageRoomQty = totalStorageRoomQty;
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

    public String getSubmitUser() {
        return submitUser;
    }

    public void setSubmitUser(String submitUser) {
        this.submitUser = submitUser;
    }

    public Long getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Long submitTime) {
        this.submitTime = submitTime;
    }

    public Byte getIsDel() {
        return isDel;
    }

    public void setIsDel(Byte isDel) {
        this.isDel = isDel;
    }
}
