package com.huamengtong.wms.entity.outwh;

import com.huamengtong.wms.entity.po.BasePO;

import java.io.Serializable;
import java.math.BigDecimal;

public class TWmsUnfrozenHeaderEntity extends BasePO implements Serializable {

    private Long id;

    private Long frozenId;

    private Long tenantId;

    private Long warehouseId;

    private Long cargoOwnerId;

    private String name;

    private String factoringType;

    private String statusCode;

    private Integer totalQty;

    private Integer totalCartonQty;

    private BigDecimal totalAmount;

    private BigDecimal totalNetWeight;

    private BigDecimal totalGrossWeight;

    private BigDecimal totalVolume;

    private Integer factoringTotalQty;

    private Long factoringOrganizeId;

    private Long factoringTime;

    private Long factoringActualTime;

    private Long auditedOrganizeId;

    private String auditedUser;

    private Long auditedTime;

    private Byte isAudited;

    private Byte isInvalided;

    private String description;

    private String createUser;

    private Long createTime;

    private String updateUser;

    private Long updateTime;

    private Byte isCancelled;

    private Byte isDel;

    private Long startTime;

    private Long endTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFrozenId() {
        return frozenId;
    }

    public void setFrozenId(Long frozenId) {
        this.frozenId = frozenId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getFactoringType() {
        return factoringType;
    }

    public void setFactoringType(String factoringType) {
        this.factoringType = factoringType == null ? null : factoringType.trim();
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode == null ? null : statusCode.trim();
    }

    public Integer getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(Integer totalQty) {
        this.totalQty = totalQty;
    }

    public Integer getTotalCartonQty() {
        return totalCartonQty;
    }

    public void setTotalCartonQty(Integer totalCartonQty) {
        this.totalCartonQty = totalCartonQty;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTotalNetWeight() {
        return totalNetWeight;
    }

    public void setTotalNetWeight(BigDecimal totalNetWeight) {
        this.totalNetWeight = totalNetWeight;
    }

    public BigDecimal getTotalGrossWeight() {
        return totalGrossWeight;
    }

    public void setTotalGrossWeight(BigDecimal totalGrossWeight) {
        this.totalGrossWeight = totalGrossWeight;
    }

    public BigDecimal getTotalVolume() {
        return totalVolume;
    }

    public void setTotalVolume(BigDecimal totalVolume) {
        this.totalVolume = totalVolume;
    }


    public Integer getFactoringTotalQty() {
        return factoringTotalQty;
    }

    public void setFactoringTotalQty(Integer factoringTotalQty) {
        this.factoringTotalQty = factoringTotalQty;
    }

    public Long getFactoringOrganizeId() {
        return factoringOrganizeId;
    }

    public void setFactoringOrganizeId(Long factoringOrganizeId) {
        this.factoringOrganizeId = factoringOrganizeId;
    }

    public Long getFactoringTime() {
        return factoringTime;
    }

    public Long getFactoringActualTime() {
        return factoringActualTime;
    }

    public void setFactoringActualTime(Long factoringActualTime) {
        this.factoringActualTime = factoringActualTime;
    }

    public void setFactoringTime(Long factoringTime) {
        this.factoringTime = factoringTime;
    }

    public Long getAuditedOrganizeId() {
        return auditedOrganizeId;
    }

    public void setAuditedOrganizeId(Long auditedOrganizeId) {
        this.auditedOrganizeId = auditedOrganizeId;
    }

    public String getAuditedUser() {
        return auditedUser;
    }

    public void setAuditedUser(String auditedUser) {
        this.auditedUser = auditedUser == null ? null : auditedUser.trim();
    }

    public Long getAuditedTime() {
        return auditedTime;
    }

    public void setAuditedTime(Long auditedTime) {
        this.auditedTime = auditedTime;
    }

    public Byte getIsAudited() {
        return isAudited;
    }

    public void setIsAudited(Byte isAudited) {
        this.isAudited = isAudited;
    }

    public Byte getIsInvalided() {
        return isInvalided;
    }

    public void setIsInvalided(Byte isInvalided) {
        this.isInvalided = isInvalided;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
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

    public Byte getIsCancelled() {
        return isCancelled;
    }

    public void setIsCancelled(Byte isCancelled) {
        this.isCancelled = isCancelled;
    }

    public Byte getIsDel() {
        return isDel;
    }

    public void setIsDel(Byte isDel) {
        this.isDel = isDel;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "TWmsUnfrozenHeaderEntity{" +
                "id=" + id +
                ", tenantId=" + tenantId +
                ", warehouseId=" + warehouseId +
                ", cargoOwnerId=" + cargoOwnerId +
                ", name='" + name + '\'' +
                ", factoringType='" + factoringType + '\'' +
                ", statusCode='" + statusCode + '\'' +
                ", totalQty=" + totalQty +
                ", totalCartonQty=" + totalCartonQty +
                ", totalAmount=" + totalAmount +
                ", totalNetWeight=" + totalNetWeight +
                ", totalGrossWeight=" + totalGrossWeight +
                ", totalVolume=" + totalVolume +
                ", factoringOrganizeId=" + factoringOrganizeId +
                ", factoringTime=" + factoringTime +
                ", auditedOrganizeId=" + auditedOrganizeId +
                ", auditedUser='" + auditedUser + '\'' +
                ", auditedTime=" + auditedTime +
                ", isAudited=" + isAudited +
                ", isInvalided=" + isInvalided +
                ", description='" + description + '\'' +
                ", createUser='" + createUser + '\'' +
                ", createTime=" + createTime +
                ", updateUser='" + updateUser + '\'' +
                ", updateTime=" + updateTime +
                ", isCancelled=" + isCancelled +
                ", isDel=" + isDel +
                '}';
    }
}