package com.huamengtong.wms.main.mapper;

import com.huamengtong.wms.dto.TWmsUserRoleDTO;
import com.huamengtong.wms.entity.main.TWmsRoleEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface RoleMapper {

    List<TWmsRoleEntity> queryRolePages(TWmsRoleEntity roleEntity);

    Integer queryRolePageCount(TWmsRoleEntity roleEntity);

    Integer deleteByPrimaryKey(Long id);

    Integer insertRole(TWmsRoleEntity roleEntity);

    Integer updateRole(TWmsRoleEntity roleEntity);

    TWmsRoleEntity selectByPrimaryKey(Long id);

    List<String> selectRoleNameByIds(List<Long> roleList);

    List<Map<String,Object>> queryRolesByUserPages(Map searchMap);

    Integer queryRolesByUserPageCount(Map searchMap);

    List<TWmsRoleEntity> selectRolesByUser(Map searchMap);

    Integer insertBatchUserRole(List<TWmsUserRoleDTO> userWarehouseDTOList);

    Integer deleteBatchUserRole(@Param("ids") Long[] ids);
}
