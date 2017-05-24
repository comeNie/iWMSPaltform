package com.huamengtong.wms.main.service;

import com.huamengtong.wms.core.web.MessageResult;

import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.TWmsLocationDTO;
import com.huamengtong.wms.entity.main.TWmsLocationEntity;
import java.util.List;
import java.util.Map;


public interface ILocationService {

    PageResponse<List<TWmsLocationEntity>> queryLocationPages(Map map);

    TWmsLocationEntity findByPrimaryKey(Long id);

    MessageResult removeByPrimaryKey(Long id);

    MessageResult createLocation(TWmsLocationDTO locationDTO);

    MessageResult modifyLocation(TWmsLocationDTO  locationDTO);

    List<TWmsLocationEntity> searchLocationByTenantId(Long tenantId);

    PageResponse<List> queryLocationsByZonePages(Map searchMap);


}
