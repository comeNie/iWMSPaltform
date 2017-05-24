package com.huamengtong.wms.main.service;

import com.huamengtong.wms.core.formwork.db.domain.CurrentUserEntity;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.TWmsWarehouseDTO;
import com.huamengtong.wms.entity.main.TWmsWarehouseEntity;

import java.util.List;
import java.util.Map;

public interface IWarehouseService {

    List<TWmsWarehouseEntity> searchWarehouseByUser(CurrentUserEntity sessionCurrentUser);

    PageResponse<List<TWmsWarehouseEntity>> queryWarehousePages(TWmsWarehouseDTO warehouseDTO);

    TWmsWarehouseEntity findWarehouseById(Long id);

    TWmsWarehouseEntity findWarehouseByWarehouseNo(String warehouseNo);

    MessageResult createWarehouse(TWmsWarehouseDTO warehouseDTO);

    MessageResult modifyWarehouse(TWmsWarehouseDTO warehouseDTO);

    MessageResult removeWarehouse(Long id);

    PageResponse<List> queryUserByWarehousePage(Map searchMap);

    Map queryAllocatableUser(Map map);

    MessageResult saveAllocatableUser(Map map);

    MessageResult removeAllocatableUser(Map map);

    PageResponse<List> findWarehouseByUserPage(Map searchMap);

    List<TWmsWarehouseEntity> searchWarehouseByIds(List<Long> whIds);

}
