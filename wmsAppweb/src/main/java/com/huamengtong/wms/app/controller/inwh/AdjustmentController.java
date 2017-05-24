package com.huamengtong.wms.app.controller.inwh;

import com.huamengtong.wms.core.formwork.db.util.DbShareField;
import com.huamengtong.wms.core.web.BaseController;
import com.huamengtong.wms.core.web.ResponseResult;
import com.huamengtong.wms.dto.inwh.TWmsAdjustmentDetailDTO;
import com.huamengtong.wms.dto.inwh.TWmsAdjustmentHeaderDTO;
import com.huamengtong.wms.em.TicketStatusCode;
import com.huamengtong.wms.inwh.service.IAdjustmentDetailService;
import com.huamengtong.wms.inwh.service.IAdjustmentHeaderService;
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
 * Created by mario on 2016/11/17.
 */
@RestController
@RequestMapping(value = "/inventory/adjustment")
public class AdjustmentController extends BaseController {

    @Autowired
    IAdjustmentHeaderService adjustmentHeaderService;

    @Autowired
    IAdjustmentDetailService adjustmentDetailService;



    @RequestMapping(value = "",method = RequestMethod.GET)
    public ResponseResult getAdjustmentHeaderList(TWmsAdjustmentHeaderDTO adjustmentHeaderDTO) throws Exception{
        return  getSucResultData(adjustmentHeaderService.getAdjustmentHeader(adjustmentHeaderDTO,getDbShardVO(DbShareField.IN_WH)));
    }

    @RequestMapping(value = "/{id}/detail",method = RequestMethod.GET)
    public ResponseResult queryDetailByHeaderId(@PathVariable Long id, @RequestParam Map paramMap) throws  Exception{
        paramMap.put("adjustId",id);
        paramMap.put("offset",getOffset(MapUtils.getInteger(paramMap,"page"),MapUtils.getInteger(paramMap,"pageSize")));
        return  getSucResultData(adjustmentDetailService.queryAdjustmentDetailByHeader(paramMap,getDbShardVO(DbShareField.IN_WH)));
    }

    @RequestMapping(value = "",method = RequestMethod.POST)
    public ResponseResult createAdjustmentHeader(@RequestBody TWmsAdjustmentHeaderDTO adjustmentHeaderDTO)throws  Exception{
        adjustmentHeaderDTO.setTenantId(getCurrentTenantId());
        adjustmentHeaderDTO.setWarehouseId(getCurrentWarehouseId());
        adjustmentHeaderDTO.setCreateUser(this.getSessionCurrentUser().getUserName());
        adjustmentHeaderDTO.setCreateTime(new Date().getTime());
        adjustmentHeaderDTO.setUpdateUser(this.getSessionCurrentUser().getUserName());
        adjustmentHeaderDTO.setUpdateTime(new Date().getTime());
        return  getMessage(adjustmentHeaderService.createAdjustmentHeader(adjustmentHeaderDTO,getDbShardVO(DbShareField.IN_WH)));
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public  ResponseResult removeByAdjustmentHeaderId(@PathVariable Long id) throws Exception{
        return getMessage(adjustmentHeaderService.removeAdjustmentHeader(id,getDbShardVO(DbShareField.IN_WH)));
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public ResponseResult modifyAdjustmentHeader(@RequestBody TWmsAdjustmentHeaderDTO adjustmentHeaderDTO) throws Exception {
        adjustmentHeaderDTO.setWarehouseId(getCurrentWarehouseId());
        adjustmentHeaderDTO.setUpdateUser(this.getSessionCurrentUser().getUserName());
        adjustmentHeaderDTO.setUpdateTime(new Date().getTime());
        return  getMessage(adjustmentHeaderService.modifyAdjustmentHeader(adjustmentHeaderDTO,getDbShardVO(DbShareField.IN_WH)));
    }

    @RequestMapping(value = "/{id}/detail",method = RequestMethod.POST)
    public ResponseResult createAdjustmentDetail(@PathVariable Long id, @RequestBody TWmsAdjustmentDetailDTO adjustmentDetailDTO)throws  Exception{

        adjustmentDetailDTO.setAdjustId(id);
        adjustmentDetailDTO.setTenantId(getCurrentTenantId());
        adjustmentDetailDTO.setCreateUser(this.getSessionCurrentUser().getUserName());
        adjustmentDetailDTO.setCreateTime(new Date().getTime());
        adjustmentDetailDTO.setUpdateUser(this.getSessionCurrentUser().getUserName());
        adjustmentDetailDTO.setUpdateTime(new Date().getTime());
        return getMessage(adjustmentDetailService.createAdjustmentDetail(adjustmentDetailDTO,getDbShardVO(DbShareField.IN_WH)));
    }

    @RequestMapping(value = "/{id}/detail/{detailId}",method = RequestMethod.DELETE)
    public ResponseResult removeAdjustmentDetail(@PathVariable Long id,@PathVariable Long detailId) throws  Exception{
        return getMessage(adjustmentDetailService.removeAdjustmentDetail(detailId,getDbShardVO(DbShareField.IN_WH)));
    }

    @RequestMapping(value="/{id}/detail/{detailId}",method = RequestMethod.PUT)
    public ResponseResult modifyAdjustmentDetail(@PathVariable Long id,@PathVariable Long detailId,@RequestBody TWmsAdjustmentDetailDTO adjustmentDetailDTO)throws  Exception{
        adjustmentDetailDTO.setId(detailId);
        adjustmentDetailDTO.setAdjustId(id);
        adjustmentDetailDTO.setUpdateUser(this.getSessionCurrentUser().getUserName());
        adjustmentDetailDTO.setUpdateTime(new Date().getTime());
        return getMessage(adjustmentDetailService.modifyAdjustmentDetail(adjustmentDetailDTO,getDbShardVO(DbShareField.IN_WH)));
    }


    /***
     * 调整提交
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/submit/{id}",method = RequestMethod.PUT)
    public ResponseResult submitAdjustment(@PathVariable Long id) throws  Exception{
        return getMessage(adjustmentHeaderService.updateAdjustmentHeaderStatus(id, TicketStatusCode.SUBMIT.toString(),this.getSessionCurrentUser().getUserName(),getDbShardVO(DbShareField.IN_WH)));
    }

    /***
     * 调整审核
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/reviewed/{id}",method = RequestMethod.PUT)
    public ResponseResult reviewAdjustment(@PathVariable Long id)throws  Exception{
        return getMessage(adjustmentHeaderService.updateAdjustmentHeaderStatus(id, TicketStatusCode.REVIEWED.toString(),this.getSessionCurrentUser().getUserName(),getDbShardVO(DbShareField.IN_WH)));
    }



   @RequestMapping(value = "/data/{cargoOwnerId}/{storageRoomId}",method = RequestMethod.GET)
    public ResponseResult getSkuInventoryList(@PathVariable Long cargoOwnerId,@PathVariable Long storageRoomId,@RequestParam Map map)throws  Exception{
       map.put("storageRoomId",storageRoomId);
       map.put("cargoOwnerId",cargoOwnerId);
       map.put("offset",getOffset(MapUtils.getInteger(map,"page"),MapUtils.getInteger(map,"pageSize")));
       return getSucResultData(adjustmentDetailService.getSkuInventoryList(map,getDbShardVO(DbShareField.IN_WH)));
    }


}
