package com.huamengtong.wms.entity.inventory;

import com.huamengtong.wms.entity.po.BasePO;

import java.math.BigDecimal;

public class TWmsInventoryEntity extends BasePO {

    private Long id;
    private Long tenantId;
    private Long warehouseId;
    private Long cargoOwnerId;
    private Long skuId;
    private String sku;
    private String skuName;
    private String barcode;
    private String unitType;
    private String spec;
    private Long storageRoomId;
    private Long zoneId;
    private Long locationId;
    private String palletNo;
    private String containerNo;
    private String workGroupNo;
    private BigDecimal price;
    private Integer onhandQty;
    private Integer allocatedQty;
    private Integer pickedQty;
    private Integer workingQty;
    private Integer packageQty;
    private Byte isHold;
    private String createUser;
    private Long createTime;
    private String updateUser;
    private Long updateTime;
    private Integer version;
    private Integer mortgagedQty;
    private String inventoryStatusCode;
    private String storageRoomIds;
    private Integer activeQty;

    public Integer getActiveQty() {
        return activeQty;
    }

    public void setActiveQty(Integer activeQty) {
        this.activeQty = activeQty;
    }

    public String getStorageRoomIds() {
        return storageRoomIds;
    }

    public void setStorageRoomIds(String storageRoomIds) {
        this.storageRoomIds = storageRoomIds;
    }

    public Integer getWorkingQty() {
        return workingQty;
    }

    public void setWorkingQty(Integer workingQty) {
        this.workingQty = workingQty;
    }

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

    public Long getStorageRoomId() {
        return storageRoomId;
    }

    public void setStorageRoomId(Long storageRoomId) {
        this.storageRoomId = storageRoomId;
    }

    public Long getZoneId() {
        return zoneId;
    }

    public void setZoneId(Long zoneId) {
        this.zoneId = zoneId;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
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

    public String getWorkGroupNo() {
        return workGroupNo;
    }

    public void setWorkGroupNo(String workGroupNo) {
        this.workGroupNo = workGroupNo;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getOnhandQty() {
        return onhandQty;
    }

    public void setOnhandQty(Integer onhandQty) {
        this.onhandQty = onhandQty;
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

    public Byte getIsHold() {
        return isHold;
    }

    public void setIsHold(Byte isHold) {
        this.isHold = isHold;
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getMortgagedQty() {
        return mortgagedQty;
    }

    public void setMortgagedQty(Integer mortgagedQty) {
        this.mortgagedQty = mortgagedQty;
    }

    public String getInventoryStatusCode() {
        return inventoryStatusCode;
    }

    public void setInventoryStatusCode(String inventoryStatusCode) {
        this.inventoryStatusCode = inventoryStatusCode;
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

    public Integer getPackageQty() {
        return packageQty;
    }

    public void setPackageQty(Integer packageQty) {
        this.packageQty = packageQty;
    }

    public Integer countActiveQty() {
        if (getOnhandQty() != null && getAllocatedQty() != null &&getPickedQty() !=null && getMortgagedQty() != null && getWorkingQty() != null && getPackageQty() != null) {
            return getOnhandQty() - getAllocatedQty() -getPickedQty()- getMortgagedQty() - getWorkingQty() - getPackageQty();
        } else {
            return 0;
        }
    }
}
