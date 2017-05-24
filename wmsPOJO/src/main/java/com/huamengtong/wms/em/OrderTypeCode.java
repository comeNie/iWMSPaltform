package com.huamengtong.wms.em;


public enum OrderTypeCode {

    Receipt("入库单","Receipt"),
    Shipment("出库单","Shipment"),
    Sorting("分拣单","Sorting"),
    ShipmentDn("出库通知单","ShipmentDn"),
    Adjustment("调整单","Adjustment"),
    ASN("到货通知单","ASN"),
    DN("发货通知单","DN"),
    CycleCount("盘点单","CycleCount"),

    Inventory("库存单","Inventory"),

    Hold("冻结单","Hold"),
    Move("移库单","Move"),
    POReturn("采购退货单","POReturn"),
    Replenish("补货单","Replenish"),
    SOReturnNotice("退货通知单","SOReturnNotice"),
    SOReturnReceipt("退货入库单","SOReturnReceipt"),
    Transfer("调拨单","Transfer"),
    Wave("波次单","Wave"),
    QC("质检单","Qc"),
    FROZEN("质押单","Frozen"),
    UNFROZEN("解押单","Unfrozen"),
    PROCESSING("库内加工单","Processing"),
    PROPACKAGE("库内包装单","Propackage"),
    INVPROPACKAGE("加工包装","InvPropackage");


    private String value;
    private String cnValue;
    private OrderTypeCode(String cnValue, String value){
        this.value = value;
        this.cnValue = cnValue;
    }
    public String toCn(){
        return this.cnValue;
    }
    public String toString(){
        return this.value;
    }
}
