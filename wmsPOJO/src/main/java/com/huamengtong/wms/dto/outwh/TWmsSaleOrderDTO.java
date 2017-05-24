package com.huamengtong.wms.dto.outwh;

import com.huamengtong.wms.entity.po.BasePO;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by mario on 2016/10/31.
 */
public class TWmsSaleOrderDTO extends BasePO implements Serializable {

    private Long id;


    private Long tenantId;


    private String referNo;


    private String tradeNo;


    private Byte isUrgent;


    private Long orderTime;


    private Long paymentTime;


    private Long expectedDate;


    private String shopNo;


    private String shopName;


    private String distributorNo;


    private String distributorName;


    private String postPayTypecode;


    private BigDecimal postFee;

    private BigDecimal actualPayment;


    private BigDecimal totalFee;


    private BigDecimal discountFee;


    private Byte isCod;


    private Byte isNeedDelivery;


    private Byte isNeedInvoice;


    private String buyerNick;


    private String buyerName;


    private String receiverName;


    private String countryCode;


    private String countryName;


    private String provinceCode;


    private String provinceName;


    private String cityCode;


    private String cityName;


    private String areaCode;


    private String areaName;


    private String zip;


    private String address;


    private String email;


    private String mobile;


    private String telephone;


    private String sellerRemark;


    private String buyerRemark;


    private String orderRemark;


    private String remark;


    private String userdef01;


    private String userdef02;


    private String userdef03;


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

    public String getReferNo() {
        return referNo;
    }

    public void setReferNo(String referNo) {
        this.referNo = referNo;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public Byte getIsUrgent() {
        return isUrgent;
    }

    public void setIsUrgent(Byte isUrgent) {
        this.isUrgent = isUrgent;
    }

    public Long getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Long orderTime) {
        this.orderTime = orderTime;
    }

    public Long getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Long paymentTime) {
        this.paymentTime = paymentTime;
    }

    public Long getExpectedDate() {
        return expectedDate;
    }

    public void setExpectedDate(Long expectedDate) {
        this.expectedDate = expectedDate;
    }

    public String getShopNo() {
        return shopNo;
    }

    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getDistributorNo() {
        return distributorNo;
    }

    public void setDistributorNo(String distributorNo) {
        this.distributorNo = distributorNo;
    }

    public String getDistributorName() {
        return distributorName;
    }

    public void setDistributorName(String distributorName) {
        this.distributorName = distributorName;
    }

    public String getPostPayTypecode() {
        return postPayTypecode;
    }

    public void setPostPayTypecode(String postPayTypecode) {
        this.postPayTypecode = postPayTypecode;
    }

    public BigDecimal getPostFee() {
        return postFee;
    }

    public void setPostFee(BigDecimal postFee) {
        this.postFee = postFee;
    }

    public BigDecimal getActualPayment() {
        return actualPayment;
    }

    public void setActualPayment(BigDecimal actualPayment) {
        this.actualPayment = actualPayment;
    }

    public BigDecimal getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
    }

    public BigDecimal getDiscountFee() {
        return discountFee;
    }

    public void setDiscountFee(BigDecimal discountFee) {
        this.discountFee = discountFee;
    }

    public Byte getIsCod() {
        return isCod;
    }

    public void setIsCod(Byte isCod) {
        this.isCod = isCod;
    }

    public Byte getIsNeedDelivery() {
        return isNeedDelivery;
    }

    public void setIsNeedDelivery(Byte isNeedDelivery) {
        this.isNeedDelivery = isNeedDelivery;
    }

    public Byte getIsNeedInvoice() {
        return isNeedInvoice;
    }

    public void setIsNeedInvoice(Byte isNeedInvoice) {
        this.isNeedInvoice = isNeedInvoice;
    }

    public String getBuyerNick() {
        return buyerNick;
    }

    public void setBuyerNick(String buyerNick) {
        this.buyerNick = buyerNick;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getSellerRemark() {
        return sellerRemark;
    }

    public void setSellerRemark(String sellerRemark) {
        this.sellerRemark = sellerRemark;
    }

    public String getBuyerRemark() {
        return buyerRemark;
    }

    public void setBuyerRemark(String buyerRemark) {
        this.buyerRemark = buyerRemark;
    }

    public String getOrderRemark() {
        return orderRemark;
    }

    public void setOrderRemark(String orderRemark) {
        this.orderRemark = orderRemark;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUserdef01() {
        return userdef01;
    }

    public void setUserdef01(String userdef01) {
        this.userdef01 = userdef01;
    }

    public String getUserdef02() {
        return userdef02;
    }

    public void setUserdef02(String userdef02) {
        this.userdef02 = userdef02;
    }

    public String getUserdef03() {
        return userdef03;
    }

    public void setUserdef03(String userdef03) {
        this.userdef03 = userdef03;
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
