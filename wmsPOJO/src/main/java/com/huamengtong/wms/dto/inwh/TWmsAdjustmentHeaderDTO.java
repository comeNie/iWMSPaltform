package com.huamengtong.wms.dto.inwh;

import com.huamengtong.wms.entity.po.BasePO;

import java.io.Serializable;

/**
 * Created by mario on 2016/11/16.
 */
public class TWmsAdjustmentHeaderDTO extends BasePO implements Serializable {


    private Long id;

    private Long tenantId;

    private Long warehouseId;

    private Long cargoOwnerId;

    private String referNo;

    private String resonCode;

    private String statusCode;

    private String description;

    private String createUser;

    private Long createTime;

    private String updateUser;

    private Long updateTime;

    private String auditedUser;

    private Long auditedTime;

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

    public Long getcargoOwnerId() {
        return cargoOwnerId;
    }

    public void setcargoOwnerId(Long cargoOwnerId) {
        this.cargoOwnerId = cargoOwnerId;
    }

    public String getReferNo() {
        return referNo;
    }

    public void setReferNo(String referNo) {
        this.referNo = referNo;
    }

    public String getResonCode() {
        return resonCode;
    }

    public void setResonCode(String resonCode) {
        this.resonCode = resonCode;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
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

    public String getAuditedUser() {
        return auditedUser;
    }

    public void setAuditedUser(String auditedUser) {
        this.auditedUser = auditedUser;
    }

    public Long getAuditedTime() {
        return auditedTime;
    }

    public void setAuditedTime(Long auditedTime) {
        this.auditedTime = auditedTime;
    }

    public Byte getIsDel() {
        return isDel;
    }

    public void setIsDel(Byte isDel) {
        this.isDel = isDel;
    }
}
