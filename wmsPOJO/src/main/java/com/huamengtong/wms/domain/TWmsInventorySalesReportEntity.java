package com.huamengtong.wms.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by mario on 2017/3/15.
 */
public class TWmsInventorySalesReportEntity implements Serializable {

    private Long skuId;
    private String sku;//商品编码
    private String skuItemName;//商品名称
    private String skuBarCode;//商品条码
    private String spec;//商品规格
    private String unitType;//商品类型
    private BigDecimal netWeight;//商品净重（重量）
    private Long categorysId;//商品类别
    private Integer startQty;//期初数量
    private BigDecimal startAmount;//期初单价
    private BigDecimal startPrice;//期初金额
    private Integer receivedQty;//入库数量
    private BigDecimal costPrice;//入库单价
    private BigDecimal totalPrice;//入库金额
    private Integer receiptAdjustmentQty;//调整入库数量
    private BigDecimal receiptAmount;//调整入库单价
    private BigDecimal receiptPrice;//调整入库金额
    private Integer proReceiptQty;//加工入库数量
    private BigDecimal proReceiptAmount;//加工入库单价
    private BigDecimal proReceiptPrice;//加工入库金额
    private Integer orderedQty;//出库数量
    private BigDecimal amount;//出库单价
    private BigDecimal price;//出库金额
    private Integer shipmentAdjustmentQty;//调整出库数量
    private BigDecimal shipmentAmount;//调整出库单价
    private BigDecimal shipmentPrice;//调整出库金额
    private Integer proShipmentQty;//加工出库数量
    private BigDecimal proShipmentAmount;//加工出库单价
    private BigDecimal proShipmentPrice;//加工出库金额
    private Integer sumQty;//结算数量
    private BigDecimal sumAmount;//结算单价
    private BigDecimal sumPrice;//结算金额
    private Long fromTime;
    private Long toTime;

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

    public String getSkuItemName() {
        return skuItemName;
    }

    public void setSkuItemName(String skuItemName) {
        this.skuItemName = skuItemName;
    }

    public String getSkuBarCode() {
        return skuBarCode;
    }

    public void setSkuBarCode(String skuBarCode) {
        this.skuBarCode = skuBarCode;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public BigDecimal getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(BigDecimal netWeight) {
        this.netWeight = netWeight;
    }

    public Long getCategorysId() {
        return categorysId;
    }

    public void setCategorysId(Long categorysId) {
        this.categorysId = categorysId;
    }

    public Integer getReceivedQty() {
        return receivedQty;
    }

    public void setReceivedQty(Integer receivedQty) {
        this.receivedQty = receivedQty;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getReceiptAdjustmentQty() {
        return receiptAdjustmentQty;
    }

    public void setReceiptAdjustmentQty(Integer receiptAdjustmentQty) {
        this.receiptAdjustmentQty = receiptAdjustmentQty;
    }

    public BigDecimal getReceiptAmount() {
        return receiptAmount;
    }

    public void setReceiptAmount(BigDecimal receiptAmount) {
        this.receiptAmount = receiptAmount;
    }

    public BigDecimal getReceiptPrice() {
        return receiptPrice;
    }

    public void setReceiptPrice(BigDecimal receiptPrice) {
        this.receiptPrice = receiptPrice;
    }

    public Integer getOrderedQty() {
        return orderedQty;
    }

    public void setOrderedQty(Integer orderedQty) {
        this.orderedQty = orderedQty;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getShipmentAdjustmentQty() {
        return shipmentAdjustmentQty;
    }

    public void setShipmentAdjustmentQty(Integer shipmentAdjustmentQty) {
        this.shipmentAdjustmentQty = shipmentAdjustmentQty;
    }

    public BigDecimal getShipmentAmount() {
        return shipmentAmount;
    }

    public void setShipmentAmount(BigDecimal shipmentAmount) {
        this.shipmentAmount = shipmentAmount;
    }

    public BigDecimal getShipmentPrice() {
        return shipmentPrice;
    }

    public void setShipmentPrice(BigDecimal shipmentPrice) {
        this.shipmentPrice = shipmentPrice;
    }

    public Integer getSumQty() {
        return sumQty;
    }

    public void setSumQty(Integer sumQty) {
        this.sumQty = sumQty;
    }

    public BigDecimal getSumAmount() {
        return sumAmount;
    }

    public void setSumAmount(BigDecimal sumAmount) {
        this.sumAmount = sumAmount;
    }

    public BigDecimal getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(BigDecimal sumPrice) {
        this.sumPrice = sumPrice;
    }

    public Integer getStartQty() {
        return startQty;
    }

    public void setStartQty(Integer startQty) {
        this.startQty = startQty;
    }

    public BigDecimal getStartAmount() {
        return startAmount;
    }

    public void setStartAmount(BigDecimal startAmount) {
        this.startAmount = startAmount;
    }

    public BigDecimal getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(BigDecimal startPrice) {
        this.startPrice = startPrice;
    }

    public Integer getProReceiptQty() {
        return proReceiptQty;
    }

    public void setProReceiptQty(Integer proReceiptQty) {
        this.proReceiptQty = proReceiptQty;
    }

    public BigDecimal getProReceiptAmount() {
        return proReceiptAmount;
    }

    public void setProReceiptAmount(BigDecimal proReceiptAmount) {
        this.proReceiptAmount = proReceiptAmount;
    }

    public BigDecimal getProReceiptPrice() {
        return proReceiptPrice;
    }

    public void setProReceiptPrice(BigDecimal proReceiptPrice) {
        this.proReceiptPrice = proReceiptPrice;
    }

    public Integer getProShipmentQty() {
        return proShipmentQty;
    }

    public void setProShipmentQty(Integer proShipmentQty) {
        this.proShipmentQty = proShipmentQty;
    }

    public BigDecimal getProShipmentAmount() {
        return proShipmentAmount;
    }

    public void setProShipmentAmount(BigDecimal proShipmentAmount) {
        this.proShipmentAmount = proShipmentAmount;
    }

    public BigDecimal getProShipmentPrice() {
        return proShipmentPrice;
    }

    public void setProShipmentPrice(BigDecimal proShipmentPrice) {
        this.proShipmentPrice = proShipmentPrice;
    }

    public Long getFromTime() {
        return fromTime;
    }

    public void setFromTime(Long fromTime) {
        this.fromTime = fromTime;
    }

    public Long getToTime() {
        return toTime;
    }

    public void setToTime(Long toTime) {
        this.toTime = toTime;
    }
}
