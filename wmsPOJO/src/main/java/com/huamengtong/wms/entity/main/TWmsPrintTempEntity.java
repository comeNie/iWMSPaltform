package com.huamengtong.wms.entity.main;

import com.huamengtong.wms.entity.po.BasePO;

public class TWmsPrintTempEntity extends BasePO {

    private Integer id;
    private Long tenantId;
    private String reportCategoryCode;
    private String reportName;
    private Byte isDefault;
    private String createUser;
    private Long createTime;
    private String updateUser;
    private Long updateTime;
    private String carrier;
    private String projectCode;
    private String content;
    private String printOptions;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPrintOptions() {
        return printOptions;
    }

    public void setPrintOptions(String printOptions) {
        this.printOptions = printOptions;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getReportCategoryCode() {
        return reportCategoryCode;
    }

    public void setReportCategoryCode(String reportCategoryCode) {
        this.reportCategoryCode = reportCategoryCode;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public Byte getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Byte isDefault) {
        this.isDefault = isDefault;
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

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }
}
