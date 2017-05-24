package com.huamengtong.wms.main.mapper;

import com.huamengtong.wms.entity.main.TWmsModuleEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ModuleMapper {

    //管理员获取菜单
    List<TWmsModuleEntity> selectAllModulesAdmin(@Param("moduleType") String moduleType);

    //普通用户根据权限获取菜单
    List<TWmsModuleEntity> selectAllModuleNormal(@Param("userId") Long userId, @Param("moduleType") String moduleType);

    List<TWmsModuleEntity> selectModuleByIds(List moduleIdList);

    Integer deleteByPrimaryKey(Long id);

    TWmsModuleEntity selectByPrimaryKey(Long id);

    Integer insertModule(TWmsModuleEntity moduleEntity);

    Integer updateModule(TWmsModuleEntity moduleEntity);

    List<TWmsModuleEntity> selectAllModules(Map paramsMap);

}
