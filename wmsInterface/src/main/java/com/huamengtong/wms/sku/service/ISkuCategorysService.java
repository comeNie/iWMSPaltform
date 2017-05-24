package com.huamengtong.wms.sku.service;

import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.TWmsSkuCategorysDTO;
import com.huamengtong.wms.entity.sku.TWmsSkuCategorysEntity;


import java.util.List;

/**
 * Created by mario on 2016/10/19.
 */
public interface ISkuCategorysService {

    PageResponse<List<TWmsSkuCategorysEntity>> querySkuCategorysPages(TWmsSkuCategorysDTO skuCategorysDTO, DbShardVO dbShardVO);

    List<TWmsSkuCategorysEntity> findByParentId(Long parentId, DbShardVO dbShardVO);

    TWmsSkuCategorysEntity findByPrimaryKey(Long id, DbShardVO dbShardVO);

    MessageResult removeByPrimaryKey(Long id, DbShardVO dbShardVO);

    MessageResult createSkuCategorys(TWmsSkuCategorysDTO skuCategorysDTO, DbShardVO dbShardVO);

    MessageResult modifySkuCategorys(TWmsSkuCategorysDTO skuCategorysDTO, DbShardVO dbShardVO);

    List<TWmsSkuCategorysEntity> findAll(DbShardVO dbShardVO);

}
