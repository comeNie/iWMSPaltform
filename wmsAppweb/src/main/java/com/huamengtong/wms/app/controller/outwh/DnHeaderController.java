package com.huamengtong.wms.app.controller.outwh;

import com.huamengtong.wms.core.formwork.db.util.DbShareField;
import com.huamengtong.wms.core.web.BaseController;
import com.huamengtong.wms.core.web.ResponseResult;
import com.huamengtong.wms.dto.outwh.TWmsDnDetailDTO;
import com.huamengtong.wms.dto.outwh.TWmsDnHeaderDTO;
import com.huamengtong.wms.dto.outwh.TWmsDnInvoiceDTO;
import com.huamengtong.wms.dto.outwh.TWmsSaleOrderDTO;
import com.huamengtong.wms.outwh.service.IDnDetailService;
import com.huamengtong.wms.outwh.service.IDnHeaderService;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * Created by mario on 2016/11/4.
 */
@RestController
@RequestMapping(value = "/dn/header")
public class DnHeaderController extends BaseController {

    @Autowired
    IDnHeaderService dnHeaderService;

    @Autowired
    IDnDetailService dnDetailService;


    @RequestMapping(value = "",method = RequestMethod.GET)
    public ResponseResult getDnHeaderList(TWmsDnHeaderDTO dnHeaderDTO) throws  Exception{
        return getSucResultData(dnHeaderService.getDnHeader(dnHeaderDTO,getDbShardVO(DbShareField.OUT_WH)));
    }

    @RequestMapping(value = "/{id}/detail",method = RequestMethod.GET)
    public ResponseResult queryDetailsByHeaderId(@PathVariable Long id,@RequestParam Map paramMap)throws Exception{
        paramMap.put("dnId",id);
        paramMap.put("offset",getOffset(MapUtils.getInteger(paramMap,"page"),MapUtils.getInteger(paramMap,"pageSize")));
        return getSucResultData(dnDetailService.queryDnDetailByHeader(paramMap,getDbShardVO(DbShareField.OUT_WH)));
    }

