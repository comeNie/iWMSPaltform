package com.huamengtong.wms.sku.service.impl;

import com.huamengtong.wms.client.IAutoIdClient;
import com.huamengtong.wms.constants.AutoIdConstants;
import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.service.BaseService;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.sku.TWmsSkuDTO;
import com.huamengtong.wms.em.DataSource;
import com.huamengtong.wms.entity.sku.TWmsSkuCargoOwnerEntity;
import com.huamengtong.wms.entity.sku.TWmsSkuEntity;
import com.huamengtong.wms.main.service.ICargoOwnerService;
import com.huamengtong.wms.sku.mapper.SkuMapper;
import com.huamengtong.wms.sku.service.ISkuCargoOwnerService;
import com.huamengtong.wms.sku.service.ISkuService;
import com.huamengtong.wms.utils.BeanUtils;
import com.huamengtong.wms.utils.StringPool;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class SkuService extends BaseService implements ISkuService {

    @Autowired
    SkuMapper skuMapper;

    @Autowired
    IAutoIdClient autoIdClient;

    @Autowired
    ICargoOwnerService cargoOwnerService;

    @Autowired
    ISkuCargoOwnerService skuCargoOwnerService;


    @Override
    public PageResponse<List<TWmsSkuEntity>> querySkuPages(TWmsSkuDTO skuDTO, DbShardVO dbshardVO) {
        TWmsSkuEntity skuEntity = BeanUtils.copyBeanPropertyUtils(skuDTO, TWmsSkuEntity.class);
        List<TWmsSkuEntity> skuEntityList = skuMapper.selectSkuPages(skuEntity, getSplitTableKey(dbshardVO));
        Integer totalSize = skuMapper.selectSkuPageCount(skuEntity, getSplitTableKey(dbshardVO));
        PageResponse<List<TWmsSkuEntity>> response = new PageResponse<>();
        response.setTotal(totalSize);
        response.setRows(skuEntityList);
        return response;
    }

    @Override
    public MessageResult createSku(TWmsSkuDTO skuDTO, DbShardVO dbshardVO) throws Exception {
        if(findByBarcode(skuDTO.getBarcode(),dbshardVO) != null){
            return MessageResult.getMessage("E00017",new Object[]{skuDTO.getItemName()});
        }
        if(findByItemName(skuDTO.getItemName(),dbshardVO) != null){
            return MessageResult.getMessage("E00018",new Object[]{skuDTO.getItemName()});
        }
        if(findByUpc(skuDTO.getUpc(),dbshardVO) != null){
            return MessageResult.getMessage("E00019",new Object[]{skuDTO.getItemName()});
        }
        TWmsSkuEntity skuEntity = BeanUtils.copyBeanPropertyUtils(skuDTO, TWmsSkuEntity.class);
        skuEntity.setId(autoIdClient.getAutoId(AutoIdConstants.TWmsSkuEntity));
        skuMapper.insertSku(skuEntity, getSplitTableKey(dbshardVO));
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult updateSku(TWmsSkuDTO skuDTO, DbShardVO dbshardVO) {
        TWmsSkuEntity skuEntity = BeanUtils.copyBeanPropertyUtils(skuDTO, TWmsSkuEntity.class);
        skuMapper.updateSku(skuEntity, getSplitTableKey(dbshardVO));
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult removeSku(Long id, DbShardVO dbShardVO) {
        List<TWmsSkuCargoOwnerEntity> skuCargoOwnerEntityList = skuCargoOwnerService.findBysku(id,getSkuDbShareVO(dbShardVO));
        if (CollectionUtils.isNotEmpty(skuCargoOwnerEntityList)){
            return MessageResult.getMessage("E11005");
        }
        skuMapper.deleteSku(id, getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }

    @Override
    public TWmsSkuEntity querySkuById(Long id, DbShardVO dbShardVO) {
        return skuMapper.selectSkuById(id, getSplitTableKey(dbShardVO));
    }

    @Override
    public List<TWmsSkuEntity> findSkuLists(String ids, DbShardVO dbShardVO) {
        if (StringUtils.isEmpty(ids)){
            return null;
        }
        String[] arrayIds = ids.split(StringPool.COMMA);
        List<TWmsSkuEntity> skuEntityLists = new ArrayList<>();
        for (String id : arrayIds) {
            TWmsSkuEntity skuEntity = this.querySkuById(Long.parseLong(id), getSkuDbShareVO(dbShardVO));
            skuEntityLists.add(skuEntity);
        }
        return CollectionUtils.isEmpty(skuEntityLists) ? null : skuEntityLists;
    }

     @Override
    public MessageResult removeSkuBatch(String ids, DbShardVO dbShardVO) {
        String [] arrayIds = ids.split(StringPool.COMMA);
        List<Long> list = new ArrayList<Long>();
        for(int i = 0; i < arrayIds.length;i++){
            list.add(Long.parseLong(arrayIds[i]));
        }
        skuMapper.deleteSkuBatch(list,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    } 
    
    @Override
    public MessageResult updateSkuUpLoad(List list, DbShardVO dbShardVO)  {
        if(CollectionUtils.isEmpty(list)){
            return MessageResult.getMessage("E00016");
        }
        Set set = new HashSet(list);
        List<TWmsSkuEntity> skuEntityLists = new ArrayList<TWmsSkuEntity>();
        Long tenantId = dbShardVO.getCurrentUser().getTenantId();
        for (Object obj:set) {
            Map<String, String> mapParam = (Map) obj;
            TWmsSkuEntity skuEntity = new TWmsSkuEntity();
            skuEntity.setId(autoIdClient.getAutoId(AutoIdConstants.TWmsSkuEntity));
            skuEntity.setTenantId(tenantId);
            if (mapParam.containsKey("商品编码")) {
                skuEntity.setSku(MapUtils.getString(mapParam, "商品编码"));
            }
            if (mapParam.containsKey("商品条码")) {
                skuEntity.setBarcode(MapUtils.getString(mapParam, "商品条码"));
            }
            if (mapParam.containsKey("商品名称")) {
                skuEntity.setItemName(MapUtils.getString(mapParam, "商品名称"));
            }
            if (mapParam.containsKey("商品类别")) {
                skuEntity.setCategorysId(MapUtils.getLong(mapParam, "商品类别"));
            }
            if (mapParam.containsKey("商品规格")) {
                skuEntity.setSpec(MapUtils.getString(mapParam, "商品规格"));
            }
            if (mapParam.containsKey("单位类型")) {
                skuEntity.setUnitType(MapUtils.getString(mapParam, "单位类型"));
            }
            if (mapParam.containsKey("来源地")){
                skuEntity.setOriginPlace(MapUtils.getString(mapParam,"来源地"));
            }
            skuEntity.setDatasourceCode(DataSource.MANUAL.toString());
            skuEntity.setCreateUser(dbShardVO.getCurrentUser().getUserName());
            skuEntity.setCreateTime(new java.util.Date().getTime());
            skuEntity.setUpdateUser(dbShardVO.getCurrentUser().getUserName());
            skuEntity.setUpdateTime(new java.util.Date().getTime());

            if (validateHasSku(skuEntity.getCargoOwnerId(), skuEntity.getBarcode(), dbShardVO)) {
                skuEntityLists.add(skuEntity);
            }
        }
        skuMapper.insertBatchSku(skuEntityLists,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }

    @Override
    public Boolean validateHasSku(Long cargoOwnerId, String barcode, DbShardVO dbShardVO){
       List<TWmsSkuEntity>  skuEntity  = skuMapper.selectValidateHasSku(cargoOwnerId,barcode,getSplitTableKey(dbShardVO));
        return CollectionUtils.isEmpty(skuEntity);
    }

    @Override
    public TWmsSkuEntity findByPrimaryKey(Long id, DbShardVO dbShardVO) {
        return skuMapper.selectSkuById(id,getSplitTableKey(dbShardVO));
    }

    /**
     * 根据barcode查询商品
     * @param barcode
     * @param dbShardVO
     * @return
     */
    @Override
    public TWmsSkuEntity findByBarcode(String barcode, DbShardVO dbShardVO) {
        return skuMapper.getSkuByBarcode(barcode,getSplitTableKey(dbShardVO));
    }

    @Override
    public List<TWmsSkuEntity> querySkuAll(DbShardVO dbShardVO) {
        return skuMapper.selectSkuAll(getSplitTableKey(dbShardVO));
    }

    @Override
    public List<TWmsSkuEntity> findByCargoOwnerId(Long cargoOwnerId, DbShardVO dbShardVO) {
        return skuMapper.selectByCargoOwnerId(cargoOwnerId,getSplitTableKey(dbShardVO));
    }

    @Override
    public List<Long> findSkuLists(TWmsSkuDTO skuDTO, DbShardVO dbShardVO) {
        TWmsSkuEntity skuEntity = BeanUtils.copyBeanPropertyUtils(skuDTO,TWmsSkuEntity.class);
        return skuMapper.selectSkuIdList(skuEntity,getSplitTableKey(dbShardVO));
    }

    @Override
    public List<TWmsSkuEntity> findByIdList(List<Long> list, DbShardVO dbShardVO) {
        return skuMapper.selectByIdList(list,getSplitTableKey(dbShardVO));
    }

    @Override
    public TWmsSkuEntity findSkuByItemName(String itemName, DbShardVO dbShardVO) {
        return skuMapper.getSkuByItemName(itemName,getSplitTableKey(dbShardVO));
    }

    @Override
    public List<TWmsSkuEntity> findSkuCargoOwner(String sku,String itemName , Long cargoOwnerId ,Long skuId, DbShardVO dbShardVO) {
        Map<String,Object> map = new HashMap<>();
        if(sku !=null){
            map.put("sku",sku);
        }
        if(itemName!=null){
            map.put("itemName",itemName);
        }
        if(cargoOwnerId !=null){
            map.put("cargoOwnerId",cargoOwnerId);
        }
        if(skuId !=null){
            map.put("skuId",skuId);
        }
        map.put("splitTableKey",getSplitTableKey(dbShardVO));
        return skuMapper.findSkuCargoOwner(map);
    }

    @Override
    public PageResponse<List<TWmsSkuEntity>> querySkuPagesByCargoOwner(TWmsSkuDTO skuDTO, DbShardVO dbshardVO) {
        TWmsSkuEntity skuEntity = BeanUtils.copyBeanPropertyUtils(skuDTO, TWmsSkuEntity.class);
        List<TWmsSkuEntity> skuEntityList = skuMapper.selectSkuPagesByCargoOwnerId(skuEntity, getSplitTableKey(dbshardVO));
        if(CollectionUtils.isEmpty(skuEntityList)){
            return null;
        }
        Integer totalSize = skuMapper.queryPageCountByCargoOwnerId(skuEntity,getSplitTableKey(dbshardVO));
        PageResponse<List<TWmsSkuEntity>> response = new PageResponse<>();
        response.setTotal(totalSize);
        response.setRows(skuEntityList);
        return response;
    }

    @Override
    public TWmsSkuEntity findByItemName(String itemName, DbShardVO dbShardVO) {
        return skuMapper.getSkuByItemName(itemName,getSplitTableKey(dbShardVO));
    }

    @Override
    public TWmsSkuEntity findByUpc(String upc, DbShardVO dbShardVO) {
        return skuMapper.selectSkuByUpc(upc,getSplitTableKey(dbShardVO));
    }
}
