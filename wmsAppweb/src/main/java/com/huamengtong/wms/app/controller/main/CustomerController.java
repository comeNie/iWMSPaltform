package com.huamengtong.wms.app.controller.main;

import com.huamengtong.wms.core.web.BaseController;
import com.huamengtong.wms.core.web.ResponseResult;
import com.huamengtong.wms.dto.TWmsCustomerDTO;
import com.huamengtong.wms.main.service.ICustomerService;
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

/**
 * Created by StickT on 2016/11/1.
 */
@RestController
@RequestMapping("/customer")
public class CustomerController extends BaseController {

    @Autowired
    ICustomerService customerService;

    @RequestMapping(value = "",method = RequestMethod.GET)
    public ResponseResult getCustomerList(TWmsCustomerDTO customerDTO)throws Exception{
        return getSucResultData(customerService.getCustomerList(customerDTO));
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public ResponseResult deleteById(@PathVariable Long id)throws Exception{
        return getMessage(customerService.removeCustomer(id));
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public ResponseResult modifyCustomer(@RequestBody TWmsCustomerDTO customerDTO)throws Exception{
        customerDTO.setUpdateUser(this.getSessionCurrentUser().getUserName());
        customerDTO.setUpdateTime(new Date().getTime());
        return getMessage(customerService.modifyCustomer(customerDTO));
    }

    @RequestMapping(value = "",method = RequestMethod.POST)
    public ResponseResult createCustomer(@RequestBody TWmsCustomerDTO customerDTO)throws Exception{
        customerDTO.setTenantId(getCurrentTenantId());
        customerDTO.setCreateUser(this.getSessionCurrentUser().getUserName());
        customerDTO.setCreateTime(new Date().getTime());
        customerDTO.setUpdateUser(this.getSessionCurrentUser().getUserName());
        customerDTO.setUpdateTime(new Date().getTime());
        return getMessage(customerService.createCustomer(customerDTO));
    }

    @RequestMapping(value = "/data",method = RequestMethod.GET)
    public ResponseResult getPopoCustomerList( @RequestParam Map map)throws Exception{
        TWmsCustomerDTO customerDTO = new TWmsCustomerDTO();
        if(map.containsKey("id")) {
            Long id = MapUtils.getLong(map, "id");
            if(id == null || id == 0){
                return getSucResultData(null);
            }
            customerDTO.setId(id);
        }
        if(map.containsKey("customerName")) {
            String customerName = MapUtils.getString(map, "customerName");
            customerDTO.setCustomerName(customerName);
        }
        if(map.containsKey("page")) {
            Integer page = MapUtils.getInteger(map, "page");
            customerDTO.setPage(page);
        }
        if(map.containsKey("pageSize")) {
            Integer pageSize = MapUtils.getInteger(map, "pageSize");
            customerDTO.setPageSize(pageSize);
        }
        return getSucResultData(customerService.getCustomerList(customerDTO));
    }


}
