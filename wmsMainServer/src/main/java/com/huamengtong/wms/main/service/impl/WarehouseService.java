package com.huamengtong.wms.main.service.impl;

import com.huamengtong.wms.constants.GlobalConstants;
import com.huamengtong.wms.convert.ListArraysConvert;
import com.huamengtong.wms.core.formwork.db.domain.CurrentUserEntity;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.TWmsUserWarehouseDTO;
import com.huamengtong.wms.dto.TWmsWarehouseDTO;
import com.huamengtong.wms.entity.main.TWmsUserEntity;
import com.huamengtong.wms.entity.main.TWmsWarehouseEntity;
import com.huamengtong.wms.main.mapper.WarehouseMapper;
import com.huamengtong.wms.main.service.IUserService;
import com.huamengtong.wms.main.service.IWarehouseService;
import com.huamengtong.wms.utils.ArrayUtil;
import com.huamengtong.wms.utils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WarehouseService implements IWarehouseService {

    @Autowired
    WarehouseMapper warehouseMapper;

    @Autowired
    IUserService userService;

    @Override
    public List<TWmsWarehouseEntity> searchWarehouseByUser(CurrentUserEntity sessionCurrentUser) {
        return warehouseMapper.selectWarehouseLists(sessionCurrentUser.getId(),sessionCurrentUser.getTenantId());
    }

    @Override
    public PageResponse<List<TWmsWarehouseEntity>> queryWarehousePages(TWmsWarehouseDTO warehouseDTO) {
        TWmsWarehouseEntity warehouseEntity = BeanUtils.copyBeanPropertyUtils(warehouseDTO,TWmsWarehouseEntity.class);
        List<TWmsWarehouseEntity> warehouseEntityList = warehouseMapper.queryWarehousePages(warehouseEntity);
        Integer totalSize = warehouseMapper.queryWarehousePageCount(warehouseEntity);
        PageResponse<List<TWmsWarehouseEntity>> response = new PageResponse();
        response.setTotal(totalSize);
        response.setRows(warehouseEntityList);
        return  response;
    }


    @Override
    public TWmsWarehouseEntity findWarehouseById(Long id) {
        return warehouseMapper.selectByPrimaryKey(id);
    }

    @Override
    public TWmsWarehouseEntity findWarehouseByWarehouseNo(String warehouseNo) {
        return warehouseMapper.selectWarehouseByWarehouseNo(warehouseNo);
    }

    @Override
    public MessageResult createWarehouse(TWmsWarehouseDTO warehouseDTO) {
        TWmsWarehouseEntity warehouseEntity = BeanUtils.copyBeanPropertyUtils(warehouseDTO,TWmsWarehouseEntity.class);
        if(warehouseEntity.getTenantId() == 0 ) {
            warehouseEntity.setTenantId(GlobalConstants.DEFAULT_TENANT_ID);
        }
        warehouseMapper.insertWarehouse(warehouseEntity);
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult modifyWarehouse(TWmsWarehouseDTO warehouseDTO) {
        TWmsWarehouseEntity warehouseEntity = BeanUtils.copyBeanPropertyUtils(warehouseDTO,TWmsWarehouseEntity.class);
        warehouseMapper.updateWarehouse(warehouseEntity);
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult removeWarehouse(Long id) {
        //如果仓库存在分配用户，则不能删除
        List list = warehouseMapper.selectUserByWarehouseId(id);
        if(CollectionUtils.isNotEmpty(list)){
           return  MessageResult.getMessage("E10001");
        }
        warehouseMapper.deleteByPrimaryKey(id);
        return MessageResult.getSucMessage();
    }

    @Override
    public PageResponse<List> queryUserByWarehousePage(Map searchMap) {
        List<Map<String, Object>> mapList = warehouseMapper.queryUserByWarehousePages(searchMap);
        //转换
        List<Object[]> list = ListArraysConvert.listMapToArrayConvert(mapList);
        List userLists = new ArrayList();
        list.forEach(x->{
            Map userMap = new HashMap();
            userMap.put("id",x[0]);
            userMap.put("userId",x[1]);
            userMap.put("userName",x[2]);
            userMap.put("realName",x[3]);
            userLists.add(userMap);
        });
        Integer totalSize = warehouseMapper.queryUserByWarehousePageCount(searchMap);
        PageResponse<List> response = new PageResponse();
        response.setTotal(totalSize);
        response.setRows(userLists);
        return  response;
    }


    @Override
    public Map queryAllocatableUser(Map searchMap) {
        List<TWmsUserEntity> userEntityList = userService.queryUsersByWarehouse(searchMap);
        Map resultMap = new HashMap();
        resultMap.put("total",userEntityList.size());
        resultMap.put("rows",userEntityList);
        return resultMap;
    }

    @Override
    public MessageResult saveAllocatableUser(Map map) {
        String createUser = MapUtils.getString(map, "createUser");
        Long warehouseId = MapUtils.getLong(map, "warehouseId");
        String ids = MapUtils.getString(map, "userIds");
        if (warehouseId != null && StringUtils.isNotBlank(ids)) {
            Long[] userIds = ArrayUtil.toLongArray(ids.split(","));
            List<TWmsUserWarehouseDTO> userWarehouseDTOList = new ArrayList<>();
            for(Long userId : userIds){
                TWmsUserEntity userEntity = userService.findUserById(userId);
                TWmsUserWarehouseDTO userWarehouseDTO = new TWmsUserWarehouseDTO();
                userWarehouseDTO.setUserId(userId);
                userWarehouseDTO.setRealName(userEntity.getRealName());
                userWarehouseDTO.setUserName(userEntity.getUserName());
                userWarehouseDTO.setWarehouseId(warehouseId);
                userWarehouseDTO.setCreateTime(new java.util.Date().getTime());
                userWarehouseDTO.setCreateUser(createUser);
                userWarehouseDTO.setUpdateTime(new java.util.Date().getTime());
                userWarehouseDTO.setUpdateUser(createUser);
                userWarehouseDTOList.add(userWarehouseDTO);
            }
            warehouseMapper.insertBatchUserWarehouse(userWarehouseDTOList);
            return MessageResult.getSucMessage();
        }
        return MessageResult.getMessage("E10002");
    }

    @Override
    public MessageResult removeAllocatableUser(Map map) {
        String userWhIds = MapUtils.getString(map, "userWhIds");
        Long warehouseId = MapUtils.getLong(map, "warehouseId");
        if (warehouseId != null && StringUtils.isNotBlank(userWhIds)) {
            Long[] ids =  ArrayUtil.toLongArray(userWhIds.split(","));
            warehouseMapper.deleteBatchUserWarehouse(ids);
            return MessageResult.getSucMessage();
        }
        return MessageResult.getMessage("E10003");
    }

    @Override
    public PageResponse<List> findWarehouseByUserPage(Map searchMap) {
        Long userId = MapUtils.getLong(searchMap, "userId");
        Long tenantId = MapUtils.getLong(searchMap, "tenantId");
        List<TWmsWarehouseEntity>  warehouseEntityList =  warehouseMapper.selectWarehouseLists(userId,tenantId);
        PageResponse pageResponse = new PageResponse();
        pageResponse.setRows(warehouseEntityList);
        pageResponse.setTotal(warehouseEntityList.size());
        return pageResponse;
    }

    @Override
    public List<TWmsWarehouseEntity> searchWarehouseByIds(List<Long> ids) {
        return warehouseMapper.selectWarehouseByIds(ids);
    }

}
