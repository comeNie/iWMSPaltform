package com.huamengtong.wms.em;


public enum OrderStructureCode {

    MultiSKU("MultiSKU","一单多品"),SingleSKU("SingleSKU","一单一品");

    private String value;
    private String cnValue;
    private OrderStructureCode(String value, String cnValue){
        this.value = value;
        this.cnValue = cnValue;
    }
    public String toString(){
        return this.value;
    }
    public String toCn(){
        return this.cnValue;
    }
}
