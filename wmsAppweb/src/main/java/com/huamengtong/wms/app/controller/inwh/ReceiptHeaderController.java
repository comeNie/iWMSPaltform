package com.huamengtong.wms.app.controller.inwh;

import com.huamengtong.wms.core.formwork.db.util.DbShareField;
import com.huamengtong.wms.core.web.BaseController;
import com.huamengtong.wms.core.web.ResponseResult;
import com.huamengtong.wms.domain.TWmsReceiptHeaderPrintEntity;
import com.huamengtong.wms.dto.inwh.TWmsReceiptDetailDTO;
import com.huamengtong.wms.dto.inwh.TWmsReceiptHeaderDTO;;
import com.huamengtong.wms.inwh.service.IReceiptDetailService;
import com.huamengtong.wms.inwh.service.IReceiptHeaderService;
import com.huamengtong.wms.utils.ArrayUtil;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequestMapping("/receipt")
@RestController
public class ReceiptHeaderController extends BaseController{

    @Autowired
    private IReceiptHeaderService receiptHeaderService;

    @Autowired
    private IReceiptDetailService receiptDetailService;


    @RequestMapping(value = "",method = RequestMethod.GET)
    public ResponseResult getHeaderList(TWmsReceiptHeaderDTO receiptHeaderDTO)throws Exception{
        return getSucResultData(receiptHeaderService.getReceiptHeader(receiptHeaderDTO,getDbShardVO(DbShareField.IN_WH)));
    }

    @RequestMapping(value = "/{id}/detail",method = RequestMethod.GET)
    public ResponseResult queryDetailsByHeaderId(@PathVariable Long id,@RequestParam Map paramMap)throws Exception{
        paramMap.put("receiptId",id);
        paramMap.put("offset",getOffset(MapUtils.getInteger(paramMap,"page"),MapUtils.getInteger(paramMap,"pageSize")));
        return getSucResultData(receiptDetailService.queryReceiptDetailByHeader(paramMap,getDbShardVO(DbShareField.IN_WH)));
    }

    @RequestMapping(value = "",method = RequestMethod.POST)
    public ResponseResult createHeader(@RequestBody TWmsReceiptHeaderDTO receiptHeaderDTO)throws Exception{
        receiptHeaderDTO.setWarehouseId(getCurrentWarehouseId());
        receiptHeaderDTO.setTenantId(getCurrentTenantId());
        receiptHeaderDTO.setCreateUser(this.getSessionCurrentUser().getUserName());
        receiptHeaderDTO.setCreateTime(new Date().getTime());
        receiptHeaderDTO.setUpdateUser(this.getSessionCurrentUser().getUserName());
        receiptHeaderDTO.setUpdateTime(new Date().getTime());
        return getMessage(receiptHeaderService.createReceiptHeader(receiptHeaderDTO,getDbShardVO(DbShareField.IN_WH)));
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public ResponseResult deleteHeaderById(@PathVariable Long id)throws Exception{
        return getMessage(receiptHeaderService.removeReceiptHeader(id,getDbShardVO(DbShareField.IN_WH)));
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public ResponseResult modifyHeader(@RequestBody TWmsReceiptHeaderDTO receiptHeaderDTO)throws Exception{
        receiptHeaderDTO.setWarehouseId(getCurrentWarehouseId());
        receiptHeaderDTO.setUpdateUser(this.getSessionCurrentUser().getUserName());
        receiptHeaderDTO.setUpdateTime(new Date().getTime());
        return getMessage(receiptHeaderService.modifyReceiptHeader(receiptHeaderDTO,getDbShardVO(DbShareField.IN_WH)));
    }

    @RequestMapping(value = "/{id}/detail",method = RequestMethod.POST)
    public ResponseResult createDetail(@PathVariable Long id, @RequestBody TWmsReceiptDetailDTO receiptDetailDTO)throws Exception{
        receiptDetailDTO.setReceiptId(id);
        receiptDetailDTO.setTenantId(getCurrentTenantId());
        receiptDetailDTO.setWarehouseId(getCurrentWarehouseId());
        receiptDetailDTO.setReceiptTime(new Date().getTime());
        receiptDetailDTO.setCreateUser(this.getSessionCurrentUser().getUserName());
        receiptDetailDTO.setCreateTime(new Date().getTime());
        receiptDetailDTO.setUpdateUser(this.getSessionCurrentUser().getUserName());
        receiptDetailDTO.setUpdateTime(new Date().getTime());
        return getMessage(receiptDetailService.createReceiptDetail(receiptDetailDTO,getDbShardVO(DbShareField.IN_WH)));
    }

    @RequestMapping(value = "/{id}/detail/{detailId}",method = RequestMethod.DELETE)
    public ResponseResult deleteDetailById(@PathVariable Long id,@PathVariable Long detailId)throws Exception{
        return getMessage(receiptDetailService.removeByPrimaryKey(detailId,this.getSessionCurrentUser().getUserName(),getDbShardVO(DbShareField.IN_WH)));
    }

    @RequestMapping(value = "/{id}/detail/{detailId}",method = RequestMethod.PUT)
    public ResponseResult modifyDetail(@PathVariable Long id,@PathVariable Long detailId,@RequestBody TWmsReceiptDetailDTO receiptDetailDTO){
        receiptDetailDTO.setId(detailId);
        receiptDetailDTO.setReceiptId(id);
        receiptDetailDTO.setUpdateUser(this.getSessionCurrentUser().getUserName());
        receiptDetailDTO.setUpdateTime(new Date().getTime());
        return getMessage(receiptDetailService.modifyReceiptDetail(receiptDetailDTO,getDbShardVO(DbShareField.IN_WH)));
    }

    /***
     * 入库单打印
     * @param ids
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/print/{ids}",method = RequestMethod.GET)
    public ResponseResult printReceiptHeaders(@PathVariable String ids)throws Exception{
        Long[] receiptIds = ArrayUtil.toLongArray(ids.split(","));
        List list = new ArrayList();
        for(int i = 0; i < receiptIds.length; i++){
            TWmsReceiptHeaderPrintEntity receiptHeaderPrintEntity= receiptHeaderService.findPrintEntityByPrimaryKey(receiptIds[i],getDbShardVO(DbShareField.IN_WH));
            list.add(receiptHeaderPrintEntity);
        }
        Map resultMap = new HashMap();
        resultMap.put("rows", list);
        return getSucResultData(resultMap);
    }


    /**
     * 入库确认
     * @param ids
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/confirm/{ids}",method = RequestMethod.GET)
    public ResponseResult receiptConfirm(@PathVariable String ids)throws Exception{
        Long[] idsArr = ArrayUtil.toLongArray(ids.split(","));
        return  getMessage(receiptHeaderService.updateConfirmReceipt(idsArr,this.getSessionCurrentUser().getUserName(),getDbShardVO(DbShareField.IN_WH)));
    }
    /**
     * 入库单生成质检单
     * @param ids
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/trans/{ids}",method = RequestMethod.GET)
    public ResponseResult receiptTrans(@PathVariable String ids)throws Exception{
        Long[] idsArr = ArrayUtil.toLongArray(ids.split(","));
        return  getMessage(receiptHeaderService.updateTransReceipt(idsArr,this.getSessionCurrentUser().getUserName(),getDbShardVO(DbShareField.IN_WH)));
    }


}
