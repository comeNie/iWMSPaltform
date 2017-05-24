package com.huamengtong.wms.entity.outwh;

import com.huamengtong.wms.entity.po.BasePO;
import com.huamengtong.wms.utils.ArrayUtil;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by mario on 2016/11/3.
 */
public class TWmsDnDetailEntity extends BasePO implements Serializable {
    private Long id;
    private Long tenantId;
    private Long dnId;
    private String referLineNo;
    private Long skuId;
    private String sku;
    private String skuName;
    private String skuBarcode;
    private String inventoryStatusCode;
    private Byte isGroup;
    private String groupNo;
    private String groupName;
    private Integer qty;
    private BigDecimal price;
    private BigDecimal amount;
    private BigDecimal payment;
    private BigDecimal discountFee;
    private BigDecimal adjustFee;
    private Byte isGift;
    private String createUser;
    private Long createTime;
    private String updateUser;
    private Long updateTime;
    private Byte isCancelled;
    private Byte isDel;
    private String storageRoomId;

    public Long[] getStorageRoomIds() {
        return storageRoomId != null && !storageRoomId.equals("") ? ArrayUtil.toLongArray(storageRoomId.replaceAll(" ","").split(",")): null;
    }

    public String getStorageRoomId() {
        return storageRoomId;
    }

    public void setStorageRoomId(String storageRoomId) {
        this.storageRoomId = storageRoomId;
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

    public Long getDnId() {
        return dnId;
    }

    public void setDnId(Long dnId) {
        this.dnId = dnId;
    }

    public String getReferLineNo() {
        return referLineNo;
    }

    public void setReferLineNo(String referLineNo) {
        this.referLineNo = referLineNo;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getSkuBarcode() {
        return skuBarcode;
    }

    public void setSkuBarcode(String skuBarcode) {
        this.skuBarcode = skuBarcode;
    }

    public String getInventoryStatusCode() {
        return inventoryStatusCode;
    }

    public void setInventoryStatusCode(String inventoryStatusCode) {
        this.inventoryStatusCode = inventoryStatusCode;
    }

    public Byte getIsGroup() {
        return isGroup;
    }

    public void setIsGroup(Byte isGroup) {
        this.isGroup = isGroup;
    }

    public String getGroupNo() {
        return groupNo;
    }

    public void setGroupNo(String groupNo) {
        this.groupNo = groupNo;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public BigDecimal getDiscountFee() {
        return discountFee;
    }

    public void setDiscountFee(BigDecimal discountFee) {
        this.discountFee = discountFee;
    }

    public BigDecimal getAdjustFee() {
        return adjustFee;
    }

    public void setAdjustFee(BigDecimal adjustFee) {
        this.adjustFee = adjustFee;
    }

    public Byte getIsGift() {
        return isGift;
    }

    public void setIsGift(Byte isGift) {
        this.isGift = isGift;
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

    public Byte getIsCancelled() {
        return isCancelled;
    }

    public void setIsCancelled(Byte isCancelled) {
        this.isCancelled = isCancelled;
    }

    public Byte getIsDel() {
        return isDel;
    }

    public void setIsDel(Byte isDel) {
        this.isDel = isDel;
    }

    @Override
    public String toString() {
        return "TWmsDnDetailEntity{" +
                "id=" + id +
                ", tenantId=" + tenantId +
                ", dnId=" + dnId +
                ", referLineNo='" + referLineNo + '\'' +
                ", skuId=" + skuId +
                ", sku='" + sku + '\'' +
                ", skuName='" + skuName + '\'' +
                ", skuBarcode='" + skuBarcode + '\'' +
                ", inventoryStatusCode='" + inventoryStatusCode + '\'' +
                ", isGroup=" + isGroup +
                ", groupNo='" + groupNo + '\'' +
                ", groupName='" + groupName + '\'' +
                ", qty=" + qty +
                ", price=" + price +
                ", amount=" + amount +
                ", payment=" + payment +
                ", discountFee=" + discountFee +
                ", adjustFee=" + adjustFee +
                ", isGift=" + isGift +
                ", createUser='" + createUser + '\'' +
                ", createTime=" + createTime +
                ", updateUser='" + updateUser + '\'' +
                ", updateTime=" + updateTime +
                ", isCancelled=" + isCancelled +
                ", isDel=" + isDel +
                '}';
    }
}
