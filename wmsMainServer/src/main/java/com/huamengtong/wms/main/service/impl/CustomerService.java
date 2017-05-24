package com.huamengtong.wms.main.service.impl;

import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.TWmsCustomerDTO;
import com.huamengtong.wms.entity.main.TWmsCustomerEntity;
import com.huamengtong.wms.main.mapper.CustomerMapper;
import com.huamengtong.wms.main.service.ICustomerService;
import com.huamengtong.wms.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by StickT on 2016/11/1.
 */

@Service
public class CustomerService implements ICustomerService {

    @Autowired
    CustomerMapper customerMapper;

    @Override
    public PageResponse<List<TWmsCustomerEntity>> getCustomerList(TWmsCustomerDTO customerDTO) {
        TWmsCustomerEntity customerEntity= BeanUtils.copyBeanPropertyUtils(customerDTO,TWmsCustomerEntity.class);
        List<TWmsCustomerEntity> customerEntities=customerMapper.queryCustomerPages(customerEntity);
        Integer totalSize=customerMapper.queryCustomerPageCount(customerEntity);
        PageResponse<List<TWmsCustomerEntity>> response=new PageResponse();
        response.setRows(customerEntities);
        response.setTotal(totalSize);
        return response;
    }

    @Override
    public MessageResult removeCustomer(Long id) {
        customerMapper.deleteByPrimaryKey(id);
        return MessageResult.getSucMessage();
    }

    @Override
    public TWmsCustomerEntity findByPrimaryKey(Long id) {
        return customerMapper.selectByPrimaryKey(id);
    }

    @Override
    public MessageResult createCustomer(TWmsCustomerDTO customerDTO) {
        TWmsCustomerEntity customerEntity=BeanUtils.copyBeanPropertyUtils(customerDTO,TWmsCustomerEntity.class);
        customerMapper.insertCustomer(customerEntity);
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult modifyCustomer(TWmsCustomerDTO customerDTO) {
        TWmsCustomerEntity customerEntity=BeanUtils.copyBeanPropertyUtils(customerDTO,TWmsCustomerEntity.class);
        customerMapper.updateCustomer(customerEntity);
        return MessageResult.getSucMessage();
    }
}
