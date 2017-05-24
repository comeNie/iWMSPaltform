package com.huamengtong.wms.inventory.mapper.service.impl;

import com.huamengtong.wms.dto.inventory.TWmsTransactionDTO;
import com.huamengtong.wms.entity.inventory.TWmsTransactionEntity;
import com.huamengtong.wms.inventory.mapper.common.SpringTxTestCase;
import com.huamengtong.wms.inventory.service.ITransactionService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;


public class TransactionServiceTest extends SpringTxTestCase {

    @Autowired
    ITransactionService transactionService;

    @Test
    public void insertTransaction(){
        TWmsTransactionEntity transactionEntity = new TWmsTransactionEntity();
        transactionEntity.setWarehouseId(88L);
        transactionEntity.setTenantId(88L);
        transactionEntity.setCargoOwnerId(2L);
        transactionEntity.setCreateUser("admin");
        transactionEntity.setCreateTime(new Date().getTime());
        transactionEntity.setUpdateUser("admin");
        transactionEntity.setUpdateTime(new Date().getTime());
        transactionEntity.setSkuId(55L);
        transactionEntity.setTypeCode("Qc");
        transactionEntity.setOrderId(998L);
        transactionEntity.setFromStorageRoomId(123L);
        transactionEntity.setToStorageRoomId(133L);
        transactionEntity.setFromZoneId(22L);
        transactionEntity.setToZoneId(23L);
        transactionEntity.setFromLocationId(22L);
        transactionEntity.setToLocationId(23L);
        transactionEntity.setFromPalletNo("VVV");
        transactionEntity.setToPalletNo("NNNN");
        transactionEntity.setFromCartonNo("CCCC");
        transactionEntity.setToCartonNo("BBBB");
        transactionEntity.setFromWorkgroupNo("kkk");
        transactionEntity.setToWorkgroupNo("MMM");
        transactionEntity.setFromInvStatusCode("Initial");
        System.out.println("insert Success!");
        transactionService.createTransactionEntityLog(transactionEntity,getDbshardVO());

    }

    @Test
    public void selectByPK(){
        System.out.print(transactionService.findByPK(10014L,getDbshardVO()).toString());

    }

}
