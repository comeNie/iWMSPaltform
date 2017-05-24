package com.huamengtong.wms.entity.inwh;

import com.huamengtong.wms.entity.po.BasePO;

import java.io.Serializable;

/**
 * Created by mario on 2017/3/28.
 */
public class TWmsProInvPackageEntity extends BasePO implements Serializable {

    private Long id;

    private Long tenantId;

    private Long warehouseId;

    private Long cargoOwnerId;

    private String workGroupNo;

    private String typeCode;

    private String statusCode;

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

    public String getWorkGroupNo() {
        return workGroupNo;
    }

    public void setWorkGroupNo(String workGroupNo) {
        this.workGroupNo = workGroupNo;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
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
}
