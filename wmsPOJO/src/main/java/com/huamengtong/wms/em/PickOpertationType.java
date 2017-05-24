package com.huamengtong.wms.em;

public enum PickOpertationType {

    UNPICK("0","未拣货"),
    PARTPICK("3","部分拣货"),
    ALLPICK("6","全部拣货"),
    UNALLOCATE("0","未分配"),
    PARTALLOCATE("3","部分分配"),
    ALLOCATE("6","全部分配"),
    SUPERALLOCATE("9","超量分配");

    private String value;
    private String cnValue;
    private PickOpertationType(String value, String cnValue){
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