    /**
     * 创建新的出库通知单
     * @param dnHeaderDTO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "",method = RequestMethod.POST)
    public ResponseResult createDnHeader(@RequestBody TWmsDnHeaderDTO dnHeaderDTO) throws  Exception{
        Long dateTime = new Date().getTime();
        String userName = this.getSessionCurrentUser().getUserName();

        dnHeaderDTO.setWarehouseId(getCurrentWarehouseId());
        dnHeaderDTO.setTenantId(getCurrentTenantId());
        dnHeaderDTO.setCreateUser(userName);
        dnHeaderDTO.setCreateTime(dateTime);
        dnHeaderDTO.setUpdateUser(userName);
        dnHeaderDTO.setUpdateTime(dateTime);

        //订单信息
        TWmsSaleOrderDTO order = dnHeaderDTO.getOrder();
        order.setTenantId(getCurrentTenantId());
        order.setCreateTime(dateTime);
        order.setCreateUser(userName);
        order.setUpdateTime(dateTime);
        order.setUpdateUser(userName);
        dnHeaderDTO.setOrder(order);

        //发票信息
        TWmsDnInvoiceDTO invoice = dnHeaderDTO.getInvoice();
        invoice.setTenantId(getCurrentTenantId());
        invoice.setCreateUser(userName);
        invoice.setCreateTime(dateTime);
        invoice.setUpdateTime(dateTime);
        invoice.setUpdateUser(userName);
        dnHeaderDTO.setInvoice(invoice);

        return  getMessage(dnHeaderService.createDnHeader(dnHeaderDTO,getDbShardVO(DbShareField.OUT_WH)));
    }

    @RequestMapping(value = "/{id}" ,method = RequestMethod.DELETE)
    public ResponseResult removeByDnHeaderId(@PathVariable Long id) throws  Exception{
        return getMessage(dnHeaderService.removeDnHeader(id,getDbShardVO(DbShareField.OUT_WH)));
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public ResponseResult modifyshipmentHeader(@RequestBody TWmsDnHeaderDTO dnHeaderDTO) throws Exception{
        dnHeaderDTO.setWarehouseId(getCurrentWarehouseId());
        dnHeaderDTO.setUpdateUser(this.getSessionCurrentUser().getUserName());
        dnHeaderDTO.setUpdateTime(new Date().getTime());
        return   getMessage(dnHeaderService.modifyDnHeader(dnHeaderDTO,getDbShardVO(DbShareField.OUT_WH)));
    }

    @RequestMapping(value = "/{id}/detail",method = RequestMethod.POST)
    public ResponseResult createDetail(@PathVariable Long id, @RequestBody TWmsDnDetailDTO dnDetailDTO)throws Exception{
        dnDetailDTO.setDnId(id);
        if(dnDetailDTO.getStorageRoomIds() != null && dnDetailDTO.getStorageRoomIds().length > 0){
            String storageRoomIds = Arrays.toString(dnDetailDTO.getStorageRoomIds());
            dnDetailDTO.setStorageRoomId(storageRoomIds.substring(1,storageRoomIds.length()-1).replaceAll(" ",""));
        }
        dnDetailDTO.setTenantId(getCurrentTenantId());
        dnDetailDTO.setCreateUser(this.getSessionCurrentUser().getUserName());
        dnDetailDTO.setCreateTime(new Date().getTime());
        dnDetailDTO.setUpdateUser(this.getSessionCurrentUser().getUserName());
        dnDetailDTO.setUpdateTime(new Date().getTime());
        return getMessage(dnDetailService.createDnDetail(dnDetailDTO,getDbShardVO(DbShareField.OUT_WH)));
    }

    @RequestMapping(value = "/{id}/detail/{detailId}",method = RequestMethod.DELETE)
    public ResponseResult deleteDetailById(@PathVariable Long id,@PathVariable Long detailId)throws Exception{
        return getMessage(dnDetailService.removeByPrimaryKey(detailId,getDbShardVO(DbShareField.OUT_WH)));
    }

    @RequestMapping(value = "/{id}/detail/{detailId}",method = RequestMethod.PUT)
    public ResponseResult modifyDetail(@PathVariable Long id,@PathVariable Long detailId,@RequestBody TWmsDnDetailDTO dnDetailDTO){
        dnDetailDTO.setId(detailId);
        dnDetailDTO.setDnId(id);
        if(dnDetailDTO.getStorageRoomIds() != null && dnDetailDTO.getStorageRoomIds().length > 0){
            String storageRoomIds = Arrays.toString(dnDetailDTO.getStorageRoomIds());
            dnDetailDTO.setStorageRoomId(storageRoomIds.substring(1,storageRoomIds.length()-1).replaceAll(" ",""));
        }
        dnDetailDTO.setUpdateUser(this.getSessionCurrentUser().getUserName());
        dnDetailDTO.setUpdateTime(new Date().getTime());
        return getMessage(dnDetailService.modifyDnDetail(dnDetailDTO,getDbShardVO(DbShareField.OUT_WH)));
    }

    /**
     *  入库通知单提交操作
     * @param id
     * @return
     */
    @RequestMapping(value = "/submit/{id}",method = RequestMethod.PUT)
    public ResponseResult submitDn(@PathVariable Long id){
        TWmsDnHeaderDTO dnHeaderDTO = new TWmsDnHeaderDTO();
        String submitUser = this.getSessionCurrentUser().getUserName();
        dnHeaderDTO.setId(id);
        dnHeaderDTO.setUpdateTime(new Date().getTime());
        dnHeaderDTO.setUpdateUser(submitUser);
        return getMessage(dnHeaderService.updateDnToShipment(dnHeaderDTO,submitUser,getDbShardVO(DbShareField.OUT_WH)));
    }

    /***
     * 查询出库通知单明细基础信息
     * @param id
     * @param paramMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{id}/detail/basic",method = RequestMethod.GET)
    public ResponseResult queryDnBasicByHeaderId(@PathVariable Long id,@RequestParam Map paramMap)throws Exception{
        paramMap.put("dnHeaderId",id);
        return getSucResultData(dnHeaderService.queryDnBasics(paramMap,getDbShardVO(DbShareField.OUT_WH)));
    }
}
