package com.huamengtong.wms.outwh.service.impl;

import com.huamengtong.wms.client.IAutoIdClient;
import com.huamengtong.wms.constants.AutoIdConstants;
import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.service.BaseService;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.outwh.TWmsDnDetailDTO;
import com.huamengtong.wms.entity.outwh.TWmsDnDetailEntity;
import com.huamengtong.wms.entity.outwh.TWmsDnHeaderEntity;
import com.huamengtong.wms.inventory.service.IInventoryService;
import com.huamengtong.wms.outwh.mapper.DnDetailMapper;
import com.huamengtong.wms.outwh.service.IDnDetailService;
import com.huamengtong.wms.outwh.service.IDnHeaderService;
import com.huamengtong.wms.utils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by mario on 2016/11/3.
 */
@Service
public class DnDetailService extends BaseService implements IDnDetailService {

    @Autowired
    DnDetailMapper dnDetailMapper;

    @Autowired
    IAutoIdClient autoIdClient;

    @Autowired
    IInventoryService inventoryService;

    @Autowired
    IDnHeaderService dnHeaderService;


    @Override
    public TWmsDnDetailEntity findByPrimaryKey(Long id, DbShardVO dbShardVO) {
        return dnDetailMapper.selectByPrimaryKey(id, getSplitTableKey(dbShardVO));
    }

    @Override
    public MessageResult removeByPrimaryKey(Long id, DbShardVO dbShardVO) {
        dnDetailMapper.deleteByPrimaryKey(id, getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult createDnDetail(TWmsDnDetailDTO dnDetailDTO, DbShardVO dbShardVO) {
        if (dnDetailDTO.getSkuId() == null || dnDetailDTO.getSkuId() == 0) {
            return MessageResult.getMessage("E40003");
        } else if (dnDetailDTO.getQty() == 0) {
            return MessageResult.getMessage("E60015");
        }
        List<TWmsDnDetailEntity> list = dnDetailMapper.queryDnDetails(dnDetailDTO.getDnId(), getSplitTableKey(dbShardVO));
        for (TWmsDnDetailEntity x : list) {
            if (dnDetailDTO.getSkuId().longValue() == x.getSkuId()) {
                return MessageResult.getMessage("E60016", new Object[]{dnDetailDTO.getDnId()});
            }
        }
        TWmsDnHeaderEntity dnHeaderEntity = dnHeaderService.findByPrimaryKey(dnDetailDTO.getDnId(), dbShardVO);
        Integer activeQty = inventoryService.queryActiveQty(dnDetailDTO.getStorageRoomIds(), dnDetailDTO.getSkuId(), dnHeaderEntity.getCargoOwnerId(), changeInwhDbShareVO(dbShardVO));
        if (activeQty == null || activeQty < dnDetailDTO.getQty()) {
            return MessageResult.getMessage("E60018");
        }
        TWmsDnDetailEntity DnDetailEntity = BeanUtils.copyBeanPropertyUtils(dnDetailDTO, TWmsDnDetailEntity.class);
        DnDetailEntity.setId(autoIdClient.getAutoId(AutoIdConstants.TWmsDnDetailEntity));
        dnDetailMapper.insertDnDetail(DnDetailEntity, getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult modifyDnDetail(TWmsDnDetailDTO dnDetailDTO, DbShardVO dbShardVO) {
        if (dnDetailDTO.getSkuId() == null || dnDetailDTO.getSkuId() == 0) {
            return MessageResult.getMessage("E40003");
        } else if (dnDetailDTO.getQty() == 0) {
            return MessageResult.getMessage("E40004");
        }
        TWmsDnHeaderEntity dnHeaderEntity = dnHeaderService.findByPrimaryKey(dnDetailDTO.getDnId(), dbShardVO);
        Integer activeQty = inventoryService.queryActiveQty(dnDetailDTO.getStorageRoomIds(), dnDetailDTO.getSkuId(), dnHeaderEntity.getCargoOwnerId(), changeInwhDbShareVO(dbShardVO));
        if (activeQty == null || activeQty < dnDetailDTO.getQty()) {
            return MessageResult.getMessage("E60018");
        }
        TWmsDnDetailEntity DnDetailEntity = BeanUtils.copyBeanPropertyUtils(dnDetailDTO, TWmsDnDetailEntity.class);
        dnDetailMapper.UpdateDnDetail(DnDetailEntity, getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }

    @Override
    public PageResponse<List> queryDnDetailByHeader(Map paramMap, DbShardVO dbShardVO) {
        paramMap.put("splitTableKey", getSplitTableKey(dbShardVO));
        List<TWmsDnDetailEntity> mapList = dnDetailMapper.queryDetailsPages(paramMap);
        Integer totalSize = dnDetailMapper.queryDetailPageCount(paramMap);
        PageResponse<List> response = new PageResponse();
        response.setTotal(totalSize);
        response.setRows(mapList);
        return response;
    }

    /**
     * 根据headerId来查找明细
     *
     * @param headerId
     * @param dbShardVO
     * @return
     */
    @Override
    public List<TWmsDnDetailEntity> getDnDetails(Long headerId, DbShardVO dbShardVO) {
        List<TWmsDnDetailEntity> mapList = dnDetailMapper.queryDnDetails(headerId, getSplitTableKey(dbShardVO));
        if (CollectionUtils.isNotEmpty(mapList)) {
            return mapList;
        }
        return null;
    }


    @Override
    public MessageResult batchDeleteDnDetails(Long[] dnIds, DbShardVO dbShardVO) {
        for (Long dnId : dnIds) {
            dnDetailMapper.deleteDnDetails(dnId, getSplitTableKey(dbShardVO));
        }
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult deleteDnDetailsByDnId(Long dnId, DbShardVO dbShardVO) {
        dnDetailMapper.deleteDnDetails(dnId, getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }
}
