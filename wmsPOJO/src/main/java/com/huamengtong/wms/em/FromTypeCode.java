package com.huamengtong.wms.em;


public enum FromTypeCode {
    CYCLECOUNT("CycleCount", "盘点"),OTHER("Other", "其他"),PURCHASE("Purchase", "采购"),
    RETURN("Return", "退货"), TRANSFER("Transfer", "调拨");

    private String value;
    private String cnValue;

    FromTypeCode(String value, String cnValue) {
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
