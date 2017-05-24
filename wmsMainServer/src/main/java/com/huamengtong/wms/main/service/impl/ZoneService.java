package com.huamengtong.wms.main.service.impl;


import com.huamengtong.wms.core.redis.RedisTemplate;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.TWmsLocationDTO;
import com.huamengtong.wms.dto.TWmsZoneDTO;
import com.huamengtong.wms.entity.main.TWmsZoneEntity;
import com.huamengtong.wms.main.mapper.ZoneMapper;
import com.huamengtong.wms.main.service.ILocationService;
import com.huamengtong.wms.main.service.IZoneService;
import com.huamengtong.wms.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;



@Service
public class ZoneService implements IZoneService {


    @Autowired
    ZoneMapper zoneMapper;

    @Autowired
    ILocationService locationService;

    @Autowired
    RedisTemplate redisTemplate;

    public static final String ZONE_KEY="zoneKey";


    @Override
    public PageResponse<List<TWmsZoneEntity>> getZoneLists(TWmsZoneDTO zoneDTO) {
        TWmsZoneEntity zoneEntity=BeanUtils.copyBeanPropertyUtils(zoneDTO,TWmsZoneEntity.class);
        List<TWmsZoneEntity> zoneEntities=zoneMapper.queryZonePages(zoneEntity);
        Integer totalSize=zoneMapper.queryZonePageCount(zoneEntity);
        PageResponse<List<TWmsZoneEntity>> response=new PageResponse();
        response.setTotal(totalSize);
        response.setRows(zoneEntities);
        return response;
    }


    @Override
    public TWmsZoneEntity findByPrimaryKey(Long id) {
        return zoneMapper.selectByPrimaryKey(id);
    }

    @Override
    public MessageResult removeZone(Long id) {
        zoneMapper.deleteByPrimaryKey(id);
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult createZone(TWmsZoneDTO zoneDTO) {
        TWmsZoneEntity zoneEntity=BeanUtils.copyBeanPropertyUtils(zoneDTO,TWmsZoneEntity.class);
        zoneMapper.insertZone(zoneEntity);
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult modifyZone(TWmsZoneDTO zoneDTO) {
        TWmsZoneEntity zoneEntity=BeanUtils.copyBeanPropertyUtils(zoneDTO,TWmsZoneEntity.class);
        zoneMapper.updateZone(zoneEntity);
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult createLocation(TWmsLocationDTO locationDTO) {
        return  locationService.createLocation(locationDTO);
    }

    @Override
    public MessageResult modifyLocation(TWmsLocationDTO locationDTO) {
        return locationService.modifyLocation(locationDTO);
    }

    @Override
    public MessageResult removeLocation(Long id) {
        return locationService.removeByPrimaryKey(id);
    }

    @Override
    public List<TWmsZoneEntity> searchZoneByWhId(Long warehouseId) {
        return zoneMapper.selectZonesByWarehouse(warehouseId);
    }

}
