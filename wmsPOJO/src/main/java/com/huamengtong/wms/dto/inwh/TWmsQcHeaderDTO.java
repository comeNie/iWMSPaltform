package com.huamengtong.wms.dto.inwh;

import com.huamengtong.wms.entity.po.BasePO;

import java.io.Serializable;

public class TWmsQcHeaderDTO extends BasePO implements Serializable {
    private Long id;


    private Long tenantId;


    private Long warehouseId;


    private Long asnId;


    private Integer totalQty;


    private Integer totalCategoryQty;


    private String qcPrincipalUser;


    private String description;


    private String statusCode;


    private String createUser;


    private Long createTime;


    private String updateUser;


    private Long updateTime;


    private String submitUser;


    private Long submitDate;


    private Byte isDel;


    private Long startTime;


    private Long endTime;

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

    public Long getAsnId() {
        return asnId;
    }

    public void setAsnId(Long asnId) {
        this.asnId = asnId;
    }

    public Integer getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(Integer totalQty) {
        this.totalQty = totalQty;
    }

    public Integer getTotalCategoryQty() {
        return totalCategoryQty;
    }

    public void setTotalCategoryQty(Integer totalCategoryQty) {
        this.totalCategoryQty = totalCategoryQty;
    }

    public String getQcPrincipalUser() {
        return qcPrincipalUser;
    }

    public void setQcPrincipalUser(String qcPrincipalUser) {
        this.qcPrincipalUser = qcPrincipalUser;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getSubmitUser() {
        return submitUser;
    }

    public void setSubmitUser(String submitUser) {
        this.submitUser = submitUser;
    }

    public Long getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Long submitDate) {
        this.submitDate = submitDate;
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
}
