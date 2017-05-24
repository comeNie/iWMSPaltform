package com.huamengtong.wms.entity.inwh;

import com.huamengtong.wms.entity.po.BasePO;

import java.io.Serializable;

/**
 * Created by mario on 2016/11/18.
 */
public class TWmsMoveEntity extends BasePO implements Serializable{

        private Long id;

        private Long tenantId;

        private Long warehouseId;

        private Long cargoOwnerId;

        private String moveReason;

        private String datasourceCode;

        private String statusCode;

        private Long skuId;

       private String sku;

       private String skuName;

       private String barcode;

        private Long fromRoomId;

        private Long toRoomId;

        private Long fromZoneId;

        private Long toZoneId;

        private Long fromLocationId;

        private Long toLocationId;

        private String fromPalletNo;

        private String toPalletNo;

        private String fromContainerNo;

        private String toContainerNo;

        private Integer movedQty;

        private String description;

        private String createUser;

        private Long createTime;

        private String updateUser;

        private Long updateTime;

        private String submitUser;

        private Long submitTime;

        private String referNo;

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

    public Long getCargoOwnerId() {
        return cargoOwnerId;
    }

    public void setCargoOwnerId(Long cargoOwnerId) {
        this.cargoOwnerId = cargoOwnerId;
    }

    public String getMoveReason() {
        return moveReason;
    }

    public void setMoveReason(String moveReason) {
        this.moveReason = moveReason;
    }

    public String getDatasourceCode() {
        return datasourceCode;
    }

    public void setDatasourceCode(String datasourceCode) {
        this.datasourceCode = datasourceCode;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Long getFromRoomId() {
        return fromRoomId;
    }

    public void setFromRoomId(Long fromRoomId) {
        this.fromRoomId = fromRoomId;
    }

    public Long getToRoomId() {
        return toRoomId;
    }

    public void setToRoomId(Long toRoomId) {
        this.toRoomId = toRoomId;
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

    public String getFromContainerNo() {
        return fromContainerNo;
    }

    public void setFromContainerNo(String fromContainerNo) {
        this.fromContainerNo = fromContainerNo;
    }

    public String getToContainerNo() {
        return toContainerNo;
    }

    public void setToContainerNo(String toContainerNo) {
        this.toContainerNo = toContainerNo;
    }

    public Integer getMovedQty() {
        return movedQty;
    }

    public void setMovedQty(Integer movedQty) {
        this.movedQty = movedQty;
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

    public String getSubmitUser() {
        return submitUser;
    }

    public void setSubmitUser(String submitUser) {
        this.submitUser = submitUser;
    }

    public Long getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Long submitTime) {
        this.submitTime = submitTime;
    }

    public String getReferNo() {
        return referNo;
    }

    public void setReferNo(String referNo) {
        this.referNo = referNo;
    }

    public Byte getIsDel() {
        return isDel;
    }

    public void setIsDel(Byte isDel) {
        this.isDel = isDel;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    @Override
    public String toString() {
        return "TWmsMoveEntity{" +
                "id=" + id +
                ", tenantId=" + tenantId +
                ", warehouseId=" + warehouseId +
                ", cargoOwnerId=" + cargoOwnerId +
                ", moveReason='" + moveReason + '\'' +
                ", datasourceCode='" + datasourceCode + '\'' +
                ", statusCode='" + statusCode + '\'' +
                ", skuId=" + skuId +
                ", fromRoomId=" + fromRoomId +
                ", toRoomId=" + toRoomId +
                ", fromZoneId=" + fromZoneId +
                ", toZoneId=" + toZoneId +
                ", fromLocationId=" + fromLocationId +
                ", toLocationId=" + toLocationId +
                ", fromPalletNo='" + fromPalletNo + '\'' +
                ", toPalletNo='" + toPalletNo + '\'' +
                ", fromContainerNo='" + fromContainerNo + '\'' +
                ", toContainerNo='" + toContainerNo + '\'' +
                ", movedQty=" + movedQty +
                ", description='" + description + '\'' +
                ", createUser='" + createUser + '\'' +
                ", createTime=" + createTime +
                ", updateUser='" + updateUser + '\'' +
                ", updateTime=" + updateTime +
                ", submitUser='" + submitUser + '\'' +
                ", submitTime=" + submitTime +
                ", referNo='" + referNo + '\'' +
                ", isDel=" + isDel +
                '}';
    }
}
