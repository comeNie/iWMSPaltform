package com.huamengtong.wms.app.controller.inwh;

import com.huamengtong.wms.core.formwork.db.util.DbShareField;
import com.huamengtong.wms.core.web.BaseController;
import com.huamengtong.wms.core.web.ResponseResult;
import com.huamengtong.wms.dto.inwh.TWmsProPackageDTO;
import com.huamengtong.wms.dto.inwh.TWmsProPackageDetailDTO;
import com.huamengtong.wms.inwh.service.IProPackageDetailService;
import com.huamengtong.wms.inwh.service.IProPackageService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by mario on 2017/2/21.
 */
@RestController
@RequestMapping(value = "/inventory/proPackage")
public class ProPackageController extends BaseController {

    @Autowired
    IProPackageService proPackageService;

    @Autowired
    IProPackageDetailService proPackageDetailService;


    @RequestMapping(value = "",method = RequestMethod.GET)
    public ResponseResult getProPackageList(TWmsProPackageDTO proPackageDTO)throws Exception{
        return getSucResultData(proPackageService.getProPackage(proPackageDTO,getDbShardVO(DbShareField.IN_WH)));
    }

    @RequestMapping(value = "/{id}/detail",method = RequestMethod.GET)
    public ResponseResult queryDetailByParentId(@PathVariable Long id, @RequestParam Map paramMap)throws Exception{
        paramMap.put("parentId",id);
        paramMap.put("offset",getOffset(MapUtils.getInteger(paramMap,"page"),MapUtils.getInteger(paramMap,"pageSize")));
        return getSucResultData(proPackageDetailService.queryProPackageDetailByParent(paramMap,getDbShardVO(DbShareField.IN_WH)));

    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public ResponseResult removeProPackage(@PathVariable Long id)throws Exception{
        return getMessage(proPackageService.removeProPackage(id,getDbShardVO(DbShareField.IN_WH)));
    }
    @RequestMapping(value = "/{id}/detail",method = RequestMethod.POST)
    public ResponseResult createProPackageDetail(@PathVariable Long id, @RequestBody TWmsProPackageDetailDTO proPackageDetailDTO)throws  Exception{
        proPackageDetailDTO.setParentId(id);
        proPackageDetailDTO.setTenantId(getCurrentTenantId());
        proPackageDetailDTO.setCreateUser(this.getSessionCurrentUser().getUserName());
        proPackageDetailDTO.setCreateTime(new Date().getTime());
        proPackageDetailDTO.setUpdateUser(this.getSessionCurrentUser().getUserName());
        proPackageDetailDTO.setUpdateTime(new Date().getTime());
        return getMessage(proPackageDetailService.createProPackageDetail(proPackageDetailDTO,getDbShardVO(DbShareField.IN_WH)));
    }

    @RequestMapping(value = "/{id}/detail/{detailId}",method = RequestMethod.DELETE)
    public ResponseResult removeProPackageDetail(@PathVariable Long id,@PathVariable Long detailId) throws Exception{
        return getMessage(proPackageDetailService.removeProPackageDetail(detailId,getDbShardVO(DbShareField.IN_WH)));
    }

    /***
     * 包装审核
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/reviewed/{id}",method = RequestMethod.PUT)
    public ResponseResult reviewProPackage(@PathVariable Long id)throws Exception{
        return getMessage(proPackageService.updateReviewProPackage(id,this.getSessionCurrentUser().getUserName(),getDbShardVO(DbShareField.IN_WH)));
    }

    /**
     * 包装作废
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/invalid/{id}",method = RequestMethod.PUT)
    public ResponseResult invalidProPackage(@PathVariable Long id)throws  Exception{
        return getMessage(proPackageService.updateInvalidProPackage(id,this.getSessionCurrentUser().getUserName(),getDbShardVO(DbShareField.IN_WH)));
    }



    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseResult createProPackage(@RequestBody Map map) throws Exception {
        TWmsProPackageDTO proPackageDTO = new TWmsProPackageDTO();
        BeanUtils.populate(proPackageDTO,MapUtils.getMap(map,"header"));
        proPackageDTO.setTenantId(this.getCurrentTenantId());
        proPackageDTO.setWarehouseId(this.getCurrentWarehouseId());
        proPackageDTO.setCreateUser(getSessionCurrentUser().getUserName());
        proPackageDTO.setUpdateUser(getSessionCurrentUser().getUserName());
        List<Map> proPackageDetailDTOList = (List<Map>) MapUtils.getObject(map,"detailList");
        List<Map> fuArrayList = (List<Map>) MapUtils.getObject(map,"fuArray");
        return getMessage(proPackageService.addProPackage(proPackageDTO,proPackageDetailDTOList,fuArrayList,getDbShardVO(DbShareField.IN_WH)));
    }


}
