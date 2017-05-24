package com.huamengtong.wms.inwh.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.huamengtong.wms.client.IAutoIdClient;
import com.huamengtong.wms.constants.AutoIdConstants;
import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.service.BaseService;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.inwh.TWmsProInvPackageDetailDTO;
import com.huamengtong.wms.entity.inwh.TWmsProInvPackageDetailEntity;
import com.huamengtong.wms.entity.sku.TWmsSkuEntity;
import com.huamengtong.wms.inwh.mapper.ProInvPackageDetailMapper;
import com.huamengtong.wms.inwh.service.IProInvPackageDetailService;
import com.huamengtong.wms.sku.service.ISkuService;
import com.huamengtong.wms.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by mario on 2017/3/28.
 */
@Service
public class ProInvPackageDetailService extends BaseService implements IProInvPackageDetailService {
    @Autowired
    ProInvPackageDetailMapper proInvPackageDetailMapper;
    @Autowired
    IAutoIdClient autoIdClient;
    @Autowired
    ISkuService skuService;

    @Override
    public TWmsProInvPackageDetailEntity findByPrimaryKey(Long id, DbShardVO dbShardVO) {
        return proInvPackageDetailMapper.selectByPrimaryKey(id,getSplitTableKey(dbShardVO));
    }

    @Override
    public MessageResult createProInvPackageDetail(TWmsProInvPackageDetailDTO proInvPackageDetailDTO, DbShardVO dbShardVO) {
        TWmsProInvPackageDetailEntity proInvPackageDetailEntity= BeanUtils.copyBeanPropertyUtils(proInvPackageDetailDTO,TWmsProInvPackageDetailEntity.class);
        proInvPackageDetailMapper.insertProInvPackageDetail(proInvPackageDetailEntity,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult modifyProInvPackageDetail(TWmsProInvPackageDetailDTO proInvPackageDetailDTO, DbShardVO dbShardVO) {
        TWmsProInvPackageDetailEntity proInvPackageDetailEntity= BeanUtils.copyBeanPropertyUtils(proInvPackageDetailDTO,TWmsProInvPackageDetailEntity.class);
        proInvPackageDetailMapper.updateProInvPackageDetail(proInvPackageDetailEntity,getSplitTableKey(dbShardVO));
        return MessageResult.getSucMessage();
    }

    @Override
    public PageResponse<List> queryProInvPackageDetailByHeader(Map map, DbShardVO dbShardVO) {
        map.put("splitTablekey",getSplitTableKey(dbShardVO));
        List<TWmsProInvPackageDetailEntity> mapList = proInvPackageDetailMapper.queryDetailPages(map);
        mapList.stream().forEach(x->{
           TWmsSkuEntity skuEntity = skuService.findByPrimaryKey(x.getInvPackageId(),getSkuDbShareVO(dbShardVO));
            x.setSku(skuEntity.getSku());
            x.setSkuName(skuEntity.getItemName());
        });
        Integer totalSize =  proInvPackageDetailMapper.queryDetailPageCount(map);
        PageResponse<List> response = new PageResponse();
        response.setRows(mapList);
        response.setTotal(totalSize);
        return response;
    }

    @Override
    public List<TWmsProInvPackageDetailEntity> getProInvPackageDetails(long parentId, DbShardVO dbShardVO) {
        List<TWmsProInvPackageDetailEntity> mapList = proInvPackageDetailMapper.queryProInvPackageDetails(parentId,getSplitTableKey(dbShardVO));
        if (CollectionUtils.isNotEmpty(mapList)) {
            return mapList;
        }
        return null;
    }

    @Override
    public MessageResult createProInvPackageDetailBatch(List<TWmsProInvPackageDetailDTO> list, DbShardVO dbShardVO) {
        for (TWmsProInvPackageDetailDTO t: list) {
            TWmsProInvPackageDetailEntity p = BeanUtils.copyBeanPropertyUtils(t,TWmsProInvPackageDetailEntity.class);
            p.setId(autoIdClient.getAutoId(AutoIdConstants.TWmsProInvPackageDetailEntity));
            proInvPackageDetailMapper.insertProInvPackageDetail(p,getSplitTableKey(dbShardVO));
        }
        return MessageResult.getSucMessage();
    }
}
