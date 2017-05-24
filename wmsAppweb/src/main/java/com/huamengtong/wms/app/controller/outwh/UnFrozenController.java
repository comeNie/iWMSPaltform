package com.huamengtong.wms.app.controller.outwh;

import com.huamengtong.wms.core.formwork.db.util.DbShareField;
import com.huamengtong.wms.core.web.BaseController;
import com.huamengtong.wms.core.web.ResponseResult;
import com.huamengtong.wms.dto.outwh.TWmsUnfrozenDetailDTO;
import com.huamengtong.wms.dto.outwh.TWmsUnfrozenHeaderDTO;
import com.huamengtong.wms.outwh.service.IUnfrozenDetailService;
import com.huamengtong.wms.outwh.service.IUnfrozenHeaderService;
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
@RequestMapping(value = "/factoring/unfrozen")
public class UnFrozenController extends BaseController{
    @Autowired
    IUnfrozenHeaderService unfrozenHeaderService;

    @Autowired
    IUnfrozenDetailService unfrozenDetailService;

    @RequestMapping(value = "",method = RequestMethod.GET)
    public ResponseResult getUnfrozenHeaderList(TWmsUnfrozenHeaderDTO unfrozenHeaderDTO) throws  Exception{
        return getSucResultData(unfrozenHeaderService.getUnfrozenHeader(unfrozenHeaderDTO,getDbShardVO(DbShareField.OUT_WH)));
    }

    @RequestMapping(value = "/{id}/detail",method = RequestMethod.GET)
    public ResponseResult queryDetailByHeaderId(@PathVariable Long id, @RequestParam Map paramMap)throws  Exception{
        paramMap.put("unFrozenId",id);
        paramMap.put("offset",getOffset(MapUtils.getInteger(paramMap,"page"),MapUtils.getInteger(paramMap,"pageSize")));
        return getSucResultData(unfrozenDetailService.queryUnfrozenDetailByHeader(paramMap,getDbShardVO(DbShareField.OUT_WH)));
    }

