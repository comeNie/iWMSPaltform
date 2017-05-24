package com.huamengtong.wms.app.controller.outwh;

import com.huamengtong.wms.core.formwork.db.util.DbShareField;
import com.huamengtong.wms.core.web.BaseController;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.ResponseResult;
import com.huamengtong.wms.dto.outwh.TWmsDnInvoiceDTO;
import com.huamengtong.wms.dto.outwh.TWmsSaleOrderDTO;
import com.huamengtong.wms.dto.outwh.TWmsShipmentDetailDTO;
import com.huamengtong.wms.dto.outwh.TWmsShipmentHeaderDTO;
import com.huamengtong.wms.em.TicketStatusCode;
import com.huamengtong.wms.enums.ResultTypeEnum;
import com.huamengtong.wms.outwh.service.IShipmentDetailService;
import com.huamengtong.wms.outwh.service.IShipmentHeaderService;
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

/**
 * Created by mario on 2016/11/1.
 */
@RestController
@RequestMapping(value = "/shipment")
public class ShipmentHeaderController extends BaseController {

    @Autowired
    IShipmentHeaderService shipmentHeaderService;

    @Autowired
    IShipmentDetailService shipmentDetailService;

    @RequestMapping(value = "",method = RequestMethod.GET)
    public ResponseResult getShipmentHeaderList(TWmsShipmentHeaderDTO shipmentHeaderDTO) throws  Exception{
        return  getSucResultData(shipmentHeaderService.getShipmentHeader(shipmentHeaderDTO,getDbShardVO(DbShareField.OUT_WH)));
    }

    @RequestMapping(value = "/{id}/detail",method = RequestMethod.GET)
    public ResponseResult queryDetailsByHeaderId(@PathVariable Long id,@RequestParam Map paramMap)throws Exception{
        paramMap.put("shipmentId",id);
        paramMap.put("offset",getOffset(MapUtils.getInteger(paramMap,"page"),MapUtils.getInteger(paramMap,"pageSize")));
        return getSucResultData(shipmentDetailService.queryShipmentDetailByHeader(paramMap,getDbShardVO(DbShareField.OUT_WH)));
    }

