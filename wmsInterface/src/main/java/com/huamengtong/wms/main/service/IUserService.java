package com.huamengtong.wms.main.service;

import com.huamengtong.wms.core.formwork.db.domain.CurrentUserEntity;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.TWmsUserDTO;
import com.huamengtong.wms.entity.main.TWmsUserEntity;

import java.util.List;
import java.util.Map;

public interface IUserService {

    TWmsUserEntity findUserById(Long userId);

    TWmsUserEntity findUser(String userName, String password);

    TWmsUserEntity findByUserName(String userName);

    PageResponse<List<TWmsUserEntity>> queryUserPages(TWmsUserDTO userDTO);

    MessageResult removeUser(Long id);

    MessageResult createUser(TWmsUserDTO userDTO);

    MessageResult modifyUser(TWmsUserDTO userDTO);

    List getRoleIdListByUserId(Long userId);

    List<TWmsUserEntity> queryUsersByWarehouse(Map searchMap);

    PageResponse<List> queryUserByRolesPage(Map searchMap);

    MessageResult modifyResetUserPwd(Long id, CurrentUserEntity userEntity);

    MessageResult modifyUserPassword(Long userId, String newPassword, String oldPassword);

}
