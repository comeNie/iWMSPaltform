package com.huamengtong.wms.main.service.impl;

import com.huamengtong.wms.convert.ListArraysConvert;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.dto.TWmsPermissionDTO;
import com.huamengtong.wms.dto.TWmsRolePermissionDTO;
import com.huamengtong.wms.entity.main.TWmsPermissionEntity;
import com.huamengtong.wms.main.mapper.PermissionMapper;
import com.huamengtong.wms.main.service.IModuleService;
import com.huamengtong.wms.main.service.IPermissionService;
import com.huamengtong.wms.utils.BeanUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PermissionService implements IPermissionService {

    @Autowired
    PermissionMapper permissionMapper;

    @Autowired
    IModuleService moduleService;

    @Override
    public MessageResult removeByPrimaryKey(Long id) {
        permissionMapper.deleteByPrimaryKey(id);
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult createPermission(TWmsPermissionDTO permissionDTO) {
        TWmsPermissionEntity permissionEntity = BeanUtils.copyBeanPropertyUtils(permissionDTO,TWmsPermissionEntity.class);
        permissionMapper.insertPermission(permissionEntity);
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult modifyPermissionEntity(TWmsPermissionDTO permissionDTO) {
        TWmsPermissionEntity permissionEntity = BeanUtils.copyBeanPropertyUtils(permissionDTO,TWmsPermissionEntity.class);
        permissionMapper.updatePermission(permissionEntity);
        return MessageResult.getSucMessage();
    }

    @Override
    public TWmsPermissionEntity findByPrimaryKey(Long id) {
        return permissionMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Map<String, Object>> findPermissionByUserId(Long id) {
        return permissionMapper.selectPermissionByUserId(id);
    }

    @Override
    public List<TWmsPermissionEntity> findPermissionByModuleId(Long moduleId) {
        return permissionMapper.selectPermissionByModuleId(moduleId);
    }

    @Override
    public Map queryPermModuleByRoles(Map searchMap) {
        Long roleId = MapUtils.getLong(searchMap,"roleId");
        List<Map<String, Object>> mapList = permissionMapper.selectPermissionModuleByRoleId(roleId);
        //转换
        List<Object[]> list = ListArraysConvert.listMapToArrayConvert(mapList);
        List<TWmsRolePermissionDTO> rolePermissionLists = new ArrayList();
        list.forEach(x->{
            TWmsRolePermissionDTO rolePermissionDTO = new TWmsRolePermissionDTO();
            rolePermissionDTO.setId((Long)x[0]);
            rolePermissionDTO.setRoleId((Long)x[1]);
            rolePermissionDTO.setActionId((Long)x[2]);
            rolePermissionDTO.setRelationType((String)x[3]);
            rolePermissionLists.add(rolePermissionDTO);
        });

        //获得所以模块及操作权限
        List<Map> allModuleList = moduleService.getAllModuleAction();
        rolePermissionLists.forEach(rolePermission->{
            String actionId = String.valueOf(rolePermission.getActionId());
            String type = rolePermission.getRelationType();
            for(Map  map: allModuleList){
                String innerId = MapUtils.getString(map,"id");
                if(type == null){
                    continue;
                }
                if(type.equals("a") && innerId.equals("a_"+actionId)){
                    map.put("checked",true);
                    break;
                }
                if(type.equals("m") && innerId.equals(actionId)){
                    map.put("checked",true);
                    break;
                }
            }
        });
        Map resultMap = new HashMap();
        resultMap.put("rows",allModuleList);
        return resultMap;
    }


    @Override
    public List<TWmsPermissionEntity> selectPermissions(Boolean flag) {
        Map map = new HashMap();
        map.put("isModuleDefault",flag);
        return permissionMapper.selectAllPermissions(map);
    }

    @Override
    public List<TWmsPermissionEntity> selectPermissions() {
        return permissionMapper.selectAllPermissions(new HashMap());
    }

    @Override
    public MessageResult saveRoleActionPermission(Map map) {
        Long roleId = MapUtils.getLong(map,"roleId");
        List actionIds = (List) map.get("actions");
        String createUser = MapUtils.getString(map,"createUser");
        //删除角色对应的权限
        permissionMapper.deleteRolePermissionByRoleId(roleId);
        List<Long> mList = new ArrayList<>();
        List <TWmsRolePermissionDTO>  rolePermissions = new ArrayList();
        for(int i = 0;i < actionIds.size();i++){
            Object objectId = actionIds.get(i);
            TWmsRolePermissionDTO rolePermission = new TWmsRolePermissionDTO();
            rolePermission.setRoleId(roleId);
            rolePermission.setCreateUser(createUser);
            rolePermission.setCreateTime(new Date().getTime());
            rolePermission.setUpdateUser(createUser);
            rolePermission.setUpdateTime(new Date().getTime());
            //保存的时候 区分是action / module
            if(objectId instanceof  String){
                String id = objectId.toString();
                id = id.substring(2);
                rolePermission.setActionId(Long.parseLong(id));
                rolePermission.setRelationType("a");
            }else{
                String id = String.valueOf(objectId);
                rolePermission.setActionId(Long.parseLong(id));
                mList.add(Long.parseLong(id));
                rolePermission.setRelationType("m");
            }
            rolePermissions.add(rolePermission);
        }
        permissionMapper.insertBatchRolePermission(rolePermissions);
        return MessageResult.getSucMessage();
    }



}
