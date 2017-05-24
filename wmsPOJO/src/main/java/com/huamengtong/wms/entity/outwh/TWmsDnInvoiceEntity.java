package com.huamengtong.wms.entity.outwh;

import com.huamengtong.wms.entity.po.BasePO;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by mario on 2016/11/9.
 */
public class TWmsDnInvoiceEntity extends BasePO implements Serializable {

    private Long id;


    private Long tenantId;


    private String invoiceNo;


    private String invoiceTitle;


    private String invoiceContent;


    private BigDecimal invoiceTotal;


    private String invoiceTypeCode;


    private Byte isPrintsku;


    private String company;


    private String taxpayerNo;


    private String regAddress;


    private String regTelephone;


    private String bankName;


    private String accountNumber;


    private Byte isDel;


    private String updateUser;


    private Long updateTime;


    private String createUser;


    private Long createTime;

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

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public String getInvoiceContent() {
        return invoiceContent;
    }

    public void setInvoiceContent(String invoiceContent) {
        this.invoiceContent = invoiceContent;
    }

    public BigDecimal getInvoiceTotal() {
        return invoiceTotal;
    }

    public void setInvoiceTotal(BigDecimal invoiceTotal) {
        this.invoiceTotal = invoiceTotal;
    }

    public String getInvoiceTypeCode() {
        return invoiceTypeCode;
    }

    public void setInvoiceTypeCode(String invoiceTypeCode) {
        this.invoiceTypeCode = invoiceTypeCode;
    }

    public Byte getIsPrintsku() {
        return isPrintsku;
    }

    public void setIsPrintsku(Byte isPrintsku) {
        this.isPrintsku = isPrintsku;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTaxpayerNo() {
        return taxpayerNo;
    }

    public void setTaxpayerNo(String taxpayerNo) {
        this.taxpayerNo = taxpayerNo;
    }

    public String getRegAddress() {
        return regAddress;
    }

    public void setRegAddress(String regAddress) {
        this.regAddress = regAddress;
    }

    public String getRegTelephone() {
        return regTelephone;
    }

    public void setRegTelephone(String regTelephone) {
        this.regTelephone = regTelephone;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Byte getIsDel() {
        return isDel;
    }

    public void setIsDel(Byte isDel) {
        this.isDel = isDel;
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

    @Override
    public String toString() {
        return "TWmsDnInvoiceEntity{" +
                "id=" + id +
                ", tenantId=" + tenantId +
                ", invoiceNo='" + invoiceNo + '\'' +
                ", invoiceTitle='" + invoiceTitle + '\'' +
                ", invoiceContent='" + invoiceContent + '\'' +
                ", invoiceTotal=" + invoiceTotal +
                ", invoiceTypeCode='" + invoiceTypeCode + '\'' +
                ", isPrintsku=" + isPrintsku +
                ", company='" + company + '\'' +
                ", taxpayerNo='" + taxpayerNo + '\'' +
                ", regAddress='" + regAddress + '\'' +
                ", regTelephone='" + regTelephone + '\'' +
                ", bankName='" + bankName + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", isDel=" + isDel +
                ", updateUser='" + updateUser + '\'' +
                ", updateTime=" + updateTime +
                ", createUser='" + createUser + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
