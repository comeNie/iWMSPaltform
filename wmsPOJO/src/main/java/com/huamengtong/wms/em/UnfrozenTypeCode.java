package com.huamengtong.wms.em;


public enum UnfrozenTypeCode {

    URGENTUNFROZEN("UrgentUnfrozen","紧急解押"),NORMALUNFROZEN("NormalUnfrozen","正常解押"),DELAYUNFROZEN("DelayUnfrozen","延迟解押");

    private String value;
    private String cnValue;

    private UnfrozenTypeCode(String value, String cnValue) {
        this.value = value;
        this.cnValue = cnValue;
    }
    public String toCn(){return this.cnValue;}
    public String toString(){
        return this.value;
    }
}
