package com.huamengtong.wms.app.controller.inwh;

import com.huamengtong.wms.core.formwork.db.util.DbShareField;
import com.huamengtong.wms.core.web.BaseController;
import com.huamengtong.wms.core.web.ResponseResult;
import com.huamengtong.wms.dto.inwh.TWmsQcCheckDTO;
import com.huamengtong.wms.inwh.service.IQcCheckService;
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
 * Created by could.hao on 2017/3/21.
 */
@RestController
@RequestMapping(value = "/qcCheck")
public class QcCheckController extends BaseController {

    @Autowired
    IQcCheckService qcCheckService;

    @RequestMapping(value = "",method = RequestMethod.GET)
    public ResponseResult getQcCheckHeader(TWmsQcCheckDTO qcCheckDTO)throws Exception{
        return getSucResultData(qcCheckService.selectQcCheckPage(qcCheckDTO,getDbShardVO(DbShareField.IN_WH)));
    }

    @RequestMapping(value = "",method = RequestMethod.POST)
    public ResponseResult addQcCheckHeader(@RequestBody TWmsQcCheckDTO qcCheckDTO)throws Exception{
        qcCheckDTO.setParentId(0L);
        qcCheckDTO.setTenantId(getCurrentTenantId());
        qcCheckDTO.setWarehouseId(getCurrentWarehouseId());
        qcCheckDTO.setCreateUser(getSessionCurrentUser().getUserName());
        qcCheckDTO.setCreateTime(new Date().getTime());
        qcCheckDTO.setUpdateUser(getSessionCurrentUser().getUserName());
        qcCheckDTO.setUpdateTime(new Date().getTime());
        return getSucResultData(qcCheckService.addQcCheck(qcCheckDTO,getDbShardVO(DbShareField.IN_WH)));
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public ResponseResult updateQcCheck(@PathVariable Long id,@RequestBody TWmsQcCheckDTO qcCheckDTO)throws Exception{
        qcCheckDTO.setTenantId(getCurrentTenantId());
        qcCheckDTO.setUpdateUser(getSessionCurrentUser().getUserName());
        qcCheckDTO.setUpdateTime(new Date().getTime());
        return getSucResultData(qcCheckService.updateQcCheck(qcCheckDTO,getDbShardVO(DbShareField.IN_WH)));
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public ResponseResult deleteQcCheck(@PathVariable Long id)throws Exception{
        return getSucResultData(qcCheckService.deleteQcCheck(id,getDbShardVO(DbShareField.IN_WH)));
    }

    /**
     * 根据头表id查询明细
     * @param headerId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "{headerId}/detail",method = RequestMethod.GET)
    public ResponseResult getQcCheckHeader(@PathVariable Long headerId, @RequestParam Map map)throws Exception{
        map.put("headerId",headerId);
        return getSucResultData(qcCheckService.selectQcCheckPagedetail(map,getDbShardVO(DbShareField.IN_WH)));
    }

    /**
     * 添加明细
     * @param headerId
     * @param qcCheckDTO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "{headerId}/detail",method = RequestMethod.POST)
    public ResponseResult addQcCheckDetail(@PathVariable Long headerId,@RequestBody TWmsQcCheckDTO qcCheckDTO)throws Exception{
        qcCheckDTO.setParentId(headerId);
        qcCheckDTO.setTenantId(getCurrentTenantId());
        qcCheckDTO.setWarehouseId(getCurrentWarehouseId());
        qcCheckDTO.setCreateUser(getSessionCurrentUser().getUserName());
        qcCheckDTO.setCreateTime(new Date().getTime());
        qcCheckDTO.setUpdateUser(getSessionCurrentUser().getUserName());
        qcCheckDTO.setUpdateTime(new Date().getTime());
        return getSucResultData(qcCheckService.addQcCheck(qcCheckDTO,getDbShardVO(DbShareField.IN_WH)));
    }

    /**
     * 更新明细
     * @param headerId
     * @param id
     * @param qcCheckDTO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "{headerId}/detail/{id}",method = RequestMethod.PUT)
    public ResponseResult updateQcCheckDetail(@PathVariable Long headerId,@PathVariable Long id,@RequestBody TWmsQcCheckDTO qcCheckDTO)throws Exception{
        qcCheckDTO.setTenantId(getCurrentTenantId());
        qcCheckDTO.setUpdateUser(getSessionCurrentUser().getUserName());
        qcCheckDTO.setUpdateTime(new Date().getTime());
        return getSucResultData(qcCheckService.updateQcCheck(qcCheckDTO,getDbShardVO(DbShareField.IN_WH)));
    }

    /**
     * 删除明细
     * @param headerId
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "{headerId}/detail/{id}",method = RequestMethod.DELETE)
    public ResponseResult deleteQcCheckDetail(@PathVariable Long headerId,@PathVariable Long id)throws Exception{
        return getSucResultData(qcCheckService.deleteQcCheck(id,getDbShardVO(DbShareField.IN_WH)));
    }

    /**
     * 质检单提交
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/submit/{id}",method = RequestMethod.GET)
    public ResponseResult submitQcCheckDetail(@PathVariable Long id)throws Exception{
        return getSucResultData(qcCheckService.
                updateQcCheck(id,getSessionCurrentUser().getUserName(),getDbShardVO(DbShareField.IN_WH)));
    }



}
