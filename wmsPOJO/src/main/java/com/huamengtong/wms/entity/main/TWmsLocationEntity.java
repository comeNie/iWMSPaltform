package com.huamengtong.wms.entity.main;

import com.huamengtong.wms.entity.po.BasePO;


public class TWmsLocationEntity extends BasePO {

    private Long id;

    private Long warehouseId;

    private Long zoneId;

    private String locationNo;

    private Byte isActive;

    private String typeCode;

    private String classCode;

    private String handlingCode;

    private String categoryCode;

    private String abcCode;

    private String description;

    private Byte isMultisku;

    private Byte isMultilot;

    private Integer putawaySeq;

    private Integer pickupSeq;

    private Double volume;

    private Double length ;

    private Double width;

    private Double height;

    private Double weightcapacity;

    private Byte isDefault;

    private String createUser;

    private Long createTime;

    private String updateUser;

    private Long updateTime;

    private Byte isDel;

    private Byte isUsed;

    public Long getId() { return id;}

    public void setId(Long id) { this.id = id;}

    public Long getWarehouseId() { return warehouseId;}

    public void setWarehouseId(Long warehouseId) { this.warehouseId = warehouseId;}

    public Long getZoneId() { return zoneId;}

    public void setZoneId(Long zoneId) { this.zoneId = zoneId;}

    public String getLocationNo() { return locationNo;}

    public void setLocationNo(String locationNo) { this.locationNo = locationNo;}


    public String getTypeCode() { return typeCode;}

    public void setTypeCode(String typeCode) { this.typeCode = typeCode;}

    public String getClassCode() { return classCode;}

    public void setClassCode(String classCode) { this.classCode = classCode;}

    public String getHandlingCode() { return handlingCode;}

    public void setHandlingCode(String handlingCode) { this.handlingCode = handlingCode;}

    public String getCategoryCode() { return categoryCode;}

    public void setCategoryCode(String categoryCode) { this.categoryCode = categoryCode;}

    public String getAbcCode() { return abcCode;}

    public void setAbcCode(String abcCode) { this.abcCode = abcCode;}

    public String getDescription() { return description;}

    public void setDescription(String description) {
        this.description = description;
    }



    public Integer getPutawaySeq() {
        return putawaySeq;
    }

    public void setPutawaySeq(Integer putawaySeq) {
        this.putawaySeq = putawaySeq;
    }

    public Integer getPickupSeq() {
        return pickupSeq;
    }

    public void setPickupSeq(Integer pickupSeq) {
        this.pickupSeq = pickupSeq;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeightcapacity() {
        return weightcapacity;
    }

    public void setWeightcapacity(Double weightcapacity) {
        this.weightcapacity = weightcapacity;
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

    public Byte getIsActive() {
        return isActive;
    }

    public void setIsActive(Byte isActive) {
        this.isActive = isActive;
    }

    public Byte getIsMultisku() {
        return isMultisku;
    }

    public void setIsMultisku(Byte isMultisku) {
        this.isMultisku = isMultisku;
    }

    public Byte getIsMultilot() {
        return isMultilot;
    }

    public void setIsMultilot(Byte isMultilot) {
        this.isMultilot = isMultilot;
    }

    public Byte getIsDel() {
        return isDel;
    }

    public void setIsDel(Byte isDel) {
        this.isDel = isDel;
    }

    public Byte getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(Byte isUsed) {
        this.isUsed = isUsed;
    }

}
