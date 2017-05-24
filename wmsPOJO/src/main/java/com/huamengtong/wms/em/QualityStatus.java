package com.huamengtong.wms.em;

/**
 * Created by could.hao on 2017/3/23.
 */
public enum  QualityStatus {
    INVALID("Invalid","已作废"),FINISHED("Finished","已完成"),INITIAL("Initial","未提交"),DOING("Doing","质检中");

    private String value;
    private String cnValue;
    private QualityStatus(String value, String cnValue){
        this.value = value;
        this.cnValue = cnValue;
    }
    public String toCn(){return this.cnValue;}
    public String toString(){
        return this.value;
    }
}
