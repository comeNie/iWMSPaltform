package com.huamengtong.wms.main.service;

import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.dto.TWmsPermissionDTO;
import com.huamengtong.wms.entity.main.TWmsPermissionEntity;

import java.util.List;
import java.util.Map;

public interface IPermissionService {

    MessageResult removeByPrimaryKey(Long id);

    MessageResult createPermission(TWmsPermissionDTO permissionDTO);

    MessageResult modifyPermissionEntity(TWmsPermissionDTO permissionDTO);

    TWmsPermissionEntity findByPrimaryKey(Long id);

    List<Map<String,Object>> findPermissionByUserId(Long id);

    Map queryPermModuleByRoles(Map searchMap);

    List<TWmsPermissionEntity> findPermissionByModuleId(Long moduleId);

    List<TWmsPermissionEntity> selectPermissions();

    MessageResult saveRoleActionPermission(Map map);

    List<TWmsPermissionEntity> selectPermissions(Boolean flag);
}
