package com.huamengtong.wms.dto.inwh;


import com.huamengtong.wms.entity.po.BasePO;

import java.io.Serializable;
import java.math.BigDecimal;


public class TWmsReceiptHeaderDTO extends BasePO implements Serializable {

    private Long id;

    private Long tenantId;

    private Long warehouseId;

    private Long cargoOwnerId;

    private Long asnId;

    private String fromTypeCode;

    private String datasourceCode;

    private Long receiptDate;

    private Long originNo;

    private Integer totalQty;

    private Integer totalPalletQty;

    private Integer totalCartonQty;

    private BigDecimal totalNetWeight;

    private BigDecimal totalGrossWeight;

    private BigDecimal totalVolume;

    private String receiptUser;

    private String qcInspector;

    private String description;

    private String statusCode;

    private String receiptStatusCode;

    private String createUser;

    private Long createTime;

    private String updateUser;

    private Long updateTime;

    private String auditedUser;

    private Long auditedTime;

    private String receiptTypeCode;

    private Byte isDel;

    private Integer version;

    private String fromOrderNo;

    private String fromOmsNo;

    private Long startTime;

    private Long endTime;

    private String qcStatusCode;

    public String getQcStatusCode() {
        return qcStatusCode;
    }

    public void setQcStatusCode(String qcStatusCode) {
        this.qcStatusCode = qcStatusCode;
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

    public Long getAsnId() {
        return asnId;
    }

    public void setAsnId(Long asnId) {
        this.asnId = asnId;
    }

    public String getFromTypeCode() {
        return fromTypeCode;
    }

    public void setFromTypeCode(String fromTypeCode) {
        this.fromTypeCode = fromTypeCode;
    }

    public String getDatasourceCode() {
        return datasourceCode;
    }

    public void setDatasourceCode(String datasourceCode) {
        this.datasourceCode = datasourceCode;
    }

    public Long getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(Long receiptDate) {
        this.receiptDate = receiptDate;
    }

    public Long getOriginNo() {
        return originNo;
    }

    public void setOriginNo(Long originNo) {
        this.originNo = originNo;
    }

    public String getReceiptStatusCode() {
        return receiptStatusCode;
    }

    public void setReceiptStatusCode(String receiptStatusCode) {
        this.receiptStatusCode = receiptStatusCode;
    }

    public Integer getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(Integer totalQty) {
        this.totalQty = totalQty;
    }

    public Integer getTotalPalletQty() {
        return totalPalletQty;
    }

    public void setTotalPalletQty(Integer totalPalletQty) {
        this.totalPalletQty = totalPalletQty;
    }

    public Integer getTotalCartonQty() {
        return totalCartonQty;
    }

    public void setTotalCartonQty(Integer totalCartonQty) {
        this.totalCartonQty = totalCartonQty;
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

    public BigDecimal getTotalVolume() {
        return totalVolume;
    }

    public void setTotalVolume(BigDecimal totalVolume) {
        this.totalVolume = totalVolume;
    }

    public String getReceiptUser() {
        return receiptUser;
    }

    public void setReceiptUser(String receiptUser) {
        this.receiptUser = receiptUser;
    }

    public String getQcInspector() {
        return qcInspector;
    }

    public void setQcInspector(String qcInspector) {
        this.qcInspector = qcInspector;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
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

    public String getAuditedUser() {
        return auditedUser;
    }

    public void setAuditedUser(String auditedUser) {
        this.auditedUser = auditedUser;
    }

    public Long getAuditedTime() {
        return auditedTime;
    }

    public void setAuditedTime(Long auditedTime) {
        this.auditedTime = auditedTime;
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getFromOrderNo() {
        return fromOrderNo;
    }

    public void setFromOrderNo(String fromOrderNo) {
        this.fromOrderNo = fromOrderNo;
    }

    public String getFromOmsNo() {
        return fromOmsNo;
    }

    public void setFromOmsNo(String fromOmsNo) {
        this.fromOmsNo = fromOmsNo;
    }
}
