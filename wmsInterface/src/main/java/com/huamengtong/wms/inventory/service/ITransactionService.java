package com.huamengtong.wms.inventory.service;

import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.entity.inventory.TWmsTransactionEntity;


public interface ITransactionService {

    MessageResult createTransactionEntityLog(TWmsTransactionEntity transactionEntity, DbShardVO dbShardVO);

    TWmsTransactionEntity findByPK(Long id,DbShardVO dbShardVO);

}
