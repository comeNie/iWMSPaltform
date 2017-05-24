package com.huamengtong.wms.dto.inwh;

import com.huamengtong.wms.entity.po.BasePO;

import java.io.Serializable;

/**
 * Created by could.hao on 2017/3/21.
 */
public class TWmsQcCheckDTO extends BasePO implements Serializable {
    private Long id;
    private Long tenantId;
    private Long qcId;
    private Long qcDetailId;
    private Long skuId;
    private Integer qcQty;
    private Byte isQualified;
    private String unQualifiedReason;
    private String description;
    private String statusCode;
    private String createUser;
    private Long createTime;
    private String updateUser;
    private Long updateTime;
    private Byte isDel;
    private String typeCode;
    private Long parentId;
    private Long warehouseId;

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
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

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Long getQcId() {
        return qcId;
    }

    public void setQcId(Long qcId) {
        this.qcId = qcId;
    }

    public Long getQcDetailId() {
        return qcDetailId;
    }

    public void setQcDetailId(Long qcDetailId) {
        this.qcDetailId = qcDetailId;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Integer getQcQty() {
        return qcQty;
    }

    public void setQcQty(Integer qcQty) {
        this.qcQty = qcQty;
    }

    public Byte getIsQualified() {
        return isQualified;
    }

    public void setIsQualified(Byte isQualified) {
        this.isQualified = isQualified;
    }

    public String getUnQualifiedReason() {
        return unQualifiedReason;
    }

    public void setUnQualifiedReason(String unQualifiedReason) {
        this.unQualifiedReason = unQualifiedReason == null ? null : unQualifiedReason.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode == null ? null : statusCode.trim();
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
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
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Byte getIsDel() {
        return isDel;
    }

    public void setIsDel(Byte isDel) {
        this.isDel = isDel;
    }
}
