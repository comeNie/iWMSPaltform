package com.huamengtong.wms.main.mapper;

import com.huamengtong.wms.dto.TWmsRolePermissionDTO;
import com.huamengtong.wms.entity.main.TWmsPermissionEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PermissionMapper {

    List selectPermissionByUserId(@Param("userId") Long userId);

    TWmsPermissionEntity selectByPrimaryKey(Long id);

    Integer deleteByPrimaryKey(Long id);

    Integer insertPermission(TWmsPermissionEntity permissionEntity);

    Integer updatePermission(TWmsPermissionEntity permissionEntity);

    List<TWmsPermissionEntity> selectPermissionByModuleId(@Param("moduleId") Long moduleId);

    List<Map<String,Object>> selectPermissionModuleByRoleId(Long roleId);

    List<TWmsPermissionEntity> selectAllPermissions(Map map);

    Integer deleteRolePermissionByRoleId(Long roleId);

    Integer insertBatchRolePermission(List<TWmsRolePermissionDTO> rolePermissionList);

}
