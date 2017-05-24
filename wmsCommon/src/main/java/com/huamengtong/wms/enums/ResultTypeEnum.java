package com.huamengtong.wms.enums;

public enum ResultTypeEnum {

    DATA("Data"),POPUP("Popup"),TOASTS("Toasts"),CONFIRM("Confirm");

    private String value;
    private ResultTypeEnum(String value){
        this.value = value;
    }
    public String toString(){
        return this.value;
    }
}
