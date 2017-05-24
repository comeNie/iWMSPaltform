package com.huamengtong.wms.em;

public enum LocationTypeCode {

    Floor("Floor", "地面货位"),Movable("Movable", "移动货位"),Normal("Normal", "普通货架"),Pallet("Pallet", "托盘货架");

    private String value;
    private String cnValue;
    private LocationTypeCode(String value, String cnValue){
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
