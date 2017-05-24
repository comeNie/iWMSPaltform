package com.huamengtong.wms.main.service;

import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.TWmsAllocationStrategyDTO;
import com.huamengtong.wms.entity.main.TWmsAllocationStrategyEntity;

import java.util.List;

/**
 * Created by mario on 2017/2/8.
 */
public interface IAllocationStrategyService {
    PageResponse<List<TWmsAllocationStrategyEntity>> queryAllocationStrategyPages(TWmsAllocationStrategyDTO allocationStrategyDTO);

    MessageResult createAllocationStrategy(TWmsAllocationStrategyDTO allocationStrategyDTO);

    MessageResult modifyAllocationStrategy(TWmsAllocationStrategyDTO allocationStrategyDTO);

    MessageResult removeAllocationStrategy(Long id);

    TWmsAllocationStrategyEntity queryAllocationStrategy(Long warehouseId);

}
