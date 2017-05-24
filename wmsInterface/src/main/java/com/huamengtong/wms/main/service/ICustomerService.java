package com.huamengtong.wms.main.service;

import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.TWmsCustomerDTO;
import com.huamengtong.wms.entity.main.TWmsCustomerEntity;

import java.util.List;

/**
 * Created by StickT on 2016/11/1.
 */
public interface ICustomerService {

    PageResponse<List<TWmsCustomerEntity>> getCustomerList(TWmsCustomerDTO customerDTO);

    MessageResult removeCustomer(Long id);

    TWmsCustomerEntity findByPrimaryKey(Long id);

    MessageResult createCustomer(TWmsCustomerDTO customerDTO);

    MessageResult modifyCustomer(TWmsCustomerDTO customerDTO);


}
