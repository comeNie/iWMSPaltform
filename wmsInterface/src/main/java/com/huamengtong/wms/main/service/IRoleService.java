package com.huamengtong.wms.main.service;

import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.TWmsRoleDTO;
import com.huamengtong.wms.entity.main.TWmsRoleEntity;

import java.util.List;
import java.util.Map;

public interface IRoleService {

    PageResponse queryRolePages(TWmsRoleDTO roleDTO);

    MessageResult removeByPrimaryKey(Long id);

    MessageResult createRole(TWmsRoleDTO roleDTO);

    MessageResult modifyRole(TWmsRoleDTO roleDTO);

    TWmsRoleEntity findByPrimaryKey(Long id);

    List<String> getRoleNameByIds(List<Long> roleList);

    PageResponse<List> queryRolesByUserPages(Map searchMap);

    Map queryAllocatableRoles(Map searchMap);

    MessageResult saveAllocatableRoles(Map searchMap);

    MessageResult removeAllocatableRole(Map searchMap);
}
