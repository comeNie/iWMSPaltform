package com.huamengtong.wms.dto.inventory;


import com.huamengtong.wms.entity.po.BasePO;

import java.io.Serializable;
import java.math.BigDecimal;

public class TWmsTransactionDTO extends BasePO implements Serializable {

    private Long id;


    private Long tenantId;


    private Long warehouseId;


    private Long cargoOwnerId;


    private Long skuId;


    private String typeCode;


    private Long orderId;


    private Long fromStorageRoomId;


    private Long toStorageRoomId;


    private Long fromZoneId;


    private Long toZoneId;


    private Long fromLocationId;


    private Long toLocationId;


    private String fromPalletNo;


    private String toPalletNo;


    private String fromCartonNo;


    private String toCartonNo;


    private String fromWorkgroupNo;


    private String toWorkgroupNo;


    private String fromInvStatusCode;


    private String toInvStatusCode;


    private Integer fromQty;


    private Integer toQty;


    private BigDecimal fromPrice;


    private BigDecimal toPrice;


    private String createUser;


    private Long createTime;


    private String updateUser;


    private Long updateTime;


    private String referNo;

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

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getFromStorageRoomId() {
        return fromStorageRoomId;
    }

    public void setFromStorageRoomId(Long fromStorageRoomId) {
        this.fromStorageRoomId = fromStorageRoomId;
    }

    public Long getToStorageRoomId() {
        return toStorageRoomId;
    }

    public void setToStorageRoomId(Long toStorageRoomId) {
        this.toStorageRoomId = toStorageRoomId;
    }

    public Long getFromZoneId() {
        return fromZoneId;
    }

    public void setFromZoneId(Long fromZoneId) {
        this.fromZoneId = fromZoneId;
    }

    public Long getToZoneId() {
        return toZoneId;
    }

    public void setToZoneId(Long toZoneId) {
        this.toZoneId = toZoneId;
    }

    public Long getFromLocationId() {
        return fromLocationId;
    }

    public void setFromLocationId(Long fromLocationId) {
        this.fromLocationId = fromLocationId;
    }

    public Long getToLocationId() {
        return toLocationId;
    }

    public void setToLocationId(Long toLocationId) {
        this.toLocationId = toLocationId;
    }

    public String getFromPalletNo() {
        return fromPalletNo;
    }

    public void setFromPalletNo(String fromPalletNo) {
        this.fromPalletNo = fromPalletNo;
    }

    public String getToPalletNo() {
        return toPalletNo;
    }

    public void setToPalletNo(String toPalletNo) {
        this.toPalletNo = toPalletNo;
    }

    public String getFromCartonNo() {
        return fromCartonNo;
    }

    public void setFromCartonNo(String fromCartonNo) {
        this.fromCartonNo = fromCartonNo;
    }

    public String getToCartonNo() {
        return toCartonNo;
    }

    public void setToCartonNo(String toCartonNo) {
        this.toCartonNo = toCartonNo;
    }

    public String getFromWorkgroupNo() {
        return fromWorkgroupNo;
    }

    public void setFromWorkgroupNo(String fromWorkgroupNo) {
        this.fromWorkgroupNo = fromWorkgroupNo;
    }

    public String getToWorkgroupNo() {
        return toWorkgroupNo;
    }

    public void setToWorkgroupNo(String toWorkgroupNo) {
        this.toWorkgroupNo = toWorkgroupNo;
    }

    public String getFromInvStatusCode() {
        return fromInvStatusCode;
    }

    public void setFromInvStatusCode(String fromInvStatusCode) {
        this.fromInvStatusCode = fromInvStatusCode;
    }

    public String getToInvStatusCode() {
        return toInvStatusCode;
    }

    public void setToInvStatusCode(String toInvStatusCode) {
        this.toInvStatusCode = toInvStatusCode;
    }

    public Integer getFromQty() {
        return fromQty;
    }

    public void setFromQty(Integer fromQty) {
        this.fromQty = fromQty;
    }

    public Integer getToQty() {
        return toQty;
    }

    public void setToQty(Integer toQty) {
        this.toQty = toQty;
    }

    public BigDecimal getFromPrice() {
        return fromPrice;
    }

    public void setFromPrice(BigDecimal fromPrice) {
        this.fromPrice = fromPrice;
    }

    public BigDecimal getToPrice() {
        return toPrice;
    }

    public void setToPrice(BigDecimal toPrice) {
        this.toPrice = toPrice;
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

    public String getReferNo() {
        return referNo;
    }

    public void setReferNo(String referNo) {
        this.referNo = referNo;
    }
}
