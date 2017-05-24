package com.huamengtong.wms.app.controller.main;

import com.huamengtong.wms.core.web.BaseController;
import com.huamengtong.wms.core.web.ResponseResult;
import com.huamengtong.wms.dto.TWmsPrintTempDTO;
import com.huamengtong.wms.main.service.IPrintTempService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/print/temp")
public class PrintTempController extends BaseController
{
    @Autowired
    private IPrintTempService printTempService;

    @RequestMapping(value = "",method = RequestMethod.GET)
    public ResponseResult getPrintTemp(TWmsPrintTempDTO tWmsPrintTempDTO)throws Exception{
        tWmsPrintTempDTO.setTenantId(this.getSessionCurrentUser().getTenantId());
        return getSucResultData(printTempService.queryPrintTempPages(tWmsPrintTempDTO));
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ResponseResult getPrintTempLists()throws Exception{
        return getSucResultData(printTempService.queryPrintTempLists());
    }

    @RequestMapping(value = "",method = RequestMethod.POST)
    public ResponseResult addPrintTemp(@RequestBody TWmsPrintTempDTO tWmsPrintTempDTO)throws Exception{
        tWmsPrintTempDTO.setTenantId(this.getSessionCurrentUser().getTenantId());
        tWmsPrintTempDTO.setCreateTime(new Date().getTime());
        tWmsPrintTempDTO.setCreateUser(this.getSessionCurrentUser().getUserName());
        tWmsPrintTempDTO.setUpdateTime(new Date().getTime());
        tWmsPrintTempDTO.setUpdateUser(this.getSessionCurrentUser().getUserName());
        return getMessage(printTempService.createPrintTemp(tWmsPrintTempDTO));
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public  ResponseResult modify(@RequestBody TWmsPrintTempDTO tWmsPrintTempDTO)throws Exception{
        tWmsPrintTempDTO.setTenantId(this.getSessionCurrentUser().getTenantId());
        tWmsPrintTempDTO.setUpdateTime(new Date().getTime());
        tWmsPrintTempDTO.setUpdateUser(this.getSessionCurrentUser().getUserName());
        return getMessage(printTempService.updatePrintTemp(tWmsPrintTempDTO));
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public ResponseResult delete(@PathVariable Integer id)throws Exception{
        return getMessage(printTempService.removePrintTemp(id));
    }

}
