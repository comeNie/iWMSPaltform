package com.huamengtong.wms.inventory.mapper;

import com.huamengtong.wms.entity.inventory.TWmsTransactionEntity;
import org.apache.ibatis.annotations.Param;

public interface TransactionMapper {

    Integer insertTransaction(@Param("entity") TWmsTransactionEntity transactionEntity, @Param("splitTableKey") String splitTableKey);

    TWmsTransactionEntity selectByPrimaryKey(@Param("id") Long id,@Param("splitTableKey") String splitTableKey);
}
