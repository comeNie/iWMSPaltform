package com.huamengtong.wms.domain;

import java.io.Serializable;
import java.util.List;

public class TWmsShipmentHeaderPrintEntity implements Serializable{
    private List list;//商品明细
    private String fullName;//客户名称
    private String telephone;//联系电话
    private String contact;//联系人
    private Long id;//出库单号
    private String printDate;//打印出库单时间
    private String receiptUser;//收货人
    private String createTableUser;//制表人
    private String roomNo;//仓库编号
    private String storeTime;//存储时间
    private String carNo;//车号
    private String platformNumber;//平台单号
    private String orderNo;//客户订单
    private String expressName;//承运商

    public String getExpressName() {

        return expressName;
    }

    public void setExpressName(String expressName) {
        this.expressName = expressName;
    }

    public String getPlatformNumber() {
        return platformNumber;
    }

    public void setPlatformNumber(String platformNumber) {
        this.platformNumber = platformNumber;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrintDate() {
        return printDate;
    }

    public void setPrintDate(String printDate) {
        this.printDate = printDate;
    }

    public String getReceiptUser() {
        return receiptUser;
    }

    public void setReceiptUser(String receiptUser) {
        this.receiptUser = receiptUser;
    }

    public String getCreateTableUser() {
        return createTableUser;
    }

    public void setCreateTableUser(String createTableUser) {
        this.createTableUser = createTableUser;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getStoreTime() {
        return storeTime;
    }

    public void setStoreTime(String storeTime) {
        this.storeTime = storeTime;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public List getList() {

        return list;
    }

    public void setList(List list) {
        this.list = list;
    }
}
