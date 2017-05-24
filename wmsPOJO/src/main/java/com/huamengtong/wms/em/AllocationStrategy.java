package com.huamengtong.wms.em;

/**
 * Created by could.hao on 2017/3/13.
 */
public enum AllocationStrategy {

    FIFO("FIFO", "先进先出", "asc"),
    FILO("FILO", "后进后出", "desc"),

    LOT_ATTRIBUTE("LotAttribute", "按批次属性", "work_group_no"),
    RECEIPT_TIME("ReceiptTime", "按收货时间", "create_time");

    private String value;
    private String cnValue;
    private String sqlColumn;

    private AllocationStrategy(String value, String cnValue, String sqlColumn) {
        this.value = value;
        this.cnValue = cnValue;
        this.sqlColumn = sqlColumn;
    }

    public String toCn() {
        return this.cnValue;
    }

    public String toString() {
        return this.value;
    }

    public String toSql() { return this.sqlColumn; }
}
