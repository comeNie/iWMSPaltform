package com.huamengtong.wms.em;

public enum  MqLogOpType {

    IN("in", "入队"), OUT("out", "出队"), SYNC("sync", "同步");

    private String value;
    private String valueCn;

    private MqLogOpType(String value, String valueCn) {
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