    /***
     * 查询出库单明细基础信息
     * @param id
     * @param paramMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{id}/detail/basic",method = RequestMethod.GET)
    public ResponseResult queryShipmentBasicByHeaderId(@PathVariable Long id,@RequestParam Map paramMap)throws Exception{
        paramMap.put("shipmentId",id);
        return getSucResultData(shipmentHeaderService.queryShipmentBasics(paramMap,getDbShardVO(DbShareField.OUT_WH)));
    }


    /***
     * 查询出库单分配结果
     * @param id
     * @param paramMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{id}/allocateResult",method = RequestMethod.GET)
    public ResponseResult queryAllocateResult(@PathVariable Long id,@RequestParam Map paramMap)throws Exception{
        paramMap.put("headerId",id);
        paramMap.put("offset",getOffset(MapUtils.getInteger(paramMap,"page"),MapUtils.getInteger(paramMap,"pageSize")));
        return getSucResultData(shipmentHeaderService.queryAllocateResult(paramMap,getDbShardVO(DbShareField.OUT_WH)));
    }

    @RequestMapping(value = "",method = RequestMethod.POST)
    public ResponseResult createShipmentHeader(@RequestBody TWmsShipmentHeaderDTO shipmentHeaderDTO) throws  Exception{
        Long dateTime = new Date().getTime();
        String userName = this.getSessionCurrentUser().getUserName();

        shipmentHeaderDTO.setWarehouseId(getCurrentWarehouseId());
        shipmentHeaderDTO.setTenantId(getCurrentTenantId());
        shipmentHeaderDTO.setCreateUser(userName);
        shipmentHeaderDTO.setCreateTime(dateTime);
        shipmentHeaderDTO.setUpdateUser(userName);
        shipmentHeaderDTO.setUpdateTime(dateTime);

        //订单信息
        TWmsSaleOrderDTO order = shipmentHeaderDTO.getOrder();
        order.setTenantId(getCurrentTenantId());
        order.setCreateTime(dateTime);
        order.setCreateUser(userName);
        order.setUpdateTime(dateTime);
        order.setUpdateUser(userName);
        shipmentHeaderDTO.setOrder(order);

        //发票信息
        TWmsDnInvoiceDTO invoice = shipmentHeaderDTO.getInvoice();
        invoice.setTenantId(getCurrentTenantId());
        invoice.setCreateTime(dateTime);
        invoice.setCreateUser(userName);
        invoice.setUpdateTime(dateTime);
        invoice.setUpdateUser(userName);
        shipmentHeaderDTO.setInvoice(invoice);

        return  getMessage(shipmentHeaderService.createShipmentHeader(shipmentHeaderDTO,getDbShardVO(DbShareField.OUT_WH)));
    }

    @RequestMapping(value = "/{id}" ,method = RequestMethod.DELETE)
    public ResponseResult removeByshipmentHeaderId(@PathVariable Long id) throws  Exception{
        return getMessage(shipmentHeaderService.removeShipmentHeader(id,getDbShardVO(DbShareField.OUT_WH)));
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public ResponseResult modifyShipmentHeader(@RequestBody TWmsShipmentHeaderDTO shipmentHeaderDTO) throws Exception{
        shipmentHeaderDTO.setWarehouseId(getCurrentWarehouseId());
        shipmentHeaderDTO.setUpdateUser(this.getSessionCurrentUser().getUserName());
        shipmentHeaderDTO.setUpdateTime(new Date().getTime());
        return   getMessage(shipmentHeaderService.modifyShipmentHeader(shipmentHeaderDTO,getDbShardVO(DbShareField.OUT_WH)));
    }

    @RequestMapping(value = "/{id}/detail",method = RequestMethod.POST)
    public ResponseResult createDetail(@PathVariable Long id, @RequestBody TWmsShipmentDetailDTO shipmentDetailDTO)throws Exception{
        Long dateTime = new Date().getTime();
        String userName = this.getSessionCurrentUser().getUserName();
        shipmentDetailDTO.setShipmentId(id);
        shipmentDetailDTO.setTenantId(getCurrentTenantId());
        shipmentDetailDTO.setWarehouseId(this.getCurrentWarehouseId());
        shipmentDetailDTO.setCreateUser(userName);
        shipmentDetailDTO.setCreateTime(dateTime);
        shipmentDetailDTO.setUpdateUser(userName);
        shipmentDetailDTO.setUpdateTime(dateTime);
        return getMessage(shipmentDetailService.createShipmentDetail(shipmentDetailDTO,getDbShardVO(DbShareField.OUT_WH)));

    }

    @RequestMapping(value = "/{id}/detail/{detailId}",method = RequestMethod.DELETE)
    public ResponseResult removeDetailById(@PathVariable Long id,@PathVariable Long detailId) throws Exception{
        return  getMessage(shipmentDetailService.removeShipmentDetail(detailId,getDbShardVO(DbShareField.OUT_WH)));
    }

    @RequestMapping(value = "/{id}/detail/{detailId}",method = RequestMethod.PUT)
    public ResponseResult modifyDetail(@PathVariable Long id,@PathVariable Long detailId,@RequestBody TWmsShipmentDetailDTO shipmentDetailDTO){
        shipmentDetailDTO.setId(detailId);
        shipmentDetailDTO.setShipmentId(id);
        shipmentDetailDTO.setUpdateUser(this.getSessionCurrentUser().getUserName());
        shipmentDetailDTO.setUpdateTime(new Date().getTime());
        return getMessage(shipmentDetailService.updateShipmentDetail(shipmentDetailDTO,getDbShardVO(DbShareField.OUT_WH)));
    }

    /***
     * 更改出货库房
     * @param map
     * @return
     */
    @RequestMapping(value = "/storageRoom",method = RequestMethod.PUT)
    public ResponseResult modifyDetail(@RequestBody Map map){
        TWmsShipmentDetailDTO shipmentDetailDTO = new TWmsShipmentDetailDTO();
        shipmentDetailDTO.setId(Long.parseLong(map.get("id").toString()));
        if(map.containsKey("storageRoomId")){
            if(map.get("storageRoomId") != null){
                shipmentDetailDTO.setStorageRoomId(map.get("storageRoomId").toString());
            }else{
                shipmentDetailDTO.setStorageRoomId(null);
            }
        }
        shipmentDetailDTO.setShipmentId(Long.parseLong(map.get("shipmentId").toString()));
        shipmentDetailDTO.setUpdateUser(this.getSessionCurrentUser().getUserName());
        shipmentDetailDTO.setUpdateTime(new Date().getTime());
        return getMessage(shipmentDetailService.updateShipmentDetail(shipmentDetailDTO,getDbShardVO(DbShareField.OUT_WH)));
    }

