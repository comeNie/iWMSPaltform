package com.huamengtong.wms.app.controller.outwh;

import com.huamengtong.wms.core.formwork.db.util.DbShareField;
import com.huamengtong.wms.core.web.BaseController;
import com.huamengtong.wms.core.web.ResponseResult;
import com.huamengtong.wms.dto.outwh.TWmsFrozenDetailDTO;
import com.huamengtong.wms.dto.outwh.TWmsFrozenHeaderDTO;
import com.huamengtong.wms.inventory.service.IInventoryService;
import com.huamengtong.wms.outwh.service.IFrozenDetailService;
import com.huamengtong.wms.outwh.service.IFrozenHeaderService;
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
@RequestMapping(value = "/factoring/frozen")
public class FrozenController extends BaseController {

    @Autowired
    IFrozenHeaderService frozenHeaderService;

    @Autowired
    IFrozenDetailService frozenDetailService;

    @Autowired
    IInventoryService inventoryService;


    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseResult getFrozenHeaderList(TWmsFrozenHeaderDTO frozenHeaderDTO) throws Exception {
        return getSucResultData(frozenHeaderService.getFrozenHeader(frozenHeaderDTO, getDbShardVO(DbShareField.OUT_WH)));
    }

    @RequestMapping(value = "/{id}/detail", method = RequestMethod.GET)
    public ResponseResult queryDetailByHeaderId(@PathVariable Long id, @RequestParam Map paramMap) throws Exception {
        paramMap.put("frozenId", id);
        paramMap.put("offset", getOffset(MapUtils.getInteger(paramMap, "page"), MapUtils.getInteger(paramMap, "pageSize")));
        return getSucResultData(frozenDetailService.queryFrozenDetailByHeader(paramMap, getDbShardVO(DbShareField.OUT_WH)));
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseResult createFrozenHeader(@RequestBody TWmsFrozenHeaderDTO frozenHeaderDTO) throws Exception {
        frozenHeaderDTO.setTenantId(getCurrentTenantId());
        frozenHeaderDTO.setWarehouseId(getCurrentWarehouseId());
        frozenHeaderDTO.setCreateUser(this.getSessionCurrentUser().getUserName());
        frozenHeaderDTO.setCreateTime(new Date().getTime());
        frozenHeaderDTO.setUpdateUser(this.getSessionCurrentUser().getUserName());
        frozenHeaderDTO.setUpdateTime(new Date().getTime());
        return getMessage(frozenHeaderService.createFrozenHeader(frozenHeaderDTO, getDbShardVO(DbShareField.OUT_WH)));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseResult removeByFrozenHeaderId(@PathVariable Long id) throws Exception {
        return getMessage(frozenHeaderService.removeFrozenHeader(id, getDbShardVO(DbShareField.OUT_WH)));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseResult modifyFrozenHeader(@RequestBody TWmsFrozenHeaderDTO frozenHeaderDTO) throws Exception {
        frozenHeaderDTO.setWarehouseId(getCurrentWarehouseId());
        frozenHeaderDTO.setUpdateUser(this.getSessionCurrentUser().getUserName());
        frozenHeaderDTO.setUpdateTime(new Date().getTime());
        return getMessage(frozenHeaderService.modifyFrozenHeader(frozenHeaderDTO, getDbShardVO(DbShareField.OUT_WH)));
    }

    @RequestMapping(value = "/{id}/detail", method = RequestMethod.POST)
    public ResponseResult createFrozenDetail(@PathVariable Long id, @RequestBody TWmsFrozenDetailDTO frozenDetailDTO) throws Exception {
        frozenDetailDTO.setFrozenId(id);
        frozenDetailDTO.setTenantId(getCurrentTenantId());
        frozenDetailDTO.setWarehouseId(getCurrentWarehouseId());
        frozenDetailDTO.setCreateUser(this.getSessionCurrentUser().getUserName());
        frozenDetailDTO.setCreateTime(new Date().getTime());
        frozenDetailDTO.setUpdateUser(this.getSessionCurrentUser().getUserName());
        frozenDetailDTO.setUpdateTime(new Date().getTime());
        return getMessage(frozenDetailService.createFrozenDetail(frozenDetailDTO, getDbShardVO(DbShareField.OUT_WH)));
    }

    @RequestMapping(value = "/{id}/detail/{detailId}", method = RequestMethod.DELETE)
    public ResponseResult deleteFrozenDetailById(@PathVariable Long id, @PathVariable Long detailId) throws Exception {
        return getMessage(frozenDetailService.removeByPrimaryKey(detailId, getDbShardVO(DbShareField.OUT_WH)));
    }

    @RequestMapping(value = "/{id}/detail/{detailId}", method = RequestMethod.PUT)
    public ResponseResult modifyFrozenDetail(@PathVariable Long id, @PathVariable Long detailId, @RequestBody TWmsFrozenDetailDTO frozenDetailDTO) throws Exception {
        frozenDetailDTO.setId(detailId);
        frozenDetailDTO.setFrozenId(id);
        frozenDetailDTO.setUpdateUser(this.getSessionCurrentUser().getUserName());
        frozenDetailDTO.setUpdateTime(new Date().getTime());
        return getMessage(frozenDetailService.modifyFrozenDetail(frozenDetailDTO, getDbShardVO(DbShareField.OUT_WH)));
    }

    /***
     * 质押单提交
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/submit/{id}", method = RequestMethod.PUT)
    public ResponseResult frozenSubmit(@PathVariable Long id) throws Exception {
        return getMessage(frozenHeaderService.executeSubmitFrozen(id,this.getSessionCurrentUser().getUserName(),getDbShardVO(DbShareField.OUT_WH)));
    }

    /***
     * 质押单撤销
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/repealed/{id}", method = RequestMethod.PUT)
    public ResponseResult frozenRepealed(@PathVariable Long id) throws Exception {
        return getMessage(frozenHeaderService.executeRepealedFrozen(id,getSessionCurrentUser().getUserName(),getDbShardVO(DbShareField.OUT_WH)));
    }

    /**
     * 质检单提交后拒绝
     *
     * @param id 质押单主表id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/reject/{id}", method = RequestMethod.PUT)
    public ResponseResult frozenReject(@PathVariable String id, @RequestBody Map map) throws Exception {
        Long frozenId = MapUtils.getLong(map,"frozenId");
        String rejectReason = MapUtils.getString(map, "rejectReason");
        return getMessage(frozenHeaderService.executeRejectFrozen(frozenId,rejectReason,getSessionCurrentUser().getUserName(),getDbShardVO(DbShareField.OUT_WH)));
    }

    /**
     * 质检单审核通过,只修改单据状态
     * @param id 质押单主表id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/audit/{id}", method = RequestMethod.PUT)
    public ResponseResult frozenAudit(@PathVariable Long id) throws Exception {
        return getMessage(frozenHeaderService.executeAuditFrozen(id,getSessionCurrentUser().getUserName(),getDbShardVO(DbShareField.OUT_WH)));
    }


    /**
     * 通过货主取出其在当前仓库未被冻结的库存
     * @param cargoOwnerId
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/data/inventory/{cargoOwnerId}",method = RequestMethod.GET)
    public ResponseResult getInventoryByCargoOwnerId(@PathVariable Long cargoOwnerId, @RequestParam Map map)throws Exception{
        map.put("cargoOwnerId",cargoOwnerId);
        map.put("offset",getOffset(MapUtils.getInteger(map,"page"),MapUtils.getInteger(map,"pageSize")));
        return getSucResultData(frozenHeaderService.getFrozenInventoryVOs(map,getDbShardVO(DbShareField.OUT_WH)));
    }


}

