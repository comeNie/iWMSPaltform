package com.huamengtong.wms.dto;

import com.huamengtong.wms.entity.po.BasePO;

import java.io.Serializable;

public class TWmsPrintMapDTO extends BasePO implements Serializable{
    private Integer id;
    private String code;
    private String name;
    private String field;
    private String detail;
    private Byte hasDetail;
    private String createUser;
    private Long createTime;
    private String updateUser;
    private Long updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Byte getHasDetail() {
        return hasDetail;
    }

    public void setHasDetail(Byte hasDetail) {
        this.hasDetail = hasDetail;
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
}
