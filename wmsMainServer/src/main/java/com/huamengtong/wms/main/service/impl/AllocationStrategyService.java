package com.huamengtong.wms.main.service.impl;

import com.huamengtong.wms.core.service.BaseService;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.TWmsAllocationStrategyDTO;
import com.huamengtong.wms.entity.main.TWmsAllocationStrategyEntity;
import com.huamengtong.wms.main.mapper.AllocationStrategyMapper;
import com.huamengtong.wms.main.service.IAllocationStrategyService;
import com.huamengtong.wms.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by mario on 2017/2/8.
 */
@Service
public class AllocationStrategyService extends BaseService implements IAllocationStrategyService {
    @Autowired
    AllocationStrategyMapper allocationStrategyMapper;


    @Override
    public PageResponse<List<TWmsAllocationStrategyEntity>> queryAllocationStrategyPages(TWmsAllocationStrategyDTO allocationStrategyDTO) {
        TWmsAllocationStrategyEntity allocationStrategyEntity = BeanUtils.copyBeanPropertyUtils(allocationStrategyDTO,TWmsAllocationStrategyEntity.class);
        List<TWmsAllocationStrategyEntity> allocationStrategyEntityList =allocationStrategyMapper.querySallocationStrategyPages(allocationStrategyEntity);
        Integer totalSize = allocationStrategyMapper.querySallocationStrategyCount(allocationStrategyEntity);
        PageResponse<List<TWmsAllocationStrategyEntity>> response = new PageResponse<>();
        response.setTotal(totalSize);
        response.setRows(allocationStrategyEntityList);
        return  response;
    }

    @Override
    public MessageResult createAllocationStrategy(TWmsAllocationStrategyDTO allocationStrategyDTO) {
        TWmsAllocationStrategyEntity allocationStrategyEntity = BeanUtils.copyBeanPropertyUtils(allocationStrategyDTO,TWmsAllocationStrategyEntity.class);
        if (allocationStrategyDTO.getIsDefault().equals(new Byte("1"))){
            Integer defalut =   allocationStrategyMapper.selectByWarehouseId(allocationStrategyDTO.getWarehouseId());
            //判断是否含有默认策略
            if (defalut>=1){
                return MessageResult.getMessage("E13001");
            }
        }


        allocationStrategyMapper.insertSallocationStrategy(allocationStrategyEntity);
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult modifyAllocationStrategy(TWmsAllocationStrategyDTO allocationStrategyDTO) {

        if (allocationStrategyDTO.getIsDefault().equals(new Byte("1"))){
             Integer defalut =   allocationStrategyMapper.selectByIsdefault(allocationStrategyDTO.getId(),allocationStrategyDTO.getWarehouseId());
            //判断是否含有默认策略
            if (defalut>=1){
                return MessageResult.getMessage("E13001");
            }
        }
        TWmsAllocationStrategyEntity allocationStrategyEntity1 = BeanUtils.copyBeanPropertyUtils(allocationStrategyDTO,TWmsAllocationStrategyEntity.class);
        allocationStrategyMapper.updateSallocationStrategy(allocationStrategyEntity1);
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult removeAllocationStrategy(Long id) {
          allocationStrategyMapper.deleteSallocationStrategy(id);
        return MessageResult.getSucMessage();
    }


    @Override
    public TWmsAllocationStrategyEntity queryAllocationStrategy(Long warehouseId) {
        return allocationStrategyMapper.selectListByWarehouseId(warehouseId);
    }

}
