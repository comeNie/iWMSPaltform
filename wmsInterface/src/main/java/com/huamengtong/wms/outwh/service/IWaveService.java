package com.huamengtong.wms.outwh.service;

import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.outwh.TWmsWaveDTO;
import com.huamengtong.wms.entity.outwh.TWmsShipmentHeaderEntity;
import com.huamengtong.wms.entity.outwh.TWmsWaveEntity;

import java.util.List;

/**
 * Created by could.hao on 2016/12/19.
 */
public interface IWaveService {

    MessageResult createWave (TWmsWaveDTO waveDTO , DbShardVO dbShardVO);

    TWmsWaveEntity findById(Long id, DbShardVO dbShardVO);

    MessageResult deleteById(Long id, DbShardVO dbShardVO);

    MessageResult modifyWave(TWmsWaveDTO waveDTO ,DbShardVO dbShardVO);

    PageResponse<TWmsWaveEntity> getPage(TWmsWaveDTO waveDTO,DbShardVO dbShardVO);

    PageResponse<List<TWmsShipmentHeaderEntity>> getShipmentHeaderPage(Long id, DbShardVO dbShardVO);

    String getShipmentIds(Long [] ids, DbShardVO dbShardVO);

}
