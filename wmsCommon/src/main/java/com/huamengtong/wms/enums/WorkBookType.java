package com.huamengtong.wms.enums;

/**
 * Created by Edwin on 2016/12/12.
 */
public enum WorkBookType {

    XLS("xls"),XLSX("xlsx");
    private String value;

    private WorkBookType(String value){
        this.value = value;
    }
    public String toString(){
        return this.value;
    }

}
