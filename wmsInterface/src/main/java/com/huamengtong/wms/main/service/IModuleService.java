package com.huamengtong.wms.main.service;

import com.huamengtong.wms.core.formwork.db.domain.CurrentUserEntity;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.dto.TWmsModuleDTO;
import com.huamengtong.wms.dto.TWmsPermissionDTO;
import com.huamengtong.wms.entity.main.TWmsModuleEntity;

import java.util.List;
import java.util.Map;

public interface IModuleService {

    /**
     * 根据当前用户获取模块菜单
     * @param sessionCurrentUser
     * @return
     */
    List getModulesByUser(CurrentUserEntity sessionCurrentUser);

    /***
     * 根据当前用户获取模块操作权限
     * @param sessionCurrentUser
     * @return
     */
    Map getAllModuleActionByUser(CurrentUserEntity sessionCurrentUser);

    /***
     * 查询所以模块菜单
     * @return
     */
    Map queryAllMenus();

    List getAllModuleAction();

    MessageResult removeByPrimaryKey(Long id);

    MessageResult createModule(TWmsModuleDTO moduleDTO);

    MessageResult modifyModule(Map map);

    TWmsModuleEntity findByPrimaryKey(Long id);

    Map queryModuleActions(Map searchMap);

    MessageResult createModuleActions(TWmsPermissionDTO permissionDTO);

    MessageResult modifyModuleActions(TWmsPermissionDTO permissionDTO);

    MessageResult removeModuleActions(Long id);

}
