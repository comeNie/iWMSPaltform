package com.huamengtong.wms.inwh.service.impl;

import com.huamengtong.wms.client.IAutoIdClient;
import com.huamengtong.wms.constants.AutoIdConstants;
import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.service.BaseService;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.inwh.TWmsAsnDetailDTO;
import com.huamengtong.wms.dto.inwh.TWmsAsnHeaderDTO;
import com.huamengtong.wms.dto.sku.TWmsSkuCargoOwnerDTO;
import com.huamengtong.wms.em.TicketStatusCode;
import com.huamengtong.wms.entity.inwh.TWmsAsnDetailEntity;
import com.huamengtong.wms.entity.inwh.TWmsAsnHeaderEntity;
import com.huamengtong.wms.entity.sku.TWmsSkuCargoOwnerEntity;
import com.huamengtong.wms.inwh.mapper.AsnDetailMapper;
import com.huamengtong.wms.inwh.service.IAsnDetailService;
import com.huamengtong.wms.inwh.service.IAsnHeaderService;
import com.huamengtong.wms.sku.service.ISkuCargoOwnerService;
import com.huamengtong.wms.utils.BeanUtils;
import com.huamengtong.wms.utils.DateUtil;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AsnDetailService extends BaseService implements IAsnDetailService {

    @Autowired
    AsnDetailMapper asnDetailMapper;

    @Autowired
    IAsnHeaderService asnHeaderService;

    @Autowired
    ISkuCargoOwnerService skuCargoOwnerService;

    @Autowired
    IAutoIdClient autoIdClient;

    @Override
    public PageResponse<List<TWmsAsnDetailEntity>> queryAsnDetailPages(Map map, DbShardVO dbShardVO) {
        List<TWmsAsnDetailEntity> asnDetailEntities = asnDetailMapper.queryAsnDetailPages(map,getSplitTableKey(dbShardVO));
        Integer totalSize=asnDetailMapper.queryAsnDetailPageCount(map,getSplitTableKey(dbShardVO));
        PageResponse<List<TWmsAsnDetailEntity>> response=new PageResponse();
        response.setTotal(totalSize);
        response.setRows(asnDetailEntities);
        return response;
    }

    @Override
    public TWmsAsnDetailEntity findByPrimaryKey(Long id, DbShardVO dbShardVO) {
        return asnDetailMapper.selectByPrimaryKey(id,getSplitTableKey(dbShardVO));
    }

    /**
     *  删除明细，同时更新主表的总净重&总毛重&总体积&总数量
     * @param id
     * @param dbShardVO
     * @return
     */
    @Override
    public MessageResult removeByPrimaryKey(Long id,String operationUser, DbShardVO dbShardVO) {
        TWmsAsnDetailEntity asnDetailEntity = asnDetailMapper.selectByPrimaryKey(id,getSplitTableKey(dbShardVO));
        TWmsAsnHeaderEntity asnHeaderEntity = asnHeaderService.findByPrimaryKey(asnDetailEntity.getAsnId(),dbShardVO);
        //主表中数据更新
        asnHeaderEntity.setTotalCategoryQty(asnHeaderEntity.getTotalCategoryQty()-1);//总品种数减少1
        asnHeaderEntity.setTotalQty(asnHeaderEntity.getTotalQty()-asnDetailEntity.getExpectedQty());
        asnHeaderEntity.setTotalNetWeight(asnHeaderEntity.getTotalNetWeight().subtract(asnDetailEntity.getNetWeight()));//总净重更新
        asnHeaderEntity.setTotalGrossWeight(asnHeaderEntity.getTotalGrossWeight().subtract(asnDetailEntity.getGrossWeight()));//总毛重
        asnHeaderEntity.setTotalCube(asnHeaderEntity.getTotalCube().subtract(asnDetailEntity.getVolume()));//总体积
        asnHeaderEntity.setUpdateUser(operationUser);//最后更新人
        asnHeaderEntity.setUpdateTime(new Date().getTime());//最后更新时间

        asnDetailMapper.deleteByPrimaryKey(id,getSplitTableKey(dbShardVO));//删除明细

        TWmsAsnHeaderDTO asnHeaderDTO = BeanUtils.copyBeanPropertyUtils(asnHeaderEntity,TWmsAsnHeaderDTO.class);
        asnHeaderService.updateAsnHeader(asnHeaderDTO,dbShardVO);//更新主表
        return MessageResult.getSucMessage();
    }

    /**
     * 增加入库通知明细单，需把总数量累加后写至主表中
     * @param asnDetailDTO
     * @param dbShardVO
     * @return
     */
    @Override
    public MessageResult insertAsnDetail(TWmsAsnDetailDTO asnDetailDTO, DbShardVO dbShardVO) {
        TWmsAsnDetailEntity asnDetailEntity1 = asnDetailMapper.selectByHeaderIdAndSkuId(asnDetailDTO.getAsnId(),asnDetailDTO.getSkuId(),getSplitTableKey(dbShardVO));
        if (!(asnDetailEntity1==null)){
            return MessageResult.getMessage("E51010");
        }
        TWmsAsnDetailEntity asnDetailEntity = BeanUtils.copyBeanPropertyUtils(asnDetailDTO,TWmsAsnDetailEntity.class);
        if(asnDetailEntity.getSkuId() ==null){
            return MessageResult.getMessage("E51016");
        }
        asnDetailEntity.setId(autoIdClient.getAutoId(AutoIdConstants.TWmsAsnDetailEntity));//设置ID
        asnDetailEntity.setStatusCode(TicketStatusCode.INIT.toString());//新建通知明细单状态全部为初始化
        TWmsAsnHeaderEntity asnHeaderEntity = asnHeaderService.findByPrimaryKey(asnDetailEntity.getAsnId(),dbShardVO);
        //判断数据，回写主表中总净重&总毛重&总体积
        summation(asnHeaderEntity,asnDetailEntity);
        if (!(asnHeaderEntity.getFromOrderNo()==null) && !asnHeaderEntity.getFromOrderNo().isEmpty()){
            asnDetailEntity.setFromOrderNo(asnHeaderEntity.getFromOrderNo());
        }
        //货主和商品进行绑定
        TWmsSkuCargoOwnerEntity skuCargoOwnerEntity = skuCargoOwnerService.selectBySkuIdAndCargoOwnerId(asnDetailEntity.getSkuId(),asnHeaderEntity.getCargoOwnerId(),getSkuDbShareVO(dbShardVO));
        if(skuCargoOwnerEntity == null){
            TWmsSkuCargoOwnerDTO skuCargoOwnerDTO = new TWmsSkuCargoOwnerDTO();
            skuCargoOwnerDTO.setSkuId(asnDetailEntity.getSkuId());
            skuCargoOwnerDTO.setSku(asnDetailEntity.getSku());
            skuCargoOwnerDTO.setCargoOwnerId(asnHeaderEntity.getCargoOwnerId());
            skuCargoOwnerDTO.setCargoOwnerBarcode(DateUtil.getNowDateTime().substring(0,10).replaceAll("-","").trim().concat(autoIdClient.getAutoId(AutoIdConstants.TWmsSkuCargoOwnerEntity,1).toString()));
            asnDetailEntity.setSkuBarcode(skuCargoOwnerDTO.getCargoOwnerBarcode());
            skuCargoOwnerService.insertSkuCargoOwner(skuCargoOwnerDTO,getSkuDbShareVO(dbShardVO));
        }else {
            asnDetailEntity.setSkuBarcode(skuCargoOwnerEntity.getCargoOwnerBarcode());
        }
        asnDetailMapper.insertAsnDetail(asnDetailEntity,getSplitTableKey(dbShardVO));//插入明细表中数据
        TWmsAsnHeaderDTO asnHeaderDTO = BeanUtils.copyBeanPropertyUtils(asnHeaderEntity,TWmsAsnHeaderDTO.class);
        asnHeaderService.updateAsnHeader(asnHeaderDTO,dbShardVO);//更新主表的数据
        return MessageResult.getSucMessage();
    }

    /**
     * 对插入对象进行判断，更新对象中数据
     * @param asnHeaderEntity  主表对象
     * @param asnDetailEntity  插入对象
     * @return
     */
    private void summation(TWmsAsnHeaderEntity asnHeaderEntity,TWmsAsnDetailEntity asnDetailEntity){
        if (asnHeaderEntity.getTotalCategoryQty()==null){
            asnHeaderEntity.setTotalCategoryQty(1);
        }else{
            asnHeaderEntity.setTotalCategoryQty(asnHeaderEntity.getTotalCategoryQty()+1);//总品种数增加1
        }
        if(!(asnDetailEntity.getExpectedQty()==null)) {
            asnHeaderEntity.setTotalQty(asnHeaderEntity.getTotalQty() + asnDetailEntity.getExpectedQty());//主表总数量
        }
        if(!(asnDetailEntity.getNetWeight()==null)){
            asnHeaderEntity.setTotalNetWeight(asnHeaderEntity.getTotalNetWeight().add(asnDetailEntity.getNetWeight()));//累加总净重
        }
        if(!(asnDetailEntity.getGrossWeight()==null)){
            asnHeaderEntity.setTotalGrossWeight(asnHeaderEntity.getTotalGrossWeight().add(asnDetailEntity.getGrossWeight()));//累加总毛重
        }
        if(!(asnDetailEntity.getVolume()==null)){
            asnHeaderEntity.setTotalCube(asnHeaderEntity.getTotalCube().add(asnDetailEntity.getVolume()));//累加总体积
        }
        asnHeaderEntity.setUpdateUser(asnDetailEntity.getUpdateUser());//最后更新人
        asnHeaderEntity.setUpdateTime(new Date().getTime());//最后更新时间
    }

    /**
     * 更新数据时判断明细表中的总净重&总毛重&总体积是否被更改,回写主表数据
     * @param asnDetailDTO
     * @param dbShardVO
     * @return
     */
    @Override
    public MessageResult updateAsnDetail(TWmsAsnDetailDTO asnDetailDTO, DbShardVO dbShardVO) {
        if (!asnDetailDTO.getStatusCode().equals(TicketStatusCode.INIT.toString())){
            return MessageResult.getMessage("E51011",new Object[]{asnDetailDTO.getId()});
        }
        TWmsAsnDetailEntity asnDetailEntity=BeanUtils.copyBeanPropertyUtils(asnDetailDTO,TWmsAsnDetailEntity.class);
        if(asnDetailEntity.getSkuId() ==null){
            return MessageResult.getMessage("E51016");
        }
        //从明细表中取出当前数据
        TWmsAsnDetailEntity asnDetailEntity1 = asnDetailMapper.selectByPrimaryKey(asnDetailEntity.getId(),getSplitTableKey(dbShardVO));
        //从主表中取出数据
        TWmsAsnHeaderEntity asnHeaderEntity = asnHeaderService.findByPrimaryKey(asnDetailEntity.getAsnId(),dbShardVO);
        summationQty(asnHeaderEntity,asnDetailEntity,asnDetailEntity1);//更新主表中的数据
        asnDetailMapper.updateAsnDetail(asnDetailEntity,getSplitTableKey(dbShardVO));
        TWmsAsnHeaderDTO asnHeaderDTO = BeanUtils.copyBeanPropertyUtils(asnHeaderEntity,TWmsAsnHeaderDTO.class);
        asnHeaderService.updateAsnHeader(asnHeaderDTO,dbShardVO);//更新主表数据
        return MessageResult.getSucMessage();
    }

    /**
     * 比较对象，更新主表中的总净重&总毛重&总体积
     * 更新主表中信息以及最后更新人和最后更新时间
     * @param asnHeaderEntity  主表对象
     * @param asnDetailEntity  更改后的明细对象
     * @param asnDetailEntity1 数据库表中的当前对象
     * @return
     */
    private void summationQty(TWmsAsnHeaderEntity asnHeaderEntity,TWmsAsnDetailEntity asnDetailEntity,TWmsAsnDetailEntity asnDetailEntity1){
        // 总数量
        if(!(asnDetailEntity.getExpectedQty()==null)){
            asnHeaderEntity.setTotalQty(asnHeaderEntity.getTotalQty()+asnDetailEntity.getExpectedQty()-asnDetailEntity1.getExpectedQty());
        }
        //总净重
        if(!(asnDetailEntity.getNetWeight()==null)){
            asnHeaderEntity.setTotalNetWeight(asnHeaderEntity.getTotalNetWeight().add(asnDetailEntity.getNetWeight()).subtract(asnDetailEntity1.getNetWeight()));
        }
        //总毛重
        if (!(asnDetailEntity.getGrossWeight()==null)){
            asnHeaderEntity.setTotalGrossWeight(asnHeaderEntity.getTotalGrossWeight().add(asnDetailEntity.getGrossWeight()).subtract(asnDetailEntity1.getGrossWeight()));
        }
        //总体积
        if(!(asnDetailEntity.getVolume()==null)){
            asnHeaderEntity.setTotalCube(asnHeaderEntity.getTotalCube().add(asnDetailEntity.getVolume()).subtract(asnDetailEntity1.getVolume()));
        }
        asnHeaderEntity.setUpdateUser(asnDetailEntity.getUpdateUser());
        asnHeaderEntity.setUpdateTime(new Date().getTime());
    }

    @Override
    public PageResponse<List> queryDetailsByHeaderId(Map map, DbShardVO dbShardVO) {
        map.put("splitTableKey",getSplitTableKey(dbShardVO));
        List<TWmsAsnDetailEntity> asnDetailEntities=asnDetailMapper.queryDetailsPages(map);
        Integer totalSize = asnDetailMapper.queryDetailPageCount(map);
        PageResponse<List> response = new PageResponse();
        response.setTotal(totalSize);
        response.setRows(asnDetailEntities);
        return response;
    }

    @Override
    public List<TWmsAsnDetailEntity> findAsnDetailsByHeaderId(Long headerId,DbShardVO dbShardVO) {
        Map<String,Object> map=new HashedMap();
        map.put("headerId",headerId);
        map.put("splitTableKey",getSplitTableKey(dbShardVO));
        return asnDetailMapper.selectAsnDetailsByHeaderId(map);
    }

    @Override
    public MessageResult deleteAsnDetailsByHeaderId(Long headerId, DbShardVO dbShardVO) {
        asnDetailMapper.deleteAsnDetailsByHeaderId(headerId,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }

    @Override
    public List<TWmsAsnDetailEntity> findByHeaderIdAndSku(Long headerId, String sku, DbShardVO dbShardVO) {
        Map<String,Object> map=new HashMap();
        map.put("headerId",headerId);
        map.put("sku",sku);
        map.put("splitTableKey",getSplitTableKey(dbShardVO));
        return asnDetailMapper.selectByHeaderIdAndSku(map);
    }

    @Override
    public MessageResult updateAsnDetailStatus(String operationCode, String operationUser, TWmsAsnDetailDTO asnDetailDTO,DbShardVO dbShardVO) {
        if(operationCode.equals(TicketStatusCode.SUBMIT.toString()) && asnDetailDTO.getStatusCode().equals(TicketStatusCode.INIT.toString())){
            asnDetailDTO.setStatusCode(TicketStatusCode.SUBMIT.toString());
            asnDetailDTO.setUpdateUser(operationUser);
            asnDetailDTO.setUpdateTime(new Date().getTime());
        }
        if(operationCode.equals(TicketStatusCode.REPEALED.toString()) && asnDetailDTO.getStatusCode().equals(TicketStatusCode.SUBMIT.toString())){
            asnDetailDTO.setStatusCode(TicketStatusCode.INIT.toString());
            asnDetailDTO.setUpdateUser(operationUser);
            asnDetailDTO.setUpdateTime(new Date().getTime());
        }
        if (operationCode.equals(TicketStatusCode.FINISH.toString()) && asnDetailDTO.getStatusCode().equals(TicketStatusCode.SUBMIT.toString())){
            asnDetailDTO.setStatusCode(TicketStatusCode.FINISH.toString());
            asnDetailDTO.setUpdateUser(operationUser);
            asnDetailDTO.setUpdateTime(new Date().getTime());
        }
        TWmsAsnDetailEntity asnDetailEntity = BeanUtils.copyBeanPropertyUtils(asnDetailDTO,TWmsAsnDetailEntity.class);
        asnDetailMapper.updateAsnDetail(asnDetailEntity,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }


    @Override
    public MessageResult updateFromQcReceipt(Integer qcQty, String operationUser,TWmsAsnDetailDTO asnDetailDTO, DbShardVO dbShardVO) {
        asnDetailDTO.setLastReceiptTime(new Date().getTime());//收货时间
        asnDetailDTO.setReceivedQty(asnDetailDTO.getReceivedQty()+qcQty);//检收数量
        asnDetailDTO.setStatusCode(TicketStatusCode.FINISH.toString());
        asnDetailDTO.setUpdateUser(operationUser);
        asnDetailDTO.setUpdateTime(new Date().getTime());
        TWmsAsnDetailEntity asnDetailEntity = BeanUtils.copyBeanPropertyUtils(asnDetailDTO,TWmsAsnDetailEntity.class);
        asnDetailMapper.updateAsnDetail(asnDetailEntity,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }
}
