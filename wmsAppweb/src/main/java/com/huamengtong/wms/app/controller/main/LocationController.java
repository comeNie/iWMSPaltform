package com.huamengtong.wms.app.controller.main;

import com.huamengtong.wms.core.web.BaseController;
import com.huamengtong.wms.core.web.ResponseResult;
import com.huamengtong.wms.dto.TWmsLocationDTO;
import com.huamengtong.wms.main.service.ILocationService;
import com.wordnik.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("location")
@Api(value = "location",description = "货位信息")
public class LocationController extends BaseController {

    @Autowired
    private ILocationService locationService;


    @RequestMapping(value = "",method = RequestMethod.GET)
    public ResponseResult getLocationlist(Map map) throws Exception{
        return getSucResultData(locationService.queryLocationPages(map));
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public ResponseResult modifyLocation(@RequestBody TWmsLocationDTO locationDTO) throws Exception{
        locationDTO.setUpdateUser(this.getSessionCurrentUser().getUserName());
        locationDTO.setUpdateTime(new Date().getTime());
        return getMessage(locationService.modifyLocation(locationDTO));
    }

    @RequestMapping(value="/{id}",method = RequestMethod.DELETE)
    public ResponseResult removeLocation(@PathVariable Long id)throws Exception{
        return getMessage(locationService.removeByPrimaryKey(id));
    }

    @RequestMapping(value = "",method = RequestMethod.POST)
    public ResponseResult createLocation(@RequestBody TWmsLocationDTO locationDTO) throws  Exception{
        locationDTO.setWarehouseId(this.getCurrentWarehouseId());
        locationDTO.setCreateUser(this.getSessionCurrentUser().getUserName());
        locationDTO.setCreateTime(new Date().getTime());
        locationDTO.setUpdateUser(this.getSessionCurrentUser().getUserName());
        locationDTO.setUpdateTime(new Date().getTime());
        return getMessage(locationService.createLocation(locationDTO));
    }
}
