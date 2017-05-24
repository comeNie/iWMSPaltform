package com.huamengtong.wms.dto.outwh;

import com.huamengtong.wms.entity.po.BasePO;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by mario on 2016/10/31.
 */
public class TWmsShipmentDetailDTO extends BasePO implements Serializable {

    private Long id;
    private Long shipmentId;
    private Long tenantId;
    private Long warehouseId;
    private Long dnDetailId;
    private String saleOrderNo;
    private String referNo;
    private Long skuId;
    private String sku;
    private String skuName;
    private String skuBarcode;
    private String inventoryStatusCode;
    private String zoneTypeCode;
    private Long zoneId;
    private Long locationId;
    private String storageRoomId;
    private String palletNo;
    private String containerNo;
    private Integer orderedQty;
    private Integer allocatedQty;
    private Integer pickedQty;
    private Integer shippedQty;
    private BigDecimal grossWeight;
    private BigDecimal netWeight;
    private BigDecimal volume;
    private String description;
    private BigDecimal amount;
    private BigDecimal price;
    private BigDecimal discountAmount;
    private BigDecimal promotionAmount;
    private BigDecimal actualPayment;
    private Byte isGift;
    private String workGroupNo;
    private String createUser;
    private Long createTime;
    private String updateUser;
    private Long updateTime;
    private Byte isCanceled;
    private Byte isDel;
    private TWmsDnInvoiceDTO invoice;
    private TWmsSaleOrderDTO order;
    private Long cargoOwnerId;
    private Long startTime;
    private Long endTime;
    private Long[] storageRoomIds;

    public Long[] getStorageRoomIds() {
        return storageRoomIds;
    }

    public void setStorageRoomIds(Long[] storageRoomIds){
        this.storageRoomIds = storageRoomIds;
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

    public void setZoneId(Long zoneId) {
        this.zoneId = zoneId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getStorageRoomId() {
        return storageRoomId;
    }

    public void setStorageRoomId(String storageRoomId) {
        this.storageRoomId = storageRoomId;
    }

    public Long getDnDetailId() {
        return dnDetailId;
    }

    public void setDnDetailId(Long dnDetailId) {
        this.dnDetailId = dnDetailId;
    }

    public Long getZoneId() {
        return zoneId;
    }

    public Long getLocationId() {
        return locationId;
    }

    public TWmsDnInvoiceDTO getInvoice() {
        return invoice;
    }

    public void setInvoice(TWmsDnInvoiceDTO invoice) {
        this.invoice = invoice;
    }

    public TWmsSaleOrderDTO getOrder() {
        return order;
    }

    public void setOrder(TWmsSaleOrderDTO order) {
        this.order = order;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(Long shipmentId) {
        this.shipmentId = shipmentId;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getSaleOrderNo() {
        return saleOrderNo;
    }

    public void setSaleOrderNo(String saleOrderNo) {
        this.saleOrderNo = saleOrderNo;
    }

    public String getReferNo() {
        return referNo;
    }

    public void setReferNo(String referNo) {
        this.referNo = referNo;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getInventoryStatusCode() {
        return inventoryStatusCode;
    }

    public void setInventoryStatusCode(String inventoryStatusCode) {
        this.inventoryStatusCode = inventoryStatusCode;
    }

    public String getZoneTypeCode() {
        return zoneTypeCode;
    }

    public void setZoneTypeCode(String zoneTypeCode) {
        this.zoneTypeCode = zoneTypeCode;
    }

    public String getPalletNo() {
        return palletNo;
    }

    public void setPalletNo(String palletNo) {
        this.palletNo = palletNo;
    }

    public String getContainerNo() {
        return containerNo;
    }

    public void setContainerNo(String containerNo) {
        this.containerNo = containerNo;
    }

    public Integer getOrderedQty() {
        return orderedQty;
    }

    public void setOrderedQty(Integer orderedQty) {
        this.orderedQty = orderedQty;
    }

    public Integer getAllocatedQty() {
        return allocatedQty;
    }

    public void setAllocatedQty(Integer allocatedQty) {
        this.allocatedQty = allocatedQty;
    }

    public Integer getPickedQty() {
        return pickedQty;
    }

    public void setPickedQty(Integer pickedQty) {
        this.pickedQty = pickedQty;
    }

    public Integer getShippedQty() {
        return shippedQty;
    }

    public void setShippedQty(Integer shippedQty) {
        this.shippedQty = shippedQty;
    }

    public BigDecimal getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(BigDecimal grossWeight) {
        this.grossWeight = grossWeight;
    }

    public BigDecimal getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(BigDecimal netWeight) {
        this.netWeight = netWeight;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getPromotionAmount() {
        return promotionAmount;
    }

    public void setPromotionAmount(BigDecimal promotionAmount) {
        this.promotionAmount = promotionAmount;
    }

    public BigDecimal getActualPayment() {
        return actualPayment;
    }

    public void setActualPayment(BigDecimal actualPayment) {
        this.actualPayment = actualPayment;
    }

    public Byte getIsGift() {
        return isGift;
    }

    public void setIsGift(Byte isGift) {
        this.isGift = isGift;
    }

    public String getWorkGroupNo() {
        return workGroupNo;
    }

    public void setWorkGroupNo(String workGroupNo) {
        this.workGroupNo = workGroupNo;
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

    public Byte getIsCanceled() {
        return isCanceled;
    }

    public void setIsCanceled(Byte isCanceled) {
        this.isCanceled = isCanceled;
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

    public String getSkuBarcode() {
        return skuBarcode;
    }

    public void setSkuBarcode(String skuBarcode) {
        this.skuBarcode = skuBarcode;
    }
}
