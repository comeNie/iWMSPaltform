package com.huamengtong.wms.inwh.service.impl;

import com.huamengtong.wms.client.IAutoIdClient;
import com.huamengtong.wms.constants.AutoIdConstants;
import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.service.BaseService;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.inwh.TWmsReceiptDetailDTO;
import com.huamengtong.wms.dto.inwh.TWmsReceiptHeaderDTO;
import com.huamengtong.wms.em.InventoryStatusCode;
import com.huamengtong.wms.entity.inwh.TWmsAsnDetailEntity;
import com.huamengtong.wms.entity.inwh.TWmsReceiptDetailEntity;
import com.huamengtong.wms.entity.inwh.TWmsReceiptHeaderEntity;
import com.huamengtong.wms.entity.sku.TWmsSkuEntity;
import com.huamengtong.wms.inwh.mapper.ReceiptDetailMapper;
import com.huamengtong.wms.inwh.service.IReceiptDetailService;
import com.huamengtong.wms.inwh.service.IReceiptHeaderService;
import com.huamengtong.wms.utils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class ReceiptDetailService extends BaseService implements IReceiptDetailService{


    @Autowired
    ReceiptDetailMapper receiptDetailMapper;

    @Autowired
    IReceiptHeaderService receiptHeaderService;

    @Autowired
    IAutoIdClient autoIdClient;

    @Override
    public TWmsReceiptDetailEntity findByPrimaryKey(Long id, DbShardVO dbShardVO) {
        return receiptDetailMapper.selectByPrimaryKey(id,getSplitTableKey(dbShardVO));
    }

    @Override
    public MessageResult removeByPrimaryKey(Long id,String operationUser, DbShardVO dbShardVO) {
        TWmsReceiptDetailEntity receiptDetailEntity = receiptDetailMapper.selectByPrimaryKey(id,getSplitTableKey(dbShardVO));
        TWmsReceiptHeaderEntity receiptHeaderEntity = receiptHeaderService.findByPrimaryKey(receiptDetailEntity.getReceiptId(),dbShardVO);
        receiptHeaderEntity.setTotalQty(receiptHeaderEntity.getTotalQty()-receiptDetailEntity.getReceivedQty());
        receiptHeaderEntity.setTotalGrossWeight(receiptHeaderEntity.getTotalGrossWeight().subtract(receiptDetailEntity.getGrossWeight()));
        receiptHeaderEntity.setTotalNetWeight(receiptHeaderEntity.getTotalNetWeight().subtract(receiptDetailEntity.getNetWeight()));
        receiptHeaderEntity.setTotalVolume(receiptHeaderEntity.getTotalVolume().subtract(receiptDetailEntity.getVolume()));
        receiptHeaderEntity.setUpdateUser(operationUser);
        receiptHeaderEntity.setUpdateTime(new Date().getTime());
        receiptDetailMapper.deleteByPrimaryKey(id,getSplitTableKey(dbShardVO));//删除明细数据
        TWmsReceiptHeaderDTO receiptHeaderDTO = BeanUtils.copyBeanPropertyUtils(receiptHeaderEntity,TWmsReceiptHeaderDTO.class);
        receiptHeaderService.modifyReceiptHeader(receiptHeaderDTO,dbShardVO);//更新主表信息
        return MessageResult.getSucMessage();
    }

    /**
     * 新增入库明细表单
     * 回写入库单主表表单中的总数量&总托数&总箱数&总净重&总毛重&总体积
     * @param receiptDetailDTO
     * @param dbShardVO
     * @return
     */
    @Override
    public MessageResult createReceiptDetail(TWmsReceiptDetailDTO receiptDetailDTO, DbShardVO dbShardVO) {
        TWmsReceiptDetailEntity receiptDetailEntity= BeanUtils.copyBeanPropertyUtils(receiptDetailDTO,TWmsReceiptDetailEntity.class);
        if(receiptDetailEntity.getId() == null){
            receiptDetailEntity.setId(autoIdClient.getAutoId(AutoIdConstants.TWmsReceiptDetailEntity));
        }
        receiptDetailMapper.insertReceiptDetail(receiptDetailEntity,getSplitTableKey(dbShardVO));//新增明细表单数据
        //回写主表单数据
        TWmsReceiptHeaderEntity receiptHeaderEntity = receiptHeaderService.findByPrimaryKey(receiptDetailEntity.getReceiptId(),dbShardVO);
        if(!(receiptDetailEntity.getReceivedQty()==null)){
            receiptHeaderEntity.setTotalQty(receiptHeaderEntity.getTotalQty()+receiptDetailEntity.getReceivedQty());//总数量
        }
        if(!(receiptDetailEntity.getNetWeight()==null)){
            receiptHeaderEntity.setTotalNetWeight(receiptHeaderEntity.getTotalNetWeight().add(receiptDetailEntity.getNetWeight()));//总净重
        }
        if(!(receiptDetailEntity.getGrossWeight()==null)){
            receiptHeaderEntity.setTotalGrossWeight(receiptHeaderEntity.getTotalGrossWeight().add(receiptDetailEntity.getGrossWeight()));//总毛重
        }
        if(!(receiptDetailEntity.getVolume()==null)){
            receiptHeaderEntity.setTotalVolume(receiptHeaderEntity.getTotalVolume().add(receiptDetailEntity.getVolume()));//总体积
        }
        receiptHeaderEntity.setUpdateUser(receiptDetailEntity.getUpdateUser());//最后更新人
        receiptHeaderEntity.setUpdateTime(new Date().getTime());//最后更新时间

        receiptDetailMapper.insertReceiptDetail(receiptDetailEntity,getSplitTableKey(dbShardVO));//插入明细数据

        TWmsReceiptHeaderDTO receiptHeaderDTO = BeanUtils.copyBeanPropertyUtils(receiptHeaderEntity,TWmsReceiptHeaderDTO.class);//拷贝属性
        receiptHeaderService.modifyReceiptHeader(receiptHeaderDTO,dbShardVO);//更新主表
        return MessageResult.getSucMessage();
    }

    /**
     * 更新数据时，检查总数量&总净重&总毛重&总体积&是否被修改,回写主表数据
     * @param receiptDetailDTO
     * @param dbShardVO
     * @return
     */
    @Override
    public MessageResult modifyReceiptDetail(TWmsReceiptDetailDTO receiptDetailDTO, DbShardVO dbShardVO) {
        TWmsReceiptDetailEntity receiptDetailEntity = BeanUtils.copyBeanPropertyUtils(receiptDetailDTO,TWmsReceiptDetailEntity.class);
        //从明细表中取出这条数据
        TWmsReceiptDetailEntity receiptDetailEntity1 = receiptDetailMapper.selectByPrimaryKey(receiptDetailEntity.getReceiptId(),getSplitTableKey(dbShardVO));
        //从主表中取出当前数据
        TWmsReceiptHeaderEntity receiptHeaderEntity = receiptHeaderService.findByPrimaryKey(receiptDetailEntity.getReceiptId(),dbShardVO);
        //判断总数量
        if(!(receiptDetailEntity.getReceivedQty()==null)){
            receiptHeaderEntity.setTotalQty(receiptHeaderEntity.getTotalQty()-receiptDetailEntity1.getReceivedQty()+receiptDetailEntity.getReceivedQty());
        }
        //判断总净重
        if(!(receiptDetailEntity.getNetWeight()==null)){
            receiptHeaderEntity.setTotalNetWeight(receiptHeaderEntity.getTotalNetWeight().add(receiptDetailEntity.getNetWeight()).subtract(receiptDetailEntity1.getNetWeight()));
        }
        //判断总毛重
        if(!(receiptDetailEntity.getGrossWeight()==null)){
            receiptHeaderEntity.setTotalGrossWeight(receiptHeaderEntity.getTotalGrossWeight().add(receiptDetailEntity.getGrossWeight()).subtract(receiptDetailEntity1.getGrossWeight()));
        }
        //判断总体积
        if(!(receiptDetailEntity.getVolume()==null)){
            receiptHeaderEntity.setTotalVolume(receiptHeaderEntity.getTotalVolume().add(receiptDetailEntity.getVolume()).subtract(receiptDetailEntity1.getVolume()));
        }
        receiptDetailMapper.updateReceiptDetail(receiptDetailEntity,getSplitTableKey(dbShardVO));
        receiptHeaderEntity.setUpdateUser(receiptDetailEntity.getUpdateUser());//最后更新人
        receiptHeaderEntity.setUpdateTime(new Date().getTime());//最后更新时间
        TWmsReceiptHeaderDTO receiptHeaderDTO = BeanUtils.copyBeanPropertyUtils(receiptHeaderEntity,TWmsReceiptHeaderDTO.class);//拷贝属性
        receiptHeaderService.modifyReceiptHeader(receiptHeaderDTO,dbShardVO);//更新主表单数据
        return MessageResult.getSucMessage();
    }

    @Override
    public PageResponse<List> queryReceiptDetailByHeader(Map paramMap, DbShardVO dbShardVO) {
        paramMap.put("splitTableKey",getSplitTableKey(dbShardVO));
        List<TWmsReceiptDetailEntity> mapList = receiptDetailMapper.queryDetailsPages(paramMap);
        Integer totalSize = receiptDetailMapper.queryDetailPageCount(paramMap);
        PageResponse<List> response=new PageResponse();
        response.setTotal(totalSize);
        response.setRows(mapList);
        return response;
    }

    @Override
    public List<TWmsReceiptDetailEntity> getReceiptDetails(Long headerId, DbShardVO dbShardVO) {
        List<TWmsReceiptDetailEntity> mapList =receiptDetailMapper.queryReceiptDetails(headerId,getSplitTableKey(dbShardVO));
        if(CollectionUtils.isNotEmpty(mapList)){
            return mapList;
        }
        return null;
    }

    @Override
    public MessageResult batchDeleteReceiptDetails(Long[] receiptIds, DbShardVO dbShardVO) {
       for (Long receiptId:receiptIds) {
           receiptDetailMapper.deleteReceiptDetails (receiptId, getSplitTableKey(dbShardVO));
       }
       return MessageResult.getSucMessage();
    }

    /**
     * 从通知单创建入库单构建方法，无需将新建表的数据累加至主表
     * 无初始化状态设置
     * @param receiptDetailDTO
     * @param dbShardVO
     * @return
     */
    @Override
    public MessageResult createReceiptDetailFromAsn(TWmsReceiptDetailDTO receiptDetailDTO, DbShardVO dbShardVO) {
        TWmsReceiptDetailEntity receiptDetailEntity= BeanUtils.copyBeanPropertyUtils(receiptDetailDTO,TWmsReceiptDetailEntity.class);
        if(receiptDetailEntity.getId() == null){
            receiptDetailEntity.setId(autoIdClient.getAutoId(AutoIdConstants.TWmsReceiptDetailEntity));
        }
        receiptDetailMapper.insertReceiptDetail(receiptDetailEntity,getSplitTableKey(dbShardVO));//新增明细表单数据
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult deleteReceiptDetailsByHeaderId(Long headerId, DbShardVO dbShardVO) {
        receiptDetailMapper.deleteReceiptDetails(headerId,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }

    /**
     * 点击收货完成，根据通知明细单和商品明细表中数据创建入库明细数据
     * 判断是否第一次质检收货
     * @param asnDetailEntity
     * @param skuEntity
     * @param operationUser
     * @param receiptHeaderId
     * @param storageRoomId
     * @param dbShardVO
     * @return
     */
    @Override
    public MessageResult createReceiptDetailsFromQc(TWmsAsnDetailEntity asnDetailEntity, TWmsSkuEntity skuEntity, String operationUser, Long receiptHeaderId, String storageRoomId, Integer qcQty,String inventoryStatusCode, BigDecimal warehouseTemp, DbShardVO dbShardVO){
        TWmsReceiptDetailEntity receiptDetailEntityInDataBase = receiptDetailMapper.selectByAsnDetailId(asnDetailEntity.getId(),storageRoomId,getSplitTableKey(dbShardVO));
        if(receiptDetailEntityInDataBase == null){
            TWmsReceiptDetailEntity receiptDetailEntity = new TWmsReceiptDetailEntity();
            Long currentTime = new Date().getTime();
            receiptDetailEntity.setId(autoIdClient.getAutoId(AutoIdConstants.TWmsReceiptDetailEntity));
            receiptDetailEntity.setReceiptId(receiptHeaderId);//主表ID
            receiptDetailEntity.setWarehouseId(asnDetailEntity.getWarehouseId());//仓库id
            receiptDetailEntity.setTenantId(asnDetailEntity.getTenantId());//租户id
            receiptDetailEntity.setAsnDetailId(asnDetailEntity.getId());//通知单明细id
            receiptDetailEntity.setStorageRoomId(storageRoomId);//库房id,String字符串
            receiptDetailEntity.setSkuId(asnDetailEntity.getSkuId());//商品id
            receiptDetailEntity.setSku(asnDetailEntity.getSku());//商品条码
            receiptDetailEntity.setSkuName(asnDetailEntity.getSkuName());// 商品名称
            receiptDetailEntity.setSkuBarcode(asnDetailEntity.getSkuBarcode());//商品二维码
            receiptDetailEntity.setInventoryStatusCode(InventoryStatusCode.GOOD.toString());
            if (!(asnDetailEntity.getGrossWeight()==null)){
                receiptDetailEntity.setGrossWeight(asnDetailEntity.getGrossWeight());//商品毛重
            }
            if(!(asnDetailEntity.getNetWeight()==null)){
                receiptDetailEntity.setNetWeight(asnDetailEntity.getNetWeight());//商品净重
            }
            if(!(asnDetailEntity.getVolume()==null)){
                receiptDetailEntity.setVolume(asnDetailEntity.getVolume()); //商品体积
            }
            receiptDetailEntity.setReceivedQty(qcQty);//检收数量赋值已收货数量
            receiptDetailEntity.setInventoryStatusCode(inventoryStatusCode);//商品品质
            receiptDetailEntity.setProperty(asnDetailEntity.getProperty());//商品用途赋值
            receiptDetailEntity.setReceiptTime(currentTime);//收货时间
            receiptDetailEntity.setWarehouseTemp(warehouseTemp);//入库时仓库温度
            receiptDetailEntity.setCreateUser(operationUser);
            receiptDetailEntity.setCreateTime(currentTime);
            receiptDetailEntity.setUpdateUser(operationUser);
            receiptDetailEntity.setUpdateTime(currentTime);
            //商品属性赋值
            receiptDetailEntity.setCategorysId(skuEntity.getCategorysId());//商品种类ID
            receiptDetailEntity.setSpec(skuEntity.getSpec());// 规格
            receiptDetailEntity.setUnitType(skuEntity.getUnitType());
            receiptDetailEntity.setCostPrice(skuEntity.getCostPrice());//单价
            receiptDetailMapper.insertReceiptDetail(receiptDetailEntity,getSplitTableKey(dbShardVO));
        }else {
            // 更新库房，检收数量
            receiptDetailEntityInDataBase.setReceivedQty(receiptDetailEntityInDataBase.getReceivedQty()+qcQty);
            receiptDetailEntityInDataBase.setUpdateUser(operationUser);
            receiptDetailEntityInDataBase.setUpdateTime(new Date().getTime());
            receiptDetailMapper.updateReceiptDetail(receiptDetailEntityInDataBase,getSplitTableKey(dbShardVO));
        }
        //仅仅更新主表的收货数量即可
        TWmsReceiptHeaderEntity receiptHeaderEntity=receiptHeaderService.findByPrimaryKey(receiptHeaderId,dbShardVO);
        receiptHeaderEntity.setTotalQty(receiptHeaderEntity.getTotalQty()+qcQty);//这里可以直接加，头表已经创建
        TWmsReceiptHeaderDTO receiptHeaderDTO = BeanUtils.copyBeanPropertyUtils(receiptHeaderEntity,TWmsReceiptHeaderDTO.class);
        receiptHeaderService.modifyReceiptHeader(receiptHeaderDTO,dbShardVO);
        return MessageResult.getSucMessage();
    }

    @Override
    public List<TWmsReceiptDetailEntity> getReceiptDetailList(TWmsReceiptDetailDTO receiptDetailDTO,List<Long> headerIds, DbShardVO dbShardVO) {
        TWmsReceiptDetailEntity receiptDetailEntity = BeanUtils.copyBeanPropertyUtils(receiptDetailDTO,TWmsReceiptDetailEntity.class);
        return receiptDetailMapper.queryReceiptDetailPages(receiptDetailEntity,headerIds,getSplitTableKey(dbShardVO));
    }

    @Override
    public Integer queryReceiptDetailPageCount(TWmsReceiptDetailDTO receiptDetailDTO,List<Long> headerIds, DbShardVO dbShardVO) {
        TWmsReceiptDetailEntity receiptDetailEntity = BeanUtils.copyBeanPropertyUtils(receiptDetailDTO,TWmsReceiptDetailEntity.class);
        return receiptDetailMapper.queryReceiptDetailsPageCount(receiptDetailEntity,headerIds,getSplitTableKey(dbShardVO));
    }

    @Override
    public Integer queryReceiptSummaryPageCount(TWmsReceiptDetailDTO receiptDetailDTO,List<Long> headerIds, DbShardVO dbShardVO) {
        TWmsReceiptDetailEntity receiptDetailEntity = BeanUtils.copyBeanPropertyUtils(receiptDetailDTO,TWmsReceiptDetailEntity.class);
        return receiptDetailMapper.queryReceiptSummaryPageCount(receiptDetailEntity,headerIds,getSplitTableKey(dbShardVO));
    }

    @Override
    public List<TWmsReceiptDetailEntity> getReceiptSummaryList(TWmsReceiptDetailDTO receiptDetailDTO,List<Long> headerIds, DbShardVO dbShardVO) {
        TWmsReceiptDetailEntity receiptDetailEntity = BeanUtils.copyBeanPropertyUtils(receiptDetailDTO,TWmsReceiptDetailEntity.class);
        return receiptDetailMapper.queryReceiptSummaryPages(receiptDetailEntity,headerIds,getSplitTableKey(dbShardVO));
    }
}
