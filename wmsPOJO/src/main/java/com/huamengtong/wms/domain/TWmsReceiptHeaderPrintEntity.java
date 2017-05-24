package com.huamengtong.wms.domain;


import java.io.Serializable;
import java.util.List;

public class TWmsReceiptHeaderPrintEntity   implements Serializable{

    private List list;//商品明细
    private String fullName;//客户名称
    private String telephone;//联系电话
    private String contact;//联系人
    private String id;//入库编号
    private String receiptDate;//入库时间
    private String receiptUser;//收货人
    private String createTableUser;//制表人
    private String roomNo;//库房编号
    private String startTime;//入库开始时间

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getReceiptType() {
        return receiptType;
    }

    public void setReceiptType(String receiptType) {
        this.receiptType = receiptType;
    }

    private String receiptType;

    public String getCreateTableUser() {
        return createTableUser;
    }

    public void setCreateTableUser(String createTableUser) {
        this.createTableUser = createTableUser;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private String address;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(String receiptDate) {
        this.receiptDate = receiptDate;
    }

    public String getReceiptUser() {
        return receiptUser;
    }

    public void setReceiptUser(String receiptUser) {
        this.receiptUser = receiptUser;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }
}
