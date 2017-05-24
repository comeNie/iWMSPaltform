package com.huamengtong.wms.app.controller.main;

import com.huamengtong.wms.core.web.BaseController;
import com.huamengtong.wms.core.web.ResponseResult;
import com.huamengtong.wms.dto.TWmsAllocationStrategyDTO;
import com.huamengtong.wms.main.service.IAllocationStrategyService;
import com.wordnik.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("allocation/strategy")
@Api(value = "allocation/strategy", description = "策略信息")
public class AllocationStrategyController extends BaseController {
    @Autowired
    IAllocationStrategyService allocationStrategyService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseResult getAllocationStrategysList(TWmsAllocationStrategyDTO allocationStrategyDTO) throws Exception {
        return getSucResultData(allocationStrategyService.queryAllocationStrategyPages(allocationStrategyDTO));

    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseResult createAllocationStrategy(@RequestBody TWmsAllocationStrategyDTO allocationStrategyDTO) throws Exception {
        allocationStrategyDTO.setTenantId(getCurrentTenantId());
        allocationStrategyDTO.setCreateUser(this.getSessionCurrentUser().getUserName());
        allocationStrategyDTO.setCreateTime(new Date().getTime());
        allocationStrategyDTO.setUpdateUser(this.getSessionCurrentUser().getUserName());
        allocationStrategyDTO.setUpdateTime(new Date().getTime());
        return getMessage(allocationStrategyService.createAllocationStrategy(allocationStrategyDTO));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseResult removeAllocationStrategy(@PathVariable Long id) throws Exception {
        return getMessage(allocationStrategyService.removeAllocationStrategy(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseResult modifyAllocationStrategy(@RequestBody TWmsAllocationStrategyDTO allocationStrategyDTO) throws Exception {
        allocationStrategyDTO.setUpdateUser(this.getSessionCurrentUser().getUserName());
        allocationStrategyDTO.setUpdateTime(new Date().getTime());
        return getMessage(allocationStrategyService.modifyAllocationStrategy(allocationStrategyDTO));
    }

}

