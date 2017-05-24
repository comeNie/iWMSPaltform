package com.huamengtong.wms.em;

/**
 * Created by could.hao on 2017/3/29.
 */
public enum  MachiningType {
    OUTPUT("output", "产出"), CONSUME("consume", "消耗");
    private String value;
    private String valueCn;

    private MachiningType(String value, String valueCn) {
        this.value = value;
        this.valueCn = valueCn;
    }

    public String toString() {
        return this.value;
    }

    public String toCn() {
        return this.valueCn;
    }
}
