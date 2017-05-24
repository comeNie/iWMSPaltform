package com.huamengtong.wms.app.controller.main;

import com.huamengtong.wms.core.web.BaseController;
import com.huamengtong.wms.core.web.ResponseResult;
import com.huamengtong.wms.dto.TWmsRoleDTO;
import com.huamengtong.wms.main.service.IPermissionService;
import com.huamengtong.wms.main.service.IRoleService;
import com.huamengtong.wms.main.service.IUserService;
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
@RequestMapping("role")
public class RoleController extends BaseController {

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IPermissionService permissionService;


    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseResult queryRoles(TWmsRoleDTO roleDTO){
        roleDTO.setTenantId(getSessionCurrentUser().getTenantId());
        return new ResponseResult(roleService.queryRolePages(roleDTO));
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseResult createRoles(@RequestBody TWmsRoleDTO roleDTO){
        roleDTO.setTenantId(getSessionCurrentUser().getTenantId());
        roleDTO.setCreateUser(this.getSessionCurrentUser().getUserName());
        roleDTO.setCreateTime(new Date().getTime());
        roleDTO.setUpdateUser(this.getSessionCurrentUser().getUserName());
        roleDTO.setUpdateTime(new Date().getTime());
        return getMessage(roleService.createRole(roleDTO));
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseResult modifyRoles(@PathVariable Long id, @RequestBody TWmsRoleDTO roleDTO){
        roleDTO.setUpdateUser(this.getSessionCurrentUser().getUserName());
        roleDTO.setUpdateTime(new Date().getTime());
        return getMessage(roleService.modifyRole(roleDTO));
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseResult removeRoles(@PathVariable Long id){
        return getMessage(roleService.removeByPrimaryKey(id));
    }

    @RequestMapping(value = "{id}/user", method = RequestMethod.GET)
    public ResponseResult queryUserByRoles(@PathVariable Long id,@RequestParam Map searchMap){
        searchMap.put("roleId",id);
        searchMap.put("tenantId",getSessionCurrentUser().getTenantId());
        searchMap.put("offset",getOffset(MapUtils.getInteger(searchMap,"page"),MapUtils.getInteger(searchMap,"pageSize")));
        return getSucResultData(userService.queryUserByRolesPage(searchMap));
    }

    @RequestMapping(value = "{id}/module", method = RequestMethod.GET)
    public ResponseResult queryModuleByRoles(@PathVariable Long id,@RequestParam Map searchMap){
        searchMap.put("roleId",id);
        return getSucResultData(permissionService.queryPermModuleByRoles(searchMap));
    }

    @RequestMapping(value = "/{id}/module",method = RequestMethod.POST)
    public ResponseResult saveRoleActionPermission(@PathVariable Long id,@RequestBody Map map) throws Exception {
        map.put("roleId",id);
        map.put("createUser",this.getSessionCurrentUser().getUserName());
        return getMessage(permissionService.saveRoleActionPermission(map));
    }

}
