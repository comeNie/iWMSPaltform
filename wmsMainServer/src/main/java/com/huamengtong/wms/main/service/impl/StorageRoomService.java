package com.huamengtong.wms.main.service.impl;

import com.huamengtong.wms.core.service.BaseService;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.TWmsStorageRoomDTO;
import com.huamengtong.wms.entity.main.TWmsStorageRoomEntity;
import com.huamengtong.wms.main.mapper.StorageRoomMapper;
import com.huamengtong.wms.main.service.IStorageRoomService;
import com.huamengtong.wms.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class StorageRoomService extends BaseService implements IStorageRoomService {

    @Autowired
    private StorageRoomMapper storageRoomMapper;

    @Override
    public PageResponse<List<TWmsStorageRoomEntity>> queryStorageRoomPages(TWmsStorageRoomDTO roomDTO) {
        TWmsStorageRoomEntity roomEntity = BeanUtils.copyBeanPropertyUtils(roomDTO,TWmsStorageRoomEntity.class);
        List<TWmsStorageRoomEntity> roomEntityList= storageRoomMapper.queryStorageRoomPages(roomEntity);
        Integer totalSize= storageRoomMapper.queryStorageRoomPageCount(roomEntity);
        PageResponse<List<TWmsStorageRoomEntity>> response=new PageResponse();
        response.setTotal(totalSize);
        response.setRows(roomEntityList);
        return response;
    }

    @Override
    public TWmsStorageRoomEntity findByPrimaryKey(Long id) {
        return storageRoomMapper.selectByPrimaryKey(id);
    }

    @Override
    public MessageResult removeByPrimaryKey(Long id) {
        TWmsStorageRoomEntity  storageRoomEntity = storageRoomMapper.selectByPrimaryKey(id);
        if(storageRoomEntity.getIsDefault() == 1){
            MessageResult.getMessage("E10007");
        }
        storageRoomMapper.deleteByPrimaryKey(id);
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult createStorageRoom(TWmsStorageRoomDTO roomDTO) {
        TWmsStorageRoomEntity roomEntity=BeanUtils.copyBeanPropertyUtils(roomDTO,TWmsStorageRoomEntity.class);
        storageRoomMapper.insertStorageRoom(roomEntity);
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult modifyStorageRoom(TWmsStorageRoomDTO roomDTO) {
        TWmsStorageRoomEntity roomEntity=BeanUtils.copyBeanPropertyUtils(roomDTO,TWmsStorageRoomEntity.class);
        storageRoomMapper.updateStorageRoom(roomEntity);
        return MessageResult.getSucMessage();
    }

    @Override
    public List<TWmsStorageRoomEntity> findStorageRoomsBywhId(Long id) {
        return storageRoomMapper.queryStorageRoomsBywhId(id);
    }

    @Override
    public List<TWmsStorageRoomEntity> getUseStorageRooms(Long warehouseId) {
        return storageRoomMapper.queryStorageRoomsBywhId(warehouseId);
    }

    @Override
    public List<TWmsStorageRoomEntity> getByIds(List<Long> ids) {
        return storageRoomMapper.selectByIds(ids);
    }

    @Override
    public List<String> getRoomNoByIds(List<Long> ids) {
        return storageRoomMapper.selectRoomNoByIds(ids);
    }
}
