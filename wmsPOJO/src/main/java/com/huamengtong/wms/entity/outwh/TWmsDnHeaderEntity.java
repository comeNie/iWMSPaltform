package com.huamengtong.wms.entity.outwh;

import com.huamengtong.wms.dto.outwh.TWmsDnInvoiceDTO;
import com.huamengtong.wms.dto.outwh.TWmsSaleOrderDTO;
import com.huamengtong.wms.entity.po.BasePO;

import java.io.Serializable;

/**
 * Created by mario on 2016/11/3.
 */
public class TWmsDnHeaderEntity extends BasePO implements Serializable {

    private Long id;

    private Long tenantId;

    private Long warehouseId;

    private Long cargoOwnerId;

    private Long orderId;

    private Long invoiceId;

    private String datasourceCode;

    private String fromtypeCode;

    private String typeCode;

    private String statusCode;

    private Byte isCancelled;

    private Byte isFailed;

    private Long carrierId;

    private String carrierName;

    private Long auditedTime;

    private String auditedUser;

    private String createUser;

    private Long createTime;

    private String updateUser;

    private Long updateTime;

    private Byte isDel;

    private TWmsDnInvoiceDTO invoice;

    private TWmsSaleOrderDTO order;

    private Byte isUrgent;

    private Byte isPrintsku;

    private String invoiceTypeCode;

    public String getInvoiceTypeCode() {
        return invoiceTypeCode;
    }

    public void setInvoiceTypeCode(String invoiceTypeCode) {
        this.invoiceTypeCode = invoiceTypeCode;
    }

    public Byte getIsUrgent() {
        return isUrgent;
    }

    public void setIsUrgent(Byte isUrgent) {
        this.isUrgent = isUrgent;
    }

    public Byte getIsPrintsku() {
        return isPrintsku;
    }

    public void setIsPrintsku(Byte isPrintsku) {
        this.isPrintsku = isPrintsku;
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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getDatasourceCode() {
        return datasourceCode;
    }

    public void setDatasourceCode(String datasourceCode) {
        this.datasourceCode = datasourceCode;
    }

    public String getFromtypeCode() {
        return fromtypeCode;
    }

    public void setFromtypeCode(String fromtypeCode) {
        this.fromtypeCode = fromtypeCode;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public Byte getIsCancelled() {
        return isCancelled;
    }

    public void setIsCancelled(Byte isCancelled) {
        this.isCancelled = isCancelled;
    }

    public Byte getIsFailed() {
        return isFailed;
    }

    public void setIsFailed(Byte isFailed) {
        this.isFailed = isFailed;
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

    public Long getAuditedTime() {
        return auditedTime;
    }

    public void setAuditedTime(Long auditedTime) {
        this.auditedTime = auditedTime;
    }

    public String getAuditedUser() {
        return auditedUser;
    }

    public void setAuditedUser(String auditedUser) {
        this.auditedUser = auditedUser;
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

    public Byte getIsDel() {
        return isDel;
    }

    public void setIsDel(Byte isDel) {
        this.isDel = isDel;
    }


    @Override
    public String toString() {
        return "TWmsDnHeaderEntity{" +
                "id=" + id +
                ", tenantId=" + tenantId +
                ", warehouseId=" + warehouseId +
                ", cargoOwnerId=" + cargoOwnerId +
                ", orderId=" + orderId +
                ", invoiceId=" + invoiceId +
                ", datasourceCode='" + datasourceCode + '\'' +
                ", fromtypeCode='" + fromtypeCode + '\'' +
                ", typeCode='" + typeCode + '\'' +
                ", statusCode='" + statusCode + '\'' +
                ", isCancelled=" + isCancelled +
                ", isFailed=" + isFailed +
                ", carrierId='" + carrierId + '\'' +
                ", carrierName='" + carrierName + '\'' +
                ", auditedTime=" + auditedTime +
                ", auditedUser='" + auditedUser + '\'' +
                ", createUser='" + createUser + '\'' +
                ", createTime=" + createTime +
                ", updateUser='" + updateUser + '\'' +
                ", updateTime=" + updateTime +
                ", isDel=" + isDel +
                '}';
    }
}
