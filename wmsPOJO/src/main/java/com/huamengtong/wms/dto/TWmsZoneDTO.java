package com.huamengtong.wms.dto;

import com.huamengtong.wms.entity.po.BasePO;

import java.io.Serializable;

public class TWmsZoneDTO extends BasePO implements Serializable{

    private Long id;

    private Long warehouseId;

    private String zoneNo;

    private String typeCode;

    private Byte isActive;

    private String description;

    private Integer priority;

    private Byte isSaleAble;

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

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getZoneNo() {
        return zoneNo;
    }

    public void setZoneNo(String zoneNo) {
        this.zoneNo = zoneNo;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public Byte getIsActive() {
        return isActive;
    }

    public void setIsActive(Byte isActive) {
        this.isActive = isActive;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Byte getIsSaleAble() {
        return isSaleAble;
    }

    public void setIsSaleAble(Byte isSaleAble) {
        this.isSaleAble = isSaleAble;
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


    @Override
    public String toString() {
        return "TWmsZoneDTO{" +
                "id=" + id +
                ", warehouseId=" + warehouseId +
                ", zoneNo='" + zoneNo + '\'' +
                ", typeCode='" + typeCode + '\'' +
                ", isActive=" + isActive +
                ", description='" + description + '\'' +
                ", priority=" + priority +
                ", isSaleAble=" + isSaleAble +
                ", createUser='" + createUser + '\'' +
                ", createTime=" + createTime +
                ", updateUser='" + updateUser + '\'' +
                ", updateTime=" + updateTime +
                '}';
    }
}
