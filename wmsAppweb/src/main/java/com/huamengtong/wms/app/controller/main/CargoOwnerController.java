package com.huamengtong.wms.app.controller.main;

import java.util.Date;

import com.huamengtong.wms.core.web.BaseController;
import com.huamengtong.wms.core.web.ResponseResult;
import com.huamengtong.wms.dto.TWmsCargoOwnerDTO;
import com.huamengtong.wms.main.service.ICargoOwnerService;
import com.wordnik.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cargo/owner")
@Api(value = "/cargo/owner",description = "货主管理")
public class CargoOwnerController extends BaseController{

    @Autowired
    private ICargoOwnerService cargoOwnerService;

    @RequestMapping(value = "",method = RequestMethod.GET)
    public ResponseResult getCargoOwnersList(TWmsCargoOwnerDTO cargoOwnerDTO)throws Exception{
        cargoOwnerDTO.setTenantId(this.getSessionCurrentUser().getTenantId());
        return getSucResultData(cargoOwnerService.queryCargoOwnerPages(cargoOwnerDTO));
    }

    @RequestMapping(value = "",method = RequestMethod.POST)
    public ResponseResult addCargoOwner(@RequestBody TWmsCargoOwnerDTO aTWmsCargoOwnerDTO)throws Exception{
        aTWmsCargoOwnerDTO.setTenantId(this.getSessionCurrentUser().getTenantId());
        aTWmsCargoOwnerDTO.setCreateTime(new Date().getTime());
        aTWmsCargoOwnerDTO.setCreateUser(this.getSessionCurrentUser().getUserName());
        aTWmsCargoOwnerDTO.setUpdateUser(this.getSessionCurrentUser().getUserName());
        aTWmsCargoOwnerDTO.setUpdateTime(new Date().getTime());
        return  getMessage(cargoOwnerService.createOwner(aTWmsCargoOwnerDTO));
    }

    @RequestMapping(value="/{id}",method = RequestMethod.PUT)
    public ResponseResult modifyCargoOwner(@RequestBody TWmsCargoOwnerDTO cargoOwnerDTO)throws Exception{
        cargoOwnerDTO.setUpdateTime(new Date().getTime());
        cargoOwnerDTO.setUpdateUser(this.getSessionCurrentUser().getUserName());
        return getMessage(cargoOwnerService.updateOwner(cargoOwnerDTO));
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public ResponseResult deleteCargoOwner(@PathVariable Long id)throws Exception{
        return getMessage(cargoOwnerService.removeCargoOwner(id));
    }
}
