package com.huamengtong.wms.entity.main;

import com.huamengtong.wms.entity.po.BasePO;


public class TWmsStorageRoomEntity extends BasePO {
    private Long id;
    private Long warehouseId;
    private String roomNo;
    private String typeCode;
    private String storageRoomType;
    private Byte isActive;
    private String description;
    private Integer priority;
    private Byte isDefault;
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

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getStorageRoomType() {
        return storageRoomType;
    }

    public void setStorageRoomType(String storageRoomType) {
        this.storageRoomType = storageRoomType;
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

    public Byte getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Byte isDefault) {
        this.isDefault = isDefault;
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
        return "TWmsStorageRoomEntity{" +
                "id=" + id +
                ", warehouseId=" + warehouseId +
                ", roomNo='" + roomNo + '\'' +
                ", typeCode='" + typeCode + '\'' +
                ", isActive=" + isActive +
                ", description='" + description + '\'' +
                ", priority=" + priority +
                ", isDefault=" + isDefault +
                ", createUser='" + createUser + '\'' +
                ", createTime=" + createTime +
                ", updateUser='" + updateUser + '\'' +
                ", updateTime=" + updateTime +
                '}';
    }
}
