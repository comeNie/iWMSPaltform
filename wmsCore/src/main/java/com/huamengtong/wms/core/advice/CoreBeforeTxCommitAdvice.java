package com.huamengtong.wms.core.advice;

import com.huamengtong.wms.core.annotation.NeedLog;
import com.huamengtong.wms.core.context.CoreContextContainer;
import com.huamengtong.wms.core.context.NeedLogContext;
import com.huamengtong.wms.core.formwork.db.domain.CurrentUserEntity;
import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.mq.producer.NeedLogProducer;
import com.huamengtong.wms.em.LogType;
import com.huamengtong.wms.entity.inventory.TWmsInventoryLogEntity;
import com.huamengtong.wms.entity.inventory.TWmsProInventoryLogEntity;
import com.huamengtong.wms.entity.inventory.TWmsTransactionEntity;
import com.huamengtong.wms.entity.main.TWmsOperationLogEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoreBeforeTxCommitAdvice implements AfterReturningAdvice {

    private static final Logger _log = LoggerFactory.getLogger(CoreBeforeTxCommitAdvice.class);

    @Autowired
    NeedLogProducer needLogProducer;

    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        NeedLog needLog = method.getAnnotation(NeedLog.class);
        if (needLog != null) {
            DbShardVO dbShardVO = null;
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof DbShardVO) {
                    dbShardVO = (DbShardVO) args[i];
                    break;
                }
            }
            String createUserName = "";
            Long userId = 0L,tenantId = 0L, warehouseId = 0L;
            if (dbShardVO != null) {
                CurrentUserEntity currentUserEntity = dbShardVO.getCurrentUser();
                createUserName = currentUserEntity.getUserName();
                userId = currentUserEntity.getId();
                tenantId = currentUserEntity.getTenantId();
                warehouseId = dbShardVO.getWarehouseId();
            }
            saveLog(needLog,dbShardVO, createUserName,userId, tenantId,warehouseId);
            CoreContextContainer.clearNeedLogContext();
        }
    }

    private void saveLog(NeedLog needLog, DbShardVO dbShardVO, String createUserName,Long userId , Long tenantId,Long warehouseId){
        LogType logTypes[] = needLog.type();
        NeedLogContext needLogContext = CoreContextContainer.getNeedLogContext();
        if (needLogContext != null && needLogContext.getEntityList().size() > 0) {
            List<Class> typeList = new ArrayList<Class>();
            for (int i = 0; i < logTypes.length; i++) {
                typeList.add(logTypes[i].getEntityClass());
            }
            for (Object obj : needLogContext.getEntityList()) {
                if (obj != null && typeList.contains(obj.getClass())) {
                    Map paramMap = new HashMap<>();
                    paramMap.put("dbShardVO",dbShardVO);
                    paramMap.put("createUserName",createUserName);
                    paramMap.put("tenantId",tenantId);
                    paramMap.put("userId",userId);
                    paramMap.put("warehouseId",warehouseId);
                    if (obj instanceof TWmsOperationLogEntity) {
                        paramMap.put("TWmsOperationLogEntity",(TWmsOperationLogEntity) obj);
                    }
                    else if (obj instanceof TWmsInventoryLogEntity) {
                        paramMap.put("TWmsInventoryLogEntity",(TWmsInventoryLogEntity) obj);
                    }
                    else if (obj instanceof TWmsTransactionEntity) {
                        paramMap.put("TWmsTransactionEntity",(TWmsTransactionEntity) obj);
                    }
                    else if (obj instanceof TWmsProInventoryLogEntity) {
                        paramMap.put("TWmsProInventoryLogEntity", (TWmsProInventoryLogEntity) obj);
                    }
                    try {
                        needLogProducer.sendNeedLog(paramMap);
                    } catch (Exception e) {
                        _log.error(" rocketMq server exception, please check your rocketMq configuration." + e );
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
