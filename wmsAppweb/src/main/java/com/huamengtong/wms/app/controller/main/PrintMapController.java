package com.huamengtong.wms.app.controller.main;

import com.huamengtong.wms.core.web.BaseController;
import com.huamengtong.wms.core.web.ResponseResult;
import com.huamengtong.wms.dto.TWmsPrintMapDTO;
import com.huamengtong.wms.main.service.IPrintMapService;
import com.wordnik.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/print/map")
@Api(value = "/print/map",description = "打印管理")
public class PrintMapController extends BaseController {

    @Autowired
    private IPrintMapService printMapService;

    @RequestMapping(value = "",method = RequestMethod.GET)
    public ResponseResult getPrintMap(TWmsPrintMapDTO tWmsPrintMapDTO)throws Exception{
        return getSucResultData(printMapService.queryPrintMapPages(tWmsPrintMapDTO));
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ResponseResult getPrintMapLists()throws Exception{
        return getSucResultData(printMapService.findPrintMap());
    }

    @RequestMapping(value = "",method = RequestMethod.POST)
    public ResponseResult addPrintMap(@RequestBody TWmsPrintMapDTO tWmsPrintMapDTO)throws Exception{
        tWmsPrintMapDTO.setCreateUser(this.getSessionCurrentUser().getUserName());
        tWmsPrintMapDTO.setCreateTime(new Date().getTime());
        tWmsPrintMapDTO.setUpdateTime(new Date().getTime());
        tWmsPrintMapDTO.setUpdateUser(this.getSessionCurrentUser().getUserName());
        return getMessage(printMapService.createPrintMap(tWmsPrintMapDTO));
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public ResponseResult modifyPrintMap(@RequestBody TWmsPrintMapDTO tWmsPrintMapDTO)throws Exception{
        tWmsPrintMapDTO.setUpdateTime(new Date().getTime());
        tWmsPrintMapDTO.setUpdateUser(this.getSessionCurrentUser().getUserName());
        return getMessage(printMapService.updatePrintMap(tWmsPrintMapDTO));
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public ResponseResult deletePrintMap(@PathVariable Integer id)throws Exception{
        return getMessage(printMapService.removePrintMap(id));
    }
}
