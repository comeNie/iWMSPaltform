package com.huamengtong.wms.em;

/**
 * 单据状态 TicketStatus
 */
public enum TicketStatusCode {

    INIT("Initial","未提交"),PASS("Passed","已通过"),CANCEL("Cancelled","已取消"),SUBMIT("Submitted","已提交"),REVIEWED("Reviewed","已审核"),
    REJECT("Rejected","已驳回"),CONFIRM("Confirmed","已确认"),CLOSED("Closed","已关闭"),REPEALED("Repealed","已撤销"),FINISH("Finished","已完成"),
    GENERATED("Generated","已生成"),INVALID("Invalid","已作废");

    private String value;
    private String cnValue;
    private TicketStatusCode(String value,String cnValue){
        this.value = value;
        this.cnValue = cnValue;
    }
    public String toString(){
        return this.value;
    }
    public String toCn(){
        return this.cnValue;
    }
    public static String toCn(String key){
        TicketStatusCode [] codes = TicketStatusCode.values();
        for(TicketStatusCode code : codes){
            if(code.toString().equals(key)){
                return code.toCn();
            }
        }
        return "";
    }
}
