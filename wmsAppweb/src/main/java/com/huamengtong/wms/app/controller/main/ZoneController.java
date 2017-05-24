package com.huamengtong.wms.app.controller.main;

import com.huamengtong.wms.core.web.BaseController;
import com.huamengtong.wms.core.web.ResponseResult;
import com.huamengtong.wms.dto.TWmsLocationDTO;
import com.huamengtong.wms.dto.TWmsZoneDTO;
import com.huamengtong.wms.main.service.ILocationService;
import com.huamengtong.wms.main.service.IZoneService;
import com.wordnik.swagger.annotations.Api;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/zone")
@Api(value = "zone",description = "货区信息")
public class ZoneController extends BaseController {

    @Autowired
    private IZoneService zoneService;

    @Autowired
    private ILocationService locationService;


    @RequestMapping(value = "",method = RequestMethod.GET)
    public ResponseResult getZoneList(TWmsZoneDTO zoneDTO) throws Exception{
        return getSucResultData(zoneService.getZoneLists(zoneDTO));
    }

    @RequestMapping(value = "/{id}/location",method = RequestMethod.GET)
    public ResponseResult queryLocationsListByZoneId(@PathVariable Long id, @RequestParam Map map)throws Exception{
        map.put("zoneId",id);
        map.put("offset",getOffset(MapUtils.getInteger(map,"page"),MapUtils.getInteger(map,"pageSize")));
        return getSucResultData(locationService.queryLocationsByZonePages(map));
    }

    @RequestMapping(value = "",method = RequestMethod.POST)
    public ResponseResult createZone(@RequestBody TWmsZoneDTO zoneDTO)throws Exception{
        zoneDTO.setWarehouseId(this.getCurrentWarehouseId());
        zoneDTO.setCreateUser(this.getSessionCurrentUser().getUserName());
        zoneDTO.setCreateTime(new Date().getTime());
        zoneDTO.setUpdateUser(this.getSessionCurrentUser().getUserName());
        zoneDTO.setUpdateTime(new Date().getTime());
        return getMessage(zoneService.createZone(zoneDTO));
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public ResponseResult modifyZone(@PathVariable Long id,@RequestBody TWmsZoneDTO zoneDTO)throws Exception{
        zoneDTO.setId(id);
        zoneDTO.setWarehouseId(this.getCurrentWarehouseId());
        zoneDTO.setUpdateUser(this.getSessionCurrentUser().getUserName());
        zoneDTO.setUpdateTime(new Date().getTime());
        return getMessage(zoneService.modifyZone(zoneDTO));
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public ResponseResult removeZone(@PathVariable Long id)throws Exception{
        return getMessage(zoneService.removeZone(id));
    }

    @RequestMapping(value = "/{id}/location",method = RequestMethod.POST)
    public ResponseResult createLocation(@PathVariable Long id, @RequestBody TWmsLocationDTO locationDTO)throws Exception{
        locationDTO.setWarehouseId(this.getCurrentWarehouseId());
        locationDTO.setZoneId(id);
        locationDTO.setCreateUser(this.getSessionCurrentUser().getUserName());
        locationDTO.setCreateTime(new Date().getTime());
        locationDTO.setUpdateUser(this.getSessionCurrentUser().getUserName());
        locationDTO.setUpdateTime(new Date().getTime());
        return getMessage(zoneService.createLocation(locationDTO));
    }

    @RequestMapping(value = "/{id}/location/{locationId}",method = RequestMethod.PUT)
    public ResponseResult modifyLocation(@PathVariable Long id,@PathVariable Long locationId,@RequestBody TWmsLocationDTO locationDTO)throws Exception{
        locationDTO.setZoneId(id);
        locationDTO.setWarehouseId(this.getCurrentWarehouseId());
        locationDTO.setId(locationId);
        locationDTO.setCreateUser(this.getSessionCurrentUser().getUserName());
        locationDTO.setCreateTime(new Date().getTime());
        locationDTO.setUpdateUser(this.getSessionCurrentUser().getUserName());
        locationDTO.setUpdateTime(new Date().getTime());
        return getMessage(zoneService.modifyLocation(locationDTO));
    }

    @RequestMapping(value = "/{id}/location/{locationId}",method = RequestMethod.DELETE)
    public ResponseResult removeLocation(@PathVariable Long id,@PathVariable Long locationId)throws Exception{
        return getMessage(zoneService.removeLocation(locationId));

    }



}
