package com.huamengtong.wms.core.service;

import com.huamengtong.wms.core.context.CoreContextContainer;
import com.huamengtong.wms.core.context.NeedLogContext;
import com.huamengtong.wms.core.formwork.db.splitdb.ShareDbUtil;
import com.huamengtong.wms.core.formwork.db.util.DbShareField;
import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.entity.inventory.TWmsInventoryLogEntity;
import com.huamengtong.wms.entity.inventory.TWmsProInventoryLogEntity;
import com.huamengtong.wms.entity.main.TWmsOperationLogEntity;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class BaseService {

    /**
     * 占位方法 用于解决spring在添加切面的时候需要有一个方法为public的问题
     */
    public void occupyingMehtod() {
    }

    public MessageResult getMessageResult(String code, Object ...param){
        return MessageResult.getMessage(code,param);
    }

    public String getSplitTableKey(DbShardVO dbShardVO) {
        return dbShardVO.getShardTableId();
    }

    /**
     * 获取默认分库对象,分表属性为租户id
     * @param dbShardVO
     * @return
     */
    protected DbShardVO getDefaultDbShareVO(DbShardVO dbShardVO){
        //获得基础数据相关数据库分库对象 分库id为租户id
        return ShareDbUtil.getNewDbsharedVO(dbShardVO, DbShareField.DEFAULT,dbShardVO.getShardDbId(),dbShardVO.getCurrentUser().getTenantId()+"");
    }

    /**
     * 获得入库/库内分库对象，分表属性仓库Id
     * @param dbShardVO
     * @return
     */
    protected DbShardVO changeInwhDbShareVO(DbShardVO dbShardVO){
        return ShareDbUtil.getNewDbsharedVO(dbShardVO, DbShareField.IN_WH,dbShardVO.getShardDbId(),dbShardVO.getWarehouseId()+"");
    }

    /**
     * 获得出库分库对象，分表属性仓库Id
     * @param dbShardVO
     * @return
     */
    protected DbShardVO changeOutwhDbShareVO(DbShardVO dbShardVO){
        return ShareDbUtil.getNewDbsharedVO(dbShardVO, DbShareField.OUT_WH,dbShardVO.getShardDbId(),dbShardVO.getWarehouseId()+"");
    }

    protected DbShardVO getSkuDbShareVO(DbShardVO dbShardVO){
        return ShareDbUtil.getNewDbsharedVO(dbShardVO, DbShareField.SKU,dbShardVO.getShardDbId(),dbShardVO.getCurrentUser().getTenantId()+"");
    }


    /***
     * 记录操作日志
     * @param id   单据号
     * @param operationCode  操作类型（收货/质检/上架/分配/拣货/出库/交接）
     * @param description    描述
     * @param orderTypeCode  单据类型（收货/质检/上架/分配/拣货/出库/交接）
     * @param operationStatusCode 操作结果状态（操作中/已完成/已失败）
     * @param dbShardVO
     */
    protected void writeOperationLog(Long id,Long cargoOwnerId, String operationCode, String description, String orderTypeCode, String operationStatusCode, DbShardVO dbShardVO){
        //操作日志
        NeedLogContext<TWmsOperationLogEntity> logContext = new NeedLogContext<TWmsOperationLogEntity>();
        TWmsOperationLogEntity bizLog = new TWmsOperationLogEntity();
        queueNeedLog(id, cargoOwnerId,operationCode, description, orderTypeCode, operationStatusCode, dbShardVO, bizLog);
        logContext.add(bizLog);
        CoreContextContainer.setNeedLogContext(logContext);
    }

    /***
     * 收集日志并编入列表
     * @param id
     * @param operationCode
     * @param description
     * @param orderTypeCode
     * @param operationStatusCode
     * @param dbShardVO
     * @param operationLogEntity
     */
    protected void queueNeedLog(Long id,Long cargoOwnerId,String operationCode,String description, String orderTypeCode, String operationStatusCode,DbShardVO dbShardVO,TWmsOperationLogEntity operationLogEntity){
        operationLogEntity.setOrderNo(id);
        if(cargoOwnerId == null){
            cargoOwnerId = 0L;
        }
        operationLogEntity.setCargoOwnerId(cargoOwnerId);
        operationLogEntity.setOrderTypeCode(orderTypeCode); //单据类型（收货/质检/上架/分配/拣货/出库/交接）
        operationLogEntity.setWarehouseId(dbShardVO.getWarehouseId());
        operationLogEntity.setTenantId(dbShardVO.getCurrentUser().getTenantId());
        operationLogEntity.setCreateUser(dbShardVO.getCurrentUser().getUserName());
        operationLogEntity.setCreateTime(new Date().getTime());
        operationLogEntity.setUpdateUser(dbShardVO.getCurrentUser().getUserName());
        operationLogEntity.setUpdateTime(new Date().getTime());
        operationLogEntity.setStatusCode(operationStatusCode); //操作结果状态（操作中/已完成/已失败）
        operationLogEntity.setDescription(description);
        operationLogEntity.setOperationCode(operationCode);  //操作类型（收货/质检/上架/分配/拣货/出库/交接）
    }


    /**
     * 记录库存日志
     * @param inventoryId 库存ID
     * @param skuId 商品ID
     * @param typeCode 单据类型（入库单/出库单）
     * @param orderId 单据ID
     * @param detailId 单据行号
     * @param qty 库存变化数量（入库2记录为2，出库2记录为-2）
     * @param dbShardVO
     */
    protected void writeInventoryLog(Long inventoryId,Long skuId,String typeCode,Long orderId,Long detailId, Integer qty, DbShardVO dbShardVO){
        //操作日志
        NeedLogContext<TWmsInventoryLogEntity> logContext = new NeedLogContext<TWmsInventoryLogEntity>();
        TWmsInventoryLogEntity bizLog = new TWmsInventoryLogEntity();
        queueNeedInventoryLog(inventoryId,skuId,orderId,detailId, qty,typeCode, dbShardVO, bizLog);
        logContext.add(bizLog);
        CoreContextContainer.setNeedLogContext(logContext);
    }

    /**
     * 构建库存 日志 实体类
     * @param inventoryId
     * @param skuId
     * @param orderId
     * @param detailId
     * @param qty
     * @param typeCode
     * @param dbShardVO
     * @param inventoryLogEntity
     */
    protected void queueNeedInventoryLog(Long inventoryId,Long skuId,Long orderId,Long detailId,Integer qty,String typeCode,DbShardVO dbShardVO,TWmsInventoryLogEntity inventoryLogEntity){
        inventoryLogEntity.setInventoryId(inventoryId);
        inventoryLogEntity.setTypeCode(typeCode);
        inventoryLogEntity.setSkuId(skuId);
        inventoryLogEntity.setOrderId(orderId);
        inventoryLogEntity.setDetailId(detailId);
        inventoryLogEntity.setQty(qty);
        inventoryLogEntity.setWarehouseId(dbShardVO.getWarehouseId());
        inventoryLogEntity.setTenantId(dbShardVO.getCurrentUser().getTenantId());
        inventoryLogEntity.setCreateUser(dbShardVO.getCurrentUser().getUserName());
        inventoryLogEntity.setCreateTime(new Date().getTime());
        inventoryLogEntity.setUpdateUser(dbShardVO.getCurrentUser().getUserName());
        inventoryLogEntity.setUpdateTime(new Date().getTime());
    }


    /**
     * 写入成品库存日志
     * @param proInventoryId 成品库存Id
     * @param skuId  商品Id
     * @param typeCode 操作类型
     * @param orderId 订单号
     * @param detailId 明细Id
     * @param qty  改变数量，增加为正，减少为负
     * @param dbShardVO  分库分表标识
     */
    protected void writeProInventoryLog(Long proInventoryId,Long skuId,String skuSpec,String typeCode ,Long orderId , Long detailId,Integer qty,DbShardVO dbShardVO){
        //操作日志
        NeedLogContext<TWmsProInventoryLogEntity> logContext = new NeedLogContext<TWmsProInventoryLogEntity>();
        TWmsProInventoryLogEntity bizLog = new TWmsProInventoryLogEntity();
        queueNeedProInventoryLog(proInventoryId,skuId,skuSpec,orderId,detailId,qty,typeCode,dbShardVO,bizLog);
        logContext.add(bizLog);
        CoreContextContainer.setNeedLogContext(logContext);
    }

    /**
     * 成品库存日志生成
     * @param proInventoryId  成品库存id
     * @param skuId  商品id
     * @param skuSpec  商品规格
     * @param orderId  订单号
     * @param detailId  明细订单号
     * @param qty     变化数量
     * @param typeCode   操作类型
     * @param dbShardVO  分库分表标识
     * @param proInventoryLogEntity  日志对象
     */
    protected void queueNeedProInventoryLog(Long proInventoryId, Long skuId,String skuSpec, Long orderId,Long detailId, Integer qty, String typeCode, DbShardVO dbShardVO, TWmsProInventoryLogEntity proInventoryLogEntity){
        proInventoryLogEntity.setProInventoryId(proInventoryId);
        proInventoryLogEntity.setSkuId(skuId);
        proInventoryLogEntity.setSkuSpec(skuSpec);
        proInventoryLogEntity.setTypeCode(typeCode);
        proInventoryLogEntity.setOrderId(orderId);
        proInventoryLogEntity.setDetailId(detailId);
        proInventoryLogEntity.setQty(qty);
        proInventoryLogEntity.setWarehouseId(dbShardVO.getWarehouseId());
        proInventoryLogEntity.setTenantId(dbShardVO.getCurrentUser().getTenantId());
        proInventoryLogEntity.setCreateUser(dbShardVO.getCurrentUser().getUserName());
        proInventoryLogEntity.setCreateTime(new Date().getTime());
        proInventoryLogEntity.setUpdateUser(dbShardVO.getCurrentUser().getUserName());
        proInventoryLogEntity.setUpdateTime(new Date().getTime());
    }

}
