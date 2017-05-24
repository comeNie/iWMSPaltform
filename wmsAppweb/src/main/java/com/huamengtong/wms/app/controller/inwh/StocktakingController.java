package com.huamengtong.wms.app.controller.inwh;

import com.huamengtong.wms.core.formwork.db.util.DbShareField;
import com.huamengtong.wms.core.web.BaseController;
import com.huamengtong.wms.core.web.ResponseResult;
import com.huamengtong.wms.dto.inwh.TWmsStocktakingDetailDTO;
import com.huamengtong.wms.dto.inwh.TWmsStocktakingHeaderDTO;
import com.huamengtong.wms.inwh.service.IStocktakingDetailService;
import com.huamengtong.wms.inwh.service.IStocktakingHeaderService;
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
 * Created by mario on 2016/11/22.
 */
@RestController
@RequestMapping("/inventory/stocktaking")
public class StocktakingController extends BaseController {

    @Autowired
    IStocktakingHeaderService stocktakingHeaderService;

    @Autowired
    IStocktakingDetailService stocktakingDetailService;


    @RequestMapping(value = "",method = RequestMethod.GET)
    public ResponseResult getStocktakingHeaderList(TWmsStocktakingHeaderDTO stocktakingHeaderDTO) throws  Exception{
        return  getSucResultData(stocktakingHeaderService.getStocktakingHeader(stocktakingHeaderDTO,getDbShardVO(DbShareField.IN_WH)));

    }

    @RequestMapping(value = "/{id}/detail",method = RequestMethod.GET)
    public ResponseResult queryDetailByHeaderId(@PathVariable Long id, @RequestParam Map paramMap)throws Exception{
        paramMap.put("headerId",id);
        paramMap.put("offset",getOffset(MapUtils.getInteger(paramMap,"page"),MapUtils.getInteger(paramMap,"pageSize")));
        return getSucResultData(stocktakingDetailService.queryStocktakingDetailByHeader(paramMap,getDbShardVO(DbShareField.IN_WH)));
    }


    @RequestMapping(value = "",method = RequestMethod.POST)
    public ResponseResult createStocktakingHeader(@RequestBody TWmsStocktakingHeaderDTO stocktakingHeaderDTO)throws  Exception{
        stocktakingHeaderDTO.setTenantId(getCurrentTenantId());
        stocktakingHeaderDTO.setWarehouseId(getCurrentWarehouseId());
        stocktakingHeaderDTO.setCreateUser(this.getSessionCurrentUser().getUserName());
        stocktakingHeaderDTO.setCreateTime(new Date().getTime());
        stocktakingHeaderDTO.setUpdateUser(this.getSessionCurrentUser().getUserName());
        stocktakingHeaderDTO.setUpdateTime(new Date().getTime());
        return  getMessage(stocktakingHeaderService.createStocktakingHeader(stocktakingHeaderDTO,getDbShardVO(DbShareField.IN_WH)));
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public ResponseResult removeStocktakingHeader(@PathVariable Long id)throws  Exception{
        return getMessage(stocktakingHeaderService.removeStocktakingHeader(id,getDbShardVO(DbShareField.IN_WH)));
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public ResponseResult modifyStocktakingHeader(@RequestBody TWmsStocktakingHeaderDTO stocktakingHeaderDTO)throws  Exception{
        stocktakingHeaderDTO.setUpdateUser(this.getSessionCurrentUser().getUserName());
        stocktakingHeaderDTO.setUpdateTime(new Date().getTime());
        return  getMessage(stocktakingHeaderService.modifyStocktakingHeader(stocktakingHeaderDTO,getDbShardVO(DbShareField.IN_WH)));
    }

    @RequestMapping(value = "/{id}/detail",method = RequestMethod.POST)
    public ResponseResult createStocktakingDetail(@PathVariable Long id, @RequestBody TWmsStocktakingDetailDTO stocktakingDetailDTO)throws  Exception{
        stocktakingDetailDTO.setHeaderId(id);
        stocktakingDetailDTO.setTenantId(getCurrentTenantId());
        stocktakingDetailDTO.setCreateUser(this.getSessionCurrentUser().getUserName());
        stocktakingDetailDTO.setCreateTime(new Date().getTime());
        stocktakingDetailDTO.setUpdateUser(this.getSessionCurrentUser().getUserName());
        stocktakingDetailDTO.setUpdateTime(new Date().getTime());
        return getMessage(stocktakingDetailService.createStocktakingDetailDetail(stocktakingDetailDTO,getDbShardVO(DbShareField.IN_WH)));
    }

    @RequestMapping(value = "/{id}/detail/{detailId}",method = RequestMethod.DELETE)
    public ResponseResult removeStocktakingDetail(@PathVariable Long id,@PathVariable Long detailId) throws Exception{
        return getMessage(stocktakingDetailService.removeStocktakingDetail(detailId,getDbShardVO(DbShareField.IN_WH)));
    }

    @RequestMapping(value = "/{id}/detail/{detailId}",method = RequestMethod.PUT)
    public ResponseResult modifyStocktakingDetail(@PathVariable Long id,@PathVariable Long detailId,@RequestBody TWmsStocktakingDetailDTO stocktakingDetailDTO)throws Exception{
        stocktakingDetailDTO.setId(detailId);
        stocktakingDetailDTO.setHeaderId(id);
        stocktakingDetailDTO.setUpdateUser(this.getSessionCurrentUser().getUserName());
        stocktakingDetailDTO.setUpdateTime(new Date().getTime());
        return getMessage(stocktakingDetailService.modifyStocktakingDetail(stocktakingDetailDTO,getDbShardVO(DbShareField.IN_WH)));
    }

    /**
     * 盘点提交
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "submit/{id}",method = RequestMethod.PUT)
    public ResponseResult stocktakingSubmit(@PathVariable String id)throws Exception{
        Long headerId=Long.parseLong(id);
        return getMessage(stocktakingHeaderService.updateSubmitStocktaking(headerId,this.getSessionCurrentUser().getUserName(),getDbShardVO(DbShareField.IN_WH)));
    }

    /**
     * 盘点确认
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "confirm/{id}",method = RequestMethod.PUT)
    public ResponseResult stocktakingConfirm(@PathVariable String id)throws Exception{
        Long headerId=Long.parseLong(id);
        return getMessage(stocktakingHeaderService.updateConfirmStocktaking(headerId,this.getSessionCurrentUser().getUserName(),getDbShardVO(DbShareField.IN_WH)));
    }


    /**
     * 盘点弹窗
     * @param id
     * @param paramMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "detail/submit/{id}",method = RequestMethod.PUT)
    public ResponseResult submitStocktakingDetails(@PathVariable String id,@RequestBody Map paramMap)throws Exception{
        Long stocktakingDetailId = MapUtils.getLong(paramMap,"stocktakingDetailId");
        Integer countQty = MapUtils.getInteger(paramMap,"countQty");
        return getMessage(stocktakingDetailService.modifySubmitStocktakingDetail(stocktakingDetailId,countQty,getSessionCurrentUser().getUserName(),getDbShardVO(DbShareField.IN_WH)));
    }


}