    @RequestMapping(value = "",method = RequestMethod.POST)
    public ResponseResult createUnfrozenHeader(@RequestBody TWmsUnfrozenHeaderDTO unfrozenHeaderDTO) throws Exception{
        unfrozenHeaderDTO.setTenantId(getCurrentTenantId());
        unfrozenHeaderDTO.setWarehouseId(getCurrentWarehouseId());
        unfrozenHeaderDTO.setCreateUser(this.getSessionCurrentUser().getUserName());
        unfrozenHeaderDTO.setCreateTime(new Date().getTime());
        unfrozenHeaderDTO.setUpdateUser(this.getSessionCurrentUser().getUserName());
        unfrozenHeaderDTO.setUpdateTime(new Date().getTime());
        return  getMessage(unfrozenHeaderService.createUnfrozenHeader(unfrozenHeaderDTO,getDbShardVO(DbShareField.OUT_WH)));
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public ResponseResult removeByUnfrozenHeaderId(@PathVariable Long id)throws Exception{
        return getMessage(unfrozenHeaderService.removeUnfrozenHeader(id,getDbShardVO(DbShareField.OUT_WH)));
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public ResponseResult modifyUnfrozenHeader(@RequestBody TWmsUnfrozenHeaderDTO unfrozenHeaderDTO)throws  Exception{
        unfrozenHeaderDTO.setWarehouseId(getCurrentWarehouseId());
        unfrozenHeaderDTO.setUpdateUser(this.getSessionCurrentUser().getUserName());
        unfrozenHeaderDTO.setUpdateTime(new Date().getTime());
        return getMessage(unfrozenHeaderService.modifyUnfrozenHeader(unfrozenHeaderDTO,getDbShardVO(DbShareField.OUT_WH)));
    }

    @RequestMapping(value = "/{id}/detail",method = RequestMethod.POST)
    public ResponseResult createUnfrozenDetail(@PathVariable Long id, @RequestBody TWmsUnfrozenDetailDTO unfrozenDetailDTO) throws Exception{
        unfrozenDetailDTO.setUnfrozenId(id);
        unfrozenDetailDTO.setTenantId(getCurrentTenantId());
        unfrozenDetailDTO.setWarehouseId(getCurrentWarehouseId());
        unfrozenDetailDTO.setCreateUser(this.getSessionCurrentUser().getUserName());
        unfrozenDetailDTO.setCreateTime(new Date().getTime());
        unfrozenDetailDTO.setUpdateUser(this.getSessionCurrentUser().getUserName());
        unfrozenDetailDTO.setUpdateTime(new Date().getTime());
        return getMessage(unfrozenDetailService.createUnfrozenDetail(unfrozenDetailDTO,getDbShardVO(DbShareField.OUT_WH)));
    }

    @RequestMapping(value = "/{id}/detail/{detailId}",method = RequestMethod.DELETE)
    public ResponseResult deleteUnfrozenDetailById(@PathVariable Long id,@PathVariable Long detailId)throws  Exception{
        return getMessage(unfrozenDetailService.removeUnfrozenDetail(detailId,getDbShardVO(DbShareField.OUT_WH)));
    }

    @RequestMapping(value = "/{id}/detail/{detailId}",method = RequestMethod.PUT)
    public ResponseResult modifyUnfrozenDetail(@PathVariable Long id,@PathVariable Long detailId,@RequestBody TWmsUnfrozenDetailDTO unfrozenDetailDTO) throws  Exception{
        unfrozenDetailDTO.setId(detailId);
        unfrozenDetailDTO.setUnfrozenId(id);
        unfrozenDetailDTO.setUpdateUser(this.getSessionCurrentUser().getUserName());
        unfrozenDetailDTO.setUpdateTime(new Date().getTime());
        return getMessage(unfrozenDetailService.modifyUnfrozenDetail(unfrozenDetailDTO,getDbShardVO(DbShareField.OUT_WH)));
    }


    /***
     * 支持单个未提交的解押单提交,将其中所有的明细一并提交
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/submit/{id}",method = RequestMethod.PUT)
    public ResponseResult submitUnfrozen(@PathVariable String id) throws  Exception{
        Long unfrozenId=Long.parseLong(id);
        return getMessage(unfrozenHeaderService.executeSubmitUnfrozen(unfrozenId,this.getSessionCurrentUser().getUserName(),getDbShardVO(DbShareField.OUT_WH)));
    }


    /**
     * 支持单个作废未提交的质押单，作废后状态为已撤销,并且不可再次被提交
     * 不修改其中的数据
     * @param id  解押单id
     * @param paramMap  作废原因，写入备注中
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/invalid/{id}" ,method = RequestMethod.PUT)
    public ResponseResult unfrozenInvalid(@PathVariable Long id,@RequestBody Map paramMap)throws Exception{
        String invalidReason = MapUtils.getString(paramMap,"invalidReason");
        return getMessage(unfrozenHeaderService.executeInvalidUnfrozen(id,invalidReason,this.getSessionCurrentUser().getUserName(),getDbShardVO(DbShareField.OUT_WH)));
    }


    /***
     * 支持单个解压单提交后撤销，回写本次申请解押数量
     * 此方法不可复用
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/repealed/{id}",method = RequestMethod.PUT)
    public ResponseResult repealedUnfrozen(@PathVariable Long id)throws  Exception{
        return getMessage(unfrozenHeaderService.executeRepealedUnfrozen(id,this.getSessionCurrentUser().getUserName(),getDbShardVO(DbShareField.OUT_WH)));
    }


    /**
     * 支持单个拒绝审核通过质押单
     * @param id
     * @param paramMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/reject/{id}",method = RequestMethod.PUT)
    public ResponseResult rejectUnfrozen(@PathVariable Long id,@RequestBody Map paramMap )throws Exception{
        String rejectReason = MapUtils.getString(paramMap,"rejectReason");
        return getMessage(unfrozenHeaderService.executeRejectUnfrozen(id,rejectReason,this.getSessionCurrentUser().getUserName(),getDbShardVO(DbShareField.OUT_WH)));
    }

    @RequestMapping(value = "/audit/{id}",method = RequestMethod.PUT)
    public ResponseResult auditUnfrozen(@PathVariable Long id)throws  Exception{
        return getMessage(unfrozenHeaderService.executeAuditUnfrozen(id,this.getSessionCurrentUser().getUserName(),getDbShardVO(DbShareField.OUT_WH)));
    }


    /**
     * 批量提交解押明细单，
     * @param id  以选择的明细ids
     * @param paramMap 参数
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/detail/submit/{id}",method = RequestMethod.PUT)
    public ResponseResult submitUnfrozenDetails(@PathVariable String id,@RequestBody Map paramMap)throws Exception{
        Long unfrozenDetailId = MapUtils.getLong(paramMap,"unfrozenDetailId");
        Integer factoringQty = MapUtils.getInteger(paramMap,"factoringQty");
        return getMessage(unfrozenDetailService.executeSubmitUnfrozenDetail(unfrozenDetailId,factoringQty,this.getSessionCurrentUser().getUserName(),getDbShardVO(DbShareField.OUT_WH)));
    }

}
