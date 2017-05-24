package com.huamengtong.wms.em;


public enum ActionCode {

    Create("Create","创建"),
    Delete("Delete","删除"),
    Update("Update","修改"),
    Confirm("Confirm","确认"),
    Submit("Submit","提交"),
    Finished("Finished","完成"),
    Cancel("Cancel","取消"),
    Reject("Reject","拒收"),
    Repealed("Repealed","撤销"),
    AutoShelve("AutoShelve","自动上架"),
    Allocate("Allocate","分配"),
    Check("Check","复核"),
    Deliver("Deliver","发货"),
    Handover("Handover","交接"),
    Pack("Pack","打包"),
    Pick("Pick","拣货"),
    Sortation("Sortation","分拣"),
    Weight("Weight","称重"),
    PrintDelivery("PrintDelivery","打印发货单"),
    PrintExpress("PrintExpress","打印物流单"),
    PrintInvoice("PrintInvoice","打印发票"),
    PrintPicking("PrintPicking","打印拣货单"),
    PrintReceipt("PrintReceipt","打印入库单"),
    PrintShipment("PrintShipment","打印出库单"),
    AUDITED("Audited","审核通过"),
    INVALID("Invalid","作废"),
    QCFINISH("QcFinish","质检收货"),
    QCFINISHED("QcFinished","质检完成"),
    RECEIPT("Receipt","收货"),
    FROZEN("Frozen","质押"),
    UNFROZEN("Unfrozen","解押"),
    CYCLECOUNT("CycleCount","盘点");


    private String value;
    private String cnValue;
    private ActionCode(String value, String cnValue){
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
