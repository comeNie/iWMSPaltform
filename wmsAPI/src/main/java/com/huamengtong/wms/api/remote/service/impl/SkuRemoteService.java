package com.huamengtong.wms.api.remote.service.impl;

import com.huamengtong.wms.api.dto.CreateSkuDTO;
import com.huamengtong.wms.api.remote.service.ISkuRemoteService;
import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.dto.sku.TWmsSkuDTO;
import com.huamengtong.wms.sku.service.ISkuService;
import com.huamengtong.wms.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkuRemoteService implements ISkuRemoteService {

    @Autowired
    ISkuService skuService;

    /***
     * 创建商品信息
     * @param skuDTOList
     * @param dbShardVO
     * @return
     * @throws Exception
     */
    @Override
    public MessageResult createSku(List<CreateSkuDTO> skuDTOList, DbShardVO dbShardVO) throws Exception {
        for (CreateSkuDTO skuDTO : skuDTOList) {
            TWmsSkuDTO wmsSkuDTO = BeanUtils.copyBeanPropertyUtils(skuDTO, TWmsSkuDTO.class);
            skuService.createSku(wmsSkuDTO, dbShardVO);
        }
        return  MessageResult.getSucMessage();
    }
}
