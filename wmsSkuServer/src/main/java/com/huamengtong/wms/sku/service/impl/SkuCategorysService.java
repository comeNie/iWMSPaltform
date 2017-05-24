package com.huamengtong.wms.sku.service.impl;

import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.service.BaseService;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.TWmsSkuCategorysDTO;
import com.huamengtong.wms.entity.sku.TWmsSkuCategorysEntity;
import com.huamengtong.wms.sku.mapper.SkuCategorysMapper;
import com.huamengtong.wms.sku.service.ISkuCategorysService;
import com.huamengtong.wms.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by mario on 2016/10/19.
 */

@Service
public class SkuCategorysService extends BaseService implements ISkuCategorysService  {
    @Autowired
    SkuCategorysMapper skuCategorysMapper;

    @Override
    public PageResponse<List<TWmsSkuCategorysEntity>> querySkuCategorysPages(TWmsSkuCategorysDTO skuCategorysDTO, DbShardVO dbShardVO) {
        TWmsSkuCategorysEntity SkuCategorysentity = BeanUtils.copyBeanPropertyUtils(skuCategorysDTO,TWmsSkuCategorysEntity.class);
        List<TWmsSkuCategorysEntity> SkuCategorysEntityList = skuCategorysMapper.querySkuCategorysPages(SkuCategorysentity,getSplitTableKey(dbShardVO));
        Integer totalSize = skuCategorysMapper.querySkuCategorysPageCount(SkuCategorysentity,getSplitTableKey(dbShardVO));
        PageResponse<List<TWmsSkuCategorysEntity>> response = new PageResponse<>();
        response.setTotal(totalSize);
        response.setRows(SkuCategorysEntityList);
        return response;
    }

    @Override
    public List<TWmsSkuCategorysEntity> findByParentId(Long parentId, DbShardVO dbShardVO) {
        return skuCategorysMapper.selectByParentId(parentId,getSplitTableKey(dbShardVO));
    }


    @Override
    public TWmsSkuCategorysEntity findByPrimaryKey(Long id, DbShardVO dbShardVO) {
        return skuCategorysMapper.selectByPrimaryKey(id,getSplitTableKey(dbShardVO));
    }

    @Override
    public MessageResult removeByPrimaryKey(Long id, DbShardVO dbShardVO) {
         skuCategorysMapper.deleteSkuCategorys(id,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult createSkuCategorys(TWmsSkuCategorysDTO skuCategorysDTO, DbShardVO dbShardVO) {
        TWmsSkuCategorysEntity entity = BeanUtils.copyBeanPropertyUtils(skuCategorysDTO,TWmsSkuCategorysEntity.class);
        skuCategorysMapper.insertSkuCategorys(entity,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult modifySkuCategorys(TWmsSkuCategorysDTO skuCategorysDTO, DbShardVO dbShardVO) {
        TWmsSkuCategorysEntity entity = BeanUtils.copyBeanPropertyUtils(skuCategorysDTO,TWmsSkuCategorysEntity.class);
        skuCategorysMapper.updateSkuCategorys(entity,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }

    @Override
    public List<TWmsSkuCategorysEntity> findAll(DbShardVO dbShardVO) {
        return skuCategorysMapper.selectAll(getSplitTableKey(dbShardVO));
    }
}
