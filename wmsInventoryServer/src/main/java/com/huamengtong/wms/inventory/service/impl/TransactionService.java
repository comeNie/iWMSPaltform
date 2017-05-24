package com.huamengtong.wms.inventory.service.impl;

import com.huamengtong.wms.client.IAutoIdClient;
import com.huamengtong.wms.constants.AutoIdConstants;
import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.service.BaseService;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.entity.inventory.TWmsTransactionEntity;
import com.huamengtong.wms.inventory.mapper.TransactionMapper;
import com.huamengtong.wms.inventory.service.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TransactionService extends BaseService implements ITransactionService {

    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private IAutoIdClient autoIdClient;

    @Override
    public MessageResult createTransactionEntityLog(TWmsTransactionEntity transactionEntity, DbShardVO dbShardVO) {
        transactionEntity.setId(autoIdClient.getAutoId(AutoIdConstants.TWmsTransactionEntity));
        transactionMapper.insertTransaction(transactionEntity,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }

    @Override
    public TWmsTransactionEntity findByPK(Long id, DbShardVO dbShardVO) {
        return transactionMapper.selectByPrimaryKey(id,getSplitTableKey(dbShardVO));
    }
}
