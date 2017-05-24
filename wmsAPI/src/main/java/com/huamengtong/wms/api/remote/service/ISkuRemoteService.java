package com.huamengtong.wms.api.remote.service;

import com.huamengtong.wms.api.dto.CreateSkuDTO;
import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.web.MessageResult;

import java.util.List;

/**
 * Created by Edwin on 2016/11/8.
 */
public interface ISkuRemoteService {

    MessageResult createSku(List<CreateSkuDTO> skuDTOList,DbShardVO dbShardVO) throws Exception;

}
