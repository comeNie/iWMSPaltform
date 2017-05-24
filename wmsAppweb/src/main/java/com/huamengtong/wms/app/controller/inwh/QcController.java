package com.huamengtong.wms.app.controller.inwh;

import com.huamengtong.wms.core.formwork.db.util.DbShareField;
import com.huamengtong.wms.core.web.BaseController;
import com.huamengtong.wms.core.web.ResponseResult;
import com.huamengtong.wms.dto.inwh.TWmsQcDetailDTO;
import com.huamengtong.wms.dto.inwh.TWmsQcHeaderDTO;
import com.huamengtong.wms.em.InventoryStatusCode;
import com.huamengtong.wms.inwh.service.IQcDetailService;
import com.huamengtong.wms.inwh.service.IQcHeaderService;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


@RestController
@RequestMapping(value = "/qc")
public class QcController extends BaseController {

    @Autowired
    IQcHeaderService qcHeaderService;

    @Autowired
    IQcDetailService qcDetailService;


    @RequestMapping(value = "",method = RequestMethod.GET)
    public ResponseResult getQcHeaderList(TWmsQcHeaderDTO qcHeaderDTO)throws Exception{
        return getSucResultData(qcHeaderService.getQcHeaders(qcHeaderDTO,getDbShardVO(DbShareField.IN_WH)));
    }

    @RequestMapping(value = "/{headerId}/detail",method = RequestMethod.GET)
    public ResponseResult getQcDetailsByHeaderId(@PathVariable Long headerId, @RequestParam Map map)throws Exception{
        map.put("id",headerId);
        map.put("offset",getOffset(MapUtils.getInteger(map,"page"),MapUtils.getInteger(map,"pageSize")));
        return getSucResultData(qcDetailService.queryDetailsByHeaderId(map,getDbShardVO(DbShareField.IN_WH)));
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public ResponseResult removeQcHeader(@PathVariable Long id)throws Exception{
        return getMessage(qcHeaderService.removeByPrimaryKey(id,getDbShardVO(DbShareField.IN_WH)));
    }

    @RequestMapping(value = "",method = RequestMethod.POST)
    public ResponseResult createQcHeader(@RequestBody TWmsQcHeaderDTO qcHeaderDTO)throws Exception{
        qcHeaderDTO.setWarehouseId(this.getCurrentWarehouseId());
        qcHeaderDTO.setTenantId(this.getCurrentTenantId());
        qcHeaderDTO.setCreateUser(this.getSessionCurrentUser().getUserName());
        qcHeaderDTO.setCreateTime(new Date().getTime());
        qcHeaderDTO.setUpdateUser(this.getSessionCurrentUser().getUserName());
        qcHeaderDTO.setUpdateTime(new Date().getTime());
        return getMessage(qcHeaderService.insertQcHeader(qcHeaderDTO,getDbShardVO(DbShareField.IN_WH)));
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public ResponseResult modifyQcHeader(@PathVariable Long id,@RequestBody TWmsQcHeaderDTO qcHeaderDTO)throws Exception{
        qcHeaderDTO.setWarehouseId(this.getCurrentWarehouseId());
        qcHeaderDTO.setTenantId(this.getCurrentTenantId());
        qcHeaderDTO.setId(id);
        qcHeaderDTO.setUpdateUser(this.getSessionCurrentUser().getUserName());
        qcHeaderDTO.setUpdateTime(new Date().getTime());
        return getMessage(qcHeaderService.modifyQcHeader(qcHeaderDTO,getDbShardVO(DbShareField.IN_WH)));
    }


    @RequestMapping(value = "/{headerId}/detail",method = RequestMethod.POST)
    public ResponseResult createQcDetail(@PathVariable Long headerId,@RequestBody TWmsQcDetailDTO qcDetailDTO)throws Exception{
        qcDetailDTO.setQcId(headerId);
        qcDetailDTO.setTenantId(this.getCurrentTenantId());
        qcDetailDTO.setWarehouseId(this.getCurrentWarehouseId());
        qcDetailDTO.setCreateUser(this.getSessionCurrentUser().getUserName());
        qcDetailDTO.setCreateTime(new Date().getTime());
        qcDetailDTO.setUpdateUser(this.getSessionCurrentUser().getUserName());
        qcDetailDTO.setUpdateTime(new Date().getTime());
        return getMessage(qcDetailService.createQcDetail(qcDetailDTO,getDbShardVO(DbShareField.IN_WH)));
    }

    @RequestMapping(value = "/{headerId}/detail/{id}",method = RequestMethod.DELETE)
    public ResponseResult removeQcDetail(@PathVariable Long headerId,@PathVariable Long id)throws Exception{
        return getMessage(qcDetailService.removeByPrimaryKey(id,this.getSessionCurrentUser().getUserName(),getDbShardVO(DbShareField.IN_WH)));
    }

    @RequestMapping(value = "/{headerId}/detail/{id}",method = RequestMethod.PUT)
    public ResponseResult modifyQcDetail(@PathVariable Long headerId,@PathVariable Long id,@RequestBody TWmsQcDetailDTO qcDetailDTO)throws Exception{
        qcDetailDTO.setId(id);
        qcDetailDTO.setQcId(headerId);
        qcDetailDTO.setUpdateUser(this.getSessionCurrentUser().getUserName());
        qcDetailDTO.setUpdateTime(new Date().getTime());
        return getMessage(qcDetailService.updateQcDetail(qcDetailDTO,getDbShardVO(DbShareField.IN_WH)));
    }

    /**
     * 质检单提交操作
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/submit/{id}",method = RequestMethod.PUT)
    public ResponseResult submitQc(@PathVariable String id)throws Exception{
        Long headerId = Long.parseLong(id);
        return getMessage(qcHeaderService.updateSubmitQc(headerId,getSessionCurrentUser().getUserName(),getDbShardVO(DbShareField.IN_WH)));
    }

    /**
     * 质检单撤销操作
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/repealed/{id}",method = RequestMethod.PUT)
    public ResponseResult repealedQc(@PathVariable String id)throws Exception{
        Long headerId=Long.parseLong(id);
        return getMessage(qcHeaderService.updateRepealedQc(headerId,getSessionCurrentUser().getUserName(),getDbShardVO(DbShareField.IN_WH)));

    }

    /**
     * 质检单质检完成操作
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/finish/{id}",method = RequestMethod.PUT)
    public ResponseResult finishQc(@PathVariable String id)throws Exception{
        Long headerId= Long.parseLong(id);
        return getMessage(qcHeaderService.updateFinishQc(headerId,getSessionCurrentUser().getUserName(),getDbShardVO(DbShareField.IN_WH)));
    }

    /**
     * 对质检明细单进行质检收货
     * 传参： 质检明细表ID，商品条码，操作用户，检收数量，存储库房，库房温度
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/qcReceive/{qcId}",method = RequestMethod.PUT)
    public ResponseResult qcReceiveFinish(@PathVariable Long qcId,@RequestBody Map map)throws Exception{
        String barcode = MapUtils.getString(map,"barcode");
        Integer qcQty = MapUtils.getInteger(map,"qcQty");
        String storageRoomId = MapUtils.getString(map,"storageRoomId");
        BigDecimal warehouseTemp = new BigDecimal(MapUtils.getString(map,"warehouseTemp")) ;
        String operationUser = this.getSessionCurrentUser().getUserName();
        return getMessage(qcHeaderService.createReceipt(qcId,barcode,operationUser,qcQty, InventoryStatusCode.GOOD.toString(),storageRoomId,warehouseTemp,getDbShardVO(DbShareField.IN_WH)));
    }


}
