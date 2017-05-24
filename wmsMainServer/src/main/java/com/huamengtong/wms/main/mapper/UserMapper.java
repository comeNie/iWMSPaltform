package com.huamengtong.wms.main.mapper;

import com.huamengtong.wms.entity.main.TWmsUserEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserMapper {

    TWmsUserEntity selectUserById(@Param("userId") Long userId);

    TWmsUserEntity selectUser(@Param("userName") String userName, @Param("password") String password);

    TWmsUserEntity findByUserName(@Param("userName") String userName);

    List<TWmsUserEntity> queryUserPages(TWmsUserEntity HWmsUserEntity);

    Integer queryUserPageCount(TWmsUserEntity HWmsUserEntity);

    Integer insertUser(TWmsUserEntity HWmsUserEntity);

    Integer updateUser(TWmsUserEntity HWmsUserEntity);

    Integer deleteUser(Long id);

    List selectUserRolesById(@Param("userId") Long userId);

    List<TWmsUserEntity> selectUsersByWarehouse(Map map);

    List<Map<String,Object>> queryUserByRolePages(Map searchMap);

    Integer queryUserByRolePageCount(Map searchMap);

    Integer updateUserPwd(TWmsUserEntity userEntity);
}
