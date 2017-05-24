package com.huamengtong.wms.em;

/**
 * Created by Edwin on 2016/11/29.
 */
public enum  OnlineStatus {

    ON_LINE("online","在线"),OFF_LINE("offline","在线"),FORCE_LOGOUT("forceLogout","强制退出");

    private String value;
    private String valueCn;

    private OnlineStatus(String value, String valueCn) {
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
