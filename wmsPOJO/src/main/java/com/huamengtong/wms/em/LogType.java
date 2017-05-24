package com.huamengtong.wms.em;

import com.huamengtong.wms.entity.inventory.TWmsInventoryLogEntity;
import com.huamengtong.wms.entity.inventory.TWmsProInventoryLogEntity;
import com.huamengtong.wms.entity.inventory.TWmsTransactionEntity;
import com.huamengtong.wms.entity.main.TWmsOperationLogEntity;

public enum LogType {

    OPREATION("operation","操作日志", TWmsOperationLogEntity.class),
    INVENTORY("inventory","库存日志", TWmsInventoryLogEntity.class),
    TRANSACTION("transaction","库存操作日志",TWmsTransactionEntity.class),
    PROINVENTORY("proInventory","成品库存日志",TWmsProInventoryLogEntity.class);

    private String value;
    private String cnValue;
    private Class entityClass;
    private LogType(String value,String cnValue,Class cls){
        this.value = value;
        this.cnValue = cnValue;
        this.entityClass = cls;
    }
    public String toCn(){
        return this.cnValue;
    }
    public String toString(){
        return this.value;
    }
    public Class getEntityClass(){return this.entityClass;}
}
