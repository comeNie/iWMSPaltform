package com.huamengtong.wms.em;

/**
 * Created by mario on 2016/12/2.
 */
public enum AdjustTypeCode {
    MOVE("Move","移库"),STOCKTAKING("Stocktaking","盘点") ,MANUAL("Manual", "手动调整");

        private String value;
        private String cnValue;

    AdjustTypeCode(String value, String cnValue){
        this.value = value;
        this.cnValue = cnValue;
    }

    public String toCn(){return  this.cnValue;}
    public String toString(){return this.value;}

}
