package com.huamengtong.wms.main.service.impl;

import com.huamengtong.wms.convert.ListArraysConvert;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.TWmsRoleDTO;
import com.huamengtong.wms.dto.TWmsUserRoleDTO;
import com.huamengtong.wms.entity.main.TWmsRoleEntity;
import com.huamengtong.wms.main.mapper.RoleMapper;
import com.huamengtong.wms.main.service.IRoleService;
import com.huamengtong.wms.utils.ArrayUtil;
import com.huamengtong.wms.utils.BeanUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoleService implements IRoleService {

    @Autowired
    RoleMapper roleMapper;

    @Override
    public PageResponse queryRolePages(TWmsRoleDTO roleDTO) {
        TWmsRoleEntity roleEntity = BeanUtils.copyBeanPropertyUtils(roleDTO,TWmsRoleEntity.class);
        List<TWmsRoleEntity> tenantEntityList = roleMapper.queryRolePages(roleEntity);
        Integer totalSize = roleMapper.queryRolePageCount(roleEntity);
        PageResponse<List<TWmsRoleEntity>> response = new PageResponse();
        response.setTotal(totalSize);
        response.setRows(tenantEntityList);
        return  response;
    }

    @Override
    public MessageResult removeByPrimaryKey(Long id) {
        roleMapper.deleteByPrimaryKey(id);
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult createRole(TWmsRoleDTO roleDTO) {
        TWmsRoleEntity roleEntity = BeanUtils.copyBeanPropertyUtils(roleDTO,TWmsRoleEntity.class);
        roleMapper.insertRole(roleEntity);
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult modifyRole(TWmsRoleDTO roleDTO) {
        TWmsRoleEntity roleEntity = BeanUtils.copyBeanPropertyUtils(roleDTO,TWmsRoleEntity.class);
        roleMapper.updateRole(roleEntity);
        return MessageResult.getSucMessage();
    }

    @Override
    public TWmsRoleEntity findByPrimaryKey(Long id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<String> getRoleNameByIds(List<Long> roleList) {
        return roleMapper.selectRoleNameByIds(roleList);
    }

    @Override
    public PageResponse<List> queryRolesByUserPages(Map searchMap) {
        List<Map<String, Object>> mapList = roleMapper.queryRolesByUserPages(searchMap);
        //转换
        List<Object[]> list = ListArraysConvert.listMapToArrayConvert(mapList);
        List userRoleLists = new ArrayList();
        list.forEach(x->{
            Map resultMap = new HashMap();
            resultMap.put("id",x[0]);
            resultMap.put("userId",x[1]);
            resultMap.put("roleId",x[2]);
            resultMap.put("roleName",x[3]);
            resultMap.put("isActive",x[4]);
            userRoleLists.add(resultMap);
        });
        Integer totalSize = roleMapper.queryRolesByUserPageCount(searchMap);
        PageResponse<List> response = new PageResponse();
        response.setTotal(totalSize);
        response.setRows(userRoleLists);
        return  response;
    }

    @Override
    public Map queryAllocatableRoles(Map searchMap) {
        List<TWmsRoleEntity> roleEntityList = roleMapper.selectRolesByUser(searchMap);
        Map resultMap = new HashMap();
        resultMap.put("total",roleEntityList.size());
        resultMap.put("rows",roleEntityList);
        return resultMap;
    }

    @Override
    public MessageResult saveAllocatableRoles(Map map) {
        String createUser = MapUtils.getString(map, "createUser");
        Long userId = MapUtils.getLong(map, "userId");
        String ids = MapUtils.getString(map, "roleIds");
        if (userId != null && StringUtils.isNotBlank(ids)) {
            Long[] roleIds = ArrayUtil.toLongArray(ids.split(","));
            List<TWmsUserRoleDTO> userWarehouseDTOList = new ArrayList<>();
            for(Long roleId : roleIds){
                TWmsUserRoleDTO userRoleDTO = new TWmsUserRoleDTO();
                userRoleDTO.setUserId(userId);
                userRoleDTO.setRoleId(roleId);
                userRoleDTO.setCreateTime(new java.util.Date().getTime());
                userRoleDTO.setCreateUser(createUser);
                userRoleDTO.setUpdateTime(new java.util.Date().getTime());
                userRoleDTO.setUpdateUser(createUser);
                userWarehouseDTOList.add(userRoleDTO);
            }
            roleMapper.insertBatchUserRole(userWarehouseDTOList);
            return MessageResult.getSucMessage();
        }
        return MessageResult.getMessage("E10004");
    }

    @Override
    public MessageResult removeAllocatableRole(Map searchMap) {
        Long userId = MapUtils.getLong(searchMap, "userId");
        String userRoleIds = MapUtils.getString(searchMap, "userRoleIds");
        if (userId != null && StringUtils.isNotBlank(userRoleIds)) {
            Long[] ids =  ArrayUtil.toLongArray(userRoleIds.split(","));
            roleMapper.deleteBatchUserRole(ids);
            return MessageResult.getSucMessage();
        }
        return MessageResult.getMessage("E10005");
    }
}
