package com.huamengtong.wms.main.service.impl;


import java.util.List;
import java.util.Map;

import com.huamengtong.wms.core.service.BaseService;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.TWmsCargoOwnerDTO;
import com.huamengtong.wms.entity.main.TWmsCargoOwnerEntity;
import com.huamengtong.wms.main.mapper.CargoOwnerMapper;
import com.huamengtong.wms.main.service.ICargoOwnerService;
import com.huamengtong.wms.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CargoOwnerService extends BaseService implements ICargoOwnerService {

    @Autowired
    CargoOwnerMapper cargoOwnerMapper;

    @Override
    public MessageResult createOwner(TWmsCargoOwnerDTO ownerDTO) {
        TWmsCargoOwnerEntity cargoOwnerEntity = BeanUtils.copyBeanPropertyUtils(ownerDTO, TWmsCargoOwnerEntity.class);
        cargoOwnerMapper.insertOwner(cargoOwnerEntity);
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult updateOwner(TWmsCargoOwnerDTO ownerDTO) {
        TWmsCargoOwnerEntity cargoOwnerEntity = BeanUtils.copyBeanPropertyUtils(ownerDTO, TWmsCargoOwnerEntity.class);
        cargoOwnerMapper.updateOwner(cargoOwnerEntity);
        return MessageResult.getSucMessage();
    }

    @Override
    public TWmsCargoOwnerEntity findCargoOwnerByCargoOwnerNo(String cargoOwnerNo) {
        return cargoOwnerMapper.selectCargoOwnerByCargoOwnerNo(cargoOwnerNo);
    }

    @Override
    public List<TWmsCargoOwnerEntity> findCargoOwner(Map map) {
        return  cargoOwnerMapper.selectALLCargoOwners(map);
    }

    
    @Override
    public PageResponse<List<TWmsCargoOwnerEntity>> queryCargoOwnerPages(TWmsCargoOwnerDTO ownerDTO) {
        TWmsCargoOwnerEntity cargoOwnerEntity = BeanUtils.copyBeanPropertyUtils(ownerDTO, TWmsCargoOwnerEntity.class);
        List<TWmsCargoOwnerEntity> cargoOwnerEntityList = cargoOwnerMapper.queryOwnerPages(cargoOwnerEntity);
        Integer totalSize = cargoOwnerMapper.queryCargoOwnerPageCount(cargoOwnerEntity);
        PageResponse<List<TWmsCargoOwnerEntity>> response = new PageResponse();
        response.setTotal(totalSize);
        response.setRows(cargoOwnerEntityList);
        return response;
    }

    @Override
    public MessageResult removeCargoOwner(Long id) {
        cargoOwnerMapper.deleteOwner(id);
        return MessageResult.getSucMessage();
    }

    @Override
    public TWmsCargoOwnerEntity findCargoOwnerById(Long id) {
        return cargoOwnerMapper.queryOwnerById(id);
    }

    @Override
    public List<TWmsCargoOwnerEntity> findByIds(List<Long> ids) {
        return cargoOwnerMapper.selectByIds(ids);
    }
}



