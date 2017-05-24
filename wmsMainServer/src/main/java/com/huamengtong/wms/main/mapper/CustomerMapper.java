package com.huamengtong.wms.main.mapper;

import com.huamengtong.wms.entity.main.TWmsCustomerEntity;

import java.util.List;

/**
 * Created by StickT on 2016/11/1.
 */
public interface CustomerMapper {

    List<TWmsCustomerEntity> queryCustomerPages(TWmsCustomerEntity customerEntity);

    Integer queryCustomerPageCount(TWmsCustomerEntity customerEntity);

    Integer insertCustomer(TWmsCustomerEntity customerEntity);

    TWmsCustomerEntity selectByPrimaryKey(Long id);

    Integer deleteByPrimaryKey(Long id);

    Integer updateCustomer(TWmsCustomerEntity customerEntity);
}
