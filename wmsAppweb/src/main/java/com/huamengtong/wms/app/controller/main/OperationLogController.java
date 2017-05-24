package com.huamengtong.wms.app.controller.main;

import com.huamengtong.wms.core.web.BaseController;
import com.huamengtong.wms.core.web.ResponseResult;
import com.huamengtong.wms.dto.TWmsOperationLogDTO;
import com.huamengtong.wms.main.service.IOperationLogService;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


/**
 * Created by Edwin on 2016/11/4.
 */

@RestController
@RequestMapping("/log")
public class OperationLogController extends BaseController {

    @Autowired
    IOperationLogService operationLogService;

    @RequestMapping(value = "/operation", method = RequestMethod.GET)
    public ResponseResult searchOperationLog(@RequestParam Long orderNo, @RequestParam String orderTypeCode,Integer pageSize, Integer page) {
        return new ResponseResult(operationLogService.searchOperationLogsByOrderNo(orderNo, orderTypeCode, pageSize, page));
    }

    @RequestMapping(value = "/operationLog",method = RequestMethod.GET)
    public ResponseResult getOperationLog(TWmsOperationLogDTO operationLogDTO) throws Exception{
       return getSucResultData(operationLogService.getOperationLog(operationLogDTO));
    }


    @RequestMapping(value = "/operationLog/export",method = RequestMethod.GET)
    public ResponseResult exportOperationLogList(@RequestParam Map map) throws Exception{
        TWmsOperationLogDTO operationLogDTO = new TWmsOperationLogDTO();
        if(map.containsKey("orderTypeCode") && MapUtils.getString(map,"orderTypeCode")!=null){
            operationLogDTO.setOrderTypeCode(MapUtils.getString(map,"orderTypeCode"));
        }
        if(map.containsKey("orderNo") && MapUtils.getLong(map,"orderNo")!=null){
            operationLogDTO.setOrderNo(MapUtils.getLong(map,"orderNo"));
        }
        if(map.containsKey("operationCode") && MapUtils.getString(map,"operationCode")!=null){
            operationLogDTO.setOperationCode(MapUtils.getString(map,"operationCode"));
        }
        if(map.containsKey("createUser") && MapUtils.getString(map,"createUser")!=null){
            operationLogDTO.setCreateUser(MapUtils.getString(map,"createUser"));
        }
        if (map.containsKey("description") && MapUtils.getString(map,"description")!=null){
            operationLogDTO.setDescription(MapUtils.getString(map,"description"));
        }
        operationLogDTO.setPage(0);
        operationLogDTO.setPageSize(0);
        return getSucResultData(operationLogService.getOperationLog(operationLogDTO));
    }

}
