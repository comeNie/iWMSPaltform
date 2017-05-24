package com.huamengtong.wms.app.controller.inwh;

import com.huamengtong.wms.core.formwork.db.util.DbShareField;
import com.huamengtong.wms.core.web.BaseController;
import com.huamengtong.wms.core.web.ResponseResult;
import com.huamengtong.wms.dto.inwh.TWmsProInvPackageDTO;
import com.huamengtong.wms.inwh.service.IProInvPackageDetailService;
import com.huamengtong.wms.inwh.service.IProInvPackageService;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by could.hao on 2017/3/29.
 */
@RestController
@RequestMapping(value = "proInvPackage")
public class ProInvPackageController extends BaseController {
    @Autowired
    IProInvPackageService proInvPackageService;
    @Autowired
    IProInvPackageDetailService proInvPackageDetailService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseResult getProInvPackageList(TWmsProInvPackageDTO proInvPackageDTO) throws Exception {
        return getSucResultData(proInvPackageService.getProInvPackage(proInvPackageDTO, getDbShardVO(DbShareField.IN_WH)));
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseResult createProPackage(@RequestBody Map map) throws Exception {
        String logName = getSessionCurrentUser().getUserName();
        Long tenantId = getCurrentTenantId();
        Long warehouseId = getCurrentWarehouseId();
        return getMessage(proInvPackageService.addProInvPackage(map,logName,tenantId,warehouseId,getDbShardVO(DbShareField.IN_WH)));
    }

    @RequestMapping(value = "/{id}/consumeDetail",method = RequestMethod.GET)
    public ResponseResult getProInvPackageConsumeDetail(@PathVariable Long id,@RequestParam Map paramMap)throws  Exception{
        paramMap.put("parentId", id);
        paramMap.put("typeCode","consume");
        paramMap.put("offset", getOffset(MapUtils.getInteger(paramMap, "page"), MapUtils.getInteger(paramMap, "pageSize")));
        return getSucResultData(proInvPackageDetailService.queryProInvPackageDetailByHeader(paramMap, getDbShardVO(DbShareField.IN_WH)));
    }
    @RequestMapping(value = "/{id}/outputDetail",method = RequestMethod.GET)
    public ResponseResult getProInvPackageOutputDetail(@PathVariable Long id,@RequestParam Map paramMap)throws  Exception{
        paramMap.put("parentId", id);
        paramMap.put("typeCode", "output");
        paramMap.put("offset", getOffset(MapUtils.getInteger(paramMap, "page"), MapUtils.getInteger(paramMap, "pageSize")));
        return getSucResultData(proInvPackageDetailService.queryProInvPackageDetailByHeader(paramMap, getDbShardVO(DbShareField.IN_WH)));
    }

    /***
     * 审核
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/reviewed/{id}",method = RequestMethod.PUT)
    public ResponseResult reviewInvPackage(@PathVariable Long id)throws Exception{
        return getMessage(proInvPackageService.updateReviewProInvPackage(id,this.getSessionCurrentUser().getUserName(),getDbShardVO(DbShareField.IN_WH)));
    }

    /**
     * 作废
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/invalid/{id}",method = RequestMethod.PUT)
    public ResponseResult invalidInvPackage(@PathVariable Long id)throws  Exception{
        return getMessage(proInvPackageService.updateInvalidProInvPackage(id,this.getSessionCurrentUser().getUserName(),getDbShardVO(DbShareField.IN_WH)));
    }
}
