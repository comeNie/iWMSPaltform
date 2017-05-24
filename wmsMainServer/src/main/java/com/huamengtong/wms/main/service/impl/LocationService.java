package com.huamengtong.wms.main.service.impl;

import com.huamengtong.wms.core.service.BaseService;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.TWmsLocationDTO;
import com.huamengtong.wms.entity.main.TWmsLocationEntity;
import com.huamengtong.wms.main.mapper.LocationMapper;
import com.huamengtong.wms.main.service.ILocationService;
import com.huamengtong.wms.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;


@Service
public class LocationService extends BaseService implements ILocationService {

    @Autowired
    LocationMapper locationMapper;


    @Override
    public PageResponse<List<TWmsLocationEntity>> queryLocationPages(Map map) {
        List<TWmsLocationEntity> locationEntities=locationMapper.queryLocationPages(map);
        Integer totalSize = locationMapper.queryLocationPageCount(map);
        PageResponse<List<TWmsLocationEntity>> response =  new PageResponse();
        response.setTotal(totalSize);
        response.setRows(locationEntities);
        return response;
    }

    @Override
    public TWmsLocationEntity findByPrimaryKey(Long id) {
        return locationMapper.selectByPrimaryKey(id);
    }

    @Override
    public MessageResult removeByPrimaryKey(Long id) {
        locationMapper.deleteByPrimaryKey(id);
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult createLocation(TWmsLocationDTO locationDTO) {
        TWmsLocationEntity entity = BeanUtils.copyBeanPropertyUtils(locationDTO,TWmsLocationEntity.class);
        locationMapper.insertLocation(entity);
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult modifyLocation(TWmsLocationDTO locationDTO) {
        TWmsLocationEntity entity = BeanUtils.copyBeanPropertyUtils(locationDTO,TWmsLocationEntity.class);
        locationMapper.updateLocation(entity);
        return MessageResult.getSucMessage();
    }

    @Override
    public List<TWmsLocationEntity> searchLocationByTenantId(Long tenantId) {
        return null;
    }

    @Override
    public PageResponse<List> queryLocationsByZonePages(Map searchMap) {
        List<TWmsLocationEntity> mapList = locationMapper.queryLocationsByZonePages(searchMap);
        Integer totalSize = locationMapper.queryLocationsByZonePageCount(searchMap);
        PageResponse<List> response = new PageResponse();
        response.setTotal(totalSize);
        response.setRows(mapList);
        return response;
    }
}
