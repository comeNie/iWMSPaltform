package com.huamengtong.wms.em;


public enum UnfrozenStatusCode {

    NONE("None","未解押"),UNFROZENING("Unfrozening","解押中"), UNFROZENED_ALL("UnfrozenedAll","全部解押"),
    UNFROZENED_PART("UnfrozenedPart","部分解押");

    private String value;
    private String cnValue;
    private UnfrozenStatusCode(String value, String cnValue){
        this.value = value;
        this.cnValue = cnValue;
    }
    public String toCn(){return this.cnValue;}
    public String toString(){
        return this.value;
    }
}
