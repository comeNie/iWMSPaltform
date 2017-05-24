package com.huamengtong.wms.em;

public enum InventoryStatusCode {

    GOOD("Good", "正品"), BAD("Bad", "残品");

    private String value;
    private String cnValue;

    private InventoryStatusCode(String value, String cnValue) {
        this.value = value;
        this.cnValue = cnValue;
    }

    private InventoryStatusCode(String value) {
        this.value = value;
        this.cnValue = cnValue;
    }

    public String toCn() {
        return this.cnValue;
    }

    public String toString() {
        return this.value;
    }


}
