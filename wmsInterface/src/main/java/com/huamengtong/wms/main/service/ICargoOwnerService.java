package com.huamengtong.wms.main.service;
import java.util.List;
import java.util.Map;

import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.TWmsCargoOwnerDTO;
import com.huamengtong.wms.entity.main.TWmsCargoOwnerEntity;

public interface ICargoOwnerService {

    MessageResult removeCargoOwner(Long id);

    PageResponse<List<TWmsCargoOwnerEntity>> queryCargoOwnerPages(TWmsCargoOwnerDTO ownerDTO);

    MessageResult createOwner(TWmsCargoOwnerDTO ownerDTO);

    MessageResult updateOwner(TWmsCargoOwnerDTO ownerDTO);

    List<TWmsCargoOwnerEntity> findCargoOwner(Map map);
    
    TWmsCargoOwnerEntity findCargoOwnerByCargoOwnerNo(String cargoOwnerNo);

    TWmsCargoOwnerEntity findCargoOwnerById(Long id);

    List<TWmsCargoOwnerEntity> findByIds(List<Long> ids);
}
