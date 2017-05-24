package com.huamengtong.wms.outwh.service.impl;

import com.huamengtong.wms.client.IAutoIdClient;
import com.huamengtong.wms.constants.AutoIdConstants;
import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.service.BaseService;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.outwh.TWmsShipmentHeaderDTO;
import com.huamengtong.wms.dto.outwh.TWmsWaveDTO;
import com.huamengtong.wms.entity.outwh.TWmsShipmentHeaderEntity;
import com.huamengtong.wms.entity.outwh.TWmsWaveEntity;
import com.huamengtong.wms.outwh.mapper.WaveMapper;
import com.huamengtong.wms.outwh.service.IShipmentHeaderService;
import com.huamengtong.wms.outwh.service.IWaveService;
import com.huamengtong.wms.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Created by could.hao on 2016/12/19.
 */
@Service
public class WaveService extends BaseService implements IWaveService {
    @Autowired
    WaveMapper waveMapper;

    @Autowired
    IAutoIdClient autoIdClient;

    @Autowired
    IShipmentHeaderService shipmentHeaderService;

    @Override
    public MessageResult createWave(TWmsWaveDTO waveDTO, DbShardVO dbShardVO) {
        TWmsWaveEntity waveEntity = BeanUtils.copyBeanPropertyUtils(waveDTO,TWmsWaveEntity.class);
        if(waveEntity.getId() == null || waveEntity.getId().longValue() == 0){
            waveEntity.setId(autoIdClient.getAutoId(AutoIdConstants.TWmsWaveEntity));
        }
        waveMapper.insert(waveEntity,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }

    @Override
    public TWmsWaveEntity findById(Long id, DbShardVO dbShardVO) {
        return waveMapper.selectByPrimaryKey(id,getSplitTableKey(dbShardVO));
    }

    @Override
    public MessageResult deleteById(Long id, DbShardVO dbShardVO) {
        waveMapper.deleteByPrimaryKey(id,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult modifyWave(TWmsWaveDTO waveDTO, DbShardVO dbShardVO) {
        TWmsWaveEntity waveEntity = BeanUtils.copyBeanPropertyUtils(waveDTO,TWmsWaveEntity.class);
        waveMapper.update(waveEntity,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }

    @Override
    public PageResponse<TWmsWaveEntity> getPage(TWmsWaveDTO waveDTO, DbShardVO dbShardVO) {
        TWmsWaveEntity waveEntity = BeanUtils.copyBeanPropertyUtils(waveDTO,TWmsWaveEntity.class);
        List<TWmsWaveEntity> waveEntities = waveMapper.queryWavePages(waveEntity,getSplitTableKey(dbShardVO));
        Integer totalSize = waveMapper.queryWavePageCount(waveEntity,getSplitTableKey(dbShardVO));
        PageResponse pageResponse = new PageResponse();
        pageResponse.setRows(waveEntities);
        pageResponse.setTotal(totalSize);
        return pageResponse;
    }

    @Override
    public PageResponse<List<TWmsShipmentHeaderEntity>> getShipmentHeaderPage(Long id, DbShardVO dbShardVO) {
        TWmsShipmentHeaderDTO shipmentHeaderDTO = new TWmsShipmentHeaderDTO();
        shipmentHeaderDTO.setWaveId(id);
        return shipmentHeaderService.getShipmentHeader(shipmentHeaderDTO,dbShardVO);
    }

    /**
     *波次打印返回出库单ids
     * @param ids 波次ids
     * @param dbShardVO
     * @return
     */
    @Override
    public String getShipmentIds(Long[] ids, DbShardVO dbShardVO) {
        String shipmentIds = "";
        List<TWmsShipmentHeaderEntity> list = shipmentHeaderService.findShipmentIdsByWaveId(Arrays.asList(ids),dbShardVO);
        for (TWmsShipmentHeaderEntity s:list) {
                shipmentIds = shipmentIds + s.getId()+",";
        }
        return shipmentIds;
    }
}