    /***
     * 出库单批量删除
     * @param ids
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/batch/delete/{ids}",method = RequestMethod.DELETE)
    public ResponseResult batchDeleteShipmenttHeaders(@PathVariable String ids) throws  Exception{
        Long[] ShipmentIds = ArrayUtil.toLongArray(ids.split(","));
                return getMessage(shipmentHeaderService.batchDeleteShipmentHeaders(ShipmentIds,this.getSessionCurrentUser().getUserName(),getDbShardVO(DbShareField.OUT_WH)));
    }
    /***
     * 出库单提交
     * @param id
     * @return
     * @throws Exception
     */

    @RequestMapping(value = "/submit/{id}",method = RequestMethod.PUT)
    public ResponseResult shipmentSubmit(@PathVariable Long id)throws Exception{
        //Long[] ShipmentIds = ArrayUtil.toLongArray(ids.split(","));
        return getMessage(shipmentHeaderService.updateShipmentHeaderStatus(id, TicketStatusCode.SUBMIT.toString(),this.getSessionCurrentUser().getUserName(),getDbShardVO(DbShareField.OUT_WH)));
    }


    /***
     * 出库单撤销
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/repealed/{id}",method = RequestMethod.PUT)
    public ResponseResult updateRepealedShipment(@PathVariable Long id)throws Exception{
        //Long[] ShipmentIds = ArrayUtil.toLongArray(ids.split(","));
        return getMessage(shipmentHeaderService.updateShipmentHeaderStatus(id,TicketStatusCode.REPEALED.toString(),this.getSessionCurrentUser().getUserName(),getDbShardVO(DbShareField.OUT_WH)));
    }

    /***
     * 出库单打印
     * @param ids
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/printShipment/{ids}",method = RequestMethod.GET)
    public ResponseResult batchShipmenttHeaders(@PathVariable String ids) throws  Exception{
        Long[] ShipmentIds = ArrayUtil.toLongArray(ids.split(","));
        List list = new ArrayList();
        for(int i = 0 ; i < ShipmentIds.length ; i ++){
            list.add(shipmentHeaderService.findPrintEntityPrintShipment(ShipmentIds[i],getDbShardVO(DbShareField.OUT_WH)));
        }
        Map map = new HashMap();
        map.put("rows",list);
        return getSucResultData(map);
    }

    /***
     * 出仓交接
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/delivery",method = RequestMethod.PUT)
    public ResponseResult updateShipmentDelivery(@RequestBody Map map) throws  Exception{
        map.put("userName",this.getSessionCurrentUser().getUserName());
        MessageResult mr = shipmentHeaderService.updateShipmentHeaderDelivery(map,getDbShardVO(DbShareField.OUT_WH));
        if(!mr.isSuc()){
            return getMessage(mr.getMessage(),new Object[]{ map.get("shipmentId").toString() });
        }
        return getMessage(mr).setResultType(ResultTypeEnum.DATA);
    }

    /**
     * 打印拣货单
      * @param ids
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/printPicking/{ids}",method = RequestMethod.GET)
    public ResponseResult batchPrintPicking(@PathVariable String ids) throws  Exception{
        Long[] ShipmentIds = ArrayUtil.toLongArray(ids.split(","));
        List list = new ArrayList();
        for(int i = 0 ; i < ShipmentIds.length ; i ++){
            list.add(shipmentHeaderService.findPrintEntityPrintPicking(ShipmentIds[i],getDbShardVO(DbShareField.OUT_WH)));
        }
        Map map = new HashMap();
        map.put("rows",list);
        return getSucResultData(map);
    }

    /**
     * 查看可以生成波次的出库单
     * @param shipmentHeaderDTO 查询条件
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/wave",method = RequestMethod.GET)
    public ResponseResult getShipmentHeaderListForWave(TWmsShipmentHeaderDTO shipmentHeaderDTO) throws  Exception{
        shipmentHeaderDTO.setDeliveryStatuscode("None");
        shipmentHeaderDTO.setAllocateStatuscode("Finished");
        shipmentHeaderDTO.setWaveSeq("wave");//根据waveSeq=wave筛选出没有波次的出库单
        return  getSucResultData(shipmentHeaderService.getShipmentHeader(shipmentHeaderDTO,getDbShardVO(DbShareField.OUT_WH)));
    }

    /**
     *出库单生成波次
     * @param ids 要生成波次的出库单ids
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/wave/submit/{ids}",method = RequestMethod.PUT)
    public ResponseResult shipmentWaveSubmit(@PathVariable String ids)throws Exception{
        Long[] shipmentIds = ArrayUtil.toLongArray(ids.split(","));
        return getMessage(shipmentHeaderService.updateShipmentHeaderToWave(shipmentIds,getCurrentTenantId(),getCurrentWarehouseId(),
                this.getSessionCurrentUser().getUserName(),
                getDbShardVO(DbShareField.OUT_WH)));
    }

}
