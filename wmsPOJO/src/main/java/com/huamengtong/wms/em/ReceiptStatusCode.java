package com.huamengtong.wms.em;

/***
 * 收货状态
 */
public enum ReceiptStatusCode {

    None("0","未收货"),Receipting("1","收货中"),ReceiptPart("3","部分收货"),ReceiptAll("6","全部收货"),ReceiptOver("9","超量收货");

    private String value;
    private String cnValue;
    private ReceiptStatusCode(String value, String cnValue){
        this.value = value;
        this.cnValue = cnValue;
    }
    public String toCn(){return this.cnValue;}
    public String toString(){
        return this.value;
    }
}
