package com.huamengtong.wms.dto.outwh;


import com.huamengtong.wms.entity.po.BasePO;

import java.io.Serializable;

public class TWmsWaveDTO extends BasePO implements Serializable{
    private Long id;
    private Long tenantId;
    private Long warehouseId;
    private Long cargoOwnerId;
    private String datasourceCode;
    private String fromTypeCode;
    private String promotionName;
    private String platformCode;
    private String typeCode;
    private String orderStructCode;
    private String invoiceoptionCode;
    private String expressName;
    private String distributorNo;
    private Integer totalOrderQty;
    private Integer totalCategoryQty;
    private String picker;
    private String containerNo;
    private Integer totalQty;
    private String statusCode;
    private Byte isCod;
    private Integer isUrgent;
    private Byte isPrintExpress;
    private Byte isPrintDelivery;
    private Byte isPrintPicking;
    private Byte isPrintInvoice;
    private String printExpressUser;
    private Long printExpressTime;
    private Byte isDel;
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

    public String getDatasourceCode() {
        return datasourceCode;
    }

    public void setDatasourceCode(String datasourceCode) {
        this.datasourceCode = datasourceCode == null ? null : datasourceCode.trim();
    }

    public String getFromTypeCode() {
        return fromTypeCode;
    }

    public void setFromTypeCode(String fromTypeCode) {
        this.fromTypeCode = fromTypeCode == null ? null : fromTypeCode.trim();
    }

    public String getPromotionName() {
        return promotionName;
    }

    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName == null ? null : promotionName.trim();
    }

    public String getPlatformCode() {
        return platformCode;
    }

    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode == null ? null : platformCode.trim();
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode == null ? null : typeCode.trim();
    }

    public String getOrderStructCode() {
        return orderStructCode;
    }

    public void setOrderStructCode(String orderStructCode) {
        this.orderStructCode = orderStructCode == null ? null : orderStructCode.trim();
    }

    public String getInvoiceoptionCode() {
        return invoiceoptionCode;
    }

    public void setInvoiceoptionCode(String invoiceoptionCode) {
        this.invoiceoptionCode = invoiceoptionCode == null ? null : invoiceoptionCode.trim();
    }

    public String getExpressName() {
        return expressName;
    }

    public void setExpressName(String expressName) {
        this.expressName = expressName == null ? null : expressName.trim();
    }

    public String getDistributorNo() {
        return distributorNo;
    }

    public void setDistributorNo(String distributorNo) {
        this.distributorNo = distributorNo == null ? null : distributorNo.trim();
    }

    public Integer getTotalOrderQty() {
        return totalOrderQty;
    }

    public void setTotalOrderQty(Integer totalOrderQty) {
        this.totalOrderQty = totalOrderQty;
    }

    public Integer getTotalCategoryQty() {
        return totalCategoryQty;
    }

    public void setTotalCategoryQty(Integer totalCategoryQty) {
        this.totalCategoryQty = totalCategoryQty;
    }

    public String getPicker() {
        return picker;
    }

    public void setPicker(String picker) {
        this.picker = picker == null ? null : picker.trim();
    }

    public String getContainerNo() {
        return containerNo;
    }

    public void setContainerNo(String containerNo) {
        this.containerNo = containerNo == null ? null : containerNo.trim();
    }

    public Integer getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(Integer totalQty) {
        this.totalQty = totalQty;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode == null ? null : statusCode.trim();
    }

    public Byte getIsCod() {
        return isCod;
    }

    public void setIsCod(Byte isCod) {
        this.isCod = isCod;
    }

    public Integer getIsUrgent() {
        return isUrgent;
    }

    public void setIsUrgent(Integer isUrgent) {
        this.isUrgent = isUrgent;
    }

    public Byte getIsPrintExpress() {
        return isPrintExpress;
    }

    public void setIsPrintExpress(Byte isPrintExpress) {
        this.isPrintExpress = isPrintExpress;
    }

    public Byte getIsPrintDelivery() {
        return isPrintDelivery;
    }

    public void setIsPrintDelivery(Byte isPrintDelivery) {
        this.isPrintDelivery = isPrintDelivery;
    }

    public Byte getIsPrintPicking() {
        return isPrintPicking;
    }

    public void setIsPrintPicking(Byte isPrintPicking) {
        this.isPrintPicking = isPrintPicking;
    }

    public Byte getIsPrintInvoice() {
        return isPrintInvoice;
    }

    public void setIsPrintInvoice(Byte isPrintInvoice) {
        this.isPrintInvoice = isPrintInvoice;
    }

    public String getPrintExpressUser() {
        return printExpressUser;
    }

    public void setPrintExpressUser(String printExpressUser) {
        this.printExpressUser = printExpressUser == null ? null : printExpressUser.trim();
    }

    public Long getPrintExpressTime() {
        return printExpressTime;
    }

    public void setPrintExpressTime(Long printExpressTime) {
        this.printExpressTime = printExpressTime;
    }

    public Byte getIsDel() {
        return isDel;
    }

    public void setIsDel(Byte isDel) {
        this.isDel = isDel;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
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
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }
}