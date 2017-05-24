package com.huamengtong.wms.dto.inwh;

import com.huamengtong.wms.entity.po.BasePO;

import java.io.Serializable;

/**
 * Created by mario on 2017/3/28.
 */
public class TWmsProInvPackageDetailDTO extends BasePO implements Serializable {
    private Long id;

    private Long parentId;

    private Long tenantId;

    private Long warehouseId;

    private Long invPackageId;

    private Integer invPackageQty;

    private Long storageRoomId;

    private String spec;

    private String unitType;

    private String typeCode;

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

    public Long getInvPackageId() {
        return invPackageId;
    }

    public void setInvPackageId(Long invPackageId) {
        this.invPackageId = invPackageId;
    }

    public Integer getInvPackageQty() {
        return invPackageQty;
    }

    public void setInvPackageQty(Integer invPackageQty) {
        this.invPackageQty = invPackageQty;
    }

    public Long getStorageRoomId() {
        return storageRoomId;
    }

    public void setStorageRoomId(Long storageRoomId) {
        this.storageRoomId = storageRoomId;
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

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
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
}
