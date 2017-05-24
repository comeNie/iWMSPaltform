package com.huamengtong.wms.main.service;

import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.TWmsLocationDTO;
import com.huamengtong.wms.dto.TWmsZoneDTO;
import com.huamengtong.wms.entity.main.TWmsZoneEntity;

import java.util.List;


public interface IZoneService {

    PageResponse<List<TWmsZoneEntity>> getZoneLists(TWmsZoneDTO zoneDTO);

    TWmsZoneEntity findByPrimaryKey(Long id);

    MessageResult removeZone(Long id);

    MessageResult createZone(TWmsZoneDTO zoneDTO);

    MessageResult modifyZone(TWmsZoneDTO zoneDTO);

    MessageResult createLocation(TWmsLocationDTO locationDTO);

    MessageResult modifyLocation(TWmsLocationDTO locationDTO);

    MessageResult removeLocation(Long id);

    List<TWmsZoneEntity> searchZoneByWhId(Long warehouseId);

}
