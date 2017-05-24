package com.huamengtong.wms.app.controller.inwh;

import com.huamengtong.wms.core.formwork.db.util.DbShareField;
import com.huamengtong.wms.core.web.BaseController;
import com.huamengtong.wms.core.web.ResponseResult;
import com.huamengtong.wms.dto.inwh.TWmsAsnDetailDTO;
import com.huamengtong.wms.dto.inwh.TWmsAsnHeaderDTO;
import com.huamengtong.wms.inwh.service.IAsnDetailService;
import com.huamengtong.wms.inwh.service.IAsnHeaderService;
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
@RequestMapping("/asn")
public class AsnController extends BaseController {

    @Autowired
    private IAsnHeaderService asnHeaderService;

    @Autowired
    private IAsnDetailService asnDetailService;

    @RequestMapping(value = "",method = RequestMethod.GET)
    public ResponseResult getHeaderList(TWmsAsnHeaderDTO asnHeaderDTO)throws Exception{
        return getSucResultData(asnHeaderService.getAsnHeaders(asnHeaderDTO,getDbShardVO(DbShareField.IN_WH)));
    }

    @RequestMapping(value = "/{id}/detail",method = RequestMethod.GET)
    public ResponseResult queryDetailsByHeaderId(@PathVariable Long id, @RequestParam Map map)throws Exception{
        map.put("id",id);
        map.put("offset",getOffset(MapUtils.getInteger(map,"page"),MapUtils.getInteger(map,"pageSize")));
        return getSucResultData(asnDetailService.queryDetailsByHeaderId(map, getDbShardVO(DbShareField.IN_WH)));
    }

    @RequestMapping(value = "",method = RequestMethod.POST)
    public ResponseResult createAsnHeader(@RequestBody TWmsAsnHeaderDTO asnHeaderDTO)throws Exception{
        asnHeaderDTO.setWarehouseId(getCurrentWarehouseId());
        asnHeaderDTO.setTenantId(getCurrentTenantId());
        asnHeaderDTO.setCreateUser(this.getSessionCurrentUser().getUserName());
        asnHeaderDTO.setCreateTime(new Date().getTime());
        asnHeaderDTO.setUpdateUser(this.getSessionCurrentUser().getUserName());
        asnHeaderDTO.setUpdateTime(new Date().getTime());
        return getMessage(asnHeaderService.insertAsnHeader(asnHeaderDTO,getDbShardVO(DbShareField.IN_WH)));
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public ResponseResult deleteAsnHeader(@PathVariable Long id)throws Exception{
        return getMessage(asnHeaderService.removeByPrimaryKey(id,getDbShardVO(DbShareField.IN_WH)));
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public ResponseResult modifyAsnHeader(@RequestBody TWmsAsnHeaderDTO asnHeaderDTO)throws Exception{
        asnHeaderDTO.setUpdateUser(this.getSessionCurrentUser().getUserName());
        asnHeaderDTO.setUpdateTime(new Date().getTime());
        return getMessage(asnHeaderService.updateAsnHeader(asnHeaderDTO,getDbShardVO(DbShareField.IN_WH)));
    }


    @RequestMapping(value = "/{id}/detail",method = RequestMethod.POST)
    public ResponseResult createAsnDetail(@PathVariable Long id, @RequestBody TWmsAsnDetailDTO asnDetailDTO)throws Exception{
        asnDetailDTO.setTenantId(getCurrentTenantId());
        asnDetailDTO.setWarehouseId(getCurrentWarehouseId());
        asnDetailDTO.setAsnId(id);
        asnDetailDTO.setCreateUser(this.getSessionCurrentUser().getUserName());
        asnDetailDTO.setCreateTime(new Date().getTime());
        asnDetailDTO.setUpdateUser(this.getSessionCurrentUser().getUserName());
        asnDetailDTO.setUpdateTime(new Date().getTime());
        return getMessage(asnDetailService.insertAsnDetail(asnDetailDTO,getDbShardVO(DbShareField.IN_WH)));
    }

    @RequestMapping(value = "/{id}/detail/{detailId}",method = RequestMethod.DELETE)
    public ResponseResult deleteAsnDetail(@PathVariable Long id,@PathVariable Long detailId)throws  Exception{
        return getMessage(asnDetailService.removeByPrimaryKey(detailId,this.getSessionCurrentUser().getUserName(),getDbShardVO(DbShareField.IN_WH)));
    }

    @RequestMapping(value = "/{id}/detail/{detailId}",method = RequestMethod.PUT)
    public ResponseResult modifyAsnDetail(@PathVariable Long id,@PathVariable Long detailId,@RequestBody TWmsAsnDetailDTO asnDetailDTO)throws Exception{
        asnDetailDTO.setId(detailId);
        asnDetailDTO.setAsnId(id);
        asnDetailDTO.setUpdateUser(this.getSessionCurrentUser().getUserName());
        asnDetailDTO.setUpdateTime(new Date().getTime());
        return getMessage(asnDetailService.updateAsnDetail(asnDetailDTO,getDbShardVO(DbShareField.IN_WH)));
    }

    /**
     * 通知单生成质检单
     * @Param id
     */
    @RequestMapping(value = "/createQc/{id}",method = RequestMethod.PUT)
    public ResponseResult generateQcs(@PathVariable String id)throws Exception{
        Long asnHeaderId=Long.parseLong(id);
        return getMessage(asnHeaderService.createQc(asnHeaderId,this.getSessionCurrentUser().getUserName(),getDbShardVO(DbShareField.IN_WH)));
    }


    /**
     * 入库通知单提交
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "submit/{id}",method = RequestMethod.PUT)
    public ResponseResult asnSubmit(@PathVariable String id)throws Exception{
        Long asnHeaderId=Long.parseLong(id);
        return getMessage(asnHeaderService.updateSubmitAsn(asnHeaderId,this.getSessionCurrentUser().getUserName(),getDbShardVO(DbShareField.IN_WH)));
     }


    /***
     * 入库通知单撤销
     * @param id
     * @return
     * @throws Exception
     */
     @RequestMapping(value = "repealed/{id}",method = RequestMethod.PUT)
    public ResponseResult asnRepealed(@PathVariable String id)throws Exception{
         Long headerId = Long.parseLong(id);
         return getMessage(asnHeaderService.updateRepealedAsn(headerId,this.getSessionCurrentUser().getUserName(),getDbShardVO(DbShareField.IN_WH)));
     }

}
