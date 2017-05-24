package com.huamengtong.wms.dto.inwh;

import com.huamengtong.wms.entity.po.BasePO;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Edwin on 2016/10/21.
 */
public class TWmsAsnHeaderDTO extends BasePO implements Serializable {

    private Long id;


    private Long tenantId;


    private Long warehouseId;


    private Long cargoOwnerId;


    private String datasourceCode;


    private String statusCode;


    private Long expectedDate;


    private String fromTypeCode;


    private String fromOrderNo;


    private String fromAddress;


    private String warehouseReferNo;


    private Long supplierId;


    private String supplierName;


    private String supplierContact;


    private String supplierTelephone;


    private String supplierAddress;


    private String supplierReferNo;


    private Long carrierId;


    private String carrierName;


    private String carrierContact;


    private String carrierTelephone;


    private String carrierAddress;


    private String carrierReferNo;


    private String transportModeCode;


    private String vehicleNo;


    private Integer totalQty;


    private Integer totalCartonQty;


    private BigDecimal totalAmount;


    private BigDecimal totalNetWeight;


    private BigDecimal totalGrossWeight;


    private BigDecimal totalCube;


    private Integer totalPalletQty;


    private String description;


    private String createUser;


    private Long createTime;


    private String updateUser;


    private Long updateTime;


    private String receiptTypeCode;


    private Byte isDel;


    private Byte isNeedQc;


    private Byte qcTimes;


    private String qcStatusCode;


    private BigDecimal qcFtp;


    private Integer totalQtyReal;


    private Integer totalCategoryQty;


    private String fromOmsNo;


    private Long startTime;


    private Long endTime;


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
        this.datasourceCode = datasourceCode;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public Long getExpectedDate() {
        return expectedDate;
    }

    public void setExpectedDate(Long expectedDate) {
        this.expectedDate = expectedDate;
    }

    public String getFromTypeCode() {
        return fromTypeCode;
    }

    public void setFromTypeCode(String fromTypeCode) {
        this.fromTypeCode = fromTypeCode;
    }

    public String getFromOrderNo() {
        return fromOrderNo;
    }

    public void setFromOrderNo(String fromOrderNo) {
        this.fromOrderNo = fromOrderNo;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getWarehouseReferNo() {
        return warehouseReferNo;
    }

    public void setWarehouseReferNo(String warehouseReferNo) {
        this.warehouseReferNo = warehouseReferNo;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierContact() {
        return supplierContact;
    }

    public void setSupplierContact(String supplierContact) {
        this.supplierContact = supplierContact;
    }

    public String getSupplierTelephone() {
        return supplierTelephone;
    }

    public void setSupplierTelephone(String supplierTelephone) {
        this.supplierTelephone = supplierTelephone;
    }

    public String getSupplierAddress() {
        return supplierAddress;
    }

    public void setSupplierAddress(String supplierAddress) {
        this.supplierAddress = supplierAddress;
    }

    public String getSupplierReferNo() {
        return supplierReferNo;
    }

    public void setSupplierReferNo(String supplierReferNo) {
        this.supplierReferNo = supplierReferNo;
    }

    public Long getCarrierId() {
        return carrierId;
    }

    public void setCarrierId(Long carrierId) {
        this.carrierId = carrierId;
    }

    public String getCarrierName() {
        return carrierName;
    }

    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName;
    }

    public String getCarrierContact() {
        return carrierContact;
    }

    public void setCarrierContact(String carrierContact) {
        this.carrierContact = carrierContact;
    }

    public String getCarrierTelephone() {
        return carrierTelephone;
    }

    public void setCarrierTelephone(String carrierTelephone) {
        this.carrierTelephone = carrierTelephone;
    }

    public String getCarrierAddress() {
        return carrierAddress;
    }

    public void setCarrierAddress(String carrierAddress) {
        this.carrierAddress = carrierAddress;
    }

    public String getCarrierReferNo() {
        return carrierReferNo;
    }

    public void setCarrierReferNo(String carrierReferNo) {
        this.carrierReferNo = carrierReferNo;
    }

    public String getTransportModeCode() {
        return transportModeCode;
    }

    public void setTransportModeCode(String transportModeCode) {
        this.transportModeCode = transportModeCode;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public Integer getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(Integer totalQty) {
        this.totalQty = totalQty;
    }

    public Integer getTotalCartonQty() {
        return totalCartonQty;
    }

    public void setTotalCartonQty(Integer totalCartonQty) {
        this.totalCartonQty = totalCartonQty;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTotalNetWeight() {
        return totalNetWeight;
    }

    public void setTotalNetWeight(BigDecimal totalNetWeight) {
        this.totalNetWeight = totalNetWeight;
    }

    public BigDecimal getTotalGrossWeight() {
        return totalGrossWeight;
    }

    public void setTotalGrossWeight(BigDecimal totalGrossWeight) {
        this.totalGrossWeight = totalGrossWeight;
    }

    public BigDecimal getTotalCube() {
        return totalCube;
    }

    public void setTotalCube(BigDecimal totalCube) {
        this.totalCube = totalCube;
    }

    public Integer getTotalPalletQty() {
        return totalPalletQty;
    }

    public void setTotalPalletQty(Integer totalPalletQty) {
        this.totalPalletQty = totalPalletQty;
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

    public String getReceiptTypeCode() {
        return receiptTypeCode;
    }

    public void setReceiptTypeCode(String receiptTypeCode) {
        this.receiptTypeCode = receiptTypeCode;
    }

    public Byte getIsDel() {
        return isDel;
    }

    public void setIsDel(Byte isDel) {
        this.isDel = isDel;
    }

    public Byte getIsNeedQc() {
        return isNeedQc;
    }

    public void setIsNeedQc(Byte isNeedQc) {
        this.isNeedQc = isNeedQc;
    }

    public Byte getQcTimes() {
        return qcTimes;
    }

    public void setQcTimes(Byte qcTimes) {
        this.qcTimes = qcTimes;
    }

    public String getQcStatusCode() {
        return qcStatusCode;
    }

    public void setQcStatusCode(String qcStatusCode) {
        this.qcStatusCode = qcStatusCode;
    }

    public BigDecimal getQcFtp() {
        return qcFtp;
    }

    public void setQcFtp(BigDecimal qcFtp) {
        this.qcFtp = qcFtp;
    }

    public Integer getTotalQtyReal() {
        return totalQtyReal;
    }

    public void setTotalQtyReal(Integer totalQtyReal) {
        this.totalQtyReal = totalQtyReal;
    }

    public Integer getTotalCategoryQty() {
        return totalCategoryQty;
    }

    public void setTotalCategoryQty(Integer totalCategoryQty) {
        this.totalCategoryQty = totalCategoryQty;
    }

    public String getFromOmsNo() {
        return fromOmsNo;
    }

    public void setFromOmsNo(String fromOmsNo) {
        this.fromOmsNo = fromOmsNo;
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
}
