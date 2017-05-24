package com.huamengtong.wms.main.service.impl;

import com.huamengtong.wms.constants.GlobalConstants;
import com.huamengtong.wms.convert.ListArraysConvert;
import com.huamengtong.wms.core.formwork.db.domain.CurrentUserEntity;
import com.huamengtong.wms.core.service.BaseService;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.TWmsUserDTO;
import com.huamengtong.wms.entity.main.TWmsUserEntity;
import com.huamengtong.wms.main.mapper.UserMapper;
import com.huamengtong.wms.main.service.IUserService;
import com.huamengtong.wms.main.validate.PreconditionsUserUtil;
import com.huamengtong.wms.utils.BeanUtils;
import com.huamengtong.wms.utils.PwdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService extends BaseService implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public TWmsUserEntity findUserById(Long userId){
        return userMapper.selectUserById(userId);
    }

    @Override
    public TWmsUserEntity findUser(String userName, String password) {
        return userMapper.selectUser(userName,password);
    }

    @Override
    public TWmsUserEntity findByUserName(String userName) {
        return userMapper.findByUserName(userName);
    }

    @Override
    public MessageResult removeUser(Long id) {
        userMapper.deleteUser(id);
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult createUser(TWmsUserDTO userDTO) {
        //check Param
        PreconditionsUserUtil.createUserValidate(userDTO);
        if(this.findByUserName(userDTO.getUserName()) != null){
            return MessageResult.getMessage("E00012",new Object[]{userDTO.getUserName()});
        }
       //DTO转换为VO
        TWmsUserEntity HWmsUserEntity = BeanUtils.copyBeanPropertyUtils(userDTO, TWmsUserEntity.class);
        HWmsUserEntity.setPassword(PwdUtils.toMd5(StringUtils.isEmpty(userDTO.getPassword()) ? GlobalConstants.DEFAULT_PASSWORD :userDTO.getPassword() ,userDTO.getUserName()));
        userMapper.insertUser(HWmsUserEntity);
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult modifyUser(TWmsUserDTO userDTO) {
        //DTO转换为VO
        TWmsUserEntity HWmsUserEntity = BeanUtils.copyBeanPropertyUtils(userDTO, TWmsUserEntity.class);
        userMapper.updateUser(HWmsUserEntity);
        return MessageResult.getSucMessage();
    }

    @Override
    public PageResponse<List<TWmsUserEntity>> queryUserPages(TWmsUserDTO userDTO) {
        //DTO转换为VO
        TWmsUserEntity HWmsUserEntity = BeanUtils.copyBeanPropertyUtils(userDTO, TWmsUserEntity.class);
        List<TWmsUserEntity> HWmsUserEntityList = userMapper.queryUserPages(HWmsUserEntity);
        Integer totalSize = userMapper.queryUserPageCount(HWmsUserEntity);
        PageResponse pageResponse = new PageResponse();
        pageResponse.setTotal(totalSize);
        pageResponse.setRows(HWmsUserEntityList);
        return pageResponse;
    }

    @Override
    public List getRoleIdListByUserId(Long userId) {
        return userMapper.selectUserRolesById(userId);
    }

    @Override
    public List<TWmsUserEntity> queryUsersByWarehouse(Map searchMap) {
        return userMapper.selectUsersByWarehouse(searchMap);
    }

    @Override
    public MessageResult modifyResetUserPwd(Long id,CurrentUserEntity currenUserEntity) {
        if( 1 != currenUserEntity.getIsAdmin()){
            return MessageResult.getMessage("E00010");
        }
        TWmsUserEntity userEntity = this.findUserById(id);
        userEntity.setUpdateUser(currenUserEntity.getUserName());
        userEntity.setUpdateTime(new java.util.Date().getTime());
        userEntity.setPassword(PwdUtils.toMd5(GlobalConstants.DEFAULT_PASSWORD,userEntity.getUserName()));
        userMapper.updateUserPwd(userEntity);
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult modifyUserPassword(Long userId, String newPassword, String oldPassword) {
        TWmsUserEntity userEntity = this.findUserById(userId);
        String passwordOldMd5 = PwdUtils.toMd5(oldPassword,userEntity.getUserName());
        if(!passwordOldMd5.equals(userEntity.getPassword())){
            return MessageResult.getMessage("E00015");
        }
        userEntity.setUpdateUser(userEntity.getUserName());
        userEntity.setUpdateTime(new java.util.Date().getTime());
        userEntity.setPassword(PwdUtils.toMd5(newPassword,userEntity.getUserName()));
        userMapper.updateUserPwd(userEntity);
        return MessageResult.getMessage("S00004");
    }

    @Override
    public PageResponse<List> queryUserByRolesPage(Map searchMap) {
        List<Map<String, Object>> mapList = userMapper.queryUserByRolePages(searchMap);
        //转换
        List<Object[]> list = ListArraysConvert.listMapToArrayConvert(mapList);
        List roleUsersLists = new ArrayList();
        list.forEach(x->{
            Map userMap = new HashMap();
            userMap.put("id",x[0]);
            userMap.put("userId",x[1]);
            userMap.put("userName",x[2]);
            userMap.put("realName",x[3]);
            userMap.put("isAdmin",x[4]);
            roleUsersLists.add(userMap);
        });
        Integer totalSize = userMapper.queryUserByRolePageCount(searchMap);
        PageResponse<List> response = new PageResponse();
        response.setTotal(totalSize);
        response.setRows(roleUsersLists);
        return  response;
    }



}
