package com.huamengtong.wms.em;

/**
 * Created by could.hao on 2016/11/25.
 */
public enum AllocateStatus {

    SUC("1","分配成功"),Fail("2","分配失败"),UNABSORBED("0","未分配");
    private String value;
    private String cnValue;

    private AllocateStatus(String value,String cnValue){
        this.value = value;
        this.cnValue = cnValue;
    }

    private AllocateStatus(String value){
        this.value = value;
    }

    public String toCn(){ return this.cnValue; }

    public String toString(){ return this.value; }

}
