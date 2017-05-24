package com.huamengtong.wms.app.controller.main;

import com.huamengtong.wms.core.web.BaseController;
import com.huamengtong.wms.core.web.ResponseResult;
import com.huamengtong.wms.dto.TWmsOrganizationsDTO;
import com.huamengtong.wms.main.service.IOrganizationsService;
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
@RequestMapping("/organizations")
@Api(value = "/organizations",description = "组织管理")
public class OrganizationsController extends BaseController{

    @Autowired
    private IOrganizationsService organizationsService;

    @RequestMapping(value = "",method = RequestMethod.GET)
    public ResponseResult getOrganizations(TWmsOrganizationsDTO organizationsDTO)throws Exception{
        return getSucResultData(organizationsService.queryOrganizationPages(organizationsDTO));
    }

    @RequestMapping(value = "/data/{typeCode}",method = RequestMethod.GET)
    public ResponseResult getOrganizationsByTypeCode(@PathVariable String typeCode, @RequestParam Map map){
        TWmsOrganizationsDTO organizationsDTO= new TWmsOrganizationsDTO();
        if (typeCode !=null){
            organizationsDTO.setTypeCode(typeCode);
            organizationsDTO.setTenantId(this.getCurrentTenantId());
        }
        if(map.containsKey("id")) {
            Long id = MapUtils.getLong(map, "id");
            if(id == null || id == 0){
                return getSucResultData(null);
            }
            organizationsDTO.setId(id);
        }
        if(map.containsKey("name")) {
            String name = MapUtils.getString(map, "name");
            organizationsDTO.setName(name);
        }
        return getSucResultData(organizationsService.queryOrganizationPages(organizationsDTO));
    }

    @RequestMapping(value = "",method = RequestMethod.POST)
    public ResponseResult addOrganizations(@RequestBody TWmsOrganizationsDTO organizationsDTO)throws Exception{
        organizationsDTO.setTenantId(this.getSessionCurrentUser().getTenantId());
        organizationsDTO.setCreateTime(new Date().getTime());
        organizationsDTO.setCreateUser(this.getSessionCurrentUser().getUserName());
        organizationsDTO.setUpdateTime(new Date().getTime());
        organizationsDTO.setUpdateUser(this.getSessionCurrentUser().getUserName());
        return getMessage(organizationsService.createOrganization(organizationsDTO));
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public ResponseResult modifyOrganizations(@RequestBody TWmsOrganizationsDTO organizationsDTO)throws Exception{
        organizationsDTO.setUpdateUser(this.getSessionCurrentUser().getUserName());
        organizationsDTO.setUpdateTime(new Date().getTime());
        return getMessage(organizationsService.modifyOrganization(organizationsDTO));
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public ResponseResult deleteOrganizations(@PathVariable Long id)throws Exception{
        return getMessage(organizationsService.removeOrganization(id));
    }
}