package com.huamengtong.wms.dto;

import com.huamengtong.wms.entity.po.BasePO;

import java.io.Serializable;

/**
 * Created by mario on 2017/2/8.
 */
public class TWmsAllocationStrategyDTO extends BasePO implements Serializable {

    private Long id;

    private Long tenantId;


    private String strategyName;


    private Byte isActive;


    private Byte isDefault;


    private String orderFieldCode;


    private String directionCode;


    private String description;


    private String createUser;


    private Long createTime;


    private String updateUser;


    private Long updateTime;

    private Long warehouseId;

    private String storageRoomId;

    private Byte isWholePriority;

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

    public String getStrategyName() {
        return strategyName;
    }

    public void setStrategyName(String strategyName) {
        this.strategyName = strategyName;
    }

    public Byte getIsActive() {
        return isActive;
    }

    public void setIsActive(Byte isActive) {
        this.isActive = isActive;
    }

    public Byte getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Byte isDefault) {
        this.isDefault = isDefault;
    }

    public String getOrderFieldCode() {
        return orderFieldCode;
    }

    public void setOrderFieldCode(String orderFieldCode) {
        this.orderFieldCode = orderFieldCode;
    }

    public String getDirectionCode() {
        return directionCode;
    }

    public void setDirectionCode(String directionCode) {
        this.directionCode = directionCode;
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

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getStorageRoomId() {
        return storageRoomId;
    }

    public void setStorageRoomId(String storageRoomId) {
        this.storageRoomId = storageRoomId;
    }

    public Byte getIsWholePriority() {
        return isWholePriority;
    }

    public void setIsWholePriority(Byte isWholePriority) {
        this.isWholePriority = isWholePriority;
    }
}
