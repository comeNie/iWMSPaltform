package com.huamengtong.wms.dto.report;

import com.huamengtong.wms.entity.po.BasePO;

import java.io.Serializable;


public class TWmsReportReceiptDetailDTO extends BasePO implements Serializable {

    private Long cargoOwnerId;

    private Long warehouseId;

    private Long receiptId;

    private Long receiptDetailId;

    private Long skuId;

    private String skuName;

    private String sku;

    private String skuBarcode;

    private String spec;

    private String unitType;

    private Integer receiptQty;

    private String storageRoomId;

    private Long receiptTime;

    private Long receiptStartTime;

    private Long receiptEndTime;




    public Long getCargoOwnerId() {
        return cargoOwnerId;
    }

    public void setCargoOwnerId(Long cargoOwnerId) {
        this.cargoOwnerId = cargoOwnerId;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Long getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(Long receiptId) {
        this.receiptId = receiptId;
    }

    public Long getReceiptDetailId() {
        return receiptDetailId;
    }

    public void setReceiptDetailId(Long receiptDetailId) {
        this.receiptDetailId = receiptDetailId;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getSkuBarcode() {
        return skuBarcode;
    }

    public void setSkuBarcode(String skuBarcode) {
        this.skuBarcode = skuBarcode;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public Integer getReceiptQty() {
        return receiptQty;
    }

    public void setReceiptQty(Integer receiptQty) {
        this.receiptQty = receiptQty;
    }

    public String getStorageRoomId() {
        return storageRoomId;
    }

    public void setStorageRoomId(String storageRoomId) {
        this.storageRoomId = storageRoomId;
    }

    public Long getReceiptTime() {
        return receiptTime;
    }

    public void setReceiptTime(Long receiptTime) {
        this.receiptTime = receiptTime;
    }

    public Long getReceiptStartTime() {
        return receiptStartTime;
    }

    public void setReceiptStartTime(Long receiptStartTime) {
        this.receiptStartTime = receiptStartTime;
    }

    public Long getReceiptEndTime() {
        return receiptEndTime;
    }

    public void setReceiptEndTime(Long receiptEndTime) {
        this.receiptEndTime = receiptEndTime;
    }
}
