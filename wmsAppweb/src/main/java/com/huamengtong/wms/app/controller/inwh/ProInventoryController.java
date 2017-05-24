package com.huamengtong.wms.app.controller.inwh;

import com.huamengtong.wms.core.formwork.db.util.DbShareField;
import com.huamengtong.wms.core.web.BaseController;
import com.huamengtong.wms.core.web.ResponseResult;
import com.huamengtong.wms.dto.inventory.TWmsProInventoryDTO;
import com.huamengtong.wms.inventory.service.IProInventoryService;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;


/**
 * Created by could.hao on 2017/1/5.
 */
@RestController
@RequestMapping(value = "/proInventory")
public class ProInventoryController extends BaseController{

    @Autowired
    IProInventoryService proInventoryService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseResult getProInventoryList(TWmsProInventoryDTO proInventoryDTO) throws  Exception{
        proInventoryDTO.setParentId(0L);
        return getSucResultData(proInventoryService.queryProInventoryPages(proInventoryDTO,getDbShardVO(DbShareField.IN_WH)));
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseResult createProInventory(@RequestBody Map map) throws Exception {
        TWmsProInventoryDTO proInventoryDTO = new TWmsProInventoryDTO();
        BeanUtils.populate(proInventoryDTO,MapUtils.getMap(map,"header"));
        proInventoryDTO.setTenantId(this.getCurrentTenantId());
        proInventoryDTO.setWarehouseId(this.getCurrentWarehouseId());
        proInventoryDTO.setCreateUser(getSessionCurrentUser().getUserName());
        proInventoryDTO.setUpdateUser(getSessionCurrentUser().getUserName());
        List<Map> proInventoryDTOList = (List<Map>) MapUtils.getObject(map,"detailList");
        return getMessage(proInventoryService.createProInventory(proInventoryDTO,proInventoryDTOList,getDbShardVO(DbShareField.IN_WH)));
    }



    @RequestMapping(value = "/{id}/detail",method = RequestMethod.GET)
    public ResponseResult getProInventoryDetailList(@PathVariable Long id)throws Exception{
        TWmsProInventoryDTO proInventoryDTO = new TWmsProInventoryDTO();
        proInventoryDTO.setParentId(id);
        proInventoryDTO.setWarehouseId(this.getCurrentWarehouseId());
        proInventoryDTO.setTenantId(this.getCurrentTenantId());
        return getSucResultData(proInventoryService.queryProInventoryPages(proInventoryDTO,getDbShardVO(DbShareField.IN_WH)));
    }


    @RequestMapping(value = "/audit/{id}",method = RequestMethod.PUT)
    public ResponseResult auditProInventory(@PathVariable Long id)throws Exception{
        return getMessage(proInventoryService.executeAuditProInventory(id,this.getSessionCurrentUser().getUserName(),getDbShardVO(DbShareField.IN_WH)));
    }

    /**
     * 分拣撤销方法
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/revoke/{id}",method = RequestMethod.PUT)
    public ResponseResult revokeProInventory(@PathVariable Long id)throws Exception{
        return getMessage(proInventoryService.executeRevokeProInventory(id,this.getSessionCurrentUser().getUserName(),getDbShardVO(DbShareField.IN_WH)));
    }

}
